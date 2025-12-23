# JDBC

* JDBC significa Java Database Connectivity. Este capítulo te introduce a los conceptos básicos de acceder a bases de datos desde Java. 
* Cubrimos las interfaces clave para cómo conectarse, realizar consultas, procesar los resultados y trabajar con transacciones.

## Introducing Relational Databases and SQL

* Data es información. Una pieza de data es un hecho, como tu primer nombre. 
* Una database es una colección organizada de data. 
* En el mundo real, un archivador es un tipo de base de datos. 
* Tiene carpetas de archivo, cada una de las cuales contiene piezas de papel. 
* Las carpetas de archivo están organizadas de alguna manera, a menudo alfabéticamente. 
* Cada pieza de papel es como una pieza de data. De manera similar, las carpetas en tu computadora son como una base de datos. 
* Las carpetas proporcionan organización, y cada archivo es una pieza de data.

* Una relational database es una base de datos que está organizada en tablas, que consisten de filas y columnas. 
* Puedes pensar en una tabla como una hoja de cálculo. Hay dos formas principales de acceder a una base de datos relacional desde Java:
  * Java Database Connectivity (JDBC): Accede a data como filas y columnas. JDBC es la API cubierta en este capítulo.
  * Java Persistence API (JPA): Accede a data a través de objetos Java usando un concepto llamado object-relational mapping (ORM). 

* La idea es que no tienes que escribir tanto código, y obtienes tu data en objetos Java. 
* JPA no está en el examen, y por lo tanto no está cubierto en este capítulo.

* Una base de datos relacional se accede a través de Structured Query Language (SQL). 
* SQL es un lenguaje de programación usado para interactuar con registros de bases de datos. 
* JDBC funciona enviando un comando SQL a la base de datos y luego procesando la respuesta.

* Además de las bases de datos relacionales, hay otro tipo de base de datos llamada NoSQL database. 
* Estas bases de datos almacenan su data en un formato distinto a tablas, como key/value, document stores, y bases de datos basadas en grafos. 
* NoSQL está fuera del alcance del examen también.

* En las siguientes secciones, introducimos una pequeña base de datos relacional que usaremos para los ejemplos en este capítulo y presentamos el SQL para acceder a ella. 
* También cubrimos algo de vocabulario que necesitas conocer.

### Identifying the Structure of a Relational Database

* Nuestra base de datos de ejemplo tiene dos tablas. Una tiene una fila por cada especie que está en nuestro zoológico. 
* La otra tiene una fila por cada animal. Estas dos se relacionan entre sí porque un animal pertenece a una especie. 
* Estas relaciones son por qué este tipo de base de datos se llama una base de datos relacional. 
* Figure 15.1 muestra la estructura de nuestra base de datos.

![ch15_01_01.png](images/ch15/ch15_01_01.png)

* Como puedes ver en Figure 15.1, tenemos dos tablas. Una se llama exhibits, y la otra se llama names. 
* Cada tabla tiene una primary key, que nos da una forma única de referenciar cada fila. 
* Después de todo, dos animales podrían tener el mismo nombre, pero no pueden tener el mismo ID. 
* No necesitas saber sobre keys para el examen. Los mencionamos para darte un poco de contexto. 
* En nuestro ejemplo, sucede que la primary key es solo una columna. En algunas situaciones, es una combinación de columnas llamada compound key. 
* Por ejemplo, un identificador de estudiante y un año podrían ser una compound key.

* Hay dos filas y tres columnas en la tabla exhibits y cinco filas y tres columnas en la tabla names. 
* Necesitas saber sobre filas y columnas para el examen.

### Writing Basic SQL Statements

* Lo más importante que necesitas saber sobre SQL para el examen es que hay cuatro tipos de statements para trabajar con la data en tablas. 
* Se les refiere como CRUD (Create, Read, Update, Delete). 
* Las palabras clave SQL no coinciden con el acrónimo, así que presta atención a la palabra clave SQL para cada una en Table 15.1.

![ch15_01_02.png](images/ch15/ch15_01_02.png)

* Eso es todo. No se espera que determines si los statements SQL son correctos. 
* No se espera que detectes errores de sintaxis en statements SQL. No se espera que escribas statements SQL. ¿Notas un tema?

