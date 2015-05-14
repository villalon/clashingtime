/**
 * 
 */
package cl.uai.webcursos.client.selectores.buscadores;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;

import cl.uai.webcursos.client.event.AsyncCallFinishedEvent;
import cl.uai.webcursos.client.event.AsyncCallStartedEvent;
import cl.uai.webcursos.client.selectores.controles.SuggestBoxAsignaturas;
import cl.uai.webcursos.client.selectores.controles.ListBoxPersonas.Criterio;

/**
 * Buscador de alumnos en base a las secciones en que están
 * inscritos.
 * @author Jorge Villalon
 *
 */
public class BuscadorAlumnosPorSecciones extends AbstractBuscadorPersonas {

	private SuggestBoxAsignaturas suggestBoxAsignaturas;

	private VerticalPanel vpanel;
	private Label lblSecciones;
	private ListBox lbSecciones;

	/**
	 * Constructor del buscador de alumnos por secciones
	 */
	public BuscadorAlumnosPorSecciones() {
		super();

		criterio = Criterio.SECCION;

		vpanel = new VerticalPanel();
		suggestBoxAsignaturas = new SuggestBoxAsignaturas();

		lblSecciones = new Label("Secciones");
		lbSecciones = new ListBox();
		lbSecciones.setVisibleItemCount(5);
		lbSecciones.setEnabled(false);

		suggestBoxAsignaturas.getSuggestBox().addSelectionHandler(new SelectionHandler<Suggestion>() {

			@Override
			public void onSelection(SelectionEvent<Suggestion> event) {
				lbSecciones.clear();
				lbSecciones.addItem("Loading...");
				handlerManager.fireEvent(new AsyncCallStartedEvent());
				String asignatura = suggestBoxAsignaturas.getSuggestBox().getText();
				lblSecciones.setText("Secciones de " + asignatura);
				int periodoAcademicoId = suggestBoxAsignaturas.getListBoxUnidadesAcademicas().getSelectedValue();
				getAgendaService().getSeccionesAsignatura(asignatura, periodoAcademicoId, new AsyncCallback<String[][]>() {

					@Override
					public void onSuccess(String[][] result) {
						lbSecciones.clear();
						for(String[] s : result) {
							lbSecciones.addItem(s[0], s[1]);
						}
						lbSecciones.setEnabled(true);
						handlerManager.fireEvent(new AsyncCallFinishedEvent(false));
					}

					@Override
					public void onFailure(Throwable caught) {
						lbSecciones.clear();
						lbSecciones.addItem("Error!");
						lbSecciones.setTitle(caught.getMessage());
						lbSecciones.setEnabled(false);
						handlerManager.fireEvent(new AsyncCallFinishedEvent(true));
					}
				});
			}
		});

		vpanel.add(suggestBoxAsignaturas);
		vpanel.add(lblSecciones);
		vpanel.add(lbSecciones);

		initWidget(vpanel);
	}

	@Override
	public String[][] getSecciones() {
		int total = 0;
		for(int i=0; i < lbSecciones.getItemCount(); i++) {
			if(lbSecciones.isItemSelected(i)) {
				total++;
			}
		}
		if(total == 0)
			return null;
		String[][] output = new String[total][2];
		int current = 0;
		for(int i=0; i < lbSecciones.getItemCount(); i++) {
			if(lbSecciones.isItemSelected(i)) {
				output[current][0] = lbSecciones.getItemText(i);
				output[current][1] = lbSecciones.getValue(i);
				current++;
			}
		}
		return output;
	}
}
