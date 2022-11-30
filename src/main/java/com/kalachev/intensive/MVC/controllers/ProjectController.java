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

  @GetMapping(value = "/find-all-projects")
  @ApiOperation(value = "display Project list", notes = "This Method Displays all existing Projects")
  public String findPositions(Model model) {
    List<String> projects = projectOptions.findAllProjects();
    if (projects.isEmpty()) {
      model.addAttribute(EMPTY, NO_PROJECTS);
    }
    model.addAttribute(PROJECTS, projects);
    return "find-all-projects";
  }

  @GetMapping("/display-employees-in-project")
  @ApiOperation(value = "display Project's Employees", notes = "This Method asks to pick Project Title from dropdown")
  public String showEmployees(Model model) {
    List<String> projects = projectOptions.findAllProjects();
    if (projects.isEmpty()) {
      model.addAttribute(EMPTY, NO_PROJECTS);
    }
    model.addAttribute(PROJECTS, projects);
    return "display-employees-in-project";
  }

  @PostMapping(value = "/display-employees-in-project")
  @ApiOperation(value = "display Project's Employees", notes = "This Method tries to find Employees of choosen Project")
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
  @ApiOperation(value = "finish displaying Projects'Employees", notes = "This Method Displays all Employees For Choosen Project")
  public String displayEmployees() {
    return "find-by-project-employee-list";
  }

  @GetMapping(value = "/create-new-project")
  @ApiOperation(value = "create Project", notes = "This Method asks to type a Title for new Project")
  public String insertProject() {
    return "create-new-project";
  }

  @PostMapping("/create-new-project")
  @ApiOperation(value = "create Project", notes = "This Method tries to create Project with choosen title")
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
  @ApiOperation(value = "finish creating Project", notes = "This Method shows success page if Project was created")
  public String finishInserting() {
    return SUCCESS_PAGE;
  }

  @GetMapping(value = "/delete-project")
  @ApiOperation(value = "delete Project", notes = "This Method asks to choose Project Title to delete from Dropdown")
  public String deleteProject(Model model) {
    List<String> projects = projectOptions.findAllProjects();
    if (projects.isEmpty()) {
      model.addAttribute(EMPTY, NO_PROJECTS);
    }
    model.addAttribute(PROJECTS, projects);
    return "delete-project";
  }

  @PostMapping(value = "/delete-project")
  @ApiOperation(value = "delete Project", notes = "This Method tries to delete Project with choosen title")
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
  @ApiOperation(value = "finish deletin Project", notes = "This Method shows success page if Project was deleted")
  public String finishDeliting() {
    return SUCCESS_PAGE;
  }

}
