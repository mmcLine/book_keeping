package com.mmc.book_keeping.tradeType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TradeType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer pkey;

    private String typeName;

    public Integer getPkey() {
        return pkey;
    }

    public void setPkey(Integer pkey) {
        this.pkey = pkey;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
