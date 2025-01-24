
# Sistema de Gestión Hospitalaria en Java

Este es el resultado de un ejercicio diseñado para practicar programación básica en Java a través de la creación de un sistema de gestión para un hospital. Al final incluyo el enunciado del problema.
El objetivo principal es desarrollar una aplicación de consola que permita gestionar departamentos, especialidades y trabajadores dentro de un hospital, utilizando conceptos básicos y avanzados de programación orientada a objetos (POO) y manipulación de bases de datos.

## Idea del Proyecto

El proyecto simula la gestión de un hospital, permitiendo realizar operaciones sobre los siguientes elementos:

- **Departamentos**: Se pueden listar, crear, modificar y eliminar departamentos.
- **Especialidades**: Cada especialidad está asociada a un departamento y se puede gestionar (listar, crear, modificar, eliminar).
- **Trabajadores**: Los trabajadores están vinculados a especialidades y departamentos, y se puede gestionar su información (listar por salario, especialidad, departamento; crear, modificar, eliminar).

La principal finalidad del proyecto es **practicar el uso de Java** y sus conceptos fundamentales como clases, objetos, estructuras de datos (listas, arrays), manejo de excepciones y trabajo con entradas y salidas (entrada de datos por consola). También se implementa una **simulación de base de datos** para permitir la interacción con los datos almacenados.

## ¿Qué se utiliza en este proyecto?

Este proyecto hace uso de diversas características del lenguaje Java, incluyendo:

- **Clases y Objetos**: Se utilizan clases como `Departamentos`, `Especialidades`, y `Trabajadores` para modelar las entidades principales del sistema.
- **Colecciones**: Se emplea la clase `ArrayList` para gestionar listas dinámicas de objetos (departamentos, especialidades, trabajadores).
- **Métodos**: Cada acción del sistema (listar, crear, modificar, eliminar) se realiza a través de métodos específicos que manejan la lógica de negocio.
- **Excepciones**: Se gestionan excepciones comunes como `IOException` y `NumberFormatException` para manejar posibles errores en la entrada de datos y asegurar que el programa funcione correctamente.
- **Entrada y salida de datos**: Utiliza la clase `BufferedReader` para recibir datos del usuario desde la consola y mostrar resultados formateados.

### Interacción con la Base de Datos

Cada operación de modificación (crear, modificar, eliminar) se realiza a través de métodos en la clase `ConectorBDH`, que simula la interacción con una base de datos. Aunque no está conectada a una base de datos real en este momento, el proyecto está preparado para que puedas integrar fácilmente una base de datos real.

## El enunciado del ejercicio es:
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
