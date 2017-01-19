package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Client;
import application.Serveur;
import application.Task;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ControleurClient implements Initializable {

	public static Client client;
	
	private static ArrayList<Task> todoList = new ArrayList<Task>();
	
	public static ArrayList<Task> getTodoList() {
		return todoList;
	}

	public static void setTodoList(ArrayList<Task> todoList) {
		ControleurClient.todoList = todoList;
	}
	
	@FXML private Label un;
	
	@FXML private TextField titre;
	@FXML private TextField description;
	@FXML private TextField priorite;
	@FXML private TextField etat;
	@FXML private TextField nameAU;
	
	@FXML private Label titrel;
	@FXML private Label descriptionl;
	@FXML private Label prioritel;
	@FXML private Label etatl;
	@FXML private Label nameUU;
	@FXML private Label nameUC;
	
	@FXML private Button ajouter;
	@FXML private Button attribuer;
	@FXML private Button prendre;
	@FXML private Button terminer;
	@FXML private Button supprimer;
	
	@FXML private ComboBox<String> listeTaches;
	
	@Override
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		
	    // listen for changes to the task combo box selection and update the displayed taskSystem.out.printlny.
	    listeTaches.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
		    @Override public void changed(ObservableValue<? extends String> selected, String oldV, String newV) {
		    	
			    if (newV != null) {
			    	for(Task tl : todoList){
			    		if(tl.getTitle().equals(newV)){
			    			titrel.setText(newV);
			    			descriptionl.setText(tl.getDescription());
			    			prioritel.setText(tl.getPriority());
			    			etatl.setText(tl.etatToString());
			    			nameUU.setText(tl.getTaskMaker());
			    			nameUC.setText(tl.getTaskCreator());
			    		}
			    	}
			   }
			}
	   });
	  }
	
	@FXML
	protected void doAjouter(ActionEvent event) throws IOException {
		 System.out.println("doAjouter");
		 
		 Task t = new Task(titre.getText(),description.getText(),priorite.getText(),Integer.parseInt(etat.getText()), un.getText(),un.getText());
		 todoList.add(t);
		 listeTaches.getItems().add(t.getTitle());
		 client.clientSendTask(t);
		 
	}
	
	@FXML
	protected void doAttribuer(ActionEvent event) throws IOException {
		System.out.println("doAttribuer");
	}
	
	@FXML
	protected void doPrendre(ActionEvent event) throws IOException {
		System.out.println("doPrendre");
	}
	
	@FXML
	protected void doTerminer(ActionEvent event) throws IOException {
		System.out.println("doTerminer");
	}
	
	@FXML
	protected void doSupprimer(ActionEvent event) throws IOException {
		System.out.println("doSupprimer");
	}
	 
}
