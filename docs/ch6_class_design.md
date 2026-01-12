# Class Design

## Understanding Inheritance

* Cuando creas una nueva clase en Java, puedes definir la clase como heredando de una clase existente. 
* Herencia (Inheritance) es el proceso por el cual una subclase automáticamente incluye ciertos miembros de la clase, incluyendo primitivos, objetos, o métodos, definidos en la clase padre.

Para propósitos ilustrativos, nos referimos a cualquier clase que hereda de otra clase como una subclass o **child class**, ya que es considerada un descendiente de esa clase. 
Alternativamente, nos referimos a la clase de la que la clase hija hereda como la superclass o **parent class**, ya que es considerada un ancestro de la clase.

### Declaring a Subclass

Comencemos con la declaración de una clase y su subclase. La Figura 6.1 muestra un ejemplo de una superclase, Mammal, y subclase Rhinoceros.

![ch06_01.png](images/ch06/ch06_01.png)

Indicamos que una clase es una subclase al declararla con la palabra clave `extends`. 
No necesitamos declarar nada en la superclase más que asegurarnos de que no esté marcada como `final`. 

* Un aspecto clave de la herencia es que es transitiva. 
* Dadas tres clases [X, Y, Z], si `X` extends `Y`, y `Y` extends `Z`, entonces `X` es considerada una subclase o descendiente de `Z`. 
* De la misma manera, `Z` es una superclase o ancestro de `X`. 
* A veces usamos el término direct subclass o descendant para indicar que la clase directamente extends la clase padre. 
* Por ejemplo, `X` es un descendiente directo solo de la clase `Y`, no de `Z`.

* Cuando una clase hereda de una clase padre, todos los miembros `public` y `protected` están automáticamente disponibles como parte de la clase hija. 
* Si las dos clases están en el mismo package, entonces los miembros package están disponibles para la clase hija. 
* Por último, pero no menos importante, los miembros `private` están restringidos a la clase en la que están definidos y nunca están disponibles vía herencia. 
* Esto no significa que la clase padre no pueda tener miembros `private` que puedan contener datos o modificar un objeto; solo significa que la subclase no tiene acceso directo a ellos.

Echemos un vistazo a un ejemplo simple:

```java
public class BigCat {
    protected double size;
}

public class Jaguar extends BigCat {
    public Jaguar() {
        size = 10.2;
    }

    public void printDetails() {
        System.out.print(size);
    }
}

public class Spider {
    public void printDetails() {
        System.out.println(size); // DOES NOT COMPILE
    }
}
```

* Jaguar es una subclase de BigCat, lo que la convierte en una superclase o clase madre de Jaguar. 
* En la clase Jaguar, se puede acceder a la propiedad `size` porque está marcada como protegida. 
* Mediante herencia, la subclase Jaguar puede leer o escribir `size` como si fuera su propio miembro. 
* En contraste, la clase Spider no tiene acceso a `size` porque no se hereda.

### Class Modifiers

* Al igual que los métodos y las variables, una declaración de clase puede tener varios modificadores. 
* La Tabla 6.1 lista los modificadores que debes conocer para el examen.

![ch06_02.png](images/ch06/ch06_02.png)

Por ahora, hablemos sobre marcar una clase como final. El modificador final previene que una clase sea extendida más. 
Por ejemplo, lo siguiente no compila:

```java
public final class Rhinoceros extends Mammal { }

public class Clara extends Rhinoceros { } // DOES NOT COMPILE
```

En el examen, presta atención a cualquier clase marcada como final. Si ves otra clase extendiéndola, sabes inmediatamente que el código no compila.

### Single vs. Multiple Inheritance

* Java soporta single inheritance, por el cual una clase puede heredar de solo una clase padre directa. 
* Java también soporta múltiples niveles de herencia, por el cual una clase puede extender otra clase, que a su vez extiende otra clase. 
* Puedes tener cualquier número de niveles de herencia, permitiendo que cada descendiente gane acceso a los miembros de su ancestro.

