package com.krishtal.serialization;

import static com.krishtal.serialization.SerializationWriter.writeBytes;

import java.util.ArrayList;
import java.util.List;

public class SObject {

	public static final byte CONTAINER_TYPE = ContainerType.OBJECT;	
	public short nameLength;
	public byte[] name;

	private int size = 1 + 2 + 2 + 2;
	
	private short fieldCount;
	private short arrayCount;
	
	private List<SField> fields = new ArrayList<SField>();
	private List<SArray> arrays = new ArrayList<SArray>();	
	
	public SObject(String name) {
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

	public void addField(SField field) {
		fields.add(field);
		size += field.getSize();
		
		fieldCount = (short)fields.size();
	}
	
	public void addArray(SArray array) {
		arrays.add(array);
		size += array.getSize();
		
		arrayCount = (short)arrays.size();
	}
	
	public int getSize() {
		return size;
	}
	
	public int getBytes(byte[] dest, int pointer) {		
		pointer = writeBytes(dest, pointer, CONTAINER_TYPE);
		pointer = writeBytes(dest, pointer, nameLength);
		pointer = writeBytes(dest, pointer, name);
		
		pointer = writeBytes(dest, pointer, fieldCount);
		for (SField field : fields)
			pointer = field.getBytes(dest, pointer);
		
		pointer = writeBytes(dest, pointer, arrayCount);
		for (SArray array : arrays)
			pointer = array.getBytes(dest, pointer);
		
		return pointer;
	}
	
}