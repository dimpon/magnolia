package com.dimpon.magnolia.configuration;


import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableConfigurationProperties({SwaggerConfig.AppProperties.class, SwaggerConfig.ContactProperties.class})
public class SwaggerConfig {

    @Autowired
    private AppProperties appProperties;

    @Autowired
    private ContactProperties contProperties;

    @Bean
    public Docket api() {

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.dimpon.magnolia"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title(appProperties.name)
                .description(appProperties.description)
                .contact(new Contact(contProperties.name, contProperties.organization, contProperties.email))
                .version(appProperties.version).build();
    }


    @ConfigurationProperties(prefix = "app")
    @Data
    public static class AppProperties {
        private String name, description, version;
    }

    @ConfigurationProperties(prefix = "contact")
    @Data
    public static class ContactProperties {
        private String name, organization, email;
    }

}
