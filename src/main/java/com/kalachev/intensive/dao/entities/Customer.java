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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "Class representing a customers in the application.")
@Entity(name = "customer")
public class Customer {

  @Id
  @SequenceGenerator(name = "customer_seq", sequenceName = "customer_sequence", initialValue = 1, allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq")
  @Column(name = "customer_id")
  private int id;

  @Size(max = 20)
  @NotBlank
  @Column(name = "company_name")
  private String companyName;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "project_id")
  private Project project;

  public Customer() {
    super();
  }

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
