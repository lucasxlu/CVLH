package com.cvlh.service.impl;

import com.cvlh.mapper.TodayEventsMapper;
import com.cvlh.model.TodayEvents;
import com.cvlh.service.TodayEventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodayEventsServiceImpl implements TodayEventsService {

    @Autowired
    private TodayEventsMapper todayEventsMapper;

    @Override
    public List<TodayEvents> getTodayEvents(int month, int day) {
        return todayEventsMapper.selectTodayEvents(month, day);
    }
}
