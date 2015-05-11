package cl.uai.webcursos.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("ajax")
public interface AgendaService extends RemoteService {
	
	/**
	 * Busca todo los alumnos dado un prefijo para el apellido (ej: gon econtrarÃ¡ GonzÃ¡lez)
	 * @param prefix prefijo a buscar
	 * @return tabla Rut,Alumno
	 * @throws Exception
	 */
	String[][] buscarAlumnos(String prefix) throws AgendaException;
	
	/**
	 * Busca todo los profesores dado un prefijo para el apellido (ej: gon econtrarÃ¡ GonzÃ¡lez)
	 * @param prefix prefijo a buscar
	 * @return tabla Rut,Profesor
	 * @throws Exception
	 */
	String[][] buscarProfesores(String prefix) throws AgendaException;
	
	/**
	 * Obtiene las actividades de un grupo de alumnos que calzan con un dÃ­a y mÃ³dulo especÃ­ficos
	 * @param ruts arreglo con los ruts de los alumnos
	 * @param dia dÃ­a de las actividades
	 * @param modulo mÃ³dulo de las actividades
	 * @return tabla Seccion,Total
	 * @throws AgendaException
	 */ 
	String[][] getActividadesAlumnos(String[] ruts, int dia, int modulo) throws AgendaException;
	
	/**
	 * Obtiene las actividades de un grupo de alumnos que calzan con un dÃ­a y mÃ³dulo especÃ­ficos
	 * @param ruts arreglo con los ruts de los alumnos
	 * @param dia dÃ­a de las actividades
	 * @param modulo mÃ³dulo de las actividades
	 * @return tabla Seccion,Total
	 * @throws AgendaException
	 */ 
	String[][] getActividadesAlumnosInscritos(String[] codigosOmega, int dia, int modulo) throws AgendaException;
	
	String[][] getActividadesAlumnosCriterios(String[] secciones, String[] ruts, String[] carreras, String[] cohortes, int dia, int modulo) throws AgendaException;
	/**
	 * Obtiene las actividades de un grupo de profesores que calzan con un dÃ­a y mÃ³dulo especÃ­ficos
	 * @param ruts arreglo con los ruts de los profesores
	 * @param dia dÃ­a de las actividades
	 * @param modulo mÃ³dulo de las actividades
	 * @return tabla Seccion,Total
	 * @throws AgendaException
	 */
	String[][] getActividadesProfesores(String[] ruts, int dia, int modulo) throws AgendaException;
	
	/**
	 * Obtiene la ruts de los alumnos inscritos en un conjunto de secciones para un perÃ­odo acadÃ©mico dado
	 * @param PeriodoAcademicoId el perÃ­odo acadÃ©mico
	 * @param codigosOmega lista separada por comas de las secciones
	 * @return tabla Rut
	 */
//	String[][] getAlumnosAsignaturas(int PeriodoAcademicoId, String codigosOmega) throws AgendaException;
	
	String[][] getAlumnosCarreraCohorte(String carrera, int cohorte) throws AgendaException;
	
	/**
	 * Obtiene las asignaturas dictadas en un periodo acadÃ©mico especÃ­fico.
	 * @param PeriodoAcademicoId perÃ­odo acadÃ©mico
	 * @return tabla NombreAsignatura
	 * @throws AgendaException
	 */
	String[][] getAsignaturasPeriodo(int PeriodoAcademicoId) throws AgendaException;
	
	String[][] getCarreras() throws AgendaException;
	/**
	 * Obtiene las facultades disponibles
	 * @return tabla FacultadId,Facultad
	 * @throws AgendaException
	 */
	String[][] getFacultades() throws AgendaException;
	
