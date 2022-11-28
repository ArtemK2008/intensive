package com.kalachev.intensive.dao;

import java.util.List;
import java.util.Set;

import com.kalachev.intensive.dao.entities.Employee;
import com.kalachev.intensive.dao.entities.Position;

public interface PositionDao {

  Position findById(int positionId);

  Position findByTitle(String positionTitle);

  List<Position> findAll();

  public boolean insert(String title);

  public boolean delete(String title);

  public Set<Employee> getEmployees(String title);

}