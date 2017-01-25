package controller;

import java.io.IOException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;

import application.Client;
import application.ODI;
import application.StaxXMLUser;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ControleurConnexion extends Pane{
	
	static boolean run = false;
	
	@FXML private TextField nameUser;
	@FXML private Button inscription;
	@FXML private Button connexion;

    public String getText() {
        return textProperty().get();
    }

    public void setText(String value) {
        textProperty().set(value);
    }

    public StringProperty textProperty() {
        return  nameUser.textProperty();
    }

    @FXML
    protected void doInscription(ActionEvent event) throws IOException {
    	
    	if(!run){
	    	//creation d'un nouveau client
	    	Client client = new Client();
	    	client.openClient();
	    	ControleurClient.client = client;
	    	run = true;
    	}
    	
		try {
			ODI o = (ODI)Naming.lookup("//localhost/od");
			String str = o.getTodolist();
			System.out.println("list : " + str);
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	//envoi du nom du client pour la vérification
    	ControleurClient.client.clientSendNEW();
    	ControleurClient.client.clientSendUserName(getText());
    	String accord = ControleurClient.client.clientRecieveAccord();
    	
        //actions
        if(accord.equals("YES")){
	        //fermeture de la fenetre de connection
	        Stage cStage = (Stage)connexion.getScene().getWindow();
	        cStage.close();  
	        
	        //ouverture de la fenetre Client
	        final URL url = getClass().getResource("/view/IHM_Client.fxml");
	        final FXMLLoader fxmlLoader = new FXMLLoader(url);
	        final Pane root = (Pane) fxmlLoader.load();
	        final Scene scene = new Scene(root);
	        final Stage stage = new Stage();
	        stage.setScene(scene);
	        stage.show();
	        stage.requestFocus();
	        //
	        Label un = (Label) root.getChildren().get(17);
	        un.setText(getText());
        }
        else{
        	Alert dialogE = new Alert(AlertType.ERROR);
        	dialogE.setTitle("ERREUR");
        	dialogE.setHeaderText("NOM INCORRET");
        	dialogE.setContentText("Un utilisateur est déja inscrit au nom de : " + getText());
        	dialogE.showAndWait();
        }
    }
    
    @FXML
    protected void doConnexion(ActionEvent event) throws IOException {
        System.out.println("doConnexion : " + getText());
        if(!run){
        	//creation d'un nouveau client
        	Client client = new Client();
        	client.openClient();
        	ControleurClient.client = client;
	    	run = true;
        }
        
    	//envoi du nom du client pour la vérification
        ControleurClient.client.clientSendOLD();
        ControleurClient.client.clientSendUserName(getText());
    	String accord = ControleurClient.client.clientRecieveAccord();
    	
        //actions
        if(!accord.equals("YES")){
        	//fermeture de la fenetre de connection
            Stage cStage = (Stage)connexion.getScene().getWindow();
            cStage.close();  
            
            //ouverture de la fenetre Client
            final URL url = getClass().getResource("/view/IHM_Client.fxml");
            final FXMLLoader fxmlLoader = new FXMLLoader(url);
            final Pane root = (Pane) fxmlLoader.load();
            final Scene scene = new Scene(root);
            final Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            //
            Label un = (Label) root.getChildren().get(17);
            un.setText(getText());
        }
        else{
        	Alert dialogE = new Alert(AlertType.ERROR);
        	dialogE.setTitle("ERREUR");
        	dialogE.setHeaderText("NOM INCORRET");
        	dialogE.setContentText("Aucun utilisateur n'est enregistrer au nom de : " + getText());
        	dialogE.showAndWait();
        }
    }
}
