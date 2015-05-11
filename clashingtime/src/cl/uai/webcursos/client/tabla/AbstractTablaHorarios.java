/**
 * 
 */
package cl.uai.webcursos.client.tabla;

import cl.uai.webcursos.client.event.AsyncCallFinishedEvent;
import cl.uai.webcursos.client.event.AsyncCallStartedEvent;
import cl.uai.webcursos.client.horario.AbstractAgendaComposite;
import cl.uai.webcursos.client.selectores.controles.ListBoxPersonas;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Tabla con los horarios de un grupo de personas, que pueden ser
 * profesores o alumnos.
 * @author Jorge Villalon
 *
 */
public abstract class AbstractTablaHorarios extends AbstractAgendaComposite {

	private final static String[] dias = {"Lunes","Martes","Miércoles","Jueves","Viernes"};
	private final static String[] modulos = {"8:00","9:30","11:00","12:30","14:15","15:45","17:15","18:45","19:15","20:45"};
	private ListBoxPersonas ruts = null;
	private String[] tipoActividad = {"CÁTEDRA","LABORATORIO","AYUDANTÍA"};
	private VerticalPanel vpanel;
	private HorizontalPanel hpanel;
	private Label lblLoading;

	private Grid tablaHorarios;

	public AbstractTablaHorarios() {
		vpanel = new VerticalPanel();

		lblLoading = new Label("Loading...");
		lblLoading.setVisible(false);

		tablaHorarios = new Grid(10, 6);
		tablaHorarios.setVisible(false);
		tablaHorarios.setStylePrimaryName("gridHorario");
		Label lbl = new Label("Módulo");
		tablaHorarios.setWidget(0, 0, lbl);
		tablaHorarios.getRowFormatter().setStyleName(0, "horarioHeader");
		for(int i=0;i<5;i++) {
			lbl = new Label(dias[i]);
			tablaHorarios.setWidget(0, i+1, lbl);
		}
		

		vpanel.add(lblLoading);
		vpanel.add(tablaHorarios);

		hpanel = new HorizontalPanel();
		hpanel.setStylePrimaryName("panelEjemplos");
		hpanel.setVisible(false);
		CeldaTablaHorario wh = new CeldaTablaHorario("", 0, 0, true);
		wh.setStylePrimaryName("ejemploCeldaDisponible");
		hpanel.add(wh);
		lbl = new Label("= libre"); 
		hpanel.add(lbl);
		hpanel.setCellVerticalAlignment(lbl, HasVerticalAlignment.ALIGN_MIDDLE);
		wh = new CeldaTablaHorario("N", 0, 0, false);
		wh.setStylePrimaryName("ejemploCeldaNoDisponible");
		hpanel.add(wh);
		hpanel.setCellVerticalAlignment(wh, HasVerticalAlignment.ALIGN_MIDDLE);
		lbl = new Label("= ocupado (N personas con actividades)"); 
		hpanel.add(lbl);
		hpanel.setCellVerticalAlignment(lbl, HasVerticalAlignment.ALIGN_MIDDLE);
		vpanel.add(hpanel);

		this.initWidget(vpanel);
	}


	private String getCeldaHorarioStyle(int modulo, int dia, String value, int max) {
		if(dia == 0)
			return "horarioModulo";
		else if(value == null)
			return "horarioDisponible";
		else
			return "horarioNoDisponible";
	}

