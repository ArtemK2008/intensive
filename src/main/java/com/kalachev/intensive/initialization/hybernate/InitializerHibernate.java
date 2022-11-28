package com.kalachev.intensive.initialization.hybernate;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kalachev.intensive.dao.EmployeeDao;
import com.kalachev.intensive.dao.PositionDao;
import com.kalachev.intensive.dao.impl.ProjectDaoImpl;
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
  @Autowired
  EmployeeInitializer employeeInitializerImpl;
  @Autowired
  EmployeeDataPopulator employeeDataPopulatorImpl;
  @Autowired
  PositionInitializer positionInitializerImpl;
  @Autowired
  PositionDataPopulator positionDataPopulatorImpl;
  @Autowired
  EmployeeDao employeeDaoImpl;
  @Autowired
  PositionDao positionDaoImpl;
  @Autowired
  ProjectDaoImpl projectDaoImpl;
  @Autowired
  ProjectInitializer projectInitializerImpl;
  @Autowired
  ProjectDataPopulator projectDataPopulatorImpl;
  @Autowired
  DatabaseCleaner databaseCleanerImpl;

  public InitializerHibernate() {
    super();
  }

  public void initializeTables() {
    databaseCleanerImpl.clearAllTables();
    generateTableData();
    populateTables();
    // assignPositionsToEmployees(employees, positions);
    assignProjectsToEmployees(projects, employees);

  }

  private void generateTableData() {
    employees = employeeInitializerImpl.generateEmployeeNames();
    positions = positionInitializerImpl.generatePositions();
    projects = projectInitializerImpl.generateProjects();
  }

  private void populateTables() {
    employeeDataPopulatorImpl.populateEmployees(employees);
    positionDataPopulatorImpl.populatePositions(positions);
    projectDataPopulatorImpl.populateProjects(projects);
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

}
