package com.ldchotels.system.dao;

import java.util.List;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import com.ldchotels.system.model.Role;

public class RoleDaoImpl extends HibernateDaoSupport implements RoleDao {

	@Override
	public Role findById(Long id) {
		return (Role) getHibernateTemplate().get(Role.class, id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Role findByCode(String code) {
		List<Role> list = (List<Role>) getHibernateTemplate().find("from Role where role_code = ?", code);
		return ((list != null) && (list.size() > 0)) ? (Role) list.get(0) : null;
	}

	@Override
	@Transactional("systemTransactionManager")
	public Role add(Role role) {
		getHibernateTemplate().saveOrUpdate(role);
		return role;
	}

	@Override
	@Transactional("systemTransactionManager")
	public Role delete(Long id) {
		Role role = findById(id);
		getHibernateTemplate().delete(role);
		return role;
	}

	@Override
	public Role detail(Long id) {
		return findById(id);
	}

	@Override
	@Transactional("systemTransactionManager")
	public Role update(Role role) {
		getHibernateTemplate().saveOrUpdate(role);
		return role;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Role> list() {
		return (List<Role>) getHibernateTemplate().find("from Role");
	}
}
