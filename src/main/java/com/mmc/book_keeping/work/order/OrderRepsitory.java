package com.mmc.book_keeping.work.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepsitory extends JpaRepository<Order,Integer>,JpaSpecificationExecutor {
    @Query(value = "select sum(amt) from trade_order where familyer_pkey=?1",nativeQuery = true)
    String getTotalAmt(Integer id);

    @Query(value = "select count(*) from trade_order where familyer_pkey=?1",nativeQuery = true)
    int getTotalCount(Integer id);

    @Deprecated
    @Query(value = "SELECT (SELECT SUM(amt) FROM trade_order WHERE trade_date between ?1 and ?2) /" +
            "(SELECT SUM(amt) FROM trade_order WHERE trade_date between ?3 and ?4 )",nativeQuery = true)
    String getRatio(LocalDate firstDay,LocalDate now,LocalDate preMonthFirstDay,LocalDate preMonthDay);

    @Query(value = "SELECT SUM(amt) FROM trade_order WHERE trade_date between ?1 and ?2 and familyer_pkey=?3",nativeQuery = true)
    String getRatio2(LocalDate firstDay,LocalDate now,Integer id);

    @Query(value = "select IFNULL(SUM(amt),0) from trade_order where trade_date = ?1 and familyer_pkey=?2",nativeQuery = true)
    double getSumByTrade_date(LocalDate date,Integer id);

    @Query(value = "select o from Order o where trade_date between  ?1 and ?2 and familyer_pkey =?3")
    List<Order> findByTrade_dateBetween(LocalDate start,LocalDate end,Integer id);

    @Query(value="SELECT DATE_FORMAT(t.trade_date,'%Y-%m') MONTH,SUM(t.amt) amtsum FROM trade_order t where t.familyer_pkey=?1 GROUP BY MONTH",nativeQuery = true)
    List<Object[]> getOrderMonth(Integer id);
}
