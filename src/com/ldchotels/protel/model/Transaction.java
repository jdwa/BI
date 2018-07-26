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
@Table(name = "vw_Transaction_master", uniqueConstraints={@UniqueConstraint(columnNames={"Name"})})
public class Transaction implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String Name;
	private Long Reservation_no__c;
	private Long Account__c;
	private Long Extra_Revenue__c;
	private Long Room_Revenue__c;
	private Long F_B_Revenue__c;
	private Integer imhaus;
	private Integer cntroom;
	private Integer rmarrive;
	private Integer rmdepart;
	private Integer Rev_Status;
	private String Hotel__c;
	private String Rate_code__c;
	private String Currency_of_the_posting__c;				   	
	private String Source_Code__c;
	private String Market_Group__c;
	private String Market__c;
	private String Discount_Reason_Codes__c;
	private Date Arrival_date_for_this_reservation__c;
	private Date Departure_date_for_this_reservation__c;
	private Date Insert_date__c;
	
	@Id
	@Column(name = "Name", unique = true)
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	
	@Column(name = "Hotel__c")
	public String getHotel__c() {
		return Hotel__c;
	}
	public void setHotel__c(String hotel__c) {
		Hotel__c = hotel__c;
	}
			
	@Column(name = "Rate_code__c")
	public String getRate_code__c() {
		return Rate_code__c;
	}
	public void setRate_code__c(String rate_code__c) {
		Rate_code__c = rate_code__c;
	}
		
	@Column(name = "Account__c")
	public Long getAccount__c() {
		return Account__c;
	}
	public void setAccount__c(Long account__c) {
		Account__c = account__c;
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
		
	@Column(name = "Discount_Reason_Codes__c")
	public String getDiscount_Reason_Codes__c() {
		return Discount_Reason_Codes__c;
	}
	public void setDiscount_Reason_Codes__c(String discount_Reason_Codes__c) {
		Discount_Reason_Codes__c = discount_Reason_Codes__c;
	}
	
	@Column(name = "Reservation_no__c")
	public Long getReservation_no__c() {
		return Reservation_no__c;
	}
	public void setReservation_no__c(Long reservation_no__c) {
		Reservation_no__c = reservation_no__c;
	}
	
	@Column(name = "Extra_Revenue__c")
	public Long getExtra_Revenue__c() {
		return Extra_Revenue__c;
	}
	public void setExtra_Revenue__c(Long extra_Revenue__c) {
		Extra_Revenue__c = extra_Revenue__c;
	}
	
	@Column(name = "Room_Revenue__c")
	public Long getRoom_Revenue__c() {
		return Room_Revenue__c;
	}
	public void setRoom_Revenue__c(Long room_Revenue__c) {
		Room_Revenue__c = room_Revenue__c;
	}
	
	@Column(name = "F_B_Revenue__c")
	public Long getF_B_Revenue__c() {
		return F_B_Revenue__c;
	}
	public void setF_B_Revenue__c(Long f_B_Revenue__c) {
		F_B_Revenue__c = f_B_Revenue__c;
	}
	
	@Column(name = "imhaus")
	public Integer getImhaus() {
		return imhaus;
	}
	public void setImhaus(Integer imhaus) {
		this.imhaus = imhaus;
	}
	
	@Column(name = "cntroom")
	public Integer getCntroom() {
		return cntroom;
	}
	public void setCntroom(Integer cntroom) {
		this.cntroom = cntroom;
	}
	
	@Column(name = "rmarrive")
	public Integer getRmarrive() {
		return rmarrive;
	}
	public void setRmarrive(Integer rmarrive) {
		this.rmarrive = rmarrive;
	}
	
	@Column(name = "rmdepart")
	public Integer getRmdepart() {
		return rmdepart;
	}
	public void setRmdepart(Integer rmdepart) {
		this.rmdepart = rmdepart;
	}
	
	@Column(name = "Rev_Status")
	public Integer getRev_Status() {
		return Rev_Status;
	}
	public void setRev_Status(Integer rev_Status) {
		Rev_Status = rev_Status;
	}
	
	@Column(name = "Currency_of_the_posting_c")
	public String getCurrency_of_the_posting__c() {
		return Currency_of_the_posting__c;
	}
	public void setCurrency_of_the_posting__c(String currency_of_the_posting__c) {
		Currency_of_the_posting__c = currency_of_the_posting__c;
	}
	
	@Column(name = "Insert_date__c")
	public Date getInsert_date__c() {
		return Insert_date__c;
	}
	public void setInsert_date__c(Date insert_date__c) {
		Insert_date__c = insert_date__c;
	}
	
	@Column(name = "Arrival_date_for_this_reservation__c")
	public Date getArrival_date_for_this_reservation__c() {
		return Arrival_date_for_this_reservation__c;
	}
	public void setArrival_date_for_this_reservation__c(
			Date arrival_date_for_this_reservation__c) {
		Arrival_date_for_this_reservation__c = arrival_date_for_this_reservation__c;
	}
	
	@Column(name = "Departure_date_for_this_reservation__c")
	public Date getDeparture_date_for_this_reservation__c() {
		return Departure_date_for_this_reservation__c;
	}
	public void setDeparture_date_for_this_reservation__c(
			Date departure_date_for_this_reservation__c) {
		Departure_date_for_this_reservation__c = departure_date_for_this_reservation__c;
	}
}
