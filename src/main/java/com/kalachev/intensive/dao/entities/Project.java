package com.kalachev.intensive.dao.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity(name = "project")
public class Project {
  @Id
  @SequenceGenerator(name = "project_seq", sequenceName = "project_sequence", initialValue = 1, allocationSize = 25)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_seq")
  @Column(name = "project_id")
  private int id;
  @Column(name = "title")
  private String title;
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "projects_employees", joinColumns = {
      @JoinColumn(name = "project_id") }, inverseJoinColumns = {
          @JoinColumn(name = "employee_id") })
  private Set<Employee> employees;
  @OneToOne(mappedBy = "project")
  private Customer customer;

  public Project() {
    super();
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
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

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

}
