package com.ldchotels.protel.bo;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ldchotels.protel.model.Transaction;
import com.ldchotels.protel.dao.TransactionDao;

public class TransactionBoImpl implements TransactionBo{

	private TransactionDao transactionDao;

	@Override
	public Transaction findByName(String name) {
		return transactionDao.findByName(name);
	}

	//DI via Spring
	public void setTransactionDao(TransactionDao transactionDao) {
		this.transactionDao = transactionDao;
	}
	
	@Override
	@Transactional
	public Transaction add(Transaction transaction) {
		return transactionDao.add(transaction);
	}

	@Override
	@Transactional
	public Transaction delete(String name) {
		return transactionDao.delete(name);
	}

	@Override
	public Transaction detail(String name) {
		return transactionDao.detail(name);
	}

	@Override
	@Transactional
	public Transaction update(Transaction transaction) {
		return transactionDao.update(transaction);
	}

	@Override
	public List<Transaction> list() {
		return transactionDao.list();
	}
	
	@Override
	public List<Transaction> list(String depBegin, String depEnd) {
		return transactionDao.list(depBegin, depEnd);
	}
}
