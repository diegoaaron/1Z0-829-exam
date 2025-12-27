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








**Palabra** cuando es una palabra en inglés importante, pero no es una palabra reservada 

() version en ingles de la palabra anterior

`   `

```java

```

---------------------------------------------------------------------


Writing main() Method
Writing a main() Method
Understanding Package Declarations and Imports
Creating Objects
Understanding Data Types
Declaring Variables
Initializing Variables
Managing Variable Scope
Destroying Objects