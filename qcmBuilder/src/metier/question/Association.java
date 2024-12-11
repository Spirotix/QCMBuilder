package src.metier.question;

import java.util.List;

import src.metier.Notion;
import src.metier.reponse.*;

public class Association extends Question
{
	private List<ReponseAssociation> lstReponseAsso;

	public Association(Notion notion, String text, int timer, double nbPoint, int difficulte, List<ReponseAssociation> lstReponseAsso, String explication)
	{
		super(notion, text, timer, nbPoint, difficulte, explication);

		this.lstReponseAsso = lstReponseAsso;
	}

	public List<ReponseAssociation> getReponses() 
	{
		return this.lstReponseAsso;
	}

	public void setReponses(List<ReponseAssociation> lstReponseAsso) 
	{
		this.lstReponseAsso = lstReponseAsso;
	}

}