	/**
	 * Obtiene los horarios de los alumnos inscritos en una lista de secciones, para un perÃ­odo
	 * acadÃ©mico especÃ­fico.
	 * @param codigosOmega arreglo de secciones
	 * @param tipos areglo de tipos de actividad (ej: CÃ�TEDRA, LABORATORIO, etc.)
	 * @return tabla Modulo,Lunes,Martes,etc. con Modulo de 1 al 9
	 * @throws AgendaException
	 */
	String[][] getHorariosAlumnosInscritos(String[] codigosOmega, String[] tipos) throws AgendaException;

	String[][] getHorariosAlumnosCriterios(String[] secciones, String[] ruts, String[] carreras, String[] cohortes, String[] tipos) throws AgendaException;
	/**
	 * Obtiene los horarios de una lista de alumnos, dado un tipo de actividad (ej: catedra, laboratorio)
	 * @param ruts lista de ruts de alumnos
	 * @param tipos lista separada por comas de tipos de actividad (ej: 'CATEDRA','LABORATORIO')
	 * @return tabla Modulo,Lunes,Martes,etc. con Modulo de 1 al 9
	 * @throws AgendaException
	 */
	String[][] getHorariosAlumnos(String[] ruts, String tipos) throws AgendaException;
	
	/**
	 * Obtiene los horarios de una lista de profesores, dado un tipo de actividad (ej: catedra, laboratorio)
	 * @param ruts lista de ruts de profesores
	 * @param tipos lista separada por comas de tipos de actividad (ej: 'CATEDRA','LABORATORIO')
	 * @return tabla Modulo,Lunes,Martes,etc. con Modulo de 1 al 9
	 * @throws AgendaException
	 */
	String[][] getHorariosProfesores(String[] ruts, String[] tipos) throws AgendaException;
	
	/**
	 * Obtiene la lista de perÃ­odos acadÃ©micos disponibles
	 * @return tabla PeriodoAcademicoId,Nombre
	 * @throws AgendaException
	 */
	String[][] getPeriodosAcademicos() throws AgendaException;
	
	/**
	 * Obtiene los profesores que dictan una asignatura para un perÃ­odo
	 * acadÃ©mico dado
	 * @param asignatura nombre de la asignatura
	 * @param PeriodoAcademicoId perÃ­odo acadÃ©mico en que se dicta
	 * @return tabla Rut,Profesor
	 * @throws AgendaException
	 */
	String[][] getProfesoresAsignatura(String asignatura, int PeriodoAcademicoId) throws AgendaException;
	
	/**
	 * Obtiene los profesores que pertenecen a una facultad especÃ­fica
	 * @param facultadId
	 * @return tabla Rut,Profesor
	 * @throws AgendaException
	 */
	String[][] getProfesoresFacultad(String facultadId) throws AgendaException;
	
	/**
	 * Obtiene los profesores que hacen clases en un perÃ­odo acadÃ©mico especÃ­fico
	 * @param PeriodoAcademicoId perÃ­odo acadÃ©mico
	 * @return tabla Rut,Profesor
	 * @throws AgendaException
	 */
	String[][] getProfesoresPeriodo(int PeriodoAcademicoId) throws AgendaException;
	
	/**
	 * Obtiene las secciones que se dictan de una asignatura dada para un perÃ­odo
	 * acadÃ©mico especÃ­fico
	 * @param asignatura el nombre de la asignatura (ej: PROGRAMACIÃ“N)
	 * @param PeriodoAcademicoId id del perÃ­odo acadÃ©mico
	 * @return tabla Seccion,CodigoOmega
	 * @throws AgendaException
	 */
	String[][] getSeccionesAsignatura(String asignatura, int PeriodoAcademicoId) throws AgendaException;
	
	/**
	 * Obtiene las unidades acadÃ©micas disponibles
	 * @return tabla PeriodoAcademicoId,NombreUnidadAcademica
	 * @throws AgendaException
	 */
	String[][] getUnidadesAcademicas() throws AgendaException;
	
	String autenticaUsuario(String user, String password) throws AgendaException;
	
	String logoutUsuario() throws AgendaException;
	
	Boolean isUsuarioLogueado() throws AgendaException;
}
