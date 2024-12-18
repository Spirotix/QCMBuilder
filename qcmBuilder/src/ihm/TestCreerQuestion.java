package src.ihm;

import java.util.*;

public class TestCreerQuestion 
{
	private ArrayList<String> lstNotion		;
	private ArrayList<String> lstRessource	;

	public TestCreerQuestion ()
	{
		this.lstRessource = new ArrayList<String>();
		this.lstRessource.add("ressource1");
		this.lstRessource.add("ressource2");
		this.lstRessource.add("ressource3");
		this.lstRessource.add("ressource4");

		this.lstNotion = new ArrayList<String>();
		this.lstNotion.add("notion1");
		this.lstNotion.add("notion2");
		this.lstNotion.add("notion3");
		this.lstNotion.add("notion4");

		// new FrameMenu(this);
	}

	public static void main (String[] a)
	{
		new TestCreerQuestion();
	}

	public ArrayList<String> getChoixNotion(String s)
	{
		ArrayList<String> str = new ArrayList<String>();
		for (int i=0; i<this.lstRessource.size();i++)
			str.add(s+" : "+this.lstNotion.get(i));

		return str;
	}

	public ArrayList<String> getChoixRessources()
	{
		return this.lstRessource;
	}

	public void ajouterNotion (String nomNotion)
	{
		this.lstNotion.add(nomNotion);
	}

	public void ajouterRessource (String nomRessource)
	{
		this.lstRessource.add(nomRessource);
	}

	/*
	 * difficulté : 
	 * Tres facile : 1 
	 * Facile : 2
	 * Moyen : 3
	 * Diffile : 4
	 */

	/*public void creerQCM(String type, String nomRessource, String nomNotion, String text, String explication, int timer, int nbPoint, int nbIndiceUtilise, ArrayList<String> lstReponse, int difficulte)
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
	}*/

	/*public void creerAsso(String type, String nomRessource, String nomNotion, String text, String explication, int timer, int nbPoint, int nbIndiceUtilise, ArrayList<String> lstReponse, int difficulte)
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
	}*/

	public void creerQuestion (String type, String nomRessource, String nomNotion, String text, String explication, int timer, int nbPoint/*/, int nbIndiceUtilise*/, ArrayList<TypeReponse> lstReponse, int difficulte)
	{
		String 	str = "" ; 
		int 	cpt = 0  ;

		str+="Question de type : "+type+" du chapitre : "+nomNotion+" de la matière : "+nomRessource;
		str+="\nintitule : "+text ;
		str += "\nexplication : "+explication;
		str+="\ntemps : "+timer + " nmbres de points : "+nbPoint +" difficulte : "+difficulte;
		for (TypeReponse s : lstReponse)
			str+="\nReponse "+ ++cpt+": "+s.toString(type) ;
		
		System.out.println(str);
	}

	public void creerQuestionnaire (String ressource, boolean estChrono, ArrayList<TypeQuestionnaire> lstType)
	{
		String str=""; 


		str += "Questionnaire sur la ressource : "+ ressource;
		
		if (estChrono)
			str+="   //Il est chronometré";
		else 
			str+="    //Il nest pas chronometré";
		
		str += "\nLes ressources : ";
		for (TypeQuestionnaire t : lstType)
			str+="\n"+t.getNotion()+"_"+t.getNbTf()+"_"+t.getNbF()+"_"+t.getNbM()+"_"+t.getNbD();
		
		System.out.println(str);
	}


	/*public void creerElim(String type, String nomRessource, String nomNotion, String text, String explication, int timer, int nbPoint, int nbIndiceUtilise, ArrayList<String> lstReponse, int difficulte)
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
	}*/
}