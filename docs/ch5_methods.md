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

* El método fly1() es una declaración válida con un cuerpo de método vacío. 
* El método fly2() no compila porque le faltan las llaves alrededor del cuerpo del método vacío. 
* Los métodos están obligados a tener un cuerpo a menos que estén declarados abstract. 
* El método fly3() es una declaración válida con una sentencia en el cuerpo del método.

## Declaring Local and Instance Variables

Las variables locales son aquellas definidas dentro de un método o bloque, mientras que las variables de instancia son aquellas que están definidas como miembro de una clase.

```java
public class Lion {
    int hunger = 4;
    
    public int feedZooAnimals() {
        int snack = 10; // Local variable
        if(snack> 4) {
            long dinnerTime = snack++;
            hunger--;
        }
        return snack;
    }
}
```

* En la clase Lion, `snack` y `dinnerTime` son variables locales accesibles solo dentro de sus respectivos bloques de código. 
* Mientras que `hunger` es una variable de instancia y creada en cada objeto de la clase Lion.

Todas las referencias de variable local son destruidas después de que el bloque es ejecutado, pero los objetos a los que apuntan aún pueden ser accesibles.

### Local Variable Modifiers

* Solo hay un modificador que puede ser aplicado a una variable local: `final`. Fácil de recordar.
* Cuando escriben métodos, los desarrolladores pueden querer establecer una variable que no cambie durante el transcurso del método. 
* En este ejemplo de código, intentar cambiar el valor u objeto al que estas variables hacen referencia resulta en un error del compilador:

```java
public void zooAnimalCheckup(boolean isWeekend) {
    final int rest;
    if(isWeekend) rest = 5; else rest = 20;
    System.out.print(rest);
    
    final var giraffe = new Animal();
    final int[] friends = new int[5];
    
    rest = 10;                          // DOES NOT COMPILE
    giraffe = new Animal();             // DOES NOT COMPILE
    friends = null;                     // DOES NOT COMPILE
}
```

* Como se muestra con la variable rest, no necesitamos asignar un valor cuando una variable final es declarada. 
* La regla es solo que debe ser asignado un valor antes de que pueda ser usado. 
* Incluso podemos usar var y final juntos. Contrasta esto con el siguiente ejemplo:

```java
public void zooAnimalCheckup(boolean isWeekend) {
    final int rest;
    if(isWeekend) rest = 5;
    System.out.print(rest); // DOES NOT COMPILE
}
```

* La variable rest podría no haber sido asignada un valor, tal como si isWeekend es false. 
* Dado que el compilador no permite el uso de variables locales que pueden no haber sido asignadas un valor, el código no compila.

* ¿Usar el modificador final significa que no podemos modificar los datos? No. 
* El atributo final solo se refiere a la referencia de la variable; los contenidos pueden ser libremente modificados (asumiendo que el objeto no es inmutable).

```java
public void zooAnimalCheckup() {
    final int rest = 5;
    final Animal giraffe = new Animal();
    final int[] friends = new int[5];
    
    giraffe.setName("George");
    friends[2] = 2;
}
```

* La variable rest es un primitivo, así que es solo un valor que no puede ser modificado. 
* Por otro lado, los contenidos de las variables giraffe y friends pueden ser libremente modificados, siempre que las variables no sean reasignadas.

### Effectively Final Variables

Una variable local effectively final es una que no es modificada después de que es asignada. 
Esto significa que el valor de una variable no cambia después de que es establecido, sin importar si está explícitamente marcada como final. 
Si no estás seguro si una variable local es effectively final, solo agrega la keyword final. Si el código aún compila, la variable es effectively final.

Dada esta definición, ¿cuál de las siguientes variables es effectively final?

```java
11: public String zooFriends() {
12:     String name = "Harry the Hippo";
13:     var size = 10;
14:     boolean wet;
15:     if(size > 100) size++;
16:     name.substring(0);
17:     wet = true;
18:     return name;
19: }
```

