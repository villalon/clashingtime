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
public class QueryHorarioAlumnosSecciones extends AbstractQueryHorario {

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
	public QueryHorarioAlumnosSecciones(ClashingTimeImpl conn) throws AgendaException {
		super(conn);
	}
	
	@Override
	public String[][] executeQuery() throws AgendaException {
		this.sqlInner = " inner join  [dbo].[Mena_InscripcionAlumnos] as i on " + 
		"	(i.periodoAcademicoId = s.periodoacademicoid " + 
		"	and s.CodigoOmega = i.CodigoOmega) " + 
		"where i.Rut in ( " +
		"	select distinct Rut from " + 
		"	 [dbo].[Mena_InscripcionAlumnos] " +
		"	where CodigoOmega in (" + getStringsList(ruts) + ") " +
		"	and PeriodoAcademicoId in (select PeriodoAcademicoId from  [dbo].[Mena_PeriodosAcademico] where Estado = 3) " +
		") ";
		return super.executeQuery();
	}
}
