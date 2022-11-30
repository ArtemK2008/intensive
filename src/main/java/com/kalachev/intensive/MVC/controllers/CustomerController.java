package com.kalachev.intensive.MVC.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kalachev.intensive.service.CustomerOptions;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Controller
public class CustomerController {
  @Autowired
  CustomerOptions customerOptions;

  static final String ERROR_PAGE = "error-page";
  static final String SUCCESS_PAGE = "success-page";
  static final String RESULT = "result";
  static final String EMPTY = "empty";
  static final String UNEXPECTED_ERROR = "Unexpected Error";

  @GetMapping(value = "/find-all-customers")
  @ApiOperation(value = "find all customers", notes = "This method displays all existing customers")
  public String findCustomers(Model model) {
    List<String> customers = customerOptions.findAllCustomers();
    if (customers.isEmpty()) {
      model.addAttribute(EMPTY, "no customers found");
    }
    model.addAttribute("customers", customers);
    return "find-all-customers";
  }

  @GetMapping(value = "/delete-customer")
  @ApiOperation(value = "delete customer", notes = "This method asks to pick existing customer from dropdown")
  public String deletePosition(Model model) {
    List<String> customers = customerOptions.findAllCustomers();
    if (customers.isEmpty()) {
      model.addAttribute(EMPTY, "no projects found");
    }
    model.addAttribute("customers", customers);
    return "delete-customer";
  }

  @PostMapping(value = "/delete-customer")
  @ApiOperation(value = "delete customer", notes = "This method deletes picked customer", produces = "String")
  public String handleDeleteCustomer(
      @ApiParam(name = "pickedCustomer", type = "String", value = "Name of Customers Company", example = "VK", required = true) @RequestParam("pickedCustomer") String companyName,
      Model model, RedirectAttributes redirectAttributes) {

    if (!customerOptions.deleteCustomer(companyName)) {
      String result = UNEXPECTED_ERROR;
      model.addAttribute(RESULT, result);
      return ERROR_PAGE;
    }
    return "redirect:/proceed-delete-customer";
  }

  @GetMapping("/proceed-delete-customer")
  @ApiOperation(value = "Finish Deliting Customer", notes = "If Customer Was Successfully Deleted Shows Success Page ")
  public String finishDeliting() {
    return SUCCESS_PAGE;
  }

}
