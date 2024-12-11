package src.metier.reponse;

import java.util.ArrayList;
import java.util.List;

public class ReponseAssociation extends Reponse
{
	private List<Integer> lstInt;
	
	public ReponseAssociation(String estVrai, String textReponse, List<Integer> lstInt)
	{
		super(estVrai, textReponse);

		this.lstInt = new ArrayList<Integer>();
		this.lstInt = lstInt;
	}
	
	public List<Integer> getLstInt()            { return lstInt;        }
	public void setLstInt(List<Integer> lstInt) { this.lstInt = lstInt; }
	public void addLstInt(int ind)              { this.lstInt.add(ind); }
	
}
