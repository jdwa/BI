package com.ldchotels.athena.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Employee implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String pers_ser;
	private String id_nos;
	private String pers_nam;
	private String mail_addr;
	private Date birth_dat;
	private Date enter_dat;
	private Date quit_dat;
		
	@Id
	@Column(name = "pers_ser", unique = true)
	public String getPers_ser() {
		return pers_ser;
	}
	public void setPers_ser(String pers_ser) {
		this.pers_ser = pers_ser;
	}
	
	@Column(name = "id_nos")
	public String getId_nos() {
		return id_nos;
	}
	public void setId_nos(String id_nos) {
		this.id_nos = id_nos;
	}
	
	@Column(name = "pers_nam")
	public String getPers_nam() {
		return pers_nam;
	}
	public void setPers_nam(String pers_nam) {
		this.pers_nam = pers_nam;
	}
	
	@Column(name = "mail_addr")
	public String getMail_addr() {
		return mail_addr;
	}
	public void setMail_addr(String mail_addr) {
		this.mail_addr = mail_addr;
	}
	
	@Column(name = "birth_dat")
	public Date getBirth_dat() {
		return birth_dat;
	}
	public void setBirth_dat(Date birth_dat) {
		this.birth_dat = birth_dat;
	}
	
	@Column(name = "enter_dat")
	public Date getEnter_dat() {
		return enter_dat;
	}
	public void setEnter_dat(Date enter_dat) {
		this.enter_dat = enter_dat;
	}
	
	@Column(name = "quit_dat")
	public Date getQuit_dat() {
		return quit_dat;
	}
	public void setQuit_dat(Date quit_dat) {
		this.quit_dat = quit_dat;
	}
}
