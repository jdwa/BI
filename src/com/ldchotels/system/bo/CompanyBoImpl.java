package com.ldchotels.system.bo;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ldchotels.system.dao.CompanyDao;
import com.ldchotels.system.model.Company;

public class CompanyBoImpl implements CompanyBo {
	
	private CompanyDao companyDao;

	@Override
	public Company findById(Long id) {
		return companyDao.findById(id);
	}

	@Override
	public Company findByNo(String no) {
		return companyDao.findByNo(no);
	}

	//DI via Spring
	public void setCompanyDao(CompanyDao companyDao) {
		this.companyDao = companyDao;
	}
	
	@Override
	@Transactional("systemTransactionManager")
	public Company add(Company company) {
		return companyDao.add(company);
	}

	@Override
	@Transactional("systemTransactionManager")
	public Company delete(Long id) {
		return companyDao.delete(id);
	}

	@Override
	public List<Company> list() {
		return companyDao.list();
	}

	@Override
	public Company detail(Long id) {
		return companyDao.detail(id);
	}

	@Override
	@Transactional("systemTransactionManager")
	public Company update(Company company) {
		return companyDao.update(company);
	}
}