* Para entender verdaderamente la herencia simple, puede ser útil contrastarla con multiple inheritance, por el cual una clase puede tener múltiples padres directos. 
* Por diseño, Java no soporta herencia múltiple en el lenguaje porque la herencia múltiple puede llevar a modelos de datos complejos, a menudo difíciles de mantener. 
* Java sí permite una excepción a la regla de herencia simple, la cual ves en el Capítulo 7—una clase puede implementar múltiples interfaces.

* La Figura 6.2 ilustra los varios tipos de modelos de herencia. 
* Los elementos de la izquierda se consideran herencia simple porque cada hijo tiene exactamente un padre. 
* Puedes notar que la herencia simple no excluye a los padres de tener múltiples hijos. 
* El lado derecho muestra elementos que tienen herencia múltiple. Como puedes ver, un objeto Dog tiene múltiples designaciones de padre.

![ch06_03.png](images/ch06/ch06_03.png)

Parte de lo que hace que la herencia múltiple sea complicada es determinar de qué padre heredar valores en caso de un conflicto. 
Por ejemplo, si tienes un objeto o método definido en todos los padres, ¿cuál hereda el hijo? 
No hay un ordenamiento natural para los padres en este ejemplo, que es por qué Java evita estos problemas al prohibir por completo la herencia múltiple.

### Inheriting Object

* A lo largo de nuestra discusión de Java en este libro, hemos mencionado la palabra object numerosas veces—y con buena razón. 
* En Java, todas las clases heredan de una sola clase: `java.lang.Object`, u Object para abreviar. 
* Además, Object es la única clase que no tiene una clase padre.

* Podrías estar preguntándote, Ninguna de las clases que he escrito hasta ahora extiende Object, entonces, ¿cómo heredan todas las clases de él?
* La respuesta es que el compilador ha estado automáticamente insertando código en cualquier clase que escribes que no extiende una clase específica. 
* Por ejemplo, las siguientes dos son equivalentes:

```java
public class Zoo { }

public class Zoo extends java.lang.Object { }
```

* La clave es que cuando Java ve que defines una clase que no extiende otra clase, el compilador automáticamente agrega la sintaxis `extends java.lang.Object` a la definición de la clase. 
* El resultado es que cada clase gana acceso a cualquier método accesible en la clase Object. 
* Por ejemplo, los métodos `toString()` y `equals()` están disponibles en Object; por lo tanto, son accesibles en todas las clases. 
* Sin ser sobreescritos en una subclase, aunque, pueden no ser particularmente útiles.

## Creating Classes

### Extending a Class

Creemos dos archivos en el mismo package, `Animal.java` y `Lion.java`.

```java
// Animal.java
public class Animal {
    private int age;
    protected String name;
    public int getAge() {
        return age;
    }
    public void setAge(int newAge) {
        age = newAge;
    }
}

// Lion.java
public class Lion extends Animal {
    protected void setProperties(int age, String n) {
        setAge(age);
        name = n;
    }
    public void roar() {
        System.out.print(name + ", age " + getAge() + ", says: Roar!");
    }
    public static void main(String[] args) {
        var lion = new Lion();
        lion.setProperties(3, "kion");
        lion.roar();
    }
}
```

Hay mucho sucediendo aquí, ¡lo sabemos! La variable age existe en la clase padre Animal y no es directamente accesible en la clase hija Lion. 
Es indirectamente accesible vía el método `setAge()`. La variable `name` es `protected`, así que es heredada en la clase `Lion` y directamente accesible. 
Creamos la instancia Lion en el método `main()` y usamos `setProperties()` para establecer las variables de instancia. 
Finalmente, llamamos al método `roar()`, el cual imprime lo siguiente:

**kion, age 3, says: Roar!**

* Echemos un vistazo a los miembros de la clase Lion. La variable de instancia age está marcada como `private` y no es directamente accesible desde la subclase Lion. 
* Por lo tanto, lo siguiente no compilaría:

```java
public class Lion extends Animal {
    public void roar() {
        System.out.print("Lions age: " + age); // DOES NOT COMPILE
    }
}
```

Recuerda cuando trabajas con subclases que los miembros `private` nunca son heredados, y los miembros package solo son heredados si las dos clases están en el mismo package. 

### Applying Class Access Modifiers

