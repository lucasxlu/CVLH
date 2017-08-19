package com.cvlh.service.impl;

import com.cvlh.mapper.NlpJobMapper;
import com.cvlh.model.NlpJob;
import com.cvlh.service.NlpJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 29140 on 2017/4/11.
 */
@Service
public class NlpJobServiceImpl implements NlpJobService {

    @Autowired
    private NlpJobMapper nlpJobMapper;

    @Override
    public void batchInsertNlpJobs(List<NlpJob> nlpJobList) {
        nlpJobMapper.batchInsertNlpJobs(nlpJobList);
    }

    @Override
    public void deleteByPrimaryKey(Long id) {
        nlpJobMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<NlpJob> selectAll(String jobType) {
        return nlpJobMapper.selectAll(jobType);
    }
}
