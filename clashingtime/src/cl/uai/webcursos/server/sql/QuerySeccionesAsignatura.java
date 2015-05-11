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
public class QuerySeccionesAsignatura extends AbstractQueryAgenda {

	private String asignatura;
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
	 * @return the asignatura
	 */
	public String getAsignatura() {
		return asignatura;
	}

	/**
	 * @param asignatura the apellido to set
	 */
	public void setAsignatura(String asignatura) {
		this.asignatura = asignatura;
	}

	/**
	 * @param conn
	 * @throws AgendaException
	 */
	public QuerySeccionesAsignatura(ClashingTimeImpl conn) throws AgendaException {
		super(conn);

		columns = new String[]{"Seccion","CodigoOmega"};
	}

	@Override
	public String[][] executeQuery() throws AgendaException {
		this.sql = "SELECT Max(NombreAsignatura) + ' s' + convert(varchar,Max(NumeroSeccion)) + ' ' + Max(ApellidoPaterno) + ', ' + Max(Nombre) as Seccion," +
		"CodigoOmega " +
		"FROM  [dbo].[Mena_Secciones] " +
		"WHERE PeriodoAcademicoId = " + periodoAcademicoId + 
		" and NombreAsignatura like '" + asignatura + "%' " +
		"group by CodigoOmega " +
		"order by Seccion";

		return super.executeQuery();
	}
}
