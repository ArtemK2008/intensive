package com.kalachev.intensive.initialization.tables;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kalachev.intensive.dao.entities.Project;
import com.kalachev.intensive.initialization.ProjectDataPopulator;

@Component
public class ProjectDataPopulatorImpl implements ProjectDataPopulator {
  @Autowired
  SessionFactory sessionFactory;

  @Override
  public void populateProjects(List<String> projects) {
    Transaction transaction = null;
    try (Session session = sessionFactory.openSession()) {
      transaction = session.beginTransaction();
      for (String proj : projects) {
        Project project = new Project();
        project.setTitle(proj);
        session.save(project);
        session.flush();
      }
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
    }
  }

}