	private Widget getCeldaHorario(int modulo, int dia, String value, int max) {
		if(dia == 0) {
			Label lbl = new Label(getTimeForModule(value));
			lbl.setTitle("Módulo " + value);
			return lbl;
		}
		else if(value == null) {
			CeldaTablaHorario lbl = new CeldaTablaHorario(" ", modulo, dia, true);
			return lbl;
		}
		else {
			CeldaTablaHorario out = new CeldaTablaHorario(value, modulo, dia, false);
			out.addClickHandler(new ClickHandler() {				
				@Override
				public void onClick(ClickEvent event) {
					final CeldaTablaHorario img = (CeldaTablaHorario) event.getSource();
					final DialogBox box = new DialogBox(true, false);
					box.setText("Actividades del día " + dias[img.getDia()-1] + " modulo " + img.getModulo());
					box.setWidget(new Label("Loading..."));
					box.showRelativeTo(img);
					AsyncCallback<String[][]> async = new AsyncCallback<String[][]>() {
						@Override
						public void onFailure(Throwable caught) {
						}
						@Override
						public void onSuccess(String[][] result) {
							Grid gr = new Grid(1, 2);
							gr.setStylePrimaryName("gridActividades");
							gr.setText(0, 0, "Actividad");
							gr.setText(0, 1, "Personas");
							for(String[] s : result) {
								gr.resize(gr.getRowCount()+1, 2);
								gr.setText(gr.getRowCount()-1, 0, s[0]);
								gr.setText(gr.getRowCount()-1, 1, s[1]);
							}
							box.setWidget(gr);
						}
					};
					loadActividadesCelda(
							ruts, 
							img.getDia(), 
							img.getModulo(), async);
				}
			});
			return out;
		}
	}

	public abstract void loadActividadesCelda(ListBoxPersonas ruts, int dia, int modulo, AsyncCallback<String[][]> async);
	public abstract void loadHorario(ListBoxPersonas ruts, String[] tipoActividad, AsyncCallback<String[][]> async);

	private int getMax(String[] s) {
		int max = 0;
		for(int i=1;i<=5;i++) {
			if(s[i] != null) {
				int value = Integer.parseInt(s[i]);
				if(max < value)
					max = value;
			}
		}
		return max;
	}
	public ListBoxPersonas getRuts() {
		return ruts;
	}

	private String getTimeForModule(String module) {
		int modulo = Integer.parseInt(module);
		return modulos[modulo-1];
	}


	public String[] getTipoActividad() {
		return tipoActividad;
	}

	public void loadHorario() {
		if(ruts == null || ruts.getListBoxPersonas().getItemCount() < 1)
			return;
		tablaHorarios.setVisible(false);
		hpanel.setVisible(false);
		lblLoading.setVisible(true);

		handlerManager.fireEvent(new AsyncCallStartedEvent());
		AsyncCallback<String[][]> async = new AsyncCallback<String[][]>() {
			@Override
			public void onFailure(Throwable caught) {
				tablaHorarios.setVisible(false);
				hpanel.setVisible(false);
				lblLoading.setVisible(true);
				lblLoading.setText("Error cargando horario");
				lblLoading.setTitle(caught.getMessage());
				handlerManager.fireEvent(new AsyncCallFinishedEvent(true));
			}
			@Override
			public void onSuccess(String[][] result) {
				int max = 0;
				for(String[] s : result) {
					if(max < getMax(s))
						max = getMax(s);
				}
				int row = 1;
				for(String[] s : result) {
					for(int i=0;i<=5;i++) {
						tablaHorarios.setWidget(row, i, getCeldaHorario(row, i, s[i], max));
						tablaHorarios.getCellFormatter().removeStyleName(row, i, "horarioDisponible");
						tablaHorarios.getCellFormatter().removeStyleName(row, i, "horarioNoDisponible");
						tablaHorarios.getCellFormatter().setStyleName(row, i, getCeldaHorarioStyle(row, i, s[i], max));
					}
					row++;
				}
				tablaHorarios.setVisible(true);
				hpanel.setVisible(true);
				lblLoading.setVisible(false);
				handlerManager.fireEvent(new AsyncCallFinishedEvent(false));
			}
		};		
		loadHorario(
				ruts, 
				tipoActividad, 
				async);
	}

	public void setRuts(ListBoxPersonas ruts) {
		this.ruts = ruts;
	}

	public void setTipoActividad(String[] tipoActividad) {
		this.tipoActividad = tipoActividad;
	}

	//	private String getSeccionesCSV() {
	//		String seccionesId = "";
	//		int total = 0;
	//		for(int i=0; i < lbSecciones.getItemCount(); i++) {
	//			if(lbSecciones.isItemSelected(i)) {
	//				seccionesId += lbSecciones.getValue(i) + ",";
	//				total++;
	//			}
	//		}
	//		if(total == 0)
	//			return null;
	//		return seccionesId.substring(0, seccionesId.length()-1);		
	//	}	
}
