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
public class QueryActividadesAlumnosCriterios extends AbstractQueryActividades {

	private String[] carreras = null;
	private String[] cohortes = null;
	/**
	 * @return the carreras
	 */
	public String[] getCarreras() {
		return carreras;
	}

	/**
	 * @param carreras the carreras to set
	 */
	public void setCarreras(String[] carreras) {
		this.carreras = carreras;
	}

	/**
	 * @return the cohortes
	 */
	public String[] getCohortes() {
		return cohortes;
	}

	/**
	 * @param cohortes the cohortes to set
	 */
	public void setCohortes(String[] cohortes) {
		this.cohortes = cohortes;
	}

	private String[] ruts = null;
	private String[] secciones = null;

	/**
	 * @return the ruts
	 */
	@Override
	public String[] getRuts() {
		return ruts;
	}

	/**
	 * @param ruts the ruts to set
	 */
	@Override
	public void setRuts(String[] ruts) {
		this.ruts = ruts;
	}

	/**
	 * @return the secciones
	 */
	public String[] getSecciones() {
		return secciones;
	}

	/**
	 * @param secciones the secciones to set
	 */
	public void setSecciones(String[] secciones) {
		this.secciones = secciones;
	}

	/**
	 * @param servlet
	 * @throws AgendaException
	 */
	public QueryActividadesAlumnosCriterios(ClashingTimeImpl servlet) throws AgendaException {
		super(servlet);
	}

	@Override
	public String[][] executeQuery() throws AgendaException {
		this.sqlInner = " where i.Rut in ( ";
		if(secciones != null)
			this.sqlInner += 
			"	select distinct Rut from " + 
			"	[dbo].[Mena_InscripcionAlumnos] " +
			"	where CodigoOmega in (" + getStringsList(secciones) + ") " +
			"	and PeriodoAcademicoId in (select PeriodoAcademicoId from [dbo].[Mena_PeriodosAcademico] where Estado = 3) ";
		if(carreras != null && cohortes != null) {
			if(secciones!= null)
				this.sqlInner += " UNION ";
			this.sqlInner += 
			"SELECT distinct Rut " +
			"FROM [dbo].[MENA_Direcciones_Alumnos_Stgo] " +
			"WHERE Carrera in (" + getStringsList(carreras) + ") " +
			"and Cohorte in (" + getIntList(cohortes) + ") ";
		}
		if(ruts != null) {
			if(secciones != null || carreras != null)
				this.sqlInner += " UNION ";
			for(int i=0; i<ruts.length; i++) {
			this.sqlInner += " SELECT '" + ruts[i] +
			"' ";
			if(i<ruts.length-2)
			this.sqlInner += " UNION ";
			}
		}
		this.sqlInner += " ) ";
		return super.executeQuery();
	}
}
