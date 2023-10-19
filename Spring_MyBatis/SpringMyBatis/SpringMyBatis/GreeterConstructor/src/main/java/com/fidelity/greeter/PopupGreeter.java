package com.fidelity.greeter;

import javax.swing.JOptionPane;

public class PopupGreeter implements Greeter {
	private Visitor visitor;

	public PopupGreeter(Visitor visitor) {
		this.visitor = visitor;
	}

	@Override
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
