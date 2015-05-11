/**
 * 
 */
package cl.uai.webcursos.client.selectores.buscadores;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import cl.uai.webcursos.client.event.AsyncCallFinishedEvent;
import cl.uai.webcursos.client.event.AsyncCallHandler;
import cl.uai.webcursos.client.event.AsyncCallStartedEvent;
import cl.uai.webcursos.client.selectores.controles.ListBoxCarreras;
import cl.uai.webcursos.client.selectores.controles.ListBoxPersonas.Criterio;

/**
 * Buscador de alumnos en base a la carrera que siguen y el cohorte
 * @author Jorge Villalon
 *
 */
public class BuscadorAlumnosPorCarrera extends AbstractBuscadorPersonas {

	private ListBoxCarreras lbCarreras;
	private ListBox lbCohortes;
	private Label lblCohortes;

	private VerticalPanel vpanel;

	/**
	 * Constructor del buscador de alumnos por secciones
	 */
	public BuscadorAlumnosPorCarrera() {
		super();
		criterio = Criterio.CARRERA_COHORTE;		

		vpanel = new VerticalPanel();
		lbCarreras = new ListBoxCarreras();
		lbCarreras.addAsyncCallHandler(new AsyncCallHandler() {			
			@Override
			public void onCallStarted(AsyncCallStartedEvent event) {
				handlerManager.fireEvent(new AsyncCallStartedEvent());
			}
			
			@Override
			public void onCallFinished(AsyncCallFinishedEvent event) {
				handlerManager.fireEvent(new AsyncCallFinishedEvent(false));
			}
		});
		
		lblCohortes = new Label("Año de ingreso de los alumnos (cohorte)");

		lbCohortes = new ListBox(true);
		lbCohortes.setVisibleItemCount(5);
		lbCohortes.setEnabled(false);

		// TODO: Cambiar 2012 en duro por fecha
		
		for(int i=2012;i>=1989;i--) {
			lbCohortes.addItem(Integer.toString(i),Integer.toString(i));
		}

		lbCarreras.getListBox().addChangeHandler(new ChangeHandler() {			
			@Override
			public void onChange(ChangeEvent event) {
				if(lbCarreras.getListBox().getSelectedIndex() > 0) {
					lbCohortes.setEnabled(true);
				} else {
					lbCohortes.setEnabled(false);
				}
			}
		});
		
		vpanel.add(lbCarreras);
		vpanel.add(lblCohortes);
		vpanel.add(lbCohortes);

		initWidget(vpanel);
	}
	
	@Override
	public String[][] getPersonas() {
		String carrera_cohorte = lbCarreras.getListBox().getItemText(lbCarreras.getListBox().getSelectedIndex()) + "-" + lbCohortes.getItemText(lbCohortes.getSelectedIndex());
		String[][] output = new String[1][2];
		output[0][0] = carrera_cohorte;
		output[0][1] = carrera_cohorte;
		return output;
	}
}
