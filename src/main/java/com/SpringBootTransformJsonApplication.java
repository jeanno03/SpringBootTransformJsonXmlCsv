package com;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import com.constants.MyConstant;
import com.myservices.MyService;
import com.myservices.MyServiceInterface;
import com.mysingletons.LazySingleton;

@SpringBootApplication
public class SpringBootTransformJsonApplication {
	
	static MyServiceInterface myService = new MyService();

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(SpringBootTransformJsonApplication.class);
		app.setDefaultProperties(Collections.singletonMap("server.port", "8090"));
		ApplicationContext context = app.run(args);
		
		//chargement du singleton
		//démarrage des logs + chargement du fichier de paramétrage		
		LazySingleton singleton = LazySingleton.getInstance();
		MyConstant.LOGGER.info("**********Application start ***********"); 		
		MyConstant.LOGGER.info("utilisation du port 8090");
		
		String path = MyConstant.getPropertyFileValue("repertoire");
		String file1 = MyConstant.getPropertyFileValue("file-01");
		String file2 = MyConstant.getPropertyFileValue("file-02");
		String fileIca = MyConstant.getPropertyFileValue("ica");
		String icaUpdated = MyConstant.getPropertyFileValue("ica-updated"); 
		String xml1 = MyConstant.getPropertyFileValue("xml-01"); 
		String xml2 = MyConstant.getPropertyFileValue("xml-02"); 
		
		MyConstant.LOGGER.info("Path 1 : \n" + path+file1);
		MyConstant.LOGGER.info("Path 2 : \n" + path+file2);
		MyConstant.LOGGER.info("Path ica : \n" + path+fileIca);
		
		//it works json
		myService.getConvertJsonToMapBasic(path+file1);	
		myService.getConvertJsonToMapTypeReference(path+file1);
		myService.getTestEmployeeElement(path+file2);
		myService.getConvertJsonToMapBasic(path+fileIca);	
		myService.getTestIcaElement(path+fileIca);	
		
		myService.getTestEditIcaElement(path+fileIca, path+icaUpdated);	
		
		//xml
		myService.getTestXml1(path+xml1);
		myService.getTestXml2(path+xml2);
		
		MyConstant.LOGGER.info("**********Application stop ***********"); 
		
		((ConfigurableApplicationContext)context).close();
	}

}

