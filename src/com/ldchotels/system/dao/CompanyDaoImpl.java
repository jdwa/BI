package com.ldchotels.system.dao;

import java.util.List;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import com.ldchotels.system.model.Company;

public class CompanyDaoImpl extends HibernateDaoSupport implements CompanyDao {

	@Override
	public Company findById(Long id) {
		return (Company) getHibernateTemplate().get(Company.class, id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Company findByNo(String no) {
		List<Company> list = (List<Company>) getHibernateTemplate().find("from Company where cmp_no = ?", no);
		return ((list != null) && (list.size() > 0)) ? (Company) list.get(0) : null;
	}

	@Override
	@Transactional("systemTransactionManager")
	public Company add(Company company) {
		getHibernateTemplate().saveOrUpdate(company);
		return company;
	}

	@Override
	@Transactional("systemTransactionManager")
	public Company delete(Long id) {
		Company company = findById(id);
		getHibernateTemplate().delete(company);
		return company;
	}

	@Override
	public Company detail(Long id) {
		return findById(id);
	}

	@Override
	@Transactional("systemTransactionManager")
	public Company update(Company company) {
		getHibernateTemplate().saveOrUpdate(company);
		return company;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Company> list() {
		return (List<Company>) getHibernateTemplate().find("from Company");
	}
}
