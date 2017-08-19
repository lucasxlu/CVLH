package com.cvlh.commons.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cvlh.commons.result.Result;
import com.cvlh.util.Json2Btable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    /**
     * 将字符串以输出流的形式输出到浏览器的流中。
     *
     * @param str
     * @param response
     */
    private void outString(String str, HttpServletResponse response) {
        try {
            response.setHeader("Content-type", "application/json;charset=UTF-8");
            response.getWriter().print(str);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     * 将list封装的数据对象组合以json的形式给bootstrap的表格。
     * @param j2b
     * @param response
     */
    public void outJson2Btable(Json2Btable j2b, HttpServletResponse response) {
        outString(JSON.toJSONString(j2b, SerializerFeature.DisableCircularReferenceDetect), response);
    }

}