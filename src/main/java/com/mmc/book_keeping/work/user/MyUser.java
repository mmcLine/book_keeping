package com.mmc.book_keeping.work.user;

import com.mmc.book_keeping.work.familyer.Familyer;
import org.springframework.stereotype.Component;

/**
 * 存取登录的用户
 */
@Component
public class MyUser {

    private User user=new User();
    private Familyer familyer=new Familyer();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Familyer getFamilyer() {
        return familyer;
    }

    public void setFamilyer(Familyer familyer) {
        this.familyer = familyer;
    }
}
