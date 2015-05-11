/**
 * 
 */
package cl.uai.webcursos.client.selectores.buscadores;

import cl.uai.webcursos.client.horario.AbstractAgendaComposite;
import cl.uai.webcursos.client.selectores.controles.ListBoxPersonas.Criterio;

/**
 * Clase abstracta que sirve de base para todos los buscadores de personas.
 * Define una tabla de personas con nombre y rut.
 * @author Jorge Villalon
 *
 */
public abstract class AbstractBuscadorPersonas extends AbstractAgendaComposite {

	protected String[][] personas = null;
	protected String[][] secciones = null;
	protected Criterio criterio = Criterio.RUT;

	/**
	 * @return the criterio
	 */
	public Criterio getCriterio() {
		return criterio;
	}

	/**
	 * @return the secciones
	 */
	public String[][] getSecciones() {
		return secciones;
	}

	/**
	 * @param secciones the secciones to set
	 */
	protected void setSecciones(String[][] secciones) {
		this.secciones = secciones;
	}

	/**
	 * Tabla de personas encontradas por el buscador
	 * @return tabla con ruts y nombres de personas
	 */
	public String[][] getPersonas() {
		return personas;
	}

	protected void setPersonas(String[][] result) {
		this.personas = result;
	}
}
