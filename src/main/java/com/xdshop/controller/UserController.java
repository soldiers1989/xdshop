package com.xdshop.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.xdshop.api.BaseParam;
import com.xdshop.api.PublishUserParam;
import com.xdshop.api.ShareParamVo;
import com.xdshop.dal.domain.Publish;
import com.xdshop.dal.domain.SysAccount;
import com.xdshop.dal.domain.User;
import com.xdshop.service.IPublishService;
import com.xdshop.service.ISysAccountService;
import com.xdshop.service.IUserService;
import com.xdshop.vo.PageVo;
import com.xdshop.vo.PublishVo;
import com.xdshop.vo.ResponseVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Controller
@RequestMapping(value="/xdshop/service")
@Api("用户控制")
public class UserController extends BaseController {
	@Autowired
	private IUserService userServiceImpl;
	
	@ApiOperation(value="下级用户")
	@RequestMapping(value="/subuser/{openId}",method=RequestMethod.GET)
	@ResponseBody
	public ResponseVo getSubuser(HttpServletRequest request,@PathVariable String openId) throws Exception{
		List<User> userList = userServiceImpl.getSubUser(openId);
		ResponseVo responseVo = new ResponseVo();
		responseVo.setData(userList);
		responseVo.setSuccessResponse(true);
		return responseVo;
	}
	
	@ApiOperation(value="已免费领取客户")
	@RequestMapping(value="/fetchuser/{publishId}",method=RequestMethod.GET)
	@ResponseBody
	public ResponseVo getFetchUser(HttpServletRequest request,@PathVariable String publishId) throws Exception{
		List<HashMap<String,Object>> userList = userServiceImpl.getFetchUser(publishId);
		ResponseVo responseVo = new ResponseVo();
		responseVo.setData(userList);
		responseVo.setSuccessResponse(true);
		return responseVo;
	}
	
	@ApiOperation(value="活动用户列表")
	@RequestMapping(value="/publishUser/list",method=RequestMethod.GET)
	@ResponseBody
	public PageVo getPulishUser(PublishUserParam param,HttpServletRequest request) throws Exception{
		List<HashMap<String,Object>> list = userServiceImpl.getPublishUser(param);
		PageVo page=new PageVo();
		page.setTotalItem(((Page)list).getTotal());
		page.setData(list);
		return page;
	}
	
	@ApiOperation(value="取票")
	@RequestMapping(value="/fetch/{publishId}/{openId}",method=RequestMethod.POST)
	@ResponseBody
	public ResponseVo fetch(HttpServletRequest request,@PathVariable String publishId,@PathVariable String openId) throws Exception{
		userServiceImpl.fetch(publishId, openId);
		ResponseVo rv = new ResponseVo();
		rv.setSuccessResponse(true);
		return rv;
	}
	
	@ApiOperation(value="取消取票")
	@RequestMapping(value="/unfetch/{publishId}/{openId}",method=RequestMethod.POST)
	@ResponseBody
	public ResponseVo unfetch(HttpServletRequest request,@PathVariable String publishId,@PathVariable String openId) throws Exception{
		userServiceImpl.unfetch(publishId, openId);
		ResponseVo rv = new ResponseVo();
		rv.setSuccessResponse(true);
		return rv;
	}
	
	@ApiOperation(value="获取客户分享海报")
	@RequestMapping(value="/usershare/{publishId}/{openId}",method=RequestMethod.GET)
	@ResponseBody
	public ResponseVo getPosterOssUrl(HttpServletRequest request,@PathVariable String publishId,@PathVariable String openId) throws Exception{
		String posterOssUrl = userServiceImpl.getPosterOssUrl(publishId, openId);
		ResponseVo responseVo = new ResponseVo();
		responseVo.setData(posterOssUrl);
		responseVo.setSuccessResponse(true);
		return responseVo;
	}
	
	
	
}
