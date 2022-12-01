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
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
public class UtillController {
  @Autowired
  Initializer initializerHibernate;

  static final String SUCCESS_PAGE = "success-page";
  static final String MIME_HTML = "text/html";

  @GetMapping("/")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Home page loaded") })
  @ApiOperation(value = "home page", notes = "Display home page", produces = MIME_HTML)
  public String home() {
    return "home";
  }

  @GetMapping("/position")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Position page loaded") })
  @ApiOperation(value = "position page", notes = "Display position page", produces = MIME_HTML)
  public String position() {
    return "position";
  }

  @GetMapping("/employee")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Employee page loaded") })
  @ApiOperation(value = "employee page", notes = "Display employee page", produces = MIME_HTML)
  public String employee() {
    return "employee";
  }

  @GetMapping("/project")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Project page loaded") })
  @ApiOperation(value = "project page", notes = "Display project page", produces = MIME_HTML)
  public String project() {
    return "project";
  }

  @GetMapping("/customer")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Customer page loaded") })
  @ApiOperation(value = "customer page", notes = "Display customer page", produces = MIME_HTML)
  public String customer() {
    return "customer";
  }

  @GetMapping("/refresh-all-data")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Tables cleaned and recreated") })
  @ApiOperation(value = "refresh data", notes = "cleanes and recreates tables", produces = MIME_HTML)
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
