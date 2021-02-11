package Data;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Models.Admin;
import Models.DemandeConge;
import Models.Employe;

public class DataEspaceAdmin {
	
	
	public static ArrayList<DemandeConge> getConges() {
		ArrayList<DemandeConge> conges = new ArrayList<DemandeConge>();
		ResultSet res = (ResultSet) DatabaseContext.executeSqlQuery("SELECT * FROM demande_conge", false);
		String nom_employe = "";
		try {
			while(res.next()) {
				nom_employe = DataFicheEmploye.getEmployeById(res.getInt("employe_id")).getNom().concat(" "+DataFicheEmploye.getEmployeById(res.getInt("employe_id")).getPrenom());
				conges.add(new DemandeConge(res.getInt("demande_id"), res.getInt("employe_id"), nom_employe, res.getDate("date_debut"), res.getDate("date_fin"), res.getString("statut"), res.getString("type_conge")));
			}
		}catch(SQLException ex) {
			JOptionPane.showMessageDialog(null,ex.getMessage());
		}
		return conges;
	}
	
	
	
	public static ArrayList<Employe> getEmployes() {
		ArrayList<Employe> employes = new ArrayList<Employe>();
		ResultSet res = (ResultSet) DatabaseContext.executeSqlQuery("SELECT * FROM employe", false);
		try {
			while(res.next()) {
				employes.add(
					new Employe(
							res.getInt("id_employe"),
							res.getString("nom"),
							res.getString("prenom"),
							res.getString("adresse"),
							res.getDate("date_naissance"),
							res.getDate("date_embauche"),
							res.getString("tel"),
							res.getString("rib"),
							res.getString("type_paiement"),
							res.getString("type_contrat"),
							res.getInt("nombre_enfants"),
							res.getDouble("salaire"),
							res.getString("email"),
							res.getString("sexe"),
							res.getBytes("photo"),
							res.getString("cin"),
							res.getInt("solde_conge")
					)
				);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		try {
			res.close();
		}catch(SQLException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}
		if(employes == null) {
			employes = new ArrayList<Employe>();
		}
		return employes;
		
	}

	
	public static boolean addEmploye(Employe employe, File photo) {
		Connection con = DatabaseContext.connectToDatabase();
		PreparedStatement ps;
		boolean result = false;
		try {
			ps = con.prepareStatement("INSERT INTO employe(nom, prenom, adresse,"
					+ "cin, date_embauche, date_naissance, nombre_enfants, tel, email, type_paiement"
					+ ", type_contrat, rib, photo, salaire, sexe, mdp_employe) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		
			ps.setString(1, employe.getNom());
			ps.setString(2, employe.getPrenom());
			ps.setString(3, employe.getAdresse());
			ps.setString(4, employe.getCin());
			ps.setDate(5, employe.getDate_embauche());
			ps.setDate(6, employe.getDate_naissance());
			ps.setInt(7, employe.getNombre_enfants());
			ps.setString(8, employe.getTel());
			ps.setString(9, employe.getEmail());
			ps.setString(10, employe.getType_paiement());
			ps.setString(11, employe.getType_contrat());
			ps.setString(12, employe.getRib());
			FileInputStream is; 
			try {
				is= new FileInputStream(photo);
				ps.setBinaryStream(13, is);
			}catch(IOException ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
			ps.setDouble(14, employe.getSalaire());
			ps.setString(15, employe.getSexe());
			ps.setString(16, String.format("%s",employe.getCin().toUpperCase()));
			if(ps.executeUpdate() > 0) {
				result = true;
			}
			else {
				result = false;
			}
			ps.close();
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
			
	}


	public static boolean updateEmploye(Employe employe, File photo) {
		// TODO Auto-generated method stub
		Connection con = DatabaseContext.connectToDatabase();
		
		PreparedStatement ps;
		boolean result = false;
		try {
			// l'admin a change la photo
			if(photo != null) {
				ps = con.prepareStatement("UPDATE employe SET"
						+ " adresse = ?, tel = ?, nombre_enfants = ?, rib = ?, salaire = ?, photo = ?, type_contrat = ?, type_paiement = ? WHERE id_employe = ?");
				ps.setInt(7, employe.getId());
				FileInputStream is; 
				try {
					is= new FileInputStream(photo);
					ps.setBinaryStream(6, is);
				}catch(IOException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
			else {
				ps = con.prepareStatement("UPDATE employe SET"
						+ " adresse = ?, tel = ?, nombre_enfants = ?, rib = ?, salaire = ?, type_contrat = ?, type_paiement = ? WHERE id_employe = ?");
				ps.setInt(8, employe.getId());
			}
			
			ps.setString(1, employe.getAdresse());
			ps.setString(2, employe.getTel());
			ps.setInt(3, employe.getNombre_enfants());
			ps.setString(4, employe.getRib());
			ps.setDouble(5, employe.getSalaire());
			ps.setString(6, employe.getType_contrat());
			ps.setString(7, employe.getType_paiement());
			if(ps.executeUpdate() > 0) {
				result = true;
			}
			else {
				result = false;
			}
			ps.close();
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
		return result;
			
		
	}

	
	public static ArrayList<Admin> getAdmins() {
		ResultSet res = (ResultSet) DatabaseContext.executeSqlQuery("SELECT * FROM admin", false);
		ArrayList<Admin> admins = new ArrayList<Admin>();
		try {
			while(res.next()) {
				admins.add(new Admin(res.getInt("id_admin"), res.getString("utilisateur_admin"), res.getString("mdp_admin")));
			}
		}catch(SQLException ex) {
			JOptionPane.showMessageDialog(null, "Cannot get admins.");
		}
		return admins;
	}


	public static boolean addAdmin(Admin admin) {
		int res = (int)DatabaseContext.executeSqlQuery("INSERT INTO admin(utilisateur_admin, mdp_admin) VALUES('"+admin.getNom_utilisateur()+"', '"+admin.getMot_de_passe()+"')", true);
		if(res > 0) {
			return true;
		}
		return false;
	}
	
	public static boolean updateConge(int id, int employe_id, int nombre_jours_a_soustraire,  boolean acceptee) {
		JOptionPane.showMessageDialog(null, "Nombre de jours a soustraire : " + nombre_jours_a_soustraire);
		String statut = "";
		Employe emp = DataFicheEmploye.getEmployeById(employe_id);
		if(acceptee) {
			statut = "acceptee";
			int res2 = (int)DatabaseContext.executeSqlQuery("UPDATE employe SET solde_conge = "+(emp.getSolde()-nombre_jours_a_soustraire)+" WHERE id_employe = "+employe_id, true);
		}
		else {
			statut = "refusee";
		}
		int res = (int)DatabaseContext.executeSqlQuery("UPDATE demande_conge SET statut = '" + statut + "' WHERE demande_id = " + id, true) ;
		if(res > 0) {
			return true;
		}
		else {
			return false;
		}
		
		
	}

}
