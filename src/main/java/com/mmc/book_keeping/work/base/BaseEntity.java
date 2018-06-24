package com.mmc.book_keeping.work.base;

import com.mmc.book_keeping.work.familyer.Familyer;
import com.mmc.book_keeping.work.user.User;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@MappedSuperclass
@Data
@EntityListeners({PersisetListener.class})
public class BaseEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer pkey;

    @JoinColumn(foreignKey = @ForeignKey(name = "user_id", value = ConstraintMode.NO_CONSTRAINT))//关联user表的字段
    @ManyToOne
    private User user;

    @JoinColumn(foreignKey = @ForeignKey(name = "familyer_id", value = ConstraintMode.NO_CONSTRAINT))//关联family表的字段
    @ManyToOne
    private Familyer familyer;

    private LocalDate createTime;
}
