package com.ibm.app.web.frmwk;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.utils.PropertyManager;

public class ConfigServlet extends HttpServlet {
	private static final Logger _LOGGER = Logger.getLogger(ConfigServlet.class
			.getName());

	/**
	 * 
	 */
	private static final long serialVersionUID = 2906341344298636909L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		Map<String, String> configParams = new HashMap<>();
		Enumeration<String> configItems = config.getInitParameterNames();
		while (configItems.hasMoreElements()) {
			String paramName = configItems.nextElement();
			configParams.put(paramName, config.getInitParameter(paramName));
		}
		loadProperties(configParams);
	}

	public static void loadProperties(Map<String, String> configParams) {
		_LOGGER.log(Level.INFO, "Loading from map config " + configParams);
		for (String key : configParams.keySet()) {
			boolean result = PropertyManager.initProperty(key,
					configParams.get(key), true);
			_LOGGER.log(Level.INFO, "Loading bunch " + key + " value "
					+ configParams.get(key) + " Load result" + result);
		}
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//super.doGet(req, resp);
		response.setContentType("application/json");
		PrintWriter printWriter = new PrintWriter(
				response.getOutputStream());
		printWriter.write("{done: true}");
		printWriter.flush();
	}

	
}
