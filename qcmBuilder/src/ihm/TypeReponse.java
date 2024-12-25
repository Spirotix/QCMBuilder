package src.ihm;

import java.util.ArrayList;

public class TypeReponse
{
	private String                  cheminImage        ;
	private String                  contenu            ;
	private boolean                 estBonneReponse    ;
	private TypeReponse             repGauche,repDroite;
	private ArrayList<TypeReponse> 	lstLiaisonRep1     ;
	private int                     ordre              ;
	private double                  cout               ;
	private String                  position           ;
	private static int              nbG , nbD          ;
	public int                      cptG, cptD         ;

	public TypeReponse(String contenu, boolean estBonneReponse)
	{
		this.contenu         = contenu        ;
		this.estBonneReponse = estBonneReponse;
	}

	public TypeReponse(String contenu, String position, String cheminImage)
	{
		this.contenu  = contenu ;
		this.position = position;

		if (this.position.equals("Gauche"))
			this.cptG = ++ TypeReponse.nbG;

		if (this.position.equals("Droite"))
			this.cptD = ++ TypeReponse.nbD;

		this.cheminImage = cheminImage;
		this.lstLiaisonRep1 = new ArrayList<TypeReponse>();
	}

	public TypeReponse(TypeReponse gauche, TypeReponse droite)
	{
		this.repGauche = gauche;
		this.repDroite = droite;
	}

	public int getCpt()
	{
		if (this.position.equals("Gauche"))
			return this.cptG;

		if (this.position.equals("Droite"))
			return this.cptD;
		return 0;
	}
	public TypeReponse(String contenu, int ordre, double cout, boolean estBonneReponse)
	{
		this.contenu         = contenu        ;
		this.ordre           = ordre          ;
		this.cout            = cout           ;
		this.estBonneReponse = estBonneReponse;
	}

	public String      getContenu         () {return this.contenu         ;}
	public String      getPosition        () {return this.position        ;}
	public boolean     getEstBonneReponse () {return this.estBonneReponse ;}
	public int         getOrdre           () {return this.ordre           ;}
	public double      getCout            () {return this.cout            ;}

	public TypeReponse getRepGauche       () {return this.repGauche       ;}
	public TypeReponse getRepDroite       () {return this.repDroite       ;}

	public void ajouterLiaison(TypeReponse t) {this.lstLiaisonRep1.add(t);} 

	public String toString (String type)
	{
		String str ="";

		str +=this.contenu;
		
		if ( type.equals("Elimination") )
		{
			if (this.estBonneReponse)
				str += " : OUI";
			else 
				str += " : NON";

			str+= " /ordre : " +this.ordre + "  points en moins : "+this.cout;
		}
		else if ( type.equals("QCM") )
		{
			if (this.estBonneReponse)
				str += " : OUI";
			else 
				str += " : NON";
		}
		else if ( type.equals("Association") )
		{
			str+=" : "+this.getPosition();
			if (this.getPosition().equals("Gauche"))
				for (TypeReponse tpRep : this.lstLiaisonRep1)
					str+=tpRep.getContenu()+"_";
		}
		return str;
	}
}
