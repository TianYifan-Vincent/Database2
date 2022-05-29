package com.shu.carsystem.Interceptors;

import com.shu.carsystem.common.Result;
import com.shu.carsystem.common.ResultEnum;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        /*
//            身份拦截器：如果没有登录，即Session中没有id信息，就强制跳转到登陆的页面。
//         */
//        HttpSession session = request.getSession();
//        if(session.getAttribute("id") == null){
//            Result result = new Result(ResultEnum.USER_NOT_EXIST.getCode(),ResultEnum.USER_NOT_EXIST.getMsg(),null);
//            response.getWriter().write("code: " + result.getCode() + ", msg: " + result.getMsg() + ", data: {" + result.getData() + "}");
//            return false;
//        }
//        return true;
    return  true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
