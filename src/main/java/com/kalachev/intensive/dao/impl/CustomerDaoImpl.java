package com.kalachev.intensive.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kalachev.intensive.dao.CustomerDao;
import com.kalachev.intensive.dao.ProjectDao;
import com.kalachev.intensive.dao.entities.Customer;
import com.kalachev.intensive.dao.entities.Project;

@Component
public class CustomerDaoImpl implements CustomerDao {
  Random random;
  @Autowired
  SessionFactory sessionFactory;
  @Autowired
  ProjectDao projectDaoImpl;

  public CustomerDaoImpl() {
    super();
    random = new Random();
  }

  @Override
  public List<Customer> findAll() {
    Transaction transaction = null;
    List<Customer> customers = new ArrayList<>();
    try (Session session = sessionFactory.openSession()) {
      transaction = session.beginTransaction();
      Query query = session.createQuery("SELECT c FROM customer c",
          Customer.class);
      customers = query.getResultList();
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
    }
    return customers;
  }

  @Override
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

  @Override
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
