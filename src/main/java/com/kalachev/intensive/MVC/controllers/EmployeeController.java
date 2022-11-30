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

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

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
  static final String EMPLOYEES = "employees";

  @GetMapping(value = "/display-employee-list")
  @ApiOperation(value = "display employee list", notes = "This Method Displays all existing Employees", produces = "text/html")
  public String findPositions(Model model) {
    List<String> employees = employeeOptions.displayEmployees();
    if (employees.isEmpty()) {
      model.addAttribute(EMPTY, NO_EMPLOYEES);
    }
    model.addAttribute(EMPLOYEES, employees);
    return "display-all-employees";
  }

  @GetMapping(value = "/add-employee")
  @ApiOperation(value = "Add Employee", notes = "This method asks to insert full name of a new Employee and assign it to Position from dropdown", produces = "text/html")
  public String addEmployee(Model model) {
    List<String> positions = positionOptions.findAllPositions();
    if (positions.isEmpty()) {
      model.addAttribute(EMPTY, "no positions found");
    }
    model.addAttribute("positions", positions);
    return "add-new-employee";
  }

  @PostMapping("/add-employee")
  @ApiOperation(value = "Add Employee", notes = "This method inserts assigned Employee", produces = "text/html", consumes = "text/plain")
  public String handleAddPositon(
      @ApiParam(name = "fullName", type = "String", value = "name of a new Employee", example = "Artem Kalachev", required = true) @RequestParam("fullName") String fullName,
      @ApiParam(name = "pickedPosition", type = "String", value = "Name of a Choosen Position", example = "backend", required = true) @RequestParam("pickedPosition") String positionTitle,
      Model model) {

    if (!employeeOptions.addNewEmployee(fullName, positionTitle)) {
      String result = UNEXPECTED_ERROR;
      model.addAttribute(RESULT, result);
      return ERROR_PAGE;
    }
    return "redirect:/proceed-add-employee";
  }

  @GetMapping("/proceed-add-employee")
  @ApiOperation(value = "Finish Inserting Employee", notes = "This method Shows Success Page if Employee was inserted", produces = "text/html")
  public String finishInserting() {
    return SUCCESS_PAGE;
  }

  @GetMapping(value = "/delete-employee", produces = "text/html")
  @ApiOperation(value = "Delete Employee by Id", notes = "This method asks to insert id of an Employee you want to delete")
  public String deleteEmployee() {
    return "delete-employee";
  }

  @PostMapping(value = "/delete-employee", produces = "text/html")
  @ApiOperation(value = "delete Employee", notes = "This method This method deletes assigned Employee", consumes = "text/plain")
  public String handleDeletePosition(
      @ApiParam(name = "employeeId", type = "String", value = "id of an Employee", example = "4", required = true) @RequestParam("employeeId") String id,
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
  @ApiOperation(value = "Finish Deleting Employee", notes = "This method Shows Success Page if Employee was deleted", produces = "text/html")
  public String finishDeleting() {
    return SUCCESS_PAGE;
  }

  @GetMapping(value = "/change-employee-position", produces = "text/html")
  @ApiOperation(value = "Change Employee Position", notes = "This method asks to insert id of an Employee and pick possible position from dropdown")
  public String changePosition(Model model) {
    List<String> positions = positionOptions.findAllPositions();
    if (positions.isEmpty()) {
      model.addAttribute(EMPTY, "no positions found");
    }
    model.addAttribute("positions", positions);
    return "change-employee-position";
  }

  @PostMapping("/change-employee-position")
  @ApiOperation(value = "Change Employee Position", notes = "This method assign Employee to picked position", produces = "text/html", consumes = "text/plain")
  public String handleChangingPosition(
      @ApiParam(name = "employeeId", type = "String", value = "id of an Employee", example = "4", required = true) @RequestParam("employeeId") String employeeId,
      @ApiParam(name = "pickedPosition", type = "String", value = "new position for an Employee", example = "backend", required = true) @RequestParam("pickedPosition") String positionTitle,
      Model model) {

    int id = Integer.parseInt(employeeId);

    if (!employeeOptions.changeEmployeesPosition(id, positionTitle)) {
      String result = UNEXPECTED_ERROR;
      model.addAttribute(RESULT, result);
      return ERROR_PAGE;
    }
    return "redirect:/proceed-change-position";
  }

  @GetMapping("/proceed-change-position")
  @ApiOperation(value = "Finish Changing Employee Position", notes = "This method Shows Success Page if Employee's possition was changed", produces = "text/html")
  public String finishChangingPosition() {
    return SUCCESS_PAGE;
  }

  @GetMapping(value = "/add-to-project")
  @ApiOperation(value = "Add to Project", notes = "This method asks to pick Employee's name and Project name from dropdowns", produces = "text/html")
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
    model.addAttribute(EMPLOYEES, employees);
    return "add-to-project";
  }

  @PostMapping("/add-to-project")
  @ApiOperation(value = "Add to Project", notes = "This method assign choosen Employee to picked Project", produces = "text/html", consumes = "text/plain")
  public String handleAssigningToProject(
      @ApiParam(name = "pickedEmployee", type = "String", value = "Employee's full name", example = "Artem Kalachev", required = true) @RequestParam("pickedEmployee") String fullName,
      @ApiParam(name = "pickedProject", type = "String", value = "Title of a Project", example = "enterprise", required = true) @RequestParam("pickedProject") String projectTitle,
      Model model) {

    if (!employeeOptions.assignToProject(fullName, projectTitle)) {
      String result = UNEXPECTED_ERROR;
      model.addAttribute(RESULT, result);
      return ERROR_PAGE;
    }
    return "redirect:/proceed-add-to-project";
  }

  @GetMapping("/proceed-add-to-project")
  @ApiOperation(value = "finish assigning to project", notes = "This method shows Success if Employee Project was changed", produces = "text/html")
  public String finishAssigningToProject() {
    return SUCCESS_PAGE;
  }

  @GetMapping(value = "/delete-from-project")
  @ApiOperation(value = "delete from Project", notes = "This method asks to pick Employee's name from dropdowns", produces = "text/html")
  public String deleteFromProject(Model model) {
    List<String> employees = employeeOptions.displayEmployees();
    if (employees.isEmpty()) {
      model.addAttribute(EMPTY, NO_EMPLOYEES);
    }
    model.addAttribute(EMPLOYEES, employees);
    return "choose-employee-to-delete";
  }

  @PostMapping(value = "/delete-from-project")
  @ApiOperation(value = "delete from Project", notes = "This method asks to pick choosen Employee's Project from dropdowns", produces = "text/html", consumes = "text/plain")
  public String handleDeleteFromProject(
      @ApiParam(name = "pickedEmployee", type = "String", value = "Employee's full name", example = "Artem Kalachev", required = true) @RequestParam("pickedEmployee") String fullName,
      Model model,
      @ApiParam(name = "pickedEmployee", type = "String", value = "Employee's full name", example = "Artem Kalachev", required = true) RedirectAttributes redirectAttributes) {

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
  @ApiOperation(value = "proceed deleting from Project", notes = "This method handles redirect for deleling Employee from Project", consumes = "text/plain", produces = "text/html")
  public String proceedDeletingFromProject() {
    return "finish-deleting-from-project";
  }

  @PostMapping(value = "/proceed-delete-from-project")
  @ApiOperation(value = "finish deleting Employee from Project", notes = "This method tries to delete choosen Employee from choosen Project", produces = "text/html", consumes = "text/plain")
  public String finishDeletingFromProject(Model model,
      @ApiParam(name = "pickedProject", type = "String", value = "Project's Title", example = "big", required = true) @RequestParam("pickedProject") String title,
      @ApiParam(name = "fullName", type = "String", value = "Employee's full name", example = "Artem Kalachev", required = true) @RequestParam("fullName") String fullName) {
    if (!employeeOptions.removeFromProject(fullName, title)) {
      String result = UNEXPECTED_ERROR;
      model.addAttribute(RESULT, result);
      return ERROR_PAGE;
    }
    return "redirect:/employee-deleted-from-project";
  }

  @GetMapping("employee-deleted-from-project")
  @ApiOperation(value = "Employee is removed from Project", notes = "This method shows Success if Employee was removed from Project", produces = "text/html")
  public String employeeDeleted() {
    return SUCCESS_PAGE;
  }

}
