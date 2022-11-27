package com.kalachev.intensive.initialization.hybernate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kalachev.intensive.initialization.EmployeeInitializer;
import com.kalachev.intensive.initialization.tables.EmployeeDataPopulator;

@Component
public class InitializerHibernate {
  List<String> employees;
  List<String> positions;
  @Autowired
  EmployeeInitializer employeeInitializerImpl;
  @Autowired
  EmployeeDataPopulator employeeDataPopulatorImpl;

  public InitializerHibernate(EmployeeInitializer employeeInitializerImpl) {
    super();
    this.employeeInitializerImpl = employeeInitializerImpl;
  }

  public void initializeTables() {
    generateTableData();
    employeeDataPopulatorImpl.populateEmployees(employees);
  }

  private void generateTableData() {
    employees = employeeInitializerImpl.generateEmployeeNames();
  }

}
