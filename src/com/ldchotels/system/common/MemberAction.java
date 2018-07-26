package com.ldchotels.system.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.ldchotels.system.bo.MemberBo;
import com.ldchotels.system.bo.CompanyBo;
import com.ldchotels.system.bo.RoleBo;
import com.ldchotels.system.model.Member;
import com.ldchotels.system.model.Company;
import com.ldchotels.system.model.Role;
import com.ldchotels.util.CryptUtils;
import com.ldchotels.util.Definition;

public class MemberAction extends ActionSupport implements Preparable, SessionAware {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private Long id; // Member ID, for Detail, Delete action
	private Member member; // For Add, Update action
	private MemberBo memberBo;
	private CompanyBo companyBo;
	private RoleBo roleBo;
	private List<Member> memberList; // For List action
	private List<Company> companyList;
	private List<Role> roleList;
	
	public MemberAction() {
		super();
	}

	/* ActionSupport */
	@Override
	public String execute() throws Exception {
		return super.execute();
	}

	/* ActionSupport */
	@Override
	public String input() throws Exception {
		return super.input();
	}

	/* ActionSupport */
	@Override
	public void validate() {
		if (member != null) {
			if (getMemberBo().findByAccount(member.getAccount()) != null) {				
				addActionError(this.getText("errors.duplicate") + member.getAccount());
			}
		}
	}
	
	/* Preparable */
	@Override
	public void prepare() throws Exception {
		if (this.memberBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.memberBo = (MemberBo) cxt.getBean("memberBo");
		}
		
		if (this.memberList == null) {
			if ((this.session != null) && (this.session.get("CurrentMember") != null)) {
				Member member = (Member) this.session.get("CurrentMember");
				if (member.getRole().getRole_code().equals(Definition.ROLE_ADMIN)) {
					this.memberList = getMemberBo().list();
				} else {
					this.memberList = getMemberBo().list(member.getCompany());
				}
			}
		}
		
		if (this.companyBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.companyBo = (CompanyBo) cxt.getBean("companyBo");
		}
		
		if (this.companyList == null) {
			this.companyList = getCompanyBo().list();
		}

		if (this.roleBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.roleBo = (RoleBo) cxt.getBean("roleBo");
		}
		
		if (this.roleList == null) {
			this.roleList = getRoleBo().list();
		}			
	}

	/* SessionAware */
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	
	public Map<String, Object> getSession(){
		   return this.session;
	}
	
	@SkipValidation
	public String add() {
		Date now = new Date();
		if (member.getCreation_date() == null){
			member.setCreation_date(now);
		}
		
		// Set password
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String en = CryptUtils.encryptString(member.getPassword(), sdf.format(member.getCreation_date()));
		member.setPassword(en);
		
		Company company = getCompanyBo().findByNo(member.getCompany().getCmp_no());
		Role role = getRoleBo().findByCode(member.getRole().getRole_code());
		member.setCompany(company);
		member.setRole(role);
		//-- member.setActive(Boolean.TRUE);
		member.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
		
		getMemberBo().add(member);
		list();
		return SUCCESS;
	}

	@SkipValidation
	public String list() {
		if ((this.session != null) && (this.session.get("CurrentMember") != null)) {
			Member member = (Member) this.session.get("CurrentMember");
			if (member.getRole().getRole_code().equals(Definition.ROLE_ADMIN)) {
				this.memberList = getMemberBo().list();
			} else {
				this.memberList = getMemberBo().list(member.getCompany());
			}
		}
		return SUCCESS;
	}

	@SkipValidation
	public String detail() {
		this.member = getMemberBo().detail(getId());
		return SUCCESS;
	}
	
	@SkipValidation
	public String update() {
		Member orgMember = getMemberBo().findById(member.getId());
		
		if (!member.getPassword().equals(orgMember.getPassword())){
			// Set password
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String en = CryptUtils.encryptString(member.getPassword(), sdf.format(member.getCreation_date()));
			orgMember.setPassword(en);
		}

		Date now = new Date(); // Update time
		Company company = getCompanyBo().findByNo(member.getCompany().getCmp_no());
		Role role = getRoleBo().findByCode(member.getRole().getRole_code());
		orgMember.setCompany(company);
		orgMember.setRole(role);
		orgMember.setActive(member.getActive()); 
		orgMember.setRemark(member.getRemark());
		orgMember.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");

		getMemberBo().update(orgMember);
		list();
		return SUCCESS;
	}

	@SkipValidation
	public String delete() {
		if (getMemberBo().findById(getId()) != null) {
			getMemberBo().delete(getId());
			list();
		} else {
			addActionError(this.getText("errors.data.not.exist") + getId());
		}
		return SUCCESS;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public MemberBo getMemberBo() {
		return this.memberBo;
	}

	public void setMemberBo(MemberBo memberBo) {
		this.memberBo = memberBo;
	}

	public List<Member> getMemberList() {
		return memberList;
	}

	public void setMemberList(List<Member> memberList) {
		this.memberList = memberList;
	}
	
	public CompanyBo getCompanyBo() {
		return companyBo;
	}

	public void setCompanyBo(CompanyBo companyBo) {
		this.companyBo = companyBo;
	}

	public RoleBo getRoleBo() {
		return roleBo;
	}

	public void setRoleBo(RoleBo roleBo) {
		this.roleBo = roleBo;
	}

	public List<Company> getCompanyList() {
		return companyList;
	}

	public void setCompanyList(List<Company> companyList) {
		this.companyList = companyList;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}
}
