package com.fidelity.serial;

import java.io.Serializable;

public class SerialExample implements Serializable {

	private static final long serialVersionUID = 3007667791011724669L;

	private int anInt;
	private String aString;
	
	public SerialExample(int anInt, String aString) {
		this.anInt = anInt;
		this.aString = aString;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aString == null) ? 0 : aString.hashCode());
		result = prime * result + anInt;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SerialExample other = (SerialExample) obj;
		if (aString == null) {
			if (other.aString != null)
				return false;
		} else if (!aString.equals(other.aString))
			return false;
		if (anInt != other.anInt)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SerialExample [anInt=" + anInt + ", aString=" + aString + "]";
	}

}
