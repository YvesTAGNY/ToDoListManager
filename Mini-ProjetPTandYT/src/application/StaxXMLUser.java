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


public class StaxXMLUser {

	public static List<User> parseXML(String fileName) {
		List<User> userlist = new ArrayList<>();
		User user = null;
		XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
		try {
			XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(new FileInputStream(fileName));
			while(xmlEventReader.hasNext()){
				XMLEvent xmlEvent = xmlEventReader.nextEvent();
				if (xmlEvent.isStartElement()){
					StartElement startElement = xmlEvent.asStartElement();
					if(startElement.getName().getLocalPart().equals("void")){
					 user = new User();
						Attribute idAttr = startElement.getAttributeByName(new QName("property"));
						/*if(idAttr != null){
							user.setUserName(idAttr.getValue());
						}*/
					}
					else if(startElement.getName().getLocalPart().equals("string")){
						xmlEvent = xmlEventReader.nextEvent();
						user.setUserName(xmlEvent.asCharacters().getData());
					}
				}
			

			if(xmlEvent.isEndElement()){
				EndElement endElement = xmlEvent.asEndElement();
				if(endElement.getName().getLocalPart().equals("void")){
					userlist.add(user);
				}
			}
		}

	} catch (FileNotFoundException | XMLStreamException e) {
		e.printStackTrace();
	}
	return userlist;
}
	public static boolean isExist(String nameUser){
		
		String fileName = "./ressource/User.xml";
		List<User> UserList = parseXML(fileName);
		for(User user : UserList){
			if(user.getUserName().equals(nameUser)){
				return true;
			}
			//System.out.println(user.toString());
		}
		return false;
	}
}
