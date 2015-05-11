/**
 * 
 */
package cl.uai.webcursos.client;

import java.io.Serializable;

/**
 * Clase que implementa las excepciones en la aplicación de agenda
 * @author Jorge Villalon
 *
 */
public class AgendaException extends Exception implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String message;
	
	/**
	 * 
	 */
	public AgendaException() {
		this.message = "No idea what's going on!";
	}

	/**
	 * 
	 */
	public AgendaException(String message) {
		this.message = message;
	}

	/**
	 * @param cause
	 */
	public AgendaException(Throwable cause) {
		super(cause);
		this.message = cause.getMessage();
	}
	
	@Override
	public String getMessage() {
		return message;
	}
}
