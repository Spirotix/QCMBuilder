package src;

import java.util.ArrayList;
import src.ihm.*;
import src.metier.*;
import src.metier.question.*;

public class Controleur
{
	private QCMBuilder qcmBuilder;
	private FrameMenu  frameMenu;

	public Controleur()
	{
		this.qcmBuilder = new QCMBuilder();
		this.frameMenu  = new FrameMenu(this);
	}

	public ArrayList<String> getChoixNotion(String s)
	{
		ArrayList<String> notions = new ArrayList<>();

		Ressource r = qcmBuilder.rechercherRessource(s);
		if (r!=null)
		{
			System.out.println(r.getNom());
			for (Notion n : r.getNotions())
			{
				notions.add(n.getNom());
			}
		}
		
		
		return notions;
	}

	public ArrayList<String> getChoixRessources()
	{
		ArrayList<String> ressources = new ArrayList<>();
		for (Ressource r : qcmBuilder.getRessources())
		{
			ressources.add( r.getCode() + "_" + r.getNom() );
		}
		return ressources;
	}

	public ArrayList<String> getQuestions(String ressource, String notion)
	{
		ArrayList<String> 	str = new ArrayList<String>()	;
		String 				temp							;

		for (Ressource r : this.qcmBuilder.getRessources())
		{
			temp = r.getCode()+"_"+r.getNom();
			if (temp.equals(ressource))
				for (Notion n : r.getNotions())
					if (n.getNom().equals(notion))
						for (Question q : n.getQuestions())
							str.add(q.getText());
		}

		return str;
	}

	
	public void supprimerQuestion(String nomQuestion, String ressource, String notion)
	{
		ArrayList<String> 	str = new ArrayList<String>()	;
		String 				temp							;

		for (Ressource r : this.qcmBuilder.getRessources())
		{
			temp = r.getCode()+"_"+r.getNom();
			if (temp.equals(ressource))
				for (Notion n : r.getNotions())
					if (n.getNom().equals(notion))
						for (Question q : n.getQuestions())
							if (q.getText().equals(nomQuestion))
							{
								System.out.println("SupprimerC");
								n.supprimerQuestion(q);
							}
		}
	}

	public boolean ajouterRessource(String code, String nom)
	{
		return qcmBuilder.ajouterRessource(new Ressource(code, nom));
	}

	public boolean ajouterNotion(String nomRessource, String nomNotion)
	{
		Ressource r = qcmBuilder.rechercherRessource(nomRessource);
		return r.ajouterNotion(new Notion(nomNotion, r));
	}

	public boolean supprimerNotion(String nomRessource, String nomNotion)
	{
		Ressource r = qcmBuilder.rechercherRessource(nomRessource);
		return r.supprimerNotion( r.rechercherNotion(nomNotion) );
	}

	public boolean supprimerRessource(String codeRessource)
	{
		return qcmBuilder.supprimerRessource( qcmBuilder.rechercherRessource(codeRessource) );
	}

	public boolean creerQuestion(String type, String code_nomRessource, String nomNotion, String text, String explication, int timer, double nbPoint, ArrayList<TypeReponse> lstReponse, int difficulte)
	{
		return qcmBuilder.creerQuestion(type, code_nomRessource, nomNotion, text, timer, nbPoint, difficulte, lstReponse, explication);
	}

	public void genererQuestionnaire(String ressource ,boolean chrono,ArrayList<TypeQuestionnaire> questions)
	{
		//qcmBuilder.genererQuestionnaire(ressource, chrono, questions);
	}

	public static void main(String[] args)
	{
		new Controleur();
	}
}
