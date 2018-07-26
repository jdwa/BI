package com.ldchotels.system.bo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.ldchotels.system.dao.MemberDao;
import com.ldchotels.system.model.Company;
import com.ldchotels.system.model.Member;
import com.ldchotels.util.CryptUtils;

public class MemberBoImpl implements MemberBo, UserDetailsService, AuthenticationProvider {

	private MemberDao memberDao;

	@Override
	public Member findById(Long id) {
		return memberDao.findById(id);
	}

	@Override
	public Member findByAccount(String account) {
		return memberDao.findByAccount(account);
	}

	//DI via Spring
	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	
	@Override
	@Transactional("systemTransactionManager")
	public Member add(Member member) {
		return memberDao.add(member);
	}

	@Override
	@Transactional("systemTransactionManager")
	public Member delete(Long id) {
		return memberDao.delete(id);
	}

	@Override
	public List<Member> list() {
		return memberDao.list();
	}

	@Override
	public List<Member> listNormal() {
		return memberDao.listNormal();
	}

	@Override
	public List<Member> listResign() {
		return memberDao.listResign();
	}

	@Override
	public List<Member> listRetire() {
		return memberDao.listRetire();
	}

	@Override
	public List<Member> listRemain() {
		return memberDao.listRemain();
	}

	@Override
	public List<Member> listReturn() {
		return memberDao.listReturn();
	}

	@Override
	public List<Member> list(Company company) {
		return memberDao.list(company);
	}

	@Override
	public List<Member> listNormal(Company company) {
		return memberDao.listNormal(company);
	}

	@Override
	public List<Member> listResign(Company company) {
		return memberDao.listResign(company);
	}

	@Override
	public List<Member> listRetire(Company company) {
		return memberDao.listRetire(company);
	}

	@Override
	public List<Member> listRemain(Company company) {
		return memberDao.listRemain(company);
	}

	@Override
	public List<Member> listReturn(Company company) {
		return memberDao.listReturn(company);
	}

	@Override
	public Member detail(Long id) {
		return memberDao.detail(id);
	}

	@Override
	@Transactional("systemTransactionManager")
	public Member update(Member member) {
		return memberDao.update(member);
	}

	@Override
	public boolean checkLogin(String account, String password) {
		Member member = findByAccount(account);
		boolean returnValue = false;
		
		if(member != null){
			// Check password
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String dn = CryptUtils.decryptString(member.getPassword(), sdf.format(member.getCreation_date()));
		
			if ((dn != null) && dn.equals(password)) 
				returnValue = true;
		}

		return returnValue;
	}

	@Override
	public Member findRemainAccount(Company company) {
		return memberDao.findRemainAccount(company);
	}

	@Override
	public Member findReturnAccount(Company company) {
		return memberDao.findReturnAccount(company);
	}
	
	/*
	 * Call by the spring security plug-in system. implement interface : UserDetailsService
	 * */
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		
		Member member = findByAccount(username);
		UserDetails user = null;
		
		if(member != null){
			Collection<GrantedAuthority> auths=new ArrayList<GrantedAuthority>();
			GrantedAuthority role_code=new SimpleGrantedAuthority(member.getRole().getRole_code());
			auths.add(role_code);
			user = new User(username, null, true, true, true, true, auths);
		}
		
		return user;
	}

	/*
	 * Call by the spring security plug-in system. implement interface : AuthenticationProvider
	 * */
	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		
        String account = authentication.getName();        
        String password = authentication.getCredentials().toString();
        UsernamePasswordAuthenticationToken token = null;

        // use the credentials to try to authenticate against the third party system        
        if (checkLogin(account, password)) {
        	Member member = findByAccount(account);
			Collection<GrantedAuthority> auths=new ArrayList<GrantedAuthority>();
			GrantedAuthority role_code=new SimpleGrantedAuthority(member.getRole().getRole_code());
			auths.add(role_code);
			token = new UsernamePasswordAuthenticationToken(account, password, auths);
        }
        
    	return token;        
	}

	@Override
    public boolean supports(Class<?> authentication) {        
		return authentication.equals(UsernamePasswordAuthenticationToken.class);    
	}

}
