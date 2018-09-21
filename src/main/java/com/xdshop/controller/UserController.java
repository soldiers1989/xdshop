package com.xdshop.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.xdshop.api.FetchUserParam;
import com.xdshop.api.PublishUserParam;
import com.xdshop.dal.domain.User;
import com.xdshop.dal.domain.UserShare;
import com.xdshop.service.IUserService;
import com.xdshop.vo.PageVo;
import com.xdshop.vo.ResponseVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping(value="/xdshop/service")
@Api("用户控制")
public class UserController extends BaseController {
	@Autowired
	private IUserService userServiceImpl;
	
	@ApiOperation(value="下级用户")
	@RequestMapping(value="/subuser/{publishId}/{openId}",method=RequestMethod.GET)
	@ResponseBody
	public ResponseVo getSubuser(HttpServletRequest request,@PathVariable String publishId,@PathVariable String openId) throws Exception{
		List<User> userList = userServiceImpl.getSubUser(publishId,openId);
		ResponseVo responseVo = new ResponseVo();
		responseVo.setData(userList);
		responseVo.setSuccessResponse(true);
		return responseVo;
	}
	
	@ApiOperation(value="已免费领取客户")
	@RequestMapping(value="/fetchuser",method=RequestMethod.GET)
	@ResponseBody
	public PageVo getFetchUser(HttpServletRequest request,FetchUserParam fetchUserParam) throws Exception{
		List<HashMap<String,Object>> userList = userServiceImpl.getFetchUser(fetchUserParam);
		PageVo page=new PageVo();
		page.setTotalItem(((Page)userList).getTotal());
		page.setData(userList);
		return page;
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
		ResponseVo rv = userServiceImpl.fetch(publishId, openId);
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
	
	@ApiOperation(value="获取姓名、手机号信息")
	@RequestMapping(value="/user/share/{publishId}/{openId}",method=RequestMethod.GET)
	@ResponseBody
	public ResponseVo getUserShareInfo(HttpServletRequest request,@PathVariable String publishId,@PathVariable String openId) throws Exception{
//		User user = userServiceImpl.getUserInfo(openId);
		UserShare userShare = userServiceImpl.getUserShareInfo(publishId, openId);
		ResponseVo rv = new ResponseVo();
		rv.setData(userShare);
		rv.setSuccessResponse(true);
		return rv;
	}
	
	@ApiOperation(value="获取门票领用状态")
	@RequestMapping(value="/fetchStatus/{publishId}/{openId}",method=RequestMethod.GET)
	@ResponseBody
	public ResponseVo getFetchStatus(HttpServletRequest request,@PathVariable String publishId,@PathVariable String openId) throws Exception{
		Boolean fetchStatus = userServiceImpl.getFetchStatus(publishId, openId);
		ResponseVo responseVo = new ResponseVo();
		responseVo.setData(fetchStatus);
		responseVo.setSuccessResponse(true);
		return responseVo;
	}
	
	
}
