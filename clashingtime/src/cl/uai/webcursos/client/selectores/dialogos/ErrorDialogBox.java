/**
 * 
 */
package cl.uai.webcursos.client.selectores.dialogos;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Cuadro de diálogo que muestra un error.
 * @author Jorge Villalon
 *
 */
public class ErrorDialogBox extends DialogBox {

	private VerticalPanel vpanel;
	private Label lblMessage;
	/**
	 * 
	 */
	public ErrorDialogBox() {
		super(true, true);
		this.setAnimationEnabled(true);
		this.setGlassEnabled(true);
		this.setText("Error");
		
		vpanel = new VerticalPanel();
		
		lblMessage = new Label("Error");
		vpanel.add(lblMessage);
		
		Button btnAceptar = new Button("Aceptar");
		btnAceptar.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		
		vpanel.add(btnAceptar);
		vpanel.setCellHorizontalAlignment(btnAceptar, HasHorizontalAlignment.ALIGN_CENTER);
		
		this.setWidget(vpanel);
	}
	
	/**
	 * Cambia el mensaje de error de la caja de texto.
	 * @param message el mensaje nuevo.
	 */
	public void setMessage(String message) {
		this.lblMessage.setText(message);
	}
}
