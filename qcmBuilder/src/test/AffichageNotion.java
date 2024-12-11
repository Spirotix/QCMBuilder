package src.test;

import src.metier.Notion;
import src.metier.Question;
import src.metier.Ressource;

public class AffichageNotion 
{
	public static void main(String[] args) 
	{
		Ressource r1 = new Ressource("R1.11 Bases de la communication");
		for ( Notion n : r1.getNotions() )
		{
			System.out.println(n.getNom());

			for ( Question q : n.getQuestions() )
			{
				System.out.println(q.getText());
			}
		}
	}
}
