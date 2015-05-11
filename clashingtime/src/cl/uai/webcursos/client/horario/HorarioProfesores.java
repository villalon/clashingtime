/**
 * 
 */
package cl.uai.webcursos.client.horario;

import cl.uai.webcursos.client.selectores.SelectorProfesores;
import cl.uai.webcursos.client.tabla.TablaHorariosProfesor;

/**
 * Clase que representa una interfaz para buscar horarios de profesores. Incluye
 * buscadores dentro de un selector y para mostrar horarios.
 * 
 * Esta clase solamente agrega el selector adecuado y cambia el título puesto
 * que la lógica está implementada en {@link AbstractHorarioPersonas}.
 * @author Jorge Villalon
 *
 */
public class HorarioProfesores extends AbstractHorarioPersonas {

	public HorarioProfesores() {
		super(new SelectorProfesores());
		
		tablaHorarios = new TablaHorariosProfesor();
		tablaHorarios.addAsyncCallHandler(handlerTablaHorarios);
		lblTitle.setText("Use los distintos criterios para buscar profesores, agregue los que desee a la lista y luego busque sus horarios.");
		
		vpanel.add(tablaHorarios);
		
		initWidget(vpanel);
	}	
}