* Como las variables y métodos, puedes aplicar modificadores de acceso a las clases. 
* Como podrías recordar del Capítulo 1, una clase de nivel superior (top-level) es una que no está definida dentro de otra clase. 
* También recuerda que un archivo **.java** puede tener como máximo una clase de nivel superior.

* Aunque solo puedes tener una clase de nivel superior, puedes tener tantas clases (en cualquier orden) con acceso package como quieras. 
* De hecho, ¡ni siquiera necesitas declarar una clase public! Lo siguiente declara tres clases, cada una con acceso package:

```java
// Bear.java
class Bird {}
class Bear {}
class Fish {}
```

Intentar declarar una clase de nivel superior con protected o private class llevará a un error de compilador, aunque:

```java
// ClownFish.java
protected class ClownFish{} // DOES NOT COMPILE
// BlueTang.java
private class BlueTang {} // DOES NOT COMPILE
```

* ¿Eso significa que una clase nunca puede ser declarada como `protected` o `private`? No exactamente. 
* En el Capítulo 7, presentamos tipos anidados (nested types) y mostramos que cuando defines una clase dentro de otra, puede usar cualquier modificador de acceso.

### Accessing the `this` Reference

* ¿Qué sucede cuando un parámetro de método tiene el mismo nombre que una variable de instancia existente? 
* Echemos un vistazo a un ejemplo. ¿Qué crees que imprime el siguiente programa?

```java
public class Flamingo {
    private String color = null;
    public void setColor(String color) {
        color = color;
    }
    public static void main(String... unused) {
        var f = new Flamingo();
        f.setColor("PINK");
        System.out.print(f.color);
    }
}
```

* Si dijiste `null`, entonces estarías en lo correcto. 
* Java usa el alcance más granular, así que cuando ve `color = color`, piensa que estás asignando el valor del parámetro del método a sí mismo (no la variable de instancia). 
* La asignación se completa exitosamente dentro del método, pero el valor de la variable de instancia color nunca es modificado y es `null` cuando se imprime en el método `main()`.

* La solución cuando tienes una variable local con el mismo nombre que una variable de instancia es usar la referencia `this` o palabra clave. 
* La referencia `this` se refiere a la instancia actual de la clase y puede ser usada para acceder a cualquier miembro de la clase, incluyendo miembros heredados. 
* Puede ser usada en cualquier método de instancia, constructor, o bloque inicializador de instancia. 
* No puede ser usada cuando no hay una instancia implícita de la clase, tal como en un método `static` o bloque inicializador `static`. 
* Aplicamos esto a nuestra implementación de método anterior de la siguiente manera:

```java
public void setColor(String color) {
    this.color = color; // Sets the instance variable with method parameter
}
```

* El código corregido ahora imprimirá PINK como se esperaba. En muchos casos, la referencia `this` es opcional. 
* Si Java encuentra una variable o método que no puede encontrar, verificará la jerarquía de clases para ver si está disponible.

### Calling the `super` Reference

* En Java, una variable o método puede ser definido tanto en una clase padre como en una clase hija. 
* Esto significa que la instancia del objeto en realidad contiene dos copias de la misma variable con el mismo nombre subyacente. 
* Cuando esto sucede, ¿cómo referenciamos la versión en la clase padre en lugar de la clase actual? Echemos un vistazo a un ejemplo.

```java
// Reptile.java
1: public class Reptile {
2:     protected int speed = 10;
3: }

// Crocodile.java
1: public class Crocodile extends Reptile {
2:     protected int speed = 20;
3:     public int getSpeed() {
4:     return speed;
5:  }
6:  public static void main(String[] data) {
7:     var croc = new Crocodile();
8:     System.out.println(croc.getSpeed()); // 20
9:  } }
```

* Una de las cosas más importantes para recordar sobre este código es que una instancia de `Crocodile` almacena dos valores separados para `speed`: uno en el nivel `Reptile` y uno en el nivel `Crocodile`. 
* En la línea 4, Java primero verifica si hay una variable local o parámetro de método llamado `speed`. 
* Dado que no lo hay, entonces verifica `this.speed;` y dado que existe, el programa imprime 20.

