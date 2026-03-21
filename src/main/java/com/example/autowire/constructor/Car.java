package com.example.autowire.constructor;

public class Car {
    private Specification specification;

    public Car(Specification specification) {
        this.specification = specification;
    }

    public void setSpecification(Specification specification){
        this.specification = specification;
    }

    public void display(){
        System.out.println("Car Details :" + specification.toString());
    }
}
