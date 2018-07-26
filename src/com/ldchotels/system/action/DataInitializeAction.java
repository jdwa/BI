package com.ldchotels.system.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ldchotels.system.bo.CompanyBo;
import com.ldchotels.system.bo.MemberBo;
import com.ldchotels.system.bo.RoleBo;
import com.ldchotels.system.model.Company;
import com.ldchotels.system.model.Member;
import com.ldchotels.system.model.Role;
import com.ldchotels.util.CryptUtils;
import com.ldchotels.util.Definition;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class DataInitializeAction extends ActionSupport implements Preparable, SessionAware {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private CompanyBo companyBo;
	private MemberBo memberBo;
	private RoleBo roleBo;
	private String account;
	private String password;
	
	public DataInitializeAction() {
		super();
	}

	/* ActionSupport */
	@Override
	public String execute() throws Exception {
		String returnValue = Action.LOGIN;
		Date now = new Date();
		
		if ("bi.admin".equals(getAccount()) && "bi.admin".equals(getPassword())) {
			
			// 公司資料初始化
			if (companyBo.findByNo(Definition.HQ_NO) == null) {
				Company cmp = new Company();
				cmp.setCmp_no(Definition.HQ_NO);
				cmp.setCmp_description(getText("company.hq"));
				cmp.setRemark(getText("company.hq"));
				cmp.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				cmp.setCreation_date(now);
				companyBo.add(cmp);
			}
			
			if (companyBo.findByNo(Definition.ZH_NO) == null) {
				Company cmp = new Company();
				cmp.setCmp_no(Definition.ZH_NO);
				cmp.setCmp_description(getText("company.zh"));
				cmp.setRemark(getText("company.zh"));
				cmp.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				cmp.setCreation_date(now);
				companyBo.add(cmp);
			}
	
			if (companyBo.findByNo(Definition.SML_NO) == null) {
				Company cmp = new Company();
				cmp.setCmp_no(Definition.SML_NO);
				cmp.setCmp_description(getText("company.sml"));
				cmp.setRemark(getText("company.sml"));
				cmp.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				cmp.setCreation_date(now);
				companyBo.add(cmp);
			}
			
			if (companyBo.findByNo(Definition.HL_NO) == null) {
				Company cmp = new Company();
				cmp.setCmp_no(Definition.HL_NO);
				cmp.setCmp_description(getText("company.hl"));
				cmp.setRemark(getText("company.hl"));
				cmp.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				cmp.setCreation_date(now);
				companyBo.add(cmp);
			}
	
			if (companyBo.findByNo(Definition.KH_NO) == null) {
				Company cmp = new Company();
				cmp.setCmp_no(Definition.KH_NO);
				cmp.setCmp_description(getText("company.kh"));
				cmp.setRemark(getText("company.kh"));
				cmp.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				cmp.setCreation_date(now);
				companyBo.add(cmp);
			}
	
			if (companyBo.findByNo(Definition.CL_NO) == null) {
				Company cmp = new Company();
				cmp.setCmp_no(Definition.CL_NO);
				cmp.setCmp_description(getText("company.cl"));
				cmp.setRemark(getText("company.cl"));
				cmp.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				cmp.setCreation_date(now);
				companyBo.add(cmp);
			}
	
			if (companyBo.findByNo(Definition.CY_NO) == null) {
				Company cmp = new Company();
				cmp.setCmp_no(Definition.CY_NO);
				cmp.setCmp_description(getText("company.cy"));
				cmp.setRemark(getText("company.cy"));
				cmp.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				cmp.setCreation_date(now);
				companyBo.add(cmp);
			}
			
			if (companyBo.findByNo(Definition.TY_NO) == null) {
				Company cmp = new Company();
				cmp.setCmp_no(Definition.TY_NO);
				cmp.setCmp_description(getText("company.ty"));
				cmp.setRemark(getText("company.ty"));
				cmp.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				cmp.setCreation_date(now);
				companyBo.add(cmp);
			}
	
			if (companyBo.findByNo(Definition.SJ_NO) == null) {
				Company cmp = new Company();
				cmp.setCmp_no(Definition.SJ_NO);
				cmp.setCmp_description(getText("company.sj"));
				cmp.setRemark(getText("company.sj"));
				cmp.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				cmp.setCreation_date(now);
				companyBo.add(cmp);
			}
			
			if (companyBo.findByNo(Definition.PDC_NO) == null) {
				Company cmp = new Company();
				cmp.setCmp_no(Definition.PDC_NO);
				cmp.setCmp_description(getText("company.pdc"));
				cmp.setRemark(getText("company.pdc"));
				cmp.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				cmp.setCreation_date(now);
				companyBo.add(cmp);
			}
	
			// 職務資料初始化
			if (roleBo.findByCode(Definition.ROLE_ADMIN) == null) {
				Role role = new Role();
				role.setRole_code(Definition.ROLE_ADMIN);
				role.setRole_description(getText("role.admin"));
				role.setRemark(getText("role.admin"));
				role.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				role.setCreation_date(now);
				roleBo.add(role);
			}
				
			if (roleBo.findByCode(Definition.ROLE_NORMAL) == null) {
				Role role = new Role();
				role.setRole_code(Definition.ROLE_NORMAL);
				role.setRole_description(getText("role.normal"));
				role.setRemark(getText("role.normal"));
				role.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				role.setCreation_date(now);
				roleBo.add(role);
			}
						
			// 帳號資料初始化
			if (memberBo.findByAccount("bi.admin") == null) {
				Member member = new Member();
				// Set password, default password : "bi.admin"
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String en = CryptUtils.encryptString("bi.admin", sdf.format(now));
				member.setAccount("bi.admin");
				member.setPassword(en);
				member.setActive(Boolean.TRUE);
				member.setCompany(companyBo.findByNo(Definition.HQ_NO));
				member.setRole(roleBo.findByCode(Definition.ROLE_ADMIN));
				member.setRemark(roleBo.findByCode(Definition.ROLE_ADMIN).getRole_description());
				member.setTimestamp(getText("action.system.defaule") + ", " + getText("action.last.update") + "[" + now + "]");
				member.setCreation_date(now);
				memberBo.add(member);
				returnValue = SUCCESS;
			}		
		}
		return returnValue;
	}

	/* ActionSupport */
	@Override
	public String input() throws Exception {
		return super.input();
	}
	
	/* ActionSupport */
	@Override
	public void validate(){
		// Check initial member account 
		if(memberBo.findByAccount("bi.admin") != null){
			addActionError(getText("action.system.admin.already.exist"));
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
		
		if (this.memberBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.memberBo = (MemberBo) cxt.getBean("memberBo");
		}	
		
		if (this.roleBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.roleBo = (RoleBo) cxt.getBean("roleBo");
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

	public CompanyBo getCompanyBo() {
		return companyBo;
	}

	public void setCompanyBo(CompanyBo companyBo) {
		this.companyBo = companyBo;
	}

	public MemberBo getMemberBo() {
		return memberBo;
	}

	public void setMemberBo(MemberBo memberBo) {
		this.memberBo = memberBo;
	}
	
	public RoleBo getRoleBo() {
		return roleBo;
	}

	public void setRoleBo(RoleBo roleBo) {
		this.roleBo = roleBo;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
