import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.PrintWriter;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;

/**
 * Simple userinterface using the jSerialComm library:
 * https://fazecast.github.io/jSerialComm
 * 
 * Javadoc: http://fazecast.github.io/jSerialComm/javadoc
 * 
 * @author Fjodor van Slooten
 *
 */
public class SerialGUI extends JFrame {

	private JPanel contentPane;
	private JLabel label;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SerialGUI frame = new SerialGUI();
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
	public SerialGUI() {
		setTitle("RFID reader");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		label = new JLabel("Please scan your badge");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(label, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(240, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(label, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(220, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
		
		// start serial commmunication:
		initializeSerialPort();
	}
	
	// used for serial communication:
	String readline;
	SerialPort comPort;
	String commPort = "COM6";
	int baudrate = 9600;
	
	
	/**
	 * Setup commPort and add an eventlistener to respond to data send from eg. an Arduino.
	 * Make sure you call this method from the constructor.
	 * Set the serial port and speed by changing the value of variables commPort and baudrate.
	 * Based on:
	 * jSerialComm Event-Based Reading Usage Example
	 * https://github.com/Fazecast/jSerialComm/wiki/Event-Based-Reading-Usage-Example
	 */
	void initializeSerialPort() {
		System.out.println("Connecting to "+commPort+" with speed "+baudrate+" (check these from Arduino IDE!)");
		comPort = SerialPort.getCommPort(commPort);
		comPort.openPort();
		comPort.setBaudRate(baudrate);
		comPort.addDataListener(new SerialPortDataListener() {
			@Override
			public int getListeningEvents() {
				return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
			}

			@Override
			public void serialEvent(SerialPortEvent event) {
				if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {
					System.err.println("No data on SerialPort");
					return;
				}
				int bytesAvailable = comPort.bytesAvailable();
				if (bytesAvailable<1) {
					System.err.println("Can not read from SerialPort");
					return;
				}
				byte[] newData = new byte[bytesAvailable];
				int numRead = comPort.readBytes(newData, newData.length);
				// System.out.println("Read " + numRead + " bytes.");
				if (numRead > 0) {
					for (int i = 0; i < newData.length; ++i) {
						if ((char)newData[i]=='\n'||(char)newData[i]=='\r') {
							readline=readline.trim();
							if (readline.length()>0) receive(readline);
							readline="";
						}
						else
							readline=readline+(char)newData[i];
					}
				}
			}
		});
	}
	
	/**
	 * Called by eventhandler when a line of text is received via the serial connection.
	 * @param line
	 */
	public void receive(String line) {
		if (line==null) return;
		System.out.println(line);
		if (line.startsWith("Card UID")) { // check for a value (string starting with 'Card UID')
			cuid(line.substring(10).trim()); // show it in the label in the userinterface
		}
	}
	
	/**
	 * Send text to the serial connection.
	 * Example used from serialWrite(String s) method of https://github.com/HirdayGupta/Java-Arduino-Communication-Library/blob/master/src/arduino/Arduino.java
	 * @param line
	 */
	public void send(String s) {
		comPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
		comPort.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 100, 0);
		try{Thread.sleep(5);} catch(Exception e){}
		PrintWriter pout = new PrintWriter(comPort.getOutputStream());
		pout.print(s);
		pout.flush();
	}
	
	/**
	 * This method will be called with the id if the SerialComm finds the keyword "Card UID"
	 * @param id
	 */
	public void cuid(String id) {
		System.out.println("cuid="+id+".");
		String welcome = "Welkom, ";
		if (id.equals("B4 DF EC E2")) label.setText(welcome+"Blue Dot");
		else if (id.equals("30 60 45 47")) label.setText(welcome+"Fjodor");
	}
}
