# Telefonia

<p align='justify'>Proyecto en lenguaje <b>Java</b> para la asignatura de <b>Programación Avanzada</b> de la <b>Universidad Jaume I</b></p>

### Autores
- Juan José González ([al341823](mailto:al341823@uji.es "al341823@uji.es"))
- David Agost ([al341819](mailto:al341819@uji.es "al341819@uji.es"))


## Práctica 1: Polimorfismo y Sobrecarga

### Enunciado

<p align='justify'>Desarrollar una aplicación de facturación para una empresa de telefonía móvil, la cual tiene una cartera de clientes. Los clientes pueden ser de dos tipos, o bien empresas, o bien particulares. Cada cliente tiene asignada una tarifa, que inicialmente codificarás como un valor numérico representando el precio por segundo, aunque ésto es algo que evolucionará en posteriores prácticas. De cada uno de los clientes se conoce su nombre, NIF, dirección, correo electrónico, fecha de alta y tarifa (€/min). Además, si el cliente es un particular también se conocen sus apellidos. En las direcciones figura: código postal, provincia y población.</p>

<p align='justify'>Dado que, como te hemos dicho, la forma de codificar las tarifas va a evolucionar en prácticas posteriores, será una buena idea definir una clase Tarifa. Inicialmente la clase sólo contendrá un dato numérico, pero en un futuro contendrá algo diferente. No obstante, si codificas la tarifa en forma de clase, los cambios futuros que tendrás que hacer serán menores.</p>

<p align='justify'>Los clientes efectúan llamadas, por lo que habrá que guardar una relación de todas las llamadas efectuadas. Para cada llamada guardaremos el número de teléfono al que se llamó, la fecha y hora de la llamada y la duración.</p>

<p align='justify'>Cada cliente tiene un conjunto de facturas, cada factura tiene asignada la tarifa en el momento de emitir la factura, aunque un cliente puede cambiar de tarifa cuando lo desee. Por simplificar, el cambio de tarifa se verá reflejado en la siguiente factura. Cada factura tiene asociados: un código único que no puede poseer ninguna otra factura, la tarifa aplicada  (€/min), la fecha de emisión de la factura, el periodo de facturación (de qué fecha a qué fecha) y el importe de la misma. El importe de la factura se calcula a partir de la suma de minutos de las llamadas que ha efectuado el cliente durante el periodo de facturación, y de la tarifa.</p>

<p align='justify'>De cara a la sesión que dedicaremos a Genericidad, te pedimos que las clases Cliente, Factura y Llamada dispongan de un método llamado getFecha(). En el caso de los clientes, este método retornará la fecha de alta del cliente, mientras que en el caso de las facturas, devolverá la fecha de emisión de la factura, y en el caso de las llamadas, devolverá la fecha en la que ésta se efectuó.</p>

### Metodología

<p align='justify'>Con respecto a las acciones que se pueden llevar a cabo en la aplicación y de las que deberá existir alguna opción de menú, para los clientes se deberá poder:</p>

- Dar de alta un nuevo cliente.
- Borrar un cliente.
- Cambiar la tarifa de un cliente.
- Recuperar los datos de un cliente a partir de su NIF.
- Recuperar el listado de todos los clientes.

<p align='justify'> Con respecto a las llamadas:</p>

- Dar de alta una llamada:
- Listar todas las llamadas de un cliente.
- Con respecto a las facturas:

<p align='justify'>Emitir una factura para un cliente, calculando el importe de la misma en función de las llamadas:</p>

- Recuperar los datos de una factura a partir de su código.
- Recuperar todas las facturas de un cliente.

### Diagrama de clases:

![Diagrama Simple](./diagrams/DiagramaSimple.png "Diagrama Simple")

### Cuestionario

