package clases;

public class Servidor implements Runnable{

	/*
	 * Identificador del servidor
	 */
	private int id;

	/*
	 *Buffer del servidor 
	 */
	private Buffer<Mensaje> buff;


	/**
	 * Metodo Constructor
	 * @param id, id del cliente
	 * @param buff, buffer del cliente
	 */
	public Servidor(int id, Buffer<Mensaje> buff) {
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
	public Buffer<Mensaje> getBuff() {
		return buff;
	}


	/**
	 * Metodo que cambia el buffer del servidor
	 * @param buff, nuevo buffer del servidor
	 */
	public void setBuff(Buffer<Mensaje> buff) {
		this.buff = buff;
	}

	/**
	 * Metodo para responder el mensaje captado en el buffer
	 * @throws InterruptedException
	 */
	public void responderMensaje() throws InterruptedException{
		Mensaje mensajePorResponder=(Mensaje) buff.sacarObjeto(); //Se obtiene el mensaje ddel buffer
		if(mensajePorResponder==null){
			Thread.yield();				//Si no hay mensaje, el servidor hace yield
		}
		else{
			synchronized (mensajePorResponder) {
				System.out.println("Se recibio el mensaje ["+mensajePorResponder.toString()+"]");
				mensajePorResponder.addValor();		//Se cambia el valor del mensaje
				mensajePorResponder.notifyAll();	//Se responde el mensaje
				System.out.println("Se Respondio el mensaje ["+mensajePorResponder.toString()+"]");
			}
		}
	}

	/**
	 * Metodo que se ejecuta cuando se invoca start()
	 */
	@Override
	public void run() {
		while(buff.getClientes()>0){	//Los servidores estan activos siempre y cuando hayan clientes en el buffer
			try {
				responderMensaje();		//Siempre van a estar respondiendo mensajes
			} catch (InterruptedException e) {
				System.out.println("Error al responder el mensaje");	//Si se decetecta un error, hay excepcion
				e.printStackTrace();
			}
		}
		System.out.println("Se apago el servidor: " + this.id);	//Mensaje avisando que se acabo el servidor
	}

}
