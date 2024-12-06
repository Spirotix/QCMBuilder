package metier;

import java.util.List;

public abstract class Question 
{
	protected String        text;
	protected int           timer;
	protected int           nbPoint;
	protected int           nbIndiceUtilisé;
	protected List<Reponse> lstReponses;
	protected int           difficulte;
	protected Notion        notions;
	
	
	public String        getText()           { return this.text;            }
	public int           getTimer()          { return this.timer;           }
	public int           getNbPoint()        { return this.nbPoint;         }
	public int           getNbIndiceUtilisé(){ return this.nbIndiceUtilisé; }
	public List<Reponse> getLstReponses()    { return this.lstReponses;     }
	public int           getDifficulte()     { return this.difficulte;      }
	public Notion        getNotions()        { return this.notions;         }

	
}

