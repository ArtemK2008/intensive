package com.kalachev.intensive.dao.entities;

import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity(name = "employee")
public class Employee {
  @Id
  @SequenceGenerator(name = "employee_seq", sequenceName = "employee_sequence", initialValue = 1, allocationSize = 25)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_seq")
  @Column(name = "employee_id", nullable = false)
  private int id;
  @Column(name = "first_name")
  private String firstName;
  @Column(name = "last_name")
  private String lastName;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "position_id", nullable = true, updatable = true, insertable = true)
  private Position position;
  @ManyToMany(mappedBy = "employees", fetch = FetchType.EAGER)
  private Set<Project> projects;

  public Employee() {
    super();
  }

  public Position getPosition() {
    return position;
  }

  public void setPosition(Position position) {
    this.position = position;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Set<Project> getProjects() {
    return projects;
  }

  public void setProjects(Set<Project> projects) {
    this.projects = projects;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Employee other = (Employee) obj;
    return id == other.id;
  }

}
