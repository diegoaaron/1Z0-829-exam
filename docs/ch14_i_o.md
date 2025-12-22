# I/O

Este capítulo se enfoca en usar APIs de I/O (entrada/salida) y NIO.2 (I/O no bloqueante) para interactuar con archivos y streams de I/O. 
El enfoque preferido para trabajar con archivos y directorios en aplicaciones de software más nuevas es usar NIO.2 en lugar de I/O donde sea posible.

---------------------------------------------------------------------
El texto dice: NIO significa non-blocking input/output API y a veces se refiere como new I/O. El examen cubre NIO versión 2. 
Hubo una versión 1 que cubría canales, pero no está en el examen.
---------------------------------------------------------------------

## Referencing Files and Directories

* Termina con el inicio de un párrafo que dice: Comenzamos este capítulo revisando qué son los archivos y directorios dentro de un sistema de archivos sistema. 
* También presentamos la clase File y la interfaz Path junto con cómo crearlas.

### Conceptualizando el Sistema de Archivos

* Comenzamos con lo básico. Los datos se almacenan en dispositivos de almacenamiento persistente, como discos duros y tarjetas de memoria. 
* Un file dentro del dispositivo de almacenamiento contiene datos. Los archivos se organizan en jerarquías usando directorios. 
* Un directory es una ubicación que puede contener archivos así como otros directorios. 
* Cuando trabajamos con directorios en Java, frecuentemente los tratamos como archivos. 
* De hecho, usamos muchas de las mismas clases e interfaces para operar sobre archivos y directorios. 
* Por ejemplo, un archivo y un directorio pueden ser renombrados con el mismo método Java. 
* Nota que a menudo decimos file para significar file or directory en este capítulo.

* Para interactuar con archivos, necesitamos conectarnos al sistema de archivos. 
* El file system está a cargo de leer y escribir datos dentro de una computadora. 
* Diferentes sistemas operativos usan diferentes sistemas de archivos para gestionar sus datos. 
* Por ejemplo, los sistemas basados en Windows usan un sistema de archivos diferente que los basados en Unix. 
* Para el examen, solo necesitas saber cómo emitir comandos usando las APIs de Java. 
* La JVM se conectará automáticamente al sistema de archivos local, permitiéndote realizar las mismas operaciones a través de múltiples plataformas.

* A continuación, el root directory es el directorio superior en el sistema de archivos, del cual todos los archivos y directorios heredan. 
* En Windows, se denota con una letra de unidad como C:, mientras que en Linux, se denota con una sola barra diagonal, /.

* Un path es una representación de un archivo o directorio dentro de un sistema de archivos. 
* Cada sistema de archivos define su propio carácter separador de ruta que se usa entre entradas de directorio. 
* El valor a la izquierda de un separador es el padre del valor a la derecha del separador. 
* Por ejemplo, el valor de ruta /user/home/zoo.txt significa que el archivo zoo.txt está dentro del directorio home, con el directorio home dentro del directorio user.

---------------------------------------------------------------------
**Separadores de Archivo del Sistema Operativo**
* Diferentes sistemas operativos varían en su formato de nombres de ruta. 
* Por ejemplo, los sistemas basados en Unix usan la barra diagonal, /, para rutas, mientras que los sistemas basados en Windows usan la barra invertida, /, carácter. 
* Dicho esto, muchos lenguajes de programación y sistemas de archivos soportan ambos tipos de barras cuando se escriben declaraciones de ruta. 
* Java ofrece una propiedad del sistema para recuperar el carácter separador local para el entorno actual:
`System.out.print(System.getProperty("file.separator"));`
---------------------------------------------------------------------

Mostramos cómo un directorio y sistema de archivos está organizado de manera jerárquica en Figure 14.1.

![ch14_01_01.png](images/ch14/ch14_01_01.png)

* Este diagrama muestra el directorio raíz, c:, como conteniendo dos directorios, app y zoo, junto con el archivo info.txt. 
* Dentro del directorio app, hay dos carpetas más, animals y employees, junto con el archivo `java.exe`. Finalmente, el directorio animals contiene dos archivos, Bear.java y Bear.class.

* Usamos tanto rutas absolutas como relativas al archivo o directorio dentro del sistema de archivos. 
* La absolute path de un archivo o directorio es la ruta completa desde el directorio raíz al archivo o directorio, incluyendo todos los subdirectorios que contienen el archivo o directorio. 
* Alternativamente, la relative path de un archivo o directorio es la ruta desde el directorio de trabajo actual al archivo o directorio. 
* Por ejemplo, lo siguiente es una ruta absoluta al archivo Bear.java:

