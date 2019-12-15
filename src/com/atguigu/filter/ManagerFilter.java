package com.atguigu.filter;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.atguigu.bean.User;

@WebFilter(urlPatterns = "/pages/manager/*",
		dispatcherTypes = {DispatcherType.REQUEST}
		//initParams = {@WebInitParam(name = "", value = "")}
)
public class ManagerFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 强转request为HttpServletRequest和response为HttpServletResponse
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		// 获取Session中的用户信息
		User user = (User) httpRequest.getSession().getAttribute("user");
		// 用户不为空，说明已登录
		if (user != null) {
			// 用户已登录，就继续访问资源
			chain.doFilter(request, response);
		} else {
			// 没有登录就重定向到登录页面
			httpResponse.sendRedirect(httpRequest.getContextPath() + "/pages/user/login.jsp");
		}

	}

	@Override
	public void destroy() {
	}

}
