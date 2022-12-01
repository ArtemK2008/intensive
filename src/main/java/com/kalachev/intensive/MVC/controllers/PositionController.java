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
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

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
  static final String MIME_HTML = "text/html";

  @GetMapping(value = "/find-all-positions")
  @ApiOperation(value = "display Positions list", notes = "This Method Displays all existing Positions", produces = MIME_HTML)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Existing positions displayed") })
  public String findPositions(Model model) {
    List<String> positions = positionOptions.findAllPositions();
    if (positions.isEmpty()) {
      model.addAttribute(EMPTY, NO_POSITION);
    }
    model.addAttribute(POSITIONS, positions);
    return "find-all-positions";
  }

  @GetMapping(value = "/create-new-position")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Shows form to type Title") })
  @ApiOperation(value = "create Position", notes = "This method asks to type Title for new Position", produces = MIME_HTML)
  public String insertPosition() {
    return "create-new-position";
  }

  @PostMapping("/create-new-position")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Trying to create new Position") })
  @ApiOperation(value = "create Position", notes = "This method tries to create Position with inputed Title", produces = MIME_HTML)
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
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "New Position created") })
  @ApiOperation(value = "finish Position creating", notes = "This method show Success page if Position was created", produces = MIME_HTML)
  public String finishInserting() {
    return SUCCESS_PAGE;
  }

  @GetMapping(value = "/delete-position")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Displays dropdown with existing Positions") })
  @ApiOperation(value = "delete Position", notes = "This method asks to pick Position from Dropdown", produces = MIME_HTML)
  public String deletePosition(Model model) {
    List<String> positions = positionOptions.findAllPositions();
    if (positions.isEmpty()) {
      model.addAttribute(EMPTY, NO_POSITION);
    }
    model.addAttribute(POSITIONS, positions);
    return "delete-position";
  }

  @PostMapping(value = "/delete-position")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Trying to delete position") })
  @ApiOperation(value = "delete Position", notes = "This method tries to delete picked Position", produces = MIME_HTML)
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
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Position deleted") })
  @ApiOperation(value = "finish deleting Position", notes = "This method show Success page if Position was deleted", produces = MIME_HTML)
  public String finishDeliting() {
    return SUCCESS_PAGE;
  }

  @GetMapping("/display-employees")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Displays all possible Positions") })
  @ApiOperation(value = "display Employees of Position", notes = "This Method asks to pick existing Position from dropdown", produces = MIME_HTML)
  public String showEmployees(Model model) {
    List<String> positions = positionOptions.findAllPositions();
    if (positions.isEmpty()) {
      model.addAttribute(EMPTY, NO_POSITION);
    }
    model.addAttribute(POSITIONS, positions);
    return "choose-position-to-display-employees";
  }

  @PostMapping(value = "/display-employees")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Tries to find all Employees for this Position") })
  @ApiOperation(value = "display Employees of Position", notes = "This Method tries to retrieve all Employees of choosen position", produces = MIME_HTML)
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
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Displays all employees for picked Possition") })
  @ApiOperation(value = "display Employes of choosen Position", notes = "This Method Displays all Employees on choosen Position", produces = MIME_HTML)
  public String displayEmployees() {
    return "find-by-position-employee-list";
  }

}
