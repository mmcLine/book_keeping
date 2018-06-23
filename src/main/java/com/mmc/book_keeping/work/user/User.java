package com.mmc.book_keeping.work.user;


import com.mmc.book_keeping.work.familyer.Familyer;

import javax.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pkey;

    private String name;

    private String tel;

    private String password;

    private String headPortrait;

    @JoinColumn(foreignKey = @ForeignKey(name = "family_id", value = ConstraintMode.NO_CONSTRAINT))//关联user表的字段
    @ManyToOne
    private Familyer familyer;

    public Integer getPkey() {
        return pkey;
    }

    public void setPkey(Integer pkey) {
        this.pkey = pkey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public Familyer getFamilyer() {
        return familyer;
    }

    public void setFamilyer(Familyer familyer) {
        this.familyer = familyer;
    }
}
