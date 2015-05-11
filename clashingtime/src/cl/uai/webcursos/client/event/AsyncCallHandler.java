/**
 * 
 */
package cl.uai.webcursos.client.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handler del evento llamado asíncrono comenzado {@link AsyncCallStartedEvent}.
 * @author Jorge Villalon
 *
 */
public interface AsyncCallHandler extends EventHandler {

	/**
	 * Ocurre cuando un evento asíncrono (típicamente una llamada AJAX) ha comenzado.
	 * @param event
	 */
	void onCallStarted(AsyncCallStartedEvent event);
	void onCallFinished(AsyncCallFinishedEvent event);
}
