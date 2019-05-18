package com.rummy.mapper;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

@SuppressWarnings("serial")
public class ObjectMapper implements Serializable {

	@JsonProperty("label")
	private String label;

	@JsonProperty("value")
	private Integer value;

	public ObjectMapper(String label, Integer value) {
		super();
		this.label = label;
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

}
