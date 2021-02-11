package Views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.icons.FlatFileViewFloppyDriveIcon;
import com.formdev.flatlaf.icons.FlatInternalFrameRestoreIcon;
import com.formdev.flatlaf.icons.FlatTreeOpenIcon;
import com.formdev.flatlaf.icons.FlatWindowCloseIcon;

import Models.Admin;
import Models.AdminTableModel;
import Models.DemandeConge;
import Models.DemandeCongeTableModel;
import Models.Employe;
import Models.EmployeTableModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;

import java.awt.Font;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import java.awt.Component;

import javax.swing.border.CompoundBorder;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.toedter.calendar.JDateChooser;

import Data.DataEspaceAdmin;
import Data.DataFicheEmploye;
import Data.DataLogin;

import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Window;
import java.awt.GridBagLayout;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.awt.GridBagConstraints;
import javax.swing.DefaultComboBoxModel;

public class EspaceAdministrateur {
	private JComboBox combobox_sexe;
	private String[] extensions_valides = {"png", "jpeg", "jpg", "bmp"};
	private String erreurs_saisi = "";
	private JDateChooser calendar_dateEmbauche;
	private JFrame frmGerhEspace;
	private JTable table_employes;
	private EmployeTableModel employes_table_model;
	private ArrayList<Employe> employes;
	private DemandeCongeTableModel conges_table_model;
	private ArrayList<DemandeConge> conges;
	private AdminTableModel admins_table_model;
	private ArrayList<Models.Admin> admins;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField textfield_nom;
	private JTextField textfield_adresse;
	private JTextField textfield_prenom;
	private JTextField textfield_nombreEnfants;
	private JTextField textfield_cin;
	private JTextField textfield_tel;
	private JTextField textfield_rib;
	private JTable table_conges;
	private JPasswordField passwordfield_ancienMotDePasse;
	private JPasswordField passwordfield_nouveauMotDePasse;
	private JPasswordField passwordfield_nouveauAdminMotDePasse;
	private JPasswordField passwordfield_confirmerNouveauAdminMotDePasse;
	private JTable table_administrateurs;
	private static EspaceAdministrateur window;
	private JTextField textfield_nouveauAdminNomUtilisateur;
	private ArrayList<String> erreurs;
	private JTextField textfield_email;
	private JDateChooser calendar_dateNaissance;
	private JTextField textfield_salaire;
	private JFileChooser filechooser_photo;
	private File photo = null;
	private JLabel label_nomImageChoisi;
	private DefaultTableCellRenderer centerRenderer;
	JPanel panel_ajouterEmploye;
	private JPasswordField passwordfield_confirmerNouveauMotDePasse;
	public static EspaceAdministrateur getInstance() {
		if(window == null) window = new EspaceAdministrateur();
		return window;
	}

	/**
	 * Create the application.
	 */
	private EspaceAdministrateur() {
		admins = DataEspaceAdmin.getAdmins();
		erreurs = new ArrayList<String>();
		employes = DataEspaceAdmin.getEmployes();
		conges = DataEspaceAdmin.getConges();
		initialize();
		frmGerhEspace.setVisible(true);
	}
	
