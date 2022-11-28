package com.kalachev.intensive.initialization.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.kalachev.intensive.initialization.CustomerInitializer;

@Component
public class CustomerInitializerImpl implements CustomerInitializer {

  @Override
  public List<String> generateCustomers() {
    return Arrays.asList("VK", "OK", "FB", "WA", "TT");
  }
}
