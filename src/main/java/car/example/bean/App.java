package car.example.bean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        // First we will load the application context from the xml configuration file
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationBeanContext.xml");

        MyBean myBean = (MyBean) context.getBean("myBean");
        System.out.println(myBean);
    }
}
