package com.mmc.book_keeping.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    public static final String SESSION_LOGIN="book_user_login";

    public static final String SESSION_USER="book_user";
    public static  final  Object OBJECT=new Object();

    @PostMapping("/book/user/login")
    public String login(String tel, String password, HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user=userService.ckeckLogin(tel,password);
        if(user!=null){
            request.getSession().setAttribute(SESSION_LOGIN,OBJECT);
            request.getSession().setAttribute(SESSION_USER,user);
            return "redirect:/book/order/getTop6";
        }else{
            request.setAttribute("message","用户不存在或密码错误！");
            return "login";
        }
   }


    public static void main(String[] args) throws UnsupportedEncodingException {

    }
}
