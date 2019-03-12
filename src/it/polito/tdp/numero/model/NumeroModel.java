package it.polito.tdp.numero.model;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class NumeroModel {
	
	private IntegerProperty NMAX = new SimpleIntegerProperty(100) ;
	private IntegerProperty TMAX = new SimpleIntegerProperty(8) ;
	private List<Integer> inseriti;
	private int segreto;
	private BooleanProperty inGioco = new SimpleBooleanProperty(false) ;
	private IntegerProperty tentativiFatti = new SimpleIntegerProperty();
	
	public NumeroModel() {
		this.inGioco.set(false);
		inseriti = new ArrayList<Integer>();
	}
	
	/**
	 * Avvia nuova partita
	 */

	public void newGame() {
		this.segreto = (int) (Math.random() * NMAX.get()) + 1;
		this.inseriti.clear();
		this.tentativiFatti.set(0);;
		this.inGioco.set(true);;
	}
	
	/**
	 * Esegue un tentativo
	 * @param t numero da testare
	 * @return 1 se troppo alto, 0 se giusto, -1 se troppo basso
	 */
	
	public int tentativo(int t) {
		
		// controllo se la partita è in corso
		if (!inGioco.get()) {
			throw new IllegalStateException("La partita è terminata");
		}
		
		// controllo se l'input è nel range corretto
		if (!tentativoValido(t)) {
			throw new InvalidParameterException(String.format("Devi inserire un numero tra %d e %d", 1, NMAX));
		}
		
		this.inseriti.add(t);
		
		this.tentativiFatti.set(this.tentativiFatti.get()+1);;
		if (this.tentativiFatti == this.TMAX){
			this.inGioco.set(false);;
		}
		
		// gestisci tentativo
		if (t == this.segreto) {
			inGioco.set(false);;
			return 0;
		}
		
		if (t>this.segreto) {
			return 1;
		}
	
		return -1;
	}
	
	/**
	 * Controlla se il tentativo è nel range corretto
	 */
	
	public boolean tentativoValido(int t) {
		if (t<1 || t>NMAX.get() || this.inseriti.contains(t)==true) {
			return false;
		} 
		return true;
		
	}

	public int getSegreto() {
		return segreto;
	}

	public boolean isInGioco() {
		return inGioco.get();
	}

	public int getTMAX() {
		return TMAX.get();
	}

	public final IntegerProperty tentativiFattiProperty() {
		return this.tentativiFatti;
	}
	

	public final int getTentativiFatti() {
		return this.tentativiFattiProperty().get();
	}
	
}
