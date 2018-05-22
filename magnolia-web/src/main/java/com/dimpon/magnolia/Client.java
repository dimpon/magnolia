package com.dimpon.magnolia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


//, fallback = Client.ServiceClientFallback.class
@FeignClient(name = "magnolia-client")
public interface Client {

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test();
//@PathVariable(name = "name") String name

    @Component
    public static class ServiceClientFallback implements Client {

        @Override
        public String test() {
            return "No entry!!";
        }
    }

}
