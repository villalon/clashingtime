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
public class QueryProfesoresPeriodo extends AbstractQueryAgenda {

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
	public QueryProfesoresPeriodo(ClashingTimeImpl conn) throws AgendaException {
		super(conn);

		columns = new String[]{"Profesor","Rut"};
	}

	@Override
	public String[][] executeQuery() throws AgendaException {
		this.sql = "select Rut, Max(ApellidoPaterno) + ', ' + Max(Nombre) as Profesor " +
		"from Mena_secciones " + 
		"where PeriodoAcademicoId = " + periodoAcademicoId +
		"and Rut is not null " +
"group by Rut";

		return super.executeQuery();
	}
}
