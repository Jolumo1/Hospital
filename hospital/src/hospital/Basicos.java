package hospital;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Basicos {

	public static void esperar(int n) {
		long tiempo0, tiempo1;
		tiempo0 = System.currentTimeMillis();

		do {
			tiempo1 = System.currentTimeMillis();

		} while ((tiempo1 - tiempo0) < (n * 1000));
	}

	public static int leerEntero(String cadena) {
		BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));
		int numero = 0;
		boolean continuar = false;
		do {

			System.out.println(cadena);
			try {
				numero = Integer.parseInt(entrada.readLine());
				continuar = false;
			} catch (NumberFormatException e) {
				System.out.println("No has introducido un numero entero correcto");
				continuar = true;
				esperar(2);
			} catch (IOException e) {
				System.out.println("Se ha producido un error de entrada/salida generico");
				continuar = true;
				esperar(2);

			}

		} while (continuar);
		return numero;

	}

	public static String leerCadena(String cadena) {
		BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));
		boolean continuar = false;
		String cadenaSalida = null;

		do {

			System.out.println(cadena);
			try {
				cadenaSalida = entrada.readLine();
				continuar = false;

			} catch (IOException e) {
				System.out.println("Se ha producido un error de entrada/salida generico");
				continuar = true;
				esperar(2);

			}

		} while (continuar);

		return cadenaSalida;

	}

	public static double leerDouble(String cadena) {
		BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));
		boolean continuar = false;
		double doubleSalida = 0;

		do {

			System.out.println(cadena);
			try {
				doubleSalida = Double.parseDouble(entrada.readLine());
				continuar = false;

			} catch (NumberFormatException |IOException e) {
				System.out.println("No has usado el formato de número correcto, ejemplo 999.99: ");
				continuar = true;
				esperar(2);

			}

		} while (continuar);

		return doubleSalida;

	}

	public static String leerFecha() {
		BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
		String fechaSalida;
		boolean continuar = true;
		String formatoFecha = "\\d{4}-\\d{2}-\\d{2}";

		do {
			System.out.println("Introduce la fecha de nacimiento en formato yyyy-mm-dd:");
			try {
				fechaSalida = br1.readLine();
				if (fechaSalida.matches(formatoFecha)) {
					continuar = false;
					break;

				} else {
					System.out.println(
							"La fecha que has introducido no tiene el formato correcto (yyyy-mm-dd). Inténtalo de nuevo.");
					continuar = true;
				}

			} catch (IOException e) {
				System.out.println("Error al leer la entrada. Por favor, inténtalo de nuevo.");
				fechaSalida = " ";
			}

		} while (continuar);

		return fechaSalida;
	}

}
