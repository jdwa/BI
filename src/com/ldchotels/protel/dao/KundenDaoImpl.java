package com.ldchotels.protel.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.ldchotels.protel.model.Kunden;

public class KundenDaoImpl extends HibernateDaoSupport implements KundenDao {

	private static Logger logger = Logger.getLogger(KundenDaoImpl.class.getName());
		
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Kunden findByKdnr(Long kdnr) {
		List<Kunden> kundenList = (List<Kunden>) getHibernateTemplate().find("from Kunden where kdnr = '" + kdnr + "'");
		return kundenList.size() > 0 ? (Kunden)kundenList.get(0) : null;
		//return (Kunden) getHibernateTemplate().get(Kunden.class, kdnr);
	}

	@Override
	@Transactional(readOnly = true)
	public Kunden add(Kunden kunden) {
		/* Do not modify DB data ! */
		//-- getHibernateTemplate().saveOrUpdate(kunden);
		logger.info("No Kunden DB data modified !");
		return kunden;
	}

	@Override
	@Transactional(readOnly = true)
	public Kunden delete(Long kdnr) {
		/* Do not modify DB data ! */
		//-- getHibernateTemplate().delete(kunden);
		logger.info("No Kunden DB data modified !");
		Kunden kunden = findByKdnr(kdnr);
		return kunden;
	}

	@Override
	@Transactional(readOnly = true)
	public Kunden detail(Long kdnr) {
		return findByKdnr(kdnr);
	}

	@Override
	@Transactional(readOnly = true)
	public Kunden update(Kunden kunden) {
		/* Do not modify DB data ! */
		//-- getHibernateTemplate().saveOrUpdate(kunden);
		logger.info("No Kunden DB data modified !");
		return kunden;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Kunden> list() {
		HibernateTemplate ht = getHibernateTemplate();
		ht.setMaxResults(100);
		logger.info("Show top 100 !");
		return (List<Kunden>) getHibernateTemplate().find("from Kunden");
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Kunden> list(String chgBegin, String chgEnd) {
		logger.info("Criteria : Kunden changed between [" + chgBegin + "] and [" + chgEnd + "]");
		return (List<Kunden>) getHibernateTemplate().find("from Kunden where changed between '" + chgBegin  + "' and '" + chgEnd + "'");
	}
}
