package com.kalachev.intensive.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kalachev.intensive.dao.ProjectDao;
import com.kalachev.intensive.dao.entities.Employee;
import com.kalachev.intensive.dao.entities.Project;

@Component
public class ProjectOptions {

  @Autowired
  ProjectDao projectDaoImpl;

  public List<String> findAllProjects() {
    List<Project> projects = projectDaoImpl.findAll();
    return projects.stream().map(Project::getTitle)
        .collect(Collectors.toList());
  }

  public List<String> findProjectsOfChosenEmployee(String employeeName) {
    Set<Project> projects = projectDaoImpl
        .findProjectsOfChosenEmployee(employeeName);
    return projects.stream().map(Project::getTitle)
        .collect(Collectors.toList());
  }

  public boolean addNewProject(String title) {
    return projectDaoImpl.insert(title);
  }

  public boolean deleteProject(String title) {
    return projectDaoImpl.delete(title);
  }

  public List<String> findEmployeesInProject(String projectTitle) {
    Set<Employee> employees = projectDaoImpl.findEmployees(projectTitle);
    return employees.stream().map(e -> e.getFirstName() + " " + e.getLastName())
        .collect(Collectors.toList());
  }

}
