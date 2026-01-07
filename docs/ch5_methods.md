# Methods

## Designing Methods

Un método tiene la siguiente estructura básica:

![ch05_01.png](images/ch05/ch05_01.png)

*  Dos de las partes—el nombre del método y la lista de parámetros—son llamadas la **method signature**. 
*  La firma del método proporciona instrucciones para cómo los llamadores pueden referenciar este método. 
*  La firma del método no incluye el tipo de retorno ni los modificadores de acceso, los cuales controlan dónde el método puede ser referenciado.

![ch05_02.png](images/ch05/ch05_02.png)

### Access Modifiers

* Los modificadores de acceso ayudan a hacer cumplir cuándo estos componentes tienen permitido hablar entre sí. 

El modificador `private` significa que el método puede ser llamado solo desde dentro de la misma clase.

* El modificador  `Package` significa que el método puede ser llamado solo desde una clase en el mismo paquete. 
* Este es complicado porque no hay keyword. Simplemente, omites el modificador de acceso. 
* El acceso de paquete a veces se refiere como package-private o default access (¡incluso dentro de este libro!).

El modificador  `protected` significa que el método puede ser llamado solo desde una clase en el mismo paquete o una subclase.

El modificador  `public` significa que el método puede ser llamado desde cualquier lugar.

```java
public class ParkTrip {
    public void skip1() {}
    default void skip2() {} // DOES NOT COMPILE
    void public skip3() {} // DOES NOT COMPILE
    void skip4() {}
}
```

* El método skip1() es una declaración válida con acceso public. 
* El método skip4() es una declaración válida con acceso de paquete. 
* El método skip2() no compila porque default no es un modificador de acceso válido. 
* Hay una keyword default, la cual es usada en sentencias switch e interfaces, pero default nunca es usada como un modificador de acceso. 
* El método skip3() no compila porque el modificador de acceso está especificado después del tipo de retorno.

### Optional Specifiers

* Hay un número de especificadores opcionales para métodos, mostrados en Table 5.2. 
* A diferencia de los modificadores de acceso, puedes tener múltiples especificadores en el mismo método (aunque no todas las combinaciones son legales). 
* Cuando esto sucede, puedes especificarlos en cualquier orden. 
* Y dado que estos especificadores son opcionales, tienes permitido no tener ninguno de ellos en absoluto.

![ch05_03.png](images/ch05/ch05_03.png)

```java
public class Exercise {
    public void bike1() {}
    public final void bike2() {}
    public static final void bike3() {}
    public final static void bike4() {}
    public modifier void bike5() {}     // DOES NOT COMPILE
    public void final bike6() {}        // DOES NOT COMPILE
    final public void bike7() {}
}
```

* El método bike1() es una declaración válida sin especificador opcional. Esto está bien—es opcional, después de todo. 
* El método bike2() es una declaración válida, con final como el especificador opcional. 
* Los métodos bike3() y bike4() son declaraciones válidas con tanto final como static como especificadores opcionales. El orden de estas dos keywords no importa. 
* El método bike5() no compila porque modifier no es un especificador opcional válido. 
* El método bike6() no compila porque el especificador opcional está después del tipo de retorno. 
* El método bike7() sí compila. 

* Java permite que los especificadores opcionales aparezcan antes del modificador de acceso. 
* Este es un caso extraño y no uno que necesitas conocer para el examen. 
* Lo estamos mencionando para que no te confundas cuando practiques.

### Return Type

* El siguiente elemento en una declaración de método es el tipo de retorno. 
* Debe aparecer después de cualquier modificador de acceso o especificadores opcionales y antes del nombre del método. 
* El tipo de retorno podría ser un tipo Java real como String o int. Si no hay tipo de retorno, la keyword `void` es usada.

```java
public class Hike {
    public void hike1() {}
    public void hike2() { return; }
    public String hike3() { return ""; }
    public String hike4() {}           // DOES NOT COMPILE
    public hike5() {}                  // DOES NOT COMPILE
    public String int hike6() { }      // DOES NOT COMPILE
    String hike7(int a) {              // DOES NOT COMPILE
        if(1 < 2) return "orange";
    }
}
```

* Dado que el tipo de retorno del método hike1() es void, la sentencia return es opcional. 
* El método hike2() muestra la sentencia return opcional que correctamente no devuelve nada. 
* El método hike3() es una declaración válida con un tipo de retorno String y una sentencia return que devuelve un String. 
* El método hike4() no compila porque la sentencia return falta. 
* El método hike5() no compila porque el tipo de retorno falta. 
* El método hike6() no compila porque intenta usar dos tipos de retorno. Obtienes solo un tipo de retorno.
* El método hike7() es un poco complicado. Hay una sentencia return, pero no siempre se ejecuta. 
* Aunque 1 es siempre menor que 2, el compilador no evaluará completamente la sentencia if y requiere una sentencia return si esta condición es falsa. 

