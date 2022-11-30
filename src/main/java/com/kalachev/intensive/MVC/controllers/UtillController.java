package com.kalachev.intensive.MVC.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.kalachev.intensive.configuration.AppConfig;
import com.kalachev.intensive.initialization.Initializer;

import io.swagger.annotations.ApiOperation;

@Controller
public class UtillController {
  @Autowired
  Initializer initializerHibernate;

  static final String SUCCESS_PAGE = "success-page";

  @GetMapping("/")
  @ApiOperation(value = "home page", notes = "Display home page")
  public String home() {
    return "home";
  }

  @GetMapping("/position")
  @ApiOperation(value = "position page", notes = "Display position page")
  public String position() {
    return "position";
  }

  @GetMapping("/employee")
  @ApiOperation(value = "employee page", notes = "Display employee page")
  public String employee() {
    return "employee";
  }

  @GetMapping("/project")
  @ApiOperation(value = "project page", notes = "Display project page")
  public String project() {
    return "project";
  }

  @GetMapping("/customer")
  @ApiOperation(value = "customer page", notes = "Display customer page")
  public String customer() {
    return "customer";
  }

  @GetMapping("/refresh-all-data")
  @ApiOperation(value = "refresh data", notes = "recreate tables")
  public String refreshData() {
    ApplicationContext context = new AnnotationConfigApplicationContext(
        AppConfig.class);
    Initializer initializer = (Initializer) context
        .getBean("initializerHibernate");
    initializer.cleanTables();
    initializer.initializeTables();
    ((ConfigurableApplicationContext) context).close();
    return SUCCESS_PAGE;
  }
}
