package com.kalachev.intensive.dao;

import java.util.List;
import java.util.Map;

import com.kalachev.intensive.dao.entities.Employee;

public interface EmployeeDao {

  Employee findByName(String name);

  void assignEmployeesToPositions(
      Map<String, List<String>> employeesWithPositions);

}