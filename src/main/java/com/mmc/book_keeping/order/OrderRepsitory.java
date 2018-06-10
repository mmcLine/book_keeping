package com.mmc.book_keeping.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepsitory extends JpaRepository<Order,Integer>,JpaSpecificationExecutor {
    @Query(value = "select sum(amt) from trade_order",nativeQuery = true)
    String getTotalAmt();

    @Query(value = "select count(*) from trade_order",nativeQuery = true)
    int getTotalCount();

    @Deprecated
    @Query(value = "SELECT (SELECT SUM(amt) FROM trade_order WHERE trade_date between ?1 and ?2) /" +
            "(SELECT SUM(amt) FROM trade_order WHERE trade_date between ?3 and ?4 )",nativeQuery = true)
    String getRatio(LocalDate firstDay,LocalDate now,LocalDate preMonthFirstDay,LocalDate preMonthDay);

    @Query(value = "SELECT SUM(amt) FROM trade_order WHERE trade_date between ?1 and ?2",nativeQuery = true)
    String getRatio2(LocalDate firstDay,LocalDate now);

    @Query(value = "select IFNULL(SUM(amt),0) from trade_order where trade_date = ?1",nativeQuery = true)
    double getSumByTrade_date(LocalDate date);

    @Query(value = "select o from Order o where trade_date between  ?1 and ?2")
    List<Order> findByTrade_dateBetween(LocalDate start,LocalDate end);

    @Query(value="SELECT DATE_FORMAT(t.trade_date,'%Y-%m') MONTH,SUM(t.amt) amtsum FROM trade_order t GROUP BY MONTH",nativeQuery = true)
    List<Object[]> getOrderMonth();
}
