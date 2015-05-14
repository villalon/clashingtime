package cl.uai.webcursos.client.selectores.controles;

import java.util.ArrayList;

import cl.uai.webcursos.client.horario.AbstractAgendaComposite;

import com.google.gwt.user.client.ui.ListBox;

public class ListBoxPersonas extends AbstractAgendaComposite {

	public enum Criterio {
		RUT,
		SECCION,
		CARRERA_COHORTE
	}
	
	private ListBox listBoxPersonas;
	private ArrayList<Criterio> criterios = new ArrayList<Criterio>();
	
	/**
	 * @return the listBoxObjetos
	 */
	public ListBox getListBoxPersonas() {
		return listBoxPersonas;
	}
	
	public ListBoxPersonas() {
		this.listBoxPersonas = new ListBox();
		initWidget(listBoxPersonas);
	}
	
	public void addItem(String item, String value, Criterio criterio) {
		this.criterios.add(criterio);
		this.listBoxPersonas.addItem(item, value);
	}
	
	public void removeItem(int index) {
		this.criterios.remove(index);
		this.listBoxPersonas.removeItem(index);
	}
	
	/**
	 * Obtiene los ruts de las personas seleccionadas en el selector.
	 * @return lista de ruts
	 */
	public String[] getRuts() {
		ArrayList<String> lista = new ArrayList<String>();
		for(int i=0;i<listBoxPersonas.getItemCount();i++) {
			if(criterios.get(i) == Criterio.RUT)
			lista.add(listBoxPersonas.getValue(i));
		}
		if(lista.size()==0)
			return null;
		return lista.toArray(new String[lista.size()]);
	}
	
	public String[] getSecciones() {
		ArrayList<String> lista = new ArrayList<String>();
		for(int i=0;i<listBoxPersonas.getItemCount();i++) {
			if(criterios.get(i) == Criterio.SECCION)
			lista.add(listBoxPersonas.getValue(i));
		}
		if(lista.size()==0)
			return null;
		return lista.toArray(new String[lista.size()]);		
	}
	
	public String[] getCarreras() {
		ArrayList<String> lista = new ArrayList<String>();
		for(int i=0;i<listBoxPersonas.getItemCount();i++) {
			if(criterios.get(i) == Criterio.CARRERA_COHORTE)
			lista.add(listBoxPersonas.getValue(i).split("-")[0]);
		}
		if(lista.size()==0)
			return null;
		return lista.toArray(new String[lista.size()]);				
	}
	
	public String[] getCohortes() {
		ArrayList<String> lista = new ArrayList<String>();
		for(int i=0;i<listBoxPersonas.getItemCount();i++) {
			if(criterios.get(i) == Criterio.CARRERA_COHORTE)
			lista.add(listBoxPersonas.getValue(i).split("-")[1]);
		}
		if(lista.size()==0)
			return null;
		return lista.toArray(new String[lista.size()]);				
	}
}
