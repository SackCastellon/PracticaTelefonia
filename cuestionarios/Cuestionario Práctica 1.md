
# Cuestionario práctica 1

1. ¿Cuantas clases has definido?

	* 10

2. ¿Cuantos interfaces has declarado?

	* 1

3. ¿Cuantos paquetes has creado?

	* 1

4. ¿Cuál es el número máximo de argumentos que has utilizado en un método?

	* 2

5. Escribe la declaración de un método con el número máximo de argumentos.

	```java
	public static void cambiarTarifa(String nif, TarifaTelefonica nuevaTarifa) {
		if (exixteCliente(nif))
			CLIENTES.get(nif).setTarifa(nuevaTarifa);
	}
	```

6. ¿Tienes algún método que no recibe argumentos? Si la respuesta es afirmativa escríbelo.

	```java
	public TarifaTelefonica getTarifa() { return this.tarifa; };
	```

7. ¿Todas tus clases tienen definido el constructor por defecto?

	* No

8. Si la pregunta anterior es no, escribe las clases que no tienen constructor por defecto y di por qué has decidido no definirlo.

	* `Administrador`: Porque es una clase con metodos estaticos que se encarga de administrar todas la operaciones
	* `ConsoleGUI`: Porque es una clase con metodos estaticos que sen ecrga de manejar la interfaz de la linea de comandos
	* `AppTelefonia`: Porque es la clase que contiene el metodo `main()`

9. ¿Cuál es el número de líneas de código que tiene tu método más largo?

	* 30

10. ¿Cuál es el número de líneas de código que tiene tu clase más larga?

	* 465 (incluyendo comentarios)

11. Escribe todas las clases que implemeten a algún interface y cual es.

	* `Cliente` implementa el interface `IFecha`
	* `FacturaTelefonica` implementa el interface `IFecha`

12. Escribe todas las clases que extiendan a otra clase y cual es.

	* `Particular` extiende la clase `Cliente`
	* `Empresa` extiende la clase `Cliente`

13. Escribe todas las clases que no implementen ningún interface ni extiendan a otra clase.

	* `TarifaTelefonica`
	* `DireccionPostal`
	* `Administrador`
	* `AppTelefonica`
	* `ConsoleGUI`

14. ¿Tienes algún atributo en alguna clase que no sea `private`? Escribe la clase y el atributo ¿Por qué has decidido que no sea `private`?

	* No

15. ¿Tienes algún método en alguna clase que sea `private`? Indica la clase y el método ¿Por qué has decidido que sea `private`?

	* Si, en `ConsoleGUI` por que son metodos especificos, usados para la comunicación con el usuario mediente la consola, y no necesitan usarse en ninguna otra clase

16. ¿Tienes algún método en alguna clase que sea `protected`? Indica la clase y el método ¿Por qué has decidido que sea `protected`?

	* No

17. ¿Tienes algún método en alguna clase sin modificador de acceso? Indica la clase y el método ¿Por qué has decidido que no tenga modificador de acceso?

	* No
