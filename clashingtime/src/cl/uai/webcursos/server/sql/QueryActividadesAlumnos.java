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
public class QueryActividadesAlumnos extends AbstractQueryActividades {

	/**
	 * @param servlet
	 * @throws AgendaException
	 */
	public QueryActividadesAlumnos(ClashingTimeImpl servlet) throws AgendaException {
		super(servlet);
	}

	@Override
	public String[][] executeQuery() throws AgendaException {
		this.sqlInner = "where i.Rut in (" + getStringsList(ruts) + ") ";
		return super.executeQuery();
	}
}
