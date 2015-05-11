/**
 * 
 */
package cl.uai.webcursos.server.sql;

import cl.uai.webcursos.client.AgendaException;
import cl.uai.webcursos.server.ClashingTimeImpl;

/**
 * @author jorge.villalon
 *
 */
public class QueryHorarioAlumnosRuts extends AbstractQueryHorario {

	private String[] ruts;
	
	public String[] getRuts() {
		return ruts;
	}
	public void setRuts(String[] ruts) {
		this.ruts = ruts;
	}
	/**
	 * @param conn
	 * @throws AgendaException
	 */
	public QueryHorarioAlumnosRuts(ClashingTimeImpl conn) throws AgendaException {
		super(conn);
	}
	
	@Override
	public String[][] executeQuery() throws AgendaException {
		this.sqlInner = "where i.Rut in ( " +
		getStringsList(ruts) +
		"	) ";
		return super.executeQuery();
	}
}
