package com.mmc.book_keeping.work.user;

import com.mmc.book_keeping.utils.Des;
import com.mmc.book_keeping.utils.SHA512;
import com.mmc.book_keeping.work.familyer.Familyer;
import com.mmc.book_keeping.work.familyer.FamilyerRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {
    @Autowired
    private UserRespository userRespository;

    @Autowired
    private FamilyerRespository familyererRespository;
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


    /**
     *注册用户
     */
    public void register(User user){
        //对前台传送过来的数据解密
        String tel=new Des().strDec(user.getTel(),Des.KEY,null,null);
        String password=new Des().strDec(user.getPassword(),Des.KEY,null,null);
        //在对密码进行加密
        String encriptPassword=SHA512.encry512(password);
        user.setPassword(encriptPassword);
        //创建默认的Familyer
        Familyer familyer=new Familyer();
        familyer.setName(user.getName()+"的家");
        familyer.setCode((int)(Math.random()*1000000)+"");
        familyer.setCreateTime(new Date());
        user.setFamilyer(familyer);

        familyererRespository.save(familyer);
        userRespository.save(user);
    }

}
