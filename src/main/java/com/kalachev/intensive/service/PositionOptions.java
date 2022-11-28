package com.kalachev.intensive.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kalachev.intensive.dao.PositionDao;
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

  public void addPosition() {

  }

  public void deletePosition() {

  }

}
