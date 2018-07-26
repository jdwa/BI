package com.ldchotels.protel.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name = "vw_Kunden_master", uniqueConstraints={@UniqueConstraint(columnNames={"kdnr"})})
public class Kunden implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long kdnr;
	private String passnr;
	private String vorname;
	private String LastName;
	private String Company__c;
	private String VIP__c;
	private String Gender;
	private String ort;
	private String strasse;
	private String strasse2;
	private String strasse3;
	private String PersonEmail;
	private String Language__c;
	private String Nationality__c;
	private String Phone;
	private String Personal_Preference__c;
	private String Market__c;
	private String Market_code__c;
	private String PersonMobilePhone;
	private String land;
	private Date PersonBirthdate;
	private Date Insert_date__c;
	private Date changed;
	private Integer aufenth;
	private Integer mailing;
	
	@Id
	@Column(name = "kdnr", unique = true)
	public Long getKdnr() {
		return kdnr;
	}
	public void setKdnr(Long kdnr) {
		this.kdnr = kdnr;
	}
	
	@Column(name = "passnr")
	public String getPassnr() {
		return passnr;
	}
	public void setPassnr(String passnr) {
		this.passnr = passnr;
	}
	
	@Column(name = "vorname")
	public String getVorname() {
		return vorname;
	}
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	
	@Column(name = "LastName")
	public String getLastName() {
		return LastName;
	}
	public void setLastName(String lastName) {
		LastName = lastName;
	}
	
	@Column(name = "Company__c")
	public String getCompany__c() {
		return Company__c;
	}
	public void setCompany__c(String company__c) {
		Company__c = company__c;
	}
	
	@Column(name = "VIP__c")
	public String getVIP__c() {
		return VIP__c;
	}
	public void setVIP__c(String vIP__c) {
		VIP__c = vIP__c;
	}
	
	@Column(name = "Gender")
	public String getGender() {
		return Gender;
	}
	public void setGender(String gender) {
		Gender = gender;
	}
	
	@Column(name = "ort")
	public String getOrt() {
		return ort;
	}
	public void setOrt(String ort) {
		this.ort = ort;
	}
	
	@Column(name = "strasse")
	public String getStrasse() {
		return strasse;
	}
	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}
	
	@Column(name = "strasse2")
	public String getStrasse2() {
		return strasse2;
	}
	public void setStrasse2(String strasse2) {
		this.strasse2 = strasse2;
	}
	
	@Column(name = "strasse3")
	public String getStrasse3() {
		return strasse3;
	}
	public void setStrasse3(String strasse3) {
		this.strasse3 = strasse3;
	}
	
	@Column(name = "PersonEmail")
	public String getPersonEmail() {
		return PersonEmail;
	}
	public void setPersonEmail(String personEmail) {
		PersonEmail = personEmail;
	}
	
	@Column(name = "Language__c")
	public String getLanguage__c() {
		return Language__c;
	}
	public void setLanguage__c(String language__c) {
		Language__c = language__c;
	}
	
	@Column(name = "Nationality__c")
	public String getNationality__c() {
		return Nationality__c;
	}
	public void setNationality__c(String nationality__c) {
		Nationality__c = nationality__c;
	}
	
	@Column(name = "Phone")
	public String getPhone() {
		return Phone;
	}
	public void setPhone(String phone) {
		Phone = phone;
	}
	
	@Column(name = "Personal_Preference__c")
	public String getPersonal_Preference__c() {
		return Personal_Preference__c;
	}
	public void setPersonal_Preference__c(String personal_Preference__c) {
		Personal_Preference__c = personal_Preference__c;
	}
	
	@Column(name = "Market__c")
	public String getMarket__c() {
		return Market__c;
	}
	public void setMarket__c(String market__c) {
		Market__c = market__c;
	}
	
	@Column(name = "Market_code__c")
	public String getMarket_code__c() {
		return Market_code__c;
	}
	public void setMarket_code__c(String market_code__c) {
		Market_code__c = market_code__c;
	}
	
	@Column(name = "PersonMobilePhone")
	public String getPersonMobilePhone() {
		return PersonMobilePhone;
	}
	public void setPersonMobilePhone(String personMobilePhone) {
		PersonMobilePhone = personMobilePhone;
	}
	
	@Column(name = "land")
	public String getLand() {
		return land;
	}
	public void setLand(String land) {
		this.land = land;
	}
	
	@Column(name = "PersonBirthdate")
	public Date getPersonBirthdate() {
		return PersonBirthdate;
	}
	public void setPersonBirthdate(Date personBirthdate) {
		PersonBirthdate = personBirthdate;
	}
	
	@Column(name = "Insert_date__c")
	public Date getInsert_date__c() {
		return Insert_date__c;
	}
	public void setInsert_date__c(Date insert_date__c) {
		Insert_date__c = insert_date__c;
	}
	
	@Column(name = "changed")
	public Date getChanged() {
		return changed;
	}
	public void setChanged(Date changed) {
		this.changed = changed;
	}
	
	@Column(name = "aufenth")
	public Integer getAufenth() {
		return aufenth;
	}
	public void setAufenth(Integer aufenth) {
		this.aufenth = aufenth;
	}
	
	@Column(name = "mailing")
	public Integer getMailing() {
		return mailing;
	}
	public void setMailing(Integer mailing) {
		this.mailing = mailing;
	}
}
