/**
 * 
 */
package cl.uai.webcursos.client.selectores;

import cl.uai.webcursos.client.event.AsyncCallFinishedEvent;
import cl.uai.webcursos.client.event.AsyncCallHandler;
import cl.uai.webcursos.client.event.AsyncCallStartedEvent;
import cl.uai.webcursos.client.horario.AbstractAgendaComposite;
import cl.uai.webcursos.client.selectores.buscadores.AbstractBuscadorPersonas;
import cl.uai.webcursos.client.selectores.controles.ListBoxPersonas;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Clase abstracta para selectores de personas (alumnos o profesores).
 * Contiene criterios de búsqueda y botones para agregar y quitar personas
 * desde un ListBox con los seleccionados.
 * @author Jorge Villalon
 *
 */
public abstract class AbstractSelectorPersonas extends AbstractAgendaComposite {

	private VerticalPanel vpanel;

	private ListBoxPersonas lbPersonasDisponibles;
	/**
	 * @return the lbPersonasDisponibles
	 */
	public ListBoxPersonas getListBoxPersonasDisponibles() {
		return lbPersonasDisponibles;
	}

	private Button btnBorrarPersonas;
	private Button btnAgregarPersonas;
	private TabPanel tpanelCriterios;
	protected Label lblSeleccionados;

	public boolean isEmpty() {
		return lbPersonasDisponibles.getListBoxPersonas().getItemCount() < 1;
	}
	
	public AbstractSelectorPersonas() {
		vpanel = new VerticalPanel();
		vpanel.setStylePrimaryName("tbSelector");
		tpanelCriterios = new TabPanel();
		tpanelCriterios.setStylePrimaryName("tabAgenda");
		tpanelCriterios.getTabBar().setStylePrimaryName("tabBarAgenda");

		lblSeleccionados = new Label();
		
		lbPersonasDisponibles = new ListBoxPersonas();
		lbPersonasDisponibles.getListBoxPersonas().setVisibleItemCount(5);
		lbPersonasDisponibles.getListBoxPersonas().setEnabled(false);

		btnBorrarPersonas = new Button("Deseleccionar /\\");
		btnBorrarPersonas.setEnabled(false);

		btnAgregarPersonas = new Button("Seleccionar \\/");
		btnAgregarPersonas.setEnabled(false);

		btnBorrarPersonas.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean removed = false;
				for(int i=lbPersonasDisponibles.getListBoxPersonas().getItemCount()-1; i>=0; i--) {
					if(lbPersonasDisponibles.getListBoxPersonas().isItemSelected(i)) {
						lbPersonasDisponibles.removeItem(i);
						removed = true;
					}
				}
				if(removed)
					handlerManager.fireEvent(new AsyncCallFinishedEvent(false));
			}
		});
		
		btnAgregarPersonas.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				AbstractBuscadorPersonas buscador = (AbstractBuscadorPersonas) tpanelCriterios.getWidget(tpanelCriterios.getTabBar().getSelectedTab());
				boolean added = false;
				if(buscador != null && buscador.getPersonas() != null)
					for(String[] s : buscador.getPersonas()) {
						lbPersonasDisponibles.addItem(s[0], s[1], buscador.getCriterio());
						added = true;
					}
				if(buscador != null && buscador.getSecciones() != null)
					for(String[] s : buscador.getSecciones()) {
						lbPersonasDisponibles.addItem(s[0], s[1], buscador.getCriterio());
						added = true;
					}
				if(added)
					handlerManager.fireEvent(new AsyncCallFinishedEvent(false));
				else {
					DialogBox dbox = new DialogBox(true, true);
					dbox.setHTML("<b>No hay criterio seleccionado</b>");
					dbox.setWidget(new Label("Debe seleccionar al menos un criterio de búsqueda de alumnos o profesores en la caja superior."));
					dbox.showRelativeTo(btnAgregarPersonas);
				}
			}
		});

		vpanel.add(tpanelCriterios);
		HorizontalPanel hpanel = new HorizontalPanel();
		hpanel.add(btnAgregarPersonas);
		hpanel.add(btnBorrarPersonas);
		vpanel.add(hpanel);
		vpanel.add(lblSeleccionados);
		vpanel.add(lbPersonasDisponibles);

		this.initWidget(vpanel);
	}

	public void addCriterio(AbstractAgendaComposite criterio, String titulo) {
		criterio.addAsyncCallHandler(new AsyncCallHandler() {

			@Override
			public void onCallStarted(AsyncCallStartedEvent event) {
				btnAgregarPersonas.setEnabled(false);
				btnBorrarPersonas.setEnabled(false);
				lbPersonasDisponibles.getListBoxPersonas().setEnabled(false);
			//	handlerManager.fireEvent(event);
			}

			@Override
			public void onCallFinished(AsyncCallFinishedEvent event) {
				if(event.isError()) {
					btnAgregarPersonas.setEnabled(false);
					btnBorrarPersonas.setEnabled(false);
					lbPersonasDisponibles.getListBoxPersonas().setEnabled(false);
				} else {
					btnAgregarPersonas.setEnabled(true);
					btnBorrarPersonas.setEnabled(true);
					lbPersonasDisponibles.getListBoxPersonas().setEnabled(true);
				}
				//handlerManager.fireEvent(event);
			}
		});

		tpanelCriterios.add(criterio, titulo);
		tpanelCriterios.selectTab(0);
	}
}
