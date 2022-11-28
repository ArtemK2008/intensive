package com.kalachev.intensive.MVC.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.kalachev.intensive.configuration.AppConfig;
import com.kalachev.intensive.initialization.hybernate.InitializerHibernate;

@Controller
public class UtillController {
  @Autowired
  InitializerHibernate initializerHibernate;

  static final String SUCCESS_PAGE = "success-page";

  @GetMapping("/")
  public String home() {
    return "home";
  }

  @GetMapping("/position")
  public String position() {
    return "position";
  }

  @GetMapping("/employee")
  public String employee() {
    return "employee";
  }

  @GetMapping("/project")
  public String project() {
    return "project";
  }

  @GetMapping("/customer")
  public String customer() {
    return "customer";
  }

  @GetMapping("/refresh-all-data")
  public String refreshData() {
    ApplicationContext context = new AnnotationConfigApplicationContext(
        AppConfig.class);
    InitializerHibernate test = (InitializerHibernate) context
        .getBean("initializerHibernate");
    test.initializeTables();
    ((ConfigurableApplicationContext) context).close();
    return SUCCESS_PAGE;
  }
}