* A diferencia de Java, las palabras clave SQL no distinguen entre mayúsculas y minúsculas. 
* Esto significa select, SELECT, y Select son todas equivalentes. Al igual que los tipos primitivos de Java, SQL tiene una cantidad de tipos de data. 
* La mayoría son autoexplicativos, como INTEGER. También hay DECIMAL, que funciona mucho como un double en Java. El más extraño es VARCHAR, que significa "variable character," que es como un String en Java. 
* La parte variable significa que la base de datos debería usar solo tanto espacio como necesite para almacenar el valor.

Aunque no tienes que saber cómo escribirlos, presentamos los cuatro statements SQL básicos en Table 15.2, ya que aparecen en muchas preguntas.

![ch15_01_03.png](images/ch15/ch15_01_03.png)

## Introducing the Interfaces of JDBC

* Para el examen, necesitas conocer cinco interfaces clave de JDBC. Las interfaces están declaradas en el JDK. 
* Son justo como todas las otras interfaces y clases que has visto en este libro. 
* Por ejemplo, en Chapter 9, "Collections and Generics," trabajaste con la interface List y la clase concreta ArrayList.

* Con JDBC, las clases concretas vienen del driver JDBC. Cada base de datos tiene un archivo JAR diferente con estas clases. 
* Por ejemplo, el JAR de PostgreSQL se llama algo como postgresql-9.4–1201.jdbc4.jar. 
* El JAR de MySQL se llama algo como mysql-connector-java-5.1.36.jar. El nombre exacto depende del vendor y la versión del JAR del driver.

* Este JAR del driver contiene una implementación de estas interfaces clave junto con una cantidad de otras interfaces. 
* La clave es que las implementaciones provistas saben cómo comunicarse con una base de datos. 
* También hay diferentes tipos de drivers; afortunadamente, no necesitas saber sobre esto para el examen.

* Figure 15.2 muestra las cinco interfaces clave que necesitas conocer. 
* También muestra que la implementación es provista por un driver JAR imaginario Foo. Inteligentemente, pegan el nombre Foo en todas las clases.

* Probablemente, has notado que no te dijimos cómo se llaman las clases de implementación en ninguna base de datos real. 
* El punto principal es que no deberías saberlo. Con JDBC, usas solo las interfaces en tu código y nunca las clases de implementación directamente. 
* De hecho, podrían ni siquiera ser clases públicas.

¿Qué hacen estas cinco interfaces? A un nivel muy alto, tenemos lo siguiente:

* Driver: Establece una conexión a la base de datos
* Connection: Envía comandos a una base de datos
* PreparedStatement: Ejecuta una query SQL
* CallableStatement: Ejecuta comandos almacenados en la base de datos
* ResultSet: Lee los resultados de una query

![ch15_01_04.png](images/ch15/ch15_01_04.png)

Todas las interfaces de base de datos están en el paquete `java.sql`, así que a menudo omitimos los imports a lo largo de este capítulo.

* En este siguiente ejemplo, te mostramos cómo se ve el código JDBC, de principio a fin. 
* Si eres nuevo en JDBC, solo nota que tres de las cinco interfaces están en el código. 
* Si eres experimentado, recuerda que el examen usa la clase DriverManager en lugar de la interface DataSource.

```java
public class MyFirstDatabaseConnection {
  public static void main(String[] args) throws SQLException {
    String url = "jdbc:hsqldb:file:zoo";
    try (Connection conn = DriverManager.getConnection(url);
         PreparedStatement ps = conn.prepareStatement(
                 "SELECT name FROM exhibits");
         ResultSet rs = ps.executeQuery()) {
        while (rs.next())
            System.out.println(rs.getString(1));
    }}}
```

* Si el URL estuviera usando nuestro driver imaginario Foo, DriverManager retornaría una instancia de FooConnection. 
* Llamar a prepareStatement() entonces retornaría una instancia de FooPreparedStatement, y llamar a executeQuery() retornaría una instancia de FooResultSet. 
* Dado que el URL usa hsqldb en su lugar, retorna las implementaciones que HyperSQL ha provisto para estas interfaces. 
* No necesitas saber sus nombres. En el resto del capítulo, explicamos cómo usar las cinco interfaces y entramos en más detalle sobre lo que hacen. 
* Al final del capítulo, estarás escribiendo código como este tú mismo.

