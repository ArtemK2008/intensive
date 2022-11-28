package com.kalachev.intensive.dao.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kalachev.intensive.dao.EmployeeDao;
import com.kalachev.intensive.dao.entities.Employee;
import com.kalachev.intensive.dao.entities.Project;

@Component
public class ProjectDaoImpl {

  Random random;
  @Autowired
  SessionFactory sessionFactory;
  @Autowired
  EmployeeDao employeeDaoImpl;

  private static final int MAX_TAKEN_PROJECT = 3;

  public ProjectDaoImpl() {
    super();
    random = new Random();
  }

  public Project findByTitle(String projectTitle) {
    Transaction transaction = null;
    Project project = new Project();
    try (Session session = sessionFactory.openSession()) {
      transaction = session.beginTransaction();
      Query query = session
          .createQuery("from project where title=:projectTitle");
      query.setParameter("projectTitle", projectTitle);
      project = (Project) query.getSingleResult();
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
    }
    return project;
  }

  public void assignEmployeesToProjects(List<String> projects,
      List<String> employees) {
    for (String name : employees) {
      assineSingleEmployee(name, projects);
    }
  }

  public void assineSingleEmployee(String name, List<String> projects) {

    Employee curEmployee = employeeDaoImpl.findByName(name);
    int curRandom = random.nextInt(MAX_TAKEN_PROJECT) + 1;
    Transaction transaction = null;
    try (Session session = sessionFactory.openSession()) {
      transaction = session.beginTransaction();
      Set<Project> empProjects = curEmployee.getProjects();
      for (int i = 0; i < curRandom; i++) {
        addRandomProject(projects, empProjects);
      }
      session.saveOrUpdate(curEmployee);
      for (Project p : empProjects) {
        p.getEmployees().add(curEmployee);
        session.saveOrUpdate(p);
      }
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
    }
  }

  private Project findRandomProject(List<String> projects) {
    int curRandom = random.nextInt(projects.size());
    return findByTitle(projects.get(curRandom));
  }

  private void addRandomProject(List<String> projectNames,
      Set<Project> empsProjects) {
    Project tempProj = findRandomProject(projectNames);
    String title = tempProj.getTitle();
    Set<String> titles = new HashSet<>();

    for (Project p : empsProjects) {
      titles.add(p.getTitle());
    }

    while (titles.contains(title)) {
      tempProj = findRandomProject(projectNames);
      title = tempProj.getTitle();
    }

    empsProjects.add(tempProj);
  }

}