`C:\app\animals\Bear.java`

Lo siguiente es una ruta relativa al mismo archivo, asumiendo que el directorio actual del usuario está establecido en `C:\app`:

`animals\Bear.java`

* Determinar si una ruta es relativa o absoluta es dependiente del sistema de archivos. 
* Para coincidir con el examen, adoptamos las siguientes convenciones:

1. Si una ruta comienza con una barra diagonal `(/)`, es absoluta, con `/` como el directorio raíz, tal como `/bird/parrot.png`.
2. Si una ruta comienza con una letra de unidad `(c:)`, es absoluta, con la letra de unidad como el directorio raíz, tal como `C:/bird/info`.
3. De lo contrario, es una ruta relativa, tal como `bird/parrot.png`.

* Las rutas absolutas y relativas pueden contener símbolos de ruta. 
* Un path symbol es una serie reservada de caracteres con significado especial en algunos sistemas de archivos. 
* Para el examen, hay dos símbolos de ruta que necesitas conocer, como se lista en Table 14.1.

![ch14_01_02.png](images/ch14/ch14_01_02.png)

* Mirando a Figure 14.2, supón que el directorio actual es `/fish/shark/hammerhead`. 
* En este caso, `../swim.txt` es una ruta relativa válida equivalente a `/fish/shark/swim.txt`. 
* De igual manera, `./play.png` se refiere a `play.png` en el directorio actual. 
* Estos símbolos también pueden ser combinados para mayor efecto. 
* Por ejemplo, `../../clownfish` es una ruta relativa equivalente a `/fish/clownfish` dentro del sistema de archivos.

* A veces verás símbolos de ruta que son redundantes o innecesarios. 
* Por ejemplo, la ruta absoluta `/fish/clownfish/../shark/./swim.txt` puede ser simplificada a `/fish/shark/swim.txt`. 
* Vemos cómo manejar estas redundancias más adelante en el capítulo cuando cubramos `normalize()`.

![ch14_01_03.png](images/ch14/ch14_01_03.png)

* Un symbolic link es un archivo especial dentro de un sistema de archivos que sirve como una referencia o puntero a otro archivo o directorio. 
* Supón que tenemos un enlace simbólico desde `/zoo/user/favorite` a `/fish/shark`. La carpeta shark y sus elementos pueden ser accedidos directamente o vía el enlace simbólico. 
* Por ejemplo, las siguientes rutas referencian el mismo archivo:

```java
/fish/shark/swim.txt
/zoo/user/favorite/swim.txt
```

* En general, los enlaces simbólicos son transparentes para el usuario, ya que el sistema operativo se encarga de resolver la referencia al archivo real. 
* Mientras las APIs de I/O no soportan enlaces simbólicos, NIO.2 incluye soporte completo para crear, detectar y navegar enlaces simbólicos dentro del sistema de archivos.

### Creating a File or Path

* Para hacer cualquier cosa útil, primero necesitas un objeto que represente la ruta a un archivo o directorio particular en el sistema de archivos. 
* Usando I/O legacy, esta es la clase `java.io.File`, mientras que con NIO.2, es la interfaz `java.nio.file.Path`. 
* La clase File y la interfaz Path no pueden leer o escribir datos dentro de un archivo, aunque son pasadas como una referencia a otras clases, como verás en este capítulo.

### Creating a File

La clase File es creada llamando a su constructor. Este código muestra tres constructores diferentes:

```java
File zooFile1 = new File("/home/tiger/data/stripes.txt");
File zooFile2 = new File("/home/tiger", "data/stripes.txt");

File parent = new File("/home/tiger");
File zooFile3 = new File(parent, "data/stripes.txt");

System.out.println(zooFile1.exists());
```

* Los tres crean un objeto File que apunta a la misma ubicación en el disco. 
* Si pasamos null como el padre al constructor final, sería ignorado, y el método se comportaría de la misma manera que el constructor de String único. 
* Por diversión, también mostramos cómo decir si el archivo existe en el sistema de archivos.

### Creating a Path

* Dado que Path es una interfaz, no podemos crear una instancia directamente. 
* Después de todo, ¡las interfaces no tienen constructores! Java proporciona un número de clases y métodos que puedes usar para obtener objetos Path.
* La manera más simple y directa de obtener un objeto Path es usar un método factory estático definido en Path o Paths. 
* Los cuatro de estos ejemplos apuntan a la misma referencia en el disco:

