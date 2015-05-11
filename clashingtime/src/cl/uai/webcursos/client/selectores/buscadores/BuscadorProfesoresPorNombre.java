/**
 * 
 */
package cl.uai.webcursos.client.selectores.buscadores;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Buscador de profesores dado el comienzo de su apellido.
 * @author Jorge Villalon
 *
 */
public class BuscadorProfesoresPorNombre extends AbstractBuscadorPersonasPorNombre {

	/**
	 * Constructor del buscador de profesores por nombre
	 */
	public BuscadorProfesoresPorNombre() {
		super();
		this.setNombreBoton("Buscar profesor");
		btnBuscar.setText(this.getNombreBoton());
		lblPersonas.setText("Ingrese las primeras letras del apellido del profesor a buscar.");
	}

	@Override
	public void loadPersonas(String apellido, AsyncCallback<String[][]> async) {
		getAgendaService().buscarProfesores(apellido, async);
	}
}
