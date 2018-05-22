package com.dimpon.magnolia.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
public class CacheRetriever {

    @Autowired
    private CacheManager cacheManager;

    @Cacheable("pets")
    public List<String> getPetsList(String name) {
        throw new NullPointerException("no pets with name " + name);
    }

}
