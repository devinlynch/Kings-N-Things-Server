package com.kings.model;

import java.util.Map;

public abstract class AbstractSerializedObject {
	/**
	 * This object in its serialized format to be sent through JSON
	 */
	public abstract Map<String, Object> toSerializedFormat();
	
}
