package com.kalachev.intensive.dao;

import java.util.List;

import com.kalachev.intensive.dao.entities.Position;

public interface PositionDao {

  Position findById(int positionId);

  Position findByTitle(String positionTitle);

  List<Position> findAll();

  public boolean insert(String title);

  public boolean delete(String title);

}