```java
Path zooPath1 = Path.of("/home/tiger/data/stripes.txt");
Path zooPath2 = Path.of("/home", "tiger", "data", "stripes.txt");

Path zooPath3 = Paths.get("/home/tiger/data/stripes.txt");
Path zooPath4 = Paths.get("/home", "tiger", "data", "stripes.txt");

System.out.println(Files.exists(zooPath1));
```

* Ambos métodos permiten pasar un parámetro varargs para pasar elementos de ruta adicionales. 
* Los valores son combinados y automáticamente separados por el separador de archivo dependiente del sistema operativo. 
* También mostramos la clase helper Files, que puede verificar si el archivo existe en el sistema de archivos.

* Como puedes ver, hay dos maneras de hacer lo mismo aquí. El método Path.of() fue introducido en Java 11 como un método estático en la interfaz. 
* La clase factory Paths también proporciona un método get() para hacer lo mismo. Nota la `s` al final de la clase Paths para distinguirla de la interfaz Path. 
* Usamos Path.of() y Paths.get() intercambiablemente en este capítulo.

---------------------------------------------------------------------
* Podrías notar que tanto las clases I/O como NIO.2 pueden interactuar con una URI. 
* Un uniform resource identifier (URI) es una cadena de caracteres que identifica un recurso. 
* Comienza con un esquema que indica el tipo de recurso, seguido por un valor de ruta tal como file:// para sistemas de archivos locales y http://, https://, y ftp:// para sistemas de archivos remotos.
---------------------------------------------------------------------

### Switching between File and Path

* Dado que File y Path ambos referencian ubicaciones en el disco, es útil ser capaz de convertir entre ellos. 
* Afortunadamente, Java hace esto fácil proporcionando métodos para hacer justo eso:

```java
File file = new File("rabbit");
Path nowPath = file.toPath();
File backToFile = nowPath.toFile();
```

* Muchas librerías antiguas usan File, haciendo conveniente ser capaz de obtener un File desde un Path y viceversa. 
* Cuando trabajas con aplicaciones más nuevas, deberías confiar en la interfaz Path de NIO.2, ya que contiene muchas más características. 
* Por ejemplo, solo NIO.2 proporciona soporte FileSystem, como estamos a punto de discutir.

### Obtaining a Path from the FileSystems Class

* NIO.2 hace uso extensivo de crear objetos con clases factory. La clase FileSystems crea instancias de la clase abstracta FileSystem. 
* Esta última incluye métodos para trabajar con el sistema de archivos directamente. Tanto Paths.get() como Path.of() son atajos para este método FileSystem. 
* Reescribamos nuestros ejemplos anteriores una vez más para ver cómo obtener una instancia Path de la manera larga:

```java
Path zooPath1 = FileSystems.getDefault()
    .getPath("/home/tiger/data/stripes.txt");
Path zooPath2 = FileSystems.getDefault()
    .getPath("/home", "tiger", "data", "stripes.txt");
```

### Reviewing I/O and NIO.2 Relationships

* El modelo para I/O es más pequeño, y solo necesitas entender la clase File. 
* En contraste, NIO.2 tiene más características y hace uso extensivo del patrón factory. Deberías familiarizarte con este enfoque. 
* Muchas de tus interacciones con NIO.2 requerirán dos tipos: una clase o interfaz abstracta y una clase factory o helper. 
* Figure 14.3 muestra las relaciones entre las clases e interfaz que hemos usado en este capítulo hasta ahora.

![ch14_01_04.png](images/ch14/ch14_01_04.png)

* Revisa Figure 14.3 cuidadosamente. En particular, mantén un ojo en sí el nombre de la clase es singular o plural. 
* Las clases con nombres plurales incluyen métodos para crear u operar sobre instancias de clase/interfaz con nombres singulares. 
* Recuerda, como una conveniencia (y fuente de confusión), un Path también puede ser creado desde la interfaz Path usando el método factory estático of().

---------------------------------------------------------------------
* El `java.io.File` es la clase I/O, mientras Files es una clase helper NIO.2. Files opera sobre instancias Path, no instancias `java.io.File`. 
* Sabemos que esto es confuso, ¡pero son de APIs completamente diferentes!
---------------------------------------------------------------------

