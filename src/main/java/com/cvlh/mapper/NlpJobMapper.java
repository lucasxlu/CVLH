package com.cvlh.mapper;

import com.cvlh.model.NlpJob;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NlpJobMapper {
    int deleteByPrimaryKey(Long id);

    int insert(NlpJob record);

    int insertSelective(NlpJob record);

    NlpJob selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(NlpJob record);

    int updateByPrimaryKey(NlpJob record);

    void batchInsertNlpJobs(List<NlpJob> nlpJobList);

    List<NlpJob> selectAll(@Param(value = "jobType") String jobType);
}