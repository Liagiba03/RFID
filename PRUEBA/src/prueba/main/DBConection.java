package prueba.main;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class DBConection {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Scanner leer = new Scanner(System.in);
			int n=0;
			Connection conection=
					DriverManager.getConnection("jdbc:mysql://localhost:3306/alumno","root","SusSand#12");
			System.out.println("Conexion exitosa");
			System.out.println("ELIGE QUE HACER");
			n = leer.nextInt();
			
			switch(n) {
				case 1:
			PreparedStatement prepared = 
					conection.prepareStatement("insert into alumno values(?,?)");
			//Para AGREGAR DATOS
			prepared.setNString(1, "Jenni");
			prepared.setNString(2, "45998f4");
			prepared.executeUpdate();
			System.out.println("Datos insertados");
				break;
				case 2:
			//PARA ACTUALIZAR DATOS
			PreparedStatement prepared2 = 
					conection.prepareStatement("UPDATE alumno SET uid=?, nombre=? WHERE uid=?");
			prepared2.setString(1, "45998f4");
			prepared2.setString(2, "Jenni");
			prepared2.setString(3, "Jenni");
			prepared2.executeUpdate();
			System.out.println("Update exitoso");
			break;
				case 3:
					//PARA CONSULTAR DATOS
					PreparedStatement prepared3 = 
							conection.prepareStatement("SELECT * FROM alumno");
					ResultSet result = prepared3.executeQuery();
					while(result.next()) {
						String uid = result.getString("uid");
						String nom = result.getString("nombre");
						System.out.println("uid:"+uid+" nombre: "+nom);
					}
					
					break;
				case 4:
					//PARA CONSULTAR DATOS
					PreparedStatement prepared4 = 
							conection.prepareStatement("DELETE FROM alumno WHERE nombre=?");
					prepared4.setString(1, "Jenni");
					prepared4.executeUpdate();
					System.out.println("Eliminación exitosa");
					break;
				case 5:
					//LEER PUERTO SERIAL   
					 String portName = "/dev/ttyACM0";
					JavaArduino ar = new JavaArduino();
					ar.Puertos();
					//ar.ArduinoSerialReader(portName);
					System.out.println("Puerto");
					break;
				case 6:
					File imagenFile = new File("/home/suseth/Descargas/Fondo.jpeg");
					byte[] imagenBytes = new byte[(int) imagenFile.length()];

					try (FileInputStream fis = new FileInputStream(imagenFile)) {
					    fis.read(imagenBytes);
					} catch (IOException e) {
					    e.printStackTrace();
					}
					
					PreparedStatement prepared5 = 
					conection.prepareStatement("update alumno set imagen = ? where uid='49a89c23'");
					prepared5.setBytes(1, imagenBytes);
				    prepared5.executeUpdate();
					System.out.println("Puerto");
					break;
				case 7:
					PreparedStatement prepared6 = 
					conection.prepareStatement("SELECT imagen FROM alumno where uid=?");
					prepared6.setString(1, "49a89c23");
					
					try (ResultSet rs = prepared6.executeQuery()) {
		                if (rs.next()) {
		                    byte[] imagenBytes1 = rs.getBytes("imagen");

		                    // Convertir los datos binarios en un ImageIcon
		                    ImageIcon imageIcon = new ImageIcon(imagenBytes1);
		                    Image imagen = imageIcon.getImage();
		                    Image imagenEscalada = imagen.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		                    ImageIcon imagenFinal = new ImageIcon(imagenEscalada);

		                    // Crear un JLabel y asignar la imagen
		                    JLabel labelImagen = new JLabel(imagenFinal);

		                    // Crear un JFrame para mostrar el JLabel
		                    JFrame frame = new JFrame();
		                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		                    frame.getContentPane().add(labelImagen);
		                    frame.pack();
		                    frame.setVisible(true);
		                }
		            }
		         catch (SQLException e) {
		            e.printStackTrace();
		        }
					
					break;
			}
			conection.close();
		}catch(Exception e) {
			System.out.println("Error en conexión: "+e);
		}
	}
}
