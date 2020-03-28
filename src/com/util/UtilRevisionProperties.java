package com.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.exception.RevisionLaboralException;

/**
 * Utility class to works with the properties file.
 * 
 * @author gon
 * @since 1.1
 */
public class UtilRevisionProperties {

	/**
	 * Logger
	 */
	final static Logger logger = LogManager.getLogger(UtilRevisionProperties.class.getName());

	
	private static UtilRevisionProperties instance = null;
	private Properties properties;

	/**
	 * Initilaize the project properties.
	 * 
	 * @throws RevisionLaboralException
	 */
	protected UtilRevisionProperties() throws RevisionLaboralException{
		try (InputStream input = new FileInputStream(Constants.PATH_CONFIG_PROPERTIES)) {
			properties = new Properties();
			properties.load(input);
		} catch (IOException ex) {
			throw new RevisionLaboralException(ex.getMessage());
		}
	}

	/**
	 * Returns this instance.
	 * 
	 * @return
	 * @throws RevisionLaboralException
	 */
	public static UtilRevisionProperties getInstance() throws RevisionLaboralException {
		if(instance == null) {
			if(logger.isDebugEnabled()){
				logger.debug("new instance of UtilRevisionProperties");
			}
			instance = new UtilRevisionProperties();
		}
		return instance;
	}

	/**
	 * Returns the property value by the key receibed.
	 * 
	 * @param key
	 * @return
	 */
	public String getValue(String key) {
		return properties.getProperty(key);
	}
}
