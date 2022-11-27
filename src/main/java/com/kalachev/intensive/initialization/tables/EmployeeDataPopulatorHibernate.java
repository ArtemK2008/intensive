package com.kalachev.intensive.initialization.tables;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kalachev.intensive.dao.entities.Employee;

@Component
public class EmployeeDataPopulatorHibernate implements EmployeeDataPopulator {
  @Autowired
  SessionFactory sessionFactory;

  public void populateEmployees(List<String> students) {
    Map<String, String> namesSplited = retriveFirstAndLastNames(students);
    Transaction transaction = null;
    try (Session session = sessionFactory.openSession()) {
      transaction = session.beginTransaction();
      for (Entry<String, String> entry : namesSplited.entrySet()) {
        Employee employee = new Employee();
        employee.setFirstName(entry.getKey());
        employee.setLastName(entry.getValue());
        session.save(employee);
        session.flush();
      }
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
    }
  }

  private boolean validateName(String name) {
    return name.split(" ").length == 2;
  }

  private Map<String, String> retriveFirstAndLastNames(List<String> names) {
    Map<String, String> splitedNames = new HashMap<>();
    for (String s : names) {
      if (validateName(s)) {
        String[] splited = s.split(" ");
        splitedNames.put(splited[0], splited[1]);
      }
    }
    return splitedNames;
  }
}
