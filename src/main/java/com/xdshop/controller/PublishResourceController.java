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
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.xdshop.api.BaseParam;
import com.xdshop.dal.domain.Publish;
import com.xdshop.dal.domain.PublishResource;
import com.xdshop.dal.domain.SysAccount;
import com.xdshop.service.IPublishResourceService;
import com.xdshop.service.IPublishService;
import com.xdshop.service.ISysAccountService;
import com.xdshop.vo.PageVo;
import com.xdshop.vo.ResponseVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Controller
@RequestMapping(value="/xdshop/service")
@Api("发布资源控制")
public class PublishResourceController extends BaseController {
	@Autowired
	private IPublishResourceService publishResourceServiceImpl;
	
	@RequestMapping(value="/publishResource/{typeCode}/{publishId}",method=RequestMethod.GET)
	@ResponseBody
	public ResponseVo publishResource(@PathVariable("typeCode") String typeCode,@PathVariable("publishId") String publishId) throws Exception{
		ResponseVo rsv = new ResponseVo();
		PublishResource publishResource =  new PublishResource();
		publishResource.setTypeCode(typeCode);
		publishResource.setPublishId(publishId);
		List<PublishResource> publishResourceList = publishResourceServiceImpl.getPublishResourceList(publishResource);
		rsv.setSuccessResponse(true);
		rsv.setData(publishResourceList);
		return rsv;
	}
}
