package com.fidelity.greeter;

import org.springframework.stereotype.Component;

@Component
public class IndiaVisitor implements Visitor {

    private String name;
    private String greeting;

    public IndiaVisitor(){
        System.out.println("created India visitor");
        this.name = "Tourist";
        this.greeting = "Namaste";
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getGreeting() {
        return greeting;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "IndiaVisitor [name=" + name + ", greeting=" + greeting + "]";
    }

}