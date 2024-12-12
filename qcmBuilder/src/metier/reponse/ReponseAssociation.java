package src.metier.reponse;

public class ReponseAssociation extends Reponse
{
	private ReponseAssociation reponseAssocie;
	private int                ind;
	private boolean            aGauche;
	
	public ReponseAssociation(String textReponse, ReponseAssociation reponseAssocie, int ind, boolean aGauche)
	{
		super(textReponse);

		this.reponseAssocie = reponseAssocie;
		this.ind            = ind;
		this.aGauche        = aGauche;
	}

	public ReponseAssociation getReponseAssocie()                                  { return reponseAssocie;                }
	public void               setReponseAssocie(ReponseAssociation reponseAssocie) { this.reponseAssocie = reponseAssocie; }

	public int                getInd()                                             { return ind;                           }
	public void               setInd(int ind)                                      { this.ind = ind;                       }

	public boolean            estAGauche()                                         { return aGauche;                       }
	public void               setAGauche(boolean aGauche)                          { this.aGauche = aGauche;               }
}
