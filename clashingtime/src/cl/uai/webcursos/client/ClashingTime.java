package cl.uai.webcursos.client;

import cl.uai.webcursos.client.event.AsyncCallFinishedEvent;
import cl.uai.webcursos.client.event.AsyncCallHandler;
import cl.uai.webcursos.client.event.AsyncCallStartedEvent;
import cl.uai.webcursos.client.horario.HorarioProfesores;
import cl.uai.webcursos.client.horario.Login;
import cl.uai.webcursos.client.horario.MainHorario;
import cl.uai.webcursos.client.horario.MenuAgenda;
import cl.uai.webcursos.client.horario.MenuAgendaLogueado;
import cl.uai.webcursos.client.selectores.dialogos.ErrorDialogBox;
import cl.uai.webcursos.client.selectores.dialogos.LoadingDialogBox;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Clase principal de acceso de la aplicación GWT. Esta página maneja el
 * tabpanel principal donde se ubican los dos widget principales de horarios
 * para profesores y alimnos.
 * 
 * También se encarga de mostrar diálogos tipo DialogBox al usuario.
 * 
 * @see
 * HorarioProfesores
 * HorarioAlumnos
 * 
 * @author Jorge Villalon
 *
 */
public class ClashingTime implements EntryPoint {

	private LoadingDialogBox dboxWait;
	private ErrorDialogBox dboxError;
	
	/**
	 * Constructor básico
	 */
	public ClashingTime() {
		dboxWait = new LoadingDialogBox();
		dboxError = new ErrorDialogBox();
	}
	
	/**
	 * Método que se ejecuta al inicio, acá se crean los paneles y se agregan al HTML
	 */
	public void onModuleLoad() {
		RootPanel.get("ayuda").setVisible(false);
		RootPanel.get("loading").setVisible(false);
		RootPanel.get("menu").add(new MenuAgenda());
		Login login = new Login();
		login.addAsyncCallHandler(new AsyncCallHandler() {
			
			@Override
			public void onCallStarted(AsyncCallStartedEvent event) {
			}
			
			@Override
			public void onCallFinished(AsyncCallFinishedEvent event) {
				if(!event.isError()) {
					RootPanel.get("main").clear();					
					RootPanel.get("main").add(new MainHorario());
					RootPanel.get("main").setVisible(true);
					RootPanel.get("menu").clear();
					RootPanel.get("menu").add(new MenuAgendaLogueado());
					RootPanel.get("ayuda").setVisible(false);
					RootPanel.get("welcome").setVisible(false);
				}
			}
		});
		RootPanel.get("login").add(login);
	}
	
	/**
	 * Muestra caja de diálogo para que el usuario espere
	 */
	public void showWaitDialog() {
		dboxWait.center();
		dboxWait.show();
	}
	
	/**
	 * Esconde la caja de diálogo de espera
	 */
	public void hideWaitDialog() {
		dboxWait.hide();
	}
	
	/**
	 * Muestra caja de díalogo de error
	 * @param message
	 */
	public void showErrorDialog(String message) {
		dboxError.setMessage(message);
		dboxError.center();
		dboxError.show();
	}
}
