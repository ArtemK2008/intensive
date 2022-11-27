package com.kalachev.intensive.initialization.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Component;

import com.kalachev.intensive.initialization.EmployeeInitializer;

@Component
public class EmployeeInitializerImpl implements EmployeeInitializer {

  Random random;

  public EmployeeInitializerImpl() {
    super();
    random = new Random();
  }

  @Override
  public List<String> generateEmployeeNames() {
    List<String> employees = new ArrayList<>();
    employees.add("Dmitriy Averianov");
    employees.add("Konstantin Artamonov");
    employees.add("Gleb Buryak");
    employees.add("Polina Verstakova");
    employees.add("Aleksandr Vorotinskiy");
    employees.add("Andrey Diomidov");
    employees.add("Aleksandr Dmitriev");
    employees.add("Andrey Zaharenkov");
    employees.add("Nikolay Zebnickiy");
    employees.add("Evgeniy Zotov");
    employees.add("Artem Kalachev");
    employees.add("Vladislav Lyapunov");
    return employees;
  }

  public Map<String, List<String>> assignEmployeesToPossitions(
      List<String> employees, List<String> possitions) {
    checkInput(possitions);
    checkInput(employees);
    Map<String, List<String>> employeesInPositions = prepareEmptyPossitions(
        possitions);
    for (String employee : employees) {
      int currRandom = random.nextInt(possitions.size());
      String currPosition = possitions.get(currRandom);
      List<String> currEmployees = employeesInPositions.get(currPosition);
      currEmployees = employeesInPositions.get(possitions.get(currRandom));
      currEmployees.add(employee);
      employeesInPositions.put(possitions.get(currRandom), currEmployees);
    }
    return employeesInPositions;
  }

  private void checkInput(List<String> entityData) {
    if (entityData == null || entityData.isEmpty()) {
      throw new IllegalArgumentException();
    }
  }

  private Map<String, List<String>> prepareEmptyPossitions(
      List<String> positions) {
    Map<String, List<String>> map = new LinkedHashMap<>();
    positions.stream().forEach(p -> map.put(p, new ArrayList<>()));
    return map;
  }

}
