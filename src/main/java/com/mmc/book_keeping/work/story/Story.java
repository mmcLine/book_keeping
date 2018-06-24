package com.mmc.book_keeping.work.story;

import com.mmc.book_keeping.work.user.User;
import com.mmc.book_keeping.work.familyer.Familyer;

import javax.persistence.*;
import java.util.Date;

@Entity
//@Table(indexes = {@Index(name = "index_story_familyer",columnList = "familyer")})
public class Story {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pkey;

    private String title;

    @Column(length = 500)
    private String content;

    private String imgUrl;

    @JoinColumn(foreignKey = @ForeignKey(name = "user_id", value = ConstraintMode.NO_CONSTRAINT))//关联user表的字段
    @ManyToOne
    private User user;

    private Date createDate;

    @JoinColumn(foreignKey = @ForeignKey(name = "familyer_id", value = ConstraintMode.NO_CONSTRAINT))//关联family表的字段
    @ManyToOne
    private Familyer familyer;


    public Integer getPkey() {
        return pkey;
    }

    public void setPkey(Integer pkey) {
        this.pkey = pkey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Familyer getFamilyer() {
        return familyer;
    }

    public void setFamilyer(Familyer familyer) {
        this.familyer = familyer;
    }
}