* ¿Pero qué pasa si queremos que el programa imprima el valor en la clase Reptile? 
* Dentro de la clase `Crocodile`, podemos acceder al valor padre de `speed`, en su lugar, usando la referencia o palabra clave `super`. 
* La referencia `super` es similar a la referencia `this`, excepto que excluye cualquier miembro encontrado en la clase actual. 
* En otras palabras, el miembro debe ser accesible vía herencia.

```java
3:  public int getSpeed() {
4:     return super.speed; // Causes the program to now print 10
5:  }
```

Veamos si has captado la idea de this y super. ¿Qué imprime el siguiente programa?

```java
1: class Insect {
2:     protected int numberOfLegs = 4;
3:     String label = "buggy";
4: }
5:
6: public class Beetle extends Insect {
7:     protected int numberOfLegs = 6;
8:     short age = 3;
9:     public void printData() {
10:        System.out.println(this.label);
11:        System.out.println(super.label);
12:        System.out.println(this.age);
13:        System.out.println(super.age);
14:        System.out.println(numberOfLegs);
15:    }
16:    public static void main(String []n) {
17:        new Beetle().printData();
18:    }
19: }
```

* Esa fue una pregunta trampa—¡este código de programa no compilaría! Revisemos cada línea del método printData(). 
* Dado que label está definido en la clase padre, es accesible vía tanto las referencias `this` como `super`. 
* Por esta razón, las líneas 10 y 11 compilan y ambas imprimirían buggy si la clase compilara. 
* Por otro lado, la variable `age` está definida solo en la clase actual, haciéndola accesible vía `this` pero no `super`. 
* Por esta razón, la línea 12 compila (e imprimiría 3), pero la línea 13 no. 
* Recuerda, mientras `this` incluye miembros actuales y heredados, `super` solo incluye miembros heredados.

* Por último, pero no menos importante, ¿qué imprimiría la línea 14 si la línea 13 estuviera comentada? 
* Aunque ambas variables `numberOfLegs` son accesibles en Beetle, Java verifica hacia afuera, comenzando con el alcance más estrecho. 
* Por esta razón, el valor de `numberOfLegs` en la clase Beetle es usado, y 6 es impreso. 
* En este ejemplo, `this.numberOfLegs` y `super.numberOfLegs` se refieren a diferentes variables con valores distintos.

* Dado que esto incluye miembros heredados, a menudo solo usas `super` cuando tienes un conflicto de nombres vía herencia. 
* Por ejemplo, tienes un método o variable definido en la clase actual que coincide con un método o variable en una clase padre. 
* Esto comúnmente surge en la sobre escritura de métodos y el ocultamiento de variables, que se discuten más adelante en este capítulo.

## Declaring Constructors

### Creating a Constructor

Comencemos con un constructor simple:

```java
public class Bunny {
    public Bunny() {
        System.out.print("hop");
    }
}
```

* El nombre del constructor, Bunny, coincide con el nombre de la clase, Bunny, y no hay tipo de retorno, ni siquiera void. 
* Eso hace que esto sea un constructor. ¿Puedes decir por qué estos dos no son constructores válidos para la clase Bunny?

```java
public class Bunny {
    public bunny() {} // DOES NOT COMPILE
    public void Bunny() {}
}
```

* El primero no coincide con el nombre de la clase porque Java es sensible a mayúsculas y minúsculas (case-sensitive). 
* Dado que no coincide, Java sabe que no puede ser un constructor y se supone que es un método regular. 
* Sin embargo, le falta el tipo de retorno y no compila. 
* El segundo método es un método perfectamente válido, pero no es un constructor porque tiene un tipo de retorno.

Como los parámetros de método, los parámetros de constructor pueden ser cualquier tipo de clase, array, o tipo primitivo válido, incluyendo genéricos, pero no pueden incluir var.

```java
public class Bonobo {
    public Bonobo(var food) { // DOES NOT COMPILE
    }
}
```

* Una clase puede tener múltiples constructores, siempre y cuando cada constructor tenga una firma de constructor única. 
* En este caso, eso significa que los parámetros del constructor deben ser distintos. 
* Como los métodos con el mismo nombre, pero diferentes firmas, declarar múltiples constructores con diferentes firmas se conoce como constructor overloading. 
* La siguiente clase Turtle tiene cuatro constructores sobrecargados distintos:

