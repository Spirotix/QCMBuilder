package src.ihm;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.*;

import src.Controleur;
import src.TypeQuestionnaire;

public class PanelGrilleQuestionnaire extends JPanel implements ActionListener 
{
	private Controleur 							ctrl;
	private ArrayList<JCheckBox> 				lstBox;
	private int 								nbTF, nbF, nbM, nbD;
	private ArrayList<ArrayList<JTextField>> 	lstTextField;
	private JLabel 								nbTFS, nbFS, nbMS, nbDS, nbTotal;
	private String 								ressource;

	public PanelGrilleQuestionnaire(Controleur ctrl, String ressource) 
	{
		this.ctrl		= ctrl		;
		this.ressource 	= ressource	;

		this.lstBox 	  = new ArrayList<>();
		this.lstTextField = new ArrayList<>();

		ArrayList<String> notions = this.ctrl.getChoixNotion(ressource);

		// Disposition principale
		this.setLayout			(new BoxLayout(this, BoxLayout.Y_AXIS	));
		this.setPreferredSize	(new Dimension(400, notions.size() * 50	));
		this.setMaximumSize		(new Dimension(400, notions.size() * 50	));

		// En-tête de la grille
		JPanel header		 = new JPanel	(new GridLayout(1, 2	));
		JPanel headerDroite  = new JPanel	(new GridLayout(1, 6	));
		header.setMaximumSize		(new Dimension(400, 30	));

		header.add(new JLabel("Notion"	, SwingConstants.CENTER));

		headerDroite.add(new JLabel(""		, SwingConstants.CENTER));
		headerDroite.add(new JLabel("TF"	, SwingConstants.CENTER));
		headerDroite.add(new JLabel("F"		, SwingConstants.CENTER));
		headerDroite.add(new JLabel("M"		, SwingConstants.CENTER));
		headerDroite.add(new JLabel("D"		, SwingConstants.CENTER));

		header.add(headerDroite);
		

		this.add(header);

		// Ajout des notions
		for (String notion : notions) 
		{
			JPanel ligneNotion 		 = new JPanel (new GridLayout(1,2 ));
			JPanel ligneNotionDroite = new JPanel (new GridLayout(1, 5));

			ligneNotion.setPreferredSize (new Dimension(400, 30));
			ligneNotion.setMaximumSize	 (new Dimension(400, 30));

			JPanel labelTemp = new JPanel(new FlowLayout(FlowLayout.LEFT)); 

			labelTemp.add(new JLabel(notion), FlowLayout.LEFT);
			labelTemp.setPreferredSize(new Dimension (notion.length(),notion.length()));

			ligneNotion.add(labelTemp);

			JCheckBox checkBox = new JCheckBox();
			checkBox.addActionListener(this);
			this.lstBox.add(checkBox);
			ligneNotionDroite.add(checkBox);

			ArrayList<JTextField> rowFields = new ArrayList<>(); 
			for (int i = 0; i < 4; i++) 
			{
				JTextField textField = new JTextField();
				textField.setEnabled(false);
				textField.setPreferredSize(new Dimension(40, 20));
				textField.addActionListener(this);
				rowFields	.add(textField);
				ligneNotionDroite.add(textField);
			}

			ligneNotion.add(ligneNotionDroite);
			this.add(ligneNotion);
			this.lstTextField.add(rowFields);
		}

		// Pied de page
		JPanel footer 		= new JPanel(new GridLayout(1, 2));
		JPanel footerDroite = new JPanel(new GridLayout(1, 5));
		footer.setMaximumSize(new Dimension(400, 30));

		this.nbTFS	 = new JLabel("0");
		this.nbFS 	 = new JLabel("0");
		this.nbMS	 = new JLabel("0");
		this.nbDS	 = new JLabel("0");
		this.nbTotal = new JLabel("Total : ");

		footer.add(new JLabel("Nb questions", SwingConstants.CENTER));

		footerDroite.add(new JLabel(""));
		footerDroite.add(this.nbTFS	);
		footerDroite.add(this.nbFS	);
		footerDroite.add(this.nbMS	);
		footerDroite.add(this.nbDS	);

		footer.add(footerDroite);
		this.add(footer);

		this.add(this.nbTotal);

		// Ajouter un JScrollPane pour le défilement
		JScrollPane scrollPane = new JScrollPane(this);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(400, 400));

	}

	public void actionPerformed(ActionEvent e) 
	{
		for (int i = 0; i < this.lstBox.size(); i++) 
			if (e.getSource().equals(this.lstBox.get(i))) 
			{
				ArrayList<JTextField> rowFields = this.lstTextField.get(i);
				boolean isChecked = this.lstBox.get(i).isSelected();

				for (JTextField textField : rowFields) 
				{
					textField.setEnabled(isChecked);
					if (!isChecked)
						textField.setText("");
				}
				break;
			}

		updateTotals();
	}

	private void updateTotals() 
	{
		this.nbTF = 0;
		this.nbF  = 0;
		this.nbM  = 0;
		this.nbD  = 0;

		for (ArrayList<JTextField> rowFields : this.lstTextField) 
		{
			this.nbTF += parseField(rowFields.get(0));
			this.nbF  += parseField(rowFields.get(1));
			this.nbM  += parseField(rowFields.get(2));
			this.nbD  += parseField(rowFields.get(3));
		}

		System.out.println(this.nbTF);
		System.out.println(this.nbF);
		System.out.println(this.nbM);
		System.out.println(this.nbD);

		this.nbTFS.setText(String.valueOf(nbTF));
		this.nbFS .setText(String.valueOf(nbF ));
		this.nbMS .setText(String.valueOf(nbM ));
		this.nbDS .setText(String.valueOf(nbD ));
		this.nbTotal.setText("Total : "+String.valueOf(nbTF+nbF+nbM+nbD ));
	}

	private int parseField(JTextField textField) 
	{
		try 
		{
			String text = textField.getText();
			return text.isEmpty() ? 0 : Integer.parseInt(text);
		} 
		catch (NumberFormatException e) 
		{
			JOptionPane.showMessageDialog(null, "Ne rentrez que des nombres entier", "Attention", JOptionPane.WARNING_MESSAGE);
			return 0;
		}
	}

	public ArrayList<TypeQuestionnaire> getSelectionner() 
	{
		ArrayList<TypeQuestionnaire> lstType = new ArrayList<>();

		for (int i = 0; i < this.lstBox.size(); i++) 
		{
			if (this.lstBox.get(i).isSelected()) 
			{
				int temp1 = parseField(this.lstTextField.get(i).get(0));
				int temp2 = parseField(this.lstTextField.get(i).get(1));
				int temp3 = parseField(this.lstTextField.get(i).get(2));
				int temp4 = parseField(this.lstTextField.get(i).get(3));

				lstType.add(new TypeQuestionnaire(this.ctrl.getChoixNotion(this.ressource).get(i), temp1, temp2, temp3, temp4));
			}
		}

		return lstType;
	}
}
