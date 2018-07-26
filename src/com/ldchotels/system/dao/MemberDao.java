package com.ldchotels.system.dao;

import java.util.List;

import com.ldchotels.system.model.Company;
import com.ldchotels.system.model.Member;

public interface MemberDao {
	public Member findById(Long id);
	public Member findByAccount(String account);
	public Member findRemainAccount(Company company);
	public Member findReturnAccount(Company company);	
	public Member add(Member member);
	public Member delete(Long id);
	public Member detail(Long id);
	public Member update(Member member);
	public List<Member> list();
	public List<Member> listNormal();
	public List<Member> listResign();
	public List<Member> listRetire();
	public List<Member> listRemain();
	public List<Member> listReturn();
	public List<Member> list(Company company);
	public List<Member> listNormal(Company company);
	public List<Member> listResign(Company company);
	public List<Member> listRetire(Company company);
	public List<Member> listRemain(Company company);
	public List<Member> listReturn(Company company);
}