---------------------------------------------------------------------
**Compiling with Modules**

* Casi todos los paquetes en el examen están en el módulo `java.base`. 
* Como podrías recordar de Chapter 12, "Modules," este módulo se incluye automáticamente cuando ejecutas tu aplicación como un módulo.

* En contraste, las clases JDBC están todas en el módulo `java.sql`. También están en el paquete `java.sql`. 
* Los nombres son los mismos, así que deberían ser fáciles de recordar. Cuando trabajas con SQL, necesitas el módulo `java.sql` y hacer `import java.sql.*`.

* Recomendamos separar tus estudios para JDBC y módulos. 
* Puedes usar el classpath cuando trabajas con JDBC y reservar tu práctica con el module path para cuando estés estudiando módulos.

Dicho eso, si quieres usar código JDBC con módulos, recuerda actualizar tu archivo module-info para incluir lo siguiente:

`requires java.sql;`
---------------------------------------------------------------------

## Connecting to a Database

* El primer paso para hacer cualquier cosa con una base de datos es conectarse a ella. 
* Primero te mostramos cómo construir el JDBC URL. 
* Luego te mostramos cómo usarlo para obtener una Connection a la base de datos.

### Building a JDBC URL

* Para acceder a un sitio web, necesitas conocer su URL. Para acceder a tu correo electrónico, necesitas conocer tu nombre de usuario y contraseña. 
* JDBC no es diferente. Para acceder a una base de datos, necesitas conocer esta información sobre ella.

* A diferencia de los URL web, los URL JDBC tienen una variedad de formatos. 
* Tienen tres partes en común, como se muestra en Figure 15.3. La primera pieza es siempre la misma. 
* Es el protocolo jdbc. La segunda parte es el subprotocol, que es el nombre de la base de datos, como hsqldb, mysql, o postgres. 
* La tercera parte es el subname, que es un formato específico de la base de datos. Dos puntos `(:)` separan las tres partes.

* El subname típicamente contiene información sobre la base de datos como su ubicación y/o nombre. La sintaxis varía. 
* Necesitas conocer sobre las tres partes principales. No necesitas memorizar los formatos de subname. ¡Uf! Ya has visto un URL así:

`jdbc:hsqldb:file:zoo`

![ch15_01_05.png](images/ch15/ch15_01_05.png)

* Nota las tres partes. Comienza con jdbc, y luego viene el subprotocol hsqldb. 
* Termina con el subname, que nos dice que estamos usando el sistema de archivos. 
* La ubicación es entonces el nombre de la base de datos.

Otros ejemplos de subnames se muestran aquí:

```java
jdbc:postgresql://localhost/zoo
jdbc:oracle:thin:@123.123.123.123:1521:zoo
jdbc:mysql://localhost:3306
jdbc:mysql://localhost:3306/zoo?profileSQL=true
```

* Puedes ver que cada uno de estos URL JDBC comienza con jdbc, seguido por dos puntos, seguido por el nombre del vendor/producto. 
* Después de eso, los URL varían. Nota cómo todos ellos incluyen la ubicación de la base de datos: localhost, 123.123.123.123:1521, y localhost:3306. 
* También, nota que el puerto es opcional cuando se usa la ubicación predeterminada.

### Getting a Database Connection

* Hay dos formas principales de obtener una Connection: DriverManager y DataSource. DriverManager es el que se cubre en el examen.
* No uses un DriverManager en código que alguien te está pagando para escribir. Un DataSource tiene más características que DriverManager.
* Por ejemplo, puede hacer pool de conexiones o almacenar la información de conexión de la base de datos fuera de la aplicación.

* La clase DriverManager está en el JDK, ya que es una API que viene con Java. Usa el patrón factory, lo que significa que llamas a un método estático para obtener una Connection en lugar de llamar a un constructor. 
* Como viste en Chapter 11, "Exceptions and Localization," el patrón factory significa que puedes obtener cualquier implementación de la interface al llamar al método. 
* La buena noticia es que el método tiene un nombre fácil de recordar: getConnection().

