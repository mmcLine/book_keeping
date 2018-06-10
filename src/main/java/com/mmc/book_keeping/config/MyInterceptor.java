package com.mmc.book_keeping.config;

import com.mmc.book_keeping.user.UserController;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.mmc.book_keeping.user.UserController.OBJECT;
import static com.mmc.book_keeping.user.UserController.SESSION_LOGIN;

public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //重定向到登录页面
        LoggerFactory.getLogger(MyInterceptor.class).info("登录拦截，判断用户是否登录");
        if(request.getSession().getAttribute(SESSION_LOGIN)==OBJECT){
            //已登录
            return true;
        }else{
            LoggerFactory.getLogger(UserController.class).warn("用户未登录，跳转登录页面");
            response.sendRedirect("/login");
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
