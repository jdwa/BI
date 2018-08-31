package com.ldchotels.protel.bo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.log4j.Logger;

import com.ldchotels.protel.model.Kunden;
import com.ldchotels.protel.model.Reservation;
import com.ldchotels.protel.model.ReservationCO;
import com.ldchotels.protel.model.Transaction;
import com.ldchotels.util.SalesforceProperty;
import com.ldchotels.util.StringUtils;

public class ProtelBoImpl implements ProtelBo {
	
	private static Logger logger = Logger.getLogger(ProtelBoImpl.class.getName());
	
	private Kunden kunden; // For Add, Update action
	private KundenBo kundenBo;
	private Reservation reservation; // For Add, Update action
	private ReservationBo reservationBo;
	private ReservationCO reservationCO; // For Add, Update action
	private ReservationCOBo reservationCOBo;
	private Transaction transaction; // For Add, Update action
	private TransactionBo transactionBo;
	
	public ProtelBoImpl(SalesforceProperty sfProperty, KundenBo kundenBo) {		
		this.kundenBo = kundenBo;
	}
	
	public ProtelBoImpl(SalesforceProperty sfProperty, ReservationBo reservationBo) {		
		this.reservationBo = reservationBo;
	}
	
	public ProtelBoImpl(SalesforceProperty sfProperty, ReservationCOBo reservationCOBo) {		
		this.reservationCOBo = reservationCOBo;
	}
	
	public ProtelBoImpl(SalesforceProperty sfProperty, TransactionBo transactionBo) {		
		this.transactionBo = transactionBo;
	}
	
	public List<Kunden> getKundensFromProtel(String chgBegin, String chgEnd) {
		// Get kundens from Protel.
		return kundenBo.list(chgBegin, chgEnd);	
	}
	
	public List<Reservation> getReservationsFromProtel(String arrBegin, String arrEnd) {
		// Get reservations from Protel.
		return reservationBo.list(arrBegin, arrEnd);	
	}
	
	public List<ReservationCO> getReservationCOsFromProtel(String coArrBegin, String coArrEnd) {
		// Get reservations from Protel.
		return reservationCOBo.list(coArrBegin, coArrEnd);	
	}
	
	public List<Transaction> getTransactionsFromProtel(String depBegin, String depEnd) {
		// Get reservations from Protel.
		return transactionBo.list(depBegin, depEnd);
	}	
	
