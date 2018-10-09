package com.krishtal.serialization;

import static com.krishtal.serialization.SerializationWriter.*;

public class IntField extends Field {

	public IntField(String name, int value) {
		setName(name);
		type = Type.INT;
		data = new byte[Type.getSize(Type.INT)];
		writeBytes(data, 0, value);
	}
	
}
