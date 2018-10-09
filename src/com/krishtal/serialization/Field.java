package com.krishtal.serialization;

import static com.krishtal.serialization.SerializationWriter.*;

public class Field {
	
	public static final byte CONTAINER_TYPE = ContainerType.FIELD;
	public byte type;
	public byte[] data;
	public byte[] name;
	public short nameLength;
	
	public void setName(String name) {
		assert(name.length() < Short.MAX_VALUE);
		
		nameLength = (short) name.length();
		this.name = name.getBytes();
	}
	
	public int getBytes(byte[] dest, int pointer) {
		dest[pointer++] = CONTAINER_TYPE;
		pointer = writeBytes(dest, pointer, CONTAINER_TYPE);
		pointer = writeBytes(dest, pointer, nameLength);
		pointer = writeBytes(dest, pointer, name);
		pointer = writeBytes(dest, pointer, type);
		pointer = writeBytes(dest, pointer, data);
		return pointer;
	}
}
