package com.ibm.utils;

import java.io.FileInputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 *Property manage class
 * @author SUDDUTT1
 *
 */
public class PropertyManager {

	private static final Logger _LOGGER = Logger
			.getLogger(PropertyManager.class.getName());

	private static Map<String, Properties> _propertyStore = new ConcurrentHashMap<String, Properties>();

	/**
	 * Loads a bunch from a property file
	 * 
	 * @param bunchName
	 * @param configPath
	 * @param isLoadFromClassPath
	 * @return true if load succeeds
	 */
	public static boolean initProperty(String bunchName, String configPath,
			boolean isLoadFromClassPath) {
		boolean isSuccess = false;
		Properties props = new Properties();
		try {
			_LOGGER.log(Level.INFO,
					"|PROP_MANAGER|init(): Going to load config from file "
							+ configPath);
			if (isLoadFromClassPath) {
				props.load(PropertyManager.class.getClassLoader()
						.getResourceAsStream(configPath));
			} else {
				props.load(new FileInputStream(configPath));
			}
			_propertyStore.put(bunchName, props);
			isSuccess = true;
		} catch (Throwable th) {
			_LOGGER.log(Level.SEVERE,
					"|PROP_MANAGER|initProperty():Error in loading file ", th);
			isSuccess = false;
		}
		return isSuccess;
	}

	/**
	 * Returns a property from the bunch
	 * 
	 * @param bunchName
	 * @param propName
	 * @return
	 */
	public static String getStringProperty(String bunchName, String propName) {
		if (_propertyStore.containsKey(bunchName)) {
			return _propertyStore.get(bunchName).getProperty(propName);
		} else {
			_LOGGER.log(
					Level.INFO,
					"|PROP_MANAGER|getStringProperty(): Property bunch "
							+ bunchName
							+ " not found is not stored. Have you used initProperty(\""
							+ bunchName
							+ "\",<path>) before calling this function ?");
		}
		return null;
	}

	/**
	 * Returns the entire property bunch
	 * @param bunchName String 
	 * @return Property
	 */
	public static Properties getProperties(String bunchName)
	{
		return _propertyStore.get(bunchName);
	}
}
