package com.fidelity.greeter;

public class WindyCityVisitor implements Visitor {

    private String name;
    private String greeting;

    public WindyCityVisitor() {
        this.greeting = "Love Da Bears";
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

}