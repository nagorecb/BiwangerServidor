# Biwanger Fantasy

Biwanger Fantasy pretende ser un videojuego en el que cada jugador se convierte en el gestor
de un equipo virtual, pero enlazando con las actuaciones de los jugadores en la realidad.


### Comandos a realizar para conectar servidor con cliente 📋

_Introducir los siguientes comandos, para conectarse con el cliente, (hacer antes que cliente):_

```
mvn compiler:compile
```

_Para la creación, uso y borrado de la Base de datos:_
_ -->create para crear tablas, delete para borrarlas_
```
mvn datanucleus:enhance
mvn datanucleus:schema-create             
mvn datanucleus:schema-delete
mvn datanucleus:schema-deletecreate //ambos juntos       
```

```
mvn jetty:run
```

_*¡Truco del almendruco!* ¡Se pueden juntar todos los comandos en una línea!_

```
mvn compiler:compile datanucleus:enhance jetty:run
```
_En este momento nos quedamos a la espera del cliente_

### Para probar los test 📋

```
mvn test
```