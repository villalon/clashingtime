/**
 * 
 */
package cl.uai.webcursos.client.horario;

import java.util.ArrayList;

import cl.uai.webcursos.client.event.AsyncCallFinishedEvent;
import cl.uai.webcursos.client.event.AsyncCallHandler;
import cl.uai.webcursos.client.event.AsyncCallStartedEvent;
import cl.uai.webcursos.client.selectores.AbstractSelectorPersonas;
import cl.uai.webcursos.client.tabla.AbstractTablaHorarios;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Esta clase implementa toda la actividad común para los horarios. Contiene un
 * selector de personas (profesores o alumnos en las subclases) {@link AbstractSelectorPersonas}, y un botón
 * para cargar el horario de las personas seleccionadas en una {@link AbstractTablaHorarios}.
 * 
 * @author Jorge Villalon
 *
 */
public abstract class AbstractHorarioPersonas extends AbstractAgendaComposite {

	protected VerticalPanel vpanel;
	protected Label lblTitle;
	protected AbstractSelectorPersonas selectorPersonas;	
	protected AbstractTablaHorarios tablaHorarios;
	protected Button btnCargarHorarios;
	protected CheckBox[] chkTipoAsignatura;
	protected AsyncCallHandler handlerTablaHorarios = new AsyncCallHandler() {
		@Override
		public void onCallStarted(AsyncCallStartedEvent event) {
			tablaHorarios.setVisible(false);
		}		
		@Override
		public void onCallFinished(AsyncCallFinishedEvent event) {
			tablaHorarios.setVisible(true);
			btnCargarHorarios.setEnabled(true);
		}
	};
	
	public AbstractHorarioPersonas(AbstractSelectorPersonas selPersonas) {
		selectorPersonas = selPersonas;
		
		vpanel = new VerticalPanel();
		vpanel.setStylePrimaryName("tbHorario");
		
		lblTitle = new Label();
		lblTitle.setWordWrap(true);
		
		btnCargarHorarios = new Button("Buscar horario");
		btnCargarHorarios.setEnabled(false);
		
		btnCargarHorarios.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				btnCargarHorarios.setEnabled(false);
				tablaHorarios.setVisible(false);
				tablaHorarios.setRuts(selectorPersonas.getListBoxPersonasDisponibles());
				if(getTipoActividades()!=null) {
					tablaHorarios.setTipoActividad(getTipoActividades());
				}
				tablaHorarios.loadHorario();
				tablaHorarios.setVisible(true);
			}
		});
		
		selectorPersonas.addAsyncCallHandler(new AsyncCallHandler() {			
			@Override
			public void onCallStarted(AsyncCallStartedEvent event) {
			}
			@Override
			public void onCallFinished(AsyncCallFinishedEvent event) {
				if(!selectorPersonas.isEmpty()) {
					btnCargarHorarios.setEnabled(true);
				} else {
					btnCargarHorarios.setEnabled(false);
				}
			}
		});
		
		chkTipoAsignatura = new CheckBox[3];
		chkTipoAsignatura[0] = new CheckBox("Cátedra");
		chkTipoAsignatura[1] = new CheckBox("Ayudantía");
		chkTipoAsignatura[2] = new CheckBox("Laboratorio");
		chkTipoAsignatura[0].setValue(true);
		chkTipoAsignatura[1].setValue(false);
		chkTipoAsignatura[2].setValue(false);

		vpanel.add(lblTitle);
		vpanel.add(selectorPersonas);
		HorizontalPanel hpanel = new HorizontalPanel();
		hpanel.add(chkTipoAsignatura[0]);
		hpanel.add(chkTipoAsignatura[1]);
		hpanel.add(chkTipoAsignatura[2]);
		DisclosurePanel dpanel = new DisclosurePanel("Tipo de actividad");
		dpanel.add(hpanel);
		vpanel.add(dpanel);
		vpanel.add(btnCargarHorarios);
		vpanel.setCellVerticalAlignment(lblTitle, HasVerticalAlignment.ALIGN_TOP);
	}	
	
	private String[] getTipoActividades() {
		ArrayList<String> output = new ArrayList<String>();
		if(chkTipoAsignatura[0].getValue()) {
			output.add("CÁTEDRA");
		}
		if(chkTipoAsignatura[1].getValue()) {
			output.add("AYUDANTÍA");
		}
		if(chkTipoAsignatura[2].getValue()) {
			output.add("LABORATORIO");
		}
		if(output.size() == 0)
			return null;
		
		return output.toArray(new String[output.size()]);
	}
}
