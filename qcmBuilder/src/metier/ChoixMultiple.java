package metier;

import java.util.List;
import java.util.ArrayList;
import java.io.File;

public class ChoixMultiple extends Question
{
	List<Reponse> lstBonneReponses;

	public ChoixMultiple(int id, String text, int timer, int nbPoint, int nbIndiceUtilisé, List<Reponse> lstReponses, int difficulte, Notion notions, List<Reponse> lstBonneReponses)
	{
		this.id = id;
		this.text = text;
		this.timer = timer;
		this.nbPoint = nbPoint;
		this.nbIndiceUtilisé = nbIndiceUtilisé;
		this.lstReponses = lstReponses;
		this.difficulte = difficulte;
		this.notions = notions;
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