	private void clearForm() {
		Component[] components = panel_ajouterEmploye.getComponents();
		for(Component comp : components) {
			if(comp.getClass().toString().contains("JTextField")) {
				JTextField tmp = (JTextField) comp;
				tmp.setText("");
			}else if(comp.getClass().toString().contains("JDateChooser")) {
				JDateChooser tmp = (JDateChooser) comp;
				tmp.setDate(null);
			}
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		filechooser_photo = new JFileChooser();
		filechooser_photo.setFileFilter(new FileNameExtensionFilter("Images", "jpg", "png", "bmp"));
		employes_table_model = new EmployeTableModel(employes);
		conges_table_model = new DemandeCongeTableModel(conges);
		//admins = new ArrayList<Models.Admin>();
		admins_table_model = new AdminTableModel(admins);
		frmGerhEspace = new JFrame();
		frmGerhEspace.setTitle("GERH : Espace Administrateur");
		frmGerhEspace.setBounds(100, 100, 732, 701);
		frmGerhEspace.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmGerhEspace.setResizable(false);
		frmGerhEspace.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 2, 706, 659);
		tabbedPane.setBorder(null);
		frmGerhEspace.getContentPane().add(tabbedPane);
		
		JPanel tab_employes = new JPanel();
		tabbedPane.addTab("Employes", null, tab_employes, null);
		tab_employes.setLayout(null);
		
		JScrollPane scrollpane_tableEmployes = new JScrollPane();
		scrollpane_tableEmployes.setBounds(10, 11, 681, 193);
		tab_employes.add(scrollpane_tableEmployes);
		
		table_employes = new JTable();
		
		
		table_employes.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollpane_tableEmployes.setViewportView(table_employes);
		table_employes.setModel(employes_table_model);
		for(int i=0; i<table_employes.getColumnCount(); i++) {
			table_employes.getColumnModel().getColumn(i).setCellRenderer( centerRenderer );	
		}
		
		JPanel panel_actions = new JPanel();
		panel_actions.setBorder(new TitledBorder(null, "Actions", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_actions.setBounds(10, 215, 681, 62);
		tab_employes.add(panel_actions);
		
		JButton button_modifier = new JButton("Modifier");
		button_modifier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//ficheEmploye.getInstance();	
				if(table_employes.getSelectedRowCount() == 0 ) {
					JOptionPane.showMessageDialog(null, "Aucune ligne selectionnée!", "Information", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					FicheEmploye.getInstance(true, employes.get(table_employes.getSelectedRow()).getId());
				}
			}
		});
		panel_actions.add(button_modifier);
		buttonGroup.add(button_modifier);
		
		JButton button_supprimer = new JButton("Supprimer");
		button_supprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(table_employes.getSelectedRowCount() == 0 ) {
					JOptionPane.showMessageDialog(null, "Aucune ligne selectionnée!", "Information", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					if(DataFicheEmploye.deleteEmploye(employes.get(table_employes.getSelectedRow()).getId())) {
						JOptionPane.showMessageDialog(null, "Employé supprimé!", "Information", JOptionPane.INFORMATION_MESSAGE);
					}else {
						JOptionPane.showMessageDialog(null, "Echec de la suppression!", "Erreur", JOptionPane.ERROR_MESSAGE);
					}
					employes = DataEspaceAdmin.getEmployes();
					employes_table_model = new EmployeTableModel(employes);
					table_employes.setModel(employes_table_model);
					centerRenderer.setHorizontalAlignment( JLabel.CENTER );				}
				}
		});
		panel_actions.add(button_supprimer);
		buttonGroup.add(button_supprimer);
		
		JButton button_ficheComplete = new JButton("Fiche compl\u00E8te");
		button_ficheComplete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//JOptionPane.showMessageDialog(parentComponent, message);;
				if(table_employes.getSelectedRowCount() == 0 ) {
					JOptionPane.showMessageDialog(null, "Aucune ligne selectionnée!", "Information", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					FicheEmploye.getInstance(false, employes.get(table_employes.getSelectedRow()).getId());
				}			
			}
		});
		panel_actions.add(button_ficheComplete);
		buttonGroup.add(button_ficheComplete);
		
		panel_ajouterEmploye = new JPanel();
		panel_ajouterEmploye.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Ajouter employ\u00E9s", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_ajouterEmploye.setBounds(10, 288, 681, 332);
		tab_employes.add(panel_ajouterEmploye);
		panel_ajouterEmploye.setLayout(null);
		
		JLabel label_nom = new JLabel("Nom");
		label_nom.setBackground(Color.WHITE);
		label_nom.setHorizontalAlignment(SwingConstants.CENTER);
		label_nom.setBounds(48, 31, 142, 14);
		panel_ajouterEmploye.add(label_nom);
		
		textfield_nom = new JTextField();
		textfield_nom.setBounds(48, 56, 142, 23);
		panel_ajouterEmploye.add(textfield_nom);
		textfield_nom.setColumns(10);
		
		JLabel label_prenom = new JLabel("Prenom");
		label_prenom.setBackground(Color.WHITE);
		label_prenom.setHorizontalAlignment(SwingConstants.CENTER);
		label_prenom.setBounds(200, 31, 142, 14);
		panel_ajouterEmploye.add(label_prenom);
		
		JLabel label_adresse = new JLabel("Adresse");
		label_adresse.setBackground(Color.WHITE);
		label_adresse.setHorizontalAlignment(SwingConstants.CENTER);
		label_adresse.setBounds(48, 90, 187, 14);
		panel_ajouterEmploye.add(label_adresse);
		
		textfield_adresse = new JTextField();
		textfield_adresse.setColumns(10);
		textfield_adresse.setBounds(48, 109, 187, 23);
		panel_ajouterEmploye.add(textfield_adresse);
		
		textfield_prenom = new JTextField();
		textfield_prenom.setColumns(10);
		textfield_prenom.setBounds(200, 56, 142, 23);
		panel_ajouterEmploye.add(textfield_prenom);
		
		textfield_nombreEnfants = new JTextField();
		textfield_nombreEnfants.setColumns(10);
		textfield_nombreEnfants.setBounds(48, 168, 96, 23);
		panel_ajouterEmploye.add(textfield_nombreEnfants);
		
		JLabel label_nombreEnfants = new JLabel("Nbr d'enfants");
		label_nombreEnfants.setBackground(Color.WHITE);
		label_nombreEnfants.setHorizontalAlignment(SwingConstants.CENTER);
		label_nombreEnfants.setBounds(48, 143, 96, 14);
		panel_ajouterEmploye.add(label_nombreEnfants);
		
		textfield_cin = new JTextField();
		textfield_cin.setColumns(10);
		textfield_cin.setBounds(245, 109, 96, 23);
		panel_ajouterEmploye.add(textfield_cin);
		
		JLabel label_cin = new JLabel("CIN");
		label_cin.setBackground(Color.WHITE);
		label_cin.setHorizontalAlignment(SwingConstants.CENTER);
		label_cin.setBounds(246, 90, 96, 14);
		panel_ajouterEmploye.add(label_cin);
		
		textfield_tel = new JTextField();
		textfield_tel.setColumns(10);
		textfield_tel.setBounds(154, 168, 188, 23);
		panel_ajouterEmploye.add(textfield_tel);
		
		JLabel label_tel = new JLabel("Telephone");
		label_tel.setBackground(Color.WHITE);
		label_tel.setHorizontalAlignment(SwingConstants.CENTER);
		label_tel.setBounds(154, 143, 188, 14);
		panel_ajouterEmploye.add(label_tel);
		
		JLabel label_rib = new JLabel("RIB");
		label_rib.setBackground(Color.WHITE);
		label_rib.setHorizontalAlignment(SwingConstants.CENTER);
		label_rib.setBounds(48, 202, 294, 14);
		panel_ajouterEmploye.add(label_rib);
		
		textfield_rib = new JTextField();
		textfield_rib.setColumns(10);
		textfield_rib.setBounds(48, 227, 294, 23);
		panel_ajouterEmploye.add(textfield_rib);
		
		JLabel label_paiement = new JLabel("Paiement");
		label_paiement.setHorizontalAlignment(SwingConstants.CENTER);
		label_paiement.setBackground(Color.WHITE);
		label_paiement.setBounds(352, 143, 142, 14);
		panel_ajouterEmploye.add(label_paiement);
		
		JComboBox combobox_paiement = new JComboBox();
		combobox_paiement.setBounds(352, 168, 142, 23);
		combobox_paiement.addItem("CHEQUE");
		combobox_paiement.addItem("VIREMENT");
		panel_ajouterEmploye.add(combobox_paiement);
		
		JLabel label_contrat = new JLabel("Contrat");
		label_contrat.setHorizontalAlignment(SwingConstants.CENTER);
		label_contrat.setBackground(Color.WHITE);
		label_contrat.setBounds(352, 202, 142, 14);
		panel_ajouterEmploye.add(label_contrat);
		
		JComboBox combobox_contrat = new JComboBox();
		combobox_contrat.setBounds(352, 227, 142, 23);
		combobox_contrat.addItem("CDI");
		combobox_contrat.addItem("CDD");
		combobox_contrat.addItem("ANAPEC");
		panel_ajouterEmploye.add(combobox_contrat);
		
		JLabel label_dateNaissance = new JLabel("Date de naissance");
		label_dateNaissance.setHorizontalAlignment(SwingConstants.CENTER);
		label_dateNaissance.setBackground(Color.WHITE);
		label_dateNaissance.setBounds(352, 31, 142, 14);
		panel_ajouterEmploye.add(label_dateNaissance);
		
		JLabel label_photo = new JLabel("");
		label_photo.setBounds(504, 31, 129, 159);
		label_photo.setBorder(new TitledBorder("Photo"));
		panel_ajouterEmploye.add(label_photo);
		
		JButton button_telechargerPhoto = new JButton("Telecharger");
		button_telechargerPhoto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
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
					label_nomImageChoisi.setText(f.getName());
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
		button_telechargerPhoto.setIcon(new FlatTreeOpenIcon());
		button_telechargerPhoto.setBounds(504, 193, 129, 32);
		panel_ajouterEmploye.add(button_telechargerPhoto);
		
		JLabel label_dateEmbauche = new JLabel("Date d'embauche");
		label_dateEmbauche.setHorizontalAlignment(SwingConstants.CENTER);
		label_dateEmbauche.setBackground(Color.WHITE);
		label_dateEmbauche.setBounds(352, 90, 142, 14);
		panel_ajouterEmploye.add(label_dateEmbauche);
		
		JButton button_enregistrerEmploye = new JButton();
		button_enregistrerEmploye.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(verifierSaisi()) {
					Employe employe = new Employe();
					employe.setNom(textfield_nom.getText());
					employe.setPrenom(textfield_prenom.getText());
					employe.setAdresse(textfield_adresse.getText());
					employe.setDate_embauche(new java.sql.Date(calendar_dateEmbauche.getDate().getTime()));
					employe.setDate_naissance(new java.sql.Date(calendar_dateNaissance.getDate().getTime()));
					employe.setNombre_enfants(Integer.parseInt(textfield_nombreEnfants.getText()));
					employe.setTel(textfield_tel.getText());
					employe.setType_paiement(combobox_paiement.getSelectedItem().toString().toLowerCase());
					employe.setType_contrat(combobox_contrat.getSelectedItem().toString().toLowerCase());
					employe.setSalaire(Double.parseDouble(textfield_salaire.getText()));
					employe.setRib(textfield_rib.getText());
					employe.setEmail(textfield_email.getText());
					employe.setCin(textfield_cin.getText());
					employe.setSexe(combobox_sexe.getSelectedItem().toString());
					byte[] photoInBytes = null;
					try {
						photoInBytes = Files.readAllBytes(photo.toPath());
						JOptionPane.showMessageDialog(null, photoInBytes.length);
						employe.setPhoto(photoInBytes);
						if(DataEspaceAdmin.addEmploye(employe, photo)) {
							employes = DataEspaceAdmin.getEmployes();
							employes_table_model = new EmployeTableModel(employes);
							table_employes.setModel(employes_table_model);
							centerRenderer.setHorizontalAlignment( JLabel.CENTER );
						}
					}catch(IOException ex) {
						JOptionPane.showMessageDialog(null, "Impossible de convertir le File en byte[]");
					}
				}
				else {
					JOptionPane.showMessageDialog(null, erreurs_saisi, "Erreur", JOptionPane.ERROR_MESSAGE);
					erreurs_saisi = "";
					erreurs = new ArrayList<String>();
				}
								
			}

			private boolean verifierSaisi() {
				String pattern = "";
				Pattern p = null;
				Matcher m = null;
				int err_count = 0;
				
				if(textfield_salaire.getText().isEmpty()) {
					erreurs.add("Le champ salaire est obligatoire!");
					err_count++;
				}
				else {
					p = Pattern.compile("^[0-9]+(\\.[0-9]+)?$");
					m = p.matcher(textfield_salaire.getText());
					if(!m.matches()) {
						erreurs.add("Le champ salaire est invalide!");
						err_count++;
					}
				}
				
				if(textfield_nom.getText().isEmpty()) {
					erreurs.add("Le champ nom est obligatoire!");
					err_count++;
				}
				if(textfield_prenom.getText().isEmpty()) {
					erreurs.add("Le champ prénom est obligatoire!");
					err_count++;
				}
				if(textfield_adresse.getText().isEmpty()) {
					erreurs.add("Le champ adresse est obligatoire!");
					err_count++;
				}
				if(textfield_cin.getText().isEmpty()) {
					erreurs.add("Le champ CIN est obligatoire!");
					err_count++;
				}
				else if(textfield_cin.getText().length() > 8 || textfield_cin.getText().length() < 8) {
					erreurs.add("Le format du CIN est incorrecte.");
					err_count++;
				}
				
				if(calendar_dateNaissance.getDate() == null) {
					erreurs.add("Le champ date de naissance est obligatoire!");
					err_count++;
				}
				if(calendar_dateEmbauche.getDate() == null) {
					erreurs.add("Le champ date d'embauche est obligatoire!");
					err_count++;
				}
				
				if(textfield_nombreEnfants.getText().isEmpty()) {
					erreurs.add("Le champ nombre d'enfants est obligatoire!");
					err_count++;
				}
				else {
					p = Pattern.compile("[0-9]*");
					m = p.matcher(textfield_nombreEnfants.getText());
					if(!m.matches()) {
						erreurs.add("Le champ nombre enfants est invalide.");
						err_count++;
					}
				}
				if(textfield_tel.getText().isEmpty()) {
					erreurs.add("Le champ tel est obligatoire!");
					err_count++;
				}
				else {
					p = Pattern.compile("0[6,7][0-9]{8}");
					m = p.matcher(textfield_tel.getText());
					if(!m.matches()) {
						erreurs.add("Le numéro de telephone est invalide!");
						err_count++;
					}
				}
				
				if(textfield_rib.getText().isEmpty()) {
					erreurs.add("Le champ RIB est obligatoire!");
					err_count++;
				}
				else {
					p = Pattern.compile("[0-9]{24}");
					m = p.matcher(textfield_rib.getText());
					if(!m.matches()) {
						erreurs.add("Le RIB est invalide.");
						err_count++;
					}
				}
				
				if(textfield_email.getText().isEmpty()) {
					erreurs.add("Le champ email est obligatoire!");
					err_count++;
				}
				else {
					p = Pattern.compile("\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b");
					m = p.matcher(textfield_email.getText());
					if(!m.matches()) {
						erreurs.add("Adresse email invalide!");
						err_count++;
					}
				}
				
				if(photo == null) {
					erreurs.add("La photo est obligatoire!");
					err_count++;
				}
				String msg_erreurs = "";
				
				for(String err : erreurs) {
					msg_erreurs=msg_erreurs.concat(err+"\n");
				}
				
				erreurs_saisi = msg_erreurs;
				//erreurs = new ArrayList<String>();
				if(err_count > 0) return false;
				return true;
			}
		});
		button_enregistrerEmploye.setIcon(new FlatFileViewFloppyDriveIcon());
		button_enregistrerEmploye.setBounds(516, 277, 102, 32);
		panel_ajouterEmploye.add(button_enregistrerEmploye);
		
		
		JButton button_reinitialiserEmploye = new JButton();
		button_reinitialiserEmploye.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clearForm();
			}
		});
		button_reinitialiserEmploye.setIcon(new FlatInternalFrameRestoreIcon());
		button_reinitialiserEmploye.setBounds(516, 243, 102, 32);
		button_reinitialiserEmploye.setIcon(new FlatWindowCloseIcon());
		panel_ajouterEmploye.add(button_reinitialiserEmploye);
		
		calendar_dateNaissance = new JDateChooser();
		calendar_dateNaissance.setBounds(352, 56, 142, 23);
		panel_ajouterEmploye.add(calendar_dateNaissance);
		
		calendar_dateEmbauche = new JDateChooser();
		calendar_dateEmbauche.setBounds(352, 109, 142, 23);
		panel_ajouterEmploye.add(calendar_dateEmbauche);
		
		textfield_email = new JTextField();
		textfield_email.setColumns(10);
		textfield_email.setBounds(48, 286, 199, 23);
		panel_ajouterEmploye.add(textfield_email);
		
		JLabel label_email = new JLabel("Adresse email");
		label_email.setHorizontalAlignment(SwingConstants.CENTER);
		label_email.setBackground(Color.WHITE);
		label_email.setBounds(48, 261, 199, 14);
		panel_ajouterEmploye.add(label_email);
		
		textfield_salaire = new JTextField();
		textfield_salaire.setColumns(10);
		textfield_salaire.setBounds(352, 286, 142, 23);
		panel_ajouterEmploye.add(textfield_salaire);
		
		JLabel label_salaire = new JLabel("Salaire");
		label_salaire.setHorizontalAlignment(SwingConstants.CENTER);
		label_salaire.setBackground(Color.WHITE);
		label_salaire.setBounds(352, 261, 142, 14);
		panel_ajouterEmploye.add(label_salaire);
		
		label_nomImageChoisi = new JLabel("");
		label_nomImageChoisi.setHorizontalAlignment(SwingConstants.CENTER);
		label_nomImageChoisi.setBounds(504, 236, 129, 23);
		panel_ajouterEmploye.add(label_nomImageChoisi);
		
		combobox_sexe = new JComboBox();
		combobox_sexe.setModel(new DefaultComboBoxModel(new String[] {"HOMME", "FEMME"}));
		combobox_sexe.setBounds(257, 286, 85, 23);
		panel_ajouterEmploye.add(combobox_sexe);
		
		JLabel label_sexe = new JLabel("Sexe");
		label_sexe.setHorizontalAlignment(SwingConstants.CENTER);
		label_sexe.setBackground(Color.WHITE);
		label_sexe.setBounds(257, 261, 85, 14);
		panel_ajouterEmploye.add(label_sexe);
		
		
		JPanel tab_conges = new JPanel();
		tabbedPane.addTab("Conges", null, tab_conges, null);
		tab_conges.setLayout(null);
		
		JScrollPane scrollpane_conges = new JScrollPane();
		scrollpane_conges.setBounds(10, 11, 681, 536);
		tab_conges.add(scrollpane_conges);
		
		table_conges = new JTable();

		conges_table_model = new DemandeCongeTableModel(conges);
		table_conges.setModel(conges_table_model);
		scrollpane_conges.setViewportView(table_conges);
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		for(int i=0; i<table_conges.getColumnCount(); i++) {
			table_conges.getColumnModel().getColumn(i).setCellRenderer( centerRenderer );	
		}
		
		JPanel panel_conges_actions = new JPanel();
		panel_conges_actions.setBorder(new TitledBorder(null, "Actions", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_conges_actions.setBounds(10, 558, 681, 62);
		tab_conges.add(panel_conges_actions);
		
		JButton button_accepterConge = new JButton("Accepter");
		button_accepterConge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table_conges.getSelectedRowCount() == 0 ) {
					JOptionPane.showMessageDialog(null, "Aucune ligne selectionnée!", "Information", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					DemandeConge d = conges.get(table_conges.getSelectedRow());
					int duree = (int) java.time.temporal.ChronoUnit.DAYS.between(LocalDate.parse(d.getDate_debut().toString()), LocalDate.parse(d.getDate_fin().toString()));
					if(DataEspaceAdmin.updateConge(d.getId(), d.getSalarie_id(), duree, true)) {
						JOptionPane.showMessageDialog(null, "Demande acceptée!", "Information", JOptionPane.INFORMATION_MESSAGE);
						updateTableConges();
						updateTableEmployes();
					}
					else {
						JOptionPane.showMessageDialog(null, "Erreur de la mise à jour!", "Erreur", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		button_accepterConge.setBackground(new Color(255, 255, 255));
		panel_conges_actions.add(button_accepterConge);
		
		JButton button_refuserConge = new JButton("Refuser");
		button_refuserConge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table_conges.getSelectedRowCount() == 0 ) {
					JOptionPane.showMessageDialog(null, "Aucune ligne selectionnée!", "Information", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					DemandeConge d = conges.get(table_conges.getSelectedRow());
					int duree = (int) java.time.temporal.ChronoUnit.DAYS.between(LocalDate.parse(d.getDate_debut().toString()), LocalDate.parse(d.getDate_fin().toString()));
					if(DataEspaceAdmin.updateConge(d.getId(), d.getSalarie_id(), duree, false)) {
						JOptionPane.showMessageDialog(null, "Demande refusée!", "Information", JOptionPane.INFORMATION_MESSAGE);
						updateTableConges();
						updateTableEmployes();
						
						
					}
					else {
						JOptionPane.showMessageDialog(null, "Erreur de la mise à jour!", "Erreur", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		panel_conges_actions.add(button_refuserConge);
		
		JPanel tab_compte = new JPanel();
		tabbedPane.addTab("Compte", null, tab_compte, null);
		tab_compte.setLayout(null);
		
		JPanel panel_modifierMotDePasse = new JPanel();
		panel_modifierMotDePasse.setBorder(new TitledBorder(null, "Modifier mot de passe", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_modifierMotDePasse.setBounds(10, 11, 681, 317);
		tab_compte.add(panel_modifierMotDePasse);
		panel_modifierMotDePasse.setLayout(null);
		
		JButton button_changeMotDePasse = new JButton("Mettre \u00E0 jour");
		button_changeMotDePasse.addActionListener(new ActionListener() {
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
					if(!passwordfield_ancienMotDePasse.getText().equals(DataLogin.getCurrentAdmin().getMot_de_passe())) {
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
						boolean res = DataLogin.updateAdminPassword(DataLogin.getCurrentAdmin().getId(), passwordfield_nouveauMotDePasse.getText());
						if(res) {
							passwordfield_ancienMotDePasse.setText("");
							passwordfield_nouveauMotDePasse.setText("");
							passwordfield_confirmerNouveauMotDePasse.setText("");
							JOptionPane.showMessageDialog(null, "Mot de passe mis a jour!", "Information", JOptionPane.INFORMATION_MESSAGE);
						}
						else {
							JOptionPane.showMessageDialog(null, "Erreur de la mise a jour du mot de passe!", "Erreur", JOptionPane.ERROR_MESSAGE);
						}
				}
			}
		});
		button_changeMotDePasse.setBounds(272, 247, 129, 28);
		panel_modifierMotDePasse.add(button_changeMotDePasse);
		
		JLabel label_ancienMotDePasse = new JLabel("Ancien mot de passe");
		label_ancienMotDePasse.setBounds(250, 33, 176, 14);
		panel_modifierMotDePasse.add(label_ancienMotDePasse);
		
		passwordfield_ancienMotDePasse = new JPasswordField();
		passwordfield_ancienMotDePasse.setBounds(250, 58, 176, 28);
		panel_modifierMotDePasse.add(passwordfield_ancienMotDePasse);
		
		passwordfield_nouveauMotDePasse = new JPasswordField();
		passwordfield_nouveauMotDePasse.setBounds(250, 124, 176, 28);
		panel_modifierMotDePasse.add(passwordfield_nouveauMotDePasse);
		
		JLabel label_nouveauMotDePasse = new JLabel("Nouveau mot de passe");
		label_nouveauMotDePasse.setBounds(250, 99, 176, 14);
		panel_modifierMotDePasse.add(label_nouveauMotDePasse);
		
		JLabel label_confirmerNouveauMotDePasse = new JLabel("Confimer novueau mot de passe");
		label_confirmerNouveauMotDePasse.setBounds(250, 163, 176, 14);
		panel_modifierMotDePasse.add(label_confirmerNouveauMotDePasse);
		
		passwordfield_confirmerNouveauMotDePasse = new JPasswordField();
		passwordfield_confirmerNouveauMotDePasse.setBounds(250, 188, 176, 28);
		panel_modifierMotDePasse.add(passwordfield_confirmerNouveauMotDePasse);
		
		JPanel panel_nouveauAdmin = new JPanel();
		panel_nouveauAdmin.setBorder(new TitledBorder(null, "Ajouter un nouveau administrateur", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_nouveauAdmin.setBounds(10, 339, 374, 281);
		tab_compte.add(panel_nouveauAdmin);
		panel_nouveauAdmin.setLayout(null);
		
		JLabel label_nomUtilisateur = new JLabel("Nom d'utilisateur");
		label_nomUtilisateur.setBounds(27, 52, 176, 14);
		panel_nouveauAdmin.add(label_nomUtilisateur);
		
		JLabel label_motDePasse = new JLabel("Mot de passe");
		label_motDePasse.setBounds(27, 118, 176, 14);
		panel_nouveauAdmin.add(label_motDePasse);
		
		passwordfield_nouveauAdminMotDePasse = new JPasswordField();
		passwordfield_nouveauAdminMotDePasse.setBounds(27, 143, 176, 28);
		panel_nouveauAdmin.add(passwordfield_nouveauAdminMotDePasse);
		
		JLabel label_confirmerMotDePasse = new JLabel("Confirmer mot de passe");
		label_confirmerMotDePasse.setBounds(27, 191, 176, 14);
		panel_nouveauAdmin.add(label_confirmerMotDePasse);
		
		passwordfield_confirmerNouveauAdminMotDePasse = new JPasswordField();
		passwordfield_confirmerNouveauAdminMotDePasse.setBounds(27, 216, 176, 28);
		panel_nouveauAdmin.add(passwordfield_confirmerNouveauAdminMotDePasse);
		
		JButton button_ajouterAdmin = new JButton("Ajouter");
		button_ajouterAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ArrayList<String> erreurs = new ArrayList<String>();
				int err_count = 0;
				String msgs = "";
				if(textfield_nouveauAdminNomUtilisateur.getText().isEmpty()) {
					erreurs.add("Le champ nom utilisateur est obligatoire!");
					err_count++;
				}
				else if(passwordfield_nouveauAdminMotDePasse.getText().isEmpty()) {
					erreurs.add("Veuillez saisir le mot de passe pour le nouveau administrateur!");
					err_count++;
				}
				else if(passwordfield_confirmerNouveauAdminMotDePasse.getText().isEmpty()) {
					erreurs.add("Veuillez confirmer le mot de passe!");
					err_count++;
				}
				else {
					if(DataLogin.usernameExists("admin", textfield_nouveauAdminNomUtilisateur.getText())) {
						erreurs.add("Le nom d'utilisateur existe déjà.\n Veuillez choisir un autre nom d'utilisateur!");
						err_count++;
					}
					if(!passwordfield_nouveauAdminMotDePasse.getText().equals(passwordfield_confirmerNouveauAdminMotDePasse.getText())) {
						erreurs.add("Les mots de passe ne correspondent pas!");
						err_count++;
					}
				}
				if(err_count > 0) {
					for(String err : erreurs) {
						msgs = msgs.concat(err+"\n");
					}
					JOptionPane.showMessageDialog(null, msgs);
				}
				
				// Ajotuer le nouveau admin
				else {
					
					boolean res = DataEspaceAdmin.addAdmin(new Admin(0, textfield_nouveauAdminNomUtilisateur.getText(), passwordfield_nouveauAdminMotDePasse.getText()));
					if(res == true) {
						JOptionPane.showMessageDialog(null, "Nouveau administrateur ajouté!", "Information", JOptionPane.INFORMATION_MESSAGE);
						updateTableAdmins();
					}
					else {
						JOptionPane.showMessageDialog(null, "Erreur de l'ajout du nouvelle administrateur!", "Erreur", JOptionPane.ERROR_MESSAGE);
					}
					
				}
			}
		});
		button_ajouterAdmin.setBounds(250, 102, 100, 36);
		panel_nouveauAdmin.add(button_ajouterAdmin);
		
		JButton button_reinitialiserAdmin = new JButton("R\u00E9initialiser");
		button_reinitialiserAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textfield_nouveauAdminNomUtilisateur.setText("");
				passwordfield_nouveauAdminMotDePasse.setText("");
				passwordfield_confirmerNouveauAdminMotDePasse.setText("");
			}
		});
		button_reinitialiserAdmin.setBounds(250, 169, 100, 36);
		panel_nouveauAdmin.add(button_reinitialiserAdmin);
		
		textfield_nouveauAdminNomUtilisateur = new JTextField();
		textfield_nouveauAdminNomUtilisateur.setBounds(27, 77, 176, 28);
		panel_nouveauAdmin.add(textfield_nouveauAdminNomUtilisateur);
		textfield_nouveauAdminNomUtilisateur.setColumns(10);
		
		JPanel panel_listeAdmins = new JPanel();
		panel_listeAdmins.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Administrateurs", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_listeAdmins.setBounds(388, 339, 303, 281);
		tab_compte.add(panel_listeAdmins);
		GridBagLayout gbl_panel_listeAdmins = new GridBagLayout();
		gbl_panel_listeAdmins.columnWidths = new int[]{0, 0};
		gbl_panel_listeAdmins.rowHeights = new int[]{0, 0};
		gbl_panel_listeAdmins.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_listeAdmins.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel_listeAdmins.setLayout(gbl_panel_listeAdmins);
		
		JScrollPane scrollpane_listeAdmins = new JScrollPane();
		GridBagConstraints gbc_scrollpane_listeAdmins = new GridBagConstraints();
		gbc_scrollpane_listeAdmins.fill = GridBagConstraints.BOTH;
		gbc_scrollpane_listeAdmins.gridx = 0;
		gbc_scrollpane_listeAdmins.gridy = 0;
		panel_listeAdmins.add(scrollpane_listeAdmins, gbc_scrollpane_listeAdmins);
		
		table_administrateurs = new JTable();
		table_administrateurs.setFont(new Font("Tahoma", Font.PLAIN, 11));
		table_administrateurs.setFillsViewportHeight(true);
		table_administrateurs.setModel(admins_table_model);
		scrollpane_listeAdmins.setViewportView(table_administrateurs);
		frmGerhEspace.setLocationRelativeTo(null);
		updateTableAdmins();

	}

	public void updateTableEmployes() {
		employes = DataEspaceAdmin.getEmployes();
		table_employes.setModel(new EmployeTableModel(employes));
		for(int i=0; i<table_employes.getColumnCount(); i++) {
			table_employes.getColumnModel().getColumn(i).setCellRenderer( centerRenderer );	
		}
		// TODO Auto-generated method stub
		
	}
	public void updateTableConges() {
		conges = DataEspaceAdmin.getConges();
		table_conges.setModel(new DemandeCongeTableModel(conges));
		for(int i=0; i<table_conges.getColumnCount(); i++) {
			table_conges.getColumnModel().getColumn(i).setCellRenderer( centerRenderer );	
		}
	}
	public void updateTableAdmins() {
		admins = DataEspaceAdmin.getAdmins();
		table_administrateurs.setModel(new AdminTableModel(admins));
		for(int i=0; i<table_administrateurs.getColumnCount(); i++) {
			table_administrateurs.getColumnModel().getColumn(i).setCellRenderer( centerRenderer );	
		}
	}
}
