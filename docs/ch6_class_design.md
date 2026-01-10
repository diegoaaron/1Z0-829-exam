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





---------------------------------------------------------------------
**Palabra** cuando es una palabra en inglés importante que tiene sentido traducirla, pero no es una palabra reservada

() version en ingles de la palabra anterior

`   `  solo cúando es una línea de código o una palabra reservada que va a ser explicada

```java

```



Initializing Objects
Inheriting Members
Creating Abstract Classes
Creating Immutable Objects