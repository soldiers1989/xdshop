package com.pujjr.gps.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author wen
 * @date 创建时间：2017年7月10日 上午11:39:15
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Autowired
	InfoConfig info;

	@Bean
	public Docket createRestApi() {
		Docket docket = new Docket(DocumentationType.SWAGGER_2);
		docket.apiInfo(apiInfo());
		docket.select().apis(RequestHandlerSelectors.basePackage(info.getGroupId())).paths(PathSelectors.any()).build();
		return docket;
	}

	private ApiInfo apiInfo() {
		ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();
		apiInfoBuilder.title(info.getTitle() + "(" + info.getProfile() + ")");
		apiInfoBuilder.description(info.getDescription());
		apiInfoBuilder.version(info.getVersion());
		apiInfoBuilder.build();
		return apiInfoBuilder.build();
	}

}