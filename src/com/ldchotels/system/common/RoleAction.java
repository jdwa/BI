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
import com.ldchotels.system.bo.RoleBo;
import com.ldchotels.system.model.Member;
import com.ldchotels.system.model.Role;

public class RoleAction extends ActionSupport implements Preparable, SessionAware {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private Long id; // Role ID, for Detail, Delete action
	private Role role; // For Add, Update action
	private RoleBo roleBo;
	private List<Role> roleList; // For List action
	
	public RoleAction() {
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
		if (role != null) {
			if (getRoleBo().findByCode(role.getRole_code()) != null) {				
				addActionError(this.getText("errors.duplicate") + role.getRole_code());
			}
		}
	}
	
	/* Preparable */
	@Override
	public void prepare() throws Exception {
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
		if (role.getCreation_date() == null){
			role.setCreation_date(now);
		}
		
		role.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
		
		getRoleBo().add(role);
		list();
		return SUCCESS;
	}

	@SkipValidation
	public String list() {
		this.roleList = getRoleBo().list();
		return SUCCESS;
	}

	@SkipValidation
	public String detail() {
		this.role = getRoleBo().detail(getId());
		return SUCCESS;
	}

	@SkipValidation
	public String update() {
		Role orgRol = getRoleBo().findById(role.getId());
		
		Date now = new Date(); // Update time
		orgRol.setRemark(role.getRemark());
		orgRol.setTimestamp(((Member)this.session.get("CurrentMember")).getAccount() + ", " + getText("action.last.update") + "[" + now + "]");
		orgRol.setRole_description(role.getRole_description());

		getRoleBo().update(orgRol);
		list();
		return SUCCESS;
	}

	@SkipValidation
	public String delete() {
		if (getRoleBo().findById(getId()) != null) {
			getRoleBo().delete(getId());
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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public RoleBo getRoleBo() {
		return this.roleBo;
	}

	public void setRoleBo(RoleBo roleBo) {
		this.roleBo = roleBo;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}
}
