/**
 * 
 */
package cl.uai.webcursos.client.selectores.controles;

import cl.uai.webcursos.client.event.AsyncCallFinishedEvent;
import cl.uai.webcursos.client.horario.AbstractAgendaComposite;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ListBox;

/**
 * Clase abstracta para los ListBox que cargan sus datos automáticamente.
 * @author Jorge Villalon
 *
 */
public abstract class AbstractListBoxAgenda extends AbstractAgendaComposite {
	
	private ListBox listBox;

	public ListBox getListBox() {
		return listBox;
	}

	protected AsyncCallback<String[][]> async = new AsyncCallback<String[][]>() {
		
		@Override
		public void onSuccess(String[][] result) {
			listBox.clear();
			listBox.insertItem(defaultItemText, defaultItemValue, 0);
			for(String[] s : result) {
				if(s.length > 1)
				listBox.insertItem(s[1], s[0], listBox.getItemCount());
				else
				listBox.insertItem(s[0], s[0], listBox.getItemCount());
			}
			listBox.setEnabled(true);
			handlerManager.fireEvent(new AsyncCallFinishedEvent(false));
		}
		
		@Override
		public void onFailure(Throwable caught) {
			listBox.clear();
			listBox.insertItem("Error loading. Try again later.", defaultItemValue, 0);
			listBox.setTitle(caught.getMessage());
			listBox.setEnabled(false);
			handlerManager.fireEvent(new AsyncCallFinishedEvent(true));
		}
	};
	
	private String defaultItemText = "Seleccione";
	private String defaultItemValue = "0";
	
	public String getDefaultItemText() {
		return defaultItemText;
	}

	public void setDefaultItemText(String defaultItemText) {
		this.defaultItemText = defaultItemText;
	}

	public String getDefaultItemValue() {
		return defaultItemValue;
	}

	public void setDefaultItemValue(String defaultItemValue) {
		this.defaultItemValue = defaultItemValue;
	}

	public AbstractListBoxAgenda() {
		this.listBox = new ListBox();
		this.listBox.setVisibleItemCount(1);
		this.listBox.insertItem("Loading...", 0);
		this.listBox.setEnabled(false);
		this.initWidget(listBox);
	}
	
	
	/**
	 * Obtiene el valor seleccionado de la lista
	 * @return el número de id del valor seleccionado
	 */
	public int getSelectedValue() {
		int periodoAcademicoId = Integer.parseInt(this.listBox.getValue(this.listBox.getSelectedIndex()));
		return periodoAcademicoId;
	}
	
	public String getSelectedValueAsString() {
		return this.listBox.getValue(this.listBox.getSelectedIndex());
	}
}
