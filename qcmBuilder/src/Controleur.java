package src;

import java.util.ArrayList;
import src.ihm.*;
import src.metier.*;

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

	public boolean ajouterRessource(String code, String nom)
	{
		return qcmBuilder.ajouterRessource(new Ressource(code, nom));
	}

	public boolean ajouterNotion(String nomRessource, String nomNotion)
	{
		Ressource r = qcmBuilder.rechercherRessource(nomRessource);
		return r.ajouterNotion(new Notion(nomNotion, r));
	}

	public void creerQuestion(String type, String code_nomRessource, String nomNotion, String text, String explication, int timer, int nbPoint, ArrayList<TypeReponse> lstReponse, int difficulte)
	{
		qcmBuilder.creerQuestion(type, code_nomRessource, nomNotion, text, timer, nbPoint, difficulte, lstReponse, explication);
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
