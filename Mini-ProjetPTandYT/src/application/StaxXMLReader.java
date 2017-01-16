package application;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class StaxXMLReader {

	public static void main(String[] args) {
		String fileName = "C:/Users/Melaine/workspace/Parsing_stax/src/emlpoye.xml";
		List<Task> tasklisk = parseXML(fileName);
		for(Task task : tasklisk){
			System.out.println(task.toString());
		}
	}

	private static List<Task> parseXML(String fileName) {
		List<Task> tasklist = new ArrayList<>();
		Task task = null;
		XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
		try {
			XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(new FileInputStream(fileName));
			while(xmlEventReader.hasNext()){
				XMLEvent xmlEvent = xmlEventReader.nextEvent();
				if (xmlEvent.isStartElement()){
					StartElement startElement = xmlEvent.asStartElement();
					task = new Task();
					if(startElement.getName().getLocalPart().equals("void")){
						
						Attribute idAttr = startElement.getAttributeByName(new QName("property"));
						if(idAttr != null){
							task.setDescription(idAttr.getValue());
						}
					}
					//set the other variables from xml elements
					else if(startElement.getName().getLocalPart().equals("string")){
						xmlEvent = xmlEventReader.nextEvent();
						task.setDescription(xmlEvent.asCharacters().getData());
					}
					
					if(startElement.getName().getLocalPart().equals("void")){
						
						//Get the 'id' attribute from Employee element
						Attribute idAttr = startElement.getAttributeByName(new QName("property"));
						if(idAttr != null){
							task.setPriority(idAttr.getValue());
						}
					}
					//set the other variables from xml elements
					else if(startElement.getName().getLocalPart().equals("string")){
						xmlEvent = xmlEventReader.nextEvent();
						task.setPriority(xmlEvent.asCharacters().getData());
					}
					
					if(startElement.getName().getLocalPart().equals("void")){
						
						//Get the 'id' attribute from Employee element
						Attribute idAttr = startElement.getAttributeByName(new QName("property"));
						if(idAttr != null){
							task.setPriority(idAttr.getValue());
						}
					}
					//set the other variables from xml elements
				
					else if(startElement.getName().getLocalPart().equals("string")){
						xmlEvent = xmlEventReader.nextEvent();
						task.setState(Integer.parseInt(xmlEvent.asCharacters().getData()));
					}
					
					if(startElement.getName().getLocalPart().equals("void")){
					
					
						Attribute idAttr = startElement.getAttributeByName(new QName("property"));
						if(idAttr != null){
							task.setPriority(idAttr.getValue());
						}
					
				}else if(startElement.getName().getLocalPart().equals("string")){
					xmlEvent = xmlEventReader.nextEvent();
					task.setTaskMarker(xmlEvent.asCharacters().getData());
				}
					
					if(startElement.getName().getLocalPart().equals("void")){
					
						Attribute idAttr = startElement.getAttributeByName(new QName("property"));
						if(idAttr != null){
							task.setPriority(idAttr.getValue());
						}
					}else if(startElement.getName().getLocalPart().equals("string")){
						xmlEvent = xmlEventReader.nextEvent();
						task.setTaskCreator(xmlEvent.asCharacters().getData());
					}
					
					if(startElement.getName().getLocalPart().equals("void")){
						
						Attribute idAttr = startElement.getAttributeByName(new QName("property"));
						if(idAttr != null){
							task.setPriority(idAttr.getValue());
						}
						
					}else if(startElement.getName().getLocalPart().equals("string")){
						xmlEvent = xmlEventReader.nextEvent();
						task.setEndDate(xmlEvent.asCharacters().getData());
					}
					
				}
				if(xmlEvent.isEndElement()){
					EndElement endElement = xmlEvent.asEndElement();
					if(endElement.getName().getLocalPart().equals("Task")){
						tasklist.add(task);
					}
				}
			}

		} catch (FileNotFoundException | XMLStreamException e) {
			e.printStackTrace();
		}
		return tasklist;
	}
}