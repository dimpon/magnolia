package com.dimpon.magnolia;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //.csrf().disable()
                .authorizeRequests()
                .antMatchers("/**").authenticated()
                .and().httpBasic()
                .and().formLogin().permitAll()
                .and().logout();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("pets")
                .password("pets")
                .authorities("PETS_LIST", "PETS_NAME")
                /*.and()
                .withUser("card")
                .password("card")
                .authorities("CARD_WRITE", "ACCOUNT_READ")
                .and()
                .withUser("client")
                .password("client")
                .authorities("CLIENT_READ", "CLIENT_WRITE", "ACCOUNT_READ", "CARD_READ")
                */
                .and()
                .withUser("crpets")
                .password("crpets")
                .authorities("CREATE_PET");
    }
}