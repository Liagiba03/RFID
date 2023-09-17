package com.tesji.model;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.tesji.view.Registrar;
import com.tesji.view.Revisar;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;

public class ConectionDB {
	Manager man;
	public static String uidDevuelto;
	private ImageIcon imageFinal;

	public void Puertos(JTextField id, JTextField nom, JTextField mat, JTextField edad, JLabel label) throws SerialPortException {
		String puertos []= SerialPortList.getPortNames();
		for(String n: puertos) {
			System.out.println(n);
		}
		SerialPort sp = new SerialPort("/dev/ttyACM0");
		
		try {
			sp.openPort();
			sp.addEventListener(new LecturaSerial(sp, id, nom, mat, edad, label),SerialPort.MASK_RXCHAR);
			//sp.closePort();
			
		}catch(Exception ex) {
			ex.printStackTrace();
			
		}
	}
	
	//Metodos de conexion a la base de datos
	public void Elejir(String dato, JTextField id, JTextField nom, JTextField mat, JTextField edad, JLabel label) throws SQLException {
		man= new Manager();
		Connection conection=
				DriverManager.getConnection("jdbc:mysql://localhost:3306/alumno","root","SusSand#12");
		//System.out.println("ENTRADA"+dato);
	
			//CONSULTAR TODOS LOS DATOS
			PreparedStatement prepared = 
			conection.prepareStatement("SELECT * FROM "+man.tabla+" WHERE "+man.uid+"=?");
			prepared.setString(1, dato);
			ResultSet result = prepared.executeQuery();
			while(result.next()) {
				uidDevuelto = result.getString("uid");
				String nome = result.getString("nombre");
				String matricula = result.getString("Matricula");
				String eda = result.getString("Edad");
				if(uidDevuelto.length()==0) {
					id.setText(dato);
					nom.setText("NO HAY REGISTROS");
					mat.setText("NO HAY REGISTROS");
					edad.setText("NO HAY REGISTROS");
				}else {
				
				id.setText(uidDevuelto);
				nom.setText(nome);
				mat.setText(matricula);
				edad.setText(eda);
				JOptionPane.showMessageDialog(null, "uid:"+uidDevuelto+" nombre: "+nome);}
			}
			//IMG
			PreparedStatement prepared6 = 
					conection.prepareStatement("SELECT imagen FROM "+man.tabla+" where "+man.uid+"=?");
					prepared6.setString(1, dato);
					
					try (ResultSet rs = prepared6.executeQuery()) {
		                if (rs.next()) {
		                    byte[] imagenBytes1 = rs.getBytes("imagen");

		                    // Convertir los datos binarios en un ImageIcon
		                    ImageIcon imageIcon = new ImageIcon(imagenBytes1);
		                    Image imagen = imageIcon.getImage();
		                    Image imagenEscalada = imagen.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		                    imageFinal = new ImageIcon(imagenEscalada);
		                 // Crear un JLabel y asignar la imagen
		                    label.setIcon(imageFinal);
		                }
		            }
			//IMG FIN
		//conection.close();
		
	}
}

class LecturaSerial implements SerialPortEventListener{
	SerialPort sp;
	String msg;
	JTextField id, nom, mat, edad;JLabel label;
	int n;
	public LecturaSerial(SerialPort sp, JTextField id, JTextField nom, JTextField mat, JTextField edad,
			JLabel label) {
		super();
		this.sp = sp;
		this.id = id;
		this.nom = nom;
		this.mat = mat;
		this.edad = edad;
		this.label = label;
	}
	public LecturaSerial() {
		super();
	}
	public void serialEvent(SerialPortEvent spe) {
		try {
			msg=sp.readString(8);
			ConectionDB db = new ConectionDB();
			System.out.println("\n:  "+msg);
			db.Elejir(msg, id, nom, mat, edad, label);

			//sp.closePort();
		}catch(Exception ex) {
			ex.printStackTrace();
		}	
	}
	
}
