package controller;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ControleurClient {

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
	
	 
}
