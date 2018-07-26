package com.ldchotels.protel.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.ldchotels.protel.model.Reservation;

public class ReservationDaoImpl extends HibernateDaoSupport implements ReservationDao {

	private static Logger logger = Logger.getLogger(ReservationDaoImpl.class.getName());
	

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Reservation findByBuchnr(Long buchnr) {
		List<Reservation> reservationList= (List<Reservation>)getHibernateTemplate().find("from Reservation where buchnr = '" + buchnr + "'");
		return reservationList.size() > 0 ? (Reservation)reservationList.get(0) : null;
		//return (Reservation) getHibernateTemplate().get(Reservation.class, buchnr);
	}

	@Override
	@Transactional(readOnly = true)
	public Reservation add(Reservation reservation) {
		/* Do not modify DB data ! */
		//-- getHibernateTemplate().saveOrUpdate(reservation);
		logger.info("No Reservation DB data modified !");
		return reservation;
	}

	@Override
	@Transactional(readOnly = true)
	public Reservation delete(Long buchnr) {
		/* Do not modify DB data ! */
		//-- getHibernateTemplate().delete(buchnr);
		logger.info("No Reservation DB data modified !");
		Reservation reservation = findByBuchnr(buchnr);
		return reservation;
	}

	@Override
	@Transactional(readOnly = true)
	public Reservation detail(Long buchnr) {
		return findByBuchnr(buchnr);
	}

	@Override
	@Transactional(readOnly = true)
	public Reservation update(Reservation reservation) {
		/* Do not modify DB data ! */
		//-- getHibernateTemplate().saveOrUpdate(reservation);
		logger.info("No Reservation DB data modified !");
		return reservation;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Reservation> list() {
		HibernateTemplate ht = getHibernateTemplate();
		ht.setMaxResults(100);
		logger.info("Show top 100 !");
		return (List<Reservation>) getHibernateTemplate().find("from Reservation");
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Reservation> list(String arrBegin, String arrEnd) {
		logger.info("Criteria : Reservation arrival between [" + arrBegin + "] and [" + arrEnd + "]");
		return (List<Reservation>) getHibernateTemplate().find("from Reservation where globdvon between '" + arrBegin  + "' and '" + arrEnd + "'");
	}
}
