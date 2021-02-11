package Data;

import java.io.File;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Models.DemandeConge;
import Models.Employe;

public class DataEspaceEmploye {

	public static ArrayList<DemandeConge> getDemandesByEmployeId(int employe_id) {
		//JOptionPane.showMessageDialog(null, "SALARIE ID : " + employe_id);
		ArrayList<DemandeConge> demandes = new ArrayList<DemandeConge>();
		ResultSet res = (ResultSet) DatabaseContext.executeSqlQuery("select * from demande_conge WHERE employe_id = "+employe_id + " ORDER BY date_fin ASC", false);
		try {
			while(res.next()) {
				demandes.add(new DemandeConge(res.getInt("demande_id"), res.getInt("employe_id"), "", res.getDate("date_debut"), res.getDate("date_fin")
						, res.getString("statut"), res.getString("type_conge")));
			}
		}catch(SQLException ex) {
			JOptionPane.showMessageDialog(null, "Methode getDemandesByEmployeId : " + ex.getMessage());
		}
		
		
		try {
			res.close();
		}catch(SQLException ex) {
			JOptionPane.showMessageDialog(null, "asdsadasdasdsadasdsdads");
		}
		
		return demandes;
	}


	public static boolean addDemande(DemandeConge demande) {
		Connection con = DatabaseContext.connectToDatabase();
		int res = 0;
		PreparedStatement ps;
		try {
			ps = con.prepareStatement("INSERT INTO demande_conge(employe_id, date_debut, date_fin, statut, type_conge) VALUES(?,?,?,?,?)");
			ps.setInt(1, demande.getSalarie_id());
			ps.setDate(2, demande.getDate_debut());
			ps.setDate(3, demande.getDate_fin());
			ps.setString(4, demande.getStatut());
			ps.setString(5, demande.getType_conge());
			res = ps.executeUpdate();
			ps.close();
			con.close();
		}
		catch(SQLException ex) {
			res = 0;
			JOptionPane.showMessageDialog(null, "addDemande error : " + ex.getMessage());
		}
		
		if(res > 0) {
			return true;
		}
		return false;
	}

	public static boolean deleteDemande(int id_demande) {
		
		DemandeConge demande = getDemandeById(id_demande);
		if(demande != null) {
			if(!demande.getStatut().equals("en-attente")) {
				JOptionPane.showMessageDialog(null, "Impossible d'annuler une demande traitée!", "Erreur", JOptionPane.ERROR_MESSAGE);
			}
			else {
				int res = (int)DatabaseContext.executeSqlQuery("DELETE FROM demande_conge WHERE demande_id = " + id_demande, true);
				if(res > 0) {
					return true;
				}
				else {
					return false;
				}
			}
		}
		return false;
		
	}
	
	public static DemandeConge getDemandeById(int id) {
		ResultSet res = (ResultSet)DatabaseContext.executeSqlQuery("SELECT * FROM demande_conge WHERE demande_id = " + id , false);
		DemandeConge demande = null;
		try {
			if(res.next()) {
				demande = new DemandeConge();
				demande.setId(id);
				demande.setSalarie_id(res.getInt("employe_id"));
				demande.setDate_debut(res.getDate("date_debut"));
				demande.setDate_fin(res.getDate("date_fin"));
				demande.setStatut(res.getString("statut"));
				demande.setType_conge(res.getString("type_conge"));
				res.close();
			}
		}catch(SQLException ex) {
			JOptionPane.showMessageDialog(null, "getDemandeById : " + ex.getMessage());
		}
		
		return demande;
	}

}
