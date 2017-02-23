package com.cvlh.controller;

import com.cvlh.commons.base.BaseController;
import com.cvlh.model.User;
import com.cvlh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by 29140 on 2017/2/23.
 */
@Controller
public class UserController extends BaseController {

    @Autowired
    public UserService userService;

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public Object showUser(HttpServletResponse httpServletResponse) {
        User user = userService.showUser();

        return user != null ? renderSuccess(user, httpServletResponse) : renderError("EORROR!!", httpServletResponse);
    }

}
