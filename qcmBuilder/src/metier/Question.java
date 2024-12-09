package metier;

import java.util.List;

public abstract class Question 
{
	protected int           id;
	protected String        text;
	protected int           timer;
	protected int           nbPoint;
	protected int           nbIndiceUtilisé;
	protected List<Reponse> lstReponses;
	protected int           difficulte;
	protected Notion        notions;
	
	
	
	public Question(String text, int timer, int nbPoint, int nbIndiceUtilisé, List<Reponse> lstReponses, int difficulte,
	                Notion notions)
	{
		this.text = text;
		this.timer = timer;
		this.nbPoint = nbPoint;
		this.nbIndiceUtilisé = nbIndiceUtilisé;
		this.lstReponses = lstReponses;
		this.difficulte = difficulte;
		this.notions = notions;
	}
	public String        getText()           { return this.text;            }
	public int           getTimer()          { return this.timer;           }
	public int           getNbPoint()        { return this.nbPoint;         }
	public int           getNbIndiceUtilisé(){ return this.nbIndiceUtilisé; }
	public List<Reponse> getLstReponses()    { return this.lstReponses;     }
	public int           getDifficulte()     { return this.difficulte;      }
	public Notion        getNotions()        { return this.notions;         }

	
}

