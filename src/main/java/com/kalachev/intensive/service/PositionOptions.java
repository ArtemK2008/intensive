package com.kalachev.intensive.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kalachev.intensive.dao.PositionDao;
import com.kalachev.intensive.dao.entities.Employee;
import com.kalachev.intensive.dao.entities.Position;

@Component
public class PositionOptions {
  @Autowired
  PositionDao positionDaoImpl;

  public List<String> findAllPositions() {
    List<Position> positions = positionDaoImpl.findAll();
    return positions.stream().map(Position::getTitle)
        .collect(Collectors.toList());

  }

  public boolean addPosition(String title) {
    return positionDaoImpl.insert(title);
  }

  public boolean deletePosition(String title) {
    return positionDaoImpl.delete(title);
  }

  public List<String> displayPositionEmployees(String title) {
    Set<Employee> employees = positionDaoImpl.getEmployees(title);
    return employees.stream().map(e -> e.getFirstName() + " " + e.getLastName())
        .collect(Collectors.toList());
  }

}
