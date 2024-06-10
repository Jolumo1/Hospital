package hospital;

import java.sql.Date;

public class Trabajadores {

	private int id_esp;
	private String nombre;
	private String apellido;
	private String nacimiento;
	private double salario;
	private int id;

	public Trabajadores(int id_esp, String nombre, String apellido, String nacimiento, double salario) {
		super();
		this.id_esp = id_esp;
		this.nombre = nombre;
		this.apellido = apellido;
		this.nacimiento = nacimiento;
		this.salario = salario;
	}

	public Trabajadores() {
		super();
	}

	public int getId_esp() {
		return id_esp;
	}

	public void setId_esp(int id_esp) {
		this.id_esp = id_esp;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getNacimiento() {
		return nacimiento;
	}

	public void setNacimiento(String nacimiento) {
		this.nacimiento = nacimiento;
	}

	public double getSalario() {
		return salario;
	}

	public void setSalario(double salario) {
		this.salario = salario;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
	    return String.format("Trabajador %-7s %-22s %-22s %-22s %-16s %-10s", 
	                         String.format("ID: %d", id), 
	                         String.format("Nombre: %s", nombre), 
	                         String.format("Apellido: %s", apellido), 
	                         String.format("Nacimiento: %s", nacimiento), 
	                         String.format("Salario: %.2f", salario), 
	                         String.format("ID Especialidad: %d", id_esp));
	}




}
