package application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class StaxXMLTASKHandler  {
	static List<Task> listtask = new ArrayList<Task>();
	static int k=0;
	public static void main(final String[] args) {

		List<String> list= new ArrayList<String>(); 
		
		Task t = null;
		/*
		 * Etape 1 : récupération d'une instance de la classe "DocumentBuilderFactory"
		 */
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		try {

			/*
			 * Etape 2 : création d'un parseur
			 */
			final DocumentBuilder builder = factory.newDocumentBuilder();

			/*
			 * Etape 3 : création d'un Document
			 */
			final Document document= builder.parse(new File("./ressource/User.xml"));

			//Affiche du prologue
			System.out.println("*************PROLOGUE************");
			System.out.println("version : " + document.getXmlVersion());
			System.out.println("encodage : " + document.getXmlEncoding());		
			System.out.println("standalone : " + document.getXmlStandalone());

			/*
			 * Etape 4 : récupération de l'Element racine
			 */
			final Element racine = document.getDocumentElement();

			//Affichage de l'élément racine
			System.out.println("\n*************RACINE************");
			System.out.println(racine.getNodeName());

			/*
	
			 */
			final NodeList racineNoeuds = racine.getChildNodes();
			final int nbRacineNoeuds = racineNoeuds.getLength();
			for (int i = 0; i<nbRacineNoeuds; i++) {
				if(racineNoeuds.item(i).getNodeType() == Node.ELEMENT_NODE) {
					final Element task = (Element) racineNoeuds.item(i);
					t = new Task();

					/*
					 * Etape 7 : récupération des numéros de téléphone
					 */

					final NodeList arguments = task.getElementsByTagName("string");
					final int nbargument = arguments.getLength();

					for(int j = 0; j<nbargument; j++) {
						final Element tel = (Element) arguments.item(j);

						//Affichage du téléphone
						System.out.println( " : " + tel.getTextContent());
						list.add(tel.getTextContent());

					}

					
					

				}

			
			}int taille = list.size();	
			while(taille>0){
				t.setDescription(list.get(k));
				System.out.println(list.get(k));
				t.setPriority(list.get(k+1));
				t.setTaskCreator(list.get(k+2));
				t.setTaskMaker(list.get(k+3));
				t.setTitle(list.get(k+4));
				listtask.add(t);
				t=new Task();
				k=k+5;
				taille=taille-k;	
				
			}
			System.out.println(list);
		
			
			System.out.println(listtask);		
		}
		catch (final ParserConfigurationException e) {
			e.printStackTrace();
		}
		catch (final SAXException e) {
			e.printStackTrace();
		}
		catch (final IOException e) {
			e.printStackTrace();
		}		
	}
	
	
	public void Change_TaskMaker(String name) throws SAXException, Throwable{
		
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		final DocumentBuilder builder = factory.newDocumentBuilder();
		final Document document= builder.parse(new File("./ressource/User.xml"));
		final Element racine = document.getDocumentElement();
		final NodeList racineNoeuds = racine.getChildNodes();
		final int nbRacineNoeuds = racineNoeuds.getLength();
		for (int i = 0; i<nbRacineNoeuds; i++) {
			if(racineNoeuds.item(i).getNodeType() == Node.ELEMENT_NODE) {
				final Element task = (Element) racineNoeuds.item(i);
		final NodeList arguments = task.getElementsByTagName("string");
		final int nbarguments = arguments.getLength();

		arguments.item(4).setNodeValue(name);
	}
		}
	}

}