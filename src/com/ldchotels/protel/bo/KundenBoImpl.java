package com.ldchotels.protel.bo;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

import com.ldchotels.protel.dao.KundenDao;
import com.ldchotels.protel.model.Kunden;

public class KundenBoImpl implements KundenBo{

	private KundenDao kundenDao;

	@Override
	public Kunden findByKdnr(Long kdnr) {
		return kundenDao.findByKdnr(kdnr);
	}

	//DI via Spring
	public void setKundenDao(KundenDao kundenDao) {
		this.kundenDao = kundenDao;
	}
	
	@Override
	@Transactional
	public Kunden add(Kunden kunden) {
		return kundenDao.add(kunden);
	}

	@Override
	@Transactional
	public Kunden delete(Long kdnr) {
		return kundenDao.delete(kdnr);
	}

	@Override
	public List<Kunden> list() {
		return kundenDao.list();
	}
	
	@Override
	public List<Kunden> list(String chgBegin, String chgEnd) {
		return kundenDao.list(chgBegin, chgEnd);
	}

	@Override
	public Kunden detail(Long kdnr) {
		return kundenDao.detail(kdnr);
	}

	@Override
	@Transactional
	public Kunden update(Kunden kunden) {
		return kundenDao.update(kunden);
	}
}
