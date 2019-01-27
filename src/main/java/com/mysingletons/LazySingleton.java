package com.mysingletons;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.constants.MyConstant;

public enum LazySingleton {
	
	INSTANCE;
	
	private LazySingleton()
	{
			getFileHandler(MyConstant.LOGGER);		
			loadPropertiesFile();
	}
	
	public static LazySingleton getInstance() {
		return INSTANCE;
	}
	
	//démarrage des logs
	private void getFileHandler(Logger logger) {

		boolean append = true;
		Date day = new Date();
		SimpleDateFormat formater = null;
		formater = new SimpleDateFormat("ddMMyy");

		try {  

			// This block configure the logger with handler and formatter  
			FileHandler fh = new FileHandler(MyConstant.LOGS_PATH+""+formater.format(day)+"-json-to-csv-app.log", append);  
			logger.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();  
			fh.setFormatter(formatter);  
			
			MyConstant.LOGGER.info("Logs start : succes");	

		} catch (SecurityException ex) { 
			//Si messsage d'erreur pas possibilité d'affichage dans les logs
			ex.printStackTrace();  
		} catch (IOException ex) {  
			ex.printStackTrace();  
		}  

	}
	
	//Chargement du fichier properties
	private void loadPropertiesFile() {
		
		FileInputStream propFile = null;
		
		try {
			
			propFile = new FileInputStream(MyConstant.PATH_FILE_INPUT_STREAM);
			MyConstant.PROP.load(propFile);
			MyConstant.LOGGER.info("load "+ MyConstant.PATH_FILE_INPUT_STREAM + " : succes");
		} catch (FileNotFoundException ex) {

			ex.printStackTrace();
		} catch (IOException ex) {

			ex.printStackTrace();
			
		}finally {
			
			if(null!=propFile) {
				try {
					propFile.close();
				}catch(Exception ex) {
					ex.printStackTrace();	
				}
			}
		}
	}

}
