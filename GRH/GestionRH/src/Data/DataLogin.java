package Data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;

import javax.swing.JOptionPane;

import Models.Admin;
import Models.Employe;
import Models.LoginResult;

public class DataLogin {
	public static int utilisateurCourant = 0;
	public static LoginResult verifyLogin(String username, String password) {
		ResultSet res = (ResultSet) DatabaseContext.executeSqlQuery(String.format("SELECT * FROM admin WHERE utilisateur_admin = '%s' AND mdp_admin = '%s'", username, password), false);
		LoginResult loginResult = new LoginResult();
		try {
			if(res.next()) {
				loginResult.setStatus(true);
				loginResult.setUserType("admin");
				utilisateurCourant = res.getInt("id_admin");
			}
			else {
				try {
					res = (ResultSet)DatabaseContext.executeSqlQuery(String.format("SELECT * FROM employe WHERE email = '%s' AND mdp_employe = '%s'", username, password), false);
					if(res.next()) {
						loginResult.setStatus(true);
						loginResult.setUserType("employe");
						utilisateurCourant = res.getInt("id_employe");
					}
					else {
						loginResult.setStatus(false);
						loginResult.setUserType(null);
					}
				}catch(SQLException e) {
					//JOptionPane.showMessageDialog(null, "Erreur de verification des informations de connexion.");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		
		return loginResult;
	}
	
	public static void updateLastAuthenticated() {
		int res = (int) DatabaseContext.executeSqlQuery("UPDATE admin SET derniere_authentification = '"+java.time.LocalDateTime.now().toString()+"' WHERE id_admin = "+DataLogin.utilisateurCourant, true);
		//JOptionPane.showMessageDialog(null, (ResultSet)DatabaseContext.executeSqlQuery("SELECT * FROM admin", false));
	}
	
	public static Employe getCurrentUser() {
		ResultSet res = (ResultSet) DatabaseContext.executeSqlQuery("SELECT * FROM employe WHERE id_employe = "+utilisateurCourant, false);
		Employe employe = new Employe();
		try {
			while(res.next()) {
				employe.setId(utilisateurCourant);
				employe.setNom(res.getString("nom"));
				employe.setPrenom(res.getString("prenom"));
				employe.setAdresse(res.getString("adresse"));
				employe.setPhoto(res.getBytes("photo"));
				employe.setCin(res.getString("cin"));
				employe.setDate_embauche(res.getDate("date_embauche"));
				employe.setDate_naissance(res.getDate("date_naissance"));
				employe.setNombre_enfants(res.getInt("nombre_enfants"));
				employe.setRib(res.getString("rib"));
				employe.setTel(res.getString("tel"));
				employe.setEmail(res.getString("email"));
				employe.setSalaire(res.getDouble("salaire"));
				employe.setSexe(res.getString("sexe"));
				employe.setType_contrat(res.getString("type_contrat"));
				employe.setType_paiement(res.getString("type_paiement"));
				employe.setMot_de_passe(res.getString("mdp_employe"));
				employe.setSolde(res.getInt("solde_conge"));
			}
		}catch(SQLException ex) {
			JOptionPane.showMessageDialog(null, "getCurrentUser : "+ex.getMessage());
		}
		try {
			res.close();
		}catch(SQLException ex) {
			
		}
		return employe;
	}
	
	public static Admin getCurrentAdmin() {
		ResultSet res = (ResultSet) DatabaseContext.executeSqlQuery("SELECT * FROM admin WHERE id_admin = "+utilisateurCourant, false);
		Admin adminCourant = new Admin();
		try {
			while(res.next()) {
				adminCourant.setId(utilisateurCourant);
				adminCourant.setNom_utilisateur(res.getString("utilisateur_admin"));
				adminCourant.setMot_de_passe(res.getString("mdp_admin"));
			}
		}catch(SQLException ex) {
			JOptionPane.showMessageDialog(null, "Cannot get current User.");
		}
		return adminCourant;
	}
	
	public static boolean usernameExists(String userType, String username) {
		ResultSet res = null;
		if(userType.equals("admin")) {
			res = (ResultSet) DatabaseContext.executeSqlQuery("SELECT * FROM "+userType+" WHERE utilisateur_admin = '"+username+"'", false);
		}
		else if(userType.equals("employe")) {
			res = (ResultSet) DatabaseContext.executeSqlQuery("SELECT * FROM "+userType+" WHERE email = '"+username+"'", false);
		}
		
		boolean exists = false;
		try {
			if(res.next()) {
				exists = true;
			}
			res.close();
		}catch(SQLException ex) {
			JOptionPane.showMessageDialog(null, "usernameExists function error : " + ex.getMessage());
		}
		
		return exists;
	}

	public static boolean updateAdminPassword(int admin_id, String nouveauMotDePasse) {
		int res = (int) DatabaseContext.executeSqlQuery("UPDATE admin SET mdp_admin = '" + nouveauMotDePasse + "' WHERE id_admin = " + admin_id, true);
		if(res > 0) return true;
		return false;
	}
	public static boolean updateEmployePassword(int employe_id, String nouveauMotDePasse) {
		int res = (int) DatabaseContext.executeSqlQuery("UPDATE employe SET mdp_employe = '" + nouveauMotDePasse + "' WHERE id_employe = " + employe_id, true);
		if(res > 0) return true;
		return false;
	}
	

}
