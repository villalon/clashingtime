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
public class QueryProfesoresApellido extends AbstractQueryAgenda {

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
	public QueryProfesoresApellido(ClashingTimeImpl conn) throws AgendaException {
		super(conn);

		columns = new String[]{"Profesor","Rut"};
	}

	@Override
	public String[][] executeQuery() throws AgendaException {
		this.sql = "select Rut, Max(ApellidoPaterno) + ', ' + Max(s.Nombre) as Profesor " +
		"from Mena_secciones as s " +
		"inner join Mena_PeriodosAcademico as pa on (pa.Estado = 3 and pa.PeriodoAcademicoId = s.PeriodoAcademicoId) " +
		"where Rut is not null and s.ApellidoPaterno like '" + 
		apellido + "%' " +
		"group by Rut";

		return super.executeQuery();
	}
}
