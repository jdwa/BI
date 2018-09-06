package com.ldchotels.athena.dao;

import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.ldchotels.athena.model.Employee;
import com.ldchotels.athena.model.EmployeeHQ;

public class EmployeeHQDaoImpl extends HibernateDaoSupport implements EmployeeDao {

	private static Logger logger = Logger.getLogger(EmployeeHQDaoImpl.class.getName());
		
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Employee findById_nos(String id_nos) {
		List<Employee> employeeList = (List<Employee>) getHibernateTemplate().find("from EmployeeHQ where id_nos = '" + id_nos + "'");
		return employeeList.size() > 0 ? (Employee)employeeList.get(0) : null;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Employee> list() {	
		//-- HibernateTemplate ht = getHibernateTemplate();
		//-- ht.setMaxResults(100);
		//-- logger.info("Show top 100 !");
		logger.info("EmployeeHQDaoImpl:list");
		return getHibernateTemplate().find("from EmployeeHQ");
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Employee> employedList(Date evaluate_dat) {
		Criteria criteria = getSession().createCriteria(EmployeeHQ.class);	  
	    criteria.add(Restrictions.or(Restrictions.isNull("quit_dat"), Restrictions.gt("quit_dat", evaluate_dat)));
		logger.info("Criteria : Employee quit date is Null or after [" + evaluate_dat + "]");
		return (List<Employee>) criteria.list();
	}
}
