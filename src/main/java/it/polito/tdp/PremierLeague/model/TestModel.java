package it.polito.tdp.PremierLeague.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

public class TestModel {

	public static void main(String[] args) {
		
		System.out.println(CorrectDate(2000,12,30));
		
		
	}
	
	public static boolean CorrectDate(int anno, int mese, int giorno) {
		try {
		LocalDate.of(anno,mese,giorno);
		return true;
			}
		catch (java.time.DateTimeException e) {
			System.out.println("Data invalida");
			return false;
		}
		}
	
	



}
