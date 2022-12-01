package com.kalachev.intensive.MVC.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kalachev.intensive.service.ProjectOptions;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
public class ProjectController {

  @Autowired
  ProjectOptions projectOptions;

  static final String ERROR_PAGE = "error-page";
  static final String SUCCESS_PAGE = "success-page";
  static final String RESULT = "result";
  static final String EMPTY = "empty";
  static final String UNEXPECTED_ERROR = "Unexpected Error";
  static final String NO_PROJECTS = "no projects found";
  static final String PROJECTS = "projects";
  static final String MIME_HTML = "text/html";

  @GetMapping(value = "/find-all-projects")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Displays all Projects") })
  @ApiOperation(value = "display Project list", notes = "This Method Displays all existing Projects", produces = MIME_HTML)
  public String findPositions(Model model) {
    List<String> projects = projectOptions.findAllProjects();
    if (projects.isEmpty()) {
      model.addAttribute(EMPTY, NO_PROJECTS);
    }
    model.addAttribute(PROJECTS, projects);
    return "find-all-projects";
  }

  @GetMapping("/display-employees-in-project")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Shows dropdown to pick Project") })
  @ApiOperation(value = "display Project's Employees", notes = "This Method asks to pick Project Title from dropdown", produces = MIME_HTML)
  public String showEmployees(Model model) {
    List<String> projects = projectOptions.findAllProjects();
    if (projects.isEmpty()) {
      model.addAttribute(EMPTY, NO_PROJECTS);
    }
    model.addAttribute(PROJECTS, projects);
    return "display-employees-in-project";
  }

  @PostMapping(value = "/display-employees-in-project")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Finds all Employees for picked Project") })
  @ApiOperation(value = "display Project's Employees", notes = "This Method tries to find Employees of choosen Project", produces = MIME_HTML)
  public String handleDisplayEmployees(
      @ApiParam(name = "pickedProject", type = "String", value = "title of Project to create", example = "big", required = true) @RequestParam("pickedProject") String title,
      Model model, RedirectAttributes redirectAttributes) {

    List<String> employees = projectOptions.findEmployeesInProject(title);
    if (employees.isEmpty()) {
      model.addAttribute(RESULT, "no employees in this position");
      return ERROR_PAGE;
    }
    redirectAttributes.addFlashAttribute("employees", employees);
    redirectAttributes.addFlashAttribute("pickedProject", title);
    return "redirect:/proceed-displayEmployees-in-project";
  }

  @GetMapping(value = "/proceed-displayEmployees-in-project")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Displays all Employees for choosen Project") })
  @ApiOperation(value = "finish displaying Projects'Employees", notes = "This Method Displays all Employees For Choosen Project", produces = MIME_HTML)
  public String displayEmployees() {
    return "find-by-project-employee-list";
  }

  @GetMapping(value = "/create-new-project")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Shows form for inserting Project Title") })
  @ApiOperation(value = "create Project", notes = "This Method asks to type a Title for new Project", produces = MIME_HTML)
  public String insertProject() {
    return "create-new-project";
  }

  @PostMapping("/create-new-project")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Tries to create Project") })
  @ApiOperation(value = "create Project", notes = "This Method tries to create Project with choosen title", produces = MIME_HTML)
  public String handleAddProject(
      @ApiParam(name = "projectTitle", type = "String", value = "title of Project to create", example = "colossal", required = true) @RequestParam("projectTitle") String title,
      Model model) {

    if (!projectOptions.addNewProject(title)) {
      String result = UNEXPECTED_ERROR;
      model.addAttribute(RESULT, result);
      return ERROR_PAGE;
    }
    return "redirect:/proceed-create-project";
  }

  @GetMapping("/proceed-create-project")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Project created") })
  @ApiOperation(value = "finish creating Project", notes = "This Method shows success page if Project was created", produces = MIME_HTML)
  public String finishInserting() {
    return SUCCESS_PAGE;
  }

  @GetMapping(value = "/delete-project")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Shows dropdown with existing Projects") })
  @ApiOperation(value = "delete Project", notes = "This Method asks to choose Project Title to delete from Dropdown", produces = MIME_HTML)
  public String deleteProject(Model model) {
    List<String> projects = projectOptions.findAllProjects();
    if (projects.isEmpty()) {
      model.addAttribute(EMPTY, NO_PROJECTS);
    }
    model.addAttribute(PROJECTS, projects);
    return "delete-project";
  }

  @PostMapping(value = "/delete-project")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Tries to delete Project") })
  @ApiOperation(value = "delete Project", notes = "This Method tries to delete Project with choosen title", produces = MIME_HTML)
  public String handleDeleteProject(
      @ApiParam(name = "pickedProject", type = "String", value = "title of Project to delete", example = "huge", required = true) @RequestParam("pickedProject") String title,
      Model model, RedirectAttributes redirectAttributes) {

    if (!projectOptions.deleteProject(title)) {
      String result = UNEXPECTED_ERROR;
      model.addAttribute(RESULT, result);
      return ERROR_PAGE;
    }
    return "redirect:/proceed-delete-project";
  }

  @GetMapping("/proceed-delete-project")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Project deleted") })
  @ApiOperation(value = "finish deletin Project", notes = "This Method shows success page if Project was deleted", produces = MIME_HTML)
  public String finishDeliting() {
    return SUCCESS_PAGE;
  }

}
