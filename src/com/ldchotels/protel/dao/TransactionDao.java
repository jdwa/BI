package com.ldchotels.protel.dao;

import java.util.List;

import com.ldchotels.protel.model.Transaction;

public interface TransactionDao {
	public Transaction findByName(String name);
	public Transaction add(Transaction transaction);
	public Transaction delete(String name);
	public Transaction detail(String name);
	public Transaction update(Transaction transaction);
	public List<Transaction> list();
	public List<Transaction> list(String depBegin, String depEnd);
}
