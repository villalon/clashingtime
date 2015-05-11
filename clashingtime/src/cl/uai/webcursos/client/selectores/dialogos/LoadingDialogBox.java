/**
 * 
 */
package cl.uai.webcursos.client.selectores.dialogos;

import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Image;

/**
 * Caja de diálogo para cuando la aplicación está esperando 
 * respuesta a una llamada asíncrona al servidor.
 * @author Jorge Villalon
 *
 */
public class LoadingDialogBox extends DialogBox {

	/**
	 * 
	 */
	public LoadingDialogBox() {
		super(false, true);
		this.setAnimationEnabled(true);
		this.setGlassEnabled(true);
		Image img = new Image("progress.gif");
		this.setWidget(img);
		this.setText("Cargando...");
	}
}
