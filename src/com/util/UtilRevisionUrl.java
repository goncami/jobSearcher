package com.util;

import java.io.IOException;
import java.text.MessageFormat;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.exception.RevisionLaboralException;

/**
 * Utility class to works with the urls.
 * 
 * @author gon
 * @since 1.1
 *
 */
public class UtilRevisionUrl {

	/**
	 * Logger
	 */
	final static Logger logger = LogManager.getLogger(UtilRevisionUrl.class.getName());

	/**
	 * Opens the url reciebed into the default web browser of the operation system.
	 *  
	 * @param strUrl
	 * @throws IOException
	 */
	public static void openUrl(String strUrl) throws IOException {
		String osName = System.getProperty("os.name");
		try {
			if (osName.startsWith("Win")) {
				Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + strUrl);
			} else if (osName.startsWith("Mac")) {
				//TODO @gon: revisar causa no funciona en mac.
				//Runtime.getRuntime().exec("open " + strUrl);
				ProcessBuilder pb = new ProcessBuilder("open", strUrl);
				pb.start();
			} else {
				System.out.println("Please open a browser and go to " + strUrl);
			}
		} catch (IOException e) {
			throw new IOException("Failed to start a browser to open the url " + strUrl, e);
		}
	}

	/**
	 * Returns the url by input receibed from user interface.
	 * 
	 * @param input
	 * @return
	 * @throws RevisionLaboralException
	 */
	public static String getUrl(String input) throws RevisionLaboralException {
		String url = null;
		switch (input) {
			case "Infojobs":
				url = UtilRevisionProperties.getInstance().getValue("url.infojobs");
				break;
			case "Linkedin":
				url = UtilRevisionProperties.getInstance().getValue("url.linkedin");
				break;
			default:
				//TODO @gon: buscar url por defecto
				url="www.google.com";
				break;
		}
		if(logger.isDebugEnabled()){
			logger.debug("input: " + input + " url: "+ url);
		}
		return url;
	}

	/**
	 * Method to format url by parameter to search.
	 * 
	 * @param web
	 * @param patter2search
	 * @return
	 */
	public static String toFormatUrl(String web, String patter2search) {
		if(logger.isDebugEnabled()){
			logger.debug("Pattern to search: " + patter2search);
		}
		String formattedUrl = MessageFormat.format(web, new Object[] { patter2search });
		if(logger.isDebugEnabled()){
			logger.debug("formattedUrl: " + formattedUrl);
		}
		return formattedUrl;
	}
}
