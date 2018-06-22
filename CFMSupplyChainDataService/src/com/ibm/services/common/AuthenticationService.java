package com.ibm.services.common;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ibm.services.common.resource.UserDetails;
import com.ibm.utils.CommonUtil;

@Path("auth")
public class AuthenticationService {

	private  static Logger _LOGGER = Logger.getLogger(AuthenticationService.class.getName());
	@POST
	@Path("authenticate")
	@Produces(MediaType.APPLICATION_JSON)
	public Response authenticate(String postBody) {
		_LOGGER.log(Level.INFO, "Post body received for authentication :" + postBody);
		UserDetails userDetails = CommonUtil.fromJson(postBody, UserDetails.class);
		//userDetails.setPassword("ASSASA");
		//userDetails.setUserId("XXwh");
		if(userDetails.getPassword().equals("password")){
			_LOGGER.log(Level.INFO,"User role sent" +userDetails.getOrg());
			return Response.ok().entity(new ServiceResponse(Status.SUCCESS, "Success",userDetails)).build();
		}else{
			return Response.ok().entity(new ServiceResponse(Status.NOT_FOUND, "Invalid ceredentials",userDetails)).build();
		}
		
	}
	@GET
	@Path("probe")
	@Produces(MediaType.APPLICATION_JSON)
	public Response probe() {
		return Response.ok().entity(new ServiceResponse(Status.SUCCESS, "Probe success","Probe is successfull")).build();
	}
}
