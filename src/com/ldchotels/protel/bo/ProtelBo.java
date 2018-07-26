package com.ldchotels.protel.bo;

import java.io.IOException;
import java.util.List;

import com.ldchotels.protel.model.Kunden;
import com.ldchotels.protel.model.Reservation;
import com.ldchotels.protel.model.ReservationCO;
import com.ldchotels.protel.model.Transaction;

public interface ProtelBo {
	
	public List<Kunden> getKundensFromProtel(String chgBegin, String chgEnd);
	public List<Reservation> getReservationsFromProtel(String arrBegin, String arrEnd);
	public List<ReservationCO> getReservationCOsFromProtel(String coArrBegin, String coArrEnd);
	public List<Transaction> getTransactionsFromProtel(String depBegin, String depEnd);
	public String saveKundensToFile(String fileName, List<Kunden> kundenList) throws IOException;
	public String saveReservationsToFile(String fileName, List<Reservation> reservationList) throws IOException;
	public String saveReservationCOsToFile(String fileName, List<ReservationCO> reservationCOList) throws IOException;
	public String saveTransactionsToFile(String fileName, List<Transaction> transactionList) throws IOException;

}
