package com.main;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.gui.GuiRevision;

/**
 * Entry point for this project.
 * 
 * <p>
 * Starts with the user interface.
 * <p>
 * 
 * @author gon
 * @since 1.1
 */
public class StartRevision {

	/**
	 * Logger
	 */
	final static Logger logger = LogManager.getLogger(StartRevision.class.getName());

	public static void main(String[] args) {
		if (logger.isDebugEnabled()) {
			logger.debug("init StartRevision");
		}
		GuiRevision.initialFrame(); 
	}

}
