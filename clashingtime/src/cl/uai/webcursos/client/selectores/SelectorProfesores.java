/**
 * 
 */
package cl.uai.webcursos.client.selectores;

import cl.uai.webcursos.client.selectores.buscadores.BuscadorProfesoresPorAsignatura;
import cl.uai.webcursos.client.selectores.buscadores.BuscadorProfesoresPorFacultad;
import cl.uai.webcursos.client.selectores.buscadores.BuscadorProfesoresPorNombre;

/**
 * Selector de profesores según una lista de criterios, que corresponden
 * a buscadores.
 * @see
 * BuscadorProfesoresPorNombre
 * BuscadorProfesoresPorFacultad
 * BuscadorProfesoresPorAsignatura
 * @author Jorge Villalon
 *
 */
public class SelectorProfesores extends AbstractSelectorPersonas {

	public SelectorProfesores() {
		addCriterio(new BuscadorProfesoresPorNombre(),"Por Nombre");
		addCriterio(new BuscadorProfesoresPorFacultad(),"Por Facultad");
		addCriterio(new BuscadorProfesoresPorAsignatura(),"Por Asignatura");
		lblSeleccionados.setText("Profesores seleccionados para buscar horario");
	}
}
