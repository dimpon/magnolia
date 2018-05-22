package com.dimpon.magnolia.repository.impl;

import com.dimpon.magnolia.repository.ServiceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
@RefreshScope
public class ServiceRepositoryImpl implements ServiceRepository {

    @Value(value = "${valueFromCommonConfig}")
    private String valueFromCommonConfig;

    @Value(value = "${valueFromServcieConfig}")
    private String valueFromServcieConfig;


    //@Cacheable(cacheNames = "pets", sync = true)
    @Override
    public List<String> getPetsList(final String name) {
        /*log.info("im in repository");
        List<String> result = new ArrayList<String>();
        result.add(name);
        return result;*/

        log.info("*** generate getPetsList:" + name);

        List<String> result = new ArrayList<String>() {{
            add("aaa");
            add("bbb");
            add("ccc");
            add(valueFromCommonConfig);
            add(valueFromServcieConfig);
            add(name);
        }};

        return result;

    }
}
