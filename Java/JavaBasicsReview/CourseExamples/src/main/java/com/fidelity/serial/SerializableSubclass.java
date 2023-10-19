package com.fidelity.serial;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SerializableSubclass extends PossiblySerializableSuperclass implements Serializable {

	private static final long serialVersionUID = -7286472287474178319L;

	private int field1;

	//adding helper method for deserialization to initialize superclass state
	private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException{
		ois.defaultReadObject();
		
		//notice the order of read and write should be same
		setAnInt(ois.readInt());
		setaString((String) ois.readObject());	
	}
	
	//adding helper method for serialization to write superclass state
	private void writeObject(ObjectOutputStream oos) throws IOException{
		oos.defaultWriteObject();
		
		oos.writeInt(getAnInt());
		oos.writeObject(getaString());
	}


	/*
	 * This constructor is not invoked when deserializing
	 * 
	 * Instead the system tries to invoked the no-args constructor of the superclass, but this does not set up state
	 */
	public SerializableSubclass(int anInt, String aString, int field1) {
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
		SerializableSubclass other = (SerializableSubclass) obj;
		if (field1 != other.field1)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SerializableSubclass [field1=" + field1 + ", super=" + super.toString() + "]";
	}

}
