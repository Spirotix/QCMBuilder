package src.metier.question;

import src.metier.Notion;

public interface Question 
{
	String getText();
	int getTimer();
	double getNbPoint();
	int getDifficulte();
	Notion getNotions();
	String getExplication();

	void setText(String text);
	void setTimer(int timer);
	void setNbPoint(double nbPoint);
	void setDifficulte(int difficulte);
	void setNotions(Notion notions);
	void setExplication(String explication);
}
