package Views;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

import Data.DataEspaceAdmin;
import Data.DataEspaceEmploye;
import Data.DataLogin;
import Models.DemandeCongeTableModel;
import Models.Employe;

import javax.swing.JTextPane;
import javax.swing.JTextArea;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

import Models.AdminTableModel;
import Models.DemandeConge;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JPasswordField;
import javax.swing.UIManager;
import javax.swing.GroupLayout.Alignment;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;

import java.awt.event.InputMethodListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Date;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.awt.event.InputMethodEvent;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class EspaceEmploye extends JFrame {
	private File photo;
	private JFileChooser filechooser_photo;
	private JDateChooser calendar_dateFin;
	private JButton button_telechargerPhoto;
	private JDateChooser calendar_dateDebut;
	private JPanel contentPane;
	private DefaultTableCellRenderer centerRenderer;
	private JTable table_conges;
	private DemandeCongeTableModel congesTableModel;
	private static EspaceEmploye window;
	private JTextField textfield_nom;
	private JTextField textfield_adresse;
	private JTextField textfield_prenom;
	private JTextField textfield_cin;
	private JTextField textfield_telephone;
	private JTextField textfield_nombreEnfants;
	private JTextField textfield_contrat;
	private JTextField textfield_rib;
	private JTextField textfield_paiement;
	private JTextField textfield_dateNaissance;
	private JTextField textfield_dateEmbauche;
	private JTextField textfield_salaire;
	private JTextField textfield_sexe;
	private JTextField textfield_email;
	private ArrayList<String> erreurs;
	private ArrayList<DemandeConge> demandes;
	private Employe employe;
	private JButton button_profilEnregistrer;
	private JPanel panel_monProfil;
	private JLabel label_photo;
	private JPasswordField passwordfield_ancienMotDePasse;
	private JPasswordField passwordfield_nouveauMotDePasse;
	private JPasswordField passwordfield_confirmerNouveauMotDePasse;
	private String erreurs_saisi = "";
	private JTextField textfield_soldeConge;

	public static EspaceEmploye getInstance() {
		if (window == null) {
			window = new EspaceEmploye();
		}
		return window;
	}

	private boolean verifierSaisi(boolean profile) {
		if (profile) {
			int err_count = 0;
			Pattern p;
			Matcher m;
			if (textfield_telephone.getText().isEmpty()) {
				erreurs.add("Le champ tel est obligatoire!");
				err_count++;
			} else {
				p = Pattern.compile("0[6,7][0-9]{8}");
				m = p.matcher(textfield_telephone.getText());
				if (!m.matches()) {
					erreurs.add("Le numéro de telephone est invalide!");
					err_count++;
				}
			}

			if (textfield_rib.getText().isEmpty()) {
				erreurs.add("Le champ RIB est obligatoire!");
				err_count++;
			} else {
				p = Pattern.compile("[0-9]{24}");
				m = p.matcher(textfield_rib.getText());
				if (!m.matches()) {
					erreurs.add("Le RIB est invalide.");
					err_count++;
				}
			}

			if (textfield_email.getText().isEmpty()) {
				erreurs.add("Le champ email est obligatoire!");
				err_count++;
			} else {
				p = Pattern.compile("\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b");
				m = p.matcher(textfield_email.getText());
				if (!m.matches()) {
					erreurs.add("Adresse email invalide!");
					err_count++;
				}
			}

			if (label_photo.getIcon() == null) {
				erreurs.add("La photo est obligatoire!");
				err_count++;
			}
			
			if(err_count > 0) {
				String msgs = "";
				for (String err : erreurs)  {
					msgs = msgs.concat(err + "\n");
				}
				erreurs_saisi = msgs;
				return false;
			}
			else {
				return true;
			}
		}
		else {
			int err_count = 0;
			String msgs = "";

			if (calendar_dateDebut.getDate() == null) {
				erreurs.add("Veuillez préciser la date de début!");
				err_count++;
			} else if (calendar_dateFin.getDate() == null) {
				erreurs.add("Veuillez préciser la date de fin!");
				err_count++;
			} else if (calendar_dateFin.getDate().before(calendar_dateDebut.getDate())) {
				erreurs.add("La date de début doit etre supérieure à la date de fin!");
				err_count++;
			}
			LocalDateTime from = LocalDateTime.ofInstant(calendar_dateDebut.getDate().toInstant(), ZoneId.systemDefault());
			LocalDateTime to = LocalDateTime.ofInstant(calendar_dateFin.getDate().toInstant(), ZoneId.systemDefault());
			Duration d = Duration.between(from, to);
			if(d.toDays() > Integer.parseInt(textfield_soldeConge.getText())) {
				erreurs.add("Vous ne disposez pas d'assez de solde pour la durée précise!");
				err_count++;
			}
			if (err_count > 0) {
				for (String err : erreurs) {
					msgs = msgs.concat(err + "\n");
				}
				return false;
			} else {
				return true;
			}
		}
	}

	private void viderNouvelleDemande() {
		calendar_dateDebut.setDate(null);
		calendar_dateFin.setDate(null);
	}
	
	private void viderMotDePasse() {
		passwordfield_ancienMotDePasse.setText("");
		passwordfield_nouveauMotDePasse.setText("");
		passwordfield_confirmerNouveauMotDePasse.setText("");
	}

	/**
	 * Create the frame.
	 */
	private EspaceEmploye() {
		employe = DataLogin.getCurrentUser();
		erreurs = new ArrayList<String>();
		demandes = DataEspaceEmploye.getDemandesByEmployeId(DataLogin.getCurrentUser().getId());
		setTitle("GERH - Espace employ\u00E9");
		setResizable(false);
		congesTableModel = new DemandeCongeTableModel(demandes);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 732, 725);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 696, 674);
		contentPane.add(tabbedPane);

		JPanel panel_demandesConges = new JPanel();
		panel_demandesConges.setBorder(null);
		tabbedPane.addTab("Mes demandes", null, panel_demandesConges, null);
		panel_demandesConges.setLayout(null);

		JScrollPane scrollpane_conges = new JScrollPane();
		scrollpane_conges.setBounds(10, 11, 671, 335);
		panel_demandesConges.add(scrollpane_conges);

		table_conges = new JTable();
		scrollpane_conges.setViewportView(table_conges);
		table_conges.setModel(congesTableModel);
		centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		JPanel panel_demandesActions = new JPanel();
		panel_demandesActions
				.setBorder(new TitledBorder(null, "Actions", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_demandesActions.setBounds(432, 357, 249, 62);
		panel_demandesConges.add(panel_demandesActions);
		panel_demandesActions.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton button_annuler = new JButton("Annuler");
		button_annuler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (table_conges.getSelectedRowCount() > 0) {
					int id_demande = demandes.get(table_conges.getSelectedRow()).getId();
					if (DataEspaceEmploye.deleteDemande(id_demande)) {
						JOptionPane.showMessageDialog(null, "Demande annulée!", "Information",
								JOptionPane.INFORMATION_MESSAGE);
						updateTableDemandes();
					} else {
						JOptionPane.showMessageDialog(null, "Erreur de la suppression de la demande!", "Erreur",
								JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Aucune demande selectionnée!", "Information",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		button_annuler
				.setToolTipText("Vous pouvez annuler les demandes pas encore consult\u00E9es par les administrateurs");
		panel_demandesActions.add(button_annuler);

		JPanel panel_faireDemande = new JPanel();
		panel_faireDemande.setBorder(
				new TitledBorder(null, "Faire une demande", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_faireDemande.setBounds(10, 357, 412, 265);
		panel_demandesConges.add(panel_faireDemande);
		panel_faireDemande.setLayout(null);

		JLabel label_dateDebut = new JLabel("Date de d\u00E9but");
		label_dateDebut.setHorizontalAlignment(SwingConstants.LEFT);
		label_dateDebut.setBounds(52, 40, 125, 14);
		panel_faireDemande.add(label_dateDebut);

		JLabel label_dateFin = new JLabel("Date de fin");
		label_dateFin.setHorizontalAlignment(SwingConstants.LEFT);
		label_dateFin.setBounds(52, 99, 125, 14);
		panel_faireDemande.add(label_dateFin);

		calendar_dateDebut = new JDateChooser();
		calendar_dateDebut.setBounds(52, 65, 125, 23);
		panel_faireDemande.add(calendar_dateDebut);
		//calendar_dateDebut.setSelectableDateRange(Date.from(java.time.LocalDate.now().), java.time.LocalDate.now().plusYears(100));
		calendar_dateDebut.setMinSelectableDate(java.util.Date.from(Instant.now()));
		calendar_dateFin = new JDateChooser();
		calendar_dateFin.setBounds(52, 124, 125, 23);
		panel_faireDemande.add(calendar_dateFin);
		calendar_dateFin.setMinSelectableDate(java.util.Date.from(Instant.now().plusSeconds(86400)));

		JLabel label_typeConge = new JLabel("Type de cong\u00E9");
		label_typeConge.setHorizontalAlignment(SwingConstants.LEFT);
		label_typeConge.setBounds(225, 40, 125, 14);
		panel_faireDemande.add(label_typeConge);

		JComboBox combobox_typeConge = new JComboBox();
		combobox_typeConge
				.setModel(new DefaultComboBoxModel(new String[] { "PAYE", "RECUPERATION", "MATERNITE", "MALADIE" }));
		combobox_typeConge.setBounds(225, 65, 125, 23);
		panel_faireDemande.add(combobox_typeConge);

		JButton button_congesEnregistrer = new JButton("Enregistrer");
		button_congesEnregistrer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!verifierSaisi(false)) {
					String msgs = "";
					for (String err : erreurs) {
						msgs = msgs.concat(err + "\n");
					}
					JOptionPane.showMessageDialog(null, msgs);
				} else {
					DemandeConge demande = new DemandeConge();
					demande.setSalarie_id(DataLogin.getCurrentUser().getId());
					demande.setDate_debut(new java.sql.Date(calendar_dateDebut.getDate().getTime()));
					demande.setDate_fin(new java.sql.Date(calendar_dateFin.getDate().getTime()));
					demande.setStatut("en-attente");
					demande.setType_conge(combobox_typeConge.getSelectedItem().toString());

					if (DataEspaceEmploye.addDemande(demande)) {
						JOptionPane.showMessageDialog(null, "Demande ajouté1!", "Information",
								JOptionPane.INFORMATION_MESSAGE);
						updateTableDemandes();
						viderNouvelleDemande();
					} else {
						JOptionPane.showConfirmDialog(null, "Erreur de l'ajout de la demande!", "Erreur",
								JOptionPane.ERROR_MESSAGE);
					}
				}

				erreurs = new ArrayList<String>();
			}

		});
		button_congesEnregistrer.setBounds(108, 190, 89, 34);
		panel_faireDemande.add(button_congesEnregistrer);

		JButton button_congesVider = new JButton("Vider");
		button_congesVider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viderNouvelleDemande();
			}
		});
		button_congesVider.setBounds(207, 190, 89, 34);
		panel_faireDemande.add(button_congesVider);
		
		JLabel label_soldeConge = new JLabel("Mon solde de cong\u00E9");
		label_soldeConge.setHorizontalAlignment(SwingConstants.LEFT);
		label_soldeConge.setBounds(225, 99, 125, 14);
		panel_faireDemande.add(label_soldeConge);
		
		textfield_soldeConge = new JTextField();
		textfield_soldeConge.setBounds(225, 124, 125, 23);
		panel_faireDemande.add(textfield_soldeConge);
		textfield_soldeConge.setColumns(10);
		textfield_soldeConge.setHorizontalAlignment(JTextField.CENTER);
		textfield_soldeConge.setEditable(false);
		textfield_soldeConge.setText(DataLogin.getCurrentUser().getSolde()+"");

		JLabel lblVousPouvez = new JLabel("* Vous pouvez annuler une demande en attente");
		lblVousPouvez.setFont(new Font("Tahoma", Font.ITALIC, 9));
		lblVousPouvez.setBounds(432, 421, 249, 16);
		panel_demandesConges.add(lblVousPouvez);

		panel_monProfil = new JPanel();
		tabbedPane.addTab("Mon profil", null, panel_monProfil, null);
		panel_monProfil.setLayout(null);

		JPanel panel_informationsPersonnelles = new JPanel();
		panel_informationsPersonnelles.setBorder(new TitledBorder(null, "Informations personnelles",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_informationsPersonnelles.setBounds(10, 11, 671, 246);
		panel_monProfil.add(panel_informationsPersonnelles);
		panel_informationsPersonnelles.setLayout(null);

		JLabel label_nom = new JLabel("Nom");
		label_nom.setBounds(189, 43, 100, 14);
		panel_informationsPersonnelles.add(label_nom);

		JLabel label_prenom = new JLabel("Pr\u00E9nom");
		label_prenom.setBounds(419, 43, 64, 14);
		panel_informationsPersonnelles.add(label_prenom);

		JLabel label_adresse = new JLabel("Adresse");
		label_adresse.setBounds(189, 164, 64, 14);
		panel_informationsPersonnelles.add(label_adresse);

		JLabel label_telephone = new JLabel("Telephone");
		label_telephone.setBounds(189, 84, 100, 14);
		panel_informationsPersonnelles.add(label_telephone);

		JLabel label_nombreEnfants = new JLabel("Nombre d'enfants");
		label_nombreEnfants.setBounds(440, 126, 100, 14);
		panel_informationsPersonnelles.add(label_nombreEnfants);

		JLabel label_cin = new JLabel("CIN");
		label_cin.setBounds(419, 84, 64, 14);
		panel_informationsPersonnelles.add(label_cin);

		textfield_nom = new JTextField();
		textfield_nom.setBounds(258, 39, 134, 23);
		panel_informationsPersonnelles.add(textfield_nom);
		textfield_nom.setColumns(10);

		textfield_adresse = new JTextField();
		textfield_adresse.setColumns(10);
		textfield_adresse.setBounds(258, 160, 369, 23);
		panel_informationsPersonnelles.add(textfield_adresse);

		textfield_prenom = new JTextField();
		textfield_prenom.setColumns(10);
		textfield_prenom.setBounds(493, 39, 134, 23);
		panel_informationsPersonnelles.add(textfield_prenom);

		textfield_cin = new JTextField();
		textfield_cin.setColumns(10);
		textfield_cin.setBounds(493, 80, 134, 23);
		panel_informationsPersonnelles.add(textfield_cin);

		textfield_telephone = new JTextField();
		textfield_telephone.setColumns(10);
		textfield_telephone.setBounds(258, 80, 134, 23);
		panel_informationsPersonnelles.add(textfield_telephone);

		textfield_nombreEnfants = new JTextField();
		textfield_nombreEnfants.setColumns(10);
		textfield_nombreEnfants.setBounds(544, 122, 83, 23);
		panel_informationsPersonnelles.add(textfield_nombreEnfants);

		label_photo = new JLabel("");
		label_photo.setBounds(35, 29, 110, 139);
		label_photo.setBorder(new TitledBorder("Photo"));
		panel_informationsPersonnelles.add(label_photo);

		button_telechargerPhoto = new JButton("Telecharger");
		button_telechargerPhoto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				filechooser_photo = new JFileChooser();
				String[] extensions_valides = {"png", "jpeg", "jpg", "bmp"};
				filechooser_photo.showOpenDialog(null);
				File f = filechooser_photo.getSelectedFile();
				String filename = f.getName();
				String fileExtension = filename.substring(filename.lastIndexOf("."),filename.length());
				StringBuilder sb = new StringBuilder(fileExtension);
				fileExtension = sb.deleteCharAt(0).toString().toLowerCase();
				boolean fichier_valide = false;
				for(String ext : extensions_valides) {
					if(ext.equals(fileExtension)) {
						fichier_valide =  true;
					}
				}
				if(!fichier_valide) {
					JOptionPane.showMessageDialog(null, "Le fichier choisi n'est pas une image!", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
				else {
					//label_nomImageChoisi.setText(f.getName());
					photo = f;
					BufferedImage image;
					try {
						image = ImageIO.read(photo);
						Image img = image.getScaledInstance(label_photo.getWidth(), label_photo.getHeight(), Image.SCALE_SMOOTH);
						label_photo.setIcon(new ImageIcon(img));
					}catch(Exception ex) {
						ex.printStackTrace();
					}
					
				}
				
				
			}
		});
		button_telechargerPhoto.setBounds(35, 177, 110, 23);
		panel_informationsPersonnelles.add(button_telechargerPhoto);

		JLabel label_dateNaissance = new JLabel("Date de naissance");
		label_dateNaissance.setBounds(189, 126, 100, 14);
		panel_informationsPersonnelles.add(label_dateNaissance);

		textfield_dateNaissance = new JTextField();
		textfield_dateNaissance.setColumns(10);
		textfield_dateNaissance.setBounds(299, 122, 134, 23);
		panel_informationsPersonnelles.add(textfield_dateNaissance);

		JLabel label_sexe = new JLabel("Sexe");
		label_sexe.setBounds(189, 203, 100, 14);
		panel_informationsPersonnelles.add(label_sexe);

		textfield_sexe = new JTextField();
		textfield_sexe.setColumns(10);
		textfield_sexe.setBounds(258, 200, 64, 23);
		panel_informationsPersonnelles.add(textfield_sexe);

		textfield_email = new JTextField();
		textfield_email.setColumns(10);
		textfield_email.setBounds(440, 200, 189, 23);
		panel_informationsPersonnelles.add(textfield_email);

		JLabel label_email = new JLabel("Adresse email");
		label_email.setBounds(353, 204, 100, 14);
		panel_informationsPersonnelles.add(label_email);

		JPanel panel_contratEtPaie = new JPanel();
		panel_contratEtPaie.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Contrat et paie",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_contratEtPaie.setBounds(10, 268, 671, 143);
		panel_monProfil.add(panel_contratEtPaie);
		panel_contratEtPaie.setLayout(null);

		JLabel label_contrat = new JLabel("Contrat");
		label_contrat.setBounds(82, 31, 64, 14);
		panel_contratEtPaie.add(label_contrat);

		textfield_contrat = new JTextField();
		textfield_contrat.setBounds(156, 27, 134, 23);
		panel_contratEtPaie.add(textfield_contrat);
		textfield_contrat.setColumns(10);

		JLabel label_paiement = new JLabel("Paiement");
		label_paiement.setBounds(82, 65, 64, 14);
		panel_contratEtPaie.add(label_paiement);

		textfield_paiement = new JTextField();
		textfield_paiement.setBounds(156, 61, 134, 23);
		panel_contratEtPaie.add(textfield_paiement);
		textfield_paiement.setColumns(10);

		JLabel label_rib = new JLabel("RIB");
		label_rib.setBounds(82, 100, 100, 14);
		panel_contratEtPaie.add(label_rib);

		textfield_rib = new JTextField();
		textfield_rib.setBounds(156, 96, 411, 23);
		panel_contratEtPaie.add(textfield_rib);
		textfield_rib.setColumns(10);

		JLabel label_dateEmbauche = new JLabel("Date d'embauche");
		label_dateEmbauche.setBounds(323, 31, 100, 14);
		panel_contratEtPaie.add(label_dateEmbauche);

		textfield_dateEmbauche = new JTextField();
		textfield_dateEmbauche.setColumns(10);
		textfield_dateEmbauche.setBounds(433, 27, 134, 23);
		panel_contratEtPaie.add(textfield_dateEmbauche);

		JLabel label_salaire = new JLabel("Salaire");
		label_salaire.setBounds(323, 65, 64, 14);
		panel_contratEtPaie.add(label_salaire);

		textfield_salaire = new JTextField();
		textfield_salaire.setColumns(10);
		textfield_salaire.setBounds(433, 61, 134, 23);
		panel_contratEtPaie.add(textfield_salaire);

		JPanel panel_authentification = new JPanel();
		panel_authentification.setBorder(
				new TitledBorder(null, "Authentification", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_authentification.setBounds(10, 422, 352, 213);
		panel_monProfil.add(panel_authentification);
		panel_authentification.setLayout(null);

		JLabel label_ancienMotDePasse = new JLabel("Ancien mot de passe");
		label_ancienMotDePasse.setBounds(21, 24, 144, 14);
		panel_authentification.add(label_ancienMotDePasse);

		passwordfield_ancienMotDePasse = new JPasswordField();
		passwordfield_ancienMotDePasse.setBounds(21, 49, 144, 23);
		panel_authentification.add(passwordfield_ancienMotDePasse);

		JLabel label_nouveauMotDePasse = new JLabel("Nouveau mot de passe");
		label_nouveauMotDePasse.setBounds(21, 83, 144, 14);
		panel_authentification.add(label_nouveauMotDePasse);

		passwordfield_nouveauMotDePasse = new JPasswordField();
		passwordfield_nouveauMotDePasse.setBounds(21, 108, 144, 23);
		panel_authentification.add(passwordfield_nouveauMotDePasse);

		JLabel label_confirmerNouveauMotDePasse = new JLabel("Confirmer le mot de passe");
		label_confirmerNouveauMotDePasse.setBounds(21, 143, 144, 14);
		panel_authentification.add(label_confirmerNouveauMotDePasse);

		passwordfield_confirmerNouveauMotDePasse = new JPasswordField();
		passwordfield_confirmerNouveauMotDePasse.setBounds(21, 168, 144, 23);
		panel_authentification.add(passwordfield_confirmerNouveauMotDePasse);
		
		JButton btnMettreJour = new JButton("Mettre \u00E0 jour");
		btnMettreJour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ArrayList<String> erreurs = new ArrayList<String>();
				String msgs = "";
				int err_count = 0;
				if(passwordfield_ancienMotDePasse.getText().isEmpty()) {
					erreurs.add("L'ancien mot de passe est obligatoire!");
					err_count++;
				}
				else if(passwordfield_nouveauMotDePasse.getText().isEmpty()) {
					erreurs.add("Veuillez saisir le nouveau mot de passe!");
					err_count++;
				}
				else if(passwordfield_confirmerNouveauMotDePasse.getText().isEmpty()) {
					erreurs.add("Veuillez confirmer le nouveau mot de passe!");
					err_count++;
				}
				else {
					if(!passwordfield_nouveauMotDePasse.getText().equals(passwordfield_confirmerNouveauMotDePasse.getText())) {
						erreurs.add("Les mots de passe ne correspondent pas!");
						err_count++;
					}
					if(!passwordfield_ancienMotDePasse.getText().equals(DataLogin.getCurrentUser().getMot_de_passe())) {
						erreurs.add("Ancien mot de passe incorrecte!");
						err_count++;
					}
				}
				
				if(err_count > 0) {
					for(String err : erreurs) {
						msgs = msgs.concat(err+"\n");
					}
					JOptionPane.showMessageDialog(null, msgs, "Erreur", JOptionPane.ERROR_MESSAGE);
				}else {
					if(DataLogin.updateEmployePassword(DataLogin.getCurrentUser().getId(), passwordfield_nouveauMotDePasse.getText())) {
						JOptionPane.showMessageDialog(null, "Mot de passe mis à jour!", "Information", JOptionPane.INFORMATION_MESSAGE);
						viderMotDePasse();
					}
					else {
						JOptionPane.showMessageDialog(null, "L'ancien mot de passe est incorrecte!", "Erreur", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnMettreJour.setBounds(212, 79, 101, 30);
		panel_authentification.add(btnMettreJour);
		
		JButton button_viderMotDePasse = new JButton("Vider");
		button_viderMotDePasse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viderMotDePasse();
			}
		});
		button_viderMotDePasse.setBounds(212, 127, 101, 30);
		panel_authentification.add(button_viderMotDePasse);

		JPanel panel_profilActions = new JPanel();
		panel_profilActions
				.setBorder(new TitledBorder(null, "Actions", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_profilActions.setBounds(524, 422, 157, 143);
		panel_monProfil.add(panel_profilActions);
		panel_profilActions.setLayout(null);

		button_profilEnregistrer = new JButton("Enregistrer");
		button_profilEnregistrer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!verifierSaisi(true)) {
					// erreurs de saisi
					JOptionPane.showMessageDialog(null, erreurs_saisi, "Erreur", JOptionPane.ERROR_MESSAGE);
					erreurs_saisi = "";
				}
				else {
					employe.setAdresse(textfield_adresse.getText());
					employe.setTel(textfield_telephone.getText());
					employe.setEmail(textfield_email.getText());
					if(DataEspaceAdmin.updateEmploye(employe, photo)) {
						JOptionPane.showMessageDialog(null, "Informations mises à jour!", "Information", JOptionPane.INFORMATION_MESSAGE);
						employe = DataLogin.getCurrentUser();
						fillUserData();
					}
					else {
						JOptionPane.showMessageDialog(null, "Erreur de la mise à jour!", "Erreur", JOptionPane.ERROR_MESSAGE);
					}
				}
				erreurs = new ArrayList<String>();
			}
		});
		button_profilEnregistrer.setBounds(32, 82, 89, 23);
		panel_profilActions.add(button_profilEnregistrer);

		JToggleButton toggle_modifierProfil = new JToggleButton("Modifier");
		toggle_modifierProfil.setBounds(32, 48, 89, 23);
		panel_profilActions.add(toggle_modifierProfil);
		toggle_modifierProfil.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				changeEditableStatus(toggle_modifierProfil.isSelected());
			}
		});
		updateTableDemandes();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.fillUserData();
	}

	private void updateTableDemandes() {
		demandes = DataEspaceEmploye.getDemandesByEmployeId(DataLogin.getCurrentUser().getId());
		table_conges.setModel(new DemandeCongeTableModel(demandes));
		for (int i = 0; i < table_conges.getColumnCount(); i++) {
			table_conges.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
		table_conges.getColumnModel().removeColumn(table_conges.getColumnModel().getColumn(0));
		table_conges.getColumnModel().removeColumn(table_conges.getColumnModel().getColumn(0));
	}

	private void fillUserData() {
		this.textfield_nom.setText(employe.getNom());
		this.textfield_prenom.setText(employe.getPrenom());
		this.textfield_dateNaissance.setText(employe.getDate_naissance().toString());
		this.textfield_dateEmbauche.setText(employe.getDate_embauche().toString());
		this.textfield_telephone.setText(employe.getTel());
		this.textfield_email.setText(employe.getEmail());
		this.textfield_contrat.setText(employe.getType_contrat().toUpperCase());
		this.textfield_adresse.setText(employe.getAdresse());
		this.textfield_sexe.setText(employe.getSexe().toUpperCase());
		JOptionPane.showMessageDialog(null, "SALAIRE : " + employe.getSalaire());
		this.textfield_salaire.setText(String.valueOf(employe.getSalaire()));
		this.textfield_rib.setText(employe.getRib());
		this.textfield_cin.setText(employe.getCin().toUpperCase());
		this.textfield_nombreEnfants.setText(String.valueOf(employe.getNombre_enfants()));
		this.textfield_paiement.setText(employe.getType_paiement().toUpperCase());
		BufferedImage image = null;
		try {
			image = ImageIO.read(new ByteArrayInputStream(employe.getPhoto()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "ERREUR DE CONVERSION DE L'IMAGE");
		}
		Image img = image.getScaledInstance(label_photo.getWidth(), label_photo.getHeight(), Image.SCALE_SMOOTH);
		label_photo.setIcon(new ImageIcon(img));
		button_telechargerPhoto.setVisible(false);
		textfield_nom.setEditable(false);
		textfield_prenom.setEditable(false);
		textfield_dateNaissance.setEditable(false);
		textfield_dateEmbauche.setEditable(false);
		button_profilEnregistrer.setEnabled(false);
		textfield_adresse.setEditable(false);
		textfield_telephone.setEditable(false);
		textfield_contrat.setEnabled(false);
		textfield_email.setEditable(false);
		textfield_cin.setEditable(false);
		textfield_sexe.setEditable(false);
		textfield_paiement.setEnabled(false);
		textfield_rib.setEditable(false);
		textfield_salaire.setEditable(false);
		textfield_nombreEnfants.setEditable(false);
		button_profilEnregistrer.setVisible(false);
	}

	private void changeEditableStatus(boolean val) {
		button_profilEnregistrer.setVisible(val);
		textfield_adresse.setEditable(val);
		textfield_telephone.setEditable(val);
		button_telechargerPhoto.setVisible(val);
		button_profilEnregistrer.setEnabled(val);
	}
}
