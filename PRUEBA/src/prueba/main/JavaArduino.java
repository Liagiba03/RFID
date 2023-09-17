package prueba.main;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;

public class JavaArduino{
	public void Puertos() throws SerialPortException {
		String puertos []= SerialPortList.getPortNames();
		for(String n: puertos) {
			System.out.println(n);
		}
		SerialPort sp = new SerialPort("/dev/ttyACM0");
		
		try {
			sp.openPort();
			sp.addEventListener(new LecturaSerial(sp),SerialPort.MASK_RXCHAR);
			/*sp.setParams(SerialPort.BAUDRATE_9600,
					SerialPort.DATABITS_8, 
					SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);
			Thread.sleep(1500);
			while(true) {
				System.out.println("Enviando...");
				sp.writeString("Arqui");
				Thread.sleep(5000);
			}*/
		}catch(Exception ex) {
			ex.printStackTrace();
			
		}
	}
	
}
class LecturaSerial implements SerialPortEventListener{

	SerialPort sp;
	public LecturaSerial (SerialPort sp) {
		this.sp =sp;
	}
	@Override
	public void serialEvent(SerialPortEvent spe) {
		try {
			String msg="";
			msg=sp.readString(8);
			System.out.print("Recibiendo...");
			System.out.println(msg);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
}
