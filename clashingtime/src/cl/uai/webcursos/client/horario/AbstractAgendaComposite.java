/**
 * 
 */
package cl.uai.webcursos.client.horario;

import cl.uai.webcursos.client.AgendaService;
import cl.uai.webcursos.client.AgendaServiceAsync;
import cl.uai.webcursos.client.event.AsyncCallFinishedEvent;
import cl.uai.webcursos.client.event.AsyncCallHandler;
import cl.uai.webcursos.client.event.AsyncCallStartedEvent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.Composite;

/**
 * Clase base para todos los componentes compuestos de la agenda.
 * @author Jorge Villalon
 *
 */
public abstract class AbstractAgendaComposite extends Composite {
	
	protected final HandlerManager handlerManager = new HandlerManager(this);
	
	private final AgendaServiceAsync agendaService = GWT
			.create(AgendaService.class);

	/**
	 * Obtiene el servicio asíncrono de la agenda
	 * @return instancia del servicio asíncrono
	 */
	public AgendaServiceAsync getAgendaService() {
		return agendaService;
	}


	/**
	 * Agregar un manejador para cuando un llamado asincrónico se inicia
	 * @param handler
	 */
	public void addAsyncCallHandler(AsyncCallHandler handler) {
		handlerManager.addHandler(AsyncCallStartedEvent.getType(), handler);
		handlerManager.addHandler(AsyncCallFinishedEvent.getType(), handler);
	}
	
	public void removeAsyncCallHandler(AsyncCallHandler handler) {
		handlerManager.removeHandler(AsyncCallStartedEvent.getType(), handler);
		handlerManager.removeHandler(AsyncCallFinishedEvent.getType(), handler);
	}

	@Override
	protected void onLoad() {
		super.onLoad();
	}
}
