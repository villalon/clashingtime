/**
 * 
 */
package cl.uai.webcursos.client.tabla;


import cl.uai.webcursos.client.selectores.controles.ListBoxPersonas;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Clase que implementa {@link AbstractTablaHorarios} para alumnos
 * @author Jorge Villalon
 *
 */
public class TablaHorariosAlumno extends AbstractTablaHorarios {

	/**
	 * @see cl.uai.webcursos.client.tabla.AbstractTablaHorarios#loadActividadesCelda(java.lang.String[], int, int, com.google.gwt.user.client.rpc.AsyncCallback)
	 */
	@Override
	public void loadActividadesCelda(ListBoxPersonas ruts, int dia, int modulo,
			AsyncCallback<String[][]> async) {
		getAgendaService().getActividadesAlumnosCriterios(ruts.getSecciones(),
				ruts.getRuts(), 
				ruts.getCarreras(),
				ruts.getCohortes(),
				dia, modulo, async);
	}

	/**
	 * @see cl.uai.webcursos.client.tabla.AbstractTablaHorarios#loadHorario(java.lang.String[], java.lang.String, com.google.gwt.user.client.rpc.AsyncCallback)
	 */
	@Override
	public void loadHorario(ListBoxPersonas ruts, String[] tipoActividad,
			AsyncCallback<String[][]> async) {
		getAgendaService().getHorariosAlumnosCriterios(ruts.getSecciones(), ruts.getRuts(), ruts.getCarreras(), ruts.getCohortes(), tipoActividad, async);
	}

}
