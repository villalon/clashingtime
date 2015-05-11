/**
 * 
 */
package cl.uai.webcursos.client.selectores;

import cl.uai.webcursos.client.selectores.buscadores.BuscadorAlumnosPorCarrera;
import cl.uai.webcursos.client.selectores.buscadores.BuscadorAlumnosPorNombre;
import cl.uai.webcursos.client.selectores.buscadores.BuscadorAlumnosPorSecciones;

/**
 * Selector de alumnos basado en criterios.
 * @see
 * BuscadorAlumnosPorSecciones
 * @author Jorge Villalon
 *
 */
public class SelectorAlumnos extends AbstractSelectorPersonas {

	public SelectorAlumnos() {
		addCriterio(new BuscadorAlumnosPorNombre(),"Por Nombre");
		addCriterio(new BuscadorAlumnosPorSecciones(),"Por Secciones");
		addCriterio(new BuscadorAlumnosPorCarrera(),"Por Carrera");
		lblSeleccionados.setText("Alumnos seleccionados para buscar horario");
	}
}
