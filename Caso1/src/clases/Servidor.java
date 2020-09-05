package clases;

public class Servidor implements Runnable{

	/*
	 * Identificador del servidor
	 */
	private int id;

	/*
	 *Buffer del servidor 
	 */
	private Buffer3<Mensaje> buff;


	/**
	 * Metodo Constructor
	 * @param id, id del cliente
	 * @param buff, buffer del cliente
	 */
	public Servidor(int id, Buffer3<Mensaje> buff) {
		this.id = id;
		this.buff = buff;
	}


	/**
	 * Metodo que devuelve la id del servidor
	 * @return id del servidor
	 */
	public int getId() {
		return id;
	}


	/**
	 * Metodo que cambia la id del servidor
	 * @param id, nueva id del servidor
	 */
	public void setId(int id) {
		this.id = id;
	}


	/**
	 * Metodo que retorna el buffer del servidor
	 * @return Buffer del servidor
	 */
	public Buffer3<Mensaje> getBuff() {
		return buff;
	}


	/**
	 * Metodo que cambia el buffer del servidor
	 * @param buff, nuevo buffer del servidor
	 */
	public void setBuff(Buffer3<Mensaje> buff) {
		this.buff = buff;
	}


	public void responderMensaje() throws InterruptedException{
		Mensaje mensajePorResponder=(Mensaje) buff.dequeue();
		if(mensajePorResponder==null){
			Thread.yield();
		}
		else{
			synchronized (mensajePorResponder) {
				mensajePorResponder.addValor();
				mensajePorResponder.notifyAll();
			}
		}
	}

	/**
	 * Metodo que se ejecuta cuando se invoca start()
	 */
	@Override
	public void run() {
		while(buff.getClientes()>0){
			try {
				responderMensaje();
			} catch (InterruptedException e) {
				System.out.println("Error al responder el mensaje");
				e.printStackTrace();
			}
		}
		System.out.println("Acaban Servidores");
	}

}
