package com.cvlh.mapper;

import com.cvlh.model.GovReport;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GovReportMapper {
    int insert(GovReport record);

    int insertSelective(GovReport record);

    List<GovReport> selectByChairman(@Param(value = "chairman") String chairman);
}