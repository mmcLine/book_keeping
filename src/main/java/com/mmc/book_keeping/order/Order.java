package com.mmc.book_keeping.order;

import com.mmc.book_keeping.tradeType.TradeType;
import com.mmc.book_keeping.user.User;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "trade_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @Override
    public String toString() {
        return "Order{" +
                "pkey=" + pkey +
                ", name='" + name + '\'' +
                ", amt=" + amt +
                ", trade_date=" + trade_date +
                ", user=" + user +
                ", tradeType=" + tradeType +
                ", content='" + content + '\'' +
                '}';
    }
}
