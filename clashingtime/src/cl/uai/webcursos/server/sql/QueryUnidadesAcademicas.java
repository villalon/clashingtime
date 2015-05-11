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
public class QueryUnidadesAcademicas extends AbstractQueryAgenda {

	/**
	 * @param conn
	 * @throws AgendaException
	 */
	public QueryUnidadesAcademicas(ClashingTimeImpl conn) throws AgendaException {
		super(conn);
		this.sql = "select UnidadAcademica + ' ' + NombrePerdiodo as NombreUnidadAcademica, PeriodoAcademicoId " +
			"from WebCursos_PeriodosAcademicos " +
			"where Estado in (2,3,4) " +
			"order by UnidadAcademica";
		this.columns = new String[]{"PeriodoAcademicoId","NombreUnidadAcademica"};
	}
}
