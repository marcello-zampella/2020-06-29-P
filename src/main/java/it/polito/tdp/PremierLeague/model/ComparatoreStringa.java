package it.polito.tdp.PremierLeague.model;

import java.util.Comparator;

public class ComparatoreStringa implements Comparator {
	
	public int compare (Object o1, Object o2) { //NON CAMBIARE
		String a1=(String) o1; //cambaire entrambi gli Object con la classe (es. Utente)
		String a2 =(String) o2; //cambaire entrambi gli Object con la classe (es. Utente)
		return a1.compareTo(a2); //crescente
		
	}

}