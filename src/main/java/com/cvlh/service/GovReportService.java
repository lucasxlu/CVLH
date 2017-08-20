package com.cvlh.service;

import com.cvlh.model.GovReport;

import java.util.List;

public interface GovReportService {

    List<GovReport> selectByChairman(String chairman);
}
