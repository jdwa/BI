package com.ldchotels.protel.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.ldchotels.protel.model.Transaction;

public class TransactionDaoImpl extends HibernateDaoSupport implements TransactionDao {

	private static Logger logger = Logger.getLogger(TransactionDaoImpl.class.getName());
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Transaction findByName(String name) {
		List<Transaction> transactionList= (List<Transaction>)getHibernateTemplate().find("from Transaction where buchnr = '" + name + "'");
		return transactionList.size() > 0 ? (Transaction)transactionList.get(0) : null;
		//return (Transaction) getHibernateTemplate().get(Transaction.class, name);
	}

	@Override
	@Transactional(readOnly = true)
	public Transaction add(Transaction transaction) {
		/* Do not modify DB data ! */
		//-- getHibernateTemplate().saveOrUpdate(reservation);
		logger.info("No Transaction DB data modified !");
		return transaction;
	}

	@Override
	@Transactional(readOnly = true)
	public Transaction delete(String name) {
		/* Do not modify DB data ! */
		//-- getHibernateTemplate().delete(name);
		logger.info("No Transaction DB data modified !");
		Transaction transaction = findByName(name);
		return transaction;
	}

	@Override
	@Transactional(readOnly = true)
	public Transaction detail(String name) {
		return findByName(name);
	}

	@Override
	@Transactional(readOnly = true)
	public Transaction update(Transaction reservation) {
		/* Do not modify DB data ! */
		//-- getHibernateTemplate().saveOrUpdate(reservation);
		logger.info("No Reservation DB data modified !");
		return reservation;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Transaction> list() {
		HibernateTemplate ht = getHibernateTemplate();
		ht.setMaxResults(100);
		logger.info("Show top 100 !");
		return (List<Transaction>) getHibernateTemplate().find("from Transaction");
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Transaction> list(String depBegin, String depEnd) {
		logger.info("Criteria : Transaction departure between [" + depBegin + "] and [" + depEnd + "]");
		return (List<Transaction>) getHibernateTemplate().find("from Transaction where Departure_date_for_this_reservation__c between '" + depBegin  + "' and '" + depEnd + "'");
	}
}
