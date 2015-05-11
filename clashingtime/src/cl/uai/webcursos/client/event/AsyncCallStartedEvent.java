/**
 * 
 */
package cl.uai.webcursos.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Evento que indica que una llamada asíncrona ha sido invocada
 * @author Jorge Villalon
 *
 */
public class AsyncCallStartedEvent extends GwtEvent<AsyncCallHandler> {
	
	private static final Type<AsyncCallHandler> TYPE = new Type<AsyncCallHandler>();
	public static Type<AsyncCallHandler> getType() {
		return TYPE;
	}
	
	@Override
	protected void dispatch(AsyncCallHandler handler) {
		handler.onCallStarted(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<AsyncCallHandler> getAssociatedType() {
		return TYPE;
	}
}

