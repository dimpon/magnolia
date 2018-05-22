package com.dimpon.magnolia;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;


@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
@EnableOAuth2Sso
//@EnableOAuth2Client
@Configuration
public class WebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class,args);
    }


    @Autowired
    OAuth2ClientContext context;

   /* @Bean
    public RequestInterceptor requestTokenBearerInterceptor() {
        return requestTemplate -> {
            OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();

            requestTemplate.header("Authorization", "bearer " + details.getTokenValue());
        };
    }*/
   @Bean
   public RequestInterceptor oauth2FeignRequestInterceptor(@Autowired OAuth2ClientContext oauth2ClientContext,@Autowired  OAuth2ProtectedResourceDetails resource){
       return new OAuth2FeignRequestInterceptor(oauth2ClientContext, resource);
   }
}
