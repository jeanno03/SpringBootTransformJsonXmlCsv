package com.myservices;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.constants.MyConstant;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.core.type.TypeReference;


public class MyService implements MyServiceInterface{

	//https://www.journaldev.com/2324/jackson-json-java-parser-api-example-tutorial
	//https://www.mkyong.com/java/jackson-tree-model-example/
	//https://openclassrooms.com/en/courses/1766341-structurez-vos-donnees-avec-xml/1768662-dom-exemple-dutilisation-en-java

	@Override
	public void getConvertJsonToMapBasic(String path) {

		try {
			byte[] mapData = Files.readAllBytes(Paths.get(path));
			Map<String,String> myMap = new HashMap<String, String>();

			ObjectMapper objectMapper = new ObjectMapper();
			myMap = objectMapper.readValue(mapData, HashMap.class);
			MyConstant.LOGGER.info("ConvertJsonToMapBasic \nMap is : " + myMap);


		} catch (IOException ex) {
			MyConstant.LOGGER.info("Error : "+ ex);
		}catch(Exception ex) {
			MyConstant.LOGGER.info("Error : "+ ex);	
		}

	}
	@Override
	public void getConvertJsonToMapTypeReference(String path) {


		try {
			byte[] mapData = Files.readAllBytes(Paths.get(path));
			Map<String,String> myMap = new HashMap<String,String>();

			ObjectMapper objectMapper = new ObjectMapper();
			myMap = objectMapper.readValue(mapData, new TypeReference<HashMap<String,String>>() {});

			MyConstant.LOGGER.info("ConvertJsonToMapTypeReference \nMap using TypeReference : " + myMap);

		} catch (IOException ex) {
			MyConstant.LOGGER.info("Error : "+ ex);
		}catch(Exception ex) {
			MyConstant.LOGGER.info("Error : "+ ex);	
		}
	}

	@Override
	public void getTestEmployeeElement(String path) {
		try {
			byte[] jsonData = Files.readAllBytes(Paths.get(path));

			ObjectMapper objectMapper = new ObjectMapper();
			//create JsonNode
			JsonNode rootNode = objectMapper.readTree(jsonData);
			JsonNode idNode = rootNode.path("id");

			MyConstant.LOGGER.info("id = "+idNode.asInt());


			JsonNode phoneNosNode = rootNode.path("phoneNumbers");
			Iterator<JsonNode> elements = phoneNosNode.elements();
			while(elements.hasNext()) {
				JsonNode phone = elements.next();
				MyConstant.LOGGER.info("Phone No = " + phone.asLong());
			}

		} catch (IOException ex) {
			MyConstant.LOGGER.info("Error : "+ ex);
		}catch(Exception ex) {
			MyConstant.LOGGER.info("Error : "+ ex);
		}
	}


	@Override
	public void getTestIcaElement(String path) {

		try {

			ObjectMapper mapper = new ObjectMapper();

			JsonNode root = mapper.readTree(new File(path));

			//un noeud
			JsonNode dataNode = root.path("Data");


			// un autre noeud
			JsonNode resultNode = dataNode.path("result");
			if (resultNode.isArray()) {
				MyConstant.LOGGER.info("resultNode.isArray() is true ");
				MyConstant.LOGGER.info("resultNode.size() : " + resultNode.size());
			}
			else {
				MyConstant.LOGGER.info("resultNode.isArray() is false ");
			}
			for(JsonNode node : resultNode) {
				String u_internal_group_watch_list = node.path("u_internal_group_watch_list").asText();
				MyConstant.LOGGER.info("u_internal_group_watch_list : " + u_internal_group_watch_list);
			}

		} catch (IOException ex) {
			MyConstant.LOGGER.info("Error : "+ ex);
		}catch (Exception ex) {
			MyConstant.LOGGER.info("Error : "+ ex);
		}

	}

	@Override
	public void getTestEditIcaElement(String path, String pathUpdated) {
		try {

			ObjectMapper mapper = new ObjectMapper();

			JsonNode root = mapper.readTree(new File(path));

			//un noeud
			JsonNode dataNode = root.path("Data");

			// un autre noeud
			JsonNode resultNode = dataNode.path("result");
			if (resultNode.isArray()) {
				MyConstant.LOGGER.info("resultNode.isArray() is true ");
				MyConstant.LOGGER.info("resultNode.size() : " + resultNode.size());
			}
			else {
				MyConstant.LOGGER.info("resultNode.isArray() is false ");
			}

			MyConstant.LOGGER.info("Update *******************");
			((ObjectNode)resultNode.get(0)).put("parent","blabla");
			((ObjectNode)resultNode.get(1)).put("parent","test");

			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(pathUpdated, true))); // append mode file writer

