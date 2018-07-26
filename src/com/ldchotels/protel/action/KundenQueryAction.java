package com.ldchotels.protel.action;

import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.ldchotels.protel.bo.KundenBo;
import com.ldchotels.protel.model.Kunden;

public class KundenQueryAction extends ActionSupport implements Preparable, SessionAware {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private KundenBo kundenBo;
	private List<Kunden> kundenList; // For List action
	
	public KundenQueryAction() {
		super();
	}

	/* ActionSupport */
	@Override
	public String execute() throws Exception {
		String returnValue = SUCCESS;
		this.kundenList = getKundenBo().list();
		return returnValue;
	}
	
	/* ActionSupport */
	@Override
	public String input() throws Exception {
		return super.input();
	}
	
	/* ActionSupport */
	@Override
	public void validate() {
		super.validate();
	}

	/* Preparable */
	@Override
	public void prepare() throws Exception {
		if (this.kundenBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.kundenBo = (KundenBo) cxt.getBean("kundenBo");
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

	public KundenBo getKundenBo() {
		return this.kundenBo;
	}

	public void setKundenBo(KundenBo kundenBo) {
		this.kundenBo = kundenBo;
	}

	public List<Kunden> getKundenList() {
		return kundenList;
	}

	public void setKundenList(List<Kunden> kundenList) {
		this.kundenList = kundenList;
	}
}
