package it.polito.tdp.PremierLeague.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	PremierLeagueDAO dao;
	
	public Model() {
		dao=new PremierLeagueDAO();
	}
	
	private SimpleWeightedGraph<Match, DefaultWeightedEdge> grafo;
	
	private ArrayList<Match> partite;
	private ArrayList<Collegamento> collegamenti;

	public void creaGrafo(int mese, int min) {
		
		grafo= new SimpleWeightedGraph<Match, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		partite=dao.getAllMatchByMonth(mese);
		Graphs.addAllVertices(grafo, partite);
		collegamenti=dao.getAllCollegamenti(mese,min);
		for(Collegamento c:collegamenti) {
			Graphs.addEdge(grafo, c.getM1(), c.getM2(), c.getPeso());
		}
		
		//System.out.println(dao.getDate(LocalDateTime.of(2012, 5, 13,0,0,0)));
		
	}

	public SimpleWeightedGraph<Match, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}

	public ArrayList<Collegamento> getCollegamenti() {
		return collegamenti;
	}

	public ArrayList<Match> getPartite() {
		return partite;
	}
	
	int max;
	ArrayList<Match> migliore;
	Match arrivo;
	
	public void cercaCammino(Match m1, Match m2) {
		LinkedList<Match> parziale=new LinkedList<Match>();
		migliore=null;
		arrivo=m2;
		max=0;
		int livello=0;
		parziale.add(m1);
		espandi(livello+1,parziale,m1);
		
	}

	private void espandi(int livello, LinkedList<Match> parziale, Match ultimo) {
		
		
		if(ultimo.equals(arrivo)) {
			int totale=0;
			if(parziale.size()!=1) {
			for(int t=0;t<parziale.size()-1;t++) {
				Match primo=parziale.get(t);
				Match secondo=parziale.get(t+1);
				totale+=grafo.getEdgeWeight(grafo.getEdge(primo, secondo));
			}
			}
			if(totale>max) {
				max=totale;
				migliore=new ArrayList<Match>(parziale);
			}
			return;
		}
		
		for(Match m:Graphs.neighborListOf(grafo, ultimo)) {
			if(!parziale.contains(m)) {
			if(this.isOkay(m,ultimo)) {
				parziale.add(m);
				espandi(livello+1,parziale,m);
				parziale.remove(m);
			}
			}
		}
		
		
	}

	private boolean isOkay(Match m1, Match ultimo1) {
		Match m=partite.get(partite.indexOf(m1));
		Match ultimo=partite.get(partite.indexOf(ultimo1));
		int casa1= m.getTeamHomeID();
		int casa2= ultimo.getTeamHomeID();
		int ospite1=m.getTeamAwayID();
		int ospite2=ultimo.getTeamAwayID();
		if(casa1==casa2 || casa1==ospite2) {
			if(ospite1==casa2 || ospite1==casa1)
				return false;
		}
		return true;
	}

	public ArrayList<Match> getMigliore() {
		return migliore;
	}
	
	
	
	
	
}
