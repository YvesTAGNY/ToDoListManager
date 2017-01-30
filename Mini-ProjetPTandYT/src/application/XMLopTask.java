package application;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLopTask {


	private String filename;
	public XMLopTask(String filename) {
		this.filename = filename;
	}
	
	

	public String addTask(String title, String description, String priority, int state , String taskCreator, String taskMaker) {
		
		
		String statestring =Integer.toString(state) ;
		final Document document = openFile();
		
		final Element racine = document.getDocumentElement();
		
		//création d'un nouvelle tache
	    final Element task = document.createElement("Task");
	    racine.appendChild(task);
			
	    //Ajout des infos
	    final Element nodedescription = document.createElement("description");
	    nodedescription.appendChild(document.createTextNode(description));
			
	    final Element nodepriority = document.createElement("priority");
	    nodepriority.appendChild(document.createTextNode(priority));
		
		 final Element nodestate = document.createElement("state");
	    nodestate.appendChild(document.createTextNode(statestring));
		
		 final Element nodetaskCreator = document.createElement("taskCreator");
	    nodetaskCreator.appendChild(document.createTextNode(taskCreator));
		
		 final Element nodetaskMaker = document.createElement("taskMaker");
	    nodetaskMaker.appendChild(document.createTextNode(taskMaker));
		
		 final Element nodetitle = document.createElement("title");
	    nodetitle.appendChild(document.createTextNode(title));
		
	    task.appendChild(nodedescription);
	    task.appendChild(nodepriority);
		
	    task.appendChild(nodestate);
	    task.appendChild(nodetaskCreator);
		
	    task.appendChild(nodetaskMaker);
	    task.appendChild(nodetitle);
	    
		saveDocument(document); //sauvegarde 
		return "nouvelle tâche ajouté";
	}
	
	
	public String removeTask( String title, String taskCreator) {
		final Document document = openFile();
		
		final Element racine = document.getDocumentElement();
		
		NodeList clientNodes = racine.getChildNodes();
		
		for(int i = 0; i < clientNodes.getLength(); i++)
		{
			if(clientNodes.item(i).getNodeType() == Node.ELEMENT_NODE)
			{
				Element el = (Element)clientNodes.item(i);
				if(el.getElementsByTagName("title").item(0).getTextContent().equals(title)  && el.getElementsByTagName("taskCreator").item(0).getTextContent().equals(taskCreator))
				{
					racine.removeChild(el);
					saveDocument(document);
					return "Task " + title + " supprimé" + taskCreator;
				}
			}
		}
		return "Cette tache n'existe pas";
	}
	
	public String finishTask(String title, String description, String priority, int state , String taskCreator, String taskMaker,String date) {
		final Document document = openFile();
		
		final Element racine = document.getDocumentElement();
		
		NodeList clientNodes = racine.getChildNodes();
		
		for(int i = 0; i < clientNodes.getLength(); i++)
		{
			if(clientNodes.item(i).getNodeType() == Node.ELEMENT_NODE)
			{
				Element el = (Element)clientNodes.item(i);
				if(el.getElementsByTagName("description").item(0).getTextContent().equals(description) && el.getElementsByTagName("priority").item(0).getTextContent().equals(priority) && el.getElementsByTagName("state").item(0).getTextContent().equals(state) && el.getElementsByTagName("taskMaker").item(0).getTextContent().equals(taskMaker) && el.getElementsByTagName("taskCreator").item(0).getTextContent().equals(taskCreator) && el.getElementsByTagName("title").item(0).getTextContent().equals(title))
				{
						 final Element nodedate = document.createElement("date");
							nodedate.appendChild(document.createTextNode(date));
					
					return "date  " + title + " ajouté" ;
				}
			}
		}
		return "ajout date de fin";
	}
		
		
		public String AttribuateTask( String title, String taskCreator, String user) {
		
			final Document document = openFile();
		
		final Element racine = document.getDocumentElement();
		
		NodeList clientNodes = racine.getChildNodes();
		
		for(int i = 0; i < clientNodes.getLength(); i++)
		{
			if(clientNodes.item(i).getNodeType() == Node.ELEMENT_NODE)
			{
				Element el = (Element)clientNodes.item(i);
				if(el.getElementsByTagName("title").item(0).getTextContent().equals(title) && el.getElementsByTagName("taskCreator").item(0).getTextContent().equals(taskCreator)){
					el.getElementsByTagName("taskMaker").item(0).setTextContent(user);	
				}
			}
	
		}
		return "tache attribuée à " + user;
		}
	/**
	 * Sauvegarde le nouveau fichier xml
	 * @param document document à sauvegarder
	 * @return
	 */
	private boolean saveDocument(Document document)
	{
		final TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = transformerFactory.newTransformer();
			String fileName = "./ressource/Task.xml";
			File file= new File(fileName);
			file.delete();
			final StreamResult sortie = new StreamResult(new File(filename));
			final StreamResult source = new StreamResult(file);
			
			transformer.transform((Source) source, sortie);
			return true;
		
		} catch (TransformerException e) {
			System.out.println("Oups");
			e.printStackTrace();
			return false;
		}
	}
	
	private ArrayList<Task> GetTaskList()
	{
		ArrayList<Task> taskl = new ArrayList<>();
		try {
			
			final Document document = openFile();
			
			final Element racine = document.getDocumentElement();
			
			NodeList clientNodes = racine.getChildNodes();
			
			for(int i = 0; i < clientNodes.getLength(); i++)
			{
				if(clientNodes.item(i).getNodeType() == Node.ELEMENT_NODE)
				{
					Element el = (Element)clientNodes.item(i);
					Task t = new Task();
					t.setDescription(el.getElementsByTagName("description").item(0).getTextContent()); 
					t.setPriority(el.getElementsByTagName("priority").item(0).getTextContent());
					t.setState(Integer.parseInt(el.getElementsByTagName("state").item(0).getTextContent())); ;
					t.setTaskMaker(el.getElementsByTagName("taskMaker").item(0).getTextContent());;
					t.setTaskCreator(el.getElementsByTagName("taskCreator").item(0).getTextContent());;
					t.setTitle(el.getElementsByTagName("title").item(0).getTextContent());;
					taskl.add(t);
				}
			}
			
			return taskl;
		}
		catch(Exception e)
		{
			System.out.println("Error parsing file");
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	/**
	 * Retourne le document DOM du fichier xml
	 * @return Document DOM
	 */
	private Document openFile()
	{
		try {
			final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			final DocumentBuilder builder = factory.newDocumentBuilder();
			return builder.parse(new File(filename));
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	


}
