package com.kalachev.intensive.initialization.tables;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kalachev.intensive.dao.entities.Customer;
import com.kalachev.intensive.initialization.CustomerDataPopulator;

@Component
public class CustomerDataPopulatorImpl implements CustomerDataPopulator {
  @Autowired
  SessionFactory sessionFactory;

  @Override
  public void populateCustomers(List<String> customers) {
    Transaction transaction = null;
    try (Session session = sessionFactory.openSession()) {
      transaction = session.beginTransaction();
      for (String c : customers) {
        Customer customer = new Customer();
        customer.setCompanyName(c);
        session.save(customer);
        session.flush();
      }
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
    }
  }

}
