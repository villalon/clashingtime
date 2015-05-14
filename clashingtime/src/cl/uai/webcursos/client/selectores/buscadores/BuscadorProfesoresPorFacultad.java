/**
 * 
 */
package cl.uai.webcursos.client.selectores.buscadores;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import cl.uai.webcursos.client.event.AsyncCallFinishedEvent;
import cl.uai.webcursos.client.event.AsyncCallStartedEvent;
import cl.uai.webcursos.client.selectores.controles.ListBoxFacultades;
import cl.uai.webcursos.client.selectores.controles.ListBoxPersonas.Criterio;

/**
 * Buscador de profesores que pertenecen a una facultad
 * @author Jorge Villalon
 *
 */
public class BuscadorProfesoresPorFacultad extends AbstractBuscadorPersonas {
	
	private VerticalPanel vpanel;
	private ListBoxFacultades lbFacultades;
	private ListBox lbProfesores;
	private Button btnSeleccionarTodos;
	
	/**
	 * Constructor del buscador de profesores por facultad
	 */
	public BuscadorProfesoresPorFacultad() {
		super();

		criterio = Criterio.RUT;

		vpanel = new VerticalPanel();
		
		lbFacultades = new ListBoxFacultades();
		HorizontalPanel hpanelUA = new HorizontalPanel();
		hpanelUA.add(lbFacultades);
		
		lbProfesores = new ListBox();
		lbProfesores.setVisibleItemCount(5);
		btnSeleccionarTodos = new Button("Seleccionar todos");
		btnSeleccionarTodos.setEnabled(false);

		lbFacultades.getListBox().addChangeHandler(new ChangeHandler() {			
			@Override
			public void onChange(ChangeEvent event) {
				loadProfesores();
			}
		});
		
		btnSeleccionarTodos.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				for(int i = 0; i < lbProfesores.getItemCount(); i++) {
					if(!lbProfesores.isItemSelected(i)) {
						lbProfesores.setItemSelected(i, true);
					}
				}
			}
		});
		
		vpanel.add(hpanelUA);
		vpanel.add(btnSeleccionarTodos);
		vpanel.add(lbProfesores);

		vpanel.setCellHorizontalAlignment(btnSeleccionarTodos, HasHorizontalAlignment.ALIGN_RIGHT);
		initWidget(vpanel);
	}
	
	private void loadProfesores() {
		if(lbFacultades.getListBox().getSelectedIndex()==0)
			return;
		lbFacultades.getListBox().setEnabled(false);
		btnSeleccionarTodos.setEnabled(false);
		lbProfesores.clear();
		handlerManager.fireEvent(new AsyncCallStartedEvent());
		String facultadId = lbFacultades.getSelectedValueAsString();
		getAgendaService().getProfesoresFacultad(facultadId, new AsyncCallback<String[][]>() {
			
			@Override
			public void onSuccess(String[][] result) {
				setPersonas(result);
				for(String[] s : result) {
					lbProfesores.addItem(s[0],s[1]);
				}
				lbFacultades.getListBox().setEnabled(true);
				btnSeleccionarTodos.setEnabled(true);
				handlerManager.fireEvent(new AsyncCallFinishedEvent(false));
			}
			
			@Override
			public void onFailure(Throwable caught) {
				lbFacultades.getListBox().setEnabled(true);
				handlerManager.fireEvent(new AsyncCallFinishedEvent(true));
			}
		});
		
	}
	
	@Override
	public String[][] getPersonas() {
		int total = 0;
		for(int i=0; i < lbProfesores.getItemCount(); i++) {
			if(lbProfesores.isItemSelected(i)) {
				total++;
			}
		}
		if(total == 0)
			return null;
		String[][] output = new String[total][2];
		int current = 0;
		for(int i=0; i < lbProfesores.getItemCount(); i++) {
			if(lbProfesores.isItemSelected(i)) {
				output[current][0] = lbProfesores.getItemText(i);
				output[current][1] = lbProfesores.getValue(i);
				current++;
			}
		}
		return output;
	}
}
