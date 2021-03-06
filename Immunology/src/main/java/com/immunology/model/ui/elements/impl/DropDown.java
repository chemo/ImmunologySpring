package com.immunology.model.ui.elements.impl;

import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.immunology.model.ui.elements.Computable;
import com.immunology.model.ui.elements.Element;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")

@Entity
@Table(name = "dropdowns")
public class DropDown extends Element implements Computable {

	@ElementCollection(fetch = FetchType.EAGER)
	private Map<String, Double> values;
	
	private String text;

	private String selected;
	
	@Transient
	private Double multiplier;
	
	public Map<String, Double> getValues() {
		return values;
	}

	public void setValues(Map<String, Double> values) {
		this.values = values;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "Dropdown [values=" + values + "]";
	}

	public Double getMultiplier() {
		return multiplier;
	}

	public void setMultiplier(Double multiplier) {
		this.multiplier = multiplier;
	}

	@JsonIgnore
	public Double getValue() {
		return values.get(selected);
	}

	public void setValue(Double value) {
		if(values.containsValue(value)) {
			for(Entry<String, Double> entry: values.entrySet()) {
				if(entry.getValue().equals(value)) {
					selected = entry.getKey();
					break;
				}
			}
		}
	}

}