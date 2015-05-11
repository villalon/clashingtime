/**
 * 
 */
package cl.uai.webcursos.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Evento que ocurre cuando una llamad asíncrona ha finalizado.
 * @author Jorge Villalon
 *
 */
public class AsyncCallFinishedEvent extends GwtEvent<AsyncCallHandler> {
	
	private boolean error = false;
	
	public boolean isError() {
		return error;
	}

	public AsyncCallFinishedEvent(boolean error) {
		this.error = error;
	}
	
	private static final Type<AsyncCallHandler> TYPE = new Type<AsyncCallHandler>();
	public static Type<AsyncCallHandler> getType() {
		return TYPE;
	}
	
	@Override
	protected void dispatch(AsyncCallHandler handler) {
		handler.onCallFinished(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<AsyncCallHandler> getAssociatedType() {
		return TYPE;
	}
}

