package it.polito.tdp.numero;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.numero.model.NumeroModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

public class NumeroController {
	
	private NumeroModel model;
	
	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private HBox boxControllopartita;

	@FXML
	private TextField txtRimasti;
	// numero di tentativi rimasti ancora da provare

	@FXML
	private HBox boxControlloTentativi;

	@FXML
	private TextField txtTentativo;
	// tentativo inserito dall'utente

	@FXML
	private TextArea txtMessaggi;

	@FXML
	void handleNuovaPartita(ActionEvent event) {
		
		// Gestione dell'interfaccia
		boxControllopartita.setDisable(true);
		boxControlloTentativi.setDisable(false);
		txtMessaggi.clear();
		txtRimasti.setText(Integer.toString(model.getTMAX()));
		txtTentativo.clear();
		// Inizio una nuova partita
		model.newGame();

	}
	
	@FXML
	void onKeyPressed(KeyEvent event) {
		if(event.getCode() == KeyCode.ENTER)
			this.sendfunction();	
    }

	@FXML
	void handleProvaTentativo(ActionEvent event) {
		this.sendfunction();
	}
		
	public void sendfunction() {
		
		// Leggi il valore del tentativo
		String ts = txtTentativo.getText();

		// Controlla se è valido
		int tentativo ;
		try {
			tentativo = Integer.parseInt(ts);
		} catch (NumberFormatException e) {
			txtMessaggi.appendText("Non è un numero valido\n");
			return ;
		}
		if (!model.tentativoValido(tentativo)) {
			txtMessaggi.appendText("Range non valido\n");
			return;
		}
		
		int risultato = model.tentativo(tentativo);

		// Controlla se ha indovinato
		// -> fine partita
		if(risultato==0) {
			txtMessaggi.appendText("Complimenti, hai indovinato in "+model.getTentativiFatti()+" tentativi\n");
			boxControllopartita.setDisable(false);
			boxControlloTentativi.setDisable(true);
			return;
		}
		else if(risultato==-1) {
			txtMessaggi.appendText("Tentativo troppo BASSO\n");
		} 
		else if(risultato==1){
			txtMessaggi.appendText("Tentativo troppo ALTO\n");
		}

		// Verifica se ha esaurito i tentativi -> fine partita
		if(!model.isInGioco()) {
			txtMessaggi.appendText("Hai PERSO, il numero segreto era: "+model.getSegreto()+"\n");
			boxControllopartita.setDisable(false);
			boxControlloTentativi.setDisable(true);
			return ;
		}

		// Aggiornare interfaccia con n. tentativi rimasti
		txtRimasti.setText(Integer.toString(model.getTMAX()-model.getTentativiFatti()));
		txtTentativo.clear();
	}

	@FXML
	void initialize() {
		assert boxControllopartita != null : "fx:id=\"boxControllopartita\" was not injected: check your FXML file 'Numero.fxml'.";
		assert txtRimasti != null : "fx:id=\"txtRimasti\" was not injected: check your FXML file 'Numero.fxml'.";
		assert boxControlloTentativi != null : "fx:id=\"boxControlloTentativi\" was not injected: check your FXML file 'Numero.fxml'.";
		assert txtTentativo != null : "fx:id=\"txtTentativo\" was not injected: check your FXML file 'Numero.fxml'.";
		assert txtMessaggi != null : "fx:id=\"txtMessaggi\" was not injected: check your FXML file 'Numero.fxml'.";

	}
	

	public void setModel(NumeroModel model) {
		this.model = model;
	}


}