* Recuerda, una prueba rápida de effectively final es solo agregar final a la declaración de variable y ver si aún compila. 
* En este ejemplo, `name` y `wet` son effectively final y pueden ser actualizadas con el modificador final, pero no `size`. 
* La variable name es asignada un valor en la línea 12 y no reasignada. La línea 16 crea un valor que nunca es usado. 
* Recuerda que los strings son inmutables. La variable size no es effectively final porque podría ser incrementada en la línea 15. 
* La variable `wet` es asignada un valor solo una vez y no modificada después.

### Instance Variable Modifiers

Como los métodos, las variables de instancia pueden usar modificadores de acceso, tales como `private`, `package`, `protected`, y `public`. 
Recuerda, el acceso `package` es indicado por la falta de cualquier modificador. 
Cubrimos cada uno de los diferentes modificadores de acceso en breve en este capítulo. 
Las variables de instancia también pueden usar especificadores opcionales, descritos en Table 5.3.

![ch05_04.png](images/ch05/ch05_04.png)

* Si una variable de instancia está marcada como final, entonces debe ser asignada un valor cuando es declarada o cuando el objeto es instanciado. 
* Como una variable final local, no puede ser asignada un valor más de una vez, sin embargo. La siguiente clase PolarBear demuestra estas propiedades:

```java
public class PolarBear {
    final int age = 10;
    final int fishEaten;
    final String name;
    
    { fishEaten = 10; }
    
    public PolarBear() {
        name = "Robert";
    }
}
```

* A la variable `age` se le da un valor cuando es declarada, mientras que la variable `fishEaten` es asignada un valor en un inicializador de instancia. 
* La variable `name` es dada un valor en el constructor sin argumentos. 
* Fallar al inicializar una variable de instancia (o asignar un valor más de una vez) llevará a un error del compilador. 
* Hablamos sobre la inicialización de variables final con más detalle cuando cubrimos constructores en el siguiente capítulo.

## Working with Varargs

### Creating Methods with Varargs

1. Un método puede tener a lo mucho un parámetro varargs.
2. Si un método contiene un parámetro varargs, debe ser el último parámetro en la lista.

```java
public class VisitAttractions {
    public void walk1(int... steps) {}
    public void walk2(int start, int... steps) {}
    public void walk3(int... steps, int start) {}  // DOES NOT COMPILE
    public void walk4(int... start, int... steps) {} // DOES NOT COMPILE
}
```

* El método walk1() es una declaración válida con un parámetro varargs. 
* El método walk2() es una declaración válida con un parámetro int y un parámetro varargs parámetro. 
* Los métodos walk3() y walk4() no compilan porque tienen un parámetro varargs en una posición que no es la última.

### Calling Methods with Varargs

Al llamar a un método con un parámetro varargs, tienes una opción. Puedes pasar un array, o puedes listar los elementos del array y dejar que Java lo cree por ti. 
Dado nuestro método walk1() anterior, que toma un parámetro varargs, podemos llamarlo de dos maneras:

```java
// Pass an array
int[] data = new int[] {1, 2, 3};
walk1(data);

// Pass a list of values
walk1(1,2,3);
```

### Accessing Elements of a Vararg

Acceder a un parámetro varargs es justo como acceder a un array. Usa indexación de arrays.

```java
16: public static void run(int... steps) {
17:     System.out.print(steps[1]);
18: }
19: public static void main(String[] args) {
20:     run(11, 77); // 77
21: }
```

* La línea 20 llama a un método varargs con dos parámetros. 
* Cuando el método es llamado, ve un array de tamaño 2. 
* Ya que los índices están basados en cero, 77 es impreso.

### Using Varargs with Other Method Parameters

¿Puedes descifrar por qué cada llamada a método produce lo que hace?

```java
1: public class DogWalker {
2:     public static void walkDog(int start, int... steps) {
3:         System.out.println(steps.length);
4:     }
5:     public static void main(String[] args) {
6:         walkDog(1);           // 0
7:         walkDog(1, 2);        // 1
8:         walkDog(1, 2, 3);     // 2
9:         walkDog(1, new int[] {4, 5}); // 2
10:    } }
```

* La línea 6 pasa 1 como start pero nada más. Esto significa que Java crea un array de longitud 0 para steps. 
* La línea 7 pasa 1 como start y un valor más. Java convierte este un valor a un array de longitud 1. 
* La línea 8 pasa 1 como start y dos valores más. Java convierte estos dos valores a un array de longitud 2. 
* La línea 9 pasa 1 como start y un array de longitud 2 directamente como steps.

