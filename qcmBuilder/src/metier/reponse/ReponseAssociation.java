package src.metier.reponse;

import java.util.ArrayList;
import java.util.List;

public class ReponseAssociation extends Reponse
{
	private List<Integer> lstInd;
	private int           ind;
	private boolean       aGauche;
	
	public ReponseAssociation(String textReponse, List<Integer> lstInd, int ind, boolean aGauche)
	{
		super(textReponse);

		this.lstInd = new ArrayList<Integer>();
		this.lstInd = lstInd;

		this.ind    = ind;
		this.aGauche = aGauche;
	}
	
	public List<Integer> getLstInd()            { return lstInd;          }
	public void setLstInd(List<Integer> lstInd) { this.lstInd = lstInd;   }
	public void addLstInd(int ind)              { this.lstInd.add(ind);   }
	public int getInd()                         { return ind;             }
	public void setInd(int ind)                 { this.ind = ind;         }
	public boolean estAGauche()                 { return aGauche;         }
	public void setAGauche(boolean aGauche)     { this.aGauche = aGauche; }
	
}
