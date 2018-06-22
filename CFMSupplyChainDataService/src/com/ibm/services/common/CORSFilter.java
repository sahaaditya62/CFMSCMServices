package com.ibm.services.common;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

@Provider
public class CORSFilter implements ContainerResponseFilter {

	@Override
	public void filter(ContainerRequestContext reqContent, ContainerResponseContext respContent) throws IOException {
		// TODO Auto-generated method stub
		
		respContent.getHeaders().add("Access-Control-Allow-Origin", "*");
		respContent.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept");
		respContent.getHeaders().add("Access-Control-Allow-Credentials", "true");
		respContent.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
		respContent.getHeaders().add("Access-Control-Max-Age", "1209600");
	}

}