Para obtener una Connection desde la base de datos HyperSQL, escribes lo siguiente:

```java
import java.sql.*;
public class TestConnect {
  public static void main(String[] args) throws SQLException {
      try (Connection conn =
                   DriverManager.getConnection("jdbc:hsqldb:file:zoo")) {
          System.out.println(conn);
      }}}
```

* Como en Chapter 11, usamos un statement try-with-resources para asegurar que los recursos de la base de datos se cierren. 
* Cubrimos el cierre de recursos de base de datos con más detalle más adelante en el capítulo. 
* También lanzamos el checked SQLException, que significa que algo salió mal. 
* Por ejemplo, podrías haber olvidado configurar la ubicación del driver de base de datos en tu classpath.

Asumiendo que el programa se ejecuta exitosamente, imprime algo como esto:

`org.hsqldb.jdbc.JDBCConnection@3dfc5fb8`

Los detalles del output no son importantes. Solo nota que la clase no es Connection. Es una implementación de vendor de Connection.

También hay una signature que toma un username y password.

```java
import java.sql.*;
public class TestExternal {
  public static void main(String[] args) throws SQLException {
    try (Connection conn = DriverManager.getConnection(
          "jdbc:postgresql://localhost:5432/ocp-book",
          "username",
          "Password20182")) {
      System.out.println(conn);
    }}}
```

* Nota los tres parámetros que se pasan a getConnection(). El primero es el JDBC URL que aprendiste en la sección anterior. 
* El segundo es el username para acceder a la base de datos, y el tercero es el password para acceder a la base de datos. 
* No hace falta decir que nuestro password no es Password20182. También, no pongas tu password en código real. 
* Es una práctica horrible. Siempre cárgala desde algún tipo de configuración, idealmente una que mantenga el valor almacenado encriptado.

Si fueras a ejecutar esto con el JAR del driver Postgres, imprimiría algo como esto:

`org.postgresql.jdbc4.Jdbc4Connection@eed1f14`

* De nuevo, nota que es una clase de implementación específica del driver. Puedes darte cuenta por el nombre del paquete. 
* Dado que el paquete es org.postgresql.jdbc4, es parte del driver PostgreSQL.

* A menos que el examen especifique una línea de comandos, puedes asumir que el JAR del driver JDBC correcto está en el classpath. 
* Los creadores del examen explícitamente preguntan sobre el JAR del driver si quieren que pienses sobre ello.

* Lo bueno sobre el patrón factory es que se encarga de la lógica de crear una clase por ti. 
* No necesitas conocer el nombre de la clase que implementa Connection, y no necesitas saber cómo se crea. 
* Probablemente, estés un poco curioso, sin embargo.

* DriverManager revisa a través de cualquier driver que pueda encontrar para ver si pueden manejar el JDBC URL. 
* Si es así, crea una Connection usando ese Driver. Si no, se rinde y lanza un SQLException.

Podrías ver `Class.forName()` en código. Se requería con drivers más antiguos (que fueron diseñados para versiones más antiguas de JDBC) antes de obtener una Connection.

## Working with a PreparedStatement

En Java, tienes una opción de trabajar con un Statement, PreparedStatement, o CallableStatement. 
Los últimos dos son subinterfaces of Statement, como se muestra en Figure 15.4.

![ch15_01_06.png](images/ch15/ch15_01_06.png)

* Más adelante en el capítulo, aprenderás sobre usar CallableStatement para queries que están dentro de la base de datos. 
* En esta sección, vemos PreparedStatement.

* ¿Qué hay de Statement, preguntas? Es una interface que tanto PreparedStatement como CallableStatement extienden. 
* Un Statement y un PreparedStatement son similares entre sí, excepto que un PreparedStatement toma parámetros, mientras que un Statement no. 
* Un Statement simplemente ejecuta cualquier query SQL que le des.

Aunque es posible ejecutar SQL directamente con Statement, no deberías. PreparedStatement es mucho superior por las siguientes razones:

