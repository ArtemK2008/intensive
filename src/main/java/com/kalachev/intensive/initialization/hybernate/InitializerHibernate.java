package com.kalachev.intensive.initialization.hybernate;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kalachev.intensive.dao.EmployeeDao;
import com.kalachev.intensive.dao.PositionDao;
import com.kalachev.intensive.dao.impl.CustomerDaoImpl;
import com.kalachev.intensive.dao.impl.ProjectDaoImpl;
import com.kalachev.intensive.initialization.CustomerDataPopulator;
import com.kalachev.intensive.initialization.CustomerInitializer;
import com.kalachev.intensive.initialization.DatabaseCleaner;
import com.kalachev.intensive.initialization.EmployeeDataPopulator;
import com.kalachev.intensive.initialization.EmployeeInitializer;
import com.kalachev.intensive.initialization.PositionDataPopulator;
import com.kalachev.intensive.initialization.PositionInitializer;
import com.kalachev.intensive.initialization.ProjectDataPopulator;
import com.kalachev.intensive.initialization.ProjectInitializer;

@Component
public class InitializerHibernate {
  List<String> employees;
  List<String> positions;
  List<String> projects;
  List<String> custumers;
  @Autowired
  EmployeeInitializer employeeInitializerImpl;
  @Autowired
  EmployeeDataPopulator employeeDataPopulatorImpl;
  @Autowired
  EmployeeDao employeeDaoImpl;

  @Autowired
  PositionInitializer positionInitializerImpl;
  @Autowired
  PositionDataPopulator positionDataPopulatorImpl;
  @Autowired
  PositionDao positionDaoImpl;

  @Autowired
  ProjectInitializer projectInitializerImpl;
  @Autowired
  ProjectDataPopulator projectDataPopulatorImpl;
  @Autowired
  ProjectDaoImpl projectDaoImpl;

  @Autowired
  CustomerInitializer customerInitializerImpl;
  @Autowired
  CustomerDataPopulator customerDataPopulatorImpl;
  @Autowired
  CustomerDaoImpl customerDaoImpl;

  @Autowired
  DatabaseCleaner databaseCleanerImpl;

  public InitializerHibernate() {
    super();
  }

  public void initializeTables() {
    databaseCleanerImpl.clearAllTables();
    System.out.println("cleaned");
    generateTableData();
    populateTables();
    assignPositionsToEmployees(employees, positions);
    assignProjectsToEmployees(projects, employees);
    assignCustomersToProjects(custumers, projects);

  }

  private void generateTableData() {
    employees = employeeInitializerImpl.generateEmployeeNames();
    positions = positionInitializerImpl.generatePositions();
    projects = projectInitializerImpl.generateProjects();
    custumers = customerInitializerImpl.generateCustomers();
  }

  private void populateTables() {
    employeeDataPopulatorImpl.populateEmployees(employees);
    positionDataPopulatorImpl.populatePositions(positions);
    projectDataPopulatorImpl.populateProjects(projects);
    customerDataPopulatorImpl.populateCustomers(custumers);
  }

  private void assignPositionsToEmployees(List<String> employees,
      List<String> positions) {
    Map<String, List<String>> employeesWithPositions = employeeInitializerImpl
        .assignEmployeesToPossitions(employees, positions);
    employeeDaoImpl.assignEmployeesToPositions(employeesWithPositions);
  }

  private void assignProjectsToEmployees(List<String> projects,
      List<String> employees) {
    projectDaoImpl.assignEmployeesToProjects(projects, employees);
  }

  private void assignCustomersToProjects(List<String> customers,
      List<String> projects) {
    customerDaoImpl.assignCustomersToProjects(customers, projects);
  }

}
