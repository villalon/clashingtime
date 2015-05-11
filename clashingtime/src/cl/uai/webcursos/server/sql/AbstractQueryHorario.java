/**
 * 
 */
package cl.uai.webcursos.server.sql;

import cl.uai.webcursos.client.AgendaException;
import cl.uai.webcursos.server.ClashingTimeImpl;
import cl.uai.webcursos.server.sql.AbstractQueryAgenda;

/**
 * @author jorge.villalon
 *
 */
public abstract class AbstractQueryHorario extends AbstractQueryAgenda {

	protected String sqlPrefix = "select m.Modulo, [1] as Lunes,[2] as Martes,[3] as 'Miércoles',[4] as Jueves,[5] as Viernes " +
	"from ( " +
	"SELECT DiaSemana as Dia, Modulo = case when Modulo like '%A' or Modulo like '%B' then LEFT(Modulo,1) else Modulo end, count(*) as Total " + 
	"FROM [dbo].[Mena_Secciones] as s " + 
	"inner join [dbo].[Mena_PeriodosAcademico] as p on " +
	"	(p.Estado = 3 and s.PeriodoAcademicoId = p.PeriodoAcademicoId) ";
	

	protected String sqlInner = 
		"/*      ALUMNOS POR SECCIONES */ " +

		"/*		ALUMNOS POR CARRERA Y COHORTE */ " +
		"/*		inner join [dbo].[Mena_InscripcionAlumnos] as i on " + 
		"	(i.periodoAcademicoId = s.periodoacademicoid " + 
		"	and s.CodigoOmega = i.CodigoOmega) " + 
		"where i.Rut in ( " +
		"	select distinct Rut from " + 
		"	[dbo].[MENA_Direcciones_Alumnos_Stgo] " +
		"	where Cohorte in (2010) " +
		"	and Carrera in ('INGENIERÍA CIVIL') " +
		")*/ " +
		"/*		PROFESOR POR RUT */ " +
		"/*		where s.Rut in ( " +
		"	'12839858' " +
		"	)*/ " +
		"/*		PROFESOR POR SECCIONES */ " +
		"/*		where s.Rut in ( " +
		"	select distinct Rut " + 
		"	from [dbo].[Mena_Secciones] " +
		"	where CodigoOmega in (15480,15481,15482,15483,15484) " +
		"	and PeriodoAcademicoId in (select PeriodoAcademicoId from [dbo].[Mena_PeriodosAcademico] where Estado = 3) " +
		"	)*/ " +
		"and s.Tipo in ('CÁTEDRA','AYUDANTÍA','LABORATORIO') " +
		"group by DiaSemana, Modulo " + 
		") as DiasModulos " + 
		"PIVOT ( " + 
		"Max(Total) " + 
		"FOR Dia in ([1],[2],[3],[4],[5]) " + 
		") as PivotTable " + 
		"right join " + 
		"(select Modulo from (select 1 as Modulo union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) as n) as m " + 
		"on (m.Modulo = PivotTable.Modulo) " + 
		"order by Modulo";

	protected String sqlSuffix =
		") " +
		"group by DiaSemana, Modulo " + 
		") as DiasModulos " + 
		"PIVOT ( " + 
		"Max(Total) " + 
		"FOR Dia in ([1],[2],[3],[4],[5]) " + 
		") as PivotTable " + 
		"right join " + 
		"(select Modulo from (select 1 as Modulo union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) as n) as m " + 
		"on (m.Modulo = PivotTable.Modulo) " + 
		"order by Modulo";
	
	private String sqlTipos =
		"and s.Tipo in (";
	
	private String[] tipos;
	
	public String[] getTipos() {
		return tipos;
	}

	public void setTipos(String[] tipos) {
		this.tipos = tipos;
	}

	/**
	 * @param servlet
	 * @throws AgendaException
	 */
	public AbstractQueryHorario(ClashingTimeImpl servlet) throws AgendaException {
		super(servlet);
		this.columns = new String[]{"Modulo","Lunes","Martes","Miércoles","Jueves","Viernes"};
	}
	
	@Override
	public String[][] executeQuery() throws AgendaException {
		this.sql = sqlPrefix + sqlInner + sqlTipos + getStringsList(tipos) + sqlSuffix;
		return super.executeQuery();
	}
}
