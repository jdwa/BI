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
@Table(name = "vw_Reservation_master", uniqueConstraints={@UniqueConstraint(columnNames={"buchnr"})})
public class Reservation implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long buchnr;
	private Long Account__c;
	private String Booker_Last_Name__c;
	private String Hotel__c;
	private String Name;
	private String Room_Type_Group__c;
	private String Room_Type_Code__c;
	private String buchstatus;
	private String Rate_code__c;
	private String Reason_of_cancellation__c;
	private String Pickup_code_Departure__c;
	private String Pickup_Codes_Arrival__c;
	private String Split_Table__c;
	private String Booker__c;
	private String source__c;
	private String source;
	private String Source_Code__c;
	private String Market_Group__c;
	private String Market__c;
	private String modifiedforecast;
	private String Booking_status__c;
	private String reschar;
	private String Discount_Reason_Codes__c;
	private String Normal_Room_type__c;
	private Date Reservation_Date__c;
	private Date Arrival_date_for_this_room__c;
	private Date Departure_date_for_this_room__c;
	private Date Date_of_cancellation__c;
	private Date globdvon;
	private Integer Number_of_Adult__c;
	private Integer anzkin1;
	private Integer anzkin2;
	private Integer anzkin3;
	private Integer anzkin4;
	
	@Id
	@Column(name = "buchnr", unique = true)
	public Long getBuchnr() {
		return buchnr;
	}
	public void setBuchnr(Long buchnr) {
		this.buchnr = buchnr;
	}
	
	@Column(name = "Booker_Last_Name__c")
	public String getBooker_Last_Name__c() {
		return Booker_Last_Name__c;
	}
	public void setBooker_Last_Name__c(String booker_Last_Name__c) {
		Booker_Last_Name__c = booker_Last_Name__c;
	}
	
	@Column(name = "Hotel__c")
	public String getHotel__c() {
		return Hotel__c;
	}
	public void setHotel__c(String hotel__c) {
		Hotel__c = hotel__c;
	}
	
	@Column(name = "Reservation_Date__c")
	public Date getReservation_Date__c() {
		return Reservation_Date__c;
	}
	public void setReservation_Date__c(Date reservation_Date__c) {
		Reservation_Date__c = reservation_Date__c;
	}
	
	@Column(name = "Name")
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	
	@Column(name = "Number_of_Adult__c")
	public Integer getNumber_of_Adult__c() {
		return Number_of_Adult__c;
	}
	public void setNumber_of_Adult__c(Integer number_of_Adult__c) {
		Number_of_Adult__c = number_of_Adult__c;
	}
	
	@Column(name = "anzkin1")
	public Integer getAnzkin1() {
		return anzkin1;
	}
	public void setAnzkin1(Integer anzkin1) {
		this.anzkin1 = anzkin1;
	}
	
	@Column(name = "anzkin2")
	public Integer getAnzkin2() {
		return anzkin2;
	}
	public void setAnzkin2(Integer anzkin2) {
		this.anzkin2 = anzkin2;
	}
	
	@Column(name = "anzkin3")
	public Integer getAnzkin3() {
		return anzkin3;
	}
	public void setAnzkin3(Integer anzkin3) {
		this.anzkin3 = anzkin3;
	}
	
	@Column(name = "anzkin4")
	public Integer getAnzkin4() {
		return anzkin4;
	}
	public void setAnzkin4(Integer anzkin4) {
		this.anzkin4 = anzkin4;
	}
	
	@Column(name = "Booking_status__c")
	public String getBooking_status__c() {
		return Booking_status__c;
	}
	public void setBooking_status__c(String booking_status__c) {
		Booking_status__c = booking_status__c;
	}
	
	@Column(name = "Arrival_date_for_this_room__c")
	public Date getArrival_date_for_this_room__c() {
		return Arrival_date_for_this_room__c;
	}
	public void setArrival_date_for_this_room__c(
			Date arrival_date_for_this_room__c) {
		Arrival_date_for_this_room__c = arrival_date_for_this_room__c;
	}
	
	@Column(name = "Departure_date_for_this_room__c")
	public Date getDeparture_date_for_this_room__c() {
		return Departure_date_for_this_room__c;
	}
	public void setDeparture_date_for_this_room__c(
			Date departure_date_for_this_room__c) {
		Departure_date_for_this_room__c = departure_date_for_this_room__c;
	}
	
	@Column(name = "Room_Type_Group__c")
	public String getRoom_Type_Group__c() {
		return Room_Type_Group__c;
	}
	public void setRoom_Type_Group__c(String room_Type_Group__c) {
		Room_Type_Group__c = room_Type_Group__c;
	}
	
	@Column(name = "Room_Type_Code__c")
	public String getRoom_Type_Code__c() {
		return Room_Type_Code__c;
	}
	public void setRoom_Type_Code__c(String room_Type_Code__c) {
		Room_Type_Code__c = room_Type_Code__c;
	}
	
	@Column(name = "buchstatus")
	public String getBuchstatus() {
		return buchstatus;
	}
	public void setBuchstatus(String buchstatus) {
		this.buchstatus = buchstatus;
	}
	
	@Column(name = "Rate_code__c")
	public String getRate_code__c() {
		return Rate_code__c;
	}
	public void setRate_code__c(String rate_code__c) {
		Rate_code__c = rate_code__c;
	}
	
	@Column(name = "Date_of_cancellation__c")
	public Date getDate_of_cancellation__c() {
		return Date_of_cancellation__c;
	}
	public void setDate_of_cancellation__c(Date date_of_cancellation__c) {
		Date_of_cancellation__c = date_of_cancellation__c;
	}
	
	@Column(name = "Reason_of_cancellation__c")
	public String getReason_of_cancellation__c() {
		return Reason_of_cancellation__c;
	}
	public void setReason_of_cancellation__c(String reason_of_cancellation__c) {
		Reason_of_cancellation__c = reason_of_cancellation__c;
	}
	
	@Column(name = "Account__c")
	public Long getAccount__c() {
		return Account__c;
	}
	public void setAccount__c(Long account__c) {
		Account__c = account__c;
	}
	
	@Column(name = "Pickup_code_Departure__c")
	public String getPickup_code_Departure__c() {
		return Pickup_code_Departure__c;
	}
	public void setPickup_code_Departure__c(String pickup_code_Departure__c) {
		Pickup_code_Departure__c = pickup_code_Departure__c;
	}
	
	@Column(name = "Pickup_Codes_Arrival__c")
	public String getPickup_Codes_Arrival__c() {
		return Pickup_Codes_Arrival__c;
	}
	public void setPickup_Codes_Arrival__c(String pickup_Codes_Arrival__c) {
		Pickup_Codes_Arrival__c = pickup_Codes_Arrival__c;
	}
	
	@Column(name = "Split_Table__c")
	public String getSplit_Table__c() {
		return Split_Table__c;
	}
	public void setSplit_Table__c(String split_Table__c) {
		Split_Table__c = split_Table__c;
	}
	
	@Column(name = "Booker__c")
	public String getBooker__c() {
		return Booker__c;
	}
	public void setBooker__c(String booker__c) {
		Booker__c = booker__c;
	}
	
	@Column(name = "source")
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}

	@Column(name = "Source__c")
	public String getSource__c() {
		return source__c;
	}
	public void setSource__c(String source__c) {
		this.source__c = source__c;
	}
	
	/* Same as source, just for compatible. */
	@Column(name = "Source_Code__c")
	public String getSource_Code__c() {
		return Source_Code__c;
	}
	public void setSource_Code__c(String source_Code__c) {
		Source_Code__c = source_Code__c;
	}
	
	@Column(name = "Market_Group__c")
	public String getMarket_Group__c() {
		return Market_Group__c;
	}
	public void setMarket_Group__c(String market_Group__c) {
		Market_Group__c = market_Group__c;
	}
	
	@Column(name = "Market__c")
	public String getMarket__c() {
		return Market__c;
	}
	public void setMarket__c(String market__c) {
		Market__c = market__c;
	}
	
	@Column(name = "modifiedforecast")
	public String getModifiedforecast() {
		return modifiedforecast;
	}
	public void setModifiedforecast(String modifiedforecast) {
		this.modifiedforecast = modifiedforecast;
	}
	
	@Column(name = "globdvon")
	public Date getGlobdvon() {
		return globdvon;
	}
	public void setGlobdvon(Date globdvon) {
		this.globdvon = globdvon;
	}
	
	@Column(name = "reschar")
	public String getReschar() {
		return reschar;
	}
	public void setReschar(String reschar) {
		this.reschar = reschar;
	}
	
	@Column(name = "Discount_Reason_Codes__c")
	public String getDiscount_Reason_Codes__c() {
		return Discount_Reason_Codes__c;
	}
	public void setDiscount_Reason_Codes__c(String discount_Reason_Codes__c) {
		Discount_Reason_Codes__c = discount_Reason_Codes__c;
	}
	
	@Column(name = "Normal_Room_type__c")
	public String getNormal_Room_type__c() {
		return Normal_Room_type__c;
	}
	public void setNormal_Room_type__c(String normal_Room_type__c) {
		Normal_Room_type__c = normal_Room_type__c;
	}
}
