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
public class QueryAlumnosApellido extends AbstractQueryAgenda {

	private String apellido;
	/**
	 * @return the apellido
	 */
	public String getApellido() {
		return apellido;
	}

	/**
	 * @param apellido the apellido to set
	 */
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	/**
	 * @param conn
	 * @throws AgendaException
	 */
	public QueryAlumnosApellido(ClashingTimeImpl conn) throws AgendaException {
		super(conn);

		columns = new String[]{"Alumno","Rut"};
	}

	@Override
	public String[][] executeQuery() throws AgendaException {
		this.sql = "SELECT [RUT] as Rut " +
			",[APELLIDO_PATERNO] + ' ' + [APELLIDO_MATERNO] + ', ' + [NOMBRES] as Alumno " +
			"FROM [dbo].[MIRO_Alumnos] " +
			"WHERE [APELLIDO_PATERNO] like '" + this.apellido + "%' " +
			"ORDER BY Alumno";
		return super.executeQuery();
	}
}
