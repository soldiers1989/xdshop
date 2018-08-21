package com.pujjr.gps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pujjr.gps.service.ITableInitService;

import io.swagger.annotations.ApiOperation;

/**
 * 表格数据初始化
 * @author pujjr
 *
 */
@RestController
@RequestMapping("/operHis")
public class TableInitController {
	@Autowired
	public ITableInitService tableInitService;
	@ResponseBody
	@ApiOperation(value="初始化bean映射关系")
	@RequestMapping(value="init/hisbeanmap",method=RequestMethod.POST)
	public void hisBeanMapInit(){
		tableInitService.hisBeanMapInit();
	}
	
	@ResponseBody
	@RequestMapping(value="init/fieldcomment",method=RequestMethod.POST)
	@ApiOperation(value="初始化字段备注信息")
	public void fieldCommentInit(){
		tableInitService.hisFieldCommentInit();
	}
}
