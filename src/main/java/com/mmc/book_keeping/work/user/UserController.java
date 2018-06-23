package com.mmc.book_keeping.work.user;

import com.mmc.book_keeping.utils.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class UserController {

    @Autowired
    private UserService userService;



    @PostMapping("/book/user/login")
    public String login(String tel, String password, HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user=userService.ckeckLogin(tel,password);
        if(user!=null){
            SpringContextUtil.getLoginUser().setUser(user);
            SpringContextUtil.getLoginUser().setFamilyer(user.getFamilyer());
            return "redirect:/book/order/getTop6";
        }else{
            request.setAttribute("message","用户不存在或密码错误！");
            return "login";
        }
   }


}
