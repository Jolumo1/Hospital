Ejercicio Evaluable 3
El Hospital.
Nos han llamado del Hopital Carlos Haya para mejorar la gestión interna del personal porque es
un autentico caos.
La base de datos que ellos tienen se llama Hospital.
El hospital cuenta con departamentos que tiene un nombre y está situado en una planta del
hospital. Dentro de cada departamento existen especialidades tienen un nombre y una
orientación (Norte, Sur, Este y Oeste). Por su parte los trabajadores pertenecen a un
departamento y de ellos se conoce su nombre, apellido, fecha de nacimiento y salario.
Crea un programa que permita gestionar el hospital: Dar de alta a trabajadores o Departamentos
y Especialidades, y borrarlos de la base de datos.
No se puede eliminar un Departamento ni una especialidad si existen trabajadores asociados.
Por su parte si queremos eliminar un departamento y tiene especialidades asociadas debe
informarse y volver a preguntar si aun así quiere eliminarlo.
Debemos poder modificar datos de cualquiera de los tres elementos que gestionamos.
Listar Departamentos se listarán ordenados por el número de especialidades que tengan.
Listar Especialidades se listarán por el total del importe del salario de sus trabajadores.
Listar Trabajadores ordenados por salario, Departamento o Especialidad.
Las salidas debe estar formateadas.

Las tablas creadas por los usuarios en la base de datos se crearán desde el programa y deberán
llamarse:
· nombreAlumno_departamento,
· nombreAlumno_especialidad,
· nombreAlumno_Trabajador.

** En este caso y trabajando con bases de datos no tienen sentido la creación de un array de
trabajadores dentro de departamento, ambos campos están relacionados por su clave foranea....
Será a través de sus claves como se podrá acceder a los datos.