			mapper.writeValue(out, resultNode.get(0));
			mapper.writeValue(out, resultNode.get(1));

			out.close();

		} catch (IOException ex) {
			MyConstant.LOGGER.info("Error : "+ ex);
		}catch (Exception ex) {
			MyConstant.LOGGER.info("Error : "+ ex);
		}
	}

	@Override
	public void getTestXml1(String path) {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();


		try {
			DocumentBuilder builder = factory.newDocumentBuilder();		
			Document document= builder.parse(new File(path));

			MyConstant.LOGGER.info("Path : " + path);

			MyConstant.LOGGER.info("Version de XML : " + document.getXmlVersion());		    
			MyConstant.LOGGER.info("Encodage : " + document.getXmlEncoding());	
			MyConstant.LOGGER.info("Document standalone? : " + document.getXmlStandalone());	

			Element racine = document.getDocumentElement();
			MyConstant.LOGGER.info("racine.getNodeName() : " + racine.getNodeName());

			NodeList racineNoeuds = racine.getChildNodes();

			int nbRacineNoeuds = racineNoeuds.getLength();
			MyConstant.LOGGER.info("***********************************");
			MyConstant.LOGGER.info("Parcours de tous les éléments  : ");
			MyConstant.LOGGER.info("***********************************");	

			for (int i = 0; i<nbRacineNoeuds; i++) {
				MyConstant.LOGGER.info("racineNoeuds.item("+i+").getNodeName() : " + racineNoeuds.item(i).getNodeName());

			}

			MyConstant.LOGGER.info("***********************************");
			MyConstant.LOGGER.info("Parcours de Node.ELEMENT_NODE : ");
			MyConstant.LOGGER.info("***********************************");	

			for (int i = 0; i<nbRacineNoeuds; i++) {
				if(racineNoeuds.item(i).getNodeType() == Node.ELEMENT_NODE) {
					Node node = racineNoeuds.item(i);
					System.out.println(node.getNodeName());
					MyConstant.LOGGER.info("node.getNodeName() : " + node.getNodeName());
				}				
			}

			MyConstant.LOGGER.info("***********************************");
			MyConstant.LOGGER.info("Récupération de l'attribut sexe : ");
			MyConstant.LOGGER.info("**********************************");

			for (int i = 0; i<nbRacineNoeuds; i++) {
				if(racineNoeuds.item(i).getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) racineNoeuds.item(i);
					MyConstant.LOGGER.info("element.getNodeName() : " + element.getNodeName());
					MyConstant.LOGGER.info("sexe : " + element.getAttribute("sexe"));

					/*
					 * Etape 6 : récupération du nom et du prénom
					 */

					MyConstant.LOGGER.info("***********************************");
					MyConstant.LOGGER.info("Récupération du nom et du prénom : ");
					MyConstant.LOGGER.info("**********************************");

					MyConstant.LOGGER.info("nom : " + element.getAttribute("sexe"));
					MyConstant.LOGGER.info("sexe : " + element.getAttribute("sexe"));


					Element nom = (Element) element.getElementsByTagName("nom").item(0);
					Element prenom = (Element) element.getElementsByTagName("prenom").item(0);			    
					MyConstant.LOGGER.info("(Element) element.getElementsByTagName('nom').item(0) : " + nom.getTextContent());
					MyConstant.LOGGER.info("(Element) element.getElementsByTagName('prenom').item(0) : " + prenom.getTextContent());	

					/*
					 * Etape 7 : récupération des numéros de téléphone
					 */

					MyConstant.LOGGER.info("***********************************");
					MyConstant.LOGGER.info("Récupération des numéros de téléphone : ");
					MyConstant.LOGGER.info("**********************************");

					NodeList telephones = element.getElementsByTagName("telephone");
					int nbTelephonesElements = telephones.getLength();

					for(int j = 0; j<nbTelephonesElements; j++) {
						Element telephone = (Element) telephones.item(j);

						//Affichage du téléphone					
						MyConstant.LOGGER.info(telephone.getAttribute("type") + " : " + telephone.getTextContent());

					}

				}				
			}


		}
		catch (ParserConfigurationException ex) {
			MyConstant.LOGGER.info("Error : "+ ex);
		}
		catch (SAXException ex) {
			MyConstant.LOGGER.info("Error : "+ ex);
		}
		catch (IOException ex) {
			MyConstant.LOGGER.info("Error : "+ ex);
		}
		catch(NullPointerException ex) {
			MyConstant.LOGGER.info("Error : "+ ex);			
		}
		catch (Exception ex) {
			MyConstant.LOGGER.info("Error : "+ ex);
		}

	}

	@Override
	public void getTestXml2(String path) {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		try {
			DocumentBuilder builder = factory.newDocumentBuilder();		
			Document document= builder.parse(new File(path));

			MyConstant.LOGGER.info("Path : " + path);

			MyConstant.LOGGER.info("Version de XML : " + document.getXmlVersion());		    
			MyConstant.LOGGER.info("Encodage : " + document.getXmlEncoding());	
			MyConstant.LOGGER.info("Document standalone? : " + document.getXmlStandalone());	

			Element racine = document.getDocumentElement();
			MyConstant.LOGGER.info("racine.getNodeName() : " + racine.getNodeName());

			NodeList racineNoeuds = racine.getChildNodes();			
			int nbRacineNoeuds = racineNoeuds.getLength();

			MyConstant.LOGGER.info("***********************************");
			MyConstant.LOGGER.info("Parcours de tous les éléments  : ");
			MyConstant.LOGGER.info("***********************************");	

			for (int i = 0; i<nbRacineNoeuds; i++) {
				MyConstant.LOGGER.info("racineNoeuds.item("+i+").getNodeName() : " + racineNoeuds.item(i).getNodeName());
			}

			MyConstant.LOGGER.info("***********************************");
			MyConstant.LOGGER.info("Parcours de Node.ELEMENT_NODE : ");
			MyConstant.LOGGER.info("***********************************");	

			for (int i = 0; i<nbRacineNoeuds; i++) {
				if(racineNoeuds.item(i).getNodeType() == Node.ELEMENT_NODE) {
					Node node = racineNoeuds.item(i);
					System.out.println(node.getNodeName());
					MyConstant.LOGGER.info("node.getNodeName() : " + node.getNodeName());
				}				
			}

			MyConstant.LOGGER.info("***********************************");
			MyConstant.LOGGER.info("Récupération de l'attribut contentId : ");
			MyConstant.LOGGER.info("**********************************");

			for (int i = 0; i<nbRacineNoeuds; i++) {
				if(racineNoeuds.item(i).getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) racineNoeuds.item(i);

					MyConstant.LOGGER.info("element.getNodeName() : " + element.getNodeName());
					MyConstant.LOGGER.info("contentId : " + element.getAttribute("contentId"));


					//fields des records
					NodeList racineNoeuds2 = racineNoeuds.item(i).getChildNodes(); 		

					//taille de chaque field
					int tailleField = racineNoeuds2.getLength();

					MyConstant.LOGGER.info("***********************************");
					MyConstant.LOGGER.info("Taille Field : ");
					MyConstant.LOGGER.info("**********************************");

					MyConstant.LOGGER.info("***********************************");
					MyConstant.LOGGER.info("Tous id : ");
					MyConstant.LOGGER.info("**********************************");

					MyConstant.LOGGER.info("tailleField : " + tailleField);

					try {
						for(int j=0;j<tailleField;j++) {

							if(racineNoeuds2.item(j).getNodeType() == Node.ELEMENT_NODE) {
								Element recordElement = (Element) racineNoeuds2.item(j);
								MyConstant.LOGGER.info("**********************************");
								MyConstant.LOGGER.info("Id de tous les élements de Record : " + recordElement.getAttribute("id"));
								try {
									
									//pas bon
									//exercice à faire : afficher les id de seulements les fields de record

								}catch(Exception ex) {
									MyConstant.LOGGER.info("Error : "+ ex);
								}

							}

						}

					}	
					catch(Exception ex) {
						MyConstant.LOGGER.info("Error : "+ ex);
					}
				}			

			}

		}
		catch (ParserConfigurationException ex) {
			MyConstant.LOGGER.info("Error : "+ ex);
		}
		catch (SAXException ex) {
			MyConstant.LOGGER.info("Error : "+ ex);
		}
		catch (IOException ex) {
			MyConstant.LOGGER.info("Error : "+ ex);
		}
		catch(NullPointerException ex) {
			MyConstant.LOGGER.info("Error : "+ ex);			
		}
		catch (Exception ex) {
			MyConstant.LOGGER.info("Error : "+ ex);
		}

	}
}
