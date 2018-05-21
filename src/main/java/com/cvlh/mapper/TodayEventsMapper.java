package com.cvlh.mapper;

import com.cvlh.model.TodayEvents;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TodayEventsMapper {
    int insert(TodayEvents record);

    int insertSelective(TodayEvents record);

    List<TodayEvents> selectTodayEvents(@Param("month") int month, @Param("day") int day);
}