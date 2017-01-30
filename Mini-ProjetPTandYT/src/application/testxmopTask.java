package application;

import java.util.ArrayList;

public class testxmopTask {
	public static class TestXmlOperations {

		public static void main(String[] args) {
			XMLopTask xmlOp = new XMLopTask( "./ressource/test.xml");
			System.out.println(xmlOp.addTask("test", "tt","er",2,"er","rt"));
			ArrayList<Task> taskl = new ArrayList<>();
			taskl = xmlOp.GetTaskList()	;	
			System.out.println(taskl);
			System.out.println(xmlOp.removeTask("test", "er"));
			System.out.println(xmlOp.AttribuateTask("Balayer", "Alix", "fr"));

			}

	}
}
