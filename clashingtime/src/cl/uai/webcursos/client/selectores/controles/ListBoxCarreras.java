/**
 * 
 */
package cl.uai.webcursos.client.selectores.controles;

import cl.uai.webcursos.client.event.AsyncCallStartedEvent;

/**
 * ListBox que contiene las facultades que dictan asignaturas.
 * @author Jorge Villalon
 *
 */
public class ListBoxCarreras extends AbstractListBoxAgenda {

	public ListBoxCarreras() {
		setDefaultItemText("Seleccione Carrera");
		setDefaultItemValue("0");
	}
	
	@Override
	protected void onLoad() {
		super.onLoad();
		handlerManager.fireEvent(new AsyncCallStartedEvent());
		getAgendaService().getCarreras(async);
	}
}