* Has visto que Java creará un array vacío si no se pasan parámetros para un vararg. 
* Sin embargo, aún es posible pasar null explícitamente. El siguiente snippet sí compila:

`walkDog(1, null); // Triggers NullPointerException in walkDog()`

* Ya que null no es un int, Java lo trata como una referencia de array que resulta ser null. 
* Simplemente, pasa el objeto array null a walkDog(). Entonces el método walkDog() lanza una excepción porque intenta determinar la longitud de null.

## Applying Access Modifiers

### Private Access

Solo el código en la misma clase puede llamar métodos `private` o acceder a campos `private`.

* Primero, echa un vistazo a Figure 5.2. Muestra las clases que usarás para explorar el acceso `private` y `package`. 
* Las cajas grandes son los nombres de los paquetes. Las cajas más pequeñas dentro de ellas son las clases en cada paquete. 

![ch05_05.png](images/ch05/ch05_05.png)

Este es código perfectamente legal porque todo es una clase:

```java
1: package pond.duck;
2: public class FatherDuck {
3:     private String noise = "quack";
4:     private void quack() {
5:         System.out.print(noise);     // private access is ok
6:     }
7: }
```

Hasta ahora, todo bien. FatherDuck declara un método `private quack()` y usa la variable de instancia `private noise` en la línea 5.

Ahora agregamos otra clase:

```java
1: package pond.duck;
2: public class BadDuckling {
3:     public void makeNoise() {
4:         var duck = new FatherDuck();
5:         duck.quack();                // DOES NOT COMPILE
6:         System.out.print(duck.noise); // DOES NOT COMPILE
7:     }
8: }
```

* BadDuckling está intentando acceder a una variable de instancia y un método que no tiene permitido tocar. 
* En la línea 5, intenta acceder a un método `private` en otra clase. 
* En la línea 6, intenta acceder a una variable de instancia `private` en otra clase. 

### Package Access

Cuando no hay modificador de acceso, Java asume acceso de paquete.

```java
package pond.duck;
public class MotherDuck {
    String noise = "quack";
    void quack() {
        System.out.print(noise);     // package access is ok
    }
}
```

* MotherDuck puede referirse a noise y llamar quack(). Después de todo, los miembros en la misma clase están ciertamente en el mismo paquete. 
* La gran diferencia es que MotherDuck permite a otras clases en el mismo paquete acceder a los miembros, mientras que FatherDuck no lo hace (debido a ser `private`). 

```java
package pond.duck;
public class GoodDuckling {
    public void makeNoise() {
        var duck = new MotherDuck();
        duck.quack();                    // package access is ok
        System.out.print(duck.noise);    // package access is ok
    }
}
```

* GoodDuckling tiene éxito en aprender a quack() y hacer ruido copiando a su madre. 
* Nota que todas las clases cubiertas hasta ahora están en el mismo paquete, pond.duck. 
* Esto permite que el acceso de paquete funcione.

En este mismo estanque, un cisne acaba de dar a luz a un bebé cisne. Un bebé cisne se llama un cygnet. El cygnet ve a los patitos aprendiendo a quack y decide aprender de MotherDuck también.

```java
package pond.swan;
import pond.duck.MotherDuck;         // import another package
public class BadCygnet {
    public void makeNoise() {
        var duck = new MotherDuck();
        duck.quack();                    // DOES NOT COMPILE
        System.out.print(duck.noise);    // DOES NOT COMPILE
    }
}
```

* ¡Oh, no! MotherDuck solo permite lecciones a otros patos restringiendo el acceso al paquete pond.duck. 
* El pobre pequeño BadCygnet está en el paquete pond.swan, y el código no compila. 

### Protected Access









---------------------------------------------------------------------
**Palabra** cuando es una palabra en inglés importante que tiene sentido traducirla, pero no es una palabra reservada

() version en ingles de la palabra anterior

`   `  solo cúando es una línea de código o una palabra reservada que va a ser explicada

```java

```

Accessing static Data
Passing Data among Methods
Overloading Methods