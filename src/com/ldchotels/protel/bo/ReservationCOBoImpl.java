package com.ldchotels.protel.bo;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ldchotels.protel.dao.ReservationCODao;
import com.ldchotels.protel.model.ReservationCO;

public class ReservationCOBoImpl implements ReservationCOBo{

	private ReservationCODao reservationCODao;

	@Override
	public ReservationCO findByBuchnr(Long buchnr) {
		return reservationCODao.findByBuchnr(buchnr);
	}

	//DI via Spring
	public void setReservationCODao(ReservationCODao reservationCODao) {
		this.reservationCODao = reservationCODao;
	}
	
	@Override
	@Transactional
	public ReservationCO add(ReservationCO reservationCO) {
		return reservationCODao.add(reservationCO);
	}

	@Override
	@Transactional
	public ReservationCO delete(Long buchnr) {
		return reservationCODao.delete(buchnr);
	}

	@Override
	public ReservationCO detail(Long buchnr) {
		return reservationCODao.detail(buchnr);
	}

	@Override
	@Transactional
	public ReservationCO update(ReservationCO reservationCO) {
		return reservationCODao.update(reservationCO);
	}

	@Override
	public List<ReservationCO> list() {
		return reservationCODao.list();
	}
	
	@Override
	public List<ReservationCO> list(String arrBegin, String arrEnd) {
		return reservationCODao.list(arrBegin, arrEnd);
	}
}
