package com.xdshop.dal.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xdshop.api.BaseParam;
import com.xdshop.dal.domain.SysAccount;

public interface SysAccountMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysAccount record);

    int insertSelective(SysAccount record);

    SysAccount selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysAccount record);

    int updateByPrimaryKey(SysAccount record);
    
    SysAccount selectByAccountId(String accountId);
    
    List<SysAccount> selectByCreateTime(@Param("queryParam")BaseParam queryParam);
}