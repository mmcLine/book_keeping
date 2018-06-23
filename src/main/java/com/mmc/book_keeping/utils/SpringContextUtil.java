package com.mmc.book_keeping.utils;

import com.mmc.book_keeping.work.user.MyUser;
import com.mmc.book_keeping.work.familyer.Familyer;
import org.springframework.context.ApplicationContext;

public class SpringContextUtil {
      
    private static ApplicationContext applicationContext;
  
    //获取上下文  
    public static ApplicationContext getApplicationContext() {  
        return applicationContext;  
    }  
  
    //设置上下文  
    public static void setApplicationContext(ApplicationContext applicationContext) {  
        SpringContextUtil.applicationContext = applicationContext;  
    }  
  
    //通过名字获取上下文中的bean  
    public static Object getBean(String name){  
        return applicationContext.getBean(name);  
    }  
      
    //通过类型获取上下文中的bean  
    public static Object getBean(Class<?> requiredType){  
        return applicationContext.getBean(requiredType);  
    }

    //获取当前登录的Familer对象
    public static Familyer getLoginFamiler(){
        return applicationContext.getBean(MyUser.class).getFamilyer();
    }

    //获取当前登录的Familer对象
    public static Integer getLoginFamilerPkey(){
        return applicationContext.getBean(MyUser.class).getFamilyer().getPkey();
    }

    //获取当前登录的MyUser对象
    public static MyUser getLoginUser(){
        return applicationContext.getBean(MyUser.class);
    }
  
} 