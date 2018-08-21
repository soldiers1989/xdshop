package com.pujjr.gps.controller.base;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.pujjr.common.result.HttpResult;
import com.pujjr.gps.controller.GpsApiController;

/**
 * @author wen
 * @date 创建时间：2017年12月5日 下午3:58:37
 *
 */
@RestControllerAdvice(assignableTypes = { GpsApiController.class })
public class GlobalExceptionHandler implements ResponseBodyAdvice<Object> {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public HttpResult<String> jsonErrorHandler(HttpServletRequest req, Exception e) throws Exception {
		logger.error(e.getMessage(), e);
		HttpResult<String> httpResult = new HttpResult<>();
		return httpResult.fail(e);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean supports(MethodParameter returnType, Class converterType) {
		// 不拦截
		return true;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
		HttpResult<Object> httpResult = new HttpResult<>();
		return httpResult.success(body);
	}
}