```java
public class Turtle {
    private String name;
    public Turtle() {
        name = "John Doe";
    }
    public Turtle(int age) {}
    public Turtle(long age) {}
    public Turtle(String newName, String... favoriteFoods) {
        name = newName;
    }
}
```

* Los constructores se utilizan cuando se crea un nuevo objeto. 
* Este proceso se llama instantiation (instanciación) porque crea una nueva instancia de la clase. 
* Un constructor se llama cuando escribimos new seguido del nombre de la clase que queremos instanciar. 

`new Turtle(15)`

Cuando Java ve la palabra clave `new`, asigna memoria para el nuevo objeto. Luego busca un constructor con una firma coincidente y lo llama.

### The Default Constructor

* Cada clase en Java tiene un constructor, ya sea que codifiques uno o no. 
* Si no incluyes ningún constructor en la clase, Java creará uno por ti sin ningún parámetro. 
* Este constructor creado por Java se llama default constructor (constructor por defecto) y se añade cada vez que se declara una clase sin ningún constructor. 
* A menudo nos referimos a él como el constructor sin argumentos por defecto, para mayor claridad. He aquí un ejemplo:

```java
public class Rabbit {
  public static void main(String[] args) {
    new Rabbit(); // Calls the default constructor
  }
}
```

* En la clase Rabbit, Java ve que no se codificó ningún constructor y crea uno. 
* La clase anterior es equivalente a la siguiente, en la cual se proporciona el constructor por defecto y, por lo tanto, no es insertado por el compilador:

```java
public class Rabbit {
  public Rabbit() {}
  public static void main(String[] args) {
    new Rabbit(); // Calls the user-defined constructor
  }
}
```

* El constructor por defecto tiene una lista de parámetros vacía y un cuerpo vacío. Está bien que tú mismo escribas esto. 
* Sin embargo, dado que no hace nada, Java está feliz de generarlo por ti y ahorrarte algo de escritura.

* Seguimos diciendo generated (generado). Esto sucede durante el paso de compilación. 
* Si miras el archivo con la extensión `.java`, el constructor seguirá faltando. 
* Solo hace una aparición en el archivo compilado con la extensión `.class`.

Para el examen, una de las reglas más importantes que necesitas saber es que el compilador solo inserta el constructor por defecto cuando no se definen constructores.


```java
public class Rabbit1 {}

public class Rabbit2 {
    public Rabbit2() {}
}

public class Rabbit3 {
    public Rabbit3(boolean b) {}
}

public class Rabbit4 {
    private Rabbit4() {}
}
```

* Solo `Rabbit1` obtiene un constructor sin argumentos por defecto. 
* No tiene un constructor codificado, así que Java genera un constructor sin argumentos por defecto. 
* Para `Rabbit2` y `Rabbit3` ambos tienen constructores públicos ya. `Rabbit4` tiene un constructor privado. 
* Dado que estas tres clases tienen un constructor definido, el constructor sin argumentos por defecto no se inserta por ti.

Echemos un vistazo rápido a cómo llamar estos constructores:

```java
1: public class RabbitsMultiply {
2:   public static void main(String[] args) {
3:     var r1 = new Rabbit1();
4:     var r2 = new Rabbit2();
5:   var r3 = new Rabbit3(true);
6:   var r4 = new Rabbit4(); // DOES NOT COMPILE
7: } }
```

* La línea 3 llama al constructor sin argumentos por defecto generado. 
* Las líneas 4 y 5 llaman a los constructores proporcionados por el usuario. 
* La línea 6 no compila. Rabbit4 hizo el constructor privado para que otras clases no pudieran llamarlo.

### Calling Overloaded Constructors with `this()`

* Dado que una clase puede contener múltiples constructores sobrecargados, estos constructores en realidad pueden llamarse entre sí. 
* Comencemos con una clase simple que contiene dos constructores sobrecargados:

```java
public class Hamster {
  private String color;
  private int weight;
  public Hamster(int weight, String color) { // First constructor
    this.weight = weight;
    this.color = color;
  }
  public Hamster(int weight) { // Second constructor
    this.weight = weight;
    color = "brown";
  }
}
```

