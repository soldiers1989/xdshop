package com.xdshop.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.xdshop.dal.domain.SysAccount;
import com.xdshop.service.ISysAccountService;

@Component
@WebFilter(urlPatterns="/service/*",filterName = "loginCheckFilter")
public class LoginCheckFilter implements Filter {
	private static final Logger logger = Logger.getLogger(LoginCheckFilter.class);
	/*@Autowired
	private ISysAccountService sysAccountService;*/
	
	/*@Value("${tokenKey}")
	private String tokenKey;*/

	public void destroy() {
		// TODO Auto-generated method stub
	}
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest req = (HttpServletRequest) request; 
	    HttpServletResponse res = (HttpServletResponse) response; 
	    HttpSession session = req.getSession(true);
	    
	    if(req.getMethod().equalsIgnoreCase("OPTIONS")){
	    	res.setStatus(200);
	    }else {
	        try  {
	        	final String authHeader = req.getHeader("Authorization");
	        	final Claims claims = Jwts.parser().setSigningKey("xdshop_key")
    	                .parseClaimsJws(authHeader).getBody();
	            String userid=claims.getSubject();
	            ServletContext sc = req.getSession().getServletContext();
	            XmlWebApplicationContext cxt = (XmlWebApplicationContext)WebApplicationContextUtils.getWebApplicationContext(sc);
	            ISysAccountService sysAccountService = (ISysAccountService)cxt.getBean(ISysAccountService.class);
	            SysAccount sysAccount = sysAccountService.getSysAccountByAccountId(userid);
	            request.setAttribute("account", sysAccount);
	            logger.info("当前验证用户："+sysAccount.getAccountId());
	        	filterChain.doFilter(request,response); 
	        }catch (SignatureException  e) {
	            res.setStatus(401);
	            return;
	        }catch (IllegalArgumentException e) {
	        	res.setStatus(401);
	            return;
	        }
	    }
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
