package com.kalachev.intensive.initialization;

import java.util.List;
import java.util.Map;

public interface EmployeeInitializer {

  public List<String> generateEmployeeNames();

  public Map<String, List<String>> assignEmployeesToPossitions(
      List<String> employees, List<String> possitions);

}
