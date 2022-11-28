package com.kalachev.intensive.dao.impl;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kalachev.intensive.dao.entities.Customer;
import com.kalachev.intensive.dao.entities.Project;

@Component
public class CustomerDaoImpl {
  Random random;
  @Autowired
  SessionFactory sessionFactory;
  @Autowired
  ProjectDaoImpl projectDaoImpl;

  public CustomerDaoImpl() {
    super();
    random = new Random();
  }

  public Customer findByName(String companyName) {
    Transaction transaction = null;
    Customer customer = new Customer();
    try (Session session = sessionFactory.openSession()) {
      transaction = session.beginTransaction();
      Query query = session
          .createQuery("from customer where company_name=:companyName");
      query.setParameter("companyName", companyName);
      customer = (Customer) query.getSingleResult();
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
    }
    return customer;
  }

  public void assignCustomersToProjects(List<String> customers,
      List<String> projects) {
    Collections.shuffle(projects);
    Collections.shuffle(customers);

    Transaction transaction = null;
    try (Session session = sessionFactory.openSession()) {
      transaction = session.beginTransaction();
      for (int i = 0; i < customers.size(); i++) {
        Customer customer = findByName(customers.get(i));
        Project project = projectDaoImpl.findByTitle(projects.get(i));
        customer.setProject(project);
        project.setCustomer(customer);
        session.saveOrUpdate(project);
        session.saveOrUpdate(customer);
      }
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
    }

  }

}
