package com.fidelity.serial;

public class SimpleSerializableSubclass extends SerialExample {

	private static final long serialVersionUID = 5245176591372668329L;
	
	private int field1;

	/*
	 * This constructor is not invoked when deserializing
	 */
	public SimpleSerializableSubclass(int anInt, String aString, int field1) {
		super(anInt, aString);
		this.field1 = field1;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + field1;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimpleSerializableSubclass other = (SimpleSerializableSubclass) obj;
		if (field1 != other.field1)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SimpleSerializableSubclass [field1=" + field1 + ", super=" + super.toString() + "]";
	}

}
