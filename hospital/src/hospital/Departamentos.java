package hospital;

public class Departamentos {

	private String nombre;
	private int planta;
	private int id;

	public Departamentos(String nombre, int planta) {
		super();
		this.nombre = nombre;
		this.planta = planta;
	}

	public Departamentos() {

	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getPlanta() {
		return planta;
	}

	public void setPlanta(int planta) {
		this.planta = planta;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
	    return String.format("Departamento - %-8s %-30s %-10s", 
	                         String.format("ID: %d", id), 
	                         String.format("Nombre: %s", nombre), 
	                         String.format("Planta: %d", planta));
	}




}
