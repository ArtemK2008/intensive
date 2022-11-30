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
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Class representing a customers in the application.")
@Entity(name = "customer")
public class Customer {

  @ApiModelProperty(notes = "Unique identifier of the Customer.", example = "1", required = true, position = 0)
  @Id
  @SequenceGenerator(name = "customer_seq", sequenceName = "customer_sequence", initialValue = 1, allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq")
  @Column(name = "customer_id")
  private int id;

  @ApiModelProperty(notes = "Name of the Customer's Company.", example = "VK", required = true, position = 1)
  @Size(max = 20)
  @NotBlank
  @Column(name = "company_name")
  private String companyName;

  @ApiModelProperty(notes = "Project this Company is working with.", example = "enterprice", required = false, position = 2)
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
