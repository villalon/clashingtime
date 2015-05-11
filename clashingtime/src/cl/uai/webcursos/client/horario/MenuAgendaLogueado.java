/**
 * 
 */
package cl.uai.webcursos.client.horario;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.RootPanel;


/**
 * @author Jorge
 *
 */
public class MenuAgendaLogueado extends MenuAgenda {

	/**
	 * 
	 */
	public MenuAgendaLogueado() {
		super();
		
		MenuItem mi = new MenuItem("Horario", new Command() {
			@Override
			public void execute() {
				RootPanel.get("welcome").setVisible(false);
				RootPanel.get("main").setVisible(true);
				RootPanel.get("ayuda").setVisible(false);
			}
		});
		this.insertItem(mi, 1);
	}
}
