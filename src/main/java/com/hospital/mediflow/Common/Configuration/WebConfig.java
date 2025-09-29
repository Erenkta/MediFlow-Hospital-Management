package com.hospital.mediflow.Common.Configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * WebMvcConfigurer allows us to configure Spring MVC behaviors.
 * And I use this implementation to change default Pageable behavior.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * This Configuration will set the default Pageable beans isPaged property to false
     * @param resolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
        resolver.setFallbackPageable(Pageable.unpaged());
        resolvers.add(resolver);
    }
}
