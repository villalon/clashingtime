/**
 * 
 */
package cl.uai.webcursos.client.horario;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author jorge.villalon
 *
 */
public class MainHorario extends AbstractAgendaComposite {

	private VerticalPanel vpanel;
	private TabPanel tpanel;
	private Label lblMensajeLogin;
	/**
	 * 
	 */
	public MainHorario() {
		vpanel = new VerticalPanel();
		lblMensajeLogin = new Label("El usuario ha sido deslogueado");
		lblMensajeLogin.setVisible(false);
		tpanel = new TabPanel();
		tpanel.add(new HorarioAlumnos(),"Alumnos");
		tpanel.add(new HorarioProfesores(),"Profesores");
		tpanel.selectTab(0);
		
		tpanel.setStylePrimaryName("tabAgenda");
		tpanel.getTabBar().setStylePrimaryName("tabBarAgenda");
		
		vpanel.add(tpanel);
		vpanel.add(lblMensajeLogin);
		initWidget(vpanel);
	}
}
