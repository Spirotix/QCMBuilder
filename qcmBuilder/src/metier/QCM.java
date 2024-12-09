package metier;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class QCM extends Question
{
	public QCM(Notion notion, String text, int timer, int nbPoint, int nbIndiceUtilisé, int difficulte)
	{
		super(notion, text, timer, nbPoint, difficulte);
	}
}