package com.ldchotels.system.common;

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
import com.ldchotels.system.bo.CompanyBo;
import com.ldchotels.system.model.Member;
import com.ldchotels.system.model.Company;

public class CompanyAction extends ActionSupport implements Preparable, SessionAware {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private Long id; // Company ID, for Detail, Delete action
	private Company company; // For Add, Update action
	private CompanyBo companyBo;
	private List<Company> companyList; // For List action
	
	public CompanyAction() {
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
		if (company != null) {
			if (getCompanyBo().findByNo(company.getCmp_no()) != null) {				
				addActionError(this.getText("errors.duplicate") + company.getCmp_no());
			}
		}
	}

	/* Preparable */
	@Override
	public void prepare() throws Exception {
		if (this.companyBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.companyBo = (CompanyBo) cxt.getBean("companyBo");
		}
		
		if (this.companyList == null) {
			this.companyList = getCompanyBo().list();
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
		if (company.getCreation_date() == null){
			company.setCreation_date(now);
		}
		
		company.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
		
		getCompanyBo().add(company);
		list();
		return SUCCESS;
	}

	@SkipValidation
	public String list() {
		this.companyList = getCompanyBo().list();
		return SUCCESS;
	}

	@SkipValidation
	public String detail() {
		this.company = getCompanyBo().detail(getId());
		return SUCCESS;
	}

	@SkipValidation
	public String update() {
		Company orgCompany = getCompanyBo().findById(company.getId());
		
		Date now = new Date(); // Update time
		if (getCompanyBo().findByNo(company.getCmp_no()) == null) {
			orgCompany.setCmp_no(company.getCmp_no());
		}
		orgCompany.setRemark(company.getRemark());
		orgCompany.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
		orgCompany.setCmp_description(company.getCmp_description());
		
		getCompanyBo().update(orgCompany);
		list();
		return SUCCESS;
	}

	@SkipValidation
	public String delete() {
		if (getCompanyBo().findById(getId()) != null) {
			getCompanyBo().delete(getId());
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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public CompanyBo getCompanyBo() {
		return this.companyBo;
	}

	public void setCompanyBo(CompanyBo companyBo) {
		this.companyBo = companyBo;
	}

	public List<Company> getCompanyList() {
		return companyList;
	}

	public void setCompanyList(List<Company> companyList) {
		this.companyList = companyList;
	}
}
