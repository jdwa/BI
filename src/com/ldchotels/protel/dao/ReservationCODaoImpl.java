package com.ldchotels.protel.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.ldchotels.protel.model.ReservationCO;

public class ReservationCODaoImpl extends HibernateDaoSupport implements ReservationCODao {

	private static Logger logger = Logger.getLogger(ReservationCODaoImpl.class.getName());
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public ReservationCO findByBuchnr(Long buchnr) {
		List<ReservationCO> reservationCOList= (List<ReservationCO>)getHibernateTemplate().find("from ReservationCO where buchnr = '" + buchnr + "'");
		return reservationCOList.size() > 0 ? (ReservationCO)reservationCOList.get(0) : null;
		//return (ReservationCO) getHibernateTemplate().get(ReservationCO.class, buchnr);
	}

	@Override
	@Transactional(readOnly = true)
	public ReservationCO add(ReservationCO reservationCO) {
		/* Do not modify DB data ! */
		//-- getHibernateTemplate().saveOrUpdate(reservationCO);
		logger.info("No ReservationCO DB data modified !");
		return reservationCO;
	}

	@Override
	@Transactional(readOnly = true)
	public ReservationCO delete(Long buchnr) {
		/* Do not modify DB data ! */
		//-- getHibernateTemplate().delete(buchnr);
		logger.info("No ReservationCO DB data modified !");
		ReservationCO reservationCO = findByBuchnr(buchnr);
		return reservationCO;
	}

	@Override
	@Transactional(readOnly = true)
	public ReservationCO detail(Long buchnr) {
		return findByBuchnr(buchnr);
	}

	@Override
	@Transactional(readOnly = true)
	public ReservationCO update(ReservationCO reservationCO) {
		/* Do not modify DB data ! */
		//-- getHibernateTemplate().saveOrUpdate(reservationCO);
		logger.info("No ReservationCO DB data modified !");
		return reservationCO;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<ReservationCO> list() {
		HibernateTemplate ht = getHibernateTemplate();
		ht.setMaxResults(100);
		logger.info("Show top 100 !");
		return (List<ReservationCO>) getHibernateTemplate().find("from ReservationCO");
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<ReservationCO> list(String arrBegin, String arrEnd) {
		logger.info("Criteria : ReservationCO arrival between [" + arrBegin + "] and [" + arrEnd + "]");
		return (List<ReservationCO>) getHibernateTemplate().find("from ReservationCO where globdvon between '" + arrBegin  + "' and '" + arrEnd + "'");
	}
}
