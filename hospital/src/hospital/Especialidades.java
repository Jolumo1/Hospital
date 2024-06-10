package hospital;

public class Especialidades {

	public enum Orientacion {
		Norte, Sur, Este, Oeste
	};

	private String nombre;
	private Orientacion orientacion;
	private int id;
	private int id_dpto;

	public Especialidades(String nombre, Orientacion orientacion, int id_dpto) {
		super();
		this.nombre = nombre;
		this.orientacion = orientacion;
		this.id_dpto = id_dpto;
	}

	public Especialidades() {

	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Orientacion getOrientacion() {
		return orientacion;
	}

	public void setOrientacion(Orientacion orientacion) {
		this.orientacion = orientacion;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_dpto() {
		return id_dpto;
	}

	public void setId_dpto(int id_dpto) {
		this.id_dpto = id_dpto;
	}

	@Override
	public String toString() {
	    return String.format("Especialidad %-7s %-30s %-20s %-8s", 
	                         String.format("ID: %d", id), 
	                         String.format("Nombre: %s", nombre), 
	                         String.format("Orientación: %s", orientacion), 
	                         String.format("ID Departamento: %d", id_dpto));
	}


}
