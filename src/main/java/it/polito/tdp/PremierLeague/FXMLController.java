/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.PremierLeague;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.PremierLeague.model.Collegamento;
import it.polito.tdp.PremierLeague.model.Match;
import it.polito.tdp.PremierLeague.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.text.DateFormatSymbols;
import java.time.Month;


public class FXMLController {

	Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnConnessioneMassima"
    private Button btnConnessioneMassima; // Value injected by FXMLLoader

    @FXML // fx:id="btnCollegamento"
    private Button btnCollegamento; // Value injected by FXMLLoader

    @FXML // fx:id="txtMinuti"
    private TextField txtMinuti; // Value injected by FXMLLoader

    @FXML // fx:id="cmbMese"
    private ComboBox<Month> cmbMese; // Value injected by FXMLLoader

    @FXML // fx:id="cmbM1"
    private ComboBox<Match> cmbM1; // Value injected by FXMLLoader

    @FXML // fx:id="cmbM2"
    private ComboBox<Match> cmbM2; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doConnessioneMassima(ActionEvent event) {
    	ArrayList<Collegamento> collegamenti=this.model.getCollegamenti();
    	int max=collegamenti.get(0).getPeso();
    	for(Collegamento c:collegamenti) {
    		if(max==c.getPeso()) {
    			this.txtResult.appendText(c+"\n");
    		}
    	}
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	String s=this.txtMinuti.getText();
    	if(!this.isNumeric(s)) {
    		this.txtResult.setText("DEVI INSERIRE UN NUMERO INTERO 	\n");
    		return;
    	}
    	int min=Integer.parseInt(s);
    	Month m=this.cmbMese.getValue();
    	if(m==null) {
    		this.txtResult.setText("SCEGLI QUALCOSA DAL MENU \n");
    		return;
    	}
    	int mese=m.getValue();
    	model.creaGrafo(mese,min);
    	this.txtResult.appendText("CREATO GRAFO DI "+model.getGrafo().vertexSet().size()+" nodi e "+model.getGrafo().edgeSet().size()+" archi \n");
    	this.cmbM1.getItems().clear();
    	this.cmbM2.getItems().clear();
    	this.cmbM1.getItems().addAll(model.getPartite());
    	this.cmbM2.getItems().addAll(model.getPartite());
    	
    }

    @FXML
    void doCollegamento(ActionEvent event) {
    	Match m1=this.cmbM1.getValue();
    	Match m2=this.cmbM2.getValue();
    	if(m1==null || m2==null) {
    		this.txtResult.appendText("SCEGLI I 2 MATCH \n");
    		return;
    	}
    	model.cercaCammino(m1,m2);
    	this.txtResult.appendText("\n *** PERCORSO MIGLIORE DA "+m1+" A "+m2+" **** \n");
    	ArrayList<Match>lista=model.getMigliore();
    	if(lista==null) {
    		this.txtResult.appendText("NESSUN PERCORSO TROVATO\n");
    		return;
    	}
    	for(Match m: lista) {
    		this.txtResult.appendText(m+"\n");
    	}
    }
    
    public static boolean isNumeric(String str) { 
  	  try {  
  	    Integer.parseInt(str);  
  	    return true;
  	  } catch(NumberFormatException e){  
  	    return false;  
  	  }  
  	}

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnConnessioneMassima != null : "fx:id=\"btnConnessioneMassima\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCollegamento != null : "fx:id=\"btnCollegamento\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMinuti != null : "fx:id=\"txtMinuti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbMese != null : "fx:id=\"cmbMese\" was not injected: check your FXML file 'Scene.fxml'.";        assert cmbM1 != null : "fx:id=\"cmbM1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbM2 != null : "fx:id=\"cmbM2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	for(int i=1;i<13;i++) {
    		this.cmbMese.getItems().add(Month.of(i));
    	}
  
    }
    
    public String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month-1];
    }
    
}
