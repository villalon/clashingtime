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
public class BuscadorAlumnosPorNombre extends AbstractBuscadorPersonasPorNombre {

	/**
	 * Constructor del buscador de profesores por nombre
	 */
	public BuscadorAlumnosPorNombre() {
		super();
		this.setNombreBoton("Buscar alumno");
		btnBuscar.setText(this.getNombreBoton());
		lblPersonas.setText("Ingrese las primeras letras del apellido del alumno a buscar.");
	}

	@Override
	public void loadPersonas(String apellido, AsyncCallback<String[][]> async) {
		getAgendaService().buscarAlumnos(apellido, async);
	}
}
