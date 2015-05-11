/**
 * 
 */
package cl.uai.webcursos.client.horario;

import cl.uai.webcursos.client.selectores.SelectorAlumnos;
import cl.uai.webcursos.client.tabla.TablaHorariosAlumno;

/**
 * Clase que representa una interfaz para buscar horarios de alumnos. Incluye
 * buscadores dentro de un selector y para mostrar horarios.
 * 
 * Esta clase solamente agrega el selector adecuado y cambia el título puesto
 * que la lógica está implementada en {@link AbstractHorarioPersonas}.
 * @author Jorge Villalon
 *
 */
public class HorarioAlumnos extends AbstractHorarioPersonas {

	public HorarioAlumnos() {
		super(new SelectorAlumnos());
		
		tablaHorarios = new TablaHorariosAlumno();
		tablaHorarios.addAsyncCallHandler(handlerTablaHorarios);
		lblTitle.setText("Use los distintos criterios para buscar alumnos, agregue los que desee a la lista y luego busque sus horarios.");
		for(int i=0;i<chkTipoAsignatura.length;i++)
			chkTipoAsignatura[i].setValue(true);
		
		vpanel.add(tablaHorarios);
		
		initWidget(vpanel);
	}
}
