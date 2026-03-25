### Coupling
- Coupling referes to how closely connected different components or system are
- **Tight Coupling**
    - Decribes a scenario where software compenents are **highly dependent** on each other.
    - Meaning chane in one component will necessitite the change in one or other component which will make the sytem more rigid and harder to modify
- **Loose Coupling**
    - Decribes a scenario where software compenents are **less dependent** on each other.
    - Meaning change in one component will have minimal or no impact onother component which will make the sytem more flexble and easier to modify or extend
    - Example : If there is an application that is interacting with the database and there is an interface which interacting with the database through so if the database changes then we will have to just adjust the implementation behind the interface and this would not effect the rest of the application. The business logic does not need to know about how the data is stored or retreved
    - Achieving Loose Coupling:
        - Interfaces and Abstraction
        - Dependency Injection
        - Event Driven Architecture
- ### Tight Coupling Example
- ```java

    package com.tight.coupling;

    // A- MySQL, Postgresql
    // B - Web Service, MongoDB

    public class UserDatabase {
        public String getUserDetails(){
            // Directly access Database here
            return "User Details From Database";
        }
    }

    package com.tight.coupling;

    public class UserManager {
        private UserDatabase userDatabase = new UserDatabase();
        public String getUserInfo(){
            return userDatabase.getUserDetails();
        }
    }

    package com.tight.coupling;

    public class TightCouplingExample {
        public static void main(String[] args) {
            UserManager userManager = new UserManager();
            System.out.println(userManager.getUserInfo());
        }
    }

  ```
- Upon running the `TightCouplingExample` class we will be able to get the output as `User Details From Database` which is hardcoded in the `UserDatabase` class.
- But what is happening over here is `UserManager` is tightly coupled with the `UserDatabase` as we have hardcoded the `private UserDatabase userDatabase = new UserDatabase();` in the `UserManager` class. 
- If tomorrow we are changing the database A to B that is we are no longer using a database but we are switching to a web service or a nosql such MongoDB, then in such case since there is tight coupling via `private UserDatabase userDatabase = new UserDatabase();` in the `UserManager` class, there will be required a lot of changes like the UserManager class will also get changed
- This is not the case of Loose Coupling.
- ### Tight Coupling Example
- To promote loose couple we are going to make use of an interface. Abstract classes can also be used but we will go with the iterface 
- ```java
    package com.loose.coupling;

    public interface UserDataProvider {
        String getUserDetails();
    }

    package com.loose.coupling;

    // A- MySQL, Postgresql
    // B - Web Service, MongoDB

    public class UserDatabaseProvider implements UserDataProvider{
        public String getUserDetails(){
            // Directly access Database here
            return "User Details From Database";
        }
    }

    package com.loose.coupling;

    public class UserManager {
        // Interface instance
        private UserDataProvider userDataProvider;

        public UserManager(UserDataProvider userDataProvider){
            this.userDataProvider = userDataProvider;
        }

        // When instantiating the UserManager we are going to provide the implementation
        // as the parameter to the constructor

        public String getUserInfo(){
            return userDataProvider.getUserDetails();
        }
    }

    package com.loose.coupling;

    public class LooseCouplingExample {
        public static void main(String[] args) {
            UserDataProvider userDataProvider = new UserDatabaseProvider();
            // The UserManager constructor expects a parameter and we need to pass in the
            // object of UserDatabaseProvider and we have the `userDataProvider` reference
            // variable of the interface so we will pass userDataProvider
            UserManager userManagerwithDB = new UserManager(userDataProvider);
            System.out.println(userManagerwithDB.getUserInfo());
        }
    }

  ```
