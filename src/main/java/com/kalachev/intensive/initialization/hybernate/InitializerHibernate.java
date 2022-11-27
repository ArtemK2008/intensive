package com.kalachev.intensive.initialization.hybernate;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kalachev.intensive.dao.EmployeeDao;
import com.kalachev.intensive.dao.PositionDao;
import com.kalachev.intensive.initialization.DatabaseCleaner;
import com.kalachev.intensive.initialization.EmployeeInitializer;
import com.kalachev.intensive.initialization.PositionDataPopulator;
import com.kalachev.intensive.initialization.PositionInitializer;
import com.kalachev.intensive.initialization.tables.EmployeeDataPopulator;

@Component
public class InitializerHibernate {
  List<String> employees;
  List<String> positions;
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
  DatabaseCleaner databaseCleanerImpl;

  public InitializerHibernate(EmployeeInitializer employeeInitializerImpl,
      PositionInitializer positionInitializerImpl) {
    super();
    this.employeeInitializerImpl = employeeInitializerImpl;
    this.positionInitializerImpl = positionInitializerImpl;
  }

  public void initializeTables() {
    databaseCleanerImpl.clearAllTables();
    generateTableData();
    employees.forEach(System.out::println);

    employeeDataPopulatorImpl.populateEmployees(employees);
    positionDataPopulatorImpl.populatePositions(positions);
    assignPositionsToEmployees();
    positionDaoImpl.insert("this is new pos");
    positionDaoImpl.delete("test");
    positionDaoImpl.findAll().stream()
        .forEach(p -> System.out.println(p.getTitle()));
  }

  private void generateTableData() {
    employees = employeeInitializerImpl.generateEmployeeNames();
    positions = positionInitializerImpl.generatePositions();
  }

  private void assignPositionsToEmployees() {
    Map<String, List<String>> employeesWithPositions = employeeInitializerImpl
        .assignEmployeesToPossitions(employees, positions);
    employeeDaoImpl.assignEmployeesToPositions(employeesWithPositions);
  }

}
