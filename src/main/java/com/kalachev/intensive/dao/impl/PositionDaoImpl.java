package com.kalachev.intensive.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
public class PositionDaoImpl implements PositionDao {
  @Autowired
  SessionFactory sessionFactory;
  @Autowired
  EmployeeDao employeeDaoImpl;

  @Override
  public Position findById(int positionId) {
    Transaction transaction = null;
    Position position = new Position();
    try (Session session = sessionFactory.openSession()) {
      transaction = session.beginTransaction();
      position = session.get(Position.class, positionId);
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
    }
    return position;
  }

  @Override
  public Position findByTitle(String positionTitle) {
    Transaction transaction = null;
    Position position = new Position();
    try (Session session = sessionFactory.openSession()) {
      transaction = session.beginTransaction();
      Query query = session
          .createQuery("from position where title=:positionTitle");
      query.setParameter("positionTitle", positionTitle);
      position = (Position) query.getSingleResult();
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
    }
    return position;
  }

  @Override
  public List<Position> findAll() {
    Transaction transaction = null;
    List<Position> positions = new ArrayList<>();
    try (Session session = sessionFactory.openSession()) {
      transaction = session.beginTransaction();
      Query query = session.createQuery("SELECT p FROM position p",
          Position.class);
      positions = query.getResultList();
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
    }
    return positions;
  }

  @Override
  public boolean insert(String title) {
    boolean isAdded = false;
    Transaction transaction = null;
    Position position = new Position();
    try (Session session = sessionFactory.openSession()) {
      transaction = session.beginTransaction();
      position.setTitle(title);
      session.save(position);
      transaction.commit();
      isAdded = true;
    } catch (Exception e) {
      e.printStackTrace();
      if (transaction != null) {
        transaction.rollback();
      }
    }
    return isAdded;
  }

  @Override
  public boolean delete(String title) {
    boolean isDeleted = false;
    Transaction transaction = null;
    try (Session session = sessionFactory.openSession()) {
      transaction = session.beginTransaction();

      Query query = session.createQuery("from position where title=:title");
      query.setParameter("title", title);
      Position position = (Position) query.getSingleResult();
      Set<Employee> employees = position.getEmployees();
      for (Employee e : employees) {
        e.setPosition(null);
      }
      session.delete(position);
      transaction.commit();
      isDeleted = true;
    } catch (Exception e) {
      e.printStackTrace();
      if (transaction != null) {
        transaction.rollback();
      }
    }
    return isDeleted;
  }

}