- Upon running the `LooseCouplingExample` class we will be able to get the output as `User Details From Database` which is implementation of the interface.
- If tomorrow we are changing the database A to B that is we are no longer using a database but we are switching to a web service or a NoSQL such as MongoDB, then in such case we will just need to create the implementation of UserDataProvider that talk to the  particular web service or MongoDB.
- ```java
    package com.loose.coupling;

    public class WebServiceDataProvider implements UserDataProvider{
        @Override
        public String getUserDetails() {
            return "Fetching Data From Web Service";
        }
    }

    package com.loose.coupling;

    public class LooseCouplingExample {
        public static void main(String[] args) {
            UserDataProvider userDataProvider = new UserDatabaseProvider();
            UserManager userManagerwithDB = new UserManager(userDataProvider);
            System.out.println(userManagerwithDB.getUserInfo());

            UserDataProvider webServiceProvider = new WebServiceDataProvider();
            UserManager userManagerWitWS = new UserManager(webServiceProvider);
            System.out.println(userManagerWitWS.getUserInfo());
        }
    }

  ```
- SO it is the same UserManager classand we have just added a new provider. We have an interface that sets the contract : hey if you wnat to provide data source into your application then you need to implement the UserDataProvide interface. After implementation is done we can create a refernce in main and we can assign it an object of implementation. 
- Then working with UserManager, we are accepting the implementation of this interface `public UserManager(UserDataProvider userDataProvider){` So whoever implements this interface can make use of UserManager.
- Similalry if in future if we want to add any new database then simply we have to provide the implementation of the interface and then in main class we just have to provide the implementation to the UserManager class constructor.
- ```java
    package com.loose.coupling;

    public class NewDatabaseProvider implements UserDataProvider{
        @Override
        public String getUserDetails() {
            return "Data from the new database";
        }
    }

    package com.loose.coupling;

    public class LooseCouplingExample {
        public static void main(String[] args) {
            UserDataProvider userDataProvider = new UserDatabaseProvider();
            UserManager userManagerwithDB = new UserManager(userDataProvider);
            System.out.println(userManagerwithDB.getUserInfo());


            UserDataProvider webServiceProvider = new WebServiceDataProvider();
            UserManager userManagerWitWS = new UserManager(webServiceProvider);
            System.out.println(userManagerWitWS.getUserInfo());

            UserDataProvider userDataProviderNewDB = new NewDatabaseProvider();
            UserManager userManagerNewDB = new UserManager(userDataProviderNewDB);
            System.out.println(userManagerNewDB.getUserInfo());
        }
    }

  ```
- **Inversion of Control (IOC)**
- It is a design principle where control of the object creation and lifwcycle management is transferred from application code to an external container or framework.
- If UserManager need an object of the UserDataProvider but during runtime we are calling UserManager with different Object type like UserDatabaseProvider, WebServiceDataProvider, NewDatabaseProvider, etc. So during runtime the main class is managing the object creation and it is making sure that UserManager gets the appropriate object. But this is we managing the code but this will become IOC if the framework manages this itself

- **Dependency Injection (DI)**
- It is a design pattern commonly used in object-oriented programming, where dependencies of a class are provided externally rather than being created within the class itself

- **Beans**
- Objects that are managed by framework are called beans
- We are providing the objects to UserManager during the runtime by frst creating the object itslef then passing it in the parameter and to create the object we are aking use of the *new* keyword
- So if we want the UserDataProvider to be managed by spring we will have to make it as a bean

### Spring Container and Configuration
- It manages the creation and management of the objects. Basically entire lifecycle of the objects is manages my this spring container. So the DI is done by the spring container during the runtime
- Types:
    - Application Context
    - Bean Factory
- We as a developer are responsible to tell spring container which objects we want to manage with the help of a configuration file
- This configuration file will contain the bean definition
- In our project structure we have a file named pom.xml in which we need to add a new dependency.
- ```xml
    <dependencies>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-core</artifactId>
            <version>6.1.6</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>6.1.6</version>
        </dependency>
    </dependencies>
  ```
- Now we will create our first bean.
- ```java
    package car.example.bean;

    public class MyBean {
        private String message;

        public void setMessage(String message){
            this.message = message;
        }

        public void showMesage(){
            System.out.println("Message : " + message);
        }

        @Override
        public String toString() {
            return "MyBean{" +
                    "message='" + message + '\'' +
                    '}';
        }
    }
  ```
