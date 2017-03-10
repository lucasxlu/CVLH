package com.cvlh.mapper;

import com.cvlh.model.GovReportHotwords;

public interface GovReportHotwordsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GovReportHotwords record);

    int insertSelective(GovReportHotwords record);

    GovReportHotwords selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GovReportHotwords record);

    int updateByPrimaryKey(GovReportHotwords record);
}