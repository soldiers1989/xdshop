package com.xdshop.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xdshop.util.FormCustomDateEditor;
import com.xdshop.vo.ResponseVo;




@Controller
public class BaseController 
{
	private static Logger log = Logger.getLogger(BaseController.class);  
	@ExceptionHandler
	@ResponseBody
	public ResponseVo handlerException(Exception e)
	{
		e.printStackTrace();
		log.error(e.getMessage());
		ResponseVo response=new ResponseVo();
		response.setSuccessResponse(false);
		response.setMessage(e.getMessage());
		return response;
	}
	
	public ResponseVo wrapperJson(Object obj)
	{
		ResponseVo response=new ResponseVo();
		response.setSuccessResponse(true);
		response.setData(obj);
		return response;
	}
	/**表单提交日期格式转换为本地时区**/
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z"); //yyyy-MM-dd'T'HH:mm:ssZ example
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new FormCustomDateEditor(dateFormat, false));
    }

}
