package com.ldchotels.protel.dao;

import java.util.List;

import com.ldchotels.protel.model.Kunden;

public interface KundenDao {
	public Kunden findByKdnr(Long id);
	public Kunden add(Kunden kunden);
	public Kunden delete(Long id);
	public Kunden detail(Long id);
	public Kunden update(Kunden kunden);
	public List<Kunden> list();
	public List<Kunden> list(String chgBegin, String chgEnd);
}
