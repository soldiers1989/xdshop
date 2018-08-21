/**
 * 
 */
package com.pujjr.gps.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pujjr.gps.controller.base.BaseController;
import com.pujjr.gps.service.OrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author wen
 * @date 创建时间：2017年6月28日 下午2:30:30
 *
 */
@RestController
@RequestMapping("/")
@Api("首页")
public class IndexController extends BaseController {

	@Autowired
	OrderService orderService;

	@GetMapping(value = "/")
	@ApiOperation(value = "首页", hidden = true)
	public void index(HttpServletResponse response) throws IOException {
		response.sendRedirect("/swagger-ui.html");
	}

	@PostMapping(value = "/index/{id}")
	@ApiOperation(value = "传值测试", hidden = true)
	public String index(@ApiParam @PathVariable("id") String id) {
		return id;
	}

}
