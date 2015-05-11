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
public class QueryProfesoresFacultad extends AbstractQueryAgenda {

	private String facultadId;
	/**
	 * @return the facultadId
	 */
	public String getFacultadId() {
		return facultadId;
	}

	/**
	 * @param facultadId the periodoAcademicoId to set
	 */
	public void setFacultadId(String facultadId) {
		this.facultadId = facultadId;
	}

	/**
	 * @param conn
	 * @throws AgendaException
	 */
	public QueryProfesoresFacultad(ClashingTimeImpl conn) throws AgendaException {
		super(conn);

		columns = new String[]{"Profesor","Rut"};
	}

	@Override
	public String[][] executeQuery() throws AgendaException {
		/*this.sql = "SELECT [Rut Profesor] as Rut, Max([A. Paterno Propfesor]) + ' ' + Max([A. Materno Profesor]) + ', ' + Max([Nombre Profesor]) as Profesor " +
		"FROM  [dbo].[Villalon_NotasAlumnosCiencias] " +
		"WHERE [ID Decanato Profesor] = " + facultadId + " " +
		"GROUP BY [RUT Profesor] " +
		"ORDER BY Profesor";*/
		
		this.sql = "select Rut, max(ApellidoPaterno) + ' ' + max(ApellidoMaterno) + ' ' + MAX(Nombre) as Profesor " +
		"from WebCursos_Secciones as ws " +
		"inner join WebCursos_ProfesoresSeccion as ps on (ws.SeccionId = ps.SeccionId) " +
		"where ws.DecanatoProfesor = '" + facultadId + "' " +
		"group by Rut " +
		"order by Profesor";

		return super.executeQuery();
	}
}
