package com.pujjr.gps.dal.dao;

import java.util.List;

import com.pujjr.gps.dal.domain.HisFieldComment;

public interface HisFieldCommentMapper {
    int deleteByPrimaryKey(String id);

    int insert(HisFieldComment record);

    int insertSelective(HisFieldComment record);

    HisFieldComment selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(HisFieldComment record);

    int updateByPrimaryKey(HisFieldComment record);
    
    /**
     * 获取属性备注表所有记录
     * @author tom
     * @time 2018年3月26日 下午5:20:15
     * @return
     */
    List<HisFieldComment> selectList();
}