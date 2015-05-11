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
public class AbstractQueryActividades extends AbstractQueryAgenda {

	protected String[] ruts = null;
	/**
	 * @return the ruts
	 */
	public String[] getRuts() {
		return ruts;
	}

	/**
	 * @param ruts the ruts to set
	 */
	public void setRuts(String[] ruts) {
		this.ruts = ruts;
	}

	/**
	 * @return the dia
	 */
	public int getDia() {
		return dia;
	}

	/**
	 * @param dia the dia to set
	 */
	public void setDia(int dia) {
		this.dia = dia;
	}

	/**
	 * @return the modulo
	 */
	public int getModulo() {
		return modulo;
	}

	/**
	 * @param modulo the modulo to set
	 */
	public void setModulo(int modulo) {
		this.modulo = modulo;
	}

	private int dia = 1;
	private int modulo = 1;
	protected String sqlInner;
	
	/**
	 * @param servlet
	 * @throws AgendaException
	 */
	public AbstractQueryActividades(ClashingTimeImpl servlet) throws AgendaException {
		super(servlet);
		this.columns = new String[]{"Seccion","Total"};
	}

	@Override
	public String[][] executeQuery() throws AgendaException {
		this.sql = "SELECT Seccion, count(*) as Total FROM (" +
		"SELECT s.NombreAsignatura + ' s' + convert(varchar,s.NumeroSeccion) + ' ' + s.ApellidoPaterno + ', ' + s.Nombre + ' ' + s.Tipo as Seccion " +
		"FROM [dbo].[Mena_InscripcionAlumnos] as i " +
		"inner join [dbo].[Mena_Secciones] as s on (i.periodoAcademicoId = s.periodoacademicoid and s.CodigoOmega = i.CodigoOmega) " +
//		"where i.Rut in (" + getStringsList(ruts) + ") " +
		this.sqlInner +
		" and DiaSemana = " + dia + " and Modulo like '" + modulo + "%' " +
		"and s.PeriodoAcademicoId in (select PeriodoAcademicoId from [dbo].[Mena_PeriodosAcademico] where Estado = 3) " +
		") as Actividades " +
		"GROUP BY Seccion";
		return super.executeQuery();
	}
}