* Performance: En la mayoría de los programas, ejecutas queries similares múltiples veces. Cuando usas PreparedStatement, el software de base de datos a menudo diseña un plan para ejecutar bien la query y lo recuerda.
* Security: Estás protegido contra un ataque llamado SQL injection cuando usas un PreparedStatement correctamente.
* Readability: Es agradable no tener que lidiar con concatenación de strings al construir un string de query con muchos parámetros.
* Future use: Incluso si tu query se está ejecutando solo una vez o no tiene ningún parámetro, deberías usar un PreparedStatement de todas formas. De esa manera, los editores futuros del código no agregarán una variable y tendrán que recordar cambiar a PreparedStatement entonces.

* Usar la interface Statement directamente no está en el alcance del examen JDBC, así que no lo cubrimos en este libro. 
* En las siguientes secciones, cubrimos obtener un PreparedStatement, ejecutar uno, trabajar con parámetros, y ejecutar múltiples updates.

### Obtaining a PreparedStatement

Para ejecutar SQL, necesitas decirle a un PreparedStatement sobre ello. Obtener un PreparedStatement desde una Connection es fácil.

```java
try (PreparedStatement ps = conn.prepareStatement(
  "SELECT * FROM exhibits")) {
  // work with ps
}
```

* Una instancia de un PreparedStatement representa un statement SQL que quieres ejecutar usando la Connection. 
* No ejecuta la query todavía. Llegamos a eso en breve.

Pasar un statement SQL cuando se crea el objeto es obligatorio. Podrías ver un truco en el examen.

```java
try (var ps = conn.prepareStatement()) { // DOES NOT COMPILE
}
```

* El ejemplo anterior no compila, porque SQL no se proporciona en el momento en que se solicita un PreparedStatement. 
* También usamos var en este ejemplo. Escribimos código JDBC tanto usando var como los nombres de clase reales para que te acostumbres a ambos enfoques.

* Hay signatures sobrecargadas que te permiten especificar un tipo ResultSet y modo de concurrencia. 
* En el examen, solo necesitas saber cómo usar las opciones predeterminadas, que procesan los resultados en orden.

### Executing a PreparedStatement

* Ahora que tenemos un PreparedStatement, podemos ejecutar el statement SQL. 
* El método para ejecutar SQL varía dependiendo de qué tipo de statement SQL es. 
* Recuerda que no se espera que seas capaz de leer SQL, pero sí necesitas saber qué significa la primera palabra clave.

### Modifying Data with executeUpdate()

* Empecemos con statements que cambian la data en una tabla. Esos son statements SQL que comienzan con DELETE, INSERT, o UPDATE. 
* Típicamente, usan un método llamado executeUpdate(). El nombre es un poco engañoso porque el statement SQL UPDATE no es el único statement que usa este método.

* El método toma el statement SQL para ejecutar como un parámetro. Retorna el número de filas que fueron insertadas, eliminadas, o cambiadas. 
* Aquí hay un ejemplo de los tres tipos de update:

```java
10: var insertSql = "INSERT INTO exhibits VALUES(10, 'Deer', 3)";
11: var updateSql = "UPDATE exhibits SET name = " " +
12:   "WHERE name = 'None'";
13: var deleteSql = "DELETE FROM exhibits WHERE id = 10";
14:
15: try (var ps = conn.prepareStatement(insertSql)) {
16:   int result = ps.executeUpdate();
17:   System.out.println(result); // 1
18: }
19:
20: try (var ps = conn.prepareStatement(updateSql)) {
21:   int result = ps.executeUpdate();
22:   System.out.println(result); // 0
23: }
24:
25: try (var ps = conn.prepareStatement(deleteSql)) {
26:   int result = ps.executeUpdate();
27:   System.out.println(result); // 1
28: }
```

* Para el examen, no necesitas leer SQL. La pregunta te dirá cuántas filas son afectadas si necesitas saberlo. 
* Nota cómo cada statement SQL distinto necesita su propia llamada a prepareStatement().