	public String saveKundensToFile(String fileName, List<Kunden> kundenList) throws IOException {
		// Create a BufferedWriter around a FileWriter.
		// Write to an output text file.
		logger.info("Saving file : [" + fileName + "] ...");
		File outputFile = new File(fileName);	
		BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		// Write these lines to the file.
		// We call newLine to insert a newline character.
		writer.write("Guest_Profile_No__c,"
				+ "FIRSTNAME,"
				+ "City__c,"
				+ "Market__c,"
				+ "Market_Code__c,"
				+ "Stays_in_the_current_fiscal_year__c,"
				+ "ID__c,"
				+ "LASTNAME,"
				+ "Company__c,"
				+ "VIP__c,"
				+ "Gender__c,"
				//-- + "PersonBirthdate__c," // Lead API name 
				+ "PersonBirthdate," // Account API name
				//-- + "Address,"
				+ "Country__c,"
				//-- + "Email," // Lead API name
				+ "PersonEmail," // Account API name
				+ "Language__c,"
				+ "Nationality__c,"
				+ "Phone,"
				+ "Personal_Preference__c,"
				//-- + "MobilePhone," // Lead API name
				+ "PersonMobilePhone," // Account API name
				+ "HasOptedOutOfEmail__c");

		for (int i = 0; i < kundenList.size(); i++) {
			writer.newLine();
			kunden = kundenList.get(i);
			writer.write(kunden.getKdnr() + ",");
			writer.write(StringUtils.addDoubleQuote(kunden.getVorname()) + ",");
			writer.write(StringUtils.addDoubleQuote(kunden.getOrt()) + ",");
			writer.write(StringUtils.addDoubleQuote(kunden.getMarket__c()) + ",");
			writer.write(StringUtils.addDoubleQuote(kunden.getMarket_code__c()) + ",");
			writer.write(kunden.getAufenth() + ",");
			writer.write(StringUtils.addDoubleQuote(kunden.getPassnr()) + ",");
			writer.write(StringUtils.addDoubleQuote(kunden.getLastName()) + ",");
			writer.write(StringUtils.addDoubleQuote(kunden.getCompany__c()) + ",");
			writer.write(StringUtils.addDoubleQuote(kunden.getVIP__c()) + ",");
			writer.write(StringUtils.addDoubleQuote(kunden.getGender()) + ",");
			if (kunden.getPersonBirthdate() != null) {
				writer.write(sdf.format(kunden.getPersonBirthdate()) + ",");
			} else {
				writer.write("" + ",");
			}
			//-- writer.write(kunden.getStrasse() + ",");
			writer.write(StringUtils.addDoubleQuote(kunden.getLand()) + ",");
			writer.write(StringUtils.addDoubleQuote(kunden.getPersonEmail()) + ",");
			writer.write(StringUtils.addDoubleQuote(kunden.getLanguage__c()) + ",");
			writer.write(StringUtils.addDoubleQuote(kunden.getNationality__c()) + ",");
			writer.write(StringUtils.addDoubleQuote(kunden.getPhone())+ ",");
			writer.write(StringUtils.addDoubleQuote(kunden.getPersonal_Preference__c()) + ",");
			writer.write(StringUtils.addDoubleQuote(kunden.getPersonMobilePhone()) + ",");
			writer.write((kunden.getMailing() == 1 ? "0" : "1"));
		}

		writer.flush();
		writer.close();
		logger.info("Saving file : [" + fileName + "] completed.");
		return outputFile.getAbsoluteFile().getAbsolutePath();
    }

