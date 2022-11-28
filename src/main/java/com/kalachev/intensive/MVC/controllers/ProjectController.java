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

@Controller
public class ProjectController {

  @Autowired
  ProjectOptions projectOptions;

  static final String ERROR_PAGE = "error-page";
  static final String SUCCESS_PAGE = "success-page";
  static final String RESULT = "result";
  static final String UNEXPECTED_ERROR = "Unexpected Error";

  @GetMapping(value = "/find-all-projects")
  public String findPositions(Model model) {
    List<String> projects = projectOptions.findAllProjects();
    if (projects.isEmpty()) {
      model.addAttribute("empty", "no projects found");
    }
    model.addAttribute("projects", projects);
    return "find-all-projects";
  }

  @GetMapping("/display-employees-in-project")
  public String showEmployees(Model model) {
    List<String> projects = projectOptions.findAllProjects();
    if (projects.isEmpty()) {
      model.addAttribute("empty", "no projects found");
    }
    model.addAttribute("projects", projects);
    return "display-employees-in-project";
  }

  @PostMapping(value = "/display-employees-in-project")
  public String handleDisplayEmployees(
      @RequestParam("pickedProject") String title, Model model,
      RedirectAttributes redirectAttributes) {

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
  public String displayEmployees() {
    return "find-by-project-employee-list";
  }

  @GetMapping(value = "/create-new-project")
  public String insertProject() {
    return "create-new-project";
  }

  @PostMapping("/create-new-project")
  public String handleAddPositon(@RequestParam("projectTitle") String title,
      Model model) {

    if (!projectOptions.addNewProject(title)) {
      String result = UNEXPECTED_ERROR;
      model.addAttribute(RESULT, result);
      return ERROR_PAGE;
    }
    return "redirect:/proceed-create-project";
  }

  @GetMapping("/proceed-create-project")
  public String finishInserting() {
    return SUCCESS_PAGE;
  }

//
  @GetMapping(value = "/delete-project")
  public String deletePosition(Model model) {
    List<String> projects = projectOptions.findAllProjects();
    if (projects.isEmpty()) {
      model.addAttribute("empty", "no projects found");
    }
    model.addAttribute("projects", projects);
    return "delete-project";
  }

  @PostMapping(value = "/delete-project")
  public String handleDeletePosition(
      @RequestParam("pickedProject") String title, Model model,
      RedirectAttributes redirectAttributes) {

    if (!projectOptions.deleteProject(title)) {
      String result = UNEXPECTED_ERROR;
      model.addAttribute(RESULT, result);
      return ERROR_PAGE;
    }
    return "redirect:/proceed-delete-project";
  }

  @GetMapping("/proceed-delete-project")
  public String finishDeliting() {
    return SUCCESS_PAGE;
  }

}
