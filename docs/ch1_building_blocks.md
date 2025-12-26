# Building Blocks

## Learning about the Environment

El entorno Java consiste en entender una serie de tecnologías. 
En las siguientes secciones, revisamos los términos clave y acrónimos que necesitas conocer y luego discutimos qué software necesitas para estudiar para el examen.

El Java Development Kit (JDK) contiene el software mínimo que necesitas para hacer desarrollo Java. Los comandos clave incluyen:

* javac: Convierte archivos fuente .java en `bytecode .class`
* java: Ejecuta el programa
* jar: Empaqueta archivos juntos
* javadoc: Genera documentación

* El programa javac genera instrucciones en un formato especial llamado bytecode que el comando java puede ejecutar. 
* Luego java lanza la Java Virtual Machine (JVM) antes de ejecutar el código. La JVM sabe cómo ejecutar bytecode en la máquina real en la que está. 
* Puedes pensar en la JVM como una caja mágica especial en tu máquina que sabe cómo ejecutar tú `archivo .class` dentro de tu sistema operativo y hardware particulares.

---------------------------------------------------------------------
**Where Did the JRE Go?**

* En Java 8 y versiones anteriores, podías descargar un Java Runtime Environment (JRE) en lugar del JDK completo. 
* El JRE era un subconjunto del JDK que se usaba para ejecutar un programa, pero no podía compilar uno. 
* Ahora, las personas pueden usar el JDK completo cuando ejecutan un programa Java. 
* Alternativamente, los desarrolladores pueden suministrar un ejecutable que contiene las piezas requeridas que habrían estado en el JRE.
---------------------------------------------------------------------

* Cuando escribes un programa, hay piezas comunes de funcionalidad y algoritmos que los desarrolladores necesitan. 
* Afortunadamente, no tenemos que escribir cada una de estas nosotros mismos. 
* Java viene con un gran conjunto de application programming interfaces (APIs) que puedes usar. 
* Por ejemplo, hay una clase StringBuilder para crear un String grande y un método en Collections para ordenar una lista. 
* Cuando escribes un programa, es útil determinar qué piezas de tu tarea pueden ser realizadas por APIs existentes.

* Podrías haber notado que dijimos que el JDK contiene el software mínimo que necesitas. 
* Muchos desarrolladores usan un integrated development environment (IDE) para hacer que escribir y ejecutar código sea más fácil.
* Aunque no recomendamos usar uno mientras estudias para el examen, todavía es bueno saber que existen.

### Downloading a JDK

Cada seis meses, Oracle lanza una nueva versión de Java. Java 17 salió en septiembre 2021. 
Esto significa que Java 17 no será la última versión cuando descargues el JDK para estudiar para el examen. 
Sin embargo, aún deberías usar Java 17 para estudiar, ya que este es un examen Java 17. 
Las reglas y el comportamiento pueden cambiar con versiones posteriores de Java. 
No querrías obtener una pregunta incorrecta porque estudiaste con una versión diferente de Java.

Puedes descargar el JDK de Oracle en el sitio web de Oracle, usando la misma cuenta que usas para registrarte para el examen. 
Hay muchos JDK disponibles, el más popular de los cuales, además del JDK de Oracle, es OpenJDK.

Muchas versiones de Java incluyen preview features que están desactivadas por defecto, pero que puedes habilitar. 
Las características de vista previa no están en el examen. 
Para evitar confusión sobre cuándo se agregó una característica al lenguaje, diremos "fue oficialmente introducida en" para denotar cuándo se movió fuera de vista previa.

---------------------------------------------------------------------
**Check Your Version of Java**

* Antes de que vayamos más lejos, por favor toma esta oportunidad para asegurarte de que tienes la versión correcta de Java en tu path.
* Ambos de estos comandos deberían incluir el número de versión 17.

```java
javac -version
java -version
```
---------------------------------------------------------------------

## Understanding the Class Structure

* En programas Java, las clases son los bloques de construcción básicos. 
* Cuando defines una class, describes todas las partes y características de uno de esos bloques de construcción. 
* En capítulos posteriores, ves otros bloques de construcción tales como interfaces, records, y enums.

* Para usar la mayoría de las clases, tienes que crear objetos. 
* Un object es una instancia en tiempo de ejecución de una clase en memoria. 
* Un objeto es frecuentemente referido como una instance, ya que representa una única representación de la clase. 
* Todos los varios objetos de todas las diferentes clases representan el estado de tu programa. 
* Una reference es una variable que apunta a un objeto.

* En las siguientes secciones, examinamos fields, methods, y comments. 
* También exploramos la relación entre clases y archivos.

### Fields and Methods

* Las clases Java tienen dos elementos primarios: methods, a menudo llamados funciones o procedimientos en otros lenguajes, y fields, más generalmente conocidos como variables. 
* Juntos estos son llamados los members de la clase. Las variables mantienen el estado del programa, y los métodos operan sobre ese estado. 
* Si el cambio es importante de recordar, una variable almacena ese cambio. 
* Eso es todo lo que las clases realmente hacen. Es el trabajo del programador crear y organizar estos elementos de tal manera que el código resultante sea útil y, idealmente, fácil para que otros programadores lo entiendan.










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