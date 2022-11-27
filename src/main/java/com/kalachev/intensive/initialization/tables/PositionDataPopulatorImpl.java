package com.kalachev.intensive.initialization.tables;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kalachev.intensive.dao.entities.Position;
import com.kalachev.intensive.initialization.PositionDataPopulator;

@Component
public class PositionDataPopulatorImpl implements PositionDataPopulator {
  @Autowired
  SessionFactory sessionFactory;

  @Override
  public void populatePositions(List<String> positions) {
    Transaction transaction = null;
    try (Session session = sessionFactory.openSession()) {
      transaction = session.beginTransaction();
      for (String pos : positions) {
        Position position = new Position();
        position.setTitle(pos);
        session.save(position);
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
