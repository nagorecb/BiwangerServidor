# Biwanger Fantasy

Biwanger Fantasy pretende ser un videojuego en el que cada jugador se convierte en el gestor de un equipo virtual, pero enlazando con las actuaciones de los jugadores en la realidad.

Este repositorio contiene la parte de servidor del juego en cuestión. La parte de cliente está en el [repositorio de cliente](https://github.com/nagorecb/BiwangerCliente).

### Comandos a realizar para conectar servidor con cliente 🚀

_Introducir los siguientes comandos, para compilar_:
```sh
$ mvn clean
$ mvn compile
```

_Para la creación, uso y borrado de la Base de datos:
```sh
mvn datanucleus:enhance
mvn datanucleus:schema-create             
mvn datanucleus:schema-delete
mvn datanucleus:schema-deletecreate //ambos juntos       
```

A continuación, para el correcto funcionamiento del servidor, se deben realizar dos ejecuciones:
1. Ejecutar el servidor REST 
```sh
mvn jetty:run
```
2. En otra consola, ejecutar el profile del hilo que controla la gestión de las pujas
```sh
mvn exec:java -PHilo
```
_En este momento, el servidor queda a la espera del cliente_

### ⚠️IMPORTANTE:⚠️

Se ha añadido otro profile además del anteriormente mencionado para poblar la Base de datos para tener datos iniciales con los que poder ir probando la aplicación sin tener que ir probando el programa desde cero. Por tanto, antes de empezar a probar, se recomienda utilizar la siguiente línea en caso de querer utilizar dichos datos:

```sh
mvn clean compile datanucleus:enhance datanucleus:schema-deletecreate exec:java -PDatos
```

Una vez hecho esto, se podrá probar la parte administradora con las credenciales email: "ADMIN" y contraseña: "ADMIN" (esto es así siempre, independientemente de cómo se pueble la base de datos) y la parte usuario con las credenciales email: "usuario@usuario.es" y contraseña: "123". Este usuario tendrá ya asignados suficientes jugadores como para poder realizar con él todas las operaciones.

 ### Test 🔎

Se han realizado testeos de todo el proyecto. Para ello, se han utilizado las siguientes herramientas:

| Herramienta | Uso |
| --- | --- |
| [JUnit 4](https://junit.org/junit4/) | Realización de test unitarios |
| [Mockito](https://site.mockito.org/) | Simulación de la parte servidora en los test |
| [Contiperf](https://mvnrepository.com/artifact/org.databene/contiperf) | Evaluación del rendimiento de las partes críticas |
| [JaCoCo](https://www.eclemma.org/jacoco/) |Comprobación de la cobertura de código de los test |
| [Travis CI](https://travis-ci.org/) |Para integración continua |


Para ejecutar dichos test, utilizamos el comando:
```sh
$ mvn test
```
Para la ejecución de JaCoCo según el límite establecido en el POM.xml:
```sh
$ mvn test jacoco:check
```
Para ver los resultados de los test de una marea más visual, se puede utilizar:
```sh
$ mvn site
```
Este comando se explicará a continuación en el punto de documentación.

### Documentación 📋

Para la realización de la documentación, se ha utilizado el plugin de maven [Doxygen](http://doxygen.nl/) y comentarios [JavaDoc](https://docs.oracle.com/javase/8/docs/technotes/tools/windows/javadoc.html). Para ello, se cuenta de el archivo de configuración Doxyfile en la carpeta de [resources](https://github.com/nagorecb/BiwangerServidor/tree/master/src/main/resources), con la configuración de la documentación a generar.

Para generar dicha documentación, se debe ejecutar el comando de site:
```sh
$ mvn site
```
Este comando hará lo siguiente:
* Genera, en la carpeta target, dashboards para los plugins añadidos en la parte de reporting del POM (en la carpeta site): 

        - JaCoCo
        - Doxygen

* Genera la documentación completa en la carpeta doc, de manera que pueda ser accedida desde el repositorio de GitHb, y desde la página http://nagorecb.github.io/BiwangerServidor.

### Equipo 💻

Se ha desarrollado el proyecto mediante la metodología ágil SCRUM, utilizando la herramienta de planificación de [YouTrack](https://www.jetbrains.com/youtrack/promo/?gclid=CjwKCAiA3abwBRBqEiwAKwICAx-iax3CyO-rPM1nMA33Wd8NCXAqCKvi4mXVZ0AnIu7QBkPE68GhqxoCLIkQAvD_BwE). Los colaboradores han sido:

* Nagore Beltrán, [nagorecb](https://github.com/nagorecb)
* Maider Calzada Aláez, [MaiderC](https://github.com/MaiderC)
* Sara Olaizola Alcántara, [saraolaizola](https://github.com/saraolaizola)
* Hodei Olaskoaga Apezetxea, [HodeiOA](https://github.com/HodeiOA)

