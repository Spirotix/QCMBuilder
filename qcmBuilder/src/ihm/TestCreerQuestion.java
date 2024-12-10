package src.ihm;

import java.util.*;

public class TestCreerQuestion 
{
	public TestCreerQuestion ()
	{
		//new FrameCreerQuestion(this);
	}

	public static void main (String[] a)
	{
		new TestCreerQuestion();
	}

	public ArrayList<String> getChoixNotion(String s)
	{
		ArrayList<String> str = new ArrayList<String>();
		str.add("Test1 : "+s);
		str.add("Test2 : "+s);
		str.add("Test3 : "+s);
		str.add("Test4 : "+s);

		return str;
	}

	public ArrayList<String> getChoixRessources()
	{
		ArrayList<String> str = new ArrayList<String>();
		
		str.add("Test1");
		str.add("Test2");
		str.add("Test3");
		str.add("Test4");

		return str;
	}

	/*
	 * difficulté : 
	 * Tres facile : 1 
	 * Facile : 2
	 * Moyen : 3
	 * Diffile : 4
	 */

	public void creerQuestion(String type, String nomRessource, String nomNotion, String text, String explication, int timer, int nbPoint/*/, int nbIndiceUtilise*/, ArrayList<String> lstReponse, int difficulte)
	{
		String str=""; 
		int cpt=0;

		str+="Question de type : "+type+" du chapitre : "+nomNotion+" de la matière : "+nomRessource;
		str+="\nintitule : "+text ;
		str += "\nexplication : "+explication;
		str+="\ntemps : "+timer + " nmbres de points : "+nbPoint +" difficulte : "+difficulte;
		for (String s : lstReponse)
			str+="\nReponse "+ ++cpt+": "+s ;
		
		System.out.println(str);
	}
}