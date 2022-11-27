package com.kalachev.intensive.initialization.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.kalachev.intensive.initialization.EmployeeInitializer;

@Component
public class EmployeeInitializerImpl implements EmployeeInitializer {

  public List<String> generateEmployeeNames() {
    List<String> employees = new ArrayList<>();
    employees.add("Dmitriy Averianov");
    employees.add("Konstantin Artamonov");
    employees.add("Gleb Buryak");
    employees.add("Polina Verstakova");
    employees.add("Aleksandr Vorotinskiy");
    employees.add("Andrey Diomidov");
    employees.add("Aleksandr Dmitriev");
    employees.add("Andrey Zaharenkov");
    employees.add("Nikolay Zebnickiy");
    employees.add("Evgeniy Zotov");
    employees.add("Artem Kalachev");
    employees.add("Vladislav Lyapunov");
    return employees;
  }

}
