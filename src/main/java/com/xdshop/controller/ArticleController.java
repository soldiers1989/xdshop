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
import com.xdshop.dal.domain.Article;
import com.xdshop.dal.domain.Publish;
import com.xdshop.dal.domain.SysAccount;
import com.xdshop.service.IArticleService;
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
public class ArticleController extends BaseController {
	@Autowired
	private IArticleService articleServiceImpl;
	
	@ApiOperation(value="获取单个发布列表")
	@RequestMapping(value="/article/{publishId}",method=RequestMethod.GET)
	@ResponseBody
	public ResponseVo getArticle(@PathVariable String publishId,HttpServletRequest request) throws Exception{
		Article article = articleServiceImpl.getArticle(publishId);
		ResponseVo responseVo=new ResponseVo();
		responseVo.setData(article);
		responseVo.setSuccessResponse(true);
		return responseVo;
	}
}
