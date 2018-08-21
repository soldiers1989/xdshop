package com.pujjr.gps.dal.dao;

import com.pujjr.gps.dal.domain.RecordSegUploadResult;

public interface RecordSegUploadResultMapper {
    int deleteByPrimaryKey(String gpsNo);

    int insert(RecordSegUploadResult record);

    int insertSelective(RecordSegUploadResult record);

    RecordSegUploadResult selectByPrimaryKey(String gpsNo);

    int updateByPrimaryKeySelective(RecordSegUploadResult record);

    int updateByPrimaryKey(RecordSegUploadResult record);
}