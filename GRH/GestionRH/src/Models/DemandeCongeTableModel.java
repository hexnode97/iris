package Models;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class DemandeCongeTableModel extends AbstractTableModel {
	private ArrayList<DemandeConge> conges;
	public DemandeCongeTableModel(ArrayList<DemandeConge> conges) {
		this.conges = conges;
	}

	private String[] columnNames = {"ID", "SALARIE", "DATE DEBUT", "DATE FIN", "TYPE", "STATUT"};
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return conges.size();
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
				return conges.get(row).getId();
			case 1:
				return conges.get(row).getNom_employe();
			case 2:
				return conges.get(row).getDate_debut();
			case 3:
				return conges.get(row).getDate_fin();
			case 4:
				return conges.get(row).getType_conge().toUpperCase();
			case 5:
				return conges.get(row).getStatut().toUpperCase();
			default:
				return null;
			}
		}
	}
	
	public String getColumnName(int col) {
		return columnNames[col];
	}
}