	public String saveReservationsToFile(String fileName, List<Reservation> reservationList) throws IOException {
		// Create a BufferedWriter around a FileWriter.
		// Write to an output text file.
		logger.info("Saving file : [" + fileName + "] ...");
		File outputFile = new File(fileName);		
		BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		// Write these lines to the file.
		// We call newLine to insert a newline character.
		writer.write("Hotel__c,"
				// + "Protel_ProfileID__r.Guest_Profile_No__c,"
				+ "Account__r.Guest_Profile_No__c," // Important! Refer to Account.Guest_Profile_No__c field.
				+ "Reservation_Date__c,"
				+ "Name,"
				+ "Number_of_Adult__c,"
				+ "Number_of_Children__c,"
				+ "Booking_status__c,"
				+ "Arrival_date_for_this_room__c,"
				+ "Departure_date_for_this_room__c,"
				+ "Room_Type_Group__c,"
				+ "Room_Type_Code__c,"
				+ "Reservation_status__c,"
				+ "Discount_Reason_Codes__c,"
				+ "Pickup_Codes_Arrival__c,"
				+ "Pickup_code_Departure__c,"
				+ "Rate_code__c,"
				+ "Split_Table__c,"
				+ "Source__c,"
				+ "Promotion_code__c,"
				+ "Source_Code__c,"
				+ "Market_Group__c,"
				+ "Market__c,"
				+ "Date_of_cancellation__c,"
				+ "Reason_of_cancellation__c");
		
		for (int i = 0; i < reservationList.size(); i++) {
			writer.newLine();
			reservation = reservationList.get(i);
			writer.write(StringUtils.addDoubleQuote(reservation.getHotel__c()) + ",");
			writer.write(StringUtils.addDoubleQuote(reservation.getAccount__c().toString()) + ",");
			if (reservation.getReservation_Date__c() != null){
				writer.write(sdf.format(reservation.getReservation_Date__c()) + ",");
			} else {
				writer.write("" + ",");
			}
			writer.write(StringUtils.addDoubleQuote(reservation.getName()) + ",");
			writer.write(reservation.getNumber_of_Adult__c() + ",");
			writer.write(reservation.getAnzkin1() + reservation.getAnzkin2() + reservation.getAnzkin3() + reservation.getAnzkin4() + ",");
			writer.write(StringUtils.addDoubleQuote(reservation.getBooking_status__c()) + ",");
			if (reservation.getArrival_date_for_this_room__c() != null){
				writer.write(sdf.format(reservation.getArrival_date_for_this_room__c()) + ",");
			} else {
				writer.write("" + ",");
			}
			if (reservation.getDeparture_date_for_this_room__c() != null){
				writer.write(sdf.format(reservation.getDeparture_date_for_this_room__c()) + ",");
			} else {
				writer.write("" + ",");
			}
			writer.write(StringUtils.addDoubleQuote(reservation.getRoom_Type_Group__c()) + ",");
			writer.write(StringUtils.addDoubleQuote(reservation.getRoom_Type_Code__c()) + ",");
			writer.write(StringUtils.addDoubleQuote(reservation.getBuchstatus()) + ",");
			writer.write(StringUtils.addDoubleQuote(reservation.getDiscount_Reason_Codes__c()) + ",");
			writer.write(StringUtils.addDoubleQuote(reservation.getPickup_Codes_Arrival__c()) + ",");
			writer.write(StringUtils.addDoubleQuote(reservation.getPickup_code_Departure__c()) + ",");
			writer.write(StringUtils.addDoubleQuote(reservation.getRate_code__c()) + ",");
			writer.write(StringUtils.addDoubleQuote(reservation.getSplit_Table__c()) + ",");
			writer.write(StringUtils.addDoubleQuote(reservation.getSource__c()) + ",");
			writer.write(StringUtils.addDoubleQuote(reservation.getSource_Code__c()) + ",");
			writer.write(StringUtils.addDoubleQuote(reservation.getSource_Code__c()) + ",");
			writer.write(StringUtils.addDoubleQuote(reservation.getMarket_Group__c()) + ",");
			writer.write(StringUtils.addDoubleQuote(reservation.getMarket__c()) + ",");
			if (reservation.getDate_of_cancellation__c() != null){
				writer.write(sdf.format(reservation.getDate_of_cancellation__c()) + ",");
			} else { 
				writer.write("" + ",");
			}
			writer.write(StringUtils.addDoubleQuote(reservation.getReason_of_cancellation__c()));
		}

		writer.flush();
		writer.close();		
		logger.info("Saving file : [" + fileName + "] completed.");
		return outputFile.getAbsoluteFile().getAbsolutePath();
    }
	    
