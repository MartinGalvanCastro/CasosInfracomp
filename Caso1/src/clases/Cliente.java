package clases;

public class Cliente implements Runnable{

	/*
	 * Identificador del Cliente
	 */
	private int id;
	
	/*
	 * Buffer del Cliente
	 */
	private Buffer3<Mensaje> buff;
	
	/*
	 * Mensajes a enviar, mensajes enviados y mensajes respondidos
	 */
	private int mensajesEnviar,mensajesEnviados;
	
	
	/**
	 * Metodo Constructor
	 * @param id, id del cliente
	 * @param buff, buffer del cliente
	 */
	public Cliente(int id,Buffer3<Mensaje> buff,int mensajesEnviar){
		this.id=id;
		this.buff=buff;
		this.mensajesEnviar=mensajesEnviar;
	}
	
	/**
	 * Metodo que envia mensajes al buffer
	 * @throws InterruptedException 
	 */
	public void enviarMensaje() throws InterruptedException{
		mensajesEnviados++;
		mensajesEnviar--;
		Mensaje mensajeEnviar = generarMensaje();
		buff.enqueue(mensajeEnviar);
		while(buff.estaEnBuffer(mensajeEnviar)){
			synchronized (mensajeEnviar) {
				try {
					mensajeEnviar.wait();
				} catch (Exception e) {
					System.err.println(String.format("Error en el mensajeEnviar.wait() para el mensaje %i para el cliente %i", mensajeEnviar.getNum(),mensajeEnviar.getCliente().getId()));
					System.err.println(e.getStackTrace());
				}
			}
		}
		mensajesEnviar--;
	}
	
	/**
	 * Metodo que retorna si el cliente ya acabo su operación
	 */
	public boolean yaAcabo(){
		return (mensajesEnviar==0)? true:false;
	}
	
	/**
	 * Metodo que devuelve la id del Cliente
	 * @return id Cliente
	 */
	public int getId() {
		return id;
	}

	/**
	 * Metodo que cambia la id del cliente
	 * @param id, nueva id del cliente
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Metodo que returna el buffer del cliente
	 * @return El buffer del cliente
	 */
	public Buffer3<Mensaje> getBuff() {
		return buff;
	}

	
	/**
	 * Metodo le asigna un nuevo buffer al cliente
	 * @param buff, nuevo buffer del cliente
	 */
	public void setBuff(Buffer3<Mensaje> buff) {
		this.buff = buff;
	}

	/**
	 * Metodo que devuele cuantos mensajes toca eviaer
	 * @return
	 */
	public int getMensajesEnviar() {
		return mensajesEnviar;
	}

	/**
	 * Metodo que modificca la cantidad de mensajes a enviar
	 * @param mensajesEnviar
	 */
	public void setMensajesEnviar(int mensajesEnviar) {
		this.mensajesEnviar = mensajesEnviar;
	}

	/**
	 * Metodo que retorna la cantidad de mensajes enviados
	 * @return
	 */
	public int getMensajesEnviados() {
		return mensajesEnviados;
	}

	/**
	 * Metodo que cambia la cantidad de mensajes enviados
	 * @param mensajesEnviados
	 */
	public void setMensajesEnviados(int mensajesEnviados) {
		this.mensajesEnviados = mensajesEnviados;
	}


	/**
	 * Metodo que se ejecuta cuando se ejeccuta start()
	 */
	@Override
	public void run() {
		while(!yaAcabo()){
			try {
				enviarMensaje();
			} catch (InterruptedException e) {
				System.out.println("Error al mandar el mensaje");
				e.printStackTrace();
			}
		}
		System.out.println("Cliente acabo");
		buff.decClientesEsperados();
		System.out.println(buff.getClientes());
		
	}
	
	/**
	 * Metodo que genera los mensajes
	 * @return
	 */
	private Mensaje generarMensaje(){
		return new Mensaje(mensajesEnviados++, this);
	}


}
