package Models;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class EmployeTableModel extends AbstractTableModel {

	
	private ArrayList<Employe> employes;
	public EmployeTableModel(ArrayList<Employe> employes) {
		this.employes = employes;
	}

	private String[] columnNames = {"ID", "NOM", "PRENOM", "DATE DE NAISSANCE", "ADRESSE", "TELEPHONE", "CONTRAT", "PAIEMENT", "SALAIRE", "DATE D'EMBAUCHE", "SOLDE CONGE"};
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return employes.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		
		// TODO Auto-generated method stub
		if(row >= this.getRowCount()) {
			return null;
		}
		else {
			switch(col) {
			case 0:
				return employes.get(row).getId();
			case 1:
				return employes.get(row).getNom().toUpperCase();
			case 2:
				return employes.get(row).getPrenom().toUpperCase();
			case 3:
				return employes.get(row).getDate_naissance();
			case 4:
				return employes.get(row).getAdresse().toUpperCase();
			case 5:
				return employes.get(row).getTel();
			case 6:
				return employes.get(row).getType_contrat().toUpperCase();
			case 7:
				return employes.get(row).getType_paiement().toUpperCase();
			case 8:
				return employes.get(row).getSalaire();
			case 9:
				return employes.get(row).getDate_embauche();
			case 10:
				return employes.get(row).getSolde();
			}
		}
		return null;
	}
	
	public String getColumnName(int col) {
		return columnNames[col];
	}

}
