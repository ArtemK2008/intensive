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

    /*
     * EmployeeDaoImpl test2 = (EmployeeDaoImpl) context
     * .getBean("employeeDaoImpl");
     */

    /*
     * Employee a = test2.findByName("Dmitriy Averianov");
     * System.out.println(a.getFirstName());
     */
    /*
     * a = test2.findByName("Konstantin Artamonov");
     * System.out.println(a.getFirstName()); a =
     * test2.findByName("Gleb Buryak"); System.out.println(a.getFirstName()); a
     * = test2.findByName("Polina Verstakova");
     * System.out.println(a.getFirstName());
     * test2.assignEmployeesToPositions(employeesWithPositions);
     * 
     * EmployeeInitializerImpl impl = new EmployeeInitializerImpl();
     * List<String> pos = new ArrayList(); pos.add("1"); pos.add("2");
     * pos.add("3"); pos.add("4"); pos.add("5"); List<String> name =
     * impl.generateEmployeeNames();
     * System.out.println(impl.assignEmployeesToPossitions(name, pos));
     */

  }

}
