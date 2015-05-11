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
public class QueryAlumnosCarreraCohorte extends AbstractQueryAgenda {

	private String carrera;
	/**
	 * @return the carrera
	 */
	public String getCarrera() {
		return carrera;
	}

	/**
	 * @param carrera the carrera to set
	 */
	public void setCarrera(String carrera) {
		this.carrera = carrera;
	}

	/**
	 * @return the cohorte
	 */
	public int getCohorte() {
		return cohorte;
	}

	/**
	 * @param cohorte the cohorte to set
	 */
	public void setCohorte(int cohorte) {
		this.cohorte = cohorte;
	}

	private int cohorte;

	/**
	 * @param conn
	 * @throws AgendaException
	 */
	public QueryAlumnosCarreraCohorte(ClashingTimeImpl conn) throws AgendaException {
		super(conn);
	}

	@Override
	public String[][] executeQuery() throws AgendaException {
		this.sql = "SELECT " +
		" Rut, Max(ApellidoPaterno) + ' ' + Max(ApellidoMaterno) + ', ' + Max(Nombre) as Alumno " +
		"FROM [dbo].[MENA_Direcciones_Alumnos_Stgo] " +
		"WHERE Carrera = '" + carrera + "' " +
		"and Cohorte = " + cohorte +
		" group by Rut";
		this.columns = new String[]{"Alumno","Rut"};
		return super.executeQuery();
	}
}
