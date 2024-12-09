package metier;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class QCM extends Question
{
	public QCM(Notion notion, int id, String text, int timer, int nbPoint, int nbIndiceUtilisé, int difficulte)
	{
		super(notion, id, text, timer, nbPoint, nbIndiceUtilisé, difficulte);
	}

	@Override
	protected List<Reponse> lireReponses()
	{
		try 
		{
			Scanner scanner = new Scanner(new File("../data/reponses.csv"));
			while (scanner.hasNextLine()) 
			{
				String line = scanner.nextLine();
				String[] parts = line.split(";");
				
			}
			scanner.close();
		} 
		catch (FileNotFoundException e) { e.printStackTrace(); }

	}

	
}