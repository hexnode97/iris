package Data;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import Models.Employe;

public class DataFicheEmploye {
	
	public static boolean deleteEmploye(int id) {
		int res = (int)DatabaseContext.executeSqlQuery("DELETE FROM employe WHERE id_employe = "+id, true);
		if(res > 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static Employe getEmployeById(int id) {
		ResultSet result = (ResultSet) DatabaseContext.executeSqlQuery("SELECT * FROM employe WHERE id_employe = "+id, false);
		Employe employe = new Employe();
		try {
			if(result.next()) {
				employe.setNom(result.getString("nom"));
				employe.setPrenom(result.getString("prenom"));
				employe.setAdresse(result.getString("adresse"));
				employe.setDate_embauche(result.getDate("date_embauche"));
				employe.setDate_naissance(result.getDate("date_naissance"));
				employe.setNombre_enfants(result.getInt("nombre_enfants"));
				employe.setTel(result.getString("tel"));
				employe.setType_paiement(result.getString("type_paiement"));
				employe.setType_contrat(result.getString("type_contrat"));
				employe.setSalaire(result.getDouble("salaire"));
				employe.setRib(result.getString("rib"));
				employe.setCin(result.getString("cin"));
				employe.setSexe(result.getString("sexe"));
				employe.setPhoto(result.getBytes("photo"));
				employe.setSolde(result.getInt("solde_conge"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		return employe;
	}
}
