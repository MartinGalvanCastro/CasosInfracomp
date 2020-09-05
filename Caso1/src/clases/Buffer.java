package clases;
import java.util.LinkedList;


public class Buffer {

	/*
	 * Tamaño del Buffer
	 */
	private int tamaio;

	/*
	 * Almacenamiento de Mensajes
	 */
	private LinkedList<Mensaje> mensajes;

	/*
	 *Clientes Esperados 
	 */
	private int clientesEsperados;

	/*
	 * Objetos para controlar los estados de vacio y lleno
	 */
	private Object lleno,vacio;

	/**
	 * Metodo constructor del buffer
	 * @param tamaio
	 */
	public Buffer(int tamanio, int clietesEsperados){
		this.tamaio=tamanio;
		this.mensajes=new LinkedList<Mensaje>();
		this.clientesEsperados=clietesEsperados;
		this.lleno=new Thread();
		this.vacio=new Thread();
	}


	/**
	 * Metodo para agregar un mensaje a el buffer
	 * Almacena el mensaje al final de la cola
	 */
	public void almacenar(Mensaje mensaje){
		synchronized (lleno) {
			while(mensajes.size()==tamaio){
				try{
					lleno.wait();
				}catch (InterruptedException e) {
					new String();
					System.err.println(String.format("Error en el lleno.wait() para el mensaje %i para el cliente %i", mensaje.getNum(),mensaje.getCliente().getId()));
					System.err.println(e.getStackTrace());
				}
			}
		}
		synchronized (this) {mensajes.addLast(mensaje);}
		synchronized (vacio) {vacio.notify();}
	}

	/**
	 * Metodo que retira el primer mensaje de la cola del buffer
	 * @return
	 */
	public Mensaje retirar(){
		synchronized (vacio) {
			while(mensajes.size()==0){
				try{
					Thread.yield();
				}catch (Exception e) {
					System.err.println("Error en el vacio.wait()");
					System.err.println(e.getStackTrace());
				}
			}
		}
		Mensaje mensaje;
		synchronized (this) {mensaje=mensajes.pop();}
		synchronized (lleno) {lleno.notifyAll();}
		return mensaje;
	}




	/**
	 * Metodo que retorna el tamaño del buffer
	 * @return, tamaño del buffer
	 */
	public int getTamaio() {
		return tamaio;
	}

	/**
	 * Metodo que cambia el tamaño del buffer
	 * @param tamaio, nuevo tamaño del bufer
	 */
	public void setTamaio(int tamaio) {
		this.tamaio = tamaio;
	}


	/**
	 * Metodo que retorna la cola de mensajes
	 * @return Cola de mensajes
	 */
	public LinkedList<Mensaje> getMensajes() {
		return mensajes;
	}


	/**
	 * Metodo que cambia la ccola de mensajes
	 * @param mensajes, nueva lista de mensajes
	 */
	public void setMensajes(LinkedList<Mensaje> mensajes) {
		this.mensajes = mensajes;
	}


	/**
	 * Metodo que retorna la cantidad de clientes esperados
	 * @return
	 */
	public int getClientesEsperados() {
		return clientesEsperados;
	}


	/**
	 * Metodo que cambia la cantidad de clientes essperados
	 * @param clientesEsperados
	 */
	public void decClientesEsperados() {
		this.clientesEsperados--;
	}

	/**
	 * Metodo que retorna el objeto de vacio
	 * @return
	 */
	public Object getVacio() {
		return vacio;
	}

	/**
	 * Metodo que cambia el objeto Vacio
	 * @param vacio
	 */
	public void setVacio(Thread vacio) {
		this.vacio = vacio;
	}

	/**
	 * Metodo que retorna el objeto de lleno
	 * @return
	 */
	public Object getLleno() {
		return lleno;
	}

	/**
	 * Metodo que cambia el objeto de vacio
	 * @param lleno
	 */
	public void setLleno(Thread lleno) {
		this.lleno = lleno;
	}
	
	/**
	 * Metodo que retorna si un mensaje esta en el buffer
	 */
	public boolean estaEnBuffer(Mensaje mensaje){
		return mensajes.contains(mensaje)?true:false;
	}
	
	/**
	 * Metodo que retorna si ya se atendieron todos los clientes
	 */
	public boolean apagarSistema(){
		return (this.clientesEsperados==0)?true:false;
	}

}
