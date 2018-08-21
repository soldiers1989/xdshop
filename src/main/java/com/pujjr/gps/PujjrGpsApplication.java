package com.pujjr.gps;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.pujjr.gps.filter.CorsFilter;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 启动类
 * 
 * @author wen 如果要发布到Eureka则添加注释@EnableEurekaClient
 */
@EnableWebMvc
@MapperScan("com.pujjr.gps.dal.dao")
@EnableSwagger2
@SpringBootApplication
public class PujjrGpsApplication {

	public static void main(String[] args) {
		System.out.println("启动开始");
		try {
			SpringApplication.run(PujjrGpsApplication.class, args);
		} catch (Exception e) {
			System.out.println("启动出错:" + e.getMessage());
		}
	}

	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(new CorsFilter());
		List<String> urlPatterns = new ArrayList<String>();
		urlPatterns.add("/*");
		registrationBean.setUrlPatterns(urlPatterns);
		return registrationBean;
	}
	
}
