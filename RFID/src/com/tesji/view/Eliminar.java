package com.tesji.view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.tesji.model.ConectionDB;
import com.tesji.model.EliminarA;
import com.tesji.model.Insertar;

public class Eliminar extends JFrame {

	private JPanel contentPane;
	private static JTextField txtID;
	private static JTextField txtNombre;
	private static JTextField txtMatricula;
	private static JTextField txtEdad;
	private static JLabel txtImagen;
	private JButton btnRegresar;
	private JButton btnEliminarRegistro;

	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Revisar frame = new Revisar();
					frame.setVisible(true);
					
					EliminarA con =  new EliminarA();
					//MANDAR VALORES A IMPRIMIR
					con.Puertos(txtID, txtNombre, txtMatricula, txtEdad, txtImagen);
							
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public JLabel getTxtImagen() {
		return txtImagen;
	}

	public void setTxtImagen(JLabel txtImagen) {
		this.txtImagen = txtImagen;
	}

	/**
	 * Create the frame.
	 */
	public Eliminar() {
		setBounds(100, 100, 364, 666);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextArea txtrUid = new JTextArea();
		txtrUid.setEditable(false);
		txtrUid.setText("UID");
		txtrUid.setBounds(52, 275, 88, 26);
		contentPane.add(txtrUid);
		
		txtID = new JTextField();
		txtID.setEditable(false);
		txtID.setBounds(166, 273, 125, 28);
		contentPane.add(txtID);
		txtID.setColumns(10);
		
		JTextArea txtrNombre = new JTextArea();
		txtrNombre.setEditable(false);
		txtrNombre.setText("Nombre");
		txtrNombre.setBounds(52, 325, 88, 26);
		contentPane.add(txtrNombre);
		
		txtNombre = new JTextField();
		txtNombre.setEditable(false);
		txtNombre.setColumns(10);
		txtNombre.setBounds(166, 323, 125, 28);
		contentPane.add(txtNombre);
		
		JTextArea txtrUid_1_1 = new JTextArea();
		txtrUid_1_1.setEditable(false);
		txtrUid_1_1.setText("Matricula");
		txtrUid_1_1.setBounds(52, 385, 88, 26);
		contentPane.add(txtrUid_1_1);
		
		txtMatricula = new JTextField();
		txtMatricula.setEditable(false);
		txtMatricula.setColumns(10);
		txtMatricula.setBounds(166, 383, 125, 28);
		contentPane.add(txtMatricula);
		
		JTextArea txtrUid_1_2 = new JTextArea();
		txtrUid_1_2.setEditable(false);
		txtrUid_1_2.setText("Edad");
		txtrUid_1_2.setBounds(52, 448, 88, 26);
		contentPane.add(txtrUid_1_2);
		
		txtEdad = new JTextField();
		txtEdad.setEditable(false);
		txtEdad.setColumns(10);
		txtEdad.setBounds(166, 446, 125, 28);
		contentPane.add(txtEdad);
		
		txtImagen = new JLabel();
		txtImagen.setBackground(new Color(255, 255, 255));
		txtImagen.setBounds(70, 41, 204, 200);
		contentPane.add(txtImagen);
		
		btnRegresar = new JButton("Regresar");
		btnRegresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Inicio in = new Inicio();
				in.setVisible(true);
				dispose();
			}
		});
		btnRegresar.setBounds(23, 599, 117, 25);
		contentPane.add(btnRegresar);
		
		btnEliminarRegistro = new JButton("Eliminar");
		btnEliminarRegistro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		btnEliminarRegistro.setBounds(174, 501, 117, 25);
		contentPane.add(btnEliminarRegistro);
	}

}
