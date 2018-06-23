package com.mmc.book_keeping.work.story;

import com.mmc.book_keeping.work.familyer.Familyer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoryRespository extends JpaRepository<Story,Integer> {
    List<Story> findAllByFamilyerEquals(Familyer id);
}
