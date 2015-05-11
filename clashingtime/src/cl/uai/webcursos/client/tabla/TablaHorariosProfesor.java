/**
 * 
 */
package cl.uai.webcursos.client.tabla;


import cl.uai.webcursos.client.selectores.controles.ListBoxPersonas;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Clase que implementa la {@link AbstractTablaHorarios} para profesores.
 * @author Jorge Villalon
 *
 */
public class TablaHorariosProfesor extends AbstractTablaHorarios {

	/**
	 * @see cl.uai.webcursos.client.tabla.AbstractTablaHorarios#loadActividadesCelda(java.lang.String[], int, int, com.google.gwt.user.client.rpc.AsyncCallback)
	 */
	@Override
	public void loadActividadesCelda(ListBoxPersonas ruts, int dia, int modulo,
			AsyncCallback<String[][]> async) {
		getAgendaService().getActividadesProfesores(ruts.getRuts(), dia, modulo, async);
	}

	/**
	 * @see cl.uai.webcursos.client.tabla.AbstractTablaHorarios#loadHorario(java.lang.String[], java.lang.String, com.google.gwt.user.client.rpc.AsyncCallback)
	 */
	@Override
	public void loadHorario(ListBoxPersonas ruts, String[] tipoActividad,
			AsyncCallback<String[][]> async) {
		getAgendaService().getHorariosProfesores(ruts.getRuts(), tipoActividad, async);

	}

}
