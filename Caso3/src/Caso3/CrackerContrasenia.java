package Caso3;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;





public class CrackerContrasenia extends Thread{

	/*
	 * Atibuto que representa la id del thread
	 */
	private int id;


	/*
	 * Atributo que representa la longitud minima de caracteres a evaluar en la clave
	 */
	private int comienzo;

	/*
	 *Atributo que representa la longuitud maxima de la cadena de caracteres a evaluar en la clave 
	 */
	private int finall;


	/*
	 * Atributo que representa la longitud minima de caracteres a evaluar en la clave
	 */
	private String comienzoW="";

	/*
	 *Atributo que representa la longuitud maxima de la cadena de caracteres a evaluar en la clave 
	 */
	private String finallW="";

	/*
	 * Atributo que representa el Message Digest para el algoritmo
	 */
	private MessageDigest md;

	/*
	 * Variable para sincronizar todos los threads si alguno encontro la cadena correcta
	 */
	private static boolean LISTO=false;

	/*
	 * Variable que represeta la cadena encontrada
	 */
	private String respuesta;

	/*
	 * Variable que representa el arreglo de bytes a encontrar
	 */
	private byte[] cadenaCifrada;

	/*
	 * Variable que representa el alfabeto disponible
	 */
	private static String ALFABETO = "abcdefghijklmnñopqrstuvwxyz";

	/*
	 * Variable que represneta el tiempo inicial de ejecución
	 */
	private long tInicio;

	
	private int len;
	
	/**
	 * Metodo constructor de Cracker Contrasenia
	 * @param id, id del thread
	 * @param c, letra de inicio minima
	 * @param f, letra de final
	 * @param algoritmo, algoritmo con el que se cifro la cadena
	 * @param cadena, cadena de respuesta
	 * @throws NoSuchAlgorithmException
	 */
	public CrackerContrasenia(int id, int c, int f, String algoritmo, byte[] cadena,int longitud) throws NoSuchAlgorithmException {
		this.id = id;
		this.comienzo=c;
		this.finall=f;
		this.finallW+=ALFABETO.charAt(f-1);
		this.comienzoW+=ALFABETO.charAt(c);
		for (int i = 0; i < longitud-1; i++) {
			this.comienzoW+=ALFABETO.charAt(c);
			this.finallW+=ALFABETO.charAt(ALFABETO.length()-1);
		}
		this.md = MessageDigest.getInstance(algoritmo);
		this.cadenaCifrada = cadena;
		this.respuesta="";
		this.len = longitud;
	}


	/**
	 * Metodo constructor de Cracker Contrasenia para que pueda extender
	 * @param id, id del thread
	 * @param c, longitud minima de la cadena a encontrar
	 * @param f, longitud maxima de la cadena a encontrar
	 * @param algoritmo, algoritmo con el que se cifro la cadena
	 * @param cadena, cadena de respuesta
	 * @throws NoSuchAlgorithmException
	 */
	public CrackerContrasenia(int id){
		this.id = id;
	}


	/**
	 * Metodo para generar una cadena candidata de manera recursiva
	 * @param sb, String builder para construir la cadena candidata
	 * @param n, Posicion del indice de lac adena candidata
	 */
	public boolean generarCadena(String sb) {
		if (!LISTO) {		//Si ya se encontro la cadena, no hacer nada
			if (sb.length() == len) {		//Si la longitud requerida es igual al indice actual
				byte[] bytes = md.digest(sb.getBytes());	//Se codifica el candidato
				if (Arrays.equals(bytes, cadenaCifrada)) {	//Si la cadena cifrada es igual a la cadena generada
					this.respuesta = sb;
					LISTO = true;	//Manda la señal a todos los otros threads de que ya acabo
				}
				return LISTO;
			}
			for (int i = comienzo; i < finall && !LISTO; i++) {
				if(sb.isEmpty()){
					generarCadena(sb+ALFABETO.charAt(i));
				}
				else{
					for (int j = 0; j < ALFABETO.length(); j++) {
						generarCadena(sb+ALFABETO.charAt(j));
					}
				}

			}
		}
		return LISTO;
	}



	/**
	 * Metodo para calcular el tiempo de ejecución en segundos
	 * @param tinicio
	 * @param tfinal
	 * @return
	 */
	private float conversorTiempo(long tfinal) {
		return (tfinal-this.tInicio)/1000F;
	}


	public boolean getListo() {
		return LISTO;
	}


	/**
	 * Metodo RUN del thread
	 */
	@Override
	public void run() {
		this.tInicio=System.currentTimeMillis();
		System.out.println("El thread "+this.id+" va a revisar el las cadenas con longitudes en el rango ["+comienzoW+","+finallW+"]"); 
		String sb = "";
		generarCadena(sb);
		float duracion = conversorTiempo(System.currentTimeMillis());
		if(this.respuesta.equals("")) {
			//System.out.println("El thread "+this.id+" no encocntro la cadena. Se tomo: "+duracion+" segundos en determinar el resultado");
		}else {
			System.out.println("El thread "+this.id+" encocntro la cadena. Se tomo: "+duracion+" segundos en determinar el resultado. La cadena es: "+ this.respuesta);
		}
	}
}
