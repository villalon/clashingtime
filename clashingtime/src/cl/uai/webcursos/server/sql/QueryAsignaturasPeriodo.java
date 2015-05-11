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
public class QueryAsignaturasPeriodo extends AbstractQueryAgenda {

	private int periodoAcademicoId;
	
	/**
	 * @return the periodoAcademicoId
	 */
	public int getPeriodoAcademicoId() {
		return periodoAcademicoId;
	}

	/**
	 * @param periodoAcademicoId the periodoAcademicoId to set
	 */
	public void setPeriodoAcademicoId(int periodoAcademicoId) {
		this.periodoAcademicoId = periodoAcademicoId;
	}

	/**
	 * @param conn
	 * @throws AgendaException
	 */
	public QueryAsignaturasPeriodo(ClashingTimeImpl conn) throws AgendaException {
		super(conn);
	}

	@Override
	public String[][] executeQuery() throws AgendaException {
		this.sql = "SELECT distinct(NombreAsignatura) as NombreAsignatura " +
		"FROM  [dbo].[Mena_Secciones]" +
		" WHERE PeriodoAcademicoId = " + periodoAcademicoId;
		this.columns = new String[]{"NombreAsignatura"};
		return super.executeQuery();
	}
}
