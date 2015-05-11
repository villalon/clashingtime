/**
 * 
 */
package cl.uai.webcursos.client.selectores.controles;

import cl.uai.webcursos.client.event.AsyncCallStartedEvent;

/**
 * ListBox de unidades académicas
 * @author Jorge Villalon
 *
 */
public class ListBoxUnidadesAcademicas extends AbstractListBoxAgenda {

	public ListBoxUnidadesAcademicas() {
		super();
		setDefaultItemText("Seleccione Unidad Académica");
		setDefaultItemValue("0");
	}

	@Override
	protected void onLoad() {
		super.onLoad();
		handlerManager.fireEvent(new AsyncCallStartedEvent());
		getAgendaService().getUnidadesAcademicas(async);
	}
}
