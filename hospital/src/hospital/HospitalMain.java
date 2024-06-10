package hospital;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class HospitalMain {

	private static ConectorBDH baseDatos = new ConectorBDH();

	public static void main(String[] args) {

		BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
		boolean salir = false;

		while (!salir) {

			System.out.println();
			System.out.println("*********************************************");
			System.out.println("**           HOSPITAL CRIMINAL             **");
			System.out.println("**            Menu de opciones:            **");
			System.out.println("*********************************************");
			System.out.println();
			System.out.println("1  - Listar departamentos");
			System.out.println("2  - Crear departamento");
			System.out.println("3  - Modificar departamento");
			System.out.println("4  - Eliminar departamento");
			System.out.println();
			System.out.println("5  - Listar especialidades");
			System.out.println("6  - Crear especialidad");
			System.out.println("7  - Modificar especialidad");
			System.out.println("8  - Eliminar especialidad");
			System.out.println();
			System.out.println("9  - Listar trabajadores por salario");
			System.out.println("10 - Listar trabajadores por especialidad");
			System.out.println("11 - Listar trabajadores por departamento");
			System.out.println("12 - Crear trabajador");
			System.out.println("13 - Modificar trabajador");
			System.out.println("14 - Eliminar trabajador");
			System.out.println();
			System.out.println("15 - Salir");

			try {

				int opcion;

				opcion = Integer.parseInt(br1.readLine());
				switch (opcion) {

				case 1:
					listarDepartamentos();
					break;

				case 2:
					crearDepartamento();
					break;

				case 3:
					modificarDepartamento(Basicos.leerEntero("Introduice la ID del departamento a modificar"));
					break;

				case 4:
					eliminarDepartamento(Basicos.leerEntero("Introduce la ID del departamento a eliminar"));
					break;

				case 5:
					listarEspecialidades();
					break;

				case 6:
					crearEspecialidad();

					break;

				case 7:
					modificarEspecialidad(Basicos.leerEntero("Introduce la ID de la especialidad a modificar"));
					break;

				case 8:
					eliminarEspecialidad(Basicos.leerEntero("Introduce la ID de la especialidad a eliminar"));
					break;

				case 9:
					listarTrabajadoresSalario();

					break;

				case 10:
					listarTrabajadoresEspecialidad();
					break;

				case 11:
					listarTrabajadoresDepartamento();
					break;

				case 12:
					crearTrabajador();

					break;

				case 13:
					modificarTrabajador(Basicos.leerEntero("Introduce la id del trabajador que quieres modificar"));
					break;

				case 14:
					eliminarTrabajador(Basicos.leerEntero("Introduce la ID del trabajador a eliminar"));
					break;

				case 15:
					System.out.println("Cerrando programa...");
					salir = true;
					baseDatos.desconectar();
					dibujito();
					break;

				default:
					System.out.println("Opcion no válida");
					break;

				}

			} catch (NumberFormatException | IOException e) {

				System.out.println("Error try catch en el menu inicial" + e);
				;
			}

		}

	}// fin main

	public static void listarDepartamentos() {
		// Todos los metodos de listar reciben una lista de objetos segun el tipo de
		// busqueda, ordenados segun la consulta sql que se hace en el conector
		// luego se recorre para imprimir formateado.

		ArrayList<Departamentos> listaDepartamentos = baseDatos.listarDepartamentoBD();

		System.out.println("Lista de departamentos ordenados por número de especialidades que contienen: ");

		for (Departamentos departamento : listaDepartamentos) {
			System.out.println(departamento.toString());
		}

	}

	public static void listarEspecialidades() {
		ArrayList<Especialidades> listaEspecialidades = baseDatos.listarEspecialidadBD();

		System.out
				.println("Lista de especialidades ordenados por el sueldo total de los trabajadores que lo componen: ");

		for (Especialidades especialidad : listaEspecialidades) {
			System.out.println(especialidad.toString());
		}

	}

	public static void listarTrabajadoresSalario() {
		ArrayList<Trabajadores> listaTrabajadores = baseDatos.listarTrabajadoresBDSalario();

		System.out.println("Lista de trabajadores ordenados por salario: ");

		for (Trabajadores trabajador : listaTrabajadores) {
			System.out.println(trabajador.toString());
		}
	}

	public static void listarTrabajadoresDepartamento() {
		ArrayList<Trabajadores> listaTrabajadores = baseDatos.listarTrabajadoresBDDepartamento();

		System.out.println(
				"Lista de trabajadores ordenados por la id del departamento al que pertenece su especialidad: ");

		for (Trabajadores trabajador : listaTrabajadores) {
			System.out.println(trabajador.toString());
		}
	}

	public static void listarTrabajadoresEspecialidad() {
		ArrayList<Trabajadores> listaTrabajadores = baseDatos.listarTrabajadoresBDEspecialidad();

		System.out.println("Lista de trabajadores ordenados por la id de la especialidad a la que pertenece: ");

		for (Trabajadores trabajador : listaTrabajadores) {
			System.out.println(trabajador.toString());
		}
	}

	public static void crearDepartamento() {

		// pide los datos por consola para crear el objeto y lo pasa al metodo
		// correspondiente del conector de la base de datos para comprobar que todo es
		// correcto y agregarlo a la base de datos

		Departamentos departamento1 = new Departamentos();
		System.out.println("Introduce los datos del nuevo departamento");
		departamento1.setNombre(Basicos.leerCadena("Introduce el nombre: "));
		departamento1.setPlanta(Basicos.leerEntero("Introduce el número de planta:"));

		if (baseDatos.insertarDepartamento(departamento1)) {
			System.out.println("Departamento dado de alta correctamente...");

		} else {
			System.out.println("Error al dar de alta el departamento...");
		}
		Basicos.esperar(2);
	}

	public static void crearEspecialidad() {

		// pide los datos por consola para crear el objeto y lo pasa al metodo
		// correspondiente del conector de la base de datos para comprobar que todo es
		// correcto y agregarlo a la base de datos

		boolean entradaCorrecta = false;
		Especialidades especialidad1 = new Especialidades();
		Especialidades.Orientacion orientacion1 = null;
		String nombre;
		int id_dpto;

		System.out.println("Introduce los datos de la nueva especialidad");
		nombre = Basicos.leerCadena("Introduce el nombre: ");

		// Bucle para asegurarse de que la entrada de orientacion es válida y
		// correpsonde a las opciones del enum
		while (!entradaCorrecta) {
			try {
				orientacion1 = Especialidades.Orientacion
						.valueOf(Basicos.leerCadena("Introduce la orientación (Norte, Sur, Este, Oeste):"));

				entradaCorrecta = true; // Si llegamos aquí, la entrada es correcta

			} catch (IllegalArgumentException e) {
				System.out.println("La orientación no es correcta, introdúzcala de nuevo.");

			}

		}

		// bucle para asegurarse de que el departamento al que se va a asignar la
		// especialidad existe usando un metodo especifico para ello

		boolean idMal = true;
		do {
			id_dpto = Basicos.leerEntero("Introduce el número del departamento al que pertenece:");

			if (baseDatos.IdDepartamentoExiste(id_dpto)) {
				especialidad1.setId_dpto(id_dpto);
				idMal = false;

			} else {
				System.out.println("Esa id de departamento no existe.");
				idMal = true;
			}

		} while (idMal);

		especialidad1.setNombre(nombre);
		especialidad1.setOrientacion(orientacion1);

		if (baseDatos.insertarEspecialidad(especialidad1)) {
			System.out.println("Especialidad dada de alta correctamente...");

		} else {
			System.out.println("Error al dar de alta la especialidad...");
		}
		Basicos.esperar(2);

	}

	public static void crearTrabajador() {
// igual que el anterior, pide datos, verifica que la id de la especialidad existe antes de asignarla y llama al metodo de la base de datos.-

		Trabajadores trabajador1 = new Trabajadores();

		boolean idMal = true;
		do {

			int id_esp = Basicos.leerEntero("Introduce la id de la especialidad del trabajador");

			if (baseDatos.IdEspecialidadExiste(id_esp)) {
				trabajador1.setId_esp(id_esp);
				idMal = false;

			} else {
				System.out.println("Esa id de especialidad no existe.");
				idMal = true;
			}

		} while (idMal);

		String nombre = Basicos.leerCadena("Introduce el nombre: ");
		String apellido = Basicos.leerCadena("Introduce el apellido: ");
		double salario = Basicos.leerDouble("Introduce el salario: ");
		String nacimiento = Basicos.leerFecha();

		trabajador1.setNombre(nombre);
		trabajador1.setApellido(apellido);
		trabajador1.setSalario(salario);
		trabajador1.setNacimiento(nacimiento);

		if (baseDatos.insertarTrabajador(trabajador1)) {
			System.out.println("Trabajador dado de alta correctamente...");

		} else {
			System.out.println("Error al dar de alta al trabajador...");
		}
		Basicos.esperar(2);

	}

	public static void eliminarTrabajador(int id) {

		// llama al metodo para buscar en la base de datos con la id facilitada, y si
		// existe llama al metodo para eliminar la entrada de la base de datos.

		int hecho = 0;
		Trabajadores trabajador1 = baseDatos.buscarTrabajadorBD(id);
		if (trabajador1.getNombre() == null) {
			System.out.println("No se ha encontrado ese ID");
		} else {
			hecho = baseDatos.eliminarTrabajadorBD(id);
		}

		if (hecho > 0) {
			System.out.println("Trabajador eliminado correctamente");
		} else {
			System.out.println("No se ha podido modificar");
		}

	}

	public static void modificarTrabajador(int id) {

		// busca por la id que recibe, si encuentra algo recibe el objeto a modificar,
		// pide los parametros y devuelve un objeto con los parametros nuevos

		int hecho = 0;
		Trabajadores trabajadorModificado = baseDatos.buscarTrabajadorBD(id);

		if (trabajadorModificado.getNombre() == null) {
			System.out.println("No se ha encontrado ese ID");
		} else {
			boolean idNuevaMal = true;
			do {
				int idNuevaEspecialidad = Basicos.leerEntero("Introduzca la id de la nueva especialidad");

				if (baseDatos.IdEspecialidadExiste(idNuevaEspecialidad)) {
					trabajadorModificado.setId_esp(idNuevaEspecialidad);
					idNuevaMal = false;

				} else {
					System.out.println("Esa id de especialidad no existe.");
					idNuevaMal = true;
				}

			} while (idNuevaMal);

			trabajadorModificado.setNombre(Basicos.leerCadena("Introduzca el nuevo nombre"));
			trabajadorModificado.setApellido(Basicos.leerCadena("Introduzca el nuevo apellido"));
			trabajadorModificado.setNacimiento(Basicos.leerFecha());
			trabajadorModificado.setSalario(Basicos.leerDouble("Introduzca el nuevo salario"));

		}

		// llama al metodo del conector para modificar el trabajador pasandole el objeto
		// modificado (la id es la misma).
		// usamos el valor de la variable hecho para saber sis e ha hecho o no, ya que
		// obtiene el num de lineas modificadas.

		hecho = baseDatos.modificarTrabajadorBD(trabajadorModificado);

		if (hecho > 0) {
			System.out.println("Modificacion efectuada correctamente");
		} else {
			System.out.println("Error en la modificacion");
		}

	}

	public static void modificarEspecialidad(int id) {
		int hecho = 0;
		Especialidades especialidadModificado = baseDatos.buscarEspecialidadBD(id);
		Especialidades.Orientacion orientacion1 = null;
		boolean entradaCorrecta = false;

		if (especialidadModificado.getNombre() == null) {
			System.out.println("No se ha encontrado ese ID");
		} else {
			boolean idNuevaMal = true;
			do {
				int idNuevoDepartamento = Basicos.leerEntero("Introduzca la id del nuevo departamento");

				if (baseDatos.IdDepartamentoExiste(idNuevoDepartamento)) {
					especialidadModificado.setId_dpto(idNuevoDepartamento);
					idNuevaMal = false;

				} else {
					System.out.println("Esa id de departamento no existe.");
					idNuevaMal = true;
				}

			} while (idNuevaMal);

			especialidadModificado.setNombre(Basicos.leerCadena("Introduzca el nuevo nombre"));

			// Bucle para verificar orientacion
			while (!entradaCorrecta) {
				try {
					orientacion1 = Especialidades.Orientacion
							.valueOf(Basicos.leerCadena("Introduce la orientación (Norte, Sur, Este, Oeste):"));

					entradaCorrecta = true; // Si llegamos aquí, la entrada es correcta

				} catch (IllegalArgumentException e) {
					System.out.println("La orientación no es correcta, introdúzcala de nuevo.");

				}

			}

			especialidadModificado.setOrientacion(orientacion1);
		}

		hecho = baseDatos.modificarEspecialidadBD(especialidadModificado);

		if (hecho > 0) {
			System.out.println("Modificacion efectuada correctamente");
		} else {
			System.out.println("Error en la modificacion");
		}

	}

	public static void modificarDepartamento(int id) {
		int hecho = 0;
		Departamentos departamentoModificado = baseDatos.buscarDepartamentoBD(id);

		if (departamentoModificado.getNombre() == null) {
			System.out.println("No se ha encontrado ese ID");
		} else {

			departamentoModificado.setNombre(Basicos.leerCadena("Introduzca el nuevo nombre"));
			departamentoModificado.setPlanta(Basicos.leerEntero("Introduzca el número de la nueva planta"));

		}

		hecho = baseDatos.modificarDepartamentoBD(departamentoModificado);

		if (hecho > 0) {
			System.out.println("Modificacion efectuada correctamente");
		} else {
			System.out.println("Error en la modificacion");
		}

	}

	public static void eliminarDepartamento(int idDpto) {

		boolean tieneTrabajadores = baseDatos.dptoTieneTrabajadores(idDpto);

		if (tieneTrabajadores) {
			System.out.println(
					"No se puede eliminar el departamento porque tiene trabajadores asociados a sus especialidades");
		} else {

			// Verificar si el departamento tiene especialidades asociadas
			boolean tieneEspecialidades = baseDatos.dptoTieneEspecialidades(idDpto);

			if (tieneEspecialidades) {
				String respuestaBorrar = Basicos.leerCadena(
						"El departamento tiene especialidades asociadas que se eliminarán tambien. Quieres continuar? SI - NO ");

				if (respuestaBorrar.equalsIgnoreCase("si")) {

					if (baseDatos.eliminarEspecialidadesEnDepartamentoBD(idDpto) > 0) {
						System.out.println("Especialidades asociadas eliminadas correctamente.");
					}

					if (baseDatos.eliminarDepartamentoBD(idDpto) > 0) {
						System.out.println("Departamento eliminado correctamente.");
					}

				} else {
					System.out.println("Saliendo...");
				}

			} else {
				if (baseDatos.eliminarDepartamentoBD(idDpto) > 0) {
					System.out.println("Departamento eliminado correctamente.");
				} else {
					System.out.println("No se pudo eliminar el departamento.");
				}
			}

		}
	}

	public static void eliminarEspecialidad(int idEspecialidad) {

		boolean tieneTrabajadores = baseDatos.especialidadTieneTrabajadores(idEspecialidad);

		if (tieneTrabajadores) {
			System.out.println("No se puede eliminar una especialidad si ttiene trabajadores asociados.");
		} else {
			int hecho = baseDatos.eliminarEspecialidadBD(idEspecialidad);
			if (hecho > 0) {
				System.out.println("Especialidad eliminada correctamente.");
			} else {
				System.out.println("No se pudo eliminar la especialidad.");
			}
		}
	}

	public static void dibujito() {

		System.out.println("      |________|___________________|_");
		System.out.println("      |        | | | | | | | | | | | |________________");
		System.out.println("      |________|___________________|_|                ,");
		System.out.println("      |        |                   |                  ,");

	}

}// fin
