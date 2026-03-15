package com.ioc.coupling;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class IOCExample {
    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("applicationIocLooseCouplingExample.xml");

//        UserDataProvider userDataProvider = new UserDatabaseProvider();
//        UserManager userManagerwithDB = new UserManager(userDataProvider);
        UserManager userDatabaseProvider = (UserManager)context.getBean("userManagerWithUserDatabaseProvider");
        System.out.println(userDatabaseProvider.getUserInfo());

        UserManager newDatabaseProvider = (UserManager)context.getBean("userManagerWithNewDatabaseProvider");
        System.out.println(newDatabaseProvider.getUserInfo());


        UserManager webServiceDatabaseProvider = (UserManager)context.getBean("userManagerWithWebServiceDataProvider");
        System.out.println(webServiceDatabaseProvider.getUserInfo());


    }
}
