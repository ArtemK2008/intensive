package com.kalachev.intensive.initialization.tables;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kalachev.intensive.dao.entities.Employee;
import com.kalachev.intensive.initialization.EmployeeDataPopulator;
import com.kalachev.intensive.utills.HibernateUtills;

@Component
public class EmployeeDataPopulatorImpl implements EmployeeDataPopulator {
  @Autowired
  SessionFactory sessionFactory;

  @Override
  public void populateEmployees(List<String> employees) {
    Transaction transaction = null;
    try (Session session = sessionFactory.openSession()) {
      transaction = session.beginTransaction();
      for (String fullname : employees) {
        if (validateName(fullname)) {
          Employee employee = new Employee();
          employee.setFirstName(HibernateUtills.splitBySpace(fullname)[0]);
          employee.setLastName(HibernateUtills.splitBySpace(fullname)[1]);
          session.save(employee);
          session.flush();
        }
      }
      transaction.commit();
    } catch (

    Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
    }
  }

  private boolean validateName(String name) {
    return name.split(" ").length == 2;
  }

}
