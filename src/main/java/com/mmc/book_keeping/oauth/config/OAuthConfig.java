package com.mmc.book_keeping.oauth.config;

import com.mmc.book_keeping.oauth.api.GithubApi;
import com.mmc.book_keeping.oauth.service.GithubOAuthService;
import com.mmc.book_keeping.oauth.service.OAuthServiceDeractor;
import org.scribe.builder.ServiceBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OAuthConfig {
    
    private static final String CALLBACK_URL = "%s/oauth/%s/callback";
    
    @Value("${oAuth.github.state}") String state;
    @Value("${oAuth.github.clientId}") String githubClientId;
    @Value("${oAuth.github.clientSecret}") String githubClientSecret;
    @Value("${demo.host}") String host;
    
    @Bean
    public GithubApi githubApi(){
        return new GithubApi(state);
    }
    
    @Bean
    public OAuthServiceDeractor getGithubOAuthService(){
        return new GithubOAuthService(new ServiceBuilder()
            .provider(githubApi())
            .apiKey(githubClientId)
            .apiSecret(githubClientSecret)
            .callback(String.format(CALLBACK_URL, host, OAuthTypes.GITHUB))
            .build());
    }
    

}
