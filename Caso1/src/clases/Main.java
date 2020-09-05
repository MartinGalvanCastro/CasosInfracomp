package clases;
import java.io.File;
import java.util.Scanner;

public class Main {

	private static String PATH ="docs/config.txt";
	private static String CLIENTES="cliente";
	private static String SERVIDOR="servidor";
	private static String MGS="mensaje";
	private static String NBUFFER="buffer";
	
	public static void main(String[] args) {
		try {
			/*Se va a leer el archivo de config.txt y extraer los parametors*/
			Scanner sc = new Scanner(new File(PATH));
			int clientes=0;int servidores=0;int mensajesPorCliente=0;int tamBuffer=0;
			
			while(sc.hasNextLine()){
				String[] params = sc.nextLine().split(":");
				if(params[0].equals(CLIENTES)){
					clientes = Integer.parseInt(params[1]);
				}
				else if(params[0].equals(SERVIDOR)){
					servidores = Integer.parseInt(params[1]);
				}
				else if(params[0].equals(MGS)){
					mensajesPorCliente = Integer.parseInt(params[1]);
				}
				else if(params[0].equals(NBUFFER)){
					tamBuffer = Integer.parseInt(params[1]);
				}
			}
		sc.close();
		
		Thread[] clientesArray=new Thread[clientes];
		Thread[] servidoresArray=new Thread[servidores];
		Buffer3<Mensaje> buff = new Buffer3<Mensaje>(tamBuffer, clientes);
		
		for (int i = 0; i < clientesArray.length; i++) {
			clientesArray[i] = new Thread(new Cliente(i, buff, mensajesPorCliente));
		}
		
		for (int i = 0; i < servidoresArray.length; i++) {
			servidoresArray[i] = new Thread(new Servidor(i, buff));
		}
		
		for (int i = 0; i < clientesArray.length; i++) {
			clientesArray[i].start();
		}
		
		for (int i = 0; i < servidoresArray.length; i++) {
			servidoresArray[i].start();
		}

		
		
		
		
		
		
		
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
