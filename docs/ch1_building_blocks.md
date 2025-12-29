# Building Blocks

## Learning about the Environment

El Java Development Kit (JDK) contiene el software mínimo que necesitas para hacer desarrollo Java. Los comandos clave incluyen:

* javac: Convierte archivos fuente .java en `bytecode .class`
* java: Ejecuta el programa
* jar: Empaqueta archivos juntos
* javadoc: Genera documentación

* El programa javac genera instrucciones en un formato especial llamado **bytecode** que el comando java puede ejecutar. 
* Luego java lanza la Java Virtual Machine (JVM) antes de ejecutar el código. La JVM sabe cómo ejecutar bytecode en el equipo real en la que está. 
* Puedes pensar en la JVM como una caja especial en el equipo que sabe cómo ejecutar un **archivo.class** que es el resultado de compilar un **archivo.java**. 

---------------------------------------------------------------------
**Where Did the JRE Go?**

* En Java 8 y versiones anteriores, podías descargar un Java Runtime Environment (JRE) en lugar del JDK completo. 
* El JRE era un subconjunto del JDK que se usaba para ejecutar un programa, pero no podía compilar uno. 
* Ahora, las personas pueden usar el JDK completo cuando ejecutan un programa Java. 
---------------------------------------------------------------------

### Downloading a JDK

* Cada seis meses, Oracle lanza una nueva versión de Java. Java 17 salió en septiembre 2021. 
* Hay muchos JDK disponibles, el más popular de los cuales, además del JDK de Oracle, es OpenJDK.


## Understanding the Class Structure

* En programas Java, las clases (class) son los bloques de construcción básicos. 
* Cuando defines una clase, describes todas las partes y características de uno de esos bloques de construcción. 

* Para usar la mayoría de las clases, tienes que crear objetos (objects). 
* Un objeto es una instancia en tiempo de ejecución de una clase en memoria. 
* Un objeto es frecuentemente referido como una instancia, ya que representa una única representación de la clase. 
* El conjunto de todos los varios objetos de todas las diferentes clases representan el estado de tu programa. 
* Una reference es una variable que apunta a un objeto.

### Fields and Methods

* Las clases Java tienen dos elementos primarios: métodos (methods), a menudo llamados funciones y campos (fields), más generalmente conocidos como variables. 
* Juntos son llamados los miembros (members) de la clase. Las variables mantienen el estado del programa, y los métodos operan sobre ese estado. 

La clase Java más simple que puedes escribir se ve así:

```java
1: public class Animal {
2: }
```

* Java llama a una palabra con significado especial un **keyword**.
* La línea 1 incluye el keyword `public`, que permite a otras clases usarla. El keyword `class` indica que estás definiendo una clase. 
* Animal da el nombre de la clase. 

```java
1: public class Animal {
2:    String name;
3: }
```

En la línea 2, definimos una variable llamada name. También declaramos el tipo de esa variable para que sea String. 
Un `String` es un valor en el que podemos poner texto.
Luego podemos agregar methods.

```java
1: public class Animal {
2:    String name;
3:    public String getName() {
4:       return name;
5:    }
6:    public void setName(String newName) {
7:       name = newName;
8:    }
9: }
```

* En las líneas 3–5, definimos un método. Un método es una operación que puede ser llamada. 
* Nuevamente, `public` es usado para significar que este método puede ser llamado desde otras clases. 
* Luego viene el tipo de retorno, en este caso, el método retorna un String. 
* En las líneas 6–8 hay otro método. Este tiene un tipo de retorno especial llamado void. 
* El keyword `void` significa que ningún valor en absoluto es retornado. 
* Este método requiere que información sea suministrada a él desde el método que llama; esta información es llamada un `parameter`. 
* El método `setName()` tiene un parámetro llamado newName, y es de tipo String. 
* Esto significa que quien utiliza este método debería pasar un parámetro String y esperar que nada sea retornado. 
* El nombre del método y los tipos de parámetros se llaman la firma del método. 

