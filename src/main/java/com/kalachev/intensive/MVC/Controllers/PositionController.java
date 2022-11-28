package com.kalachev.intensive.MVC.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kalachev.intensive.service.PositionOptions;

@Controller
public class PositionController {
  @Autowired
  PositionOptions positionOptions;

  static final String ERROR_PAGE = "error-page";
  static final String SUCCESS_PAGE = "success-page";
  static final String RESULT = "result";
  static final String UNEXPECTED_ERROR = "Unexpected Error";

  @GetMapping(value = "/find-all-positions")
  public String findPositions(Model model) {
    List<String> positions = positionOptions.findAllPositions();
    if (positions.isEmpty()) {
      model.addAttribute("empty", "no positions found");
    }
    model.addAttribute("positions", positions);
    return "find-all-positions";
  }

  @GetMapping(value = "/create-new-position")
  public String insertPosition() {
    return "create-new-position";
  }

  @PostMapping("/create-new-position")
  public String handleAddPositon(@RequestParam("positionTitle") String title,
      Model model) {

    if (!positionOptions.addPosition(title)) {
      String result = UNEXPECTED_ERROR;
      model.addAttribute(RESULT, result);
      return ERROR_PAGE;
    }
    return "redirect:/proceed-create-position";
  }

  @GetMapping("/proceed-create-position")
  public String finishInserting() {
    return SUCCESS_PAGE;
  }

  @GetMapping(value = "/delete-position")
  public String deletePosition(Model model) {
    List<String> positions = positionOptions.findAllPositions();
    if (positions.isEmpty()) {
      model.addAttribute("empty", "no positions found");
    }
    model.addAttribute("positions", positions);
    return "delete-position";
  }

  @PostMapping(value = "/delete-position")
  public String handleDeletePosition(
      @RequestParam("pickedPosition") String title, Model model,
      RedirectAttributes redirectAttributes) {
    if (!positionOptions.deletePosition(title)) {
      String result = UNEXPECTED_ERROR;
      model.addAttribute(RESULT, result);
      return ERROR_PAGE;
    }
    return "redirect:/proceed-delete-position";
  }

  @GetMapping("/proceed-delete-position")
  public String finishDeliting() {
    return SUCCESS_PAGE;
  }

  @GetMapping("/display-employees")
  public String showEmployees(Model model) {
    List<String> positions = positionOptions.findAllPositions();
    if (positions.isEmpty()) {
      model.addAttribute("empty", "no positions found");
    }
    model.addAttribute("positions", positions);
    return "choose-position-to-display-employees";
  }

  @PostMapping(value = "/display-employees")
  public String handleDisplayingEmployees(
      @RequestParam("pickedPosition") String title, Model model,
      RedirectAttributes redirectAttributes) {

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
  public String displayEmployees() {
    return "find-by-position-employee-list";
  }

}
