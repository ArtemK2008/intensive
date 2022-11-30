package com.kalachev.intensive.MVC.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kalachev.intensive.service.PositionOptions;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Controller
public class PositionController {
  @Autowired
  PositionOptions positionOptions;

  static final String ERROR_PAGE = "error-page";
  static final String SUCCESS_PAGE = "success-page";
  static final String RESULT = "result";
  static final String UNEXPECTED_ERROR = "Unexpected Error";
  static final String EMPTY = "empty";
  static final String NO_POSITION = "no positions found";
  static final String POSITIONS = "positions";

  @GetMapping(value = "/find-all-positions")
  @ApiOperation(value = "display Positions list", notes = "This Method Displays all existing Positions")
  public String findPositions(Model model) {
    List<String> positions = positionOptions.findAllPositions();
    if (positions.isEmpty()) {
      model.addAttribute(EMPTY, NO_POSITION);
    }
    model.addAttribute(POSITIONS, positions);
    return "find-all-positions";
  }

  @GetMapping(value = "/create-new-position")
  @ApiOperation(value = "create Position", notes = "This method asks to type Title for new Position")
  public String insertPosition() {
    return "create-new-position";
  }

  @PostMapping("/create-new-position")
  @ApiOperation(value = "create Position", notes = "This method tries to create Position with inputed Title")
  public String handleAddPositon(
      @ApiParam(name = "positionTitle", type = "String", value = "title of a new Position", example = "mentor", required = true) @RequestParam("positionTitle") String title,
      Model model) {

    if (!positionOptions.addPosition(title)) {
      String result = UNEXPECTED_ERROR;
      model.addAttribute(RESULT, result);
      return ERROR_PAGE;
    }
    return "redirect:/proceed-create-position";
  }

  @GetMapping("/proceed-create-position")
  @ApiOperation(value = "finish Position creating", notes = "This method show Success page if Position was created")
  public String finishInserting() {
    return SUCCESS_PAGE;
  }

  @GetMapping(value = "/delete-position")
  @ApiOperation(value = "delete Position", notes = "This method asks to pick Position from Dropdown")
  public String deletePosition(Model model) {
    List<String> positions = positionOptions.findAllPositions();
    if (positions.isEmpty()) {
      model.addAttribute(EMPTY, NO_POSITION);
    }
    model.addAttribute(POSITIONS, positions);
    return "delete-position";
  }

  @PostMapping(value = "/delete-position")
  @ApiOperation(value = "delete Position", notes = "This method tries to delete picked Position")
  public String handleDeletePosition(
      @ApiParam(name = "pickedPosition", type = "String", value = "title of Position to delete", example = "hr", required = true) @RequestParam("pickedPosition") String title,
      Model model, RedirectAttributes redirectAttributes) {
    if (!positionOptions.deletePosition(title)) {
      String result = UNEXPECTED_ERROR;
      model.addAttribute(RESULT, result);
      return ERROR_PAGE;
    }
    return "redirect:/proceed-delete-position";
  }

  @GetMapping("/proceed-delete-position")
  @ApiOperation(value = "finish deleting Position", notes = "This method show Success page if Position was deleted")
  public String finishDeliting() {
    return SUCCESS_PAGE;
  }

  @GetMapping("/display-employees")
  @ApiOperation(value = "display Employees of Position", notes = "This Method asks to pick existing Position from dropdown")
  public String showEmployees(Model model) {
    List<String> positions = positionOptions.findAllPositions();
    if (positions.isEmpty()) {
      model.addAttribute(EMPTY, NO_POSITION);
    }
    model.addAttribute(POSITIONS, positions);
    return "choose-position-to-display-employees";
  }

  @PostMapping(value = "/display-employees")
  @ApiOperation(value = "display Employees of Position", notes = "This Method tries to retrieve all Employees of choosen position")
  public String handleDisplayingEmployees(
      @ApiParam(name = "pickedPosition", type = "String", value = "title of Position to display its Employees", example = "hr", required = true) @RequestParam("pickedPosition") String title,
      Model model, RedirectAttributes redirectAttributes) {

    List<String> employees = positionOptions.displayPositionEmployees(title);
    if (employees.isEmpty()) {
      model.addAttribute(RESULT, "no employees in this position");
      return ERROR_PAGE;
    }
    redirectAttributes.addFlashAttribute("employees", employees);
    redirectAttributes.addFlashAttribute("pickedPosition", title);
    return "redirect:/proceed-displayEmployees";
  }

  @GetMapping(value = "/proceed-displayEmployees")
  @ApiOperation(value = "display Employes of choosen Position", notes = "This Method Displays all Employees on choosen Position")
  public String displayEmployees() {
    return "find-by-position-employee-list";
  }

}
