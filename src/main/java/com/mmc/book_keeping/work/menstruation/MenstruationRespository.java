package com.mmc.book_keeping.work.menstruation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenstruationRespository extends JpaRepository<Menstruation,Integer> {
    @Query(value = "select * from menstruation where  familyer_pkey =?1 ORDER BY pkey DESC  limit ?2,?3",nativeQuery = true)
    List<Menstruation>  findFourRecord(Integer familyer,int start, int end);



}
