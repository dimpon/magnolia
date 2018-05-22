package com.dimpon.magnolia;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RefreshScope
public class WebController {


    @Value(value = "${magnolia.web.value1}")
    private String value1;

    @Autowired
    private Client client;

    @Autowired
    OAuth2ClientContext context;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {

        OAuth2AccessToken accessToken = context.getAccessToken();
        if(accessToken!=null){
            Map<String, Object> additionalInformation = accessToken.getAdditionalInformation();
            log.info(additionalInformation.toString());
        }

        return "index";
    }


    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public String index(@PathVariable(name = "name") String name, Model model) {

        log.info("*** token:" + context.getAccessToken());
        String token = ((OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getTokenValue();
        log.info("*** token:" + token);
        String pets = client.test();

        model.addAttribute("pets", pets);
        return "index";
    }
}
