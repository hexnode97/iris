package Views;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.SystemColor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import Data.DataEspaceAdmin;
import Data.DataFicheEmploye;
import Models.Employe;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Component;

import javax.swing.border.MatteBorder;
import javax.swing.UIManager;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FicheEmploye extends JFrame {
	private String[] extensions_valides = {"png", "jpeg", "jpg", "bmp"};	
	private ArrayList<String> erreurs;
	private String erreurs_saisi = "";
	private File photo;
	private JFileChooser filechooser_photo;
	private JPanel panel_contratEtPaie;
	private static int employeId = 0;
	private JTextField textfield_nom;
	private JTextField textfield_prenom;
	private JTextField textfield_adresse;
	private JTextField textfield_tel;
	private JTextField textfield_rib;
	private JTextField textfield_dateNaissance;
	private JTextField textfield_dateEmbauche;
	private JTextField textfield_cin;
	private static boolean editOnly;
	private static FicheEmploye window;
	private JPanel panel_infosPersonnelles;
	private JLabel label_photo;
	private JLabel label_nom;
	private JLabel label_prenom;
	private JLabel label_adresse;
	private JLabel label_tel;
	private JLabel label_nombreEnfants;
	private JLabel label_dateNaissance;
	private JLabel label_cin;
	private JButton button_telechargerPhoto;
	private JLabel label_contrat;
	private JLabel label_paiement;
	private JComboBox combobox_contrat;
	private JComboBox combobox_paiement;
	private JLabel lblDateDembauche;
	private JLabel label_salaire;
	private JLabel label_rib;
	private JButton button_enregistrer;
	private Employe employe;
	private JTextField textfield_salaire;
	private JTextField textfield_nombreEnfants;
	
	public static FicheEmploye getInstance(boolean editable, int idEmploye) {
		editOnly = editable;
		if(window == null) {
			window = new FicheEmploye(DataFicheEmploye.getEmployeById(idEmploye));
			employeId = idEmploye;
		}
		return window;
	}

	/**
	 * Create the frame.
	 */
	
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

		if(textfield_adresse.getText().isEmpty()) {
			erreurs.add("Le champ adresse est obligatoire!");
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
				erreurs.add("La valeur nombre d'enfants est invalide!");
				err_count++;
			}
		}
		if(textfield_tel.getText().isEmpty()) {
			erreurs.add("Le champ tel est obligatoire!");
			err_count++;
		}else {
			p = Pattern.compile("0[6,7][0-9]{8}");
			m = p.matcher(textfield_tel.getText());
			if(!m.matches()) {
				erreurs.add("La valeur Tel est invalide!");
				err_count++;
			}
		}
		if(textfield_rib.getText().isEmpty()) {
			erreurs.add("Le champ RIB est obligaoire!");
			err_count++;
		}
		else {
			p = Pattern.compile("[0-9]{24}");
			m = p.matcher(textfield_rib.getText());
			if(!m.matches()) {
				erreurs.add("La valeur RIB est invalide!");
				err_count++;
			}
		}
		
		if(err_count > 0) {
			for(String err : erreurs) {
				erreurs_saisi = erreurs_saisi.concat(err+"\n");
			}
			erreurs = new ArrayList<String>();
			return false;
		}
		return true;
	}
	
	private FicheEmploye(Employe employe) {
		filechooser_photo = new JFileChooser();
		erreurs = new ArrayList<String>();
		setTitle("Fiche employ\u00E9");
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				window.dispose();
				window = null;
			}
		});
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setType(Type.POPUP);
		setBackground(SystemColor.inactiveCaption);
		getContentPane().setLayout(null);
		panel_infosPersonnelles = new JPanel();
		panel_infosPersonnelles.setBorder(new TitledBorder(null, "Informations personnelles", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_infosPersonnelles.setBounds(6, 11, 423, 265);
		getContentPane().add(panel_infosPersonnelles);
		panel_infosPersonnelles.setLayout(null);
		
		label_photo = new JLabel("");
		label_photo.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Photo", TitledBorder.CENTER, TitledBorder.BOTTOM, null, new Color(0, 0, 0)));
		label_photo.setBounds(23, 31, 114, 153);
		panel_infosPersonnelles.add(label_photo);
		
		label_nom = new JLabel("Nom");
		label_nom.setBounds(160, 21, 66, 25);
		panel_infosPersonnelles.add(label_nom);
		
		label_prenom = new JLabel("Prenom");
		label_prenom.setBounds(160, 57, 66, 25);
		panel_infosPersonnelles.add(label_prenom);
		
		label_adresse = new JLabel("Adresse");
		label_adresse.setBounds(160, 89, 66, 25);
		panel_infosPersonnelles.add(label_adresse);
		
		label_tel = new JLabel("Tel");
		label_tel.setBounds(160, 124, 66, 25);
		panel_infosPersonnelles.add(label_tel);
		
		textfield_nom = new JTextField();
		textfield_nom.setBounds(251, 20, 149, 23);
		panel_infosPersonnelles.add(textfield_nom);
		textfield_nom.setColumns(10);
		
		textfield_prenom = new JTextField();
		textfield_prenom.setColumns(10);
		textfield_prenom.setBounds(251, 53, 149, 23);
		panel_infosPersonnelles.add(textfield_prenom);
		
		textfield_adresse = new JTextField();
		textfield_adresse.setColumns(10);
		textfield_adresse.setBounds(221, 87, 179, 23);
		panel_infosPersonnelles.add(textfield_adresse);
		
		textfield_tel = new JTextField();
		textfield_tel.setColumns(10);
		textfield_tel.setBounds(251, 122, 149, 23);
		panel_infosPersonnelles.add(textfield_tel);
		
		label_nombreEnfants = new JLabel("Nbr d'enfants");
		label_nombreEnfants.setBounds(160, 157, 87, 25);
		panel_infosPersonnelles.add(label_nombreEnfants);
		
		label_dateNaissance = new JLabel("Date de naissance");
		label_dateNaissance.setBounds(160, 196, 124, 14);
		panel_infosPersonnelles.add(label_dateNaissance);
		
		textfield_dateNaissance = new JTextField();
		textfield_dateNaissance.setBounds(276, 189, 124, 23);
		panel_infosPersonnelles.add(textfield_dateNaissance);
		textfield_dateNaissance.setColumns(10);
		
		label_cin = new JLabel("CIN");
		label_cin.setBounds(160, 229, 88, 14);
		panel_infosPersonnelles.add(label_cin);
		
		textfield_cin = new JTextField();
		textfield_cin.setColumns(10);
		textfield_cin.setBounds(276, 222, 124, 23);
		panel_infosPersonnelles.add(textfield_cin);
		
		button_telechargerPhoto = new JButton("Telecharger");
		button_telechargerPhoto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		button_telechargerPhoto.setBounds(23, 195, 114, 23);
		panel_infosPersonnelles.add(button_telechargerPhoto);
		
		panel_contratEtPaie = new JPanel();
		panel_contratEtPaie.setBorder(new TitledBorder(null, "Contrat et Paie", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_contratEtPaie.setBounds(6, 287, 423, 152);
		getContentPane().add(panel_contratEtPaie);
		panel_contratEtPaie.setLayout(null);
		
		label_contrat = new JLabel("Contrat");
		label_contrat.setBounds(20, 33, 46, 14);
		panel_contratEtPaie.add(label_contrat);
		
		label_paiement = new JLabel("Paiement");
		label_paiement.setBounds(215, 30, 46, 14);
		panel_contratEtPaie.add(label_paiement);
		
		combobox_contrat = new JComboBox();
		combobox_contrat.setModel(new DefaultComboBoxModel(new String[] {"CDI", "CDD", "ANAPEC"}));
		combobox_contrat.setBounds(78, 27, 117, 23);
		panel_contratEtPaie.add(combobox_contrat);
		
		combobox_paiement = new JComboBox();
		combobox_paiement.setModel(new DefaultComboBoxModel(new String[] {"CHEQUE", "VIREMENT"}));
		combobox_paiement.setBounds(280, 27, 117, 23);
		panel_contratEtPaie.add(combobox_paiement);
		
		label_rib = new JLabel("RIB");
		label_rib.setBounds(20, 71, 46, 14);
		panel_contratEtPaie.add(label_rib);
		
		textfield_rib = new JTextField();
		textfield_rib.setBounds(78, 67, 319, 23);
		panel_contratEtPaie.add(textfield_rib);
		textfield_rib.setColumns(10);
		
		lblDateDembauche = new JLabel("Date d'embauche");
		lblDateDembauche.setBounds(20, 109, 93, 14);
		panel_contratEtPaie.add(lblDateDembauche);
		
		textfield_dateEmbauche = new JTextField();
		textfield_dateEmbauche.setHorizontalAlignment(SwingConstants.CENTER);
		textfield_dateEmbauche.setBounds(121, 105, 82, 23);
		panel_contratEtPaie.add(textfield_dateEmbauche);
		textfield_dateEmbauche.setColumns(10);
		this.getContentPane().add(panel_contratEtPaie);
		
		label_salaire = new JLabel("Salaire");
		label_salaire.setBounds(252, 109, 38, 14);
		panel_contratEtPaie.add(label_salaire);
		
		button_enregistrer = new JButton("Enregistrer");
		button_enregistrer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!verifierSaisi()) {
					JOptionPane.showMessageDialog(null, erreurs_saisi, "Erreur", JOptionPane.ERROR_MESSAGE);
					erreurs_saisi = "";
				}
				else {
					Employe employe = new Employe();
					employe.setId(employeId);
					employe.setAdresse(textfield_adresse.getText());
					employe.setRib(textfield_rib.getText());
					employe.setSalaire(Double.parseDouble(textfield_salaire.getText()));
					employe.setNombre_enfants(Integer.parseInt(textfield_nombreEnfants.getText()));
					employe.setTel(textfield_tel.getText());
					employe.setType_contrat(combobox_contrat.getSelectedItem().toString());
					employe.setType_paiement(combobox_paiement.getSelectedItem().toString());
					if(DataEspaceAdmin.updateEmploye(employe, photo)) {
						JOptionPane.showMessageDialog(null, "Informations mis a jour!", "Information", JOptionPane.INFORMATION_MESSAGE);
						fillData(DataFicheEmploye.getEmployeById(employe.getId()));
						EspaceAdministrateur.getInstance().updateTableEmployes();
					}
					else {
						JOptionPane.showMessageDialog(null, "Erreur de mise a jour!", "Erreur", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		button_enregistrer.setBounds(168, 444, 89, 45);
		getContentPane().add(button_enregistrer);
		this.setBounds(0, 0, 452, 535);
		this.setLocationRelativeTo(null);
		
		
		// On peut pas modifier ces attributs, alors ils doivent etre mis toujours a false
		textfield_cin.setEditable(false);
		textfield_dateNaissance.setEditable(false);
		textfield_nom.setEditable(false);
		textfield_prenom.setEditable(false);
		
		textfield_nombreEnfants = new JTextField();
		textfield_nombreEnfants.setHorizontalAlignment(SwingConstants.CENTER);
		textfield_nombreEnfants.setBounds(313, 156, 87, 23);
		panel_infosPersonnelles.add(textfield_nombreEnfants);
		textfield_nombreEnfants.setColumns(10);
		textfield_dateEmbauche.setEditable(false);
		textfield_salaire = new JTextField();
		textfield_salaire.setHorizontalAlignment(SwingConstants.CENTER);
		textfield_salaire.setBounds(304, 105, 93, 23);
		panel_contratEtPaie.add(textfield_salaire);
		textfield_salaire.setColumns(10);
		//////////////////////////////////////////////////////////////////////////
		
		fillData(employe);
		setEditableStatus(editOnly);
		
		
		this.setVisible(true);
	}
	

	private void fillData(Employe employe) {
		
		textfield_nom.setText(employe.getNom());
		textfield_prenom.setText(employe.getPrenom());
		textfield_dateNaissance.setText(employe.getDate_naissance().toString());
		textfield_adresse.setText(employe.getAdresse());
		//textfield_cin.setText(employe.getCin());
		textfield_tel.setText(employe.getTel());
		textfield_rib.setText(employe.getRib());
		combobox_contrat.setSelectedItem(employe.getType_contrat().toUpperCase());
		textfield_dateEmbauche.setText(employe.getDate_embauche().toString());
		textfield_salaire.setText(String.valueOf(employe.getSalaire()));
		textfield_cin.setText(employe.getCin());
		textfield_nombreEnfants.setText(String.valueOf(employe.getNombre_enfants()));
		combobox_paiement.setSelectedItem(employe.getType_paiement().toUpperCase());
		combobox_contrat.setSelectedItem(employe.getType_contrat().toUpperCase());
		BufferedImage image = null;
		try {
			image = ImageIO.read(new ByteArrayInputStream(employe.getPhoto()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "ERREUR DE CONVERSION DE L'IMAGE");
		}
		Image img = image.getScaledInstance(label_photo.getWidth(), label_photo.getHeight(), Image.SCALE_SMOOTH);
		label_photo.setIcon(new ImageIcon(img));
		
		/*try {
			image = ImageIO.read(is);
			label_photo.setIcon((Icon)image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Cannot convert image");
		}*/
		//label_photo.setIcon((Icon) new ImageIcon(employe.getPhoto()));
	}
	
	
	private void setEditableStatus(boolean editable) {
		
		// Enlever les boutons si l'admin clique sur fiche complete
		if(editable == false) {
			label_photo.setBounds(23, 61, 114, 153);
			button_enregistrer.setVisible(false);
			button_telechargerPhoto.setVisible(false);
			this.setBounds(0,0,450, 480);
			this.setLocationRelativeTo(null);
		}
		
		// rendre editable ou non a base de la valeur de editable : true => editable, false => non editable
		textfield_adresse.setEditable(editable);
		textfield_tel.setEditable(editable);
		combobox_contrat.setEnabled(editable);
		combobox_paiement.setEnabled(editable);
		textfield_rib.setEditable(editable);
		textfield_salaire.setEditable(editable);
		textfield_nombreEnfants.setEditable(editable);
	}
}
