package com.ldchotels.protel.bo;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ldchotels.protel.dao.ReservationDao;
import com.ldchotels.protel.model.Reservation;

public class ReservationBoImpl implements ReservationBo{

	private ReservationDao reservationDao;

	@Override
	public Reservation findByBuchnr(Long buchnr) {
		return reservationDao.findByBuchnr(buchnr);
	}

	//DI via Spring
	public void setReservationDao(ReservationDao reservationDao) {
		this.reservationDao = reservationDao;
	}
	
	@Override
	@Transactional
	public Reservation add(Reservation reservation) {
		return reservationDao.add(reservation);
	}

	@Override
	@Transactional
	public Reservation delete(Long buchnr) {
		return reservationDao.delete(buchnr);
	}

	@Override
	public Reservation detail(Long buchnr) {
		return reservationDao.detail(buchnr);
	}

	@Override
	@Transactional
	public Reservation update(Reservation reservation) {
		return reservationDao.update(reservation);
	}

	@Override
	public List<Reservation> list() {
		return reservationDao.list();
	}
	
	@Override
	public List<Reservation> list(String arrBegin, String arrEnd) {
		return reservationDao.list(arrBegin, arrEnd);
	}
}
