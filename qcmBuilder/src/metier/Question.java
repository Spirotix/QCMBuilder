package metier;

import java.util.List;

public abstract class Question 
{
	protected Notion        notions;
	protected int           id;
	protected String        text;
	protected int           timer;
	protected int           nbPoint;
	protected int           nbIndiceUtilisé;
	protected int           difficulte;
	protected List<Reponse> lstReponses;
	
	
	
	public Question(Notion notion, int id, String text, int timer, int nbPoint, int nbIndiceUtilisé, int difficulte)
	{
		this.notions = notion;
		this.id = id;
		this.text = text;
		this.timer = timer;
		this.nbPoint = nbPoint;
		this.nbIndiceUtilisé = nbIndiceUtilisé;
		this.difficulte = difficulte;
		this.lstReponses = lireReponses();
	}

	protected abstract List<Reponse> lireReponses();
	public String        getText()           { return this.text;            }
	public int           getTimer()          { return this.timer;           }
	public int           getNbPoint()        { return this.nbPoint;         }
	public int           getNbIndiceUtilisé(){ return this.nbIndiceUtilisé; }
	public List<Reponse> getLstReponses()    { return this.lstReponses;     }
	public int           getDifficulte()     { return this.difficulte;      }
	public Notion        getNotions()        { return this.notions;         }

	
}

