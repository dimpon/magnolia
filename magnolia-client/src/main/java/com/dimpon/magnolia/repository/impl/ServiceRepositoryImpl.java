package com.dimpon.magnolia.repository.impl;

import com.dimpon.magnolia.repository.ServiceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class ServiceRepositoryImpl implements ServiceRepository {

    @Autowired
    @Qualifier("feignclient")
    private ServiceClient client;



    //@Cacheable("pets")
    @CachePut("pets")
    @Override
    public List<String> getPetsList(String name) {
        log.info("call service name:"+name);
        /*log.info("im in repository");
        List<String> result = new ArrayList<String>();
        result.add(name);
        return result;*/
        return client.getPetsList(name);
    }
}
