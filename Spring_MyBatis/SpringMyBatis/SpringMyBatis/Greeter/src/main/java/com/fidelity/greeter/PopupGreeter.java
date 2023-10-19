package com.fidelity.greeter;

import javax.swing.JOptionPane;

public class PopupGreeter implements Greeter {
	private Visitor visitor;

	@Override
	public void greet() {
		String greeting = visitor.getGreeting();
		String name = visitor.getName();
		JOptionPane.showMessageDialog(null, greeting + ", " + name);
	}

	public void setVisitor(Visitor v) {
		this.visitor = v;		
	}

	@Override
	public String toString() {
		return "PopupGreeter [visitor=" + visitor + "]";
	}
}
