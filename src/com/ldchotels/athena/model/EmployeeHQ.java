package com.ldchotels.athena.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name = "ct_tp1.pers_mn", uniqueConstraints={@UniqueConstraint(columnNames={"pers_ser"})})
public class EmployeeHQ extends Employee {
	private static final long serialVersionUID = 1L;
	
	public EmployeeHQ(){
		super();
	}
}