* Table 14.2 revisa las APIs que hemos cubierto para crear objetos `java.io.File` y `java.nio.file.Path`. 
* Cuando lees la tabla, recuerda que los métodos estáticos operan sobre la clase/interfaz, mientras los métodos de instancia requieren una instancia de un objeto. 
* Asegúrate de saber esto bien antes de proceder con el resto del capítulo.

![ch14_01_05.png](images/ch14/ch14_01_05.png)

## Operating on File and Path

Ahora que sabemos cómo crear objetos File y Path, podemos comenzar a usarlos para hacer cosas útiles. 
En esta sección, exploramos la funcionalidad disponible para nosotros que involucra directorios.

### Using Shared Functionality

* Muchas operaciones pueden ser hechas usando tanto las librerías I/O como NIO.2. 
* Presentamos muchas APIs comunes en Table 14.3 y Table 14.4. 
* Aunque estas tablas pueden parecer como muchos métodos para aprender, muchos de ellos son autoexplicativos. 
* Puedes ignorar los parámetros varargs por ahora. Los explicamos más adelante en el capítulo.

![ch14_01_06.png](images/ch14/ch14_01_06.png)

![ch14_01_07.png](images/ch14/ch14_01_07.png)

* Ahora intentemos usar algunas de estas APIs. Lo siguiente es un programa de muestra usando solo APIs I/O legacy. 
* Dada una ruta de archivo, genera información sobre el archivo o directorio, tal como si existe, qué archivos están contenidos dentro de él, y así sucesivamente:

```java
10: public static void io() {
11:   var file = new File("C:\\data\\zoo.txt");
12:   if (file.exists()) {
13:     System.out.println("Absolute Path: " + file.getAbsolutePath());
14:     System.out.println("Is Directory: " + file.isDirectory());
15:     System.out.println("Parent Path: " + file.getParent());
16:     if (file.isFile()) {
17:       System.out.println("Size: " + file.length());
18:       System.out.println("Last Modified: " + file.lastModified());
19:     } else {
20:       for (File subfile : file.listFiles()) {
21:         System.out.println("  " + subfile.getName());
22:     } } } }
```

Si la ruta proporcionada apunta a un archivo válido, el programa genera algo similar a lo siguiente debido a la declaración if en la línea 16:

Absolute Path: C:\data\zoo.txt
Is Directory: false
Parent Path: C:\data
Size: 12382
Last Modified: 1650610000000

Finalmente, si la ruta proporcionada apunta a un directorio válido, tal como `C:\data`, el programa genera algo similar a lo siguiente, gracias al bloque else:

Absolute Path: C:\data
Is Directory: true
Parent Path: C:\
 employees.txt
 zoo.txt
 zoo-backup.txt

En estos ejemplos, ves que la salida de un programa basado en I/O es completamente dependiente de los directorios y archivos disponibles en tiempo de ejecución en el sistema de archivos subyacente.

* En el examen, podrías ver rutas que parecen archivos, pero son directorios o viceversa. 
* Por ejemplo, /data/zoo.txt podría ser un archivo o un directorio, incluso aunque tiene una extensión de archivo. 
* ¡No asumas que es alguno a menos que la pregunta te lo diga!