```java
public int numberVisitors(int month) {
  return 10;
}
```

* El nombre del método es numberVisitors. 
* Hay un parámetro llamado month, que es de tipo int, el cual es un tipo numérico. 
* Por lo tanto, **la firma del método es** `numberVisitors(int)`.

### Comments

* Otra parte común del código se llama un comentario (comment). 
* Debido a que los comentarios no son código ejecutable, puedes colocarlos en muchos lugares. 
* Los comentarios pueden hacer que tu código sea más fácil de leer. 
* Hay tres tipos de comentarios en Java. El primero se llama comentario de una sola línea:

`// comment until end of line`

El compilador ignora cualquier cosa que escribas después de eso en la misma línea. 
A continuación viene el comentario multilínea:

```java
/* Multiple
 * line comment
 */
```

* Un comentario multilínea incluye cualquier cosa que comience desde el símbolo /* hasta el símbolo */. 
* A menudo escriben un asterisco (*) al comienzo de cada línea de un comentario multilínea para hacerlo más fácil de leer, pero no tienes que hacerlo. 
* Finalmente, tenemos un comentario Javadoc:

```java
/**
 * Javadoc multiple-line comment
 * @author Jeanne and Scott
 */
```

Este comentario es similar a un comentario multilínea, excepto que comienza con /**. 
Esta sintaxis especial le indica a la herramienta Javadoc que preste atención al comentario. 
Los comentarios Javadoc tienen una estructura específica que la herramienta Javadoc sabe cómo leer.

### Classes and Source Files

* La mayor parte del tiempo, cada clase Java se define en su propio archivo **.java**.
* Una clase de nivel superior (la clase asociada al nombre del archivo .java) a menudo es `public`, lo que significa que cualquier código puede llamarla. 
* Curiosamente, Java no requiere que el tipo sea public. Por ejemplo, esta clase está bien:

```java
1: class Animal {
2:    String name;
3: }
```

Incluso puedes poner dos clases de nivel superior en el mismo archivo. 
Cuando lo haces, una de estas puede ser `public`. Eso significa que un archivo que contiene lo siguiente también está bien:

```java
1: public class Animal {
2:    private String name;
3: }
4: class Animal2 {}
```

Cuando una clase tiene un tipo `public`, necesita que coincida con el nombre del archivo. 

## Writing a main() Method

Un programa Java comienza la ejecución con su método `main()`. 
El método `main()` a menudo se llama un punto de entrada al programa, porque es el punto de inicio que la JVM busca cuando comienza a ejecutar un nuevo programa.

### Creating a main() Method

El método main() permite que la JVM llame a nuestro código. La clase más simple posible con un método main() se ve así:

```java
1: public class Zoo {
2:    public static void main(String[] args) {
3:    System.out.println("Hello World");
4:    }
5: }
```

Para compilar código Java con el comando `javac`, el archivo debe tener la extensión **.java**. 
El nombre del archivo debe coincidir con el nombre de la clase public. 
El resultado es un archivo de bytecode con el mismo nombre pero con una extensión de nombre de archivo **.class**. 
Recuerda que el bytecode consiste en instrucciones que la JVM sabe cómo ejecutar.

Las reglas para lo que un archivo Java contiene, y en qué orden, son más detalladas de lo que hemos explicado hasta ahora (hay más sobre este tema más adelante en el capítulo). 
Para mantener las cosas simples por ahora, seguimos este subconjunto de las reglas:
* Cada archivo puede contener solo una clase public.
* El nombre del archivo debe coincidir con el nombre de la clase, incluyendo mayúsculas y minúsculas, y tener una extensión .**java**.
* Si la clase Java es un punto de entrada para el programa, debe contener un método `main()` válido.

* Primero revisemos las palabras en la firma del método `main()`, una a la vez. 
* La palabra clave `public` es lo que se llama un **access modifier**. 
* Declara el nivel de exposición de este método a los llamadores potenciales en el programa. 
* Naturalmente, public significa acceso completo desde cualquier lugar en el programa.
* La palabra clave `static` vincula un método a su clase para que pueda ser llamado solo por el nombre de la clase, como en, por ejemplo, `Zoo.main()`. 
* Java no necesita crear un objeto para llamar al método main() lo cual es bueno, ya que aún no has aprendido sobre crear objetos. 
* De hecho, la JVM hace esto, más o menos, cuando carga el nombre de la clase dado a ella. 
* Si un método main() no tiene las palabras clave correctas, obtendrás un error tratando de ejecutarlo.

* La palabra clave `void` representa el tipo de retorno (return type). 
* Un método que no devuelve datos retorna el control al invocador silenciosamente. 
* En general, es una buena práctica usar void para métodos que cambian el estado de un objeto. 
* En ese sentido, el método main() cambia el estado del programa de iniciado ha finalizado.
* Finalmente, llegamos a la lista de parámetros del método main(), representada como un array de objetos `java.lang.String`. 
* Puedes usar cualquier nombre de variable válido junto con cualquiera de estos tres formatos:

```java
String[] args
String options[]
String... friends
```

* El compilador acepta cualquiera de estos. 
* El nombre de variable `args` es común porque sugiere que esta lista contiene valores que fueron leídos (argumentos) cuando la JVM se inició. 
* Los caracteres `[]` son corchetes y representan un array. Un **array** es una lista de tamaño fijo de elementos que son todos del mismo tipo. 
* Los caracteres `...` se llaman varargs (listas de argumentos variables). 

### Passing Parameters to a Java Program

Veamos cómo enviar datos al método main() de nuestro programa. Primero, modificamos el programa Zoo para imprimir los primeros dos argumentos pasados:

```java
public class Zoo {
    public static void main(String[] args) {
        System.out.println(args[0]);
        System.out.println(args[1]);
    }
}
```

El código `args[0]` accede al primer elemento del array. Eso es correcto: los índices de array comienzan con 0 en Java. Para ejecutarlo, escribe esto:

```java
javac Zoo.java
java Zoo Bronx Zoo
```

* El programa identifica correctamente las primeras dos "palabras" como los argumentos. 
* Los espacios se usan para separar los argumentos. Si quieres espacios dentro de un argumento, necesitas usar comillas como en este ejemplo:

```java
javac Zoo.java
java Zoo "San Diego" Zoo
```

Ahora tenemos un espacio en la salida:

```
San Diego
Zoo
```

Finalmente, ¿qué sucede si no pasas suficientes argumentos?

```java
javac Zoo.java
java Zoo Zoo
```

Se lee bien `args[0]`, y Zoo se imprime. Luego Java entra en pánico. ¡No hay un segundo argumento! 
¿Qué hacer? Java imprime una excepción diciéndote que no tiene idea qué hacer con este argumento en la posición 1. 

```java
Zoo
Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: Index 1 out of bounds for length 1
    at Zoo.main(Zoo.java:4)
```

## Understanding Package Declarations and Imports

Java pone las clases en packages (carpetas para manejar un orden). Estas son agrupaciones lógicas para las clases.
Supón que intentas compilar este código:

```java
public class NumberPicker {
  public static void main(String[] args) {
    Random r = new Random(); // DOES NOT COMPILE
    System.out.println(r.nextInt(10));
  }
}
```

El compilador Java útilmente te da un error que se ve así: `error: cannot find symbol`

La causa de este error es omitir una declaración (statement) `import`, la cual le dice a Java donde buscar la clase `Random`.

```java
import java.util.Random; // import tells us where to find Random
public class NumberPicker {
  public static void main(String[] args) {
    Random r = new Random();
    System.out.println(r.nextInt(10)); // a number 0-9
  }
}
```

Ahora el código se ejecuta; imprime un número aleatorio entre 0 y 9. Igual que los arrays, a Java le gusta comenzar a contar con 0.

### Packages

* Los paquetes al agrupar clases tienen la característica de ser jerárquicos, por ejemplo si empieza con java. 
* Esto significa que vino del JDK si usa otro nombre por lo general denota de qué aplicación viene por ejemplo com.wiley.java.book

### Wildcards

Las clases en el mismo paquete son frecuentemente importadas juntas. Puedes importar todas las clases de un paquete usando *

```java
import java.util.*; // imports java.util.Random among other things
public class NumberPicker {
  public static void main(String[] args) {
    Random r = new Random();
    System.out.println(r.nextInt(10));
  }
}
```

* Cada clase en el paquete java.util está disponible para este programa cuando Java lo compila. 
* La declaración import no trae paquetes hijos, campos, o métodos; importa solo clases directamente bajo el paquete definido con el wildcard. 

Digamos que querías usar la clase `AtomicInteger` del paquete `java.util.concurrent.atomic`. ¿Qué import o imports soportan esto?

```java
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
```

Solo el último import permite que la clase sea reconocida porque los paquetes hijos no están incluidos con los primeros dos.

* Podrías pensar que incluir tantas clases ralentiza la ejecución de tu programa, pero no es así. El compilador determina qué se necesita realmente.
* Listar las clases usadas hace el código más fácil de leer, especialmente para nuevos programadores. 
* Usar el wildcard puede acortar la lista de import. Verás ambos enfoques en el examen.

### Redundant Imports

* Hay un paquete especial en el mundo Java llamado `java.lang`. Este paquete es especial, ya que se importa automáticamente. 
* Puedes escribir este paquete en una declaración import, pero no tienes que hacerlo. 
* En el siguiente código, ¿cuántos de los imports crees que son redundantes?

```java
1: import java.lang.System;
2: import java.lang.*;
3: import java.util.Random;
4: import java.util.*;
5: public class NumberPicker {
6:   public static void main(String[] args) {
7:     Random r = new Random();
8:     System.out.println(r.nextInt(10));
9:   }
10: }
```

* La respuesta es que tres de los imports son redundantes. Las líneas 1 y 2 son redundantes porque todo en `java.lang` se importa automáticamente. 
* La línea 4 también es redundante en este ejemplo porque Random ya se importó desde `java.util.Random`. 
* Si la línea 3 no estuviera presente, `java.util.*` no sería redundante.

* Echemos un vistazo a un ejemplo más para asegurarnos de que entiendes los casos límite para imports. 
* Para este ejemplo, Files y Paths están ambos en el paquete `java.nio.file`. 
* ¿Qué declaraciones import crees que funcionarían para compilar este código?

```java
public class InputImports {
  public void read(Files files) {
    Paths.get("name");
  }
}
```

Hay dos respuestas posibles. La más corta es usar un wildcard para importar ambas al mismo tiempo.

`import java.nio.file.*;`

La otra respuesta es importar ambas clases explícitamente.

```java
import java.nio.file.Files;
import java.nio.file.Paths;
```

Ahora consideremos algunos imports que no funcionan.

```java
import java.nio.*;     // NO GOOD - a wildcard only matches class names, not "file.Files"

import java.nio.*.*;   // NO GOOD - you can only have one wildcar and it must be at the end

import java.nio.file.Paths.*; // NO GOOD - you cannot import methods only class names
```

### Naming Conflicts

* Una de las razones para usar paquetes es para que los nombres de clase no tengan que ser únicos en todo Java. 
* Esto significa que a veces querrás importar una clase que puede encontrarse en múltiples lugares. 

* Un ejemplo común de esto es la clase Date. Java proporciona implementaciones de `java.util.Date` y `java.sql.Date`. 
* ¿Qué declaración import podemos usar si queremos la versión `java.util.Date`?

* La respuesta debería ser fácil ahora. Puedes escribir ya sea `import java.util.*;` o `import java.util.Date;`. 
* Los casos difíciles surgen cuando otros imports están presentes.

```java
import java.util.*;
import java.sql.*; // causes Date declaration to not compile
```

* Cuando el nombre de clase se encuentra en múltiples paquetes, Java te da un error de compilador. 
* En nuestro ejemplo, la solución fácil es eliminar el import `java.sql.*` que no necesitamos. 
* ¿Pero qué hacemos si necesitamos un montón entero de otras clases en el paquete java.sql?

```java
import java.util.Date;
import java.sql.*;
```

* Si importas explícitamente un nombre de clase, toma precedencia sobre cualquier wildcard presente. 
* Java piensa, "El programador realmente quiere que asuma el uso de la clase `java.util.Date`"

---------------------------------------------------------------------
**If You Really Need to Use Two Classes with the Same Name**

A veces realmente quieres usar Date de dos paquetes diferentes. 
Cuando esto sucede, puedes elegir uno para usar en la declaración import y usar el nombre de clase completamente calificado (fully qualified class name) del otro. 
O puedes eliminar ambas declaraciones import y siempre usar el nombre de clase completamente calificado.

```java
public class Conflicts {
  java.util.Date date;
  java.sql.Date sqlDate;
}
```
---------------------------------------------------------------------

### Creating a New Package

* Hasta ahora, todo el código que hemos escrito en este capítulo ha estado en el **default package**. 
* Este es un paquete especial sin nombre que solo deberías usar para código desechable. 
* Puedes decir que el código está en el paquete por defecto porque no hay nombre de paquete. 
* En el examen, verás el paquete por defecto usado mucho para ahorrar espacio en los listados de código. 
* En la vida real, siempre nombra tus paquetes para evitar conflictos de nombres y para permitir que otros reutilicen tu código.

* Ahora es momento de crear un nuevo paquete. La estructura de directorios en tu computadora está relacionada con el nombre del paquete. 
* Supón que tenemos estas dos clases:

```java
package packagea;
public class ClassA {}

package packageb;
import packagea.ClassA;
public class ClassB {
    public static void main(String[] args) {
        ClassA a;
        System.out.println("Got it");
    }
}
```

* Cuando ejecutas un programa, Java sabe dónde buscar esos nombres de paquete. 
* En este caso, ejecutar desde `C:\temp` funciona porque tanto `packagea` como `packageb` están debajo de él.

### Compiling and Running Code with Packages

* El primer paso es crear los dos archivos de la sección anterior, la tabla 1.1 muestra los nombres de archivo. 

![ch01_01_01.png](images/ch01/ch01_01_01.png)

* Ahora es momento de compilar el código. Afortunadamente, esto es lo mismo sin importar el sistema operativo. 
* Para compilar, escribe el siguiente comando:

`javac packagea/ClassA.java packageb/ClassB.java`

Si el comando funciona, dos nuevos archivos serán creados: **packagea/ClassA.class** y **packageb/ClassB.class**

Ahora que tu código ha compilado, puedes ejecutarlo escribiendo el siguiente comando:

`java packageb.ClassB`

* Si funciona, verás **Got it** impreso. 
* Podrías haber notado que escribimos `ClassB` en lugar de `ClassB.class`.  
* Como se discutió anteriormente, no pasas la extensión cuando ejecutas un programa. 

### Compiling to Another Directory

* Por defecto, el comando javac coloca las clases compiladas en el mismo directorio que el código fuente. 
* También proporciona una opción para colocar los archivos de clase en un directorio diferente. 
* La opción `-d` especifica este directorio objetivo.

Si estás siguiendo el ejemplo, elimina los archivos ClassA.class y ClassB.class que fueron creados en la sección anterior. 

¿Dónde crees que este comando creará el archivo ClassA.class?

`javac -d classes packagea/ClassA.java packageb/ClassB.java`

La respuesta correcta está en `classes/packagea/ClassA.class`. La estructura de paquete se preserva bajo el directorio objetivo solicitado. 

* Para ejecutar el programa, especificas el classpath para que Java sepa dónde encontrar las clases. 
* Hay tres opciones que puedes usar. Las tres hacen lo mismo:

```java
java -cp classes packageb.ClassB
java -classpath classes packageb.ClassB
java --class-path classes packageb.ClassB
```

![ch01_01_02.png](images/ch01/ch01_01_02.png)

### Compiling with JAR Files

* Tal como el directorio **classes** en el ejemplo anterior, también puedes especificar la ubicación de los otros archivos explícitamente usando un classpath. 
* Esta técnica es útil cuando los archivos con las clases están ubicados en otro lugar o en archivos JAR especiales. 
* Un Java archive (JAR) es como un archivo ZIP de principalmente archivos de clase Java.

En Windows, escribes lo siguiente:

`java -cp ".;C:\temp\someOtherLocation;c:\temp\myJar.jar" myPackage.MyClass`

Y en macOS/Linux, escribes esto:

`java -cp ".:/tmp/someOtherLocation:/tmp/myJar.jar" myPackage.MyClass`

* El punto (.) indica que quieres incluir el directorio actual en el classpath. 
* El resto del comando dice buscar archivos de clase sueltos (o paquetes) en **someOtherLocation** y dentro de **myJar.jar**. 
* Windows usa el (;) para separar partes del classpath; otros sistemas operativos usan (:).

Igual que cuando estás compilando, puedes usar un wildcard (*) para coincidir con todos los JARs en un directorio. Aquí hay un ejemplo:

`java -cp "C:\temp\directoryWithJars\*" myPackage.MyClass`

* Este comando agregará al classpath todos los JARs que están en **directoryWithJars**. 
* No incluirá ningún JAR en el classpath que esté en un subdirectorio de **directoryWithJars**.

### Creating a JAR File

* Puedes crear un archivo JAR tú mismo. Para hacerlo, usas el comando jar. 
* Los comandos más simples crean un jar conteniendo los archivos en el directorio actual. 
* Puedes usar la forma corta o larga para cada opción.

```java
jar -cvf myNewFile.jar .
jar --create --verbose --file myNewFile.jar .
```

Alternativamente, puedes especificar un directorio en lugar de usar el directorio actual.

`jar -cvf myNewFile.jar -C dir .`

Table 1.4 lista las opciones que necesitas usar el comando jar para crear un archivo JAR. 

![ch01_01_03.png](images/ch01/ch01_01_03.png)

Ahora que has visto las partes más comunes de una clase, echemos un vistazo al orden correcto para escribirlas en un archivo. 
Los comentarios pueden ir en cualquier lugar del código. Pero el orden de la siguiente tabla debe ser memorizado para el examen.

| Elemento                              | Ejemplo               | Requerido | Donde debe ir                                                           |
|---------------------------------------|-----------------------|-----------|-------------------------------------------------------------------------|
| Declaración de paquete                | `package abc; `       | No        | Primera línea en el archivo (excluyendo comentarios o líneas en blanco) |
| Declaraciones import                  | `import java.util.*;` | No        | Inmediatamente después del paquete (si está presente)                   |
| Declaración de tipo de nivel superior | `public class C  `    | Sí        | Inmediatamente después del import (si hay alguno)                       |
| Declaraciones de campos               | `int value;`          | No        | Cualquier elemento de nivel superior dentro de una clase                |
| Declaraciones de métodos              | `void method()`       | No        | Cualquier elemento de nivel superior dentro de una clase                |


Veamos algunos ejemplos para ayudarte a recordar esto. El primer ejemplo contiene uno de cada elemento:

```java
package structure;  // package must be first non-comment
import java.util.*;  // import must come after package
public class Meerkat { // then comes the class
  double weight;  // fields and methods can go in either order
  public double getWeight() {
    return weight; 
  }
  double height;  // another field - they don't need to be together
}
```

Hasta ahora, todo bien. Este es un patrón común con el que deberías estar familiarizado. ¿Qué tal este?

```java
/* header */

