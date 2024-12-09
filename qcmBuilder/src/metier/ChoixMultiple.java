package metier;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChoixMultiple extends Question
{
	List<Reponse> lstBonneReponses;

	public ChoixMultiple(int id, String text, int timer, int nbPoint, int nbIndiceUtilisé, List<Reponse> lstReponses, int difficulte, Notion notions, List<Reponse> lstBonneReponses)
	{
		super(text, timer, nbPoint, nbIndiceUtilisé, lstReponses, difficulte, notions);
		this.lstBonneReponses = lireReponses();
	}

	private List<Reponse> lireReponses()
	{
		List<Reponse> reponses = new ArrayList<>();
		try
		{
			Scanner scanner = new Scanner(new File("reponses.rtf"));
			while (scanner.hasNextLine())
			{
				String line = scanner.nextLine();
				String[] parts = line.split(";");
				if (parts[0].equals(Integer.toString(id)))
				{
					
				}
			}
			scanner.close();
		}
		catch (FileNotFoundException e) { e.printStackTrace(); }

		return reponses;
	}
}
