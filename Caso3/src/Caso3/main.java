package Caso3;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.lang.management.ManagementFactory;
import java.lang.Math;
import javax.management.Attribute;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.AttributeList;

public class main {

	public final static String[] algoritmos = new String[]{"MD5","SHA-256","SHA-384","SHA-512"};

	public static int MAXLENGTH;

	/**
	 * Metodo main
	 * @param args, Argumentos del programa
	 * @throws Exception Si se genera algun error, sale de este metodo
	 */
	public static void main(String[] args) throws Exception {
		int algoritmo = 0;
		String cadena = "dzzzz";
		MAXLENGTH=cadena.length();
		System.out.println("El algoritmo seleccionado es: " +algoritmos[algoritmo]);
		if(verificarCadena(cadena)) {
			byte[] codigoHash = generarCodigo(cadena, algoritmos[algoritmo]);
			identificarEntrada(codigoHash, algoritmos[algoritmo]);
		}else {
			System.err.println("La cadena "+cadena+ " no es una cadena valida, verifique que sean maximo 7 caracteres alfabeticos en minuscula");
			throw new Exception("Cadena no valida");
		}
	}

	/**
	 * Metodo que genera el codigo Hash de la cadena.
	 * @param cadena. Cadena a codificar
	 * @param algoritmo. Algoritmo de para generar el codigo hash. Puede ser MD5,SHA256,SHA384,SHA512
	 * @return La cadena codifiada en bytes
	 * @throws NoSuchAlgorithmException Si se ingresa un algoritmo inexistente o uno no valido
	 */
	public static byte[] generarCodigo(String cadena, String algoritmo) throws NoSuchAlgorithmException{
		if(!contiene(algoritmos,algoritmo)) {
			throw new NoSuchAlgorithmException("No se ingreso un algoritmo valido");
		}
		MessageDigest md = MessageDigest.getInstance(algoritmo);
		md.update(cadena.getBytes());
		return  md.digest();
	}

	/**
	 * Metodo que identifica la cadena de entrada
	 * @param cadena. Codigo hash de la cadena
	 * @param algoritmo. Algoritmo de cifrado para generar el codigo
	 * @throws NoSuchAlgorithmException 
	 */
	public static void identificarEntrada(byte[]cadena,String algoritmo) throws NoSuchAlgorithmException{
		int letrasAlfabeto = 27;
		int numeroThreads = 8;	
		int rango = letrasAlfabeto/numeroThreads;	//Se obtiene en cuantas particiones se tiene que 
		int fin = 0;					//Se define el fin para la iteracion
		int id=0;						//ID de los threads
		CrackerContrasenia[] threads = new CrackerContrasenia[numeroThreads+1];
		while(fin<letrasAlfabeto && id<numeroThreads) {	 
			int com = fin+1;	//Se calculan los rangos
			fin = com + rango;
			if (fin>letrasAlfabeto) {
				fin = letrasAlfabeto-1*(numeroThreads-1-id);
			}
			threads[id] = new CrackerContrasenia(id+1, com-1, fin,algoritmo, cadena,MAXLENGTH);
			id++;
		}
		threads[id] = new medicionCPU(id+1);
		try {
			for (int i = threads.length-1; i >=0 ; i--) {
				threads[i].start();
			}
			id++;
			;


		}catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * Metodo que verifica que la cadena tenga maximo 7 caracteres alfabeticos en minuscula
	 * @param cadena, la cadena a codificar
	 * @return True si es valida, False si no es valida
	 */
	private static boolean verificarCadena(String cadena) {
		return cadena.matches("^[a-z]{1,"+MAXLENGTH+"}$");
	}

	/**
	 * Metodo que verifica si el arreglo de string tiene un determinado arreglo
	 * @param arreglo, Arreglo de Strings
	 * @param elemento, Elemento a buscar
	 * @return True si esta dentro del arreglo, false si no
	 */
	private static boolean contiene(String[] arreglo, String elemento) {
		boolean esta = false;
		for (int i = 0; i < arreglo.length && !esta; i++) {
			if(arreglo[i].equals(elemento)) {
				esta = true;
			}
		}
		return esta;
	}

	/**
	 * @throws Exception 
	 * Imprime el Menu del programa
	 * @param in, Scanner que toma la entrada del programa
	 * @return El algoritmo elegido
	 * @throws 
	 */
	private static int menu(Scanner in) throws Exception {
		int resp=-1;
		System.out.println("Ingrese el algoritmo con el cual desea generar el codigo hash");
		for (int i = 0; i < algoritmos.length; i++) {
			System.out.println((i+1)+"."+algoritmos[i]);
		}
		try {
			resp = in.nextInt()-1;
			if(resp>=algoritmos.length) {
				throw new Exception("Se ingreso un numero arriba del rango permitido");
			}
		}catch (Exception e) {
			System.err.println("No se ingreso un numero");
			throw new Exception("No se ingreso un numero");
		}
		return resp;
	}




}
