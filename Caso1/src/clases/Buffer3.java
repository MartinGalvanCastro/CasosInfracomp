package clases;

import java.util.LinkedList;
import java.util.List;

public class Buffer3<E> {

	private List<E> queue = new LinkedList<E>();
	private int  limit;
	private int numerosClientes;

	public Buffer3(int limit, int  numerosClientes){
		this.limit = limit;
		this.numerosClientes=numerosClientes;
	}


	public synchronized void enqueue(E item)
			throws InterruptedException  {
		while(this.queue.size() == this.limit) {
			wait();
		}
		this.queue.add(item);
		if(this.queue.size() > 0) {
			notifyAll();
		}
	}


	public synchronized Object dequeue()
			throws InterruptedException{
		if(this.queue.size() == 0){
			return null;
		}
		if(this.queue.size() < this.limit){
			notifyAll();
		}

		return this.queue.remove(0);
	}

	public synchronized boolean estaEnBuffer(E msg){
		return queue.contains(msg);
	}

	public synchronized void decClientesEsperados(){
		numerosClientes--;
	}

	public synchronized int getClientes(){
		return numerosClientes;
	}

}

