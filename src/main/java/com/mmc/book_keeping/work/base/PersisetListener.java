package com.mmc.book_keeping.work.base;





import com.mmc.book_keeping.utils.SpringContextUtil;

import javax.persistence.PrePersist;
import java.time.LocalDate;

/**
 * 操作基类的监听器，在每次新增数据库前对基类的这三个属性赋值
 */
public class PersisetListener {

    @PrePersist
    public void preInsert(Object entity) {
        //新增时赋值用户，家庭，创建时间
        BaseEntity baseEntity=(BaseEntity)entity;
        baseEntity.setUser(SpringContextUtil.getLoginUser().getUser());
        baseEntity.setFamilyer(SpringContextUtil.getLoginFamiler());
        baseEntity.setCreateTime(LocalDate.now());
    }
}
