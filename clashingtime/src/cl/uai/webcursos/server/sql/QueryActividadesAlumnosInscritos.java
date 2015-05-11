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
public class QueryActividadesAlumnosInscritos extends AbstractQueryActividades {

	/**
	 * @param conn
	 * @throws AgendaException
	 */
	public QueryActividadesAlumnosInscritos(ClashingTimeImpl conn) throws AgendaException {
		super(conn);
	}

	@Override
	public String[][] executeQuery() throws AgendaException {
		this.sqlInner = "where i.Rut in (select distinct Rut from [dbo].[Mena_InscripcionAlumnos] where " +
		"PeriodoAcademicoId in (select PeriodoAcademicoId from  [dbo].[Mena_PeriodosAcademico] where Estado = 3) " +
		"and CodigoOmega in (" + getStringsList(ruts) + ")) ";
		return super.executeQuery();
	}
}
