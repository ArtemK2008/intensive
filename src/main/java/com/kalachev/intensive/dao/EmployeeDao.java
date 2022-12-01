package com.kalachev.intensive.dao;

import java.util.List;
import java.util.Map;

import com.kalachev.intensive.dao.entities.Employee;

public interface EmployeeDao {

  Employee findByName(String name);

  public Employee findById(int id);

  public boolean insert(String fullName, String positionTitle);

  public boolean changePosition(int employeeId, String newPosition);

  public boolean delete(int id);

  public boolean checkIfNameExists(String name);

  void assignEmployeesToPositions(
      Map<String, List<String>> employeesWithPositions);

  public List<Employee> findAll();

  public boolean addToProject(String fullName, String title);

  public boolean deleteFromProject(String fullName, String title);
}