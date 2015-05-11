/**
 * 
 */
package cl.uai.webcursos.client.tabla;

import com.google.gwt.user.client.ui.Label;

/**
 * Esta clase corresponde a las celdas de la tabla de horarios. Cada
 * una de ellas representa un día y un módulo específico.
 * @author Jorge Villalón
 *
 */
public class CeldaTablaHorario extends Label {

	private boolean available;
	private int modulo;
	private int dia;

	/**
	 * Constructor de la celda de una tabla de horarios
	 * @param text texto a mostrar en la celda
	 * @param row modulo de la celda
	 * @param column dia de la celda
	 * @param available si está disponible
	 */
	public CeldaTablaHorario(String text, int row, int column, boolean available) {
		super(text);
		this.modulo = row;
		this.dia = column;
		this.available = available;
		this.setStylePrimaryName("celdaHorario");

		if(available) {
			this.setTitle("Libre");
//			this.setStylePrimaryName("horarioDisponible");
		} else {
			this.setTitle("Ocupado");
//			this.setStylePrimaryName("horarioNoDisponible");
		}
		this.setTitle("Click para ver detalle.");
	}
	
	/**
	 * Módulo en que la celda está ubicada
	 * @return número de módulo (del 1 al 9)
	 */
	public int getModulo() {
		return modulo;
	}
	
	/**
	 * Día en que la celda está ubicada
	 * @return número de día (ej: Lunes = 1)
	 */
	public int getDia() {
		return dia;
	}
	
	/**
	 * Indica si la celda está libre o tiene actividades
	 * @return si la celda está libre
	 */
	public boolean isAvailable() {
		return available;
	}
}
