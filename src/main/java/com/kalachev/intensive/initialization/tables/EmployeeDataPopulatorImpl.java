package com.kalachev.intensive.initialization.tables;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kalachev.intensive.dao.entities.Employee;

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
          employee.setFirstName(fullname.split(" ")[0]);
          employee.setLastName(fullname.split(" ")[1]);
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
    System.out.println(
        name + " is name and " + name.split(" ").length + " is length");
    return name.split(" ").length == 2;
  }

}
