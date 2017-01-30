package application;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

public class testxmlopTask {
	public static class TestXmlOperations {

		public static void main(String[] args) throws TransformerException, SAXException, IOException, ParserConfigurationException {
			XMLopTask xmlOp = new XMLopTask( "./ressource/Task.xml");
//			System.out.println(xmlOp.addTask("Balayer", "Balayer la cour, et jeter les feuilles","MOYEN",1,"Alix","Pierre"));
//			xmlOp.addTask("Astiquer", "Balayer la cour, et jeter les feuilles","FORTE",0,"Alix","Alix");
//			xmlOp.addTask("Décharger", "Balayer la cour, et jeter les feuilles","FAIBLE",2,"Alix","Yves");
			System.out.println(xmlOp.finishTask("Décharger","13/02/2017"));
			
			ArrayList<Task> taskl = new ArrayList<>();
			taskl = xmlOp.GetTaskList()	;	
			System.out.println(taskl);
			//System.out.println(xmlOp.removeTask("test", "er"));
			//System.out.println(xmlOp.AttribuateTask("Balayer", "Alix", "fr"));

			}

	}
}