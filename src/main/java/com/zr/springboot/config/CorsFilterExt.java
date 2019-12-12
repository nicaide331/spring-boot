package com.zr.springboot.config;

import org.jboss.resteasy.plugins.interceptors.CorsFilter;
import org.jboss.resteasy.resteasy_jaxrs.i18n.Messages;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.PreMatching;

@PreMatching
public class CorsFilterExt extends CorsFilter {
	
	@Override
	protected void checkOrigin(ContainerRequestContext requestContext, String origin) {
		
		if(allowedOrigins.size() <= 0) {
			requestContext.setProperty("cors.failure", true);
			throw new ForbiddenException(Messages.MESSAGES.originNotAllowed(origin));
		}
		
		String allowedOrigin = allowedOrigins.iterator().next();
		
		if(!"*".equals(allowedOrigin) && !origin.endsWith(allowedOrigin)) {
			requestContext.setProperty("cors.failure", true);
			throw new ForbiddenException(Messages.MESSAGES.originNotAllowed(origin));
		}
	}
}
