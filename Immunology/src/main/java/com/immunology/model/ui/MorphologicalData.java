package com.immunology.model.ui;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
@Entity
@Table(name = "morphological_data")
public class MorphologicalData extends Form{

}
