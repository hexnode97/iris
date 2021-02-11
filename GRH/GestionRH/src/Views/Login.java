package Views;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatLightLaf;

import Data.DataLogin;
import Models.LoginResult;

import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JPasswordField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField textfield_nomUtilisateur;
	private Views.EspaceAdministrateur espace_admin;
	private static Login window;
	private JPasswordField passwordfield_motDePasse;
	private ArrayList<String> erreurs;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new FlatLightLaf());
		}catch(Exception e) {
			
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new Login();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	private boolean verifierSaisi() {
		int err_count = 0;
		if(textfield_nomUtilisateur.getText().isEmpty()) {
			erreurs.add("Champ nom d'utilisateur requis!");
			err_count++;
		}
		if(passwordfield_motDePasse.getText().isEmpty()) {
			erreurs.add("Champ mot de pase requis!");
			err_count++;
		}
		
		if(err_count > 0) return false;
		return true;
		
	}


	/**
	 * Create the frame.
	 */
	public Login() {
		erreurs = new ArrayList<String>();
		setTitle("GERH Software");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 372);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textfield_nomUtilisateur = new JTextField();
		textfield_nomUtilisateur.setBounds(133, 115, 180, 27);
		contentPane.add(textfield_nomUtilisateur);
		textfield_nomUtilisateur.setColumns(10);
		
		JCheckBox checkbox_afficherMotDePasse = new JCheckBox("Afficher mot de passe");
		checkbox_afficherMotDePasse.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(checkbox_afficherMotDePasse.isSelected()) {
					passwordfield_motDePasse.setEchoChar((char)0);
				}
				else {
					passwordfield_motDePasse.setEchoChar('*');					
				}
			}
		});
		checkbox_afficherMotDePasse.setBounds(133, 220, 180, 23);
		contentPane.add(checkbox_afficherMotDePasse);
		
		JButton button_seConnecter = new JButton("Se connecter");
		button_seConnecter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				// verifier les infos
				LoginResult loginResult = null;
				if(verifierSaisi()) {
					loginResult = DataLogin.verifyLogin(textfield_nomUtilisateur.getText(), passwordfield_motDePasse.getText());
					if(loginResult.getStatus() == false) {
						JOptionPane.showMessageDialog(null, "Informations erronées.", "Authentification", JOptionPane.ERROR_MESSAGE);
					}
					else {
						if(loginResult.getUserType().equals("admin")) {
							DataLogin.updateLastAuthenticated();
							Views.EspaceAdministrateur.getInstance();
						}
						else {
							Views.EspaceEmploye.getInstance();
						}
						window.dispose();
					}
				}
				else {
					String msgs_erreur = "";
					for(String err : erreurs) {
						msgs_erreur = msgs_erreur.concat(err+"\n");
					}
					erreurs = new ArrayList<String>();
					JOptionPane.showMessageDialog(null, msgs_erreur, "Erreur", JOptionPane.ERROR_MESSAGE);
				}
				
				
			}
		});	
		button_seConnecter.setBounds(162, 266, 119, 37);
		contentPane.add(button_seConnecter);
		
		JLabel label_nomUtilisateur = new JLabel("Nom d'utilisateur");
		label_nomUtilisateur.setBounds(133, 90, 180, 14);
		contentPane.add(label_nomUtilisateur);
		
		JLabel label_motDePasse = new JLabel("Mot de passe");
		label_motDePasse.setBounds(133, 158, 180, 14);
		contentPane.add(label_motDePasse);
		
		JLabel lblBienvenueSurGerh = new JLabel("Bienvenue sur GERH");
		lblBienvenueSurGerh.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblBienvenueSurGerh.setHorizontalAlignment(SwingConstants.CENTER);
		lblBienvenueSurGerh.setBounds(133, 32, 180, 37);
		contentPane.add(lblBienvenueSurGerh);
		
		passwordfield_motDePasse = new JPasswordField();
		passwordfield_motDePasse.setEchoChar('*');
		passwordfield_motDePasse.setBounds(133, 183, 180, 27);
		contentPane.add(passwordfield_motDePasse);
		this.setLocationRelativeTo(null);
		
	}
}
