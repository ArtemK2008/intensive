package com.kalachev.intensive.initialization.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.kalachev.intensive.initialization.ProjectInitializer;

@Component
public class ProjectInitializerImpl implements ProjectInitializer {

  @Override
  public List<String> generateProjects() {
    return Arrays.asList("small", "big", "huge", "enterprise", "pet");
  }

}
