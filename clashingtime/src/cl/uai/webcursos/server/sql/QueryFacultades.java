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
public class QueryFacultades extends AbstractQueryAgenda {

	/**
	 * @param conn
	 * @throws AgendaException
	 */
	public QueryFacultades(ClashingTimeImpl conn) throws AgendaException {
		super(conn);
		/*this.sql = "SELECT distinct[Decanato Profesor] as FacultadId " +
				",[ID Decanato Profesor] as Facultad " +
		"FROM  [dbo].[Villalon_NotasAlumnosCiencias]" +
		"ORDER BY [Decanato Profesor]";*/
		
		this.sql = "select DecanatoProfesor as Facultad, DecanatoProfesor as FacultadId " +
		"from WebCursos_Secciones " +
		"where DecanatoProfesor is not null " +
		"and LEN(DecanatoProfesor) > 0 " +
		"group by DecanatoProfesor";
		
		this.columns = new String[]{"Facultad","FacultadId"};
	}
}
