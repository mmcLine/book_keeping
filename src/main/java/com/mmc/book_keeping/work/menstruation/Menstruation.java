package com.mmc.book_keeping.work.menstruation;

import com.mmc.book_keeping.work.base.BaseEntity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import java.time.LocalDate;

/**
 * 大姨妈记录
 */
@Data
@Entity
@Builder
@EqualsAndHashCode(callSuper = false)
public class Menstruation extends BaseEntity {

    //来大姨妈的日期
    private LocalDate mensDate;

    //月经周期
    private int cycle;

    //预计下次的日期
    private LocalDate nextMensDate;

    public Menstruation(){}

    public Menstruation(LocalDate mensDate, int cycle, LocalDate nextMensDate) {
        this.mensDate = mensDate;
        this.cycle = cycle;
        this.nextMensDate = nextMensDate;
    }
}
