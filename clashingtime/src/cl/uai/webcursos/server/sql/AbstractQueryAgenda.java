/**
 * 
 */
package cl.uai.webcursos.server.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Hashtable;

import cl.uai.webcursos.client.AgendaException;
import cl.uai.webcursos.server.ClashingTimeImpl;

/**
 * Clase abstracta que implementa todas las querys de la agenda
 * @author Jorge Villalon
 *
 */
public abstract class AbstractQueryAgenda {

	protected ClashingTimeImpl agendaServlet;
	protected Hashtable<String, ArrayList<String>> results = new Hashtable<String, ArrayList<String>>();
	protected String sql;
	protected String[] columns;


	/**
	 * 
	 * @param conn
	 * @throws AgendaException
	 */
	public AbstractQueryAgenda(ClashingTimeImpl servlet) throws AgendaException {
		this.agendaServlet = servlet;
		results = new Hashtable<String, ArrayList<String>>();
	}

	public String[][] executeQuery() throws AgendaException {
		if(columns == null)
			throw new AgendaException("Columnas es null");
		try {
			PreparedStatement statement = ClashingTimeImpl.getConnection().prepareStatement(this.sql);
			System.out.println(this.sql);
			ResultSet resultSet = statement.executeQuery();
			for(String column : columns) {
				results.put(column, new ArrayList<String>());
			}
			while(resultSet.next()) {
				for(String column : columns) {
					if(resultSet.getObject(column)!=null)
						results.get(column).add(resultSet.getObject(column).toString());
					else
						results.get(column).add(null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new AgendaException(e.getMessage());
		}
		return getTableFromHash(results, columns);		
	}


	@SuppressWarnings("unchecked")
	protected String[][] getTableFromHash(Hashtable<String, ArrayList<String>> h) throws AgendaException {
		if(h.keySet().size()==0)
			return null;
		int rows = ((ArrayList<String>)h.values().toArray()[0]).size();
		String[][] output = new String[rows][h.keySet().size()];
		for(int i=0;i<rows;i++) {
			int j=0;
			for(String key : h.keySet()) {
				output[i][j] = h.get(key).get(i);
				j++;
			}
		}
		return output;
	}

	@SuppressWarnings("unchecked")
	private String[][] getTableFromHash(Hashtable<String, ArrayList<String>> h, String[] sortedKeys) throws AgendaException {
		if(h.keySet().size()==0)
			return null;
		int rows = ((ArrayList<String>)h.values().toArray()[0]).size();
		String[][] output = new String[rows][h.keySet().size()];
		for(int i=0;i<rows;i++) {
			int j=0;
			for(String key : sortedKeys) {
				output[i][j] = h.get(key).get(i);
				j++;
			}
		}
		return output;
	}

	protected String getStringsList(String[] list) {
		StringBuffer buff = new StringBuffer();
		for(int i=0;i<list.length;i++) {
			buff.append("'");
			buff.append(list[i]);
			if(i<list.length-1)
				buff.append("',");
			else
				buff.append("'");
		}
		return buff.toString();
	}

	protected String getIntList(String[] list) {
		StringBuffer buff = new StringBuffer();
		for(int i=0;i<list.length;i++) {
			buff.append(list[i]);
			if(i<list.length-1)
				buff.append(",");
		}
		return buff.toString();
	}
}
