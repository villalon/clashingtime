/**
 * 
 */
package cl.uai.webcursos.server.sql;

import cl.uai.webcursos.client.AgendaException;
import cl.uai.webcursos.server.ClashingTimeImpl;

/**
 * @author Jorge Villalon
 *
 */
public class QueryActividadesProfesores extends AbstractQueryActividades {

	/**
	 * @param conn
	 * @throws AgendaException
	 */
	public QueryActividadesProfesores(ClashingTimeImpl conn) throws AgendaException {
		super(conn);
	}

	@Override
	public String[][] executeQuery() throws AgendaException {
		this.sqlInner = "where s.Rut in (" + getStringsList(ruts) + ") ";
		return super.executeQuery();
	}
}
