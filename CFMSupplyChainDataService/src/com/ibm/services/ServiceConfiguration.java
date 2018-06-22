package com.ibm.services;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.ibm.services.asset.SupplyChainService;
import com.ibm.services.asset.hlfdao.SupplyChainMgmtHLFDAO;
import com.ibm.services.common.AuthenticationService;
import com.ibm.services.common.CORSFilter;

/**
 * Application service configuration
 * @author SUDDUTT1
 *
 */

@ApplicationPath("api")

public class ServiceConfiguration extends Application {

	/* (non-Javadoc)
	 * @see javax.ws.rs.core.Application#getClasses()
	 */
	@Override
	public Set<Class<?>> getClasses() {
		// TODO Auto-generated method stub
		Set<Class<?>> resources = new HashSet<Class<?>>();
		resources.add(SupplyChainService.class);
		resources.add(AuthenticationService.class);
		resources.add(CORSFilter.class);
		
		return resources;
	}

	
}
