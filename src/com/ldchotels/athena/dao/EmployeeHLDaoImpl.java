package com.ldchotels.athena.dao;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.ldchotels.athena.model.Employee;

public class EmployeeHLDaoImpl extends HibernateDaoSupport implements EmployeeDao {

	private static Logger logger = Logger.getLogger(EmployeeHLDaoImpl.class.getName());
		
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Employee findById_nos(String id_nos) {
		List<Employee> employeeList = (List<Employee>) getHibernateTemplate().find("from EmployeeHL where id_nos = '" + id_nos + "'");
		return employeeList.size() > 0 ? (Employee)employeeList.get(0) : null;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Employee> list() {	
		//-- HibernateTemplate ht = getHibernateTemplate();
		//-- ht.setMaxResults(100);
		//-- logger.info("Show top 100 !");
		logger.info("EmployeeHLDaoImpl:list");
		return getHibernateTemplate().find("from EmployeeHL");
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Employee> list(Date birth_dat) {
		logger.info("Criteria : Employee birthday = [" + birth_dat + "]");
		return (List<Employee>) getHibernateTemplate().find("from EmployeeHL where birth_dat = '" + birth_dat + "'");
	}
}
