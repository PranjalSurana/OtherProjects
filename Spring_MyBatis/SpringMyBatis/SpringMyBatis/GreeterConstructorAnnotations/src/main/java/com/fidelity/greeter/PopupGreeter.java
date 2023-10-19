package com.fidelity.greeter;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("greeter")
public class PopupGreeter implements Greeter {
	private Visitor visitor;
	
	@Autowired
	public PopupGreeter(@Qualifier("amarilloVis") Visitor visitor) {
		super();
		this.visitor = visitor;
	}

	public Visitor getVisitor() {
		return visitor;
	}

	public void greet() {
		String greeting = visitor.getGreeting();
		String name = visitor.getName();
		JOptionPane.showMessageDialog(null, greeting + ", " + name);
	}

	@Override
	public String toString() {
		return "PopupGreeter [visitor=" + visitor + "]";
	}

}
