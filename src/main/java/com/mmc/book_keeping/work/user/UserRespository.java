package com.mmc.book_keeping.work.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRespository extends JpaRepository<User,Integer> {

    User findByTelAndPassword(String tel,String password);

    User findByTel(String tel);
}
