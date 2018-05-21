package com.cvlh.controller;

import com.cvlh.commons.base.BaseController;
import com.cvlh.model.TodayEvents;
import com.cvlh.service.TodayEventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class TodayEventsController extends BaseController {

    @Autowired
    private TodayEventsService todayEventsService;

    @RequestMapping(value = "/hzau/entertain/tdevents", method = RequestMethod.GET)
    @ResponseBody
    public Object showTodayEvents(int month, int day, HttpServletResponse httpServletResponse) {
        List<TodayEvents> todayEventsList = todayEventsService.getTodayEvents(month, day);

        return null != todayEventsList ? renderSuccess(todayEventsList, httpServletResponse) : renderError("No Data!", httpServletResponse);
    }
}