* Uno de los constructores toma un solo parámetro `int`. El otro toma un `int` y un `String`. 
* Estas listas de parámetros son diferentes, por lo que los constructores están sobrecargados exitosamente.

* Hay un poco de duplicación, ya que `this.weight` se asigna de la misma manera en ambos constructores. 
* En programación, incluso un poco de duplicación tiende a convertirse en mucha duplicación a medida que seguimos agregando "solo una cosa más". 
* Por ejemplo, imagina que tenemos cinco variables siendo establecidas como `this.weight`, en lugar de solo una. 
* Lo que realmente queremos es que el primer constructor llame al segundo constructor con dos parámetros. 
* Entonces, ¿cómo puedes hacer que un constructor llame a otro constructor? Podrías estar tentado a reescribir el primer constructor de la siguiente manera:

```java
public Hamster(int weight) { // Second constructor
  Hamster(weight, "brown"); // DOES NOT COMPILE
}
```

* Este intento sí compila. No hace lo que queremos, sin embargo. 
* Cuando este constructor es llamado, crea un nuevo objeto con el peso y color por defecto. 
* Luego construye un objeto diferente con el peso y color deseados. 
* De esta manera, terminamos con dos objetos, uno de los cuales se descarta después de que se crea. Eso no es lo que queremos. 
* Queremos que el peso y color se establezcan en el objeto que estamos tratando de instanciar en primer lugar.

* Java proporciona una solución: `this()`—sí, la misma palabra clave que usamos para referirnos a miembros de instancia, pero con paréntesis. 
* Cuando `this()` se usa con paréntesis, Java llama a otro constructor en la misma instancia de la clase.

```java
public Hamster(int weight) { // Second constructor
  this(weight, "brown");
}
```

* Llamar a `this()` tiene una regla especial que necesitas conocer. 
* Si eliges llamarlo, la llamada a `this()` debe ser la primera declaración en el constructor. 
* El efecto secundario de esto es que solo puede haber una llamada a `this()` en cualquier constructor.

```java
3: public Hamster(int weight) {
4:   System.out.println("chew");
5:   // Set weight and default color
6:   this(weight, "brown");  // DOES NOT COMPILE
7: }
```

* Aunque una declaración de impresión en la línea 4 no cambia ninguna variable, sigue siendo una declaración de Java y no está permitido insertarla antes de la llamada a `this()`. 
* El comentario en la línea 5 está bien. Los comentarios no se consideran declaraciones y están permitidos en cualquier lugar.

Hay una última regla para constructores sobrecargados de la que deberías estar consciente. Considera la siguiente definición de la clase Gopher:

```java
public class Gopher {
  public Gopher(int dugHoles) {
    this(5); // DOES NOT COMPILE
  }
}
```

* El compilador es capaz de detectar que este constructor se está llamando a sí mismo infinitamente. 
* Esto a menudo se conoce como un cycle (ciclo) y es similar a los bucles infinitos que discutimos en Chapter 3, "Making Decisions." 
* Dado que el código nunca puede terminar, el compilador se detiene y reporta esto como un error. Del mismo modo, esto tampoco compila:

```java
public class Gopher {
  public Gopher() {
    this(5); // DOES NOT COMPILE
  }
  public Gopher(int dugHoles) {
    this(); // DOES NOT COMPILE
  }
}
```

En este ejemplo, los constructores se llaman entre sí, y el proceso continúa infinitamente. Dado que el compilador puede detectar esto, reporta un error.

Aquí resumimos las reglas que deberías conocer sobre constructores que cubrimos en esta sección. 

* Una clase puede contener muchos constructores sobrecargados, siempre que la firma de cada uno sea distinta.
* El compilador inserta un constructor sin argumentos por defecto si no se declaran constructores.
* Si un constructor llama a this(), entonces debe ser la primera línea del constructor.
* Java no permite llamadas de constructor cíclicas.

### Calling Parent Constructors with `super()`

Hay un conjunto más de reglas que necesitamos cubrir, sin embargo, para llamar constructores en la clase padre. 
Después de todo, ¿cómo se inicializan los miembros de instancia de la clase padre?

La primera declaración de every (cada) constructor es una llamada a un constructor padre usando `super()` o a otro constructor en la clase usando `this()`. 

