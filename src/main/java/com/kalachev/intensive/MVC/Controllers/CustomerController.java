package com.kalachev.intensive.MVC.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.kalachev.intensive.service.CustomerOptions;

@Controller
public class CustomerController {
  @Autowired
  CustomerOptions customerOptions;

  @GetMapping(value = "/find-all-customers")
  public String findCustomers(Model model) {
    List<String> customers = customerOptions.findAllCustomers();
    if (customers.isEmpty()) {
      model.addAttribute("empty", "no customers found");
    }
    model.addAttribute("customers", customers);
    return "find-all-customers";
  }

}
