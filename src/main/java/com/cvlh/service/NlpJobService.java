package com.cvlh.service;

import com.cvlh.model.NlpJob;

import java.util.List;

/**
 * Created by 29140 on 2017/4/11.
 */

public interface NlpJobService {
    void batchInsertNlpJobs(List<NlpJob> nlpJobList);

    void deleteByPrimaryKey(Long id);

    List<NlpJob> selectAll(String jobType);
}
