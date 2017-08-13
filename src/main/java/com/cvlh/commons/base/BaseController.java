package com.cvlh.commons.base;

import com.cvlh.commons.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2016/8/18.
 */
public abstract class BaseController {

    protected final Logger logger = LoggerFactory.getLogger(getClass());


    /**
     * ajax失败
     *
     * @param msg 失败的信息
     * @return {Object}
     */
    public Object renderError(String msg, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow_Methods", "POST,GET,OPTIONS,PUT,DELETE");
        Result result = new Result();
        result.setStatus(1);
        result.setMsg(msg);

        return result;
    }

    /**
     * ajax 成功
     *
     * @return {object}
     */
    public Object renderSuccess(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow_Methods", "POST,GET,OPTIONS,PUT,DELETE");
        Result result = new Result();
        result.setStatus(0);

        return result;
    }

    /**
     * ajax 成功
     *
     * @param msg 成功的消息
     * @return {object}
     */
    public Object renderSuccess(String msg, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow_Methods", "POST,GET,OPTIONS,PUT,DELETE");
        Result result = new Result();
        result.setStatus(0);
        result.setMsg(msg);

        return result;
    }

    /**
     * ajax 返回结果
     *
     * @param msg 消息 status(0,成功,:1，失败)
     * @return {object}
     */
    public Object render(String msg, Integer status, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow_Methods", "POST,GET,OPTIONS,PUT,DELETE");
        Result result = new Result();
        result.setStatus(status);
        result.setMsg(msg);

        return result;
    }

    /**
     * ajax 成功
     *
     * @param obj 成功的对象
     * @return {object}
     */
    public Object renderSuccess(Object obj, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow_Methods", "POST,GET,OPTIONS,PUT,DELETE");
        Result result = new Result();
        result.setMsg("OK");
        result.setStatus(0);
        result.setData(obj);

        return result;
    }

}