package com.tesji.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import com.tesji.model.ConectionDB;
import com.tesji.model.EliminarA;
import com.tesji.model.Insertar;

import jssc.SerialPortException;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Registrar extends JFrame {

	private JPanel contentPane;
	DefaultTableModel model;
	private JButton btnGuardar;
	private static JTextField txtUID;
	private static JTextField txtNombre;
	private static JTextField txtMatricula;
	private static JLabel txtRuta;
	private static JTextField txtEdad;
	private static String ruta;
	Insertar n = new Insertar();
	private JButton btnRegresar;
	
	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Registrar frame = new Registrar();
					frame.setVisible(true);
					
					//LEER PUERTOS
					Insertar in = new Insertar();
					try {
						try {
							in.Puertos(txtUID, txtNombre, txtMatricula, txtEdad, ruta);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public void limpiar() {
		txtUID.setText(null);
		txtNombre.setText(null);
		txtMatricula.setText(null);
		txtEdad.setText(null);
	}
	
	public Registrar() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 465, 662);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		model = new DefaultTableModel();
		model.addColumn("UID");
		model.addColumn("Nombre");
		model.addColumn("Matricula");
		model.addColumn("Edad");
		model.addColumn("Foto");
		
		btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(114, 343, 117, 25);
		contentPane.add(btnGuardar);
		
		JTextArea txtrUid = new JTextArea();
		txtrUid.setText("UID");
		txtrUid.setEditable(false);
		txtrUid.setBounds(132, 121, 88, 26);
		contentPane.add(txtrUid);
		
		txtUID = new JTextField();
		txtUID.setEditable(false);
		txtUID.setColumns(10);
		txtUID.setBounds(246, 119, 125, 28);
		contentPane.add(txtUID);
		
		JTextArea txtrNombre = new JTextArea();
		txtrNombre.setText("Nombre");
		txtrNombre.setEditable(false);
		txtrNombre.setBounds(132, 171, 88, 26);
		contentPane.add(txtrNombre);
		
		txtNombre = new JTextField();
		txtNombre.setColumns(10);
		txtNombre.setBounds(246, 169, 125, 28);
		contentPane.add(txtNombre);
		
		JTextArea txtrUid_1_1 = new JTextArea();
		txtrUid_1_1.setText("Matricula");
		txtrUid_1_1.setEditable(false);
		txtrUid_1_1.setBounds(132, 231, 88, 26);
		contentPane.add(txtrUid_1_1);
		
		txtMatricula = new JTextField();
		txtMatricula.setColumns(10);
		txtMatricula.setBounds(246, 229, 125, 28);
		contentPane.add(txtMatricula);
		
		JTextArea txtrUid_1_2 = new JTextArea();
		txtrUid_1_2.setText("Edad");
		txtrUid_1_2.setEditable(false);
		txtrUid_1_2.setBounds(132, 294, 88, 26);
		contentPane.add(txtrUid_1_2);
		
		txtEdad = new JTextField();
		txtEdad.setColumns(10);
		txtEdad.setBounds(246, 292, 125, 28);
		contentPane.add(txtEdad);
		
		txtRuta = new JLabel("");
		txtRuta.setBounds(124, 380, 243, 15);
		contentPane.add(txtRuta);
		
		JButton btnFoto = new JButton("Foto...");
		btnFoto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser selectorArchivos = new JFileChooser();
				selectorArchivos.setFileSelectionMode(JFileChooser.FILES_ONLY);
				// indica cual fue la accion de usuario sobre el jfilechooser
				int resultado = selectorArchivos.showOpenDialog(btnFoto);
				if(resultado == JFileChooser.APPROVE_OPTION) {
					ruta = selectorArchivos.getSelectedFile().getPath();
					txtRuta.setText(ruta);
				}else {
					JOptionPane.showMessageDialog(null, this, "ELije un archivo", resultado);
				}
				
				
			}
		});
		btnFoto.setBounds(254, 343, 117, 25);
		contentPane.add(btnFoto);
		
		btnRegresar = new JButton("Regresar");
		btnRegresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Inicio in = new Inicio();
				in.setVisible(true);
				dispose();
			}
		});
		btnRegresar.setBounds(30, 582, 117, 25);
		contentPane.add(btnRegresar);
		
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//GUARDAR EN LA BASE DE DATOS
				if(txtNombre.getText().length()==0||txtEdad.getText().length()==0||txtMatricula.getText().length()==0
						||ruta.length()==0) {
					JOptionPane.showMessageDialog(null,"LLena todos los campos");
				}
				else {
					
					try {
						n.Elejir(txtUID.getText(), txtUID, txtNombre, txtMatricula, txtEdad, ruta);
						//limpiar();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		}});
		
		
	}
}
