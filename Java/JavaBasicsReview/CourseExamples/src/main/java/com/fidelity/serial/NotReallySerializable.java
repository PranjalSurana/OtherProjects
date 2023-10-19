package com.fidelity.serial;

import java.io.Serializable;

public class NotReallySerializable extends NotSerializable implements Serializable {

	private static final long serialVersionUID = 3009539078160867055L;

	private int field1;

	/*
	 * This constructor is not invoked when deserializing
	 * 
	 * Instead the system tries to invoked the no-args constructor of the superclass, which does not exist
	 */
	public NotReallySerializable(int anInt, String aString, int field1) {
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
		NotReallySerializable other = (NotReallySerializable) obj;
		if (field1 != other.field1)
			return false;
		return true;
	}


}
