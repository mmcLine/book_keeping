package com.mmc.book_keeping.oauth.bean;

import com.mmc.book_keeping.work.user.User;

import javax.persistence.*;

@Entity
public class OAuthUser {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    @OneToOne
    private User user;

    private String oAuthType;
    private String oAuthId;

    public String getoAuthType() {
        return oAuthType;
    }
    public void setoAuthType(String oAuthType) {
        this.oAuthType = oAuthType;
    }
    public String getoAuthId() {
        return oAuthId;
    }
    public void setoAuthId(String oAuthId) {
        this.oAuthId = oAuthId;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    

}
