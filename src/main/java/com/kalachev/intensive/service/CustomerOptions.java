package com.kalachev.intensive.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kalachev.intensive.dao.CustomerDao;
import com.kalachev.intensive.dao.entities.Customer;

@Component
public class CustomerOptions {
  @Autowired
  CustomerDao customerDaoImpl;

  public List<String> findAllCustomers() {
    List<Customer> customers = customerDaoImpl.findAll();
    return customers.stream().map(Customer::getCompanyName)
        .collect(Collectors.toList());
  }

  public boolean deleteCustomer(String companyName) {
    return customerDaoImpl.delete(companyName);
  }

}