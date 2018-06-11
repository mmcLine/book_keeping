package com.mmc.book_keeping.oauth.repository;

import com.mmc.book_keeping.oauth.bean.OAuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OauthUserRepository extends JpaRepository<OAuthUser, Integer> {
    
    OAuthUser findByOAuthTypeAndOAuthId(String oAuthType, String oAuthId);

}
