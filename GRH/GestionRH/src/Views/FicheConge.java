package Views;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import java.awt.Color;

public class FicheConge extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JLabel lblEnattente;
	private JTextArea textArea;
	private JLabel lblMotif;
	private JLabel lblNewLabel_1;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public FicheConge() {
		setTitle("Fiche cong\u00E9");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 330, 369);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblId = new JLabel("ID");
		lblId.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblId.setBounds(31, 70, 99, 14);
		contentPane.add(lblId);
		
		JLabel lblNewLabel = new JLabel("Employ\u00E9");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(31, 99, 99, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblDateDeDbut = new JLabel("Date de d\u00E9but");
		lblDateDeDbut.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDateDeDbut.setBounds(31, 128, 99, 14);
		contentPane.add(lblDateDeDbut);
		
		JLabel lblDateDeFin = new JLabel("Date de fin");
		lblDateDeFin.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDateDeFin.setBounds(31, 157, 99, 14);
		contentPane.add(lblDateDeFin);
		
		JLabel lblNombreDeJours = new JLabel("Nombre de jours");
		lblNombreDeJours.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNombreDeJours.setBounds(31, 188, 99, 14);
		contentPane.add(lblNombreDeJours);
		
		textField = new JTextField();
		textField.setBounds(152, 68, 144, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(152, 97, 144, 20);
		contentPane.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(152, 126, 144, 20);
		contentPane.add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(152, 155, 144, 20);
		contentPane.add(textField_3);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(152, 186, 144, 20);
		contentPane.add(textField_4);
		
		lblEnattente = new JLabel("En-attente");
		lblEnattente.setHorizontalAlignment(SwingConstants.CENTER);
		lblEnattente.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblEnattente.setBounds(152, 40, 144, 14);
		contentPane.add(lblEnattente);
		
		textArea = new JTextArea();
		textArea.setBackground(Color.WHITE);
		textArea.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textArea.setBounds(31, 242, 265, 80);
		contentPane.add(textArea);
		//textArea.setBorder(new TitledBorder("Motif"));
		
		lblMotif = new JLabel("Motif");
		lblMotif.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblMotif.setBounds(31, 217, 265, 14);
		contentPane.add(lblMotif);
		
		lblNewLabel_1 = new JLabel("Fiche cong\u00E9");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_1.setBounds(25, 11, 271, 34);
		contentPane.add(lblNewLabel_1);
	}

}
