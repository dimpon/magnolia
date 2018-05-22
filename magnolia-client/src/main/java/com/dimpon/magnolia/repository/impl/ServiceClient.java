package com.dimpon.magnolia.repository.impl;

import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "magnolia-service", fallback = ServiceClient.ServiceClientFallback.class)
@Qualifier("feignclient")
public interface ServiceClient {

    @RequestMapping(value = "/petslist/{name}", method = RequestMethod.GET)
    List<String> getPetsList(@PathVariable("name") String name);

    @Component
    public static class ServiceClientFallback implements ServiceClient {

        @Autowired
        private CacheRetriever retriever;

        @Autowired
        private CacheManager cacheManager;

        @Override
        public List<String> getPetsList(String name) {

            //cacheManager.getCache("pets").get(name, List.class);
            return retriever.getPetsList(name);
        }
    }

}
