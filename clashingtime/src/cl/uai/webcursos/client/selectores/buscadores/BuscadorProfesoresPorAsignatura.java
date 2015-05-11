/**
 * 
 */
package cl.uai.webcursos.client.selectores.buscadores;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;

import cl.uai.webcursos.client.event.AsyncCallFinishedEvent;
import cl.uai.webcursos.client.event.AsyncCallStartedEvent;
import cl.uai.webcursos.client.selectores.controles.SuggestBoxAsignaturas;
import cl.uai.webcursos.client.selectores.controles.ListBoxPersonas.Criterio;

/**
 * Interfaz para buscar todolos profesores que dictan una asignatura en particular.
 * @author Jorge Villalon
 *
 */
public class BuscadorProfesoresPorAsignatura extends AbstractBuscadorPersonas {
	
	private VerticalPanel vpanel;
	private SuggestBoxAsignaturas suggestBoxAsignaturas;
	
	/**
	 * Constructor del buscador de profesores por asignatura
	 */
	public BuscadorProfesoresPorAsignatura() {
		super();
		criterio = Criterio.RUT;
		
		vpanel = new VerticalPanel();

		suggestBoxAsignaturas = new SuggestBoxAsignaturas();
		
		suggestBoxAsignaturas.getSuggestBox().addSelectionHandler(new SelectionHandler<Suggestion>() {			
			@Override
			public void onSelection(SelectionEvent<Suggestion> event) {
				int periodoAcademico = suggestBoxAsignaturas.getListBoxUnidadesAcademicas().getSelectedValue();
				AsyncCallback<String[][]> async =  new AsyncCallback<String[][]>() {
					
					@Override
					public void onSuccess(String[][] result) {
						setPersonas(result);
						handlerManager.fireEvent(new AsyncCallFinishedEvent(false));
					}
					
					@Override
					public void onFailure(Throwable caught) {
						handlerManager.fireEvent(new AsyncCallFinishedEvent(true));						
					}
				};
				handlerManager.fireEvent(new AsyncCallStartedEvent());
				getAgendaService().getProfesoresAsignatura(
							suggestBoxAsignaturas.getSuggestBox().getText(), periodoAcademico, async);
			}
		});
			
		vpanel.add(suggestBoxAsignaturas);

		initWidget(vpanel);
	}	
}
