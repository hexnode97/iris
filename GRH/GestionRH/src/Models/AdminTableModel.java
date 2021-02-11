package Models;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class AdminTableModel extends AbstractTableModel {

	private String[] columnNames = {"Nom d'utilisateur", "Dernière authentification"};
	private ArrayList<Admin> admins;
	
	public AdminTableModel(ArrayList<Admin> admins) {
		this.admins = admins;
	}
	
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return admins.size();
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}
	
	@Override
	public Object getValueAt(int row, int col) {
		// TODO Auto-generated method stub
		if(row >= admins.size()) {
			return null;
		}
		else {
			switch(col) {
			case 0:
				return admins.get(row).getNom_utilisateur();
			case 1:
				return admins.get(row).getDerniere_authentification();
			default:
				return null;
			}
		}
	}

}
