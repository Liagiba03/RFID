package com.tesji.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.Color;

public class Inicio extends JFrame {

	private JPanel contentPane;
	private JButton btnSalir;
	private JButton btnRevisar;
	private JButton btnRegistrar;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Inicio frame = new Inicio();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Inicio() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 735, 504);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		btnSalir = new JButton("");
		btnSalir.setBackground(new Color(153, 193, 241));
		btnSalir.setIcon(new ImageIcon(Inicio.class.getResource("/Imag/Cerrar.png")));
		btnSalir.setOpaque(true);
		btnSalir.setContentAreaFilled(false);
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnSalir.setBounds(640, 12, 63, 46);
		panel.add(btnSalir);
		
		btnRevisar = new JButton("Revisión");
		btnRevisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Revisar re = new Revisar();
				re.main(null);
				dispose();
			}
		});
		btnRevisar.setBounds(194, 172, 117, 25);
		panel.add(btnRevisar);
		
		btnRegistrar = new JButton("Registrar");
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Registrar rea = new Registrar();
				rea.main(null);
				dispose();
			}
		});
		btnRegistrar.setBounds(415, 172, 117, 25);
		panel.add(btnRegistrar);
	}
}