- Now we need Spring container to manage this class for us. For this we will head to browser and search for `xml schema based configuration spring` then we will copy some xml structure and paste it in the new file `applicationBeanContext.xml` inside the `resources`.
- ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="
            http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

        <!-- bean definitions here -->
        <bean id="myBean" class="car.example.bean.MyBean"></bean>

    </beans>
  ```
- Here we are telling that we are going to make use of the id 'myBean' instead of the fully qualified class name. We want this particular class to be managed by spring container. So if we say we need the object of this particular class then spring will provide us at runtime.
- ```java
    package car.example.bean;

    import org.springframework.context.ApplicationContext;
    import org.springframework.context.support.ClassPathXmlApplicationContext;

    public class App {
        public static void main(String[] args) {
            // First we will load the application context from the xml configuration file
            ApplicationContext context = new ClassPathXmlApplicationContext("applicationBeanContext.xml");

            MyBean myBean = (MyBean) context.getBean("myBean"); 
            System.out.println(myBean); // op: MyBean{message='null'}
        }
    }
  ```
- The output is null because the message variable was not initialized.
- In the first plce we made use of the ApplicationContext which is an interface that represents the IOC container in the spring that manages the beans and dependencies. We also made use of ClassPathXmlApplicationContext which is an implementation of ApplicationContext which loads the implementation from an xml file located in the class path and we have passed in the applicationBeanContext which contains the spring bean definition like where is it located, it's id, identifier, etc.
- Spring container with the help of this xml file knows that MyBean is the class that it need to provide when requested.
- Next we retreieved MyBean from the context with the help of getBean method and as the bean is managed by spring it gives you the object of the same.
- Now the one proble we are getting is that the output is coming as null `MyBean{message='null'}`. So we need to add a tag `property` in the xml config file inside the `bean` tag
- ```xml
    <bean id="myBean" class="car.example.bean.MyBean">
        <property name="message" value="I am first bean"></property>
    </bean>
  ```
- So the IOC will make use of the setter and will set the value inside the message variable whatever has been provided inside the property tag. Output: `MyBean{message='I am first bean'}`.
- If we don't have the setter then we will get - `Exception encountered during context initialization - cancelling refresh attempt: org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'myBean' defined in class path resource [applicationBeanContext.xml]: Invalid property 'message' of bean class [car.example.bean.MyBean]: Bean property 'message' is not writable or has an invalid setter method. Does the parameter type of the setter match the return type of the getter?`. That is why setter is necessary.

### Lifecycle of Bean
- Bean Definition included configuration metadata that the container needs to know to create and manage the bean.
- Bean Definition can be provided in various ways, including XML configuration files, annotations, and java based configurations.
- Beans  are configured using XML files, where each bean is defined within `<bean>` tag with attributes soecifying class, properties and  dependencies.
- Beans can be configured using annotations like @Component, @Service, @Repository, etc., which are scanned by spring and managed as beans.
- Lifecycle:
    - Instantiation : here the bean gets created using the bean definition and the constructor injection
    - Population of properties : Which mean the properties will be set on the instance either through the setter, constructor or field injection
    - Initialization : Initialization method is invoked after the properties are set
    - Ready for use : 
    - Destruction : once the usage is done it is destructed or removed from the container during the application shutown

### Dependency Injection (DI)
- It is a design pattern used in software development to achieve loose coupling within classes by removing the direct dependency  instantiation from the dependent class itself.
- **Constructor Injection**
- Dependencies are provided to the dependent class through its constructor
- Dependencies are passed as arguments to the constructor when the dependent class is instantiated.
- Constructor injection ensures that dependencies are available when the objet is created.
- Example : We will create `Specification` class for the class `Car`.
- ```java
    package car.example.constructor.injection;

    public class Specification {
        private String make;
        private String model;

        public String getMake() {
            return make;
        }

        public void setMake(String make) {
            this.make = make;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        @Override
        public String toString() {
            return "Specification{" +
                    "make='" + make + '\'' +
                    ", model='" + model + '\'' +
                    '}';
        }
    }

    package car.example.constructor.injection;

    public class Car {
        private Specification specification;

        public Car(Specification specification) {
            this.specification = specification;
        }

        public void display(){
            System.out.println("Car Details :" + specification.toString());
        }
    }
  ```
- ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="
            http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

        <!-- bean definitions here -->
        <bean id="carSpecification" class="car.example.constructor.injection.Specification">
            <property name="make" value="Toyota"></property>
            <property name="model" value="Corolla"></property>
        </bean>

        <bean id="myCar" class="car.example.constructor.injection.Car">
            <constructor-arg ref="carSpecification"></constructor-arg>
        </bean>
    </beans>
  ```
- ```java
    package car.example.constructor.injection;

    import org.springframework.context.ApplicationContext;
    import org.springframework.context.support.ClassPathXmlApplicationContext;

    public class App {
        public static void main(String[] args) {
            ApplicationContext context = new ClassPathXmlApplicationContext("applicationConstructorInjection.xml");
            Car myCar = (Car) context.getBean("myCar");
            myCar.display(); //  Details :Specification{make='Toyota', model='Corolla'}
        }
    }
  ```
- **Setter Injection**
- ```java
    package car.example.setter.injection;

    public class Specification {
        private String make;
        private String model;

        public String getMake() {
            return make;
        }

        public void setMake(String make) {
            this.make = make;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        @Override
        public String toString() {
            return "Specification{" +
                    "make='" + make + '\'' +
                    ", model='" + model + '\'' +
                    '}';
        }
    }

    package car.example.setter.injection;

    public class Car {
        private Specification specification;

        public void setSpecification(Specification specification) {
            this.specification = specification;
        }

        public void display(){
            System.out.println("Car Details :" + specification.toString());
        }
    }
  ```
- ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="
            http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

        <!-- bean definitions here -->
        <bean id="carSpecification" class="car.example.setter.injection.Specification">
            <property name="make" value="Suzuki"/>
            <property name="model" value="Vitara"/>
        </bean>

        <bean id="myCar" class="car.example.setter.injection.Car">
            <property name="specification" ref="carSpecification"/>
        </bean>
    </beans>
  ```
- ```java
    package car.example.setter.injection;

    import org.springframework.context.ApplicationContext;
    import org.springframework.context.support.ClassPathXmlApplicationContext;

    public class App {
        public static void main(String[] args) {
            ApplicationContext context = new ClassPathXmlApplicationContext("applicationSetterInjection.xml");
            Car myCar = (Car) context.getBean("myCar");
            myCar.display();// Car Details :Specification{make='Suzuki', model='Vitara'}
        }
    }
  ```
- The only visible change is in the xml files where we can see one uses property tag while the other uses constructor-org tag.
- Earlier in the loose coupling example we were manually passing object to the UserManager Constructor but now we know how the spring container provides us the required bean at the runtime. So we will implement it.
- So we had 3 implementation of the UserDataProvider interface. And we will create the beans or all those 3
- ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="
            http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

        <!-- bean definitions here -->
        <bean id="userDatabaseProvider" class="com.ioc.coupling.UserDatabaseProvider"/>
        <bean id="newDatabaseProvider" class="com.ioc.coupling.NewDatabaseProvider"/>
        <bean id="webServiceDataProvider" class="com.ioc.coupling.WebServiceDataProvider"/>

        <bean name="userManagerWithUserDatabaseProvider" class="com.ioc.coupling.UserManager">
            <constructor-arg ref="userDatabaseProvider"/>
        </bean>

        <bean name="userManagerWithNewDatabaseProvider" class="com.ioc.coupling.UserManager">
            <constructor-arg ref="newDatabaseProvider"/>
        </bean>

        <bean name="userManagerWithWebServiceDataProvider" class="com.ioc.coupling.UserManager">
            <constructor-arg ref="webServiceDataProvider"/>
        </bean>
    </beans>
  ```
- But not only this we are going to need any implementation as we have passed these to the UserManager constructors so.
- ```java
    package com.ioc.coupling;

    public interface UserDataProvider {
        String getUserDetails();
    }

    package com.ioc.coupling;

    public class UserDatabaseProvider implements UserDataProvider {
        public String getUserDetails(){
            return "ioc User Details From Database";
        }
    }

    package com.ioc.coupling;

    public class NewDatabaseProvider implements UserDataProvider {
        @Override
        public String getUserDetails() {
            return "ioc Data from the new database";
        }
    }

    package com.ioc.coupling;

    public class WebServiceDataProvider implements UserDataProvider {
        @Override
        public String getUserDetails() {
            return "ioc Fetching Data From Web Service";
        }
    }

    package com.ioc.coupling;

    public class UserManager {
        private UserDataProvider userDataProvider;

        public UserManager(UserDataProvider userDataProvider){
            this.userDataProvider = userDataProvider;
        }

        // When instantiating the USerManager we are going to provide the implementation
        // as the parameter to the constructor

        public String getUserInfo(){
            return userDataProvider.getUserDetails();
        }
    }

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
  ```

### Autowire By Name
- ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="
            http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

        <!-- bean definitions here -->
        <bean id="specification" class="com.example.autowire.name.Specification">
            <property name="make" value="Honda"></property>
            <property name="model" value="City"></property>
        </bean>

        <bean id="myCar" class="com.example.autowire.name.Car" autowire="byName">
            <!-- Car bean is having a dependency on Specification bean, but we are not explicitly wiring it here -->
            <!-- So whatever dependency Car class needs will be provided By name -->
        </bean>
    </beans>
  ```
- ```java
    package com.example.autowire.name;

    public class Car {
        private Specification specification;

        public void setSpecification(Specification specification){
            this.specification = specification;
        }

        public void display(){
            System.out.println("Car Details :" + specification.toString());
        }
    }

    package com.example.autowire.name;

    public class Specification {
        private String make;
        private String model;

        public String getMake() {
            return make;
        }

        public void setMake(String make) {
            System.out.println("Setter is called");
            this.make = make;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        @Override
        public String toString() {
            return "Specification{" +
                    "make='" + make + '\'' +
                    ", model='" + model + '\'' +
                    '}';
        }
    }

    package com.example.autowire.name;

    import org.springframework.context.ApplicationContext;
    import org.springframework.context.support.ClassPathXmlApplicationContext;

    public class App {
        public static void main(String[] args) {
            ApplicationContext context = new ClassPathXmlApplicationContext("autowireByName.xml");
            Car myCar = (Car) context.getBean("myCar");
            myCar.display(); // Car Details :Specification{make='Honda', model='City'}
        }
    }
  ```
- So autowiring by name works with the help of setter. If we give diffrent name in the bean id and different in the setter then it will not work.
- if we add another bean in the xml like:
- ```xml
    <bean id="specification1" class="com.example.autowire.name.Specification">
        <property name="make" value="Hyundai"></property>
        <property name="model" value="Verna"></property>
    </bean>
  ```
- Then for this to work we will have to write the ne setter which actually set the values for this particular beanId.

- **Autowiring By Type**
- ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="
            http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

        <!-- bean definitions here -->
        <bean id="specification" class="com.example.autowire.type.Specification">
            <property name="make" value="Honda"></property>
            <property name="model" value="City"></property>
        </bean>

        <bean id="specification1" class="com.example.autowire.type.Specification">
            <property name="make" value="Honda"></property>
            <property name="model" value="City"></property>
        </bean>

        <bean id="myCar" class="com.example.autowire.type.Car" autowire="byType">
        </bean>
    </beans>
  ```
- This time we have added two beas with different id's but with same "type" that is "Specification" and it will not look for name as it did in the case of autowireByName
- ```java
    package com.example.autowire.type;

    import org.springframework.context.ApplicationContext;
    import org.springframework.context.support.ClassPathXmlApplicationContext;

    public class App {
        public static void main(String[] args) {
            ApplicationContext context = new ClassPathXmlApplicationContext("autowireByType.xml");
            Car myCar = (Car) context.getBean("myCar");
            myCar.display();
        }
    }
  ```
- Now if we run this it will get cofused as boththe beans have same "type" that is "Specification" in the xml configuration. Exception : No qualifying bean of type 'com.example.autowire.type.Specification' available: expected single matching bean but found 2: specification,specification1. So we will comment out one bean then it will run and give the result.
- **Autowiring By Constructor**
- ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="
            http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

        <!-- bean definitions here -->
        <bean id="specification" class="com.example.autowire.constructor.Specification">
            <property name="make" value="Suzuku"></property>
            <property name="model" value="Estilo"></property>
        </bean>

    <!--    <bean id="specification1" class="com.example.autowire.constructor.Specification">-->
    <!--        <property name="make" value="Honda"></property>-->
    <!--        <property name="model" value="City"></property>-->
    <!--    </bean>-->

        <bean id="myCar" class="com.example.autowire.constructor.Car" autowire="constructor">
        </bean>

    </beans>
  ```
- We need to add a constructor in the Car class for the Specification for the autowire by constructor to work. Also if we don't comment the other bean that has same "type" that is "Specification" then we will get the same exception that we got in the case of autowire by type.

### Annotations
- **@Component**
- Component refers to a java class that is managed by the Spring IoC container.
- Component is like a bean only but it specifically designed to be autodetected and configured from classpath scanning.
- **Component Scanning**
- Component scanning is a feature helps to automatically detect and register beans from predefined package paths
- You will create components but spring nees to know where it needs to check for component and register itself.
- Hands on Example:
- ```java
    package com.example.componentscan;

    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.stereotype.Component;

    @Component
    public class Employee {
        private int employeeId;
        @Value("Saeel")
        private String firstName;
        @Value("Dhatrak")
        private String lastName;
        @Value("#{120000*12}")
        private double salary;

        public int getEmployeeId() {
            return employeeId;
        }

        public void setEmployeeId(int employeeId) {
            this.employeeId = employeeId;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public double getSalary() {
            return salary;
        }

        public void setSalary(double salary) {
            this.salary = salary;
        }

        @Override
        public String toString() {
            return "Employee{" +
                    "employeeId=" + employeeId +
                    ", firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", salary=" + salary +
                    '}';
        }
    }
  ```
- ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns:context="http://www.springframework.org/schema/context"
        xsi:schemaLocation="http://www.springframework.org/schema/beans
            https://www.springframework.org/schema/beans/spring-beans.xsd
            http://www.springframework.org/schema/context
            https://www.springframework.org/schema/context/spring-context.xsd">

        <context:component-scan base-package="com.example.componentscan"/>
    </beans>
  ```
- ```java    
    package com.example.componentscan;

    import com.example.autowire.constructor.Car;
    import org.springframework.context.ApplicationContext;
    import org.springframework.context.support.ClassPathXmlApplicationContext;

    public class App {
        public static void main(String[] args) {
            ApplicationContext context = new ClassPathXmlApplicationContext("componentScanDemo.xml");
            Employee employee = (Employee) context.getBean("employee");
            System.out.println(employee.toString());
        }
    }
  ```
- output : Employee{employeeId=0, firstName='Saeel', lastName='Dhatrak', salary=1440000.0}
- So upon running this even we have not created a bean id in the xml config still the @component scan will be able to figire out the employee bean and we will be able to get the output.
- But still here also we are providing the location t spring in the xml configuration manually but we wll get rod of that also in next step.
- Here we will not make use of any of the xml file instead we will make use of two annotations that is @Cofiguration and @ComponentScan
- ```java
    package com.example.componentscan.annotation;

    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.stereotype.Component;

    @Component
    public class Employee {
        private int employeeId;
        @Value("Saeel")
        private String firstName;
        @Value("Dhatrak")
        private String lastName;
        @Value("#{120000*12}")
        private double salary;

        public int getEmployeeId() {
            return employeeId;
        }

        public void setEmployeeId(int employeeId) {
            this.employeeId = employeeId;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public double getSalary() {
            return salary;
        }

        public void setSalary(double salary) {
            this.salary = salary;
        }

        @Override
        public String toString() {
            return "Employee{" +
                    "employeeId=" + employeeId +
                    ", firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", salary=" + salary +
                    '}';
        }
    }
    ///-----------------------------------------------------------
    package com.example.componentscan.annotation;


    import org.springframework.context.annotation.ComponentScan;
    import org.springframework.context.annotation.Configuration;

    @Configuration
    @ComponentScan(basePackages = "com.example.componentscan.annotation")
    public class AppConfig {
    }

    ///-----------------------------------------------------------
    package com.example.componentscan.annotation;

    import org.springframework.context.ApplicationContext;
    import org.springframework.context.annotation.AnnotationConfigApplicationContext;
    import org.springframework.context.support.ClassPathXmlApplicationContext;

    public class App {
        public static void main(String[] args) {
            ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
            Employee employee = (Employee) context.getBean("employee");
            System.out.println(employee.toString());
        }
    }
  ```
- output : Employee{employeeId=0, firstName='Saeel', lastName='Dhatrak', salary=1440000.0}
- So the base package we need to privode and it will scan it using the annotation power.

- **AUTOWIRED ANNOTATION**
- It allows automatic dependency Injection in our spring managed beans
- ```java
    package com.example.autowired.annotation;

    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.stereotype.Component;

    @Component
    public class Employee {
        private int employeeId;
        @Value("Saeel")
        private String firstName;
        @Value("Dhatrak")
        private String lastName;
        @Value("#{120000*12}")
        private double salary;

        public int getEmployeeId() {
            return employeeId;
        }

        public void setEmployeeId(int employeeId) {
            this.employeeId = employeeId;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public double getSalary() {
            return salary;
        }

        public void setSalary(double salary) {
            this.salary = salary;
        }

        @Override
        public String toString() {
            return "Employee{" +
                    "employeeId=" + employeeId +
                    ", firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", salary=" + salary +
                    '}';
        }
    }
        // -------------------------------------------------------------------

    package com.example.autowired.annotation;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Component;

    @Component
    public class Manager {
        private Employee employee;

        @Autowired
        public Manager(Employee employee){
            this.employee = employee;
        }

        @Override
        public String toString() {
            return "Manager{" +
                    "employee=" + employee +
                    '}';
        }
    }
        // -------------------------------------------------------------------
    package com.example.autowired.annotation;


    import org.springframework.context.annotation.ComponentScan;
    import org.springframework.context.annotation.Configuration;

    @Configuration
    @ComponentScan(basePackages = "com.example.autowired.annotation")
    public class AppConfig {
    }
        // -------------------------------------------------------------------
    package com.example.autowired.annotation;

    import org.springframework.context.ApplicationContext;
    import org.springframework.context.annotation.AnnotationConfigApplicationContext;

    public class App {
        public static void main(String[] args) {
            ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
            Employee employee = (Employee) context.getBean("employee");
            System.out.println(employee.toString());

            Manager manager = context.getBean("manager", Manager.class);
            System.out.println(manager.toString());
        }
    }
  ```
- op : Employee{employeeId=0, firstName='Saeel', lastName='Dhatrak', salary=1440000.0}
- op : Manager{employee=Employee{employeeId=0, firstName='Saeel', lastName='Dhatrak', salary=1440000.0}}
- Even if we remove the constructor and put the @Autowired annotation on the field then this will work as well and it will become field level injection rather than the constructor based injection and aso the constructor based injection is preferred more over the field base.
- **@Qualifier** 
- We can have multiple beans created of same type like for example if multiple Employee beans registerd in container then if we make use of @Autowired then Spring Container will get confused and it might throw error that there are two beans of type Employee so in that particular case we can make use of @Qualifier
