package src.ihm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class PanelReponseAsso extends JPanel
{
	private PanelCreerQuestionAsso 	panelQ;
	private ArrayList<JPanel>		reponsesPossibles;
	private PanelReponseGaucheAsso 	panelGauche;
	private PanelReponseDroiteAsso 	panelDroite;

	private PanelReponseGaucheAsso 	lierGauche;
	private PanelReponseDroiteAsso	lierDroite;


	public PanelReponseAsso (PanelCreerQuestionAsso panelQ)
	{
		this.panelQ = panelQ;
		this.setLayout(new GridLayout(1,2));

		//Initialisation
		this.panelGauche = new PanelReponseGaucheAsso(this);
		this.panelDroite = new PanelReponseDroiteAsso(this);

		this.panelQ.addReponseDroite(this.panelDroite);
		this.panelQ.addReponseGauche(this.panelGauche);


		//Insertion
		this.add (this.panelGauche 	);
		this.add (this.panelDroite	);

		this.setVisible(true);

	}

	public void lier(PanelReponseGaucheAsso gauche)
	{
		
		this.lierGauche=gauche;

		String str1, str2;
		if (this.lierGauche==null)
			str1=null;
		else 
			str1 = this.lierGauche.getNom();

		if (this.lierDroite==null)
			str2=null;
		else 
			str2 = this.lierDroite.getNom();
		
		System.out.println("etape 1 : "+str1 +" : "+str2);

		if (this.lierDroite!=null)
		{
			this.lierGauche.ajouterBonneRep(this.lierDroite);
			this.lierDroite=null;
			this.lierGauche=null;
		}
		System.out.println("etape 2 : "+str1 +" : "+str2);
	}

	public void lier(PanelReponseDroiteAsso droite)
	{
		this.lierDroite=droite;

		String str1, str2;
		if (this.lierGauche==null)
			str1=null;
		else 
			str1 = this.lierGauche.getNom();

		if (this.lierDroite==null)
			str2=null;
		else 
			str2 = this.lierDroite.getNom();
		
		System.out.println("etape 1 : "+str1 +" : "+str2);

		if (this.lierGauche!=null)
		{	
			System.out.println("liaison");
			this.lierGauche.ajouterBonneRep(this.lierDroite);
			this.lierDroite=null;
			this.lierGauche=null;
		}

		System.out.println("etape 2 : "+str1 +" : "+str2);
	}

	public void supprimer(PanelReponseGaucheAsso p)
	{
		if (this.panelDroite==null)
			this.panelQ.supprimer(this);
		else 
		{
			this.removeAll();
			this.add(new JPanel());
			this.add(this.panelDroite );
			this.panelGauche=null;
			this.revalidate();
			this.repaint();
		}
	}

	public void supprimer(PanelReponseDroiteAsso p)
	{
		if (this.panelGauche==null)
			this.panelQ.supprimer(this);
		else 
		{
			this.removeAll();
			this.add(this.panelGauche);
			this.panelDroite=null;
			this.add(new JPanel());
			this.revalidate();
			this.repaint();
		}
		
	}

	public String getString()
	{
		String str = "";
		
		
		return str;
	}
}