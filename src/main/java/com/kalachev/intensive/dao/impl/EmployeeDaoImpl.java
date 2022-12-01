package com.kalachev.intensive.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kalachev.intensive.dao.EmployeeDao;
import com.kalachev.intensive.dao.PositionDao;
import com.kalachev.intensive.dao.ProjectDao;
import com.kalachev.intensive.dao.entities.Employee;
import com.kalachev.intensive.dao.entities.Position;
import com.kalachev.intensive.dao.entities.Project;
import com.kalachev.intensive.utills.HibernateUtills;

@Component
public class EmployeeDaoImpl implements EmployeeDao {
  @Autowired
  SessionFactory sessionFactory;
  @Autowired
  PositionDao positionDaoImpl;
  @Autowired
  ProjectDao projectDaoImpl;

  @Override
  public List<Employee> findAll() {
    Transaction transaction = null;
    List<Employee> employees = new ArrayList<>();
    try (Session session = sessionFactory.openSession()) {
      transaction = session.beginTransaction();
      Query query = session.createQuery("SELECT e FROM employee e",
          Employee.class);
      employees = query.getResultList();
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
    }
    return employees;
  }

  @Override
  public Employee findByName(String name) {
    Employee employee = new Employee();
    Transaction transaction = null;
    try (Session session = sessionFactory.openSession()) {
      transaction = session.beginTransaction();
      Query query = session.createQuery(
          "from employee where first_name=:first and last_name=:last");
      query.setParameter("first", HibernateUtills.splitBySpace(name)[0]);
      query.setParameter("last", HibernateUtills.splitBySpace(name)[1]);
      employee = (Employee) query.getSingleResult();
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
    }
    return employee;
  }

  @Override
  public Employee findById(int id) {
    Transaction transaction = null;
    Employee employee = new Employee();
    try (Session session = sessionFactory.openSession()) {
      transaction = session.beginTransaction();
      employee = session.get(Employee.class, id);
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
    }
    return employee;
  }

  @Override
  public boolean insert(String fullName, String positionTitle) {
    Transaction transaction = null;
    boolean isAdded = false;
    try (Session session = sessionFactory.openSession()) {
      transaction = session.beginTransaction();
      Employee employee = new Employee();
      employee.setFirstName(HibernateUtills.splitBySpace(fullName)[0]);
      employee.setLastName(HibernateUtills.splitBySpace(fullName)[1]);
      Position position = positionDaoImpl.findByTitle(positionTitle);
      employee.setPosition(position);
      position.getEmployees().add(employee);
      session.saveOrUpdate(employee);
      session.saveOrUpdate(position);
      isAdded = true;
      transaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
      if (transaction != null) {
        transaction.rollback();
      }
    }

    return isAdded;
  }

  @Override
  public boolean delete(int id) {
    Transaction transaction = null;
    boolean isDeleted = false;
    try (Session session = sessionFactory.openSession()) {
      transaction = session.beginTransaction();

      Employee employee = session.get(Employee.class, id);
      Position curPosition = employee.getPosition();
      Set<Project> thisEmployeeProjects = employee.getProjects();
      for (Project p : thisEmployeeProjects) {
        p.getEmployees().remove(employee);
      }
      curPosition.getEmployees().remove(employee);
      session.delete(employee);
      isDeleted = true;
      transaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
      if (transaction != null) {
        transaction.rollback();
      }
    }
    return isDeleted;
  }

  @Override
  public boolean changePosition(int employeeId, String newPosition) {
    Transaction transaction = null;
    boolean isChanged = false;
    Position changedPosition = positionDaoImpl.findByTitle(newPosition);

    try (Session session = sessionFactory.openSession()) {
      transaction = session.beginTransaction();

      Query query = session.createQuery(
          "Select e from employee e join fetch e.position where e.id=:employeeId");
      query.setParameter("employeeId", employeeId);
      Employee employee = (Employee) query.getSingleResult();
      employee.getPosition().getEmployees().remove(employee);
      employee.setPosition(changedPosition);
      changedPosition.getEmployees().add(employee);
      session.saveOrUpdate(employee);
      session.saveOrUpdate(changedPosition);
      isChanged = true;
      transaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
      if (transaction != null) {
        transaction.rollback();
      }
    }
    return isChanged;
  }

  @Override
  public boolean addToProject(String fullName, String title) {
    Transaction transaction = null;
    boolean isAdded = false;

    try (Session session = sessionFactory.openSession()) {
      transaction = session.beginTransaction();
      Employee employee = findByName(fullName);
      Set<Project> projects = employee.getProjects();
      Project curProject = projectDaoImpl.findByTitle(title);
      projects.add(curProject);
      curProject.getEmployees().add(employee);
      session.saveOrUpdate(curProject);
      session.saveOrUpdate(employee);
      isAdded = true;
      transaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
      if (transaction != null) {
        transaction.rollback();
      }
    }
    return isAdded;
  }

  @Override
  public boolean deleteFromProject(String fullName, String title) {
    Transaction transaction = null;
    boolean isRemoved = false;

    try (Session session = sessionFactory.openSession()) {
      transaction = session.beginTransaction();
      Employee employee = findByName(fullName);
      Set<Project> projects = employee.getProjects();
      Project curProject = projectDaoImpl.findByTitle(title);
      projects.remove(curProject);
      curProject.getEmployees().remove(employee);
      session.saveOrUpdate(curProject);
      session.saveOrUpdate(employee);
      isRemoved = true;
      transaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
      if (transaction != null) {
        transaction.rollback();
      }
    }
    return isRemoved;
  }

  @Override
  public void assignEmployeesToPositions(
      Map<String, List<String>> employeesWithPositions) {
    Transaction transaction = null;

    for (Entry<String, List<String>> entry : employeesWithPositions
        .entrySet()) {
      Position curPosition = positionDaoImpl.findByTitle(entry.getKey());
      for (String name : entry.getValue()) {
        Employee curEmployee = findByName(name);
        curEmployee.setPosition(curPosition);
        curPosition.getEmployees().add(curEmployee);
        try (Session session = sessionFactory.openSession()) {
          transaction = session.beginTransaction();
          session.saveOrUpdate(curEmployee);
          session.saveOrUpdate(curPosition);
          transaction.commit();
        } catch (Exception e) {
          if (transaction != null) {
            transaction.rollback();
          }
        }
      }
    }
  }

  @Override
  public boolean checkIfNameExists(String name) {
    boolean isExist = false;
    Transaction transaction = null;
    try (Session session = sessionFactory.openSession()) {
      transaction = session.beginTransaction();

      Query query = session.createQuery(
          "from employee where first_name=:first and last_name=:last");
      query.setParameter("first", HibernateUtills.splitBySpace(name)[0]);
      query.setParameter("last", HibernateUtills.splitBySpace(name)[1]);
      if (!query.getResultList().isEmpty()) {
        isExist = true;
      }
      transaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
      if (transaction != null) {
        transaction.rollback();
      }
    }
    return isExist;
  }
}
