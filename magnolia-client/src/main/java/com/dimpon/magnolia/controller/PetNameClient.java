package com.dimpon.magnolia.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Slf4j
public class PetNameClient {

    @Autowired
    private OAuth2RestTemplate oAuth2RestTemplate;

    public String getName(String name) {
        String token = ((OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getTokenValue();
        return getName(token, name);
    }

    @HystrixCommand(fallbackMethod = "fallback")
    private String getName(String token, String name) {
        oAuth2RestTemplate.getOAuth2ClientContext().setAccessToken(new DefaultOAuth2AccessToken(token));
        String url = "http://magnolia-service/petsname/" + name;
        return oAuth2RestTemplate.getForObject(url, String.class);
    }

    private String fallback(String token, String name) {
        log.info("fallback");
        return "Mumu";
    }
}
