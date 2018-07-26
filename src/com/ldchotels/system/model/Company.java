package com.ldchotels.system.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "Company", uniqueConstraints={@UniqueConstraint(columnNames={"cmp_no"})})
public class Company implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String cmp_no;
	private String cmp_description;
	private String remark;
	private Date creation_date;
	private String timestamp;
	
	@Id
	@GeneratedValue
	@Column(name = "id", unique = true)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "cmp_no", unique = true)
	public String getCmp_no() {
		return cmp_no;
	}
	public void setCmp_no(String cmp_no) {
		this.cmp_no = cmp_no;
	}
	
	@Column(name = "cmp_description")
	public String getCmp_description() {
		return cmp_description;
	}
	public void setCmp_description(String cmp_description) {
		this.cmp_description = cmp_description;
	}
	
	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name = "creation_date")
	public Date getCreation_date() {
		return creation_date;
	}
	public void setCreation_date(Date creation_date) {
		this.creation_date = creation_date;
	}
	
	@Column(name = "timestamp")
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}	
}
