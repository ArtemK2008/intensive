package com.kalachev.intensive.dao.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity(name = "customer")
public class Customer {
  @Id
  @SequenceGenerator(name = "customer_seq", sequenceName = "customer_sequence", initialValue = 1, allocationSize = 25)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq")
  @Column(name = "customer_id")
  private int id;
  @Column(name = "company_name")
  private String companyName;
  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "project_id")
  private Project project;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

}
