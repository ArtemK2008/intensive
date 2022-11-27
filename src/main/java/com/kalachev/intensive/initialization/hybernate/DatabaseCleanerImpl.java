package com.kalachev.intensive.initialization.hybernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kalachev.intensive.initialization.DatabaseCleaner;

@Component
public class DatabaseCleanerImpl implements DatabaseCleaner {

  @Autowired
  SessionFactory sessionFactory;

  @Override
  public void clearAllTables() {
    Transaction transaction = null;
    try (Session session = sessionFactory.openSession()) {
      transaction = session.beginTransaction();
      session.createNativeQuery("DELETE FROM position_employee")
          .executeUpdate();
      session.createNativeQuery("DELETE FROM employee").executeUpdate();
      session.createNativeQuery("DELETE FROM position").executeUpdate();
      session
          .createNativeQuery("ALTER SEQUENCE hibernate_sequence RESTART WITH 1")
          .executeUpdate();
      session
          .createNativeQuery("ALTER SEQUENCE employee_sequence RESTART WITH 1")
          .executeUpdate();
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
    }
  }

}
