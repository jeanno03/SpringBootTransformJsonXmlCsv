package com.constants;

import java.util.Properties;
import java.util.logging.Logger;

import com.SpringBootTransformJsonApplication;

public interface MyConstant {

	public static final String PATH_FILE_INPUT_STREAM = "/home/jeanno/Files/FileInputStream.properties";
	public static final String LOGS_PATH = "/home/jeanno/Files/";

	//Chargement de PROP via le Singleton
	public static final Properties PROP = new Properties();
	public static final Logger LOGGER = Logger.getLogger(SpringBootTransformJsonApplication.class.getName());

	//Récupération de value en fonction de key
	public static String getPropertyFileValue(String para) {
		String str = MyConstant.PROP.getProperty(para);
		return str;
	}
}