    public String saveReservationCOsToFile(String fileName, List<ReservationCO> reservationCOList) throws IOException {
		// Create a BufferedWriter around a FileWriter.
		// Write to an output text file.
    	logger.info("Saving file : [" + fileName + "] ...");
		File outputFile = new File(fileName);		
		BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		// Write these lines to the file.
		// We call newLine to insert a newline character.
		writer.write("Hotel__c,"
				// + "Protel_ProfileID__r.Guest_Profile_No__c,"
				+ "Account__r.Guest_Profile_No__c," // Important! Refer to Account.Guest_Profile_No__c field.
				+ "Reservation_Date__c,"
				+ "Name,"
				+ "Number_of_Adult__c,"
				+ "Number_of_Children__c,"
				+ "Booking_status__c,"
				+ "Arrival_date_for_this_room__c,"
				+ "Departure_date_for_this_room__c,"
				+ "Room_Type_Group__c,"
				+ "Room_Type_Code__c,"
				+ "Reservation_status__c,"
				+ "Discount_Reason_Codes__c,"
				+ "Pickup_Codes_Arrival__c,"
				+ "Pickup_code_Departure__c,"
				+ "Rate_code__c,"
				+ "Split_Table__c,"
				+ "Source__c,"
				+ "Promotion_code__c,"
				+ "Source_Code__c,"
				+ "Market_Group__c,"
				+ "Market__c,"
				+ "Date_of_cancellation__c,"
				+ "Reason_of_cancellation__c");

		for (int i = 0; i < reservationCOList.size(); i++) {
			writer.newLine();
			reservationCO = reservationCOList.get(i);
			writer.write(StringUtils.addDoubleQuote(reservationCO.getHotel__c()) + ",");
			writer.write(StringUtils.addDoubleQuote(reservationCO.getAccount__c().toString()) + ",");
			if (reservationCO.getReservation_Date__c() != null){
				writer.write(sdf.format(reservationCO.getReservation_Date__c()) + ",");
			} else {
				writer.write("" + ",");
			}
			writer.write(StringUtils.addDoubleQuote(reservationCO.getName()) + ",");
			writer.write(reservationCO.getNumber_of_Adult__c() + ",");
			writer.write(reservationCO.getAnzkin1() + reservationCO.getAnzkin2() + reservationCO.getAnzkin3() + reservationCO.getAnzkin4() + ",");
			writer.write(StringUtils.addDoubleQuote(reservationCO.getBooking_status__c()) + ",");
			if (reservationCO.getArrival_date_for_this_room__c() != null){
				writer.write(sdf.format(reservationCO.getArrival_date_for_this_room__c()) + ",");
			} else {
				writer.write("" + ",");
			}
			if (reservationCO.getDeparture_date_for_this_room__c() != null){
				writer.write(sdf.format(reservationCO.getDeparture_date_for_this_room__c()) + ",");
			} else {
				writer.write("" + ",");
			}
			writer.write(StringUtils.addDoubleQuote(reservationCO.getRoom_Type_Group__c()) + ",");
			writer.write(StringUtils.addDoubleQuote(reservationCO.getRoom_Type_Code__c()) + ",");
			writer.write(StringUtils.addDoubleQuote(reservationCO.getBuchstatus()) + ",");
			writer.write(StringUtils.addDoubleQuote(reservationCO.getDiscount_Reason_Codes__c()) + ",");
			writer.write(StringUtils.addDoubleQuote(reservationCO.getPickup_Codes_Arrival__c()) + ",");
			writer.write(StringUtils.addDoubleQuote(reservationCO.getPickup_code_Departure__c()) + ",");
			writer.write(StringUtils.addDoubleQuote(reservationCO.getRate_code__c()) + ",");
			writer.write(StringUtils.addDoubleQuote(reservationCO.getSplit_Table__c()) + ",");
			writer.write(StringUtils.addDoubleQuote(reservationCO.getSource__c()) + ",");
			writer.write(StringUtils.addDoubleQuote(reservationCO.getSource_Code__c()) + ",");
			writer.write(StringUtils.addDoubleQuote(reservationCO.getSource_Code__c()) + ",");
			writer.write(StringUtils.addDoubleQuote(reservationCO.getMarket_Group__c()) + ",");
			writer.write(StringUtils.addDoubleQuote(reservationCO.getMarket__c()) + ",");
			if (reservationCO.getDate_of_cancellation__c() != null){
				writer.write(sdf.format(reservationCO.getDate_of_cancellation__c()) + ",");
			} else { 
				writer.write("" + ",");
			}
			writer.write(StringUtils.addDoubleQuote(reservationCO.getReason_of_cancellation__c()));
		}

		writer.flush();
		writer.close();
		logger.info("Saving file : [" + fileName + "] completed.");
		return outputFile.getAbsoluteFile().getAbsolutePath();
    }
	
