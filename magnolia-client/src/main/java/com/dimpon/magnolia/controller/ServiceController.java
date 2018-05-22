package com.dimpon.magnolia.controller;

import com.dimpon.magnolia.repository.ServiceRepository;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@Slf4j
@RefreshScope
public class ServiceController {

    @Value("#{${magnolia.client.codes}}")
    //private String codes;
    private Map<String, String> codes;


    @Autowired
    private IgnoreSettings settings;

    @Value(value = "${sec.value}")
    private String vaa;

    @Value(value = "${abc}")
    private String abc;


    @Autowired
    private ServiceRepository repository;

    @RequestMapping(value = "/getMyPets/{name}", method = RequestMethod.GET)
    //@HystrixCommand(fallbackMethod = "fallBack")
    public List<String> getMyPets(@PathVariable String name) {
        log.info("*** getMyPets:" + name);
        return repository.getPetsList(name);
    }


    @RequestMapping(value = "/test", method = RequestMethod.GET)
    //@HystrixCommand(fallbackMethod = "fallBack")
    public String test() {
        log.info("*** test");

        String token = ((OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getTokenValue();

        log.info("*** token:" + token);
        return "ok";
    }


    @Autowired
    private PetNameClient petClient;


    @RequestMapping(value = "/petsname/{name}", method = RequestMethod.GET)
    public String getPetName(@PathVariable final String name) {
        return petClient.getName(name);
    }


    @Autowired
    private CreatePetClient createClient;


    @RequestMapping(value = "/pets/{name}", method = RequestMethod.GET)
    public void createPet(@PathVariable("name") final String name) {
        createClient.createPets(name);
    }


    public List<String> fallBack(String name) {
        throw new IllegalArgumentException();
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    protected ResponseEntity<String> handleResourceAccessException() {
        return new ResponseEntity<String>("Not Found", HttpStatus.NOT_FOUND);
    }


    @Configuration
    @ConfigurationProperties("my")
    @Data
    public static class IgnoreSettings {
        List<String> params;

        Map<String, String> paramsik;
    }

}
