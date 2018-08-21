package com.pujjr.gps.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author wen
 * @date 创建时间：2017年7月10日 上午11:39:15
 *
 */
@Configuration
public class InfoConfig {

	@Value("${server.port}")
	private String port;

	@Value("${spring.profiles.active}")
	private String profile;

	@Value("${info.groupId}")
	private String groupId;

	@Value("${info.name}")
	private String title;

	@Value("${info.description}")
	private String description;

	@Value("${info.version}")
	private String version;

	/**
	 * @return groupId
	 */
	public String getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId
	 *            要设置的 groupId
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            要设置的 title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            要设置的 description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            要设置的 version
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return port
	 */
	public String getPort() {
		return port;
	}

	/**
	 * @param port
	 *            要设置的 port
	 */
	public void setPort(String port) {
		this.port = port;
	}

	/**
	 * @return profile
	 */
	public String getProfile() {
		return profile;
	}

	/**
	 * @param profile
	 *            要设置的 profile
	 */
	public void setProfile(String profile) {
		this.profile = profile;
	}

}