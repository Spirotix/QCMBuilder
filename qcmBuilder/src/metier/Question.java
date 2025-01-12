package metier;

import java.util.List;

public abstract class Question 
{
	protected Notion        notions;
	protected String        text;
	protected int           timer;
	protected double        nbPoint;
	protected int           nbIndiceUtilisé;
	protected int           difficulte;
	protected List<Reponse> lstReponses;

	public Question(Notion notion, String text, int timer, double nbPoint, int difficulte, List<Reponse> lstReponse)
	{
		this.notions = notion;
		this.text = text;
		this.timer = timer;
		this.nbPoint = nbPoint;
		this.nbIndiceUtilisé = 0;
		this.difficulte = difficulte;
		this.lstReponses = lstReponse;
	}

	public String        getText()           { return this.text;            }
	public int           getTimer()          { return this.timer;           }
	public int           getNbPoint()        { return this.nbPoint;         }
	public int           getNbIndiceUtilisé(){ return this.nbIndiceUtilisé; }
	public List<Reponse> getLstReponses()    { return this.lstReponses;     }
	public int           getDifficulte()     { return this.difficulte;      }
	public Notion        getNotions()        { return this.notions;         }

	public void setText           (String text              ) { this.text = text;                       }
	public void setTimer          (int timer                ) { this.timer = timer;                     }
	public void setNbPoint        (int nbPoint              ) { this.nbPoint = nbPoint;                 }
	public void setNbIndiceUtilisé(int nbIndiceUtilisé      ) { this.nbIndiceUtilisé = nbIndiceUtilisé; }
	public void setDifficulte     (int difficulte           ) { this.difficulte = difficulte;           }
	public void setLstReponses    (List<Reponse> lstReponses) { this.lstReponses = lstReponses;         }
	public void setNotions        (Notion notions           ) { this.notions = notions;                 }
}
