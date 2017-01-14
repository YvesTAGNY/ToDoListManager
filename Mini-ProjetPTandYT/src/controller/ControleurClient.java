package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
	    
	    //test
	    listeTaches.getItems().setAll("ecrire", "manger", "nourrire");

	    // pour la label du combobox
	    //--->selectedFruit.textProperty().bind(listeTaches.getSelectionModel().selectedItemProperty());

	    // listen for changes to the task combo box selection and update the displayed taskSystem.out.printlny.
	    listeTaches.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
		    @Override public void changed(ObservableValue<? extends String> selected, String oldV, String newV) {
		    	/*if (oldV != null) {
			        switch(oldV) {
			            case "ecrire": System.out.println("1"); break;
			            case "manger": System.out.println("2"); break;
			            case "nourrire": System.out.println("3"); break;
			        }
			    }*/
			    if (newV != null) {
			        switch(newV) {
			        	case "ecrire": titrel.setText(newV); System.out.println("1"); break;
			            case "manger": titrel.setText(newV); System.out.println("2"); break;
			            case "nourrire": titrel.setText(newV); System.out.println("3"); break;
			        }
			   }
			}
	   });
	  }
	
	@FXML
	protected void doAjouter(ActionEvent event) throws IOException {
		 System.out.println("doAjouter");
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
