package com.kalachev.intensive.MVC.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.kalachev.intensive.service.PositionOptions;

@Controller
public class PositionController {
  @Autowired
  PositionOptions positionOptions;

  @GetMapping(value = "/find-all-positions")
  public String findPositions(Model model) {
    List<String> positions = positionOptions.findAllPositions();
    if (positions.isEmpty()) {
      model.addAttribute("empty", "no positions found");
    }
    model.addAttribute("positions", positions);
    return "find-all-positions";
  }
}
