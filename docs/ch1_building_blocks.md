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




---------------------------------------------------------------------
**Palabra** cuando es una palabra en inglés importante que tiene sentido traducirla, pero no es una palabra reservada 

() version en ingles de la palabra anterior

`   `  solo cúando es una línea de código o una palabra reservada que va a ser explicada

```java

```

Creating Objects
Understanding Data Types
Declaring Variables
Initializing Variables
Managing Variable Scope
Destroying Objects