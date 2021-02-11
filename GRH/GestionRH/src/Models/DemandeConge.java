package Models;

import java.sql.Date;

public class DemandeConge {
	private int id;
	private int salarie_id;
	private java.sql.Date date_debut;
	private java.sql.Date date_fin;
	private String statut;
	private String type_conge;
	private String nom_employe;
	public DemandeConge(int id, int employe_id, String nom_employe, Date date_debut, Date date_fin, String statut, String type_conge) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.salarie_id = employe_id;
		this.nom_employe = nom_employe;
		this.date_debut = date_debut;
		this.date_fin = date_fin;
		this.statut  = statut;
		this.type_conge  = type_conge;
	}
	public DemandeConge() {
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSalarie_id() {
		return salarie_id;
	}
	public void setSalarie_id(int salarie_id) {
		this.salarie_id = salarie_id;
	}
	public java.sql.Date getDate_debut() {
		return date_debut;
	}
	public void setDate_debut(java.sql.Date date_debut) {
		this.date_debut = date_debut;
	}
	public java.sql.Date getDate_fin() {
		return date_fin;
	}
	public void setDate_fin(java.sql.Date date_fin) {
		this.date_fin = date_fin;
	}
	public String getStatut() {
		return statut;
	}
	public void setStatut(String statut) {
		this.statut = statut;
	}
	public String getType_conge() {
		return type_conge;
	}
	public void setType_conge(String type_conge) {
		this.type_conge = type_conge;
	}
	public String getNom_employe() {
		return nom_employe;
	}
	public void setNom_employe(String nom_employe) {
		this.nom_employe = nom_employe;
	}
}
