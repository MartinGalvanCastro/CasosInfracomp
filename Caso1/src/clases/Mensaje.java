package clases;

public class Mensaje implements Comparable<Mensaje>{

	/*
	 * Remitente del mensaje
	 */
	private Cliente cliente;


	/*
	 * Cotenido del mensaje
	 */
	private String contenido;


	/*
	 * Numero del Mensaje
	 */
	private int num;


	/*
	 * Valor del mensaje
	 */
	private int valor;


	public Mensaje(int num, Cliente cliente){
		this.num=num;
		this.cliente=cliente;
		this.contenido = setContenido();
		this.valor=0;
	}


	/**
	 * Metodo que devuelve el cliente que mando el mensaje
	 * @return
	 */
	public Cliente getCliente() {
		return cliente;
	}


	/**
	 * Metodo que cambia el cliente que mando el mensaje
	 * @param cliente
	 */
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
		this.contenido=setContenido();
	}

	/**
	 * Metodo que devuelve el contenido del mensaje
	 * @return
	 */
	public String getContenido() {
		return contenido;
	}

	/**
	 * Metodo que cambia el contenido del mensaje segun un formato dado
	 * @return
	 */
	public String setContenido() {
		return String.format(
				"Mensaje #: %d \n"
						+"Remintente: Cliente ID %d \n"
						,this.num,this.cliente.getId());
	}

	/**
	 * Metodo que devuelve el # de mensaje
	 * @return
	 */
	public int getNum() {
		return num;
	}

	/**
	 * Metodo que cambia el # de mensaje
	 * @param num
	 */
	public void setNum(int num) {
		this.num = num;
	}

	/**
	 * Metodo que retorna el valor del mensaje
	 * @return
	 */
	public int getValor() {
		return valor;
	}

	/**
	 * Metodo que aumenta el vaor del mensaje cuanddo se responde
	 */
	public void addValor() {
		this.valor++;
	}


	@Override
	public int compareTo(Mensaje o) {
		if(o.cliente.getId()==this.cliente.getId() && o.num==this.num){
			return 1;
		}else{
			return 0;
		}
	}




}
