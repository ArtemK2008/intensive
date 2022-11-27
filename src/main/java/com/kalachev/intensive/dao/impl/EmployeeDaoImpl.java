package com.kalachev.intensive.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kalachev.intensive.dao.EmployeeDao;
import com.kalachev.intensive.dao.PositionDao;
import com.kalachev.intensive.dao.entities.Employee;
import com.kalachev.intensive.dao.entities.Position;

@Component
public class EmployeeDaoImpl implements EmployeeDao {
  @Autowired
  SessionFactory sessionFactory;
  @Autowired
  PositionDao positionDao;

  @Override
  public Employee findByName(String name) {
    Employee employee = new Employee();
    String[] splited = name.split(" ");
    Transaction transaction = null;
    try (Session session = sessionFactory.openSession()) {
      transaction = session.beginTransaction();
      Query query = session.createQuery(
          "from employee where first_name=:first and last_name=:last");
      query.setParameter("first", splited[0]);
      query.setParameter("last", splited[1]);
      employee = (Employee) query.getSingleResult();
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
    }
    return employee;

  }

  @Override
  public void assignEmployeesToPositions(
      Map<String, List<String>> employeesWithPositions) {
    Transaction transaction = null;

    for (Entry<String, List<String>> entry : employeesWithPositions
        .entrySet()) {
      Position curPosition = positionDao.findByTitle(entry.getKey());
      for (String name : entry.getValue()) {
        Employee curEmployee = findByName(name);
        curEmployee.setPosition(curPosition);
        curPosition.getEmployees().add(curEmployee);
        try (Session session = sessionFactory.openSession()) {
          transaction = session.beginTransaction();
          session.saveOrUpdate(curEmployee);
          session.saveOrUpdate(curPosition);
          transaction.commit();
        } catch (Exception e) {
          if (transaction != null) {
            transaction.rollback();
          }
        }
      }
    }
  }
}
