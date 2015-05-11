/**
 * 
 */
package cl.uai.webcursos.client.horario;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author jorge.villalon
 *
 */
public class MenuAgenda extends MenuBar {

	/**
	 * 
	 */
	public MenuAgenda() {
		this.addItem(new MenuItem("Home", new Command() {			
			@Override
			public void execute() {
				RootPanel.get("welcome").setVisible(true);
				RootPanel.get("main").setVisible(false);
				RootPanel.get("ayuda").setVisible(false);
			}
		}));
		//this.addItem(new MenuItem("Item 2", c));
		this.addItem(new MenuItem("Ayuda", new Command() {
			@Override
			public void execute() {
				RootPanel.get("main").setVisible(false);
				RootPanel.get("welcome").setVisible(false);
				RootPanel.get("ayuda").setVisible(true);
			}
		}));
	}
}