    public String saveTransactionsToFile(String fileName, List<Transaction> transactionList) throws IOException {
		// Create a BufferedWriter around a FileWriter.
		// Write to an output text file.
    	logger.info("Saving file : [" + fileName + "] ...");
		File outputFile = new File(fileName);		
		BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		// Write these lines to the file.
		// We call newLine to insert a newline character.
		writer.write("Name,"
				+ "Hotel__c,"
				+ "Account__r.Guest_Profile_No__c," // Important! Refer to Account.Guest_Profile_No__c field.
				+ "Arrival_date_for_this_room__c," 
				+ "Departure_date_for_this_room__c,"
				+ "Reservation_no__r.Name," // Important! Refer to Reservation_no__r.Name field.
				+ "Currency_of_the_posting__c,"
				+ "Source_Code__c,"
				+ "Market_Group__c,"
				+ "Data_source__c,"
				+ "Market__c,"
				+ "Rate_code__c,"
				+ "Discount_Reason_Codes__c,"
				+ "Promotion_code__c,"
				+ "Protel_Room_Date__c,"
				+ "Insert_date_in_PMS__c,"
				+ "Check_in_mark__c,"
				+ "Check_out_mark__c,"
				+ "Room_night_mark__c,"
				+ "F_B_Revenue__c,"
				+ "Extra_Revenue__c,"
				+ "Room_Revenue__c,"
				+ "Rev_Status__c,"
				+ "Transaction_Number__c");
		
		for (int i = 0; i < transactionList.size(); i++) {
			writer.newLine();
			transaction = transactionList.get(i);
			writer.write(StringUtils.addDoubleQuote(transaction.getName()) + ",");
			writer.write(StringUtils.addDoubleQuote(transaction.getHotel__c()) + ",");
			writer.write(StringUtils.addDoubleQuote(transaction.getAccount__c().toString()) + ",");
			if (transaction.getArrival_date_for_this_reservation__c() != null){
				writer.write(sdf.format(transaction.getArrival_date_for_this_reservation__c()) + ",");
			} else {
				writer.write("" + ",");
			}
			if (transaction.getDeparture_date_for_this_reservation__c() != null){
				writer.write(sdf.format(transaction.getDeparture_date_for_this_reservation__c()) + ",");
			} else {
				writer.write("" + ",");
			}
			writer.write(StringUtils.addDoubleQuote(transaction.getReservation_no__c().toString()) + ",");
			writer.write(StringUtils.addDoubleQuote(transaction.getCurrency_of_the_posting__c()) + ",");
			writer.write(StringUtils.addDoubleQuote(transaction.getSource_Code__c()) + ",");
			writer.write(StringUtils.addDoubleQuote(transaction.getMarket_Group__c()) + ",");
			writer.write("Protel" + ",");
			writer.write(StringUtils.addDoubleQuote(transaction.getMarket__c()) + ",");
			writer.write(StringUtils.addDoubleQuote(transaction.getRate_code__c()) + ",");
			writer.write(StringUtils.addDoubleQuote(transaction.getDiscount_Reason_Codes__c()) + ",");
			writer.write(StringUtils.addDoubleQuote(transaction.getSource_Code__c()) + ",");
			if (transaction.getInsert_date__c() != null){
				writer.write(sdf.format(transaction.getInsert_date__c()) + ",");
			} else {
				writer.write("" + ",");
			}
			if (transaction.getInsert_date__c() != null){
				writer.write(sdf.format(transaction.getInsert_date__c()) + ",");
			} else {
				writer.write("" + ",");
			}
			writer.write(transaction.getCntroom() + ",");
			writer.write(transaction.getRmdepart() + ",");
			writer.write(transaction.getImhaus() + ",");
			writer.write(transaction.getF_B_Revenue__c() + ",");
			writer.write(transaction.getExtra_Revenue__c() + ",");
			writer.write(transaction.getRoom_Revenue__c() + ",");
			writer.write(transaction.getRev_Status() + ",");
			writer.write(StringUtils.addDoubleQuote(transaction.getName()));
		}

		writer.flush();
		writer.close();		
		logger.info("Saving file : [" + fileName + "] completed.");
		return outputFile.getAbsoluteFile().getAbsolutePath();
    }
}

