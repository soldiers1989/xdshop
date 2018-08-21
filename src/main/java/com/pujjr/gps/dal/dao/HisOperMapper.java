package com.pujjr.gps.dal.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.gps.dal.domain.HisOper;
import com.pujjr.gps.po.HisOperPo;

public interface HisOperMapper {
    int deleteByPrimaryKey(String id);

    int insert(HisOper record);

    int insertSelective(HisOper record);

    HisOper selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(HisOper record);

    int updateByPrimaryKey(HisOper record);
    
    HashMap<String,Object> selectCommon(HashMap<String,Object> condition);
    
    List<HisOperPo> selectHisOperList(@Param("appId")String appId, @Param("accountId")String accountId);
}