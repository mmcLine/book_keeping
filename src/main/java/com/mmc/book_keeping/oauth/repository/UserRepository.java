package com.mmc.book_keeping.oauth.repository;

import com.mmc.book_keeping.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    
    User findByName(String name);

}
