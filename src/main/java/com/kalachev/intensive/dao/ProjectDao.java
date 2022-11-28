package com.kalachev.intensive.dao;

import java.util.List;
import java.util.Set;

import com.kalachev.intensive.dao.entities.Employee;
import com.kalachev.intensive.dao.entities.Project;

public interface ProjectDao {

  List<Project> findAll();

  Project findByTitle(String projectTitle);

  Set<Employee> findEmployees(String projectTitle);

  Set<Project> findProjectsOfChosenEmployee(String fullname);

  boolean insert(String title);

  boolean delete(String title);

  void assignEmployeesToProjects(List<String> projects, List<String> employees);

  void assineSingleEmployee(String name, List<String> projects);

}