package Caso2;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;





public class CrackerContrasenia implements Runnable{

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
	private static char[] ALFABETO = "abcdefghijklmnñopqrstuvwxyz".toCharArray();

	/*
	 * Variable que represneta el tiempo inicial de ejecución
	 */
	private long tInicio;
	
	/**
	 * Metodo constructor de Cracker Contrasenia
	 * @param id, id del thread
	 * @param c, longitud minima de la cadena a encontrar
	 * @param f, longitud maxima de la cadena a encontrar
	 * @param algoritmo, algoritmo con el que se cifro la cadena
	 * @param cadena, cadena de respuesta
	 * @throws NoSuchAlgorithmException
	 */
	public CrackerContrasenia(int id, int c, int f, String algoritmo, byte[] cadena, long tInicio) throws NoSuchAlgorithmException {
		this.id = id;
		this.comienzo = c;
		this.finall = f;
		this.md = MessageDigest.getInstance(algoritmo);
		this.cadenaCifrada = cadena;
		this.respuesta="";
		this.tInicio = tInicio;
	}


	/**
	 * Metodo para generar una cadena candidata de manera recursiva
	 * @param sb, String builder para construir la cadena candidata
	 * @param n, Posicion del indice de lac adena candidata
	 */
	public boolean generarCadena(StringBuilder sb, int n) {
		if (!LISTO) {		//Si ya se encontro la cadena, no hacer nada
			if (n == sb.length()) {		//Si la longitud requerida es igual al indice actual
				String candidato = sb.toString();	//Se obtiene el candidato
				byte[] bytes = md.digest(candidato.getBytes());	//Se codifica el candidato

				if (Arrays.equals(bytes, cadenaCifrada)) {	//Si la cadena cifrada es igual a la cadena generada
					this.respuesta = candidato;
					LISTO = true;	//Manda la señal a todos los otros threads de que ya acabo
				}
				return LISTO;	//Retorna la cadena
			}
			//Se pobla el candidato si no tiene la longitud requerida
			for (int i = 0; i < ALFABETO.length && !LISTO; i++) {
				char letter = ALFABETO[i];
				sb.setCharAt(n, letter);
				generarCadena(sb, n + 1);	//Se hace el llamado recursivo
			}
		}
		return LISTO;	//Se retorna la cadena
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

	/**
	 * Metodo RUN del thread
	 */
	@Override
	public void run() {
		System.out.println("El thread "+this.id+" va a revisar el las cadenas con longitudes en el rango ["+comienzo+","+finall+"]");
		for (int i = comienzo; i <= finall && !LISTO; i++) {	//Siempre que algun thread no haya encontrado la cadena, 
			StringBuilder sb = new StringBuilder();     //o no se supere la longitud maxima
			sb.setLength(i);
			generarCadena(sb, 0);
		}
		float duracion = conversorTiempo(System.currentTimeMillis());
		if(this.respuesta.equals("")) {
			//System.out.println("El thread "+this.id+" no encocntro la cadena. Se tomo: "+duracion+" segundos en determinar el resultado");
		}else {
			System.out.println("El thread "+this.id+" encocntro la cadena. Se tomo: "+duracion+" segundos en determinar el resultado. La cadena es: "+ this.respuesta);
		}
	}
}