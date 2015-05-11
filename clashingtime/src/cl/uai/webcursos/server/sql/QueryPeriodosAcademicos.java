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
public class QueryPeriodosAcademicos extends AbstractQueryAgenda {

	/**
	 * @param conn
	 * @throws AgendaException
	 */
	public QueryPeriodosAcademicos(ClashingTimeImpl conn) throws AgendaException {
		super(conn);
		this.sql = "SELECT [PeriodoAcademicoId],[Nombre] " +
		"FROM  [dbo].[Mena_PeriodosAcademico] " +
		"WHERE Estado=3";
		this.columns = new String[]{"PeriodoAcademicoId","Nombre"};
	}
}
