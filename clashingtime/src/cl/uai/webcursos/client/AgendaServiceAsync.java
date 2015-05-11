package cl.uai.webcursos.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Servicio asincrónico de Reportes de la Agenda UAI
 * 
 * @author Jorge Villalon
 *
 */
public interface AgendaServiceAsync {
	
	/**
	 * Ver {@link AgendaService#buscarProfesores(String)}
	 * @param prefix
	 * @param callback
	 */
	void buscarProfesores(String prefix, AsyncCallback<String[][]> callback);

	/**
	 * Ver {@link AgendaService#getActividadesAlumnos(String[], int, int)}
	 * @param ruts
	 * @param dia
	 * @param modulo
	 * @param callback
	 */
	void getActividadesAlumnos(String[] ruts, int dia, int modulo, AsyncCallback<String[][]> callback);

	/**
	 * Ver {@link AgendaService#getActividadesProfesores(String[], int, int)}
	 * @param ruts
	 * @param dia
	 * @param modulo
	 * @param callback
	 */
	void getActividadesProfesores(String[] ruts, int dia, int modulo,
			AsyncCallback<String[][]> callback);

	/**
	 * Ver {@link AgendaService#getAlumnosAsignaturas(int, String)}
	 * @param PeriodoAcademicoId
	 * @param codigosOmega
	 * @param callback
	 */
//	void getAlumnosAsignaturas(int PeriodoAcademicoId, String codigosOmega,
//			AsyncCallback<String[][]> callback);

	/**
	 * Ver {@link AgendaService#getAsignaturasPeriodo(int)}
	 * @param PeriodoAcademicoId
	 * @param callback
	 */
	void getAsignaturasPeriodo(int PeriodoAcademicoId,
			AsyncCallback<String[][]> callback);

	/**
	 * Ver {@link AgendaService#getFacultades()}
	 * @param callback
	 */
	void getFacultades(AsyncCallback<String[][]> callback);

	/**
	 * Ver {@link AgendaService#getHorariosAlumnos(int, String)}
	 * @param PeriodoAcademicoId período académico en que las asignaturas se dictan.
	 * @param codigosOmega lista separada por comas de los códigos Omega de las asignaturas.
	 * @param callback
	 */
	void getHorariosAlumnosInscritos(String[] codigosOmega, String[] tipos,
			AsyncCallback<String[][]> callback);

	/**
	 * Ver {@link AgendaService#getHorariosAlumnos(String[], String)}
	 * @param ruts
	 * @param tipos
	 * @param callback
	 */
	void getHorariosAlumnos(String[] ruts, String tipos, AsyncCallback<String[][]> callback);

	/**
	 * Ver {@link AgendaService#getHorariosProfesores(String[], String)}
	 * @param ruts
	 * @param tipos
	 * @param callback
	 */
	void getHorariosProfesores(String[] ruts, String[] tipos,
			AsyncCallback<String[][]> callback);

	/**
	 * Ver {@link AgendaService#getPeriodosAcademicos()}
	 * @param callback
	 */
	void getPeriodosAcademicos(AsyncCallback<String[][]> callback);

	/**
	 * Ver {@link AgendaService#getProfesoresAsignatura(String, int)}
	 * @param asignatura
	 * @param PeriodoAcademicoId
	 * @param callback
	 */
	void getProfesoresAsignatura(String asignatura, int PeriodoAcademicoId,
			AsyncCallback<String[][]> callback);

	/**
	 * Ver {@link AgendaService#getProfesoresFacultad(int)}
	 * @param facultadId
	 * @param callback
	 */
	void getProfesoresFacultad(String facultadId,
			AsyncCallback<String[][]> callback);

	/**
	 * Ver {@link AgendaService#getProfesoresPeriodo(int)}
	 * @param PeriodoAcademicoId
	 * @param callback
	 */
	void getProfesoresPeriodo(int PeriodoAcademicoId,
			AsyncCallback<String[][]> callback);

	/**
	 * Ver {@link AgendaService#getSeccionesAsignatura(String, int)}
	 * @param asignatura
	 * @param PeriodoAcademicoId
	 * @param callback
	 */
	void getSeccionesAsignatura(String asignatura, int PeriodoAcademicoId,
			AsyncCallback<String[][]> callback);

	/**
	 * Ver {@link AgendaService#getUnidadesAcademicas()}
	 * @param callback
	 */
	void getUnidadesAcademicas(AsyncCallback<String[][]> callback);

	void getActividadesAlumnosInscritos(String[] codigosOmega, int dia,
			int modulo, AsyncCallback<String[][]> callback);

	void getCarreras(AsyncCallback<String[][]> callback);

	void getAlumnosCarreraCohorte(String carrera, int cohorte,
			AsyncCallback<String[][]> callback);

	void getHorariosAlumnosCriterios(String[] secciones, String[] ruts,
			String[] carreras, String[] cohortes, String[] tipos,
			AsyncCallback<String[][]> callback);

	void getActividadesAlumnosCriterios(String[] secciones, String[] ruts,
			String[] carreras, String[] cohortes, int dia, int modulo,
			AsyncCallback<String[][]> callback);

	void autenticaUsuario(String user, String password,
			AsyncCallback<String> callback);

	void isUsuarioLogueado(AsyncCallback<Boolean> callback);

	void logoutUsuario(AsyncCallback<String> callback);

	void buscarAlumnos(String prefix, AsyncCallback<String[][]> callback);
}
