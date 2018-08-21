package com.pujjr.gps.service.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * 基本service,要添加事务则在class上添加@Transactional(rollbackFor = Exception.class)
 * 
 * @author wen
 * @param <T>
 */
@Transactional(rollbackFor = Exception.class)
public abstract class BaseService<T> {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

}
