//package src.ihm;

import java.util.ArrayList;

public class TypeQuestionnaire 
{
	private String 	notion				;
	private int 	nbTf, nbF, nbM, nbD	; 

	public TypeQuestionnaire (String notion, int nbTf, int nbF, int nbM, int nbD)
	{
		this.notion	= notion;
		this.nbTf	= nbTf	;
		this.nbF 	= nbF	;
		this.nbM 	= nbM	;
		this.nbD 	= nbD	;
	}

	public String 	getNotion() {return this.notion	;}
	public int 		getNbTf  () {return this.nbTf	;}
	public int 		getNbF	 () {return this.nbF	;}
	public int 		getNbM	 ()	{return this.nbM	;}
	public int 		getNbD	 () {return this.nbD	;}
}