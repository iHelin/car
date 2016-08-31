package com.ihelin.car.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ihelin.car.db.entity.User;

public class H5AuthInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String from = request.getServletPath();
		User user = (User) request.getSession().getAttribute("weixinUser");
		if (user == null) {
			String path = request.getContextPath() + "/h5/wx_login";
			String queryStr = request.getQueryString();
			if (!StringUtils.isEmpty(queryStr)) {
				from = from + "?" + queryStr;
			}
			if (from.endsWith("/h5/wx_login"))
				response.sendRedirect(path);
			response.sendRedirect(path + "?from=" + from);
			return false;
		}
		/*if (user.getIsBind() == null || !user.getIsBind()) {
			String uri = request.getRequestURI();
			if (isIgnoredUri(uri)) {
				return true;
			} else {
				response.sendRedirect(request.getContextPath() + "/h5/user/bind_phone");
				return false;
			}
		}*/
		return true;
	}

	boolean isIgnoredUri(String uri) {
		return uri.matches(".+(?i)(bind_phone|bind_phone.do|send_verify_code)+");
	}

}
