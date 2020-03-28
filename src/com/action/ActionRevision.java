package com.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.exception.RevisionLaboralException;
import com.util.UtilRevisionCsv;
import com.util.UtilRevisionUrl;

/**
 * Provides actions to the project.
 * 
 * @author gon
 * @since 1.1
 *
 */
public class ActionRevision {

	/**
	 * Logger
	 */
	final static Logger logger = LogManager.getLogger(ActionRevision.class.getName());

	/**
	 * Receibe a web url, format it with contento of csv file and call method that open it.
	 * 
	 * @param web
	 * @throws RevisionLaboralException
	 */
	public static void executeRevision(String web) throws RevisionLaboralException {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
			
			if(logger.isDebugEnabled()){
				logger.debug("START " + formatter.format(new Date(System.currentTimeMillis())));
			}
			for (String patter2search : UtilRevisionCsv.readCsv("listado.csv")) {
				//Time to wait before opens the url
				TimeUnit.SECONDS.sleep(3L);
				
				String formattedUrl = UtilRevisionUrl.toFormatUrl(web, patter2search);
				
				UtilRevisionUrl.openUrl(formattedUrl);
				
				//Time to wait after opens the url
				TimeUnit.SECONDS.sleep(3L);
			}
			
			if(logger.isDebugEnabled()){
				logger.debug("END " + formatter.format(new Date(System.currentTimeMillis())));
			}
			
		} catch (InterruptedException e) {
			throw new RevisionLaboralException(e.getMessage());
		} catch (IOException e) {
			throw new RevisionLaboralException(e.getMessage());
		}
	}

}
