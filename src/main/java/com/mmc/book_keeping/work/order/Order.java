package com.mmc.book_keeping.work.order;

import com.mmc.book_keeping.work.tradeType.TradeType;
import com.mmc.book_keeping.work.user.User;
import com.mmc.book_keeping.work.familyer.Familyer;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
//@Table(name = "book_order",indexes = {@Index(name = "index_order_familyer",columnList = "familyer",unique = false)})
@Table(name = "trade_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pkey;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "decimal(8,2)",nullable = false)
    private double amt;

   @Column(nullable = false,name = "trade_date")
    private LocalDate trade_date;

    //映射多对一的关联关系
    @JoinColumn(foreignKey = @ForeignKey(name = "user_id", value = ConstraintMode.NO_CONSTRAINT))//关联user表的字段
    @ManyToOne
    private User user;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "type_id", value = ConstraintMode.NO_CONSTRAINT))
    private TradeType tradeType;

    private String content;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "familyer_id", value = ConstraintMode.NO_CONSTRAINT))
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

    public double getAmt() {
        return amt;
    }

    public void setAmt(double amt) {
        this.amt = amt;
    }

    public LocalDate getTrade_date() {
        return trade_date;
    }

    public void setTrade_date(LocalDate trade_date) {
        this.trade_date = trade_date;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public TradeType getTradeType() {
        return tradeType;
    }

    public void setTradeType(TradeType tradeType) {
        this.tradeType = tradeType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Familyer getFamilyer() {
        return familyer;
    }

    public void setFamilyer(Familyer familyer) {
        this.familyer = familyer;
    }
}
