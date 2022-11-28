package com.kalachev.intensive.MVC.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kalachev.intensive.service.EmployeeOptions;
import com.kalachev.intensive.service.PositionOptions;
import com.kalachev.intensive.service.ProjectOptions;

@Controller
public class EmployeeController {
  @Autowired
  EmployeeOptions employeeOptions;
  @Autowired
  PositionOptions positionOptions;
  @Autowired
  ProjectOptions projectOptions;

  static final String ERROR_PAGE = "error-page";
  static final String SUCCESS_PAGE = "success-page";
  static final String RESULT = "result";
  static final String UNEXPECTED_ERROR = "Unexpected Error";
  static final String NO_EMPLOYEES = "no employees found";
  static final String EMPTY = "empty";

  @GetMapping(value = "/display-employee-list")
  public String findPositions(Model model) {
    List<String> employees = employeeOptions.displayEmployees();
    if (employees.isEmpty()) {
      model.addAttribute(EMPTY, NO_EMPLOYEES);
    }
    model.addAttribute("employees", employees);
    return "display-all-employees";
  }

  @GetMapping(value = "/add-employee")
  public String addEmployee(Model model) {
    List<String> positions = positionOptions.findAllPositions();
    if (positions.isEmpty()) {
      model.addAttribute(EMPTY, "no positions found");
    }
    model.addAttribute("positions", positions);
    return "add-new-employee";
  }

  @PostMapping("/add-employee")
  public String handleAddPositon(@RequestParam("fullName") String fullName,
      @RequestParam("pickedPosition") String positionTitle, Model model) {

    if (!employeeOptions.addNewEmployee(fullName, positionTitle)) {
      String result = UNEXPECTED_ERROR;
      model.addAttribute(RESULT, result);
      return ERROR_PAGE;
    }
    return "redirect:/proceed-add-employee";
  }

  @GetMapping("/proceed-add-employee")
  public String finishInserting() {
    return SUCCESS_PAGE;
  }

  @GetMapping(value = "/delete-employee")
  public String deleteEmployee() {
    return "delete-employee";
  }

  @PostMapping(value = "/delete-employee")
  public String handleDeletePosition(@RequestParam("employeeId") String id,
      Model model) {
    int test = Integer.parseInt(id);
    if (!employeeOptions.deleteEmployeeById(test)) {
      String result = UNEXPECTED_ERROR;
      model.addAttribute(RESULT, result);
      return ERROR_PAGE;
    }
    return "redirect:/proceed-delete-employee";
  }

  @GetMapping("/proceed-delete-employee")
  public String finishDeliting() {
    return SUCCESS_PAGE;
  }

  @GetMapping(value = "/change-employee-position")
  public String changePosition(Model model) {
    List<String> positions = positionOptions.findAllPositions();
    if (positions.isEmpty()) {
      model.addAttribute("empty", "no positions found");
    }
    model.addAttribute("positions", positions);
    return "change-employee-position";
  }

  @PostMapping("/change-employee-position")
  public String handleChangingPosition(
      @RequestParam("employeeId") String employeeId,
      @RequestParam("pickedPosition") String positionTitle, Model model) {

    int id = Integer.parseInt(employeeId);

    if (!employeeOptions.changeEmployeesPosition(id, positionTitle)) {
      String result = UNEXPECTED_ERROR;
      model.addAttribute(RESULT, result);
      return ERROR_PAGE;
    }
    return "redirect:/proceed-change-position";
  }

  @GetMapping("/proceed-change-position")
  public String finishChangingPosition() {
    return SUCCESS_PAGE;
  }

  @GetMapping(value = "/add-to-project")
  public String addToProject(Model model) {
    List<String> employees = employeeOptions.displayEmployees();
    List<String> projects = projectOptions.findAllProjects();
    if (employees.isEmpty()) {
      model.addAttribute(EMPTY, NO_EMPLOYEES);
    }
    if (projects.isEmpty()) {
      model.addAttribute(EMPTY, "no projects found");
    }
    model.addAttribute("projects", projects);
    model.addAttribute("employees", employees);
    return "add-to-project";
  }

  @PostMapping("/add-to-project")
  public String handleAssigningToProject(
      @RequestParam("pickedEmployee") String fullName,
      @RequestParam("pickedProject") String projectTitle, Model model) {

    if (!employeeOptions.assignToProject(fullName, projectTitle)) {
      String result = UNEXPECTED_ERROR;
      model.addAttribute(RESULT, result);
      return ERROR_PAGE;
    }
    return "redirect:/proceed-add-to-project";
  }

  @GetMapping("/proceed-add-to-project")
  public String finishAssigningToProject() {
    return SUCCESS_PAGE;
  }

  @GetMapping(value = "/delete-from-project")
  public String deleteFromProject(Model model) {
    List<String> employees = employeeOptions.displayEmployees();
    if (employees.isEmpty()) {
      model.addAttribute(EMPTY, NO_EMPLOYEES);
    }
    model.addAttribute("employees", employees);
    return "choose-employee-to-delete";
  }

  @PostMapping(value = "/delete-from-project")
  public String handleDeleteFromProject(
      @RequestParam("pickedEmployee") String fullName, Model model,
      RedirectAttributes redirectAttributes) {

    List<String> projects = projectOptions
        .findProjectsOfChosenEmployee(fullName);

    if (projects.isEmpty()) {
      model.addAttribute(RESULT, "no projects for this employee");
      return ERROR_PAGE;
    }
    redirectAttributes.addFlashAttribute("projects", projects);
    redirectAttributes.addFlashAttribute("pickedEmployee", fullName);
    return "redirect:/proceed-delete-from-project";
  }

  @GetMapping(value = "/proceed-delete-from-project")
  public String proceedDelitingFromProject() {
    return "finish-deliting-from-project";
  }

  @PostMapping(value = "/proceed-delete-from-project")
  public String finishDelitingFromProject(Model model,
      @RequestParam("pickedProject") String title,
      @RequestParam("fullName") String fullName) {
    if (!employeeOptions.removeFromProject(fullName, title)) {
      String result = UNEXPECTED_ERROR;
      model.addAttribute(RESULT, result);
      return ERROR_PAGE;
    }
    return "redirect:/employee-deleted";
  }

  @GetMapping("employee-deleted")
  public String employeeDeleted() {
    return SUCCESS_PAGE;
  }

}
