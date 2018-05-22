package com.dimpon.magnolia.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Component
@Slf4j
public class CreatePetClient {

    @Autowired
    @Qualifier("OAuth2CreatePet")
    private OAuth2RestTemplate oAuth2CardService;

    @HystrixCommand(fallbackMethod = "fallback")
    public void createPets(String name) {

        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("name", name);



        oAuth2CardService.postForLocation("http://magnolia-service/pets", map, String.class);
    }

    private void fallback(String name) {
      log.info("Try to create pet....");
    }

}
