package com.ldchotels.system.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.ldchotels.system.model.Company;
import com.ldchotels.system.model.Member;
import com.ldchotels.util.Definition;

public class MemberDaoImpl extends HibernateDaoSupport implements MemberDao {

	@Override
	public Member findById(Long id) {
		return (Member) getHibernateTemplate().get(Member.class, id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Member findByAccount(String account) {
		List<Member> list = (List<Member>) getHibernateTemplate().find("from Member where account = ?", account);
		return ((list != null) && (list.size() > 0)) ? (Member) list.get(0) : null;
	}

	@Override
	@Transactional("systemTransactionManager")
	public Member add(Member member) {
		getHibernateTemplate().saveOrUpdate(member);
		return member;
	}

	@Override
	@Transactional("systemTransactionManager")
	public Member delete(Long id) {
		Member member = findById(id);
		getHibernateTemplate().delete(member);
		return member;
	}

	@Override
	public Member detail(Long id) {
		return findById(id);
	}

	@Override
	@Transactional("systemTransactionManager")
	public Member update(Member member) {
		getHibernateTemplate().saveOrUpdate(member);
		return member;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Member> list() {
		return (List<Member>) getHibernateTemplate().find("from Member");
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Member> listNormal() {
		return (List<Member>) getHibernateTemplate().find("from Member e where e.role.role_code = ?", Definition.ROLE_NORMAL);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Member> listResign() {
		return (List<Member>) getHibernateTemplate().find("from Member e where e.role.role_code = ?", Definition.ROLE_RESIGN);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Member> listRetire() {
		return (List<Member>) getHibernateTemplate().find("from Member e where e.role.role_code = ?", Definition.ROLE_RETIRE);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Member> listRemain() {
		return (List<Member>) getHibernateTemplate().find("from Member e where e.role.role_code = ?", Definition.ROLE_REMAIN);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Member> listReturn() {
		return (List<Member>) getHibernateTemplate().find("from Member e where e.role.role_code = ?", Definition.ROLE_RETURN);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Member> list(Company company) {
		return (List<Member>) getHibernateTemplate().find("from Member e where e.company = ?", company);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Member> listNormal(Company company) {
		return (List<Member>) getHibernateTemplate().find("from Member e where e.role.role_code = ? and e.company = ?", Definition.ROLE_NORMAL, company);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Member> listResign(Company company) {
		return (List<Member>) getHibernateTemplate().find("from Member e where e.role.role_code = ? and e.company = ?", Definition.ROLE_RESIGN, company);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Member> listRetire(Company company) {
		return (List<Member>) getHibernateTemplate().find("from Member e where e.role.role_code = ? and e.company = ?", Definition.ROLE_RETIRE, company);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Member> listRemain(Company company) {
		return (List<Member>) getHibernateTemplate().find("from Member e where e.role.role_code = ? and e.company = ?", Definition.ROLE_REMAIN, company);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Member> listReturn(Company company) {
		return (List<Member>) getHibernateTemplate().find("from Member e where e.role.role_code = ? and e.company = ?", Definition.ROLE_RETURN, company);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Member findRemainAccount(Company company) {
		List<Member> list = (List<Member>) getHibernateTemplate().find("from Member e where e.role.role_code = ? and e.company = ?", Definition.ROLE_REMAIN, company);
		if ((list != null) && (list.size() == 0)) {
			list = (List<Member>) getHibernateTemplate().find("from Member e where e.role.role_code = ?", Definition.ROLE_REMAIN);
		}
		return ((list != null) && (list.size() > 0)) ? (Member) list.get(0) : null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Member findReturnAccount(Company company) {
		List<Member> list = (List<Member>) getHibernateTemplate().find("from Member e where e.role.role_code = ? and e.company = ?", Definition.ROLE_RETURN, company);
		if ((list != null) && (list.size() == 0)) {
			list = (List<Member>) getHibernateTemplate().find("from Member e where e.role.role_code = ?", Definition.ROLE_RETURN);
		}
		return ((list != null) && (list.size() > 0)) ? (Member) list.get(0) : null;
	}
}
