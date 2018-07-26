package com.ldchotels.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionContext;

public class StrutsUtils {
	public static HttpSession getSession() {
		return ((HttpServletRequest) (ActionContext.getContext()
				.getValueStack().getContext().get(StrutsStatics.HTTP_REQUEST)))
				.getSession();
	}

	public static HttpServletRequest getRequest() {
		return (HttpServletRequest) (ActionContext.getContext().getValueStack()
				.getContext().get(StrutsStatics.HTTP_REQUEST));
	}

	public static HttpServletResponse getReponse() {
		return (HttpServletResponse) (ActionContext.getContext()
				.getValueStack().getContext().get(StrutsStatics.HTTP_RESPONSE));
	}
}