* La línea 15 crea el statement insert, y la línea 16 ejecuta ese statement para insertar una fila. 
* El resultado es 1 porque una fila fue afectada. La línea 20 crea el statement update, y la línea 21 verifica toda la tabla para registros coincidentes a actualizar. Dado que no hay registros coincidentes, el resultado es 0. 
* La línea 25 crea el statement delete, y la línea 26 elimina la fila creada en la línea 16. De nuevo, una fila es afectada, así que el resultado es 1.

### Reading Data with executeQuery()

A continuación, veamos un statement SQL que comienza con SELECT. Esta vez, usamos el método e`xecuteQuery()`.

```java
30: var sql = "SELECT * FROM exhibits";
31: try (var ps = conn.prepareStatement(sql);
32:   ResultSet rs = ps.executeQuery()) {
33:
34:   // work with rs
35: }
```

* En la línea 31, creamos un PreparedStatement para nuestra query SELECT. En la línea 32, lo ejecutamos. 
* Dado que estamos ejecutando una query para obtener un resultado, el tipo de retorno es ResultSet. 
* En la siguiente sección, te mostramos cómo procesar el ResultSet.

### Processing Data with execute()

* Hay un tercer método llamado execute() que puede ejecutar ya sea una query o un update. 
* Retorna un boolean para que sepamos si hay un ResultSet. De esa manera, podemos llamar al método apropiado para obtener más detalle. 
* El patrón luce así:

```java
boolean isResultSet = ps.execute();
if (isResultSet) {
  try (ResultSet rs = ps.getResultSet()) {
    System.out.println("ran a query");
  }
} else {
  int result = ps.getUpdateCount();
  System.out.println("ran an update");
}
```

* Si el PreparedStatement se refiere a sql que es un SELECT, el boolean es true, y podemos obtener el ResultSet. 
* Si no es un SELECT, podemos obtener el número de filas actualizadas.

### Using the Correct Method

¿Qué crees que pasa si usamos el método incorrecto para un statement SQL? Echemos un vistazo:

```java
var sql = "SELECT * FROM names";
try (var ps = conn.prepareStatement(sql)) {

  var result = ps.executeUpdate();
}
```

Esto lanza un SQLException similar al siguiente:

```java
Exception in thread "main" java.sql.SQLException:
statement does not generate a row count
```

* No podemos obtener un error del compilador dado que el SQL es un String. 
* Podemos obtener una exception, sin embargo, y sí la obtenemos. 
* También obtenemos un SQLException cuando usamos executeQuery() con SQL que cambia la base de datos.

```java
Exception in thread "main" java.sql.SQLException:
statement does not generate a result set
```

De nuevo, obtenemos una exception porque el driver no puede traducir la query al tipo de retorno esperado.

### Reviewing PreparedStatement Methods

* Para revisar, asegúrate de que conoces Table 15.3 y Table 15.4 bien. 
* Table 15.3 muestra qué statements SQL pueden ser ejecutados por cada uno de los tres métodos clave en PreparedStatement. 
* Table 15.4 muestra qué es retornado por cada método.

![ch15_01_07.png](images/ch15/ch15_01_07.png)

![ch15_01_08.png](images/ch15/ch15_01_08.png)

### Working with Parameters

Supongamos que nuestro zoológico adquiere un nuevo elefante y queremos registrarlo en nuestra tabla names. 
Ya hemos aprendido suficiente para hacer esto.

```java
public static void register(Connection conn) throws SQLException {
  var sql = "INSERT INTO names VALUES(6, 1, 'Edith')";
  
  try (var ps = conn.prepareStatement(sql)) {
    ps.executeUpdate();
  }
}
```

* Sin embargo, todo está hard-coded. Queremos poder pasar los valores como parámetros. 
* Afortunadamente, un PreparedStatement nos permite configurar parámetros. 
* En lugar de especificar los tres valores en el SQL, podemos usar un signo de interrogación `(?)`. 
* Un bind variable es un placeholder que te permite especificar los valores reales en runtime. 
* Una bind variable es como un parámetro, y verás que las bind variables se referencian tanto como variables como parámetros. 
* Podemos reescribir nuestro statement SQL usando bind variables.
















```java

```

---------------------------------------------------------------------
Getting Data from a ResultSet
Calling a CallableStatement
Controlling Data with Transactions
Closing Database Resources
