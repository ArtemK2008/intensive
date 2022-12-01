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
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
public class CustomerController {
  @Autowired
  CustomerOptions customerOptions;

  static final String ERROR_PAGE = "error-page";
  static final String SUCCESS_PAGE = "success-page";
  static final String RESULT = "result";
  static final String EMPTY = "empty";
  static final String UNEXPECTED_ERROR = "Unexpected Error";
  static final String MIME_HTML = "text/html";

  @GetMapping(value = "/find-all-customers")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Displays all existiong customers") })
  @ApiOperation(value = "find all customers", notes = "This method displays all existing customers", produces = MIME_HTML)
  public String findCustomers(Model model) {
    List<String> customers = customerOptions.findAllCustomers();
    if (customers.isEmpty()) {
      model.addAttribute(EMPTY, "no customers found");
    }
    model.addAttribute("customers", customers);
    return "find-all-customers";
  }

  @GetMapping(value = "/delete-customer")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Shows dropdown with existing customers") })
  @ApiOperation(value = "delete customer", notes = "This method asks to pick existing customer from dropdown", produces = MIME_HTML)
  public String deleteCustomer(Model model) {
    List<String> customers = customerOptions.findAllCustomers();
    if (customers.isEmpty()) {
      model.addAttribute(EMPTY, "no projects found");
    }
    model.addAttribute("customers", customers);
    return "delete-customer";
  }

  @PostMapping(value = "/delete-customer")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Tries to delete picked Customer") })
  @ApiOperation(value = "delete customer", notes = "This method tries to delete picked customer", produces = MIME_HTML)
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
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Customer deleted") })
  @ApiOperation(value = "Finish Deliting Customer", notes = "If Customer Was Successfully Deleted Shows Success Page ", produces = MIME_HTML)
  public String finishDeliting() {
    return SUCCESS_PAGE;
  }

}
