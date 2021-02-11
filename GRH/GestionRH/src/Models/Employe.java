package Models;

public class Employe {
	private int id; 
	private String nom;
	private String prenom;
	private String adresse;
	private java.sql.Date date_naissance;
	private java.sql.Date date_embauche;
	private String tel;
	private String rib;
	private String type_paiement;
	private String type_contrat; 
	private int nombre_enfants;
	private double salaire;
	private String email;
	private String sexe;
	private String cin;
	private byte[] photo;
	private String mot_de_passe;
	private int solde_conge;
	
	public int getSolde() {
		return solde_conge;
	}
	public void setSolde(int s) {
		if(s > 0) {
			this.solde_conge = s;
		}
	}
	
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	
	public String getCin() {
		return cin;
	}
	public void setCin(String cin) {
		this.cin = cin;
	}
	
	public Employe(int id, String nom, String prenom, String adresse, java.sql.Date date_naissance, java.sql.Date date_embauche, 
			String tel, String rib, String type_paiement, String type_contrat, int nombre_enfants, double salaire, String email, String sexe, byte[] photo, String cin, int solde_conge) {
		this.cin = cin;
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.adresse = adresse;
		this.date_naissance = date_naissance;
		this.date_embauche = date_embauche;
		this.tel = tel;
		this.rib = rib;
		this.type_paiement = type_paiement;
		this.type_contrat = type_contrat;
		this.nombre_enfants = nombre_enfants;
		this.salaire = salaire;
		this.email = email;
		this.sexe = sexe;
		this.photo = photo;
		if(solde_conge != 0) {
			this.solde_conge = solde_conge;
		}
	}
	public Employe() {
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public java.sql.Date getDate_naissance() {
		return date_naissance;
	}
	public void setDate_naissance(java.sql.Date date_naissance) {
		this.date_naissance = date_naissance;
	}
	public java.sql.Date getDate_embauche() {
		return date_embauche;
	}
	public void setDate_embauche(java.sql.Date date_embauche) {
		this.date_embauche = date_embauche;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getRib() {
		return rib;
	}
	public void setRib(String rib) {
		this.rib = rib;
	}
	public String getType_paiement() {
		return type_paiement;
	}
	public void setType_paiement(String type_paiement) {
		this.type_paiement = type_paiement;
	}
	public String getType_contrat() {
		return type_contrat;
	}
	public void setType_contrat(String type_contrat) {
		this.type_contrat = type_contrat;
	}
	public int getNombre_enfants() {
		return nombre_enfants;
	}
	public void setNombre_enfants(int nombre_enfants) {
		this.nombre_enfants = nombre_enfants;
	}
	public double getSalaire() {
		return salaire;
	}
	public void setSalaire(double salaire) {
		this.salaire = salaire;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSexe() {
		return sexe;
	}
	public void setSexe(String sexe) {
		this.sexe = sexe;
	}
	public String getMot_de_passe() {
		return mot_de_passe;
	}
	public void setMot_de_passe(String mot_de_passe) {
		this.mot_de_passe = mot_de_passe;
	}
	
}
