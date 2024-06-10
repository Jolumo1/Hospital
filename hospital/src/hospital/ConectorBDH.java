package hospital;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.TimeZone;

import hospital.Especialidades.Orientacion;

public class ConectorBDH {

	private String bd = "hospital";
	private String login = "root";
	private String password = "";
	private String url = "jdbc:mysql://localhost:3306/" + bd + "?serverTimezone=" + TimeZone.getDefault().getID();
	private Connection conn = null;

	public ConectorBDH() {
		// Cargamos la clase jdbc
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("No se encontró el Driver");
		}

		// Obtenemos la conexión
		try {
			conn = DriverManager.getConnection(url, login, password);

		} catch (SQLException e) {
			System.out.println("Error al conectar la base de datos");
		}

		if (conn != null) {
			System.out.println("Conexión a base de datos " + bd + ". lista");

			Statement orden;

			// Bloque para crear las tablas con sus campos si no existen en la base de datos
			// al iniciar el programa.

			try {
				orden = conn.createStatement();

				orden.executeUpdate(
						"CREATE TABLE IF NOT EXISTS jose_departamento (" + "ID_dpto INT AUTO_INCREMENT PRIMARY KEY, "
								+ "nombre VARCHAR(100) NOT NULL, " + "planta INT NOT NULL " + ")");

				orden.executeUpdate(
						"CREATE TABLE IF NOT EXISTS jose_especialidad (" + "id_esp INT AUTO_INCREMENT PRIMARY KEY, "
								+ "id_dpto INT NOT NULL, " + "nombre VARCHAR(100) NOT NULL, "
								+ "orientacion ENUM('Norte', 'Sur', 'Este', 'Oeste') NOT NULL, "
								+ "FOREIGN KEY (id_dpto) REFERENCES jose_departamento(ID_dpto)" + ")");

				orden.executeUpdate("CREATE TABLE IF NOT EXISTS jose_trabajador ("
						+ "id_trabajador INT AUTO_INCREMENT PRIMARY KEY, " + "id_esp INT NOT NULL, "
						+ "nombre VARCHAR(100) NOT NULL, " + "apellido VARCHAR(100) NOT NULL, "
						+ "fecha_nacimiento DATE NOT NULL, " + "salario DECIMAL(10, 2) NOT NULL, "
						+ "FOREIGN KEY (id_esp) REFERENCES jose_especialidad(id_esp)" + ")");

				// bloque para agregar un departamento en caso de que no haya ninguno al iniciar
				// el programa.
				ResultSet resDept;

				resDept = orden.executeQuery("SELECT max(ID_dpto) FROM `jose_departamento`");

				int deptId = 99;

				if (resDept.next()) {
					deptId = resDept.getInt(1);

				}

				if (deptId == 0) {

					orden.executeUpdate("INSERT INTO jose_departamento(ID_dpto, nombre, planta) VALUES(" + 1
							+ ", 'Cirugía', " + 1 + ")");

				}

				// bloque para agregar una especialidad en caso de que no haya ninguna al
				// iniciar el programa.

				resDept = orden.executeQuery("SELECT max(ID_dpto) FROM `jose_especialidad`");

				int espId = 99;

				if (resDept.next()) {
					espId = resDept.getInt(1);

				}

				if (espId == 0) {

					orden.executeUpdate(
							"INSERT INTO jose_especialidad(id_esp, id_dpto, nombre, orientacion) VALUES(1, 1, 'Cardiología', 'Norte')");

				}

				orden.close();

			} catch (SQLException e) {
				System.out.println("Error al crear la tabla: " + e);
			}

		}
	}

	public void desconectar() {
		conn = null;
		System.out.println("La conexion a la base de datos " + bd + " ha terminado");

	}

	public boolean insertarDepartamento(Departamentos departamento1) {

		// recibe un objeto por parametro, desglosa los atributos y los mete en la base
		// de datos con la consulta correspondiente

		boolean hecho = false;

		String nombre = departamento1.getNombre();
		int planta = departamento1.getPlanta();

		try {

			Statement orden = conn.createStatement();

			int i = orden.executeUpdate(
					"INSERT INTO jose_departamento(nombre, planta) VALUES('" + nombre + "','" + planta + "')");
			System.out.println("Se han insertado " + i + " filas");

			orden.close();
			hecho = true;

		} catch (SQLException e) {
			System.out.println("Error al insertar el departamento en la base de datos" + e);

		}

		return hecho;
	}

	public boolean insertarEspecialidad(Especialidades especialidad1) {
		boolean hecho = false;

		String nombre = especialidad1.getNombre();
		Especialidades.Orientacion orientacion1 = especialidad1.getOrientacion();
		int id_dpto = especialidad1.getId_dpto();

		try {

			Statement orden = conn.createStatement();

			int i = orden.executeUpdate("INSERT INTO jose_especialidad(id_dpto, nombre, orientacion) VALUES(" + id_dpto
					+ ", '" + nombre + "', '" + orientacion1 + "')");

			System.out.println("Se han insertado " + i + " filas");

			orden.close();
			hecho = true;

		} catch (SQLException e) {
			System.out.println("Error al insertar la especialidad en la base de datos: " + e);

		}

		return hecho;
	}

	public boolean insertarTrabajador(Trabajadores trabajador1) {
		boolean hecho = false;

		int id_esp = trabajador1.getId_esp();
		String nombre = trabajador1.getNombre();
		String apellido = trabajador1.getApellido();
		double salario = trabajador1.getSalario();
		String nacimiento = trabajador1.getNacimiento();

		try {

			Statement orden = conn.createStatement();

			int i = orden.executeUpdate(
					"INSERT INTO jose_trabajador(id_esp, nombre, apellido, salario,  fecha_nacimiento) VALUES(" + id_esp
							+ ", '" + nombre + "', '" + apellido + "', '" + salario + "', '" + nacimiento + "')");

			System.out.println("Se han insertado " + i + " filas");

			orden.close();
			hecho = true;

		} catch (SQLException e) {
			System.out.println("Error al insertar el trabajador en la base de datos: " + e);

		}

		return hecho;
	}

	public ArrayList<Departamentos> listarDepartamentoBD() {

		// Creamos una lista de departamentos, pasamos una consulta a la base de datos
		// que devuelve el departamento ordenado por el numero de especialidades que
		// tiene y metemos cada resultado en la lista, asi en la lista ya entran por
		// orden. El metodo devuelve la lista para imprimirla en el Main.

		ArrayList<Departamentos> listaDepartamentos = new ArrayList();

		// System.out.println("Lista de departamentos ordenados por número de
		// especialidades: ");
		ResultSet resultadoConsulta;
		try {
			Statement orden = conn.createStatement();
			resultadoConsulta = orden.executeQuery(
					"SELECT 	jose_departamento.ID_dpto,	jose_departamento.nombre,	jose_departamento.planta,	COUNT(jose_especialidad.id_esp) AS cantidad_especialidades\r\n"
							+ "FROM jose_departamento\r\n"
							+ "LEFT JOIN jose_especialidad ON jose_departamento.ID_dpto = jose_especialidad.id_dpto\r\n"
							+ "GROUP BY jose_departamento.nombre\r\n" + "ORDER BY cantidad_especialidades DESC;");

			while (resultadoConsulta.next()) {

				Departamentos departamento1 = new Departamentos();
				departamento1.setId(resultadoConsulta.getInt("ID_dpto"));
				departamento1.setNombre(resultadoConsulta.getString("nombre"));
				departamento1.setPlanta(resultadoConsulta.getInt("planta"));

				listaDepartamentos.add(departamento1);

				// esta es la forma original que tenia de hacerlo pero creo que no permites que
				// se imprima en el main.
				// Me parece mejor opcion porque ademas de listar el
				// departamento puedes añadir otros campos que den info adicional, como en este
				// caso decir la cantidad de especialidades.

				// System.out.println("ID: " + resultadoConsulta.getInt("ID_dpto") +
				// ", Nombre: " + resultadoConsulta.getString("nombre") + ", Planta: " +
				// resultadoConsulta.getInt("planta") + ", Cantidad de Especialidades: " +
				// resultadoConsulta.getString("cantidad_especialidades"));

			}

			orden.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return listaDepartamentos;
	}

	public ArrayList<Trabajadores> listarTrabajadoresBDSalario() {
		// ver explicacion funcionamiento de listarDepartamentoBD();
		// System.out.println("Lista de trabajadores ordenados por salario: ");

		ArrayList<Trabajadores> listaTrabajadores = new ArrayList();

		ResultSet resultadoConsulta;
		try {
			Statement orden = conn.createStatement();
			resultadoConsulta = orden
					.executeQuery("SELECT * FROM jose_trabajador \r\n" + "ORDER BY jose_trabajador.salario DESC");

			while (resultadoConsulta.next()) {
				Trabajadores trabajador1 = new Trabajadores();

				trabajador1.setId(resultadoConsulta.getInt("id_trabajador"));
				trabajador1.setId_esp(resultadoConsulta.getInt("id_esp"));
				trabajador1.setNombre(resultadoConsulta.getString("nombre"));
				trabajador1.setApellido(resultadoConsulta.getString("apellido"));
				trabajador1.setNacimiento(resultadoConsulta.getString("fecha_nacimiento"));
				trabajador1.setSalario(resultadoConsulta.getDouble("salario"));

				listaTrabajadores.add(trabajador1);

				// System.out.println("ID: " + resultadoConsulta.getInt("id_trabajador") + ",
				// Nombre: "
				// + resultadoConsulta.getString("nombre") + ", Apellido: "
				// + resultadoConsulta.getString("apellido") + ", Fecha de nacimiento: "
				// + resultadoConsulta.getString("fecha_nacimiento") + ", Salario: "
				// + resultadoConsulta.getDouble("salario") + ", ID Especialidad: "
				// + resultadoConsulta.getInt("id_esp"));
			}

			orden.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return listaTrabajadores;
	}

	public ArrayList<Trabajadores> listarTrabajadoresBDDepartamento() {
		// ver explicacion funcionamiento de listarDepartamentoBD();
		// System.out.println("Lista de trabajadores ordenados por la id del
		// departamento al que pertenece su especialidad: ");

		ArrayList<Trabajadores> listaTrabajadores = new ArrayList();

		ResultSet resultadoConsulta;

		try {
			Statement orden = conn.createStatement();
			resultadoConsulta = orden.executeQuery(
					"SELECT \r\n" + "    jose_trabajador.*, jose_departamento.nombre as nombre_departamento\r\n"
							+ "FROM \r\n" + "    jose_trabajador\r\n" + "LEFT JOIN \r\n"
							+ "    jose_especialidad ON jose_trabajador.id_esp = jose_especialidad.id_esp\r\n"
							+ "LEFT JOIN \r\n"
							+ "    jose_departamento ON jose_especialidad.id_dpto = jose_departamento.ID_dpto\r\n"
							+ "ORDER BY \r\n" + "    jose_departamento.ID_dpto ASC;");

			while (resultadoConsulta.next()) {
				Trabajadores trabajador1 = new Trabajadores();

				trabajador1.setId(resultadoConsulta.getInt("id_trabajador"));
				trabajador1.setId_esp(resultadoConsulta.getInt("id_esp"));
				trabajador1.setNombre(resultadoConsulta.getString("nombre"));
				trabajador1.setApellido(resultadoConsulta.getString("apellido"));
				trabajador1.setNacimiento(resultadoConsulta.getString("fecha_nacimiento"));
				trabajador1.setSalario(resultadoConsulta.getDouble("salario"));

				listaTrabajadores.add(trabajador1);

				// System.out.println("ID: " + resultadoConsulta.getInt("id_trabajador") + ",
				// Nombre: "
				// + resultadoConsulta.getString("nombre") + ", Apellido: "
				// + resultadoConsulta.getString("apellido") + ", Fecha de nacimiento: "
				// + resultadoConsulta.getString("fecha_nacimiento") + ", Salario: "
				// + resultadoConsulta.getDouble("salario") + ", Departamento: "
				// + resultadoConsulta.getString("nombre_departamento") + ", ID Departamento: "
				// + resultadoConsulta.getInt("ID_dpto"));
			}

			orden.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return listaTrabajadores;
	}

	public ArrayList<Trabajadores> listarTrabajadoresBDEspecialidad() {
		// ver explicacion funcionamiento de listarDepartamentoBD();
		// System.out.println("Lista de trabajadores ordenados por la id de la
		// especialidad a la que pertenece: ");

		ArrayList<Trabajadores> listaTrabajadores = new ArrayList();

		ResultSet resultadoConsulta;

		try {
			Statement orden = conn.createStatement();
			resultadoConsulta = orden.executeQuery(
					"SELECT \r\n" + "    jose_trabajador.*, jose_especialidad.nombre as nombre_especialidad\r\n"
							+ "FROM \r\n" + "    jose_trabajador\r\n" + "LEFT JOIN \r\n"
							+ "    jose_especialidad ON jose_trabajador.id_esp = jose_especialidad.id_esp\r\n"
							+ "ORDER BY \r\n" + "    jose_especialidad.id_esp ASC");

			while (resultadoConsulta.next()) {
				Trabajadores trabajador1 = new Trabajadores();

				trabajador1.setId(resultadoConsulta.getInt("id_trabajador"));
				trabajador1.setId_esp(resultadoConsulta.getInt("id_esp"));
				trabajador1.setNombre(resultadoConsulta.getString("nombre"));
				trabajador1.setApellido(resultadoConsulta.getString("apellido"));
				trabajador1.setNacimiento(resultadoConsulta.getString("fecha_nacimiento"));
				trabajador1.setSalario(resultadoConsulta.getDouble("salario"));

				listaTrabajadores.add(trabajador1);

				// System.out.println("ID: " + resultadoConsulta.getInt("id_trabajador") + ",
				// Nombre: "
				// + resultadoConsulta.getString("nombre") + ", Apellido: "
				// + resultadoConsulta.getString("apellido") + ", Fecha de nacimiento: "
				// + resultadoConsulta.getString("fecha_nacimiento") + ", Salario: "
				// + resultadoConsulta.getDouble("salario") + ", Especialidad: "
				// + resultadoConsulta.getString("nombre_especialidad") + ", ID Especialidad: "
				// + resultadoConsulta.getInt("id_esp"));
			}

			orden.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return listaTrabajadores;
	}

	public ArrayList<Especialidades> listarEspecialidadBD() {

		// ver funcionamiento explicado de listarDepartamendoBD()

		// System.out.println("Lista de especialidades ordenados por el sueldo total de
		// los trabajadores que lo componen: ");

		ArrayList<Especialidades> listaEspecialidades = new ArrayList();

		ResultSet resultadoConsulta;
		try {
			Statement orden = conn.createStatement();
			resultadoConsulta = orden.executeQuery(
					"SELECT jose_especialidad.id_esp, jose_especialidad.id_dpto, jose_especialidad.nombre, jose_especialidad.orientacion,\r\n"
							+ "    SUM(jose_trabajador.salario) AS suma_salarios\r\n" + "FROM \r\n"
							+ "    jose_especialidad\r\n" + "LEFT JOIN \r\n"
							+ "    jose_trabajador ON jose_especialidad.id_esp = jose_trabajador.id_esp\r\n"
							+ "GROUP BY \r\n" + "    jose_especialidad.id_esp, jose_especialidad.nombre\r\n"
							+ "ORDER BY \r\n" + "    suma_salarios DESC;");

			while (resultadoConsulta.next()) {
				Especialidades especialidad1 = new Especialidades();

				especialidad1.setId(resultadoConsulta.getInt("ID_esp"));
				especialidad1.setId_dpto(resultadoConsulta.getInt("id_dpto"));
				especialidad1.setNombre(resultadoConsulta.getString("nombre"));
				especialidad1.setOrientacion(Orientacion.valueOf(resultadoConsulta.getString("orientacion")));

				listaEspecialidades.add(especialidad1);

				// System.out.println("ID: " + resultadoConsulta.getInt("ID_esp") + ",
				// Departamento: "
				// + resultadoConsulta.getInt("id_dpto") + ", Nombre: " +
				// resultadoConsulta.getString("nombre")
				// + ", Orientación: " + resultadoConsulta.getString("orientacion") + ", Suma
				// salarios: "
				// + resultadoConsulta.getInt("suma_salarios"));

			}

			orden.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return listaEspecialidades;

	}

	public int eliminarTrabajadorBD(int idEliminar) {
		int hecho = 0;
		try {

			Statement orden = conn.createStatement();

			hecho = orden.executeUpdate("DELETE FROM jose_trabajador WHERE id_trabajador = " + idEliminar + ";");

			orden.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hecho;
	}

	public Trabajadores buscarTrabajadorBD(int idBuscar) {

		// busca en la base de datos por ID, si encuentra algo guarda los parametros de
		// la busqueda en un objeto y lo devuelve

		Trabajadores trabajador1 = new Trabajadores();
		try {
			Statement orden = conn.createStatement();
			ResultSet resultadoConsulta;
			resultadoConsulta = orden
					.executeQuery("SELECT * FROM jose_trabajador WHERE id_trabajador = " + idBuscar + ";");
			while (resultadoConsulta.next()) {
				trabajador1.setId(resultadoConsulta.getInt("id_trabajador"));
				trabajador1.setId_esp(resultadoConsulta.getInt("id_esp"));
				trabajador1.setNombre(resultadoConsulta.getString("nombre"));
				trabajador1.setApellido(resultadoConsulta.getString("apellido"));
				trabajador1.setNacimiento(resultadoConsulta.getString("fecha_nacimiento"));
				trabajador1.setSalario(resultadoConsulta.getDouble("salario"));
			}

			orden.close();
		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			System.out.println("Error buscarPersonaID");
		}

		return trabajador1;

	}

// 
	public Especialidades buscarEspecialidadBD(int idBuscar) {
		// busca en la base de datos por ID, si encuentra algo guarda los parametros de
		// la busqueda en un objeto y lo devuelve

		Especialidades especialidad1 = new Especialidades();
		try {
			Statement orden = conn.createStatement();
			ResultSet resultadoConsulta;
			resultadoConsulta = orden.executeQuery("SELECT * FROM jose_especialidad WHERE id_esp = " + idBuscar + ";");
			while (resultadoConsulta.next()) {
				especialidad1.setId(resultadoConsulta.getInt("id_esp"));
				especialidad1.setId_dpto(resultadoConsulta.getInt("id_dpto"));
				especialidad1.setNombre(resultadoConsulta.getString("nombre"));
				especialidad1.setOrientacion(Orientacion.valueOf(resultadoConsulta.getString("orientacion")));
			}
			orden.close();
		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			System.out.println("Error buscarEspecialidadID" + e);
		}

		return especialidad1;

	}

	public Departamentos buscarDepartamentoBD(int idBuscar) {
		Departamentos departamento1 = new Departamentos();
		try {
			Statement orden = conn.createStatement();
			ResultSet resultadoConsulta;
			resultadoConsulta = orden.executeQuery("SELECT * FROM jose_departamento WHERE ID_dpto = " + idBuscar + ";");
			while (resultadoConsulta.next()) {

				departamento1.setId(resultadoConsulta.getInt("ID_dpto"));
				departamento1.setNombre(resultadoConsulta.getString("nombre"));
				departamento1.setPlanta(resultadoConsulta.getInt("planta"));

			}
			orden.close();
		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			System.out.println("Error buscarEspecialidadID" + e);
		}

		return departamento1;

	}

// 
	public int modificarTrabajadorBD(Trabajadores trabajadorModificado) {
		// recibe un objeto, desglosa los atributos y los mete en la base de datos

		int hecho = 0;

		int id = trabajadorModificado.getId();
		int id_esp = trabajadorModificado.getId_esp();
		String nombre = trabajadorModificado.getNombre();
		String apellido = trabajadorModificado.getApellido();
		String nacimiento = trabajadorModificado.getNacimiento();
		double salario = trabajadorModificado.getSalario();

		try {

			Statement orden = conn.createStatement();

			hecho = orden.executeUpdate("UPDATE jose_trabajador SET id_trabajador = '" + id + "', id_esp = '" + id_esp
					+ "', nombre = '" + nombre + "', apellido = '" + apellido + "', fecha_nacimiento = '" + nacimiento
					+ "', salario = '" + salario + "' WHERE id_trabajador = " + id + ";");

			orden.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return hecho;

	}

	public int modificarEspecialidadBD(Especialidades especialidadModificado) {

		int hecho = 0;

		int id = especialidadModificado.getId();
		int id_dpto = especialidadModificado.getId_dpto();
		String nombre = especialidadModificado.getNombre();
		Orientacion orientacion = especialidadModificado.getOrientacion();

		try {

			Statement orden = conn.createStatement();

			hecho = orden.executeUpdate("UPDATE jose_especialidad SET id_esp = '" + id + "', id_dpto = '" + id_dpto
					+ "', nombre = '" + nombre + "', orientacion = '" + orientacion + "' WHERE id_esp = " + id + ";");
			orden.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("error al actualizar la especialidad" + e);
		}

		return hecho;

	}

	public int modificarDepartamentoBD(Departamentos departamentoModificado) {

		int hecho = 0;

		int id = departamentoModificado.getId();
		String nombre = departamentoModificado.getNombre();
		int planta = departamentoModificado.getPlanta();

		try {

			Statement orden = conn.createStatement();

			hecho = orden.executeUpdate("UPDATE jose_departamento SET nombre = '" + nombre + "', planta = '" + planta
					+ "' WHERE ID_dpto = " + id + ";");

			orden.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("error al actualizar el departamento" + e);
		}

		return hecho;

	}

	public boolean IdTrabajadorExiste(int id) {
		// para saber si una id esta en la tabla hago un arraylist de ids, al que le
		// meto todas las id que encuentra la consulta sql.
		// devuelve booleano si la id buscada está en ese array o no

		ArrayList<Integer> listaIdTrabajador = new ArrayList<>();
		boolean encontrado = false;
		try {
			Statement orden = conn.createStatement();
			ResultSet resultadoConsulta;
			resultadoConsulta = orden.executeQuery("select id_trabajador FROM jose_trabajador;");
			while (resultadoConsulta.next()) {
				listaIdTrabajador.add(resultadoConsulta.getInt(1));

			}

			if (listaIdTrabajador.contains(id)) {
				encontrado = true;
			} else {
				encontrado = false;
			}
			orden.close();
		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			System.out.println("Error buscar ID");
		}

		return encontrado;

	}

	public boolean IdEspecialidadExiste(int id) {
		ArrayList<Integer> listaIdEspecialidad = new ArrayList<>();
		boolean encontrado = false;
		try {
			Statement orden = conn.createStatement();
			ResultSet resultadoConsulta;
			resultadoConsulta = orden.executeQuery("select id_esp FROM jose_especialidad;");
			while (resultadoConsulta.next()) {
				listaIdEspecialidad.add(resultadoConsulta.getInt(1));

			}

			if (listaIdEspecialidad.contains(id)) {
				encontrado = true;
			} else {
				encontrado = false;
			}
			orden.close();

		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			System.out.println("Error buscar ID");
		}

		return encontrado;

	}

	public boolean IdDepartamentoExiste(int id) {
		ArrayList<Integer> listaIdDepartamento = new ArrayList<>();
		boolean encontrado = false;
		try {
			Statement orden = conn.createStatement();
			ResultSet resultadoConsulta;
			resultadoConsulta = orden.executeQuery("select id_dpto FROM jose_departamento;");
			while (resultadoConsulta.next()) {
				listaIdDepartamento.add(resultadoConsulta.getInt(1));

			}

			if (listaIdDepartamento.contains(id)) {
				encontrado = true;
			} else {
				encontrado = false;
			}

			orden.close();

		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			System.out.println("Error buscar ID");
		}

		return encontrado;

	}

	public boolean dptoTieneEspecialidades(int idDpto) {

		// con la consulta sql contamos las veces que la id del departamento que nos
		// interesa aparece en la tabla de especialidades, si es mas de 0 sabemos que
		// está y devuelve booleano

		boolean tiene = true;

		try {
			Statement orden = conn.createStatement();
			ResultSet resultadoConsulta = orden
					.executeQuery("SELECT COUNT(*) FROM jose_especialidad WHERE id_dpto = " + idDpto + ";");
			if (resultadoConsulta.next()) {
				int cantidadEspecialidades = resultadoConsulta.getInt(1);

				if (cantidadEspecialidades > 0) {
					tiene = true;
				} else {
					tiene = false;
				}

			}
			orden.close();
		} catch (SQLException e) {
			System.out.println("Error al verificar especialidades del departamento: " + e.getMessage());
		}

		return tiene;
	}

	public boolean dptoTieneTrabajadores(int idDpto) {

		boolean tiene = true;

		try {
			Statement orden = conn.createStatement();
			ResultSet resultadoConsulta = orden.executeQuery(
					"select COUNT(jose_trabajador.id_trabajador) as trabajadores\r\n" + "FROM jose_trabajador\r\n"
							+ "JOIN jose_especialidad on jose_trabajador.id_esp = jose_especialidad.id_esp\r\n"
							+ "WHERE jose_especialidad.id_dpto = " + idDpto);

			if (resultadoConsulta.next() && resultadoConsulta.getInt("trabajadores") > 0) {

				tiene = true;

			} else {
				tiene = false;
			}
			
			orden.close();
		} catch (SQLException e) {
			System.out.println("Error al verificar trabajadores del departamento: " + e.getMessage());
		}

		return tiene;
	}

	public int eliminarDepartamentoBD(int idDepartamento) {
		int hecho = 0;

		try {
			Statement orden = conn.createStatement();
			hecho = orden.executeUpdate("DELETE FROM jose_departamento WHERE ID_dpto = " + idDepartamento + ";");

			orden.close();

		} catch (

		SQLException e) {
			System.out.println("Error al eliminar departamento: " + e.getMessage());
		}

		return hecho;
	}

	public boolean especialidadTieneTrabajadores(int idEspecialidad) {
		boolean tiene = true;

		try {
			Statement orden = conn.createStatement();
			ResultSet resultadoConsulta = orden
					.executeQuery("SELECT COUNT(*) FROM jose_trabajador WHERE id_esp = " + idEspecialidad + ";");
			if (resultadoConsulta.next()) {
				int cantidadTrabajadores = resultadoConsulta.getInt(1);

				if (cantidadTrabajadores > 0) {
					tiene = true;
				} else {
					tiene = false;
				}
			}
			orden.close();
		} catch (SQLException e) {
			System.out.println("Error al verificar las especialidades de los trabajadores: " + e.getMessage());
		}

		return tiene;
	}

	public int eliminarEspecialidadBD(int idEspecialidad) {
		int hecho = 0;

		try {
			Statement orden = conn.createStatement();

			hecho = orden.executeUpdate("DELETE FROM jose_especialidad WHERE id_esp = " + idEspecialidad + ";");

			orden.close();

		} catch (SQLException e) {
			System.out.println("Error al eliminar especialidad: " + e.getMessage());
		}

		return hecho;
	}
	
	public int eliminarEspecialidadesEnDepartamentoBD(int idDepartamento) {
		int hecho = 0;

		try {
			Statement orden = conn.createStatement();

			hecho = orden.executeUpdate("DELETE FROM jose_especialidad WHERE id_dpto = " + idDepartamento + ";");

			orden.close();

		} catch (SQLException e) {
			System.out.println("Error al eliminar especialidad: " + e.getMessage());
		}

		return hecho;
	}
	

}// fin
