/**
 * 
 */
package cl.uai.webcursos.client.selectores.controles;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import cl.uai.webcursos.client.event.AsyncCallFinishedEvent;
import cl.uai.webcursos.client.event.AsyncCallHandler;
import cl.uai.webcursos.client.event.AsyncCallStartedEvent;
import cl.uai.webcursos.client.horario.AbstractAgendaComposite;

/**
 * SuggestBox para asignaturas que se dictan en la UAI. Contiene
 * un {@link ListBoxUnidadesAcademicas} para seleccionar primero
 * la unidad académica, cuya lista de asignaturas se agregarán a
 * las sugerencias de texto.
 * 
 * @author Jorge Villalon
 *
 */
public class SuggestBoxAsignaturas extends AbstractAgendaComposite {

	private VerticalPanel vpanel;
	private ListBoxUnidadesAcademicas listBoxUnidadesAcademicas;
	private Label lblAsignaturas;
	private SuggestBox suggestBox;
	private MultiWordSuggestOracle suggestOracleAsignaturas;
	
	public SuggestBoxAsignaturas() {
		vpanel = new VerticalPanel();
		
		listBoxUnidadesAcademicas = new ListBoxUnidadesAcademicas();
		
		lblAsignaturas = new Label("Nombre de la asignatura.");
		suggestOracleAsignaturas = new MultiWordSuggestOracle();
		suggestBox = new SuggestBox(suggestOracleAsignaturas);
		suggestBox.getValueBox().setEnabled(false);

		listBoxUnidadesAcademicas.getListBox().addChangeHandler(new ChangeHandler() {			
			@Override
			public void onChange(ChangeEvent event) {
				loadAsignaturas();
			}
		});
		
		listBoxUnidadesAcademicas.addAsyncCallHandler(new AsyncCallHandler() {			
			@Override
			public void onCallStarted(AsyncCallStartedEvent event) {
				suggestBox.setText("Loading");
				suggestBox.getValueBox().setEnabled(false);
				handlerManager.fireEvent(event);
			}

			@Override
			public void onCallFinished(AsyncCallFinishedEvent event) {
				if(event.isError()) {
					suggestBox.setText("Error!");
					suggestBox.getValueBox().setEnabled(false);
				} else {
					suggestBox.setText("");
					suggestBox.getValueBox().setEnabled(true);
				}
				handlerManager.fireEvent(event);
			}
		});
		
		suggestBox.getValueBox().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				suggestBox.getValueBox().selectAll();
			}
		});
		
		vpanel.add(listBoxUnidadesAcademicas);
		vpanel.add(lblAsignaturas);
		vpanel.add(suggestBox);
		
		this.initWidget(vpanel);		
	}

	public ListBoxUnidadesAcademicas getListBoxUnidadesAcademicas() {
		return listBoxUnidadesAcademicas;
	}
	
	public SuggestBox getSuggestBox() {
		return suggestBox;
	}
	
	private void loadAsignaturas() {
		if(listBoxUnidadesAcademicas.getListBox().getSelectedIndex()==0)
			return;
		listBoxUnidadesAcademicas.getListBox().setEnabled(false);
		suggestBox.getValueBox().setEnabled(false);
		suggestBox.getValueBox().setText("Loading...");
		suggestOracleAsignaturas.clear();
		handlerManager.fireEvent(new AsyncCallStartedEvent());
		int periodoAcademico = listBoxUnidadesAcademicas.getSelectedValue();
		getAgendaService().getAsignaturasPeriodo(periodoAcademico, new AsyncCallback<String[][]>() {
			
			@Override
			public void onFailure(Throwable caught) {
				listBoxUnidadesAcademicas.getListBox().setEnabled(true);
				suggestBox.getValueBox().setEnabled(false);
				suggestBox.getValueBox().setText("Error!");
				suggestBox.setTitle(caught.getMessage());
				handlerManager.fireEvent(new AsyncCallFinishedEvent(true));
			}
			
			@Override
			public void onSuccess(String[][] result) {
				for(String[] s : result) {
					suggestOracleAsignaturas.add(s[0]);
				}
				listBoxUnidadesAcademicas.getListBox().setEnabled(true);
				suggestBox.getValueBox().setEnabled(true);
				suggestBox.getValueBox().setText("");
				handlerManager.fireEvent(new AsyncCallFinishedEvent(false));
			}
		});
		
	}

}
