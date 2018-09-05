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
import com.xdshop.api.ShareParamVo;
import com.xdshop.dal.domain.Publish;
import com.xdshop.dal.domain.SysAccount;
import com.xdshop.service.IPublishService;
import com.xdshop.service.ISysAccountService;
import com.xdshop.vo.PageVo;
import com.xdshop.vo.PublishVo;
import com.xdshop.vo.ResponseVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Controller
@RequestMapping(value="/xdshop/service")
@Api("发布控制")
public class PublishController extends BaseController {
	@Autowired
	private IPublishService publishServiceImpl;
	
	@ApiOperation(value="发布初始化")
	@RequestMapping(value="/publish/init",method=RequestMethod.GET)
	@ResponseBody
	public ResponseVo initPublish(HttpServletRequest request) throws Exception{
		Publish publish = publishServiceImpl.initPublish();
		ResponseVo responseVo = new ResponseVo();
		responseVo.setData(publish);
		responseVo.setSuccessResponse(true);
		return responseVo;
	}
	
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
	public ResponseVo savePublish(@RequestBody PublishVo publishVo,HttpServletRequest request) throws Exception{
		ResponseVo responseVo = new ResponseVo();
		publishServiceImpl.savePublish(publishVo);
		responseVo.setSuccessResponse(true);
		responseVo.setMessage("保存成功");
		return responseVo;
	}
	
	@ApiOperation(value="启用发布")
	@RequestMapping(value="/publish/open",method=RequestMethod.POST)
	@ResponseBody
	public ResponseVo openPublish(@RequestBody PublishVo publishVo,HttpServletRequest request) throws Exception{
		ResponseVo responseVo = new ResponseVo();
		publishServiceImpl.openPublish(publishVo);
		responseVo.setSuccessResponse(true);
		responseVo.setMessage("保存成功");
		return responseVo;
	}
	
	
	@RequestMapping(value="/publish/uploadResource",method=RequestMethod.POST)
	@ResponseBody
	public ResponseVo uploadResource(MultipartFile file,String typeCode,String publishId) throws Exception {
		ResponseVo rsv = new ResponseVo();
		publishServiceImpl.uploadResource(file, typeCode, publishId);
		rsv.setSuccessResponse(true);
		return rsv;
	}
	
	@ApiOperation(value="获取单个发布列表")
	@RequestMapping(value="/publish/{id}",method=RequestMethod.GET)
	@ResponseBody
	public ResponseVo getPublish(@PathVariable String id,HttpServletRequest request) throws Exception{
		Publish publish = publishServiceImpl.getPublish(id);
		ResponseVo responseVo=new ResponseVo();
		responseVo.setData(publish);
		responseVo.setSuccessResponse(true);
		return responseVo;
	}
	
	@ApiOperation(value="分享发布")
	@RequestMapping(value="/publish/share",method=RequestMethod.POST)
	@ResponseBody
	public ResponseVo sharePublish(@RequestBody ShareParamVo shareParamVo,HttpServletRequest request) throws Exception{
		ResponseVo responseVo = new ResponseVo();
		String sharePicUrl = publishServiceImpl.generalSharePic(shareParamVo);
		responseVo.setSuccessResponse(true);
		responseVo.setData(sharePicUrl);
		return responseVo;
	}
}
