package com.fidelity.serial;

import java.io.Serializable;

public class NotSerializableSubclass extends PossiblySerializableSuperclass implements Serializable {

	private static final long serialVersionUID = 8652849123260527196L;

	private int field1;

	/*
	 * This constructor is not invoked when deserializing
	 * 
	 * Instead the system tries to invoked the no-args constructor of the superclass, but this does not set up state,
	 * so the superclass state is neither serialized nor deserialized
	 */
	public NotSerializableSubclass(int anInt, String aString, int field1) {
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
		NotSerializableSubclass other = (NotSerializableSubclass) obj;
		if (field1 != other.field1)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "NotSerializableSubclass [field1=" + field1 + ", super=" + super.toString() + "]";
	}

}
