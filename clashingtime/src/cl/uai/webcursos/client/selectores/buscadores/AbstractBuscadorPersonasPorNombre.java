/**
 * 
 */
package cl.uai.webcursos.client.selectores.buscadores;

import cl.uai.webcursos.client.event.AsyncCallFinishedEvent;
import cl.uai.webcursos.client.event.AsyncCallStartedEvent;
import cl.uai.webcursos.client.selectores.controles.ListBoxPersonas.Criterio;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Buscador de profesores dado el comienzo de su apellido.
 * @author Jorge Villalon
 *
 */
public abstract class AbstractBuscadorPersonasPorNombre extends AbstractBuscadorPersonas {

	protected VerticalPanel vpanel;
	protected TextBox txtPersonas;
	protected Label lblPersonas;
	protected Button btnBuscar;
	protected ListBox lbProfesores;
	private AsyncCallback<String[][]> async;
	private String nombreBoton;

	/**
	 * @return the nombreBoton
	 */
	public String getNombreBoton() {
		return nombreBoton;
	}

	/**
	 * @param nombreBoton the nombreBoton to set
	 */
	public void setNombreBoton(String nombreBoton) {
		this.nombreBoton = nombreBoton;
	}

	/**
	 * Constructor del buscador de profesores por nombre
	 */
	public AbstractBuscadorPersonasPorNombre() {
		super();
		
		criterio = Criterio.RUT;

		async = new AsyncCallback<String[][]>() {

			@Override
			public void onSuccess(String[][] result) {
				lbProfesores.clear();
				for(String[] s : result) {
					lbProfesores.addItem(s[0], s[1]);
				}
				btnBuscar.setText(getNombreBoton());
				btnBuscar.setEnabled(true);
				handlerManager.fireEvent(new AsyncCallFinishedEvent(false));
			}

			@Override
			public void onFailure(Throwable caught) {
				txtPersonas.setText("Error loading...");
				txtPersonas.setTitle(caught.getMessage());
				txtPersonas.setEnabled(false);
				btnBuscar.setEnabled(false);
				handlerManager.fireEvent(new AsyncCallFinishedEvent(true));
			}
		};
		
		vpanel = new VerticalPanel();
		txtPersonas = new TextBox();
		btnBuscar = new Button("Buscar alumno");
		lbProfesores = new ListBox();
		lbProfesores.setVisibleItemCount(5);

		lblPersonas = new Label("Ingrese las primeras letras del apellido del alumno a buscar.");

		btnBuscar.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				btnBuscar.setText("Loading");
				btnBuscar.setEnabled(false);
				handlerManager.fireEvent(new AsyncCallStartedEvent());
				loadPersonas(txtPersonas.getText(), async);
//				getAgendaService().buscarAlumnos(txtProfesores.getText(), async);
			}
		});

		vpanel.add(lblPersonas);
		HorizontalPanel hpanel = new HorizontalPanel();
		hpanel.add(txtPersonas);
		hpanel.add(btnBuscar);
		vpanel.add(hpanel);
		vpanel.add(lbProfesores);

		initWidget(vpanel);
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
	
	public abstract void loadPersonas(String apellido, AsyncCallback<String[][]> async);
}
