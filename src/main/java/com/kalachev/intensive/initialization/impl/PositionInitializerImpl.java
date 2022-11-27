package com.kalachev.intensive.initialization.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.kalachev.intensive.initialization.PositionInitializer;

@Component
public class PositionInitializerImpl implements PositionInitializer {

  @Override
  public List<String> generatePositions() {
    List<String> positions = Arrays.asList("test", "backend", "frontend",
        "devOps", "hr");
    return positions;
  }
}