package structure;

// class Meerkat
public class Meerkat { }
```

* Todavía bien. Podemos poner comentarios en cualquier lugar, las líneas en blanco son ignoradas, y los imports son opcionales. 
* En el siguiente ejemplo, tenemos un problema:

```java
import java.util.*;
package structure;  // DOES NOT COMPILE
String name;      // DOES NOT COMPILE
public class Meerkat { } // DOES NOT COMPILE
```

* Hay dos problemas aquí. Uno es que las declaraciones package e import están invertidas. 
* Aunque ambas son opcionales, package debe venir antes de import si está presente. 
* El otro problema es que un field intenta una declaración fuera de una clase. 
* Esto no está permitido. Los fields y métodos deben estar dentro de una clase.

## Creating Objects

* Recuerda que un objeto es una instancia de una clase. 
* En las siguientes secciones, examinamos constructores, object fields, instance initializers, y el orden en el cual los valores son inicializados.

### Calling Constructors

Para crear una instancia de una clase, todo lo que tienes que hacer es escribir `new` antes del nombre de la clase y agregar paréntesis después de él. 

`Park p = new Park();`

Hay que ver que `Park()` se le denomina un método constructor, que es un tipo especial de método que crea un nuevo objeto. 

```java
public class Chick {
   public Chick() {
      System.out.println("in constructor");
   }
}
```

Hay dos puntos clave a notar sobre el constructor: el nombre del constructor coincide con el nombre de la clase, y no hay tipo de retorno. 

---------------------------------------------------------------------
Puedes ver un método como este en el examen:

```java
public class Chick {
    public void Chick() {} // NOT A CONSTRUCTOR
}
```

Cuando veas un nombre de método que tiene el nombre de la clase y tiene un tipo de retorno, presta especial atención. 
No es un constructor, ya que tiene un tipo de retorno. Es un método regular que compila, pero no será llamado cuando escribas `new Chick()`.
---------------------------------------------------------------------

* El propósito de un constructor es inicializar campos, aunque puedes poner cualquier código allí. 
* Otra forma de inicializar campos es hacerlo directamente en la línea en la que se declaran. Este ejemplo muestra ambos enfoques:

```java
public class Chicken {
    int numEggs = 12; // initialize on line
    String name;
    
    public Chicken() {
        name = "Duke"; // initialize in constructor
    }
}
```

Para la mayoría de las clases, no tienes que codificar un constructor el compilador te suministra un constructor predeterminado que «no hace nada». 

### Reading and Writing Member Fields

Es posible leer y escribir variables de instancia directamente desde donde se invocan el objeto.

```java
public class Swan {
    int numberEggs; // instance variable
    
    public static void main(String[] args) {
        Swan mother = new Swan();
        mother.numberEggs = 1;    // set variable
        System.out.println(mother.numberEggs); // read variable
    }
}
```

El "caller" en este caso es el método main(), el cual podría estar en la misma clase o en otra clase. 
Esta clase establece numberEggs a 1 y luego lee numberEggs directamente para imprimirlo. 

Incluso puedes leer valores de campos ya inicializados en una línea inicializando un nuevo field:






---------------------------------------------------------------------
**Palabra** cuando es una palabra en inglés importante que tiene sentido traducirla, pero no es una palabra reservada 

() version en ingles de la palabra anterior

`   `  solo cúando es una línea de código o una palabra reservada que va a ser explicada

```java

```

Understanding Data Types
Declaring Variables
Initializing Variables
Managing Variable Scope
Destroying Objects