package src.metier;

public abstract class Question 
{
	protected Notion        notions;
	protected String        text;
	protected int           timer;
	protected double        nbPoint;
	protected int           difficulte;

	public Question(Notion notion, String text, int timer, double nbPoint, int difficulte)
	{
		this.notions     = notion;
		this.text        = text;
		this.timer       = timer;
		this.nbPoint     = nbPoint;
		this.difficulte  = difficulte;
	}
	
	public String        getText()        { return this.text;        }
	public int           getTimer()       { return this.timer;       }
	public double        getNbPoint()     { return this.nbPoint;     }
	public int           getDifficulte()  { return this.difficulte;  }
	public Notion        getNotions()     { return this.notions;     }

	public void setText        (String text              ) { this.text        = text;        }
	public void setTimer       (int timer                ) { this.timer       = timer;       }
	public void setNbPoint     (int nbPoint              ) { this.nbPoint     = nbPoint;     }
	public void setDifficulte  (int difficulte           ) { this.difficulte  = difficulte;  }
	public void setNotions     (Notion notions           ) { this.notions     = notions;     }
}
