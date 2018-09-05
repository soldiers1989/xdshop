package com.xdshop.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.ApiOperation;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.xdshop.api.BaseParam;
import com.xdshop.dal.domain.SysAccount;
import com.xdshop.service.ISysAccountService;
import com.xdshop.vo.PageVo;
import com.xdshop.vo.ResponseVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Controller
public class AuthController extends BaseController {
	
	@Autowired
	private ISysAccountService sysAccountService;
	
	@RequestMapping(value="/xdshop/auth",method=RequestMethod.POST)
	@ResponseBody
	public ResponseVo auth(@RequestBody SysAccount account,HttpServletResponse response,HttpSession session) throws Exception{
		SysAccount sysAccount=sysAccountService.getSysAccountByAccountId(account.getAccountId());
		if(sysAccount!=null){
			//验证密码
			String password = account.getPassword();
			String existPassord = sysAccount.getPassword();
			if(password.equals(existPassord)){
				String token=Jwts.builder().setSubject(account.getAccountId())
			            .claim("roles", account.getAccountId()).setIssuedAt(new Date())
			            .signWith(SignatureAlgorithm.HS256, "xdshop_pkey").compact();
				HashMap<String,Object> map=new HashMap<String,Object>();
				map.put("Authorization", token);
				map.put("account", sysAccount);
				response.setHeader("Authorization", token);
				return this.wrapperJson(map);
			}else{
				throw new Exception("密码错误");
			}
			
		}else{
			throw new Exception("用户不存在");
		}
	}
	
	@ApiOperation(value="列表测试")
	@RequestMapping(value="/xdshop/service/account/list",method=RequestMethod.GET)
	@ResponseBody
	public PageVo getAccountList(BaseParam baseParam,HttpServletRequest request){
		List<SysAccount> list = sysAccountService.getSysAccountList(baseParam);
		PageVo page=new PageVo();
		page.setTotalItem(((Page)list).getTotal());
		page.setData(list);
		return page;
	}
}
