package com.kalachev.intensive.hybernateTest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.kalachev.intensive.configuration.AppConfig;
import com.kalachev.intensive.initialization.hybernate.InitializerHibernate;

public class Test {

  public static void main(String[] args) {

    ApplicationContext context = new AnnotationConfigApplicationContext(
        AppConfig.class);
    InitializerHibernate test = (InitializerHibernate) context
        .getBean("initializerHibernate");
    test.initializeTables();
  }

}
