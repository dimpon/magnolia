package com.dimpon.magnolia.controller;

import com.dimpon.magnolia.repository.ServiceRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@Slf4j
@RefreshScope
public class ServiceController {

    @Autowired
    private ServiceRepository repo;

    @ApiOperation(value = "Interpret PDUResponse.", notes = "Use <p><b><code>{\"CANID\":1918,\"DataField\":[98,34,3,0,56,54],\"ErrorCode\":0,\"PDUIndex\":4,\"Timestamp\":458528}</code></b></p> as example value for the request body")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = List.class),
            @ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "Unauthorized")})
    @RequestMapping(value = "/petslist/{name}", method = RequestMethod.GET)

    //@HystrixCommand(fallbackMethod = "getBackup")

    //@Cacheable("pets")
    //@PreAuthorize("hasAuthority('PETS_LIST')")
    @PreAuthorize("hasAuthority('PETS_NAME')")
    public List<String> getPetsList(@ApiParam(value = "Internal Measurement ID", defaultValue = "78") @PathVariable final String name) {

        log.info("*** getPetsList:" + name);

        Random random = new Random();
        int i = random.nextInt(90) + 10;

        log.info("*** random:" + i);
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            log.error("", e);
        }
        return repo.getPetsList(name);
    }

    @PreAuthorize("hasAuthority('PETS_NAME')")
    @RequestMapping(value = "/petsname/{name}", method = RequestMethod.GET)
    public String getPetName(@PathVariable final String name) {

        String token = ((OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getTokenValue();

        return "My name is " + name;
    }

    @PreAuthorize("hasAuthority('CREATE_PET')")
    @RequestMapping(value = "/pets", method = RequestMethod.POST)
    public void createPet(@RequestParam("name") final String name) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("create pet with name " + name);
    }


    public List<String> getBackup(String name) {
        return new ArrayList<>();
    }

}
