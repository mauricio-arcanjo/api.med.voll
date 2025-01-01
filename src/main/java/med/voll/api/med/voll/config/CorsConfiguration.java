package med.voll.api.med.voll.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
    CORS is a mechanism used to add HTTP headers that tell browsers to allow a web application to run on one origin
    and access resources from a different origin. This type of action is called a cross-origin HTTP request. In practice,
    then, it tells browsers whether or not a particular resource can be accessed.

    http://localhost:3000 would be the address of the Front-end application and .allowedMethods the methods that will
     be allowed to be executed. With this, you can consume your API without any problems from a Front-end application.
 */

@Configuration
public class CorsConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT");
    }
}