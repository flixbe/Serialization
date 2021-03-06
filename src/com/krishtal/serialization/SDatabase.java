package com.krishtal.serialization;

import static com.krishtal.serialization.SerializationWriter.writeBytes;

import java.util.ArrayList;
import java.util.List;

public class SDatabase {

	public static final byte[] HEADER = "SDB".getBytes();
	public static final byte CONTAINER_TYPE = ContainerType.DATABASE;	
	public short nameLength;
	public byte[] name;
	private int size = HEADER.length + 1 + 2 + 4 + 2;
	private short objectCount;
	private List<SObject> objects = new ArrayList<SObject>();
	
	public SDatabase(String name) {
		setName(name);
	}
	
	public void setName(String name) {
		assert(name.length() < Short.MAX_VALUE);
		
		if (this.name != null)
			size -= this.name.length;
		
		nameLength = (short)name.length();
		this.name = name.getBytes();
		size += nameLength;
	}
	
	public int getSize() {
		return size;
	}
	
	public void addObject(SObject object) {
		objects.add(object);
		size += object.getSize();
		objectCount = (short)objects.size();
	}
	
	public int getBytes(byte[] dest, int pointer) {
		pointer = writeBytes(dest, pointer, HEADER);
		pointer = writeBytes(dest, pointer, CONTAINER_TYPE);
		pointer = writeBytes(dest, pointer, nameLength);
		pointer = writeBytes(dest, pointer, size);
		pointer = writeBytes(dest, pointer, name);
		
		pointer = writeBytes(dest, pointer, objectCount);
		for (SObject object : objects)
			pointer = object.getBytes(dest, pointer);
		
		return pointer;
	}
	
}
