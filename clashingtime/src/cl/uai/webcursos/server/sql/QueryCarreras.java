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
public class QueryCarreras extends AbstractQueryAgenda {

	/**
	 * @param conn
	 * @throws AgendaException
	 */
	public QueryCarreras(ClashingTimeImpl conn) throws AgendaException {
		super(conn);
		this.sql = "SELECT distinct " +
		"Carrera " +
		"FROM  [dbo].[MENA_Direcciones_Alumnos_Stgo]" +
		"ORDER BY Carrera";
		this.columns = new String[]{"Carrera"};
	}
}
