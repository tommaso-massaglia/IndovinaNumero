package it.polito.tdp.numero.model;

import java.security.InvalidParameterException;

public class NumeroModel {
	
	private final int NMAX = 100;
	private final int TMAX = 8;

	private int segreto;
	private int tentativiFatti;
	private boolean inGioco;
	
	public NumeroModel() {
		this.inGioco = false;
	}
	
	/**
	 * Avvia nuova partita
	 */

	public void newGame() {
		this.segreto = (int) (Math.random() * NMAX) + 1;
		this.tentativiFatti = 0;
		this.inGioco = true;
	}
	
	/**
	 * Esegue un tentativo
	 * @param t numero da testare
	 * @return 1 se troppo alto, 0 se giusto, -1 se troppo basso
	 */
	
	public int tentativo(int t) {
		
		// controllo se la partita è in corso
		if (!inGioco) {
			throw new IllegalStateException("La partita è terminata");
		}
		
		// controllo se l'input è nel range corretto
		if (!tentativoValido(t)) {
			throw new InvalidParameterException(String.format("Devi inserire un numero tra %d e %d", 1, NMAX));
		}
		
		this.tentativiFatti++;
		if (this.tentativiFatti == this.TMAX){
			this.inGioco = false;
		}
		
		// gestisci tentativo
		if (t == this.segreto) {
			inGioco = false;
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
		if (t<1 || t>NMAX) {
			return false;
		} else { return true;}
	}

	public int getSegreto() {
		return segreto;
	}

	public int getTentativiFatti() {
		return tentativiFatti;
	}

	public boolean isInGioco() {
		return inGioco;
	}

	public int getTMAX() {
		return TMAX;
	}

}
