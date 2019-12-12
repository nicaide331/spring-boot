package com.zr.springboot.config;

import org.jboss.resteasy.plugins.interceptors.CorsFilter;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

/**
 * 描述
 *
 * @author nicaide
 * @date 2019年12月12日 10:10:00
 */
@Component
@Provider
public class CorsFeature implements Feature {

    @Override
    public boolean configure(FeatureContext context) {
        CorsFilter corsFilter = new CorsFilterExt();
        corsFilter.getAllowedOrigins().add("*");
        corsFilter.setAllowedMethods("POST, GET, OPTIONS, DELETE, PATCH, PUT");
        corsFilter.setCorsMaxAge(3600);
        corsFilter.setAllowedHeaders("Origin, X-Requested-With, Content-Type, Accept, Authorization, Cache-Control");
        context.register(corsFilter);
        return true;
    }
}