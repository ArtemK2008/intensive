package com.kalachev.intensive.dao;

import java.util.List;

import com.kalachev.intensive.dao.entities.Customer;

public interface CustomerDao {

  List<Customer> findAll();

  Customer findByName(String companyName);

  void assignCustomersToProjects(List<String> customers, List<String> projects);

  public boolean delete(String companyName);

}