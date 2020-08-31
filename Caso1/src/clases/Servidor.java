package clases;

public class Servidor implements Runnable{

	/*
	 * Identificador del servidor
	 */
	private int id;
	
	/*
	 *Buffer del servidor 
	 */
	private Buffer buff;
	
	
	/**
	 * Metodo Constructor
	 * @param id, id del cliente
	 * @param buff, buffer del cliente
	 */
	public Servidor(int id, Buffer buff) {
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
	public Buffer getBuff() {
		return buff;
	}


	/**
	 * Metodo que cambia el buffer del servidor
	 * @param buff, nuevo buffer del servidor
	 */
	public void setBuff(Buffer buff) {
		this.buff = buff;
	}


	public void responderMensaje(){
		Mensaje mensajePorResponder=buff.retirar();
		synchronized (mensajePorResponder) {
			mensajePorResponder.addValor();
			mensajePorResponder.notify();
		}
	}

	/**
	 * Metodo que se ejecuta cuando se invoca start()
	 */
	@Override
	public void run() {
		while(!buff.apagarSistema()){
			responderMensaje();
		}
		
	}

}
