package com.cvlh.service.impl;

import com.cvlh.mapper.GovReportMapper;
import com.cvlh.model.GovReport;
import com.cvlh.service.GovReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GovReportServiceImpl implements GovReportService {

    @Autowired
    private GovReportMapper govReportMapper;

    @Override
    public List<GovReport> selectByChairman(String chairman) {
        return govReportMapper.selectByChairman(chairman);
    }
}
