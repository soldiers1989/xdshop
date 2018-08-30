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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.xdshop.api.BaseParam;
import com.xdshop.dal.domain.Publish;
import com.xdshop.dal.domain.SysAccount;
import com.xdshop.service.IPublishService;
import com.xdshop.service.ISysAccountService;
import com.xdshop.vo.PageVo;
import com.xdshop.vo.ResponseVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Controller
@RequestMapping(value="/xdshop/service")
@Api("发布控制")
public class PublishController extends BaseController {
	@Autowired
	private IPublishService publishServiceImpl;
	@ApiOperation(value="发布列表")
	@RequestMapping(value="/publish/list",method=RequestMethod.GET)
	@ResponseBody
	public PageVo getPublishList(BaseParam baseParam,HttpServletRequest request) throws Exception{
		List<Publish> list = publishServiceImpl.getPublishList(baseParam);
		PageVo page=new PageVo();
		page.setTotalItem(((Page)list).getTotal());
		page.setData(list);
		return page;
	}
	
	@ApiOperation(value="保存发布")
	@RequestMapping(value="/publish/save",method=RequestMethod.POST)
	@ResponseBody
	public ResponseVo savePublish(@RequestBody Publish publish,HttpServletRequest request) throws Exception{
		ResponseVo responseVo = new ResponseVo();
		publishServiceImpl.savePublish(publish);
		responseVo.setSuccessResponse(true);
		responseVo.setMessage("保存成功");
		return responseVo;
	}
}
