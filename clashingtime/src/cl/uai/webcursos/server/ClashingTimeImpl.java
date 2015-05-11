package cl.uai.webcursos.server;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cl.uai.webcursos.client.AgendaException;
import cl.uai.webcursos.client.AgendaService;
import cl.uai.webcursos.server.ldap.ActiveDirectoryUAI;
import cl.uai.webcursos.server.sql.QueryActividadesAlumnos;
import cl.uai.webcursos.server.sql.QueryActividadesAlumnosCriterios;
import cl.uai.webcursos.server.sql.QueryActividadesAlumnosInscritos;
import cl.uai.webcursos.server.sql.QueryActividadesProfesores;
import cl.uai.webcursos.server.sql.QueryAlumnosApellido;
import cl.uai.webcursos.server.sql.QueryAlumnosCarreraCohorte;
import cl.uai.webcursos.server.sql.QueryAsignaturasPeriodo;
import cl.uai.webcursos.server.sql.QueryHorarioAlumnosCriterios;
import cl.uai.webcursos.server.sql.QueryProfesoresApellido;
import cl.uai.webcursos.server.sql.QueryCarreras;
import cl.uai.webcursos.server.sql.QueryFacultades;
import cl.uai.webcursos.server.sql.QueryHorarioAlumnosRuts;
import cl.uai.webcursos.server.sql.QueryHorarioAlumnosSecciones;
import cl.uai.webcursos.server.sql.QueryHorarioProfesoresRuts;
import cl.uai.webcursos.server.sql.QueryPeriodosAcademicos;
import cl.uai.webcursos.server.sql.QueryProfesoresAsignatura;
import cl.uai.webcursos.server.sql.QueryProfesoresFacultad;
import cl.uai.webcursos.server.sql.QueryProfesoresPeriodo;
import cl.uai.webcursos.server.sql.QuerySeccionesAsignatura;
import cl.uai.webcursos.server.sql.QueryUnidadesAcademicas;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class ClashingTimeImpl extends RemoteServiceServlet implements
AgendaService {

	private static Connection connection = null;
	private static Logger logger = Logger.getLogger(ClashingTimeImpl.class.getName());
	private static Properties properties = new Properties();
	
	public static Connection getConnection() throws AgendaException {
		try {
			if(connection == null || connection.isClosed()) {
				connection = DriverManager.getConnection(
						properties.getProperty("agenda.sqlConnectionString"), 
						properties.getProperty("agenda.dbUser"), 
						properties.getProperty("agenda.dbPassword"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AgendaException(e.getMessage());
		}
		return connection;
	}

	@Override
	public String autenticaUsuario(String user, String password)
			throws AgendaException {
		String output = ActiveDirectoryUAI.autenticaUsuario(user, password);
		HttpServletRequest request = this.getThreadLocalRequest();
		HttpSession session = request.getSession();
		session.setAttribute("login", user);
		logger.info("Usuario:" + user + " logued in as:" + output);
		return output;
	}

	@Override
	public String[][] buscarAlumnos(String prefix) throws AgendaException {
			QueryAlumnosApellido query = new QueryAlumnosApellido(this);
			query.setApellido(prefix);
			return query.executeQuery();
	}

	@Override
	public String[][] buscarProfesores(String prefix)
	throws AgendaException {
		QueryProfesoresApellido query = new QueryProfesoresApellido(this);
		query.setApellido(prefix);
		return query.executeQuery();
	}

//	@Override
//	public String[][] getAlumnosAsignaturas(int PeriodoAcademicoId,
//			String codigosOmega) throws AgendaException {
//		Hashtable<String, ArrayList<String>> results = new Hashtable<String, ArrayList<String>>();
//		try {
//		validaCodigosOmega(codigosOmega);
//		Statement statement = getConnection().createStatement();
//		String query = "select distinct Rut " +
//		"from  [dbo].[Mena_InscripcionAlumnos] " +
//		"where PeriodoAcademicoId = " + PeriodoAcademicoId + 
//		" and CodigoOmega in (" + codigosOmega + ")";
//		ResultSet rset = statement.executeQuery(query);
//		results.put("Rut", new ArrayList<String>());
//		while(rset.next()) {
//			results.get("Rut").add(rset.getString("Rut"));
//		}
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new AgendaException(e.getMessage());			
//		}
//		
//		return getTableFromHash(results);
//	}

	@Override
	public String[][] getActividadesAlumnos(String[] ruts, int dia, int modulo)
	throws AgendaException {
		QueryActividadesAlumnos query = new QueryActividadesAlumnos(this);
		query.setDia(dia);
		query.setModulo(modulo);
		query.setRuts(ruts);
		return query.executeQuery();
	}

	@Override
	public String[][] getActividadesAlumnosCriterios(String[] secciones,
			String[] ruts, String[] carreras, String[] cohortes,
			int dia, int modulo)
			throws AgendaException {
		QueryActividadesAlumnosCriterios query = new QueryActividadesAlumnosCriterios(this);
		query.setCarreras(carreras);
		query.setCohortes(cohortes);
		query.setRuts(ruts);
		query.setSecciones(secciones);
		query.setDia(dia);
		query.setModulo(modulo);
		return query.executeQuery();
	}

	@Override
	public String[][] getActividadesAlumnosInscritos(String[] codigosOmega,
			int dia, int modulo) throws AgendaException {
		QueryActividadesAlumnosInscritos query = new QueryActividadesAlumnosInscritos(this);
		query.setDia(dia);
		query.setModulo(modulo);
		query.setRuts(codigosOmega);
		return query.executeQuery();
	}

	@Override
	public String[][] getActividadesProfesores(String[] ruts, int dia,
			int modulo) throws AgendaException {
		QueryActividadesProfesores query = new QueryActividadesProfesores(this);
		query.setDia(dia);
		query.setModulo(modulo);
		query.setRuts(ruts);
		return query.executeQuery();
	}

	@Override
	public String[][] getAlumnosCarreraCohorte(String carrera, int cohorte)
			throws AgendaException {
		QueryAlumnosCarreraCohorte query = new QueryAlumnosCarreraCohorte(this);
		query.setCarrera(carrera);
		query.setCohorte(cohorte);
		return query.executeQuery();
	}

	@Override
	public String[][] getAsignaturasPeriodo(int PeriodoAcademicoId) throws AgendaException {
		QueryAsignaturasPeriodo query = new QueryAsignaturasPeriodo(this);
		query.setPeriodoAcademicoId(PeriodoAcademicoId);
		return query.executeQuery();
	}

	@Override
	public String[][] getCarreras() throws AgendaException {
		QueryCarreras query = new QueryCarreras(this);
		return query.executeQuery();
	}

	@Override
	public String[][] getFacultades() throws AgendaException {
		QueryFacultades query = new QueryFacultades(this);
		return query.executeQuery();
	}

	@Override
	public String[][] getHorariosAlumnos(String[] ruts, String tipos)
	throws AgendaException {
		QueryHorarioAlumnosRuts query = new QueryHorarioAlumnosRuts(this);
		query.setRuts(ruts);
		query.setTipos(tipos.split(","));
		return query.executeQuery();
	}

	@Override
	public String[][] getHorariosAlumnosCriterios(String[] secciones,
			String[] ruts, String[] carreras, String[] cohortes, String[] tipos)
			throws AgendaException {
		QueryHorarioAlumnosCriterios query = new QueryHorarioAlumnosCriterios(this);
		query.setCarreras(carreras);
		query.setCohortes(cohortes);
		query.setRuts(ruts);
		query.setSecciones(secciones);
		query.setTipos(tipos);
		return query.executeQuery();
	}

	@Override
	public String[][] getHorariosAlumnosInscritos(String[] codigosOmega, String[] tipos) throws AgendaException {
		QueryHorarioAlumnosSecciones query = new QueryHorarioAlumnosSecciones(this);
		query.setRuts(codigosOmega);
		query.setTipos(tipos);
		return query.executeQuery();
	}

	@Override
	public String[][] getHorariosProfesores(String[] ruts,
			String[] tipos) throws AgendaException {
		QueryHorarioProfesoresRuts query = new QueryHorarioProfesoresRuts(this);
		query.setRuts(ruts);
		query.setTipos(tipos);
		return query.executeQuery();
	}

	@Override
	public String[][] getPeriodosAcademicos() throws AgendaException {
		QueryPeriodosAcademicos query = new QueryPeriodosAcademicos(this);
		return query.executeQuery();
	}

	@Override
	public String[][] getProfesoresAsignatura(String asignatura,
			int PeriodoAcademicoId) throws AgendaException {
		QueryProfesoresAsignatura query = new QueryProfesoresAsignatura(this);
		query.setAsignatura(asignatura);
		query.setPeriodoAcademicoId(PeriodoAcademicoId);
		return query.executeQuery();
	}

	@Override
	public String[][] getProfesoresFacultad(String facultadId)
	throws AgendaException {
		QueryProfesoresFacultad query = new QueryProfesoresFacultad(this);
		query.setFacultadId(facultadId);
		return query.executeQuery();
	}

	@Override
	public String[][] getProfesoresPeriodo(int PeriodoAcademicoId)
	throws AgendaException {
		QueryProfesoresPeriodo query = new QueryProfesoresPeriodo(this);
		query.setPeriodoAcademicoId(PeriodoAcademicoId);
		return query.executeQuery();
	}

	@Override
	public String[][] getSeccionesAsignatura(String asignatura, int PeriodoAcademicoId) throws AgendaException {
		QuerySeccionesAsignatura query = new QuerySeccionesAsignatura(this);
		query.setAsignatura(asignatura);
		query.setPeriodoAcademicoId(PeriodoAcademicoId);
		return query.executeQuery();
	}

	@Override
	public String[][] getUnidadesAcademicas() throws AgendaException {
		QueryUnidadesAcademicas query = new QueryUnidadesAcademicas(this);
		return query.executeQuery();
	}

	@Override
	public void init() throws ServletException {
		super.init();
		try {
			String filename = getServletContext().getRealPath("agenda.properties");
			properties.load(new FileReader(filename));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		logger.info("Starting server");
		logger.info(properties.getProperty("agenda.sqlConnectionString"));
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			logger.severe(e.getLocalizedMessage());
		}
	}

	@Override
	public Boolean isUsuarioLogueado() throws AgendaException {
		HttpServletRequest request = this.getThreadLocalRequest();
		HttpSession session = request.getSession();
		Object login = session.getAttribute("login");
		return new Boolean(login != null);
	}

	@Override
	public String logoutUsuario() throws AgendaException {
		HttpServletRequest request = this.getThreadLocalRequest();
		HttpSession session = request.getSession();
		session.removeAttribute("login");
		return "ok";
	}
}
