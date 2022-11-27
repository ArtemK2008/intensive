package com.kalachev.intensive.dao.entities;

import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;

@Entity(name = "position")
public class Position {
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "position_id")
  private int positionId;
  @Column(name = "title")
  private String title;
  @OneToMany(mappedBy = "position", fetch = FetchType.LAZY)
  private Set<Employee> employees;

  public Position() {
    super();
  }

  public int getPositionId() {
    return positionId;
  }

  public void setPositionId(int positionId) {
    this.positionId = positionId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Set<Employee> getEmployees() {
    return employees;
  }

  public void setEmployees(Set<Employee> employees) {
    this.employees = employees;
  }

  @Override
  public int hashCode() {
    return Objects.hash(positionId);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Position other = (Position) obj;
    return positionId == other.positionId;
  }

}
