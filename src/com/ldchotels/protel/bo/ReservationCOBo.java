package com.ldchotels.protel.bo;

import java.util.List;

import com.ldchotels.protel.model.ReservationCO;

public interface ReservationCOBo {
	public ReservationCO findByBuchnr(Long id);
	public ReservationCO add(ReservationCO reservationCO);
	public ReservationCO delete(Long id);
	public ReservationCO detail(Long id);
	public ReservationCO update(ReservationCO reservationCO);	
	public List<ReservationCO> list();
	public List<ReservationCO> list(String arrBegin, String arrEnd);
}
