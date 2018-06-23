package com.mmc.book_keeping.oauth.controller;

import com.mmc.book_keeping.oauth.bean.OAuthUser;
import com.mmc.book_keeping.oauth.repository.OauthUserRepository;
import com.mmc.book_keeping.oauth.repository.UserRepository;
import com.mmc.book_keeping.oauth.service.OAuthServiceDeractor;
import com.mmc.book_keeping.oauth.service.OAuthServices;
import com.mmc.book_keeping.work.user.User;
import com.mmc.book_keeping.work.user.UserService;
import com.mmc.book_keeping.utils.SpringContextUtil;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AccountController {
    
    public static final Logger logger = LoggerFactory.getLogger(AccountController.class);
    
    @Autowired
    OAuthServices oAuthServices;
    @Autowired
    OauthUserRepository oauthUserRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    /**
     * 获取其他登录方式的链接
     * @param model
     * @return
     */
    @RequestMapping(value = {"", "/login"}, method=RequestMethod.GET)
    public String showLogin(Model model){
        model.addAttribute("oAuthServices", oAuthServices.getAllOAuthServices());
        return "login";
    }

    /**
     * 回调
     * @param code
     * @param type
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/oauth/{type}/callback", method=RequestMethod.GET)
    public String claaback(@RequestParam(value = "code", required = true) String code,
                           @PathVariable(value = "type") String type,
                           HttpServletRequest request, Model model){
        OAuthServiceDeractor oAuthService = oAuthServices.getOAuthService(type);
        Token accessToken = oAuthService.getAccessToken(null, new Verifier(code));
        //从github服务器获取下来组装成的OAuthUser对象
        OAuthUser oAuthInfo = oAuthService.getOAuthUser(accessToken);
        //检测是否已经绑定过
        OAuthUser oldUser = oauthUserRepository.findByOAuthTypeAndOAuthId(oAuthInfo.getoAuthType(),
                oAuthInfo.getoAuthId());
        if(oldUser == null){
            model.addAttribute("oAuthInfo", oAuthInfo);
            return "user_bind";
        }else{
           return goIndex(request,oldUser);
        }
    }

    /**
     * 绑定原始账号和第三方账号
     * @param model
     * @param user
     * @param oAuthType
     * @param oAuthId
     * @param request
     * @return
     */
    @RequestMapping(value = "/bindUser", method=RequestMethod.POST)
    public String register(Model model, User user,
                           @RequestParam(value = "oAuthType", required = false, defaultValue = "") String oAuthType,
                           @RequestParam(value = "oAuthId", required = true, defaultValue = "") String oAuthId,
                           HttpServletRequest request){
        OAuthUser oAuthUser = new OAuthUser();
        oAuthUser.setoAuthId(oAuthId);
        oAuthUser.setoAuthType(oAuthType);
        //验证通过
        User user1=userService.ckeckLogin(user.getTel(),user.getPassword());
        if(user1!=null){
            oAuthUser.setUser(user1);
            oauthUserRepository.save(oAuthUser);
            return goIndex(request,oAuthUser);
        }else {
            model.addAttribute("message","账号密码不匹配，绑定失败！");
            model.addAttribute("oAuthServices", oAuthServices.getAllOAuthServices());
            return "login";
        }
    }
    
    @RequestMapping(value = "/success", method=RequestMethod.GET)
    @ResponseBody
    public Object success(HttpServletRequest request){
        return request.getSession().getAttribute("oauthUser");
    }

    /**
     * 登录成功页面
     */
    public String goIndex(HttpServletRequest request,OAuthUser oAuthUser){
        SpringContextUtil.getLoginUser().setUser(oAuthUser.getUser());
        SpringContextUtil.getLoginUser().setFamilyer(oAuthUser.getUser().getFamilyer());
        return "redirect:/book/order/getTop6";
    }

}
