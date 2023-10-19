package com.fidelity.greeter;

import org.springframework.stereotype.Component;

@Component("bostonVis")
public class BostonVisitor implements Visitor {

    private String name;
    private String greeting;
    public BostonVisitor() {
        this.name = "Abby Johnson";
        this.greeting = "Happy to Meet You";
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getGreeting() {
        return greeting;
    }
}