Echemos un vistazo a la clase `Animal` y su subclase `Zebra` y veamos cómo sus constructores pueden escribirse correctamente para llamarse entre sí:

```java
public class Animal {
  private int age;
  public Animal(int age) {
    super(); // Refers to constructor in java.lang.Object
    this.age = age;
  }
}

public class Zebra extends Animal {
  public Zebra(int age) {
    super(age); // Refers to constructor in Animal
  }
  public Zebra() {
    this(4); // Refers to constructor in Zebra with int argument
  }
}
```

* En la clase `Animal`, la primera declaración del constructor es una llamada al constructor padre definido en `java.lang.Object`, que no toma argumentos. 
* En la segunda clase, `Zebra`, la primera declaración del primer constructor es una llamada al constructor de `Animal`, que toma un solo argumento. 
* La clase `Zebra` también incluye un segundo constructor sin argumentos que no llama a `super()` sino que en su lugar llama al otro constructor dentro de la clase `Zebra` usando `this(4)`.

* Como llamar a `this()`, llamar a `super()` solo puede usarse como la primera declaración del constructor. 
* Por ejemplo, las siguientes dos definiciones de clase no compilarán:

```java
public class Zoo {
  public Zoo() {
    System.out.println("Zoo created");
    super();  // DOES NOT COMPILE
  }
}

public class Zoo {
  public Zoo() {
    super();
    System.out.println("Zoo created");
    super();  // DOES NOT COMPILE
  }
}
```

* La primera clase no compilará porque la llamada al constructor padre debe ser la primera declaración del constructor. 
* En el segundo fragmento de código, `super()` es la primera declaración del constructor, pero también se usa como la tercera declaración. 
* Dado que `super()` solo puede llamarse una vez como la primera declaración del constructor, el código no compilará.

Si la clase padre tiene más de un constructor, la clase hija puede usar cualquier constructor padre válido en su definición, como se muestra en el siguiente ejemplo:

```java
public class Animal {
  private int age;
  private String name;
  public Animal(int age, String name) {
    super();
    this.age = age;
    this.name = name;
  }
  public Animal(int age) {
    super();
    this.age = age;
    this.name = null;
  }
}

public class Gorilla extends Animal {
    public Gorilla(int age) {
        super(age,"Gorilla"); // Calls the first Animal constructor
    }
    public Gorilla() {
        super(5);      // Calls the second Animal constructor
    }
}
```

* En este ejemplo, el primer constructor hijo toma un argumento, `age`, y llama al constructor padre, que toma dos argumentos, `age` y `name`. 
* El segundo constructor hijo no toma argumentos, y llama al constructor padre, que toma un argumento, `age`. 
* En este ejemplo, observa que los constructores hijo no están obligados a llamar constructores padre coincidentes. 
* Cualquier constructor padre válido es aceptable siempre que se proporcionen los parámetros de entrada apropiados al constructor padre.

### Understanding Compiler Enhancements

* Java inserta automáticamente una llamada al constructor sin argumentos `super()` si no llamas explícitamente a `this()` o `super()` como la primera línea de un constructor. 
* Por ejemplo, las siguientes tres definiciones de clase y constructor son equivalentes, porque el compilador automáticamente las convertirá todas al último ejemplo: 

```java
public class Donkey {}

public class Donkey {
  public Donkey() {}
}

public class Donkey {
  public Donkey() {
    super();
  }
}
```

* Asegúrate de comprender las diferencias entre estas tres definiciones de clase `Donkey` y por qué Java las convertirá automáticamente todas a la última definición. 
* Mientras lees la siguiente sección, ten en mente el proceso que el compilador de Java realiza.

### Default Constructor Tips and Tricks

* Hemos presentado muchas reglas hasta ahora, y podrías haber notado algo. Digamos que tenemos una clase que no incluye un constructor sin argumentos. 
* ¿Qué sucede si definimos una subclase sin constructores, o una subclase con un constructor que no incluye una referencia a `super()`?

```java
public class Mammal {
  public Mammal(int age) {}
}

public class Seal extends Mammal {} // DOES NOT COMPILE

public class Elephant extends Mammal {
  public Elephant() {}     // DOES NOT COMPILE
}
```

