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
public class QueryHorarioAlumnosCarreraCohorte extends AbstractQueryHorario {

	private String carrera;
	private String cohorte;
	
	/**
	 * @param conn
	 * @throws AgendaException
	 */
	public QueryHorarioAlumnosCarreraCohorte(ClashingTimeImpl conn) throws AgendaException {
		super(conn);
	}
	
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
	public String getCohorte() {
		return cohorte;
	}

	/**
	 * @param cohorte the cohorte to set
	 */
	public void setCohorte(String cohorte) {
		this.cohorte = cohorte;
	}

	@Override
	public String[][] executeQuery() throws AgendaException {
		this.sqlInner = "where i.Rut in ( " +
		"SELECT distinct Rut " +
		"FROM  [dbo].[MENA_Direcciones_Alumnos_Stgo] " +
		"WHERE Carrera = '" + carrera + "' " +
		"and Cohorte = " + cohorte +
		"	) ";
		return super.executeQuery();
	}
}
