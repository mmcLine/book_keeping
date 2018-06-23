package com.mmc.book_keeping.work.familyer;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "familyer")
public class Familyer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pkey;
    private String name;
    //随机数，用于判断两个用户绑定为一个家庭的验证码
    private String code;
    private Date createTime;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
