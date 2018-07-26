package com.ldchotels.protel.bo;

import java.util.List;

import com.ldchotels.protel.model.Reservation;

public interface ReservationBo {
	public Reservation findByBuchnr(Long id);
	public Reservation add(Reservation reservation);
	public Reservation delete(Long id);
	public Reservation detail(Long id);
	public Reservation update(Reservation reservation);	
	public List<Reservation> list();
	public List<Reservation> list(String arrBegin, String arrEnd);
}
