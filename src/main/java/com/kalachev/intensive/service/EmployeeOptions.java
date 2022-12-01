package com.kalachev.intensive.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kalachev.intensive.dao.EmployeeDao;
import com.kalachev.intensive.dao.entities.Employee;

@Component
public class EmployeeOptions {

  // VALIDATION COMING HERE SOON

  @Autowired
  EmployeeDao employeeDaoImpl;

  public List<String> displayEmployees() {
    List<Employee> employees = employeeDaoImpl.findAll();
    return employees.stream().map(e -> e.getFirstName() + " " + e.getLastName())
        .collect(Collectors.toList());
  }

  public boolean addNewEmployee(String fullName, String positionTitle) {
    return employeeDaoImpl.insert(fullName, positionTitle);
  }

  public boolean deleteEmployeeById(int id) {
    return employeeDaoImpl.delete(id);
  }

  public boolean changeEmployeesPosition(int employeeId, String newPosition) {
    return employeeDaoImpl.changePosition(employeeId, newPosition);
  }

  public boolean assignToProject(String fullName, String title) {
    return employeeDaoImpl.addToProject(fullName, title);
  }

  public boolean removeFromProject(String fullName, String title) {
    return employeeDaoImpl.deleteFromProject(fullName, title);
  }

}
