package com.cvlh.service;

import com.cvlh.model.TodayEvents;

import java.util.List;

public interface TodayEventsService {

    List<TodayEvents> getTodayEvents(int month, int day);
}
