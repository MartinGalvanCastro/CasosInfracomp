package clases;

import java.util.LinkedList;
import java.util.List;

/*
 * Clase Buffer, se implemento de manera generica
 */
public class Buffer<E> {

	/*
	 * Cola que almacena los mensajes. Se implelento de manera generica
	 */
	private List<E> cola = new LinkedList<E>();
	
	/*
	 * Limite del buffer
	 */
	private int  limite;
	
	/*
	 * Numero de clientes en el sistema
	 */
	private int numerosClientes;

	/**
	 * Metodo constructor
	 * @param limite, limite de objetos en el buffer
	 * @param numerosClientes
	 */
	public Buffer(int limit, int  numerosClientes){
		this.limite = limit;
		this.numerosClientes=numerosClientes;
	}

	/**
	 * Metodo para meter cosas al mensaje
	 * @param item, item a meter en el buffer. En este caso es un mensaje
	 * @throws InterruptedException si hay algun problema en el wait
	 */
	public synchronized void meterObjeto(E item) throws InterruptedException  {
		//Mientras el tamaño de la cola sea igual al limite, el buffer hace wait
		while(this.cola.size() == this.limite) {
			wait();
		}
		
		//Se agrega el item al buffer si hay espacio disponible
		this.cola.add(item);
		
		//Si existe almenos un item en la cola se avisa a todos
		if(this.cola.size() > 0) {
			notifyAll();
		}
	}


	/**
	 * Metodo apra sacar un objeto del buffer. En este caso un mensaje
	 * @return Objeto tipo E si hay un item en la cola. Si no se retorna nulo
	 * @throws InterruptedException si hay algun problema en el wait
	 */
	public synchronized E sacarObjeto() throws InterruptedException{
		//Si el tamaño de la cola es cero se retorna nulo
		if(this.cola.size() == 0){
			return null;
		}
		//Si el tamaño de la cola es menor que el limite se avisa a todos que se puede meter
		if(this.cola.size() < this.limite){
			notifyAll();
		}
		
		//Se retorna el primer item de la cola
		return this.cola.remove(0);
	}

	/**
	 * Metodo que avisa si un item esta en el buffer. En este caso un mensaje
	 * @param msg, mensaje a preguntar si esta en el buffer
	 * @return true si esta, false en caso contrario
	 */
	public synchronized boolean estaEnBuffer(E msg){
		return cola.contains(msg);
	}

	/**
	 * Metodo que reduce la cantidad de clientes del buffer
	 */
	public synchronized void decClientesEsperados(){
		numerosClientes--;
	}

	/**
	 * Metodo que retorna la cantidad actual de clientes en el buffers
	 * @return
	 */
	public synchronized int getClientes(){
		return numerosClientes;
	}

}

