package src.ihm;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.*;

import src.Controleur;

public class PanelGrilleQuestionnaire extends JPanel implements ActionListener
{
	private Controleur 					ctrl;
	private ArrayList<JCheckBox> 				lstBox;
	private int 								nbTF, nbF, nbM , nbD ;
	private ArrayList<ArrayList<JTextField>> 	lstTextField;
	private JLabel 								nbTFS, nbFS, nbMS, nbDS, nbTotal;
	private String								ressource;

	public PanelGrilleQuestionnaire(Controleur ctrl, String ressource) 
	{
		this.ctrl = ctrl;
		this.ressource = ressource;
		this.lstBox 		= new ArrayList<JCheckBox>();
		this.lstTextField 	= new ArrayList<ArrayList<JTextField>>();

		ArrayList<String> notions = this.ctrl.getChoixNotion(ressource);

		this.setLayout(new GridLayout(notions.size() + 2, 7));

		this.add(new JLabel("Notion", SwingConstants.CENTER));
		this.add(new JLabel(""		, SwingConstants.CENTER));
		this.add(new JLabel("TF"	, SwingConstants.CENTER));
		this.add(new JLabel("F"		, SwingConstants.CENTER));
		this.add(new JLabel("M"		, SwingConstants.CENTER));
		this.add(new JLabel("D"		, SwingConstants.CENTER));
		this.add(new JLabel(""		, SwingConstants.CENTER));

		for (String notion : notions) 
		{
			this.add(new JLabel(notion, SwingConstants.CENTER));

			JCheckBox checkBox = new JCheckBox();
			checkBox.addActionListener(this);
			this.lstBox.add(checkBox);
			this.add(checkBox);

			ArrayList<JTextField> rowFields = new ArrayList<>();

			for (int i = 0; i < 4; i++) 
			{
				JTextField textField = new JTextField();
				textField.setEnabled(false);
				textField.addActionListener(this);
				rowFields.add(textField);
				this.add(textField);
			}
			this.add (new JLabel(""));

				this.lstTextField.add(rowFields);
		}

		this.nbTFS 		 = new JLabel("0");
		this.nbFS 		 = new JLabel("0");
		this.nbMS 		 = new JLabel("0");
		this.nbDS 		 = new JLabel("0");
		this.nbTotal 	 = new JLabel("Total : ");

		this.add(new JLabel("Nb questions/catégorie", SwingConstants.CENTER));
		this.add(new JLabel("", SwingConstants.CENTER));
		this.add(this.nbTFS	);
		this.add(this.nbFS	 );
		this.add(this.nbMS	 );
		this.add(this.nbDS	 );
		this.add(this.nbTotal);

		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) 
	{
		for (int i = 0; i < this.lstBox.size(); i++) 
		{
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
			return 0; 
		}
	}

	public ArrayList<TypeQuestionnaire> getSelectionner()
	{
		ArrayList<TypeQuestionnaire> lstType = new ArrayList<TypeQuestionnaire>();

		for (int i = 0; i < this.lstBox.size(); i++) 
		{
			if (this.lstBox.get(i).isSelected())
			{
				int temp1 = 0;
				int temp2 = 0;
				int temp3 = 0;
				int temp4 = 0;

				if (!(this.lstTextField.get(i).get(0).getText().equals("")))
					temp1=Integer.parseInt(this.lstTextField.get(i).get(0).getText());
					
				if (!(this.lstTextField.get(i).get(1).getText().equals("")))
					temp2=Integer.parseInt(this.lstTextField.get(i).get(1).getText());

				if (!(this.lstTextField.get(i).get(2).getText().equals("")))
					temp3=Integer.parseInt(this.lstTextField.get(i).get(2).getText());

				if (!(this.lstTextField.get(i).get(3).getText().equals("")))
					temp4=Integer.parseInt(this.lstTextField.get(i).get(3).getText());

				lstType.add(new TypeQuestionnaire(this.ctrl.getChoixNotion(this.ressource).get(i), temp1, temp2, temp3, temp4));
			}
		}

		return lstType;
	}
}