---------------------------------------------------------------------
En el ejemplo anterior, usamos dos barras invertidas `(\\)` en el String de ruta, tal como `C:\\data\\zoo.txt`. 
Cuando el compilador ve un `\\` dentro de una expresión String, lo interpreta como un valor `\` único.
---------------------------------------------------------------------

```java
25: public static void nio() throws IOException {
26:   var path = Path.of("C:\\data\\zoo.txt");
27:   if (Files.exists(path)) {
28:     System.out.println("Absolute Path: " + path.toAbsolutePath());
29:     System.out.println("Is Directory: " + Files.isDirectory(path));
30:     System.out.println("Parent Path: " + path.getParent());
31:     if (Files.isRegularFile(path)) {
32:       System.out.println("Size: " + Files.size(path));
33:       System.out.println("Last Modified: "
34:         + Files.getLastModifiedTime(path));
35:     } else {
36:       try (Stream<Path> stream = Files.list(path)) {
37:         stream.forEach(p ->
38:           System.out.println("  " + p.getName()));
39:     } } } }
```

* La mayor parte de este ejemplo es equivalente y reemplaza las llamadas al método I/O en las tablas anteriores con las versiones NIO.2. 
* Sin embargo, hay diferencias clave. Primero, la línea 25 declara una excepción chequeada. Más APIs en NIO.2 lanzan IOException que las APIs I/O. 
* En este caso, Files.size(), Files.getLastModifiedTime(), y Files.list() lanzan una IOException.

* Segundo, las líneas 36-39 usan un Stream y una lambda en lugar de un loop. 
* Dado que los streams usan evaluación lazy, esto significa que el método cargará cada elemento de ruta según sea necesario, en lugar del directorio completo de una vez.

---------------------------------------------------------------------
**Closing the Stream**
* ¿Notaste que en la última muestra de código, pusimos nuestro objeto Stream dentro de un try-with-resources? 
* Los métodos basados en stream de NIO.2 abren una conexión al sistema de archivos that must be properly closed; de lo contrario, podría ocurrir una fuga de recursos. 
* Una fuga de recursos dentro del sistema de archivos significa que la ruta puede estar bloqueada de modificación mucho después de que el proceso que la usó se complete.

* Si asumiste que la operación terminal de un stream cerraría automáticamente los recursos del sistema de archivos subyacentes, estarías equivocado. 
* Hubo mucho de debate sobre este comportamiento cuando fue presentado por primera vez; en resumen, requerir a los desarrolladores cerrar el stream ganó.

* En el lado positivo, no todos los streams necesitan ser cerrados: solo aquellos que abren recursos, como los encontrados en NIO.2. 
* Por ejemplo, no necesitas cerrar ninguno de los streams con los que trabajaste en Chapter 10, "Streams."

* Finalmente, el examen no siempre cierra apropiadamente los recursos NIO.2. 
* Para coincidir con el examen, a veces omitimos cerrar recursos NIO.2 en preguntas de revisión y práctica. 
* Siempre usa declaraciones try-with-resources con estos métodos NIO.2 en tu propio código.
---------------------------------------------------------------------

* Para el resto de esta sección, solo discutimos los métodos NIO.2, porque son más importantes. 
* También hay más para saber sobre ellos, y es más probable que aparezcan en el examen.

### Handling Methods That Declare IOException

Muchos de los métodos presentados en este capítulo declaran IOException. Las causas comunes de que un método lance esta excepción incluyen lo siguiente:
* Pérdida de comunicación con el sistema de archivos subyacente.
* El archivo o directorio existe, pero no puede ser accedido o modificado.
* El archivo existe, pero no puede ser sobreescrito.
* El archivo o directorio es requerido pero no existe.

* Los métodos que acceden o cambian archivos y directorios, tal como aquellos en la clase Files, frecuentemente declaran IOException. 
* Hay excepciones a esta regla, como veremos. Por ejemplo, el método Files.exists() no declara IOException. 
* Si lanzara una excepción cuando el archivo no existe, ¡nunca sería capaz de retornar false! 
* Como regla general, si un método NIO.2 declara una IOException, usually requiere que las rutas sobre las que opera existan.

### Providing NIO.2 Optional Parameters

* Muchos de los métodos NIO.2 en este capítulo incluyen un varargs que toma una lista opcional de valores. 
* Table 14.5 presenta los argumentos con los que deberías estar familiarizado para el examen.

![ch14_01_08.png](images/ch14/ch14_01_08.png)

* Con las excepciones de Files.copy() y Files.move(), no discutiremos estos parámetros varargs cada vez que presentemos un método. 
* Su comportamiento debería ser directo, sin embargo. 
* Por ejemplo, ¿puedes descifrar qué hace la siguiente llamada a Files.exists() con el LinkOption en el siguiente fragmento de código?

```java
Path path = Paths.get("schedule.xml");
boolean exists = Files.exists(path, LinkOption.NOFOLLOW_LINKS);
```

* El Files.exists() simplemente verifica si un archivo existe. 
* Pero si el parámetro es un enlace simbólico, el método verifica si el objetivo del enlace simbólico existe, en su lugar. 
* Proporcionar LinkOption.NOFOLLOW_LINKS significa que el comportamiento predeterminado será anulado, y el método verificará si el enlace simbólico en sí existe.

* Nota que algunos de los enums en Table 14.5 heredan una interfaz. 
* Eso significa que algunos métodos aceptan una variedad de tipos enum. 
* Por ejemplo, el método Files.move() toma un varargs CopyOption, por lo que puede tomar enums de diferentes tipos, y más opciones pueden ser añadidas con el tiempo.

















```java

```

---------------------------------------------------------------------


Introducing I/O Streams
Reading and Writing Files
Serializing Data
Interacting with Users
Working with Advanced APIs
Review of Key APIs
Summary