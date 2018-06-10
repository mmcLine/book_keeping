package com.mmc.book_keeping.user;

import com.mmc.book_keeping.utils.Des;
import com.mmc.book_keeping.utils.SHA512;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRespository userRespository;
    /**
     * 验证用户名密码
     * @param tel
     * @param password
     * @return 返回true，就是验证成功，允许登录
     */
    public User ckeckLogin(String tel,String password){
        //对前台传送过来的数据解密
        String tel2=new Des().strDec(tel,Des.KEY,null,null);
        String password2=new Des().strDec(password,Des.KEY,null,null);
        //在对密码进行加密
        String encriptPassword=SHA512.encry512(password2);
        User user=userRespository.findByTelAndPassword(tel2,encriptPassword);
        return user;
    }



}
