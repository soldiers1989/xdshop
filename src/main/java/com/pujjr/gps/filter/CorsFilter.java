package com.pujjr.gps.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;

/**
 * 
 * @author wen
 *
 */
public class CorsFilter implements Filter {

	private static final String OPTIONS = "OPTIONS";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		res.setHeader("Access-Control-Allow-Origin", "*");
		res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE,PUT");
		res.setHeader("Access-Control-Allow-Headers", "X-Requested-With,accept,Content-Type, X-Codingpedia,Authorization,token,expireTime");
		res.setHeader("Access-Control-Expose-Headers", "X-My-Custom-Header, X-Another-Custom-Header");
		res.setHeader("Access-Control-Max-Age", "3600");
		if (req.getMethod().equalsIgnoreCase(OPTIONS)) {
			res.setStatus(HttpStatus.OK.value());
		} else {
			filterChain.doFilter(req, res);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO 自动生成的方法存根

	}

	@Override
	public void destroy() {
		// TODO 自动生成的方法存根

	}

}