### Method Name

* Los nombres de método siguen las mismas reglas que practicamos con nombres de variable.
* Por convención, los métodos comienzan con una letra minúscula, pero no están obligados a hacerlo.

```java
public class BeachTrip {
    public void jog1() {}
    public void 2jog() {} // DOES NOT COMPILE
    public jog3 void() {} // DOES NOT COMPILE
    public void Jog_$() {}
    public _() {}         // DOES NOT COMPILE
    public void() {}      // DOES NOT COMPILE
}
```

* El método `jog1()` es una declaración válida con un nombre tradicional. 
* El método `2jog()` no compila porque los identificadores no tienen permitido comenzar con números. 
* El método `jog3()` no compila porque el nombre del método está antes del tipo de retorno. 
* El método `Jog_$()` es una declaración válida. Mientras que ciertamente no es una buena práctica comenzar un nombre de método con una letra mayúscula y terminar con puntuación, es legal. 
* El método `_` no está permitido, ya que consiste de un solo guion bajo. 
* La línea final de código no compila porque el nombre del método falta.

### Parameter List

* Aunque la lista de parámetros es requerida, no tiene que contener ningún parámetro. 
* Esto significa que puedes tener simplemente un par vacío de paréntesis después del nombre del método, como sigue:

```java
public class PhysicalEducation {
    public void run1() {}
    public void run2 {}            // DOES NOT COMPILE
    public void run3(int a) {}
    public void run4(int a; int b) {} // DOES NOT COMPILE
    public void run5(int a, int b) {}
}
```

* El método run1() es una declaración válida sin ningún parámetro. 
* El método run2() no compila porque le faltan los paréntesis alrededor de la lista de parámetros. 
* El método run3() es una declaración válida con un parámetro. 
* El método run4() no compila porque los parámetros están separados por un punto y coma en lugar de una coma. 
* Los puntos y coma son para separar sentencias, no para listas de parámetros. 
* El método run5() es una declaración válida con dos parámetros.

### Method Signature

* Una firma de método, compuesta del nombre del método y la lista de parámetros, es lo que Java usa para determinar de manera única exactamente qué método estás intentando llamar. 
* Una vez que determina qué método estás intentando llamar, entonces determina si la llamada está permitida.
* Es importante notar que los nombres de los parámetros en la firma del método no son usados como parte de una firma de método. 
* La lista de parámetros trata sobre los tipos de parámetros y su orden. Por ejemplo, los siguientes dos métodos tienen exactamente la misma firma:

```java
public class Trip {
    public void visitZoo(String name, int waitTime) {}
    public void visitZoo(String attraction, int rainFall) {} // DOES NOT COMPILE
}
```

A pesar de tener nombres de parámetro diferentes, estos dos métodos tienen la misma firma y no pueden ser declarados dentro de la misma clase. 
Cambiar el orden de los tipos de parámetro sí permite que el método compile, sin embargo:

```java
public class Trip {
    public void visitZoo(String name, int waitTime) {}
    public void visitZoo(int rainFall, String attraction) {}
}
```

### Exception List

En Java, el código puede indicar que algo salió mal lanzando una excepción solo necesitas saber que es opcional y dónde en la declaración del método va si está presente. 

```java
public class ZooMonorail {
    public void zeroExceptions() {}
    public void oneException() throws IllegalArgumentException {}
    public void twoExceptions() throws IllegalArgumentException, InterruptedException {}
}
```

Mientras que la lista de excepciones es opcional, puede ser requerida por el compilador, dependiendo de lo que aparece dentro del cuerpo del método. 

### Method Body

Un cuerpo de método es simplemente un bloque de código. Tiene llaves que contienen cero o más sentencias Java.

```java
public class Bird {
    public void fly1() {}
    public void fly2()      // DOES NOT COMPILE
    public void fly3(int a) { int name = 5; }
}
```













---------------------------------------------------------------------
**Palabra** cuando es una palabra en inglés importante que tiene sentido traducirla, pero no es una palabra reservada

() version en ingles de la palabra anterior

`   `  solo cúando es una línea de código o una palabra reservada que va a ser explicada

```java

```

Declaring Local and Instance Variables
Working with Varargs
Applying Access Modifiers
Accessing static Data
Passing Data among Methods
Overloading Methods