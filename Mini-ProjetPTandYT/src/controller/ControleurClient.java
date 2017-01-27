package controller;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Client;
import application.Serveur;
import application.Task;
import application.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControleurClient implements Initializable {

	public static boolean LTisEmty = true; 
	
	public static Client client;
	
	public static Task tache; 

	public static String listtache; 
	
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
		if(!LTisEmty){
			try {
				reconstitutionDesTache();
				for(Task t : todoList)
					 listeTaches.getItems().add(t.getTitle());
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
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
			    			tache = tl;
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
		 client.clientSendAJOUTER();
		 client.clientSendTask(titre.getText(),description.getText(),priorite.getText(),Integer.parseInt(etat.getText()), un.getText(),un.getText());
		 
	}
	
	@FXML
	protected void doAttribuer(ActionEvent event) throws IOException {
		System.out.println("doAttribuer");
	}
	
	@FXML
	protected void doPrendre(ActionEvent event) throws IOException {
		System.out.println("doPrendre");
		if (tache.getTaskMaker() != un.getText()){
			tache.setTaskMaker(un.getText());
		}
		client.clientSendPRENDRE();
	}
	
	@FXML
	protected void doTerminer(ActionEvent event) throws IOException {
		System.out.println("doTerminer");
		client.clientSendTERMINER();
		tache.closeTask();
	}
	
	@FXML
	protected void doSupprimer(ActionEvent event) throws IOException {
		System.out.println("doSupprimer");
	}
	
	@FXML
	protected void doQuitter(ActionEvent event) throws IOException {
		System.out.println("doQuitter");
		 Stage cStage = (Stage)un.getScene().getWindow();
		 client.clientSendQUITTER();
		 client.closeClient();
         cStage.close();  
         //System.exit(0);
	}
	 
	static void reconstitutionDesTache() throws NumberFormatException, RemoteException {
		Task t = null;
		String [] decoupeList = listtache.split("T:");
        for(int i = 1; i<decoupeList.length;i++){
                System.out.println("t - : " + i);
                String [] decoupeTask = decoupeList[i].split("/");
                t = new Task(decoupeTask[0],decoupeTask[1],decoupeTask[2],Integer.parseInt(decoupeTask[3]),decoupeTask[4],decoupeTask[5],decoupeTask[6]);
                todoList.add(t);
        }

	} 
}