* La respuesta es que ninguna subclase compila. Dado que `Mammal` define un constructor, el compilador no inserta un constructor sin argumentos. 
* El compilador insertará un constructor sin argumentos por defecto en `Seal`, sin embargo, pero será una implementación simple que solo llama a un constructor padre por defecto inexistente.

```java
public class Seal extends Mammal {
  public Seal() {
    super(); // DOES NOT COMPILE
  }
}
```

* Del mismo modo, `Elephant` no compilará por razones similares. 
* El compilador no ve una llamada a `super()` o `this()` como la primera línea del constructor, así que inserta una llamada a un `super()` sin argumentos inexistente automáticamente.

En estos casos, el compilador no ayudará, y must (debes) crear al menos un constructor en tu clase hija que llame explícitamente a un constructor padre vía el comando `super()`.

```java
public class Seal extends Mammal {
  public Seal() {
    super(6); // Explicit call to parent constructor
  }
}

public class Elephant extends Mammal {
  public Elephant() {
    super(4); // Explicit call to parent constructor
  }
}
```

* Las subclases pueden incluir constructores sin argumentos incluso si sus clases padre no. 
* Por ejemplo, lo siguiente compila porque `Elephant` incluye un constructor sin argumentos:

`public class AfricanElephant extends Elephant {}`

Para el examen, deberías ser capaz de detectar inmediatamente por qué clases como nuestras primeras implementaciones de `Seal` y `Elephant` no compilaron.

Concluimos esta sección agregando tres reglas de constructor a tu conjunto de habilidades:

* La primera línea de cada constructor es una llamada a un constructor padre usando `super()` o a un constructor sobrecargado usando `this()`.
* Si el constructor no contiene una referencia a `this()` o `super()`, entonces el compilador inserta automáticamente `super()` sin argumentos como la primera línea del constructor.
* Si un constructor llama a `super()`, entonces debe ser la primera línea del constructor.

## Initializing Objects

### Initializing Classes

* Comenzamos nuestra discusión sobre el orden de inicialización con la inicialización de clases. 
* Primero, inicializamos la clase, lo que implica invocar a todos los miembros estáticos en la jerarquía de clases, comenzando con la superclase más alta y trabajando hacia abajo. 
* Esto a veces se conoce como loading (cargar) la clase. La Java Virtual Machine (JVM) controla cuándo se inicializa la clase, aunque puedes asumir que la clase se carga antes de que se use. 
* La clase puede ser inicializada cuando el programa comienza primero, cuando un miembro estático de la clase es referenciado, o poco antes de que una instancia de la clase sea creada.

* Una de las reglas más importantes con la inicialización de clases es que ocurre como máximo una vez para cada clase. 
* La clase también puede nunca cargarse si no se usa en el programa. Resumimos el orden de inicialización para una clase de la siguiente manera:

**Initialize Class X**

1. Si hay una superclase Y de X, entonces inicializa la clase Y primero.
2. Procesa todas las declaraciones de variables estáticas en el orden en que aparecen en la clase.
3. Procesa todos los inicializadores estáticos en el orden en que aparecen en la clase.

Echando un vistazo a un ejemplo, ¿qué imprime el siguiente programa?

```java
public class Animal {
  static { System.out.print("A"); }
}

public class Hippo extends Animal {
  public static void main(String[] grass) {
    System.out.print("C");
    new Hippo();
    new Hippo();
    new Hippo();
  }
  static { System.out.print("B"); }
}
```

* Imprime **ABC** exactamente una vez. 
* Dado que el método `main()` está dentro de la clase `Hippo`, la clase será inicializada primero, comenzando con la superclase e imprimiendo **AB**. 
* Después, el método `main()` es ejecutado, imprimiendo **C**. Aunque el método `main()` crea tres instancias, la clase se carga solo una vez.








---------------------------------------------------------------------
**Palabra** cuando es una palabra en inglés importante que tiene sentido traducirla, pero no es una palabra reservada

() version en ingles de la palabra anterior

`   `  solo cúando es una línea de código o una palabra reservada que va a ser explicada

```java

```


Inheriting Members
Creating Abstract Classes
Creating Immutable Objects