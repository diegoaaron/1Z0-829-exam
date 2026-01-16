# Cap. 07 - Beyond Classes

## Interfaces

Tipo de dato abstracto que define una lista de métodos abstractos en la que cualquier clase que implemente la interfaz debe aprovisionarlos.

![ch07_01.png](images/ch07/ch07_01.png)

```java
public abstract interface CanBurrow {
    public abstract Float getSpeed(int age);

    public static final int MINIUM_DEPTH = 1;
}
```

* una interfaz solo puede contener métodos abstractos, estáticos, por defecto y constantes.
* una interfaz puede estar totalmente vacía.
* una interfaz siempre es considerada abstracta.
* una interfaz tiene modificadores implícitos (el compilador los inserta automáticamente en el código) a diferencia de las clases abstractas.
* una interfaz no requiere tener definido ningún método para existir.

```java
// compila
public abstract interface WalksOnTwoLegs {}

public class Biped {
    public static void main(String[] args) {
        // no compila porque no una interfaz no puede ser instanciada 
        var e = new WalksOnTwoLegs();

    }
}

// no compila porque una interfaz no puede ser final (marcarla asi indicaria que no se puede utilizar por otra clase)
public final interface WalksOnEightLegs {}
```

* Una clase puede usar (implementar) las interfaces a través de la palabra reservada `implements`.
* Una clase puede implementar múltiples interfaces, separándolas con una `,` coma.

![ch07_02.png](images/ch07/ch07_02.png)

```java
public interface Climb {
    Number getSpeed(int age); // tiene modificador 'public' implícito
}

public class FieldMouse implements Climb, CanBurrow {

    // implementación del método de la interfaz Climb debe declarar explicitamente 'public' para funcionar
    public Float getSpeed(int age) {
        return 11f; // retorna un tipo de dato covariante
    }
}

// La covarianza en tipos de retorno significa que cuando una subclase o implementación sobrescribe un método, 
// puede devolver un tipo de dato más específico (una subclase) del tipo declarado originalmente.
// en este caso, FieldMouse implementa el método getSpeed de la interfaz Climb, devolviendo un Float, 
// cuando el método original devuelve un Number.
```

* Una interfaz puede ser aumentada (extendida) por otra utilizando la palabra reservada `extends`.
* Una interfaz puede extenderse utilizando otras interfaces, separándolas con una `,` coma.

```java
public interface Nocturnal {
    public int hunt();
}
public interface CanFly{
    public void flap();
}

public interface HasBigEyes extends Nocturnal, CanFly {}

public class Owl implements HasBigEyes {
    public int hunt() {return 5;}
    public void flap() {System.out.println("Flap!");}
}
```

```java
public interface HasTail{
    public int getTailLength();
}

public interface HasWhiskers {
    public int getNumberOfWhiskers();
}

public abstract class HarborSeal extends HasTail, HasWhiskers {
    // No necesita implementar los métodos aqui ya que es una clase abstracta
}

// No compila porque CommonSeal no implementa los métodos de la clase abstracta HarborSeal
public class CommonSeal extends HarborSeal {} 
}
```
* Una clase puede implementar una interfaz, pero una clase no puede extender una interfaz
* Una interfaz puede extender otra interfaz, pero una interfaz no puede implementar otra interfaz

```java
public interface CanRun {}
public class Cheetah extends CanRun{} // No compila porque la clase Cheetah no puede extender una interfaz

public class Hyena {}
public interface HasFur extends Hyena {} // No compila porque la interfaz HasFur no puede extender una clase
```

Java acepta la herencia de dos métodos abstractos que tienen declaraciones de métodos compatibles (métodos que se pueden anular por uno solo, ya que se puede usar un tipo de retorno covariante)

```java
public interface Herbivore {
    public void eatPlants();
}

public interface Omnivore {
    public void eatPlants();
}

public class  Bear implements Herbivore, Omnivore {
    public void eatPlants() {
        System.out.println("Eating plants");
    }
}

public interface CanBark {
    public void makeSound();
}

public interface CanHowl {
    public integer makeSound();
}

// no compila porque existe forma de implementar ambos métodos con el mismo nombre y tipo de retorno
public class Wolf implements CanBark, CanHowl {}
```

Una interfaz puede tener modificadores implícitos los cuales son: 

* `abstract` es por defecto en las interfaces
* `public`, `static`, `final` son por defecto en las variables de una interfaz
* Los métodos de las interfaces con un cuerpo vació son `abstract` por defecto
* Los métodos de una interfaz sin el modificador `private` son implícitamente `public`
  * Esto aplica a los métodos abstractos, estáticos y por defecto

En el siguiente fragmento de código, se muestra cómo el compilador agrega los modificadores implícitos a la interfaz:

```java
public interface Soar {
    int MAX_HEIGHT = 1000; 
    static final boolean UNDERWATER = true; 
    void fly(int speed); 
    abstract void takeOff(); 
    public abstract double dive();
}

public abstract interface Soar {
    public static final int MAX_HEIGHT = 1000;
    public static final boolean UNDERWATER = true;
    public abstract void fly(int speed);
    public abstract void takeOff();
    public abstract double dive();
}
```

Si intentamos marcar un método o variable con un modificador que entraría en conflicto con el modificador implícito, la compilación fallará.

```java
public interface Dance {
    private count =4; // No compila porque private no es un modificador válido para una variable de interfaz
    protected void step(); // No compila porque protected no es un modificador válido para un método de interfaz
}
```

**Diferencias entre interfaces y clases abstractas**

* Solo las interfaces utilizan modificadores implícitos (aunque ambos son de tipo abstracto)

```java
abstract class Husky {
    abstract void play();
}

interface Poodle {
    void play();
}

// Ambas definiciones de métodos se consideran abstractas pero la clase Husky no se compilara si el método play() 
// no está marcado como abstract, mientras que el método en la interfaz Poodle se compilará con o sin el modificador abstract.

public class Webby extends Husky {
    void play() {}
}

public class Georgette implements Poodle {
    void play() {} 
}

// el métotdo play() de Webby al ser de tipo paquete, compilara ya que es producto de una herencia mientras que 
// el método play() de Georgette no compilará ya que es producto de una implementación de una interfaz y debe ser público.
```

### Métodos concretos en las interfaces

Las interfaces contemplas más elementos que solo métodos abstractos y constantes, los cuales se listan a continuación:

|                       | Tipo de Miembro | Modificador requerido | Modificador implícito | Tiene cuerpo o valor |
|-----------------------|-----------------|-----------------------|-----------------------|----------------------|
| Constant variable     | Class           | —                     | public, static, final | Yes                  |
| abstract method       | Instance        | —                     | public, abstract      | No                   |
| default method        | Instance        | default               | public                | Yes                  |
| static method         | Class           | static                | public                | Yes                  |
| private method        | Instance        | private               | —                     | Yes                  |
| private static method | Class           | private, static       | —                     | Yes                  |

_**¿Qué pasa con los miembros de tipo paquete o protegido para una interfaz?**_

* El modificador protected existe para permitir acceso a subclases (herencia). Como las interfaces no se extienden, sino que se implementan, el concepto de protected no tiene sentido en este contexto.
* Las interfaces no soportan package access (el nivel de acceso sin modificador), porque todos los métodos sin modificador en interfaces son implícitamente public, cambiar esto rompería programas.

#### Método por defecto de una interfaz

Un método por defecto usando la palabra clave `default` permite a las interfaces proporcionar una implementación predeterminada para los métodos, y esta ser modificada por la clase que lo implementa si en verdad requiere utilizarla.
Esto ayuda a tener que modificar implementaciones de interfaces sin romper el código existente.

```java
public interface IsColdBlooded {
  boolean hasScales();

  default double getTemperature() {
    return 10.0;
  }

  default double getAltitude() {
    return 1000.0;
  }
}

public class Snake implements IsColdBlooded {

  public boolean hasScales() {
    return true;
  }
  
  public double getTemperature() {
    return 20.0;
  }
  // No es necesario implementar getAltitude() porque ya tiene una implementación por defecto
}
```

**Reglas para los métodos por defecto:**

* Un método por defecto solo puede ser declarado dentro de una interfaz.
* Un método por defecto debe ser etiquetado con la palabra clave `default` y tener un cuerpo.
* Un método por defecto es implícitamente `public`.
* Un método por defecto no puede ser marcado como `abstract`, `static` o `private`.
* Un método por defecto puede ser sobrescrito por una clase que implementa la interfaz, pero no es obligatorio hacerlo.
* Si una clase hereda dos o más métodos por defecto con la misma firma, la clase debe sobrescribir el método.

```java
// esto no compila porque combina el comportamiento de un metodo por defecto y un metodo concreto. 
public interface Carnivore {
    public default void eatMeat();
    public int getRequiredFoodAmount(){
        return 13;
    }
}

// la clase Cat no compilara porque por regla debe implementar el método getSpeed() mientras que Dog si.
public interface Walk {
    public default int getSpeed() {
        return 5;
    }
}

public interface Run {
    public default  int getSpeed() {
        return 10;
    }
}

public class Cat implements Walk, Run {}

public class Dog implements Walk, Run {
    public int getSpeed() {
        return 1;
    }
    
    // Si queremos acceder al método por defecto de una interfaz que se sobreescribe, debemos usar la siguiente sintaxis:
    public int getWalkSpeed() {
        return Walk.super.getSpeed();
    }
}
```

#### Método estático de una interfaz

Estos métodos se definen con la palabra clave static y casi siempre se comportan igual que los métodos estáticos definidos en las clases.

**Reglas para los métodos estáticos:**

* Un método estático debe ser marcado con la palabra `static` y debe tener un cuerpo
* Un método estático sin un modificador de acceso es implícitamente `public` (se puede usar un modificador de acceso `private`)
* Un método estático no puede ser marcado como `abstract` o `final`
* Un método estático no se puede heredar y no se puede acceder a él en una clase que implementa la interfaz sin una referencia al nombre de la interfaz

```java
public interface Hop {
    static int getJumpHeight() {
        return 8;
    }
}

public class Skip {
    public int skip(){
        return Hop.getJumpHeight(); 
    }
}

public class Bunny implements Hop {
    public void printDetails() {
        // No se puede acceder al método estático de la interfaz Hop desde una instancia de Bunny
        // System.out.println(getJumpHeight()); // Esto no compila
        System.out.println(Hop.getJumpHeight()); // Debe ser llamado con el nombre de la interfaz
    }
}
```
#### Método privado de una interfaz

Los métodos privados y los métodos de interfaz estáticos privados solo se pueden usar dentro de la interfaz donde se declararon y su uso es principalmente para reducir la duplicación de código.

```java
public interface Schedule {
    default void wakeUp() {checkTime(7);}
    private void haveBreakfast() {checkTime(7);}
    static void workOut() {checkTime(18);}
    private static void checkTime(int hour) {
        if (hour > 17) {
            System.out.println("Estas llegando tarde al trabajo!");
        } else {
            System.out.println("Tienes tiempo para un café.");
        }
    }
}
// Podrías escribir esta interfaz sin usar un método privado copiando el contenido del método checkTime() donde lo requieras, 
//  pero de esta forma será más fácil de leer, ya que no se está repitiendo en más lugares. También se podría definir como checkTime() público 
//  pero eso expondría a todos este método entrando en conflicto con el principio de encapsulación.
```

La diferencia entre un método privado no estático y uno estático es que el primero se puede llamar desde métodos default u otros privados no estáticos de la interfaz. Mientras que el privado estático puede ser llamado desde métodos static, default y cualquier otro privado.

**Reglas para los métodos privados:**

* Un método de interfaz privado debe estar marcado como `private` e incluir un cuerpo de método.
* Un método de interfaz estático privado puede ser llamado por cualquier método dentro de la definición de la interfaz.
* Un método de interfaz privado solo puede ser llamado por un método por defecto otros métodos privados no estáticos dentro ode la interfaz.

_Para ambos tipos de métodos privados, una clase que implementa la interfaz no puede invocarlos directamente_

**Llamar a métodos abstractos** 

Los métodos `default` y `private` no-estáticos pueden llamar a métodos abstractos de la misma interfaz. Esto es posible porque estos métodos se ejecutan en el contexto de una instancia concreta de una clase que implementa la interfaz. Cuando se invoca un método default, ya existe un objeto real que tiene implementaciones concretas de todos los métodos abstractos.

```java
public interface ZooRenovation {
    public String projectName();
    abstract String status();
    
    default void printStatus() {
        System.out.println("Project: " + projectName() + ", Status: " + status());
    }
}

// En este ejemplo, tanto projectName() como status() tiene los mismos modificadores 
// y se pueden llamar mediante el método predeterminado printStatus()
```

#### Comentarios finales de la sección 

* Trata los métodos privados, abstractos, predeterminados y no estáticos como pertenecientes a una instancia de la interfaz.
* Trata los métodos y variables estáticos como pertenecientes al objeto de clase de la interfaz. 
* Todos los tipos de métodos de interfaz privados solo son accesibles dentro de la declaración de la interfaz.

## Enumeraciones

En programación, es común tener variables con un grupo específico de valores como días de la semana o estaciones del año, una enumeración es un conjunto fijo de constantes.

En una enumeración simple el `;` al final es opcional.

Las enumeraciones se consideran constantes por lo que se escriben usando el formato SNAKE_CASE.

* Las enumeraciones son como constantes estáticas y finales, ya que se inicializan solo una vez en la JVM
* No se puede extender una enumeración, sus valores son fijos.

```java
public enum Season {
    VERANO, OTONO, INVIERNO, PRIMAVERA;
}

var s = Season.VERANO;
System.out.println(s); // Imprime: VERANO
System.out.println(Season.VERANO); // Imprime: VERANO
System.out.println(s == Season.VERANO); // Imprime: true

public enum NuevasSeason extends Season {} // No compila, ya que no se puede extender una enumeración
```

Las enumeraciones tienen métodos implícitos como `values()`, `name()`, `ordinal()` y  `valueOf()`

No se puede comparar un entero con un valor de enumeración, ya que las enumeraciones son un propio tipo y los enteros son primitivos.

Al trabajar con código antiguo podemos obtener el valor de una enumeración pasando una cadena como parámetro al método `valueOf()`, el cual devuelve una instancia de la enumeración correspondiente.

```java
for (var season : Season.values()) {
    System.out.println(season.name() + " - " + season.ordinal());
}

// Imprime: VERANO - 0
// Imprime: OTONO - 1
// Imprime: INVIERNO - 2
// Imprime: PRIMAVERA - 3

if(Season.VERANO == 2) {} // No compila, ya que no se puede comparar un valor de enumeración con un entero

Season s = Season.valueOf("VERANO");  // Devuelve el objeto Season.VERANO
Season t = Season.valueOf("verano"); // Devuelve un error IllegalArgumentException ya que no existe un valor de enumeración "verano" en minusculas 
```

### Usando enumeraciones con sentencias SWITCH

En un switch con enumeraciones, Java va a inferir que estás usando un tipo de enumeración por lo que se puede usar directamente el nombre de la enumeración en un switch.

```java
Season summer = Season.VERANO;

switch (summer) {
    case VERANO:
        System.out.println("Es verano");
        break;
    case OTONO:
        System.out.println("Es otoño");
        break;
    default:
        System.out.println("No es verano ni otoño");
        break;
        }
        
Season summer = Season.VERANO;
var message = switch (summer) {
    case Season.VERANO -> "Es verano"; // no compila, ya que no es necesario el nombre de la enumeración
    case 0 -> "Es otoño"; // No compila, ya que no se puede comparar un valor de enumeración con un entero
    default -> "No es verano ni otoño";
};
```

### Agregando constructores, campos y métodos a las enumeraciones

Una enumeración básica solo tiene valores, una compleja puede tener elementos adicionales.

```java
// La siguiente enumeración se crea porque se quiere tener un seguimiento de los patrones de tráfico por estación.

public enum Season {
    WINTER("Low"), SPRING("Medium"), SUMMER("High"), AUTUMN("Medium");
    
    private final String expectedVisitors;
    
    private Season(String expectedVisitors){
        this.expectedVisitors = expectedVisitors;
    }
    
    public void printExpectedVisitors() {
        System.out.println(expectedVisitors);
    }
}

// La línea 2 define los valores de enumeración y termina con `;` ya que hay más información.

// La línea 4 tiene la variable de instancia como `private` y `final` para que la propiedad no se pueda modificar, 
// ya que es lo correcto porque las enumeraciones son inmutables y solo debe haber una en la JVM.

// Todos los constructores de enumeración son implícitamente privados (ya que no se puede extender una enumeración, 
// los constructores solo se pueden llamar desde dentro y si no es privado no se compilará).

// Los parentesis en la línea 2 son llamadas al constructor pero sin la palabra clave `new`. La primera vez que solicitamos 
// cualquiera de los valores de la enumeración, Java construye todos los valores, después simplemente devuelve los valores construidos.
```

```java
// ¿Por qué no se imprime nada para o2?
public enum OnlyOne {
    ONCE(true);
    private OnlyOne(boolean b) {
        System.out.println("construccion");
    }
}

public class PrintTheOne {
    public static void main(String[] args) {
        System.out.println("inicio");
        OnlyOne o1 = OnlyOne.ONCE; // Imprime: "construccion" porque se ejecuto el constructor de la enumeración
        OnlyOne o2 = OnlyOne.ONCE; // No imprime nada, ya que solo se hace referencia al valor ya creado y no se vuelve a ejecutar el constructor
        System.out.println("fin");
    }
}
```
Como llamamos a un método de una enumeración, se puede usar el nombre de la enumeración seguido del método.

```java
Season.SUMMER.printExpectedVisitors(); // Imprime: High
```
También es posible definir diferentes métodos para cada enumeración

```java
// Que paso, parece una clase abstracta y un montón de subclases. La enumeración tiene definido un método abstracto. 
// Esto significa que cada valor de enumeración debe implementar este método sino se tiene un error de compilación.

public enum Season {
    WINTER {
        public String getHours() {
            return "9am - 5pm";
        }
    },
    SPRING {
        public String getHours() {
            return "8am - 6pm";
        }
    },
    SUMMER {
        public String getHours() {
            return "10am - 8pm";
        }
    },
    FALL {
        public String getHours() {
            return "9am - 7pm";
        }
    };
    public abstract String getHours(); // Método abstracto que cada enumeración debe implementar
}

// Que pasa si queremos que todos y cada uno de los valores de la enumeración tengan un método, 
// podemos crear el método para el que se quieran. Solo codificamos los casos especiales y dejamos que los demás 
// usen la implementación proporcionada por la enumeración. 

public enum Season {
    WINTER {
        public String getHours() {
            return "9am - 5pm";
        }
    },
    SUMMER {
        public String getHours() {
            return "8am - 6pm";
        }
    },
    SPRING, FALL {
        public String getHours() {
            return "10am - 8pm";
        }
    }
}

// Una enumeración puede incluso implementar una interfaz, ya que esto solo requiere sobrecargar métodos abstractos.

public interface Weather { int getAverageTemperature(); }
public enum Season implements Weather {
    WINTER, SPRING, SUMMER, FALL;
    public int getAverageTemperature() {
        return 30;
    }
}

// El hecho de que una enumeración pueda tener muchos métodos nos signifiica que deba tenerlos. 
// Intenta que tus enumeraciones sean simples.
```

La lista de valores de enumeración siempre van la inicio.

## Clases Selladas - Sealing Classes

Una clase sellada es una clase que restringe qué otras clases pueden extenderla directamente.

```java
public sealed class Bear permits Kodiak, Panda {} // solo Kodiak y Panda pueden extender Bear

public final class Kodiak extends Bear {} // Kodiak es una clase final, no puede ser extendida por otras clases

public non-sealed class Panda extends Bear {} //Otras clases sí pueden extender de Panda sin restricciones
```

**Palabras clave asociadas:**

- `sealed`: Define una clase sellada.
- `non-sealed`: Permite que una clase sellada sea extendida sin restricciones.
- `permits`: Especifica qué clases pueden extender una clase sellada.

```java
public class sealed Frog permits GlassFrog {} // no compila porque sealed esta despues de class
public final class GlassFrog extends Frog {} 

public abstract sealed class Wolf permits Timber{}
public final class Timber extends Wolf {}
public final class MyWolf extends Wolf {} // no compila porque MyWolf no esta en la lista de clases permitidas
```

Las clases selladas son declaradas comúnmente con el modificador `abastract` aunque no es obligatorio

### Compilación de clases selladas

Si intentamos compilar la siguiente clase sellada sin ningún código:

```java
// Penguin.java
package zoo;
public sealed class Penguin permits Emperor {}

// No compilará ya que una clase sellada debe declararse y compilarse en el mismo paquete que sus sub-clases directas
```

Si intentamos compilar la sub-clase declarada y la clase sellada en el mismo paquete:

```java
// Penguin.java
package zoo;
public sealed class Penguin permits Emperor {}

// Emperor.java
package zoo;
public final class Emperor {}

// No compilara porque Emperor no extiende Penguin
```

### Especificaciones para el modificador de las sub-clases

Mientras que en las interfaces se puede tener un cierto número de modificadores implícitos, en las clases selladas cada subclase debe tener uno de los siguientes modificadores: `final`, `non-sealed` o `abstract`.

**Una sub-clase `final`**

Este modificador se puede aplicar directamente a una sub-clase sellada

Al igual que una clase regular, el modificador `final` impide que la subclase Gazelle se extienda más

```java
public sealed class Antelope permits Gazelle {}

public final class Gazelle extends Antelope {} 

public class George extends Gazelle {} // no compila no se puede extender una clase final
```

**Una sub-clase `sealed`**

Al utilizar `sealed` en una sub-clase debemos seguir las reglas de sellado inicial que consiste en definir su propia lista de sub-clases permitidas.

```java
public sealed class Mammal permits Equine {}

public sealed class Equine extends Mammal permits Zebra {}

public final class Zebra extends  Equine {}

// El compilador permitira que Mammal acepte objetos de tipo Zebra a pesar de que no este en el permits de Mammal
```

**Una sub-clase `non-sealed`**

Esto permite que una clase esté abierta a cualquier clase y sub-clases

```java
public sealed class Wolf permits Timber {}

public non-sealed class Timber extends Wolf {}

public class MyWolf extends Timber {}

public class MyFurryWolf extends MyWolf {}

// Para este ejemplo, MyFurryWolf se considera una sub-clase de Wolf gracias al polimorfismo (una instancia de una subclase puede ser tratada como una instancia de su superclase) ya que no-sealed permite que Timber sea extendida sin restricciones.
```

**Omitiendo la palabra clave `permits`**

Hasta ahora todos los ejemplos que has visto han requerido una cláusula `permits` al declarar una clase sellada, pero no siempre será necesario

```java
// Snake.java
public sealed class Snake {}

final class Cobra extends Snake {} // no se necesita porque estan en el mismo archivo

// Snake.java
public sealed class Snake {
    final class Cobra extends Snake {} // no se necesita porque es una clase anidada 
}

// Si de forma explicita en una clase sellada como en sus sub-clases anidadas usamos permits, deberemos referenciar al espacio de nombres de la clase principal

public sealed class Snake permits Snake.Cobra {
    final class Cobra extends Snake {} 
}
```

Necesitamos saber que una clase anidada es una clase definida dentro de otra clase y que la regla de omisión también se aplican a estas.

| Ubicación de las sub-clases directas     | palabra `permits`            |
|------------------------------------------|------------------------------|
| Archivo diferente al de la clase sellada | Requerido                    |
| En el mismo archivo que la clase sellada | Permitido, pero no requerido |
| Anidado dentro de la clase sellada       | Permitido, pero no requerido |

### Sellado de interfaces

Además de las clases, las interfaces también se pueden sellar. La idea es análoga a la de las clases, y se aplican muchas de las mismas reglas. Por ejemplo, la interfaz sellada debe aparecer en el mismo paquete o módulo con nombre que las clases o interfaces que la extienden o implementan directamente.

Una característica distintiva de una interfaz sellada es que la lista de permisos puede aplicarse a una clase que implementa la interfaz o a una interfaz que la extiende.

```java
// interfaz sellada
public sealed interface Swims permits Duck, Swan, Floats {}

// Clases que implementan la interfaz sellada
public final class Duck implements Swims {}
public final class Swan implements Swims {}

// Interfaz que extiende la interfaz sellada
public non-sealed interface Floats extends Swims {}
```

Hay que tener en cuenta que las interfaces son implícitamente abstractas y no se pueden marcar como finales por lo cual las interfaces que extienden una interfaz sellada solo pueden ser selladas o no selladas.

**Revisando las reglas de las clases selladas**

* Las clases selladas se declaran con los modificadores `sealed` y `permits`.
* Las clases selladas deben declararse en el mismo paquete o módulo que sus subclases directas.
* Las subclases directas de las clases selladas se deben marcarse como `sealed`, `no-sealed` o `final`.
* La cláusula `permits` es opcional si la clase sellada y sus subclases directas se declaran dentro del mismo archivo o si las subclases están anidadas dentro de la clase sellada.
* Las interfaces se pueden sellar para limitar las clases que las implementan o las interfaces que las extienden. 


## Registros - Records

Una clase sellada es una clase que restringe qué otras clases pueden extenderla directamente.

Antes de avanzar debemos entender el concepto de encapsulación, para eso debemos saber que es un **POJO "Plan Old Java Object"** el cual es una clase usada pra modelar y pasar datos sencillos.

```java
public class Crane {
    int numbersEggs;
    String name;
    
    public Crane(int numbersEggs, String name) {
        this.numbersEggs = numbersEggs;
        this.name = name;
    }
}

// Los campos son de acceso al paquete, lo que signfica que desde otra clase del mismo paquete podrían cambiar valores y crear datos no válidos como: 

public class Poacher {
    public void badActor() {
        var mother = new Crane(5, "Cathy");
        mother.numbersEggs = -100;
    }
}

// Esto claramente no es bueno por lo que podemos recurrir a la encapsulación, es una forma de proteger a los miembros de 
// la clase al restringir el acceso a ellos. la encapsulación nos permite modificar los métodos y el comportamiento de la 
// clase posteriormente sin que alguien tenga acceso directo a una variable de instancia dentro de la clase. Por ejemplo, 
// podemos cambiar el tipo de datos de una variable de instancia, pero manteenr las mismas firmas del método con lo 
// cual mantenemos el control total sobre el funcionamiento interno de la clase. Por ejemplo la clase Crane podría quedar así:

public final class Crane {
    private final int numbersEggs;
    private final String name;

    public Crane(int numbersEggs, String name) {
        if (numbersEggs >= 0) this.numbersEggs = numbersEggs;
        else throw new IllegalArgumentException();
        this.name = name;
    }

    public int getNumbersEggs() {
        return numbersEggs;
    }

    public String getName() {
        return name;
    }
}

// Esto nos asegura tener una clase inmutable (gracias a la definición final en la clase y variables, la ausencia de 
// setter y tipos de datos int y string) y encapsulada (gracias a la definición private que no deja un acceso directo 
// a las vairables desde afuera, final para que no sean reasiganda  una valor despues de inicializarlo, 
// metodos getter que dan un acceso controlado a las variables, final en la clase que protege la implementación de herencia) 


// En el siguiente ejemplo, podemos ver como siempre que hagamos privadas las variables de instancia la clase se encapsulara bien

public class Vet {
    private String name = "Dr. Smith";
    private int yearsOfExperience = 10;
}
```

### Definiendo un registro

`public record Crane(int numbersEggs, String name) {}`

La línea anterior crea un registro con el cual el compilador inserta la implementación de las variables asi como los métodos de objeto `equals()`, `hashCode()` y `toString()`.

Ya que por defecto el compilador inyecta un constructor con el mismo orden de los parámetros al crear el registro, si se crea una instancia sin respetar ese orden u omitiendo un valor se genera un error de compilación.

```java
var mommy = new Crane(4, "Cathy"); // Correcto
System.out.println(mommy.numbersEggs()); // 4
System.out.println(mommy.name()); // Cathy

var mommy = new Crane("Luis", 5); // Error
var mommy = new Crane(5); // Error
```

Elementos agregados automáticamente por el compilador al definir un registro:

* Constructor: un constructor público con los parámetros en el mismo orden que se definieron en el registro.
* Métodos de acceso: métodos públicos con el mismo nombre que los componentes del registro (para cada variable).
* equals(): un método que compara dos instancias del registro para ver si son iguales.
* hashCode(): un método que devuelve un valor hash para la instancia del registro.
* toString(): un método que devuelve una representación de cadena de la instancia del registro.

```java
var father = new Crane(3, "Bob");
System.out.println(father); // Crane[numbersEggs=3, name=Bob]

var copy = new Crane(3, "Bob");
System.out.println(copy); // Crane[numbersEggs=3, name=Bob]

System.out.println(father.equals(copy)); // true (porque son records y tienen el mismo contenido inmutable)
System.out.println(father.hashCode() + "," + copy.hashCode()); // 1007, 1007
```

Es permitido crear un registro sin ningún componente, aunque no es muy útil.

`public record Empty() {}`

### Entendiendo la inmutabilidad de los registros

Ya que los registros no tienen setters y no se pueden modificar de ninguna forma después de su creación, la unica opción es crear otro objeto.

```java
var cusin = new Crane(2, "Anna");
var friend = new Crane(cousin.numberEggs(), "Anna");
```

Teniendo como analogía que las interfaces son implícitamente abstractas, los registros son implícitamente finales. El modificador final es opcional.

```java
public final record Crane(int numbersEggs, String name) {}
```

También al igual que las enumeraciones eso significa que no puedes extender ni heredar un registro.

```java
public record BlueCrane() extends Crane {} // No compila
```

Al igual que las enumeraciones, un registro puede implementar una interfaz regular o sellada siempre que implemente todos los métodos abstractos.

```java
public interface Bird {}
public record Crane(int numbersEggs, String name) implements Bird {}
```

### Creando constructores

Podemos definir la creación de un constructor dentro del registro de forma denominado;

#### El constructor largo

El cual el compilador lo inserta automáticamente, ya que utiliza todos los campos del registro:

```java
public record Crane(int numbersEggs, String name) {
    public Crane(int numbersEggs, String name) {
        if (numbersEggs < 0) {
            throw new IllegalArgumentException("Number of eggs cannot be negative");
        }
        this.numbersEggs = numbersEggs;
        this.name = name;
    }
}
```

El compilador no insertará un constructor si define uno con la misma lista de parámetros, pero internamente no se incluyen todos los campos del registro.

```java
public record Crane(int numbersEggs, String name) {
    public Crane(int numbersEggs, String name) {} // No compila, ya que no se incluyen todos los campos del registro
}
```

#### El constructor corto

Este tipo es util, ya que permite que los registros procesen la validación y las transformaciones. No requiere que nombremos a los parámetros.

```java
public record Crane(int numbersEggs, String name) {
    public Crane {
        if(numbersEggs <0) {
            throw new IllegalArgumentException("Number of eggs cannot be negative");
        }
        name = name.toUpperCase();
    }
}
```

Si bien los constructores compactos pueden modificar parámetros del constructor, no pueden sobreescribirlos los campos finales (ya que aún no existen al momento de la ejecución del constructor, sino después de completar esta parte).

```java
public record Crane(int numbersEggs, String name) {
    public Crane {
        this.numbersEggs =  10; // No compila
    }
}

public record Crane(int numbersEggs, String name) {
    public Crane {
        numbersEggs =  10; // Si compila
    }
}
```

### Sobrecarga de constructores

También podemos crear constructores sobrecargados que tomen una lista de parámetros diferentes, esto se relaciona directamente con los constructores de forma larga.

```java
// La primera línea de un constructor sobrecargado deb ser una llamada explicita a otro constructor a través de this() 
// si no hay otro se llama al constructor largo.

public record Crane(int numbersEggs, String name) {
    public Crane(String firstName, String lastName) {
        this(0, firstName + " " + lastName); // Llama al constructor largo
    }
}

// A diferencia de los constructores compactos, solo se puede transformar los datos en la primera línea despues todos los campos ya estaran asignados y el objeto es inmutable. 

public record Crane(int numbersEggs, String name) {
    public Crane(String firstName, String lastName) {
        this(0, firstName + " " + lastName); // Llama al constructor largo
        numbersEggs = 10; // no afecta (aplica al parametro pero no al campo de instancia) 
        this.numbersEggs = 20; // No compila, ya que no se puede reasignar un campo final
    }
}

// Tampoco se pueden declarar dos constructores que se llame infinitamente entre si. 

public record Crane(int numbersEggs, String name) {
    public Crane(String name) {
        this(1); // no compila
    }
    public Crane(int numbersEggs) {
        this(""); // no compila
    }
}
```

### Personalizando registros

Los registros aceptan muchos de los modificadores que se pueden aplicar a las clases como:

* Constructores sobrecargados y compactos
* Métodos de instancia, incluyendo la anulación de cualquier método proporcionado (`equals()`, `hashCode()`, `toString()`)
* Clases anidadas, interfaces, anotaciones, enumeraciones y registros

```java
// Como ejemplo lo siguiente anula 2 metodos de instancia utilizando la anotación opcional @Override

public record Crane(int numbersEggs, String name) {
    @Override
    public String numbersEggs() {
        return 10;
    }

    @Override
    public String toString() {
        return name;
    }
}
```

Si bien puedo agregar métodos, campos estáticos y otros tipos de datos, no se puede agregar campos de instancia fuera de la declaración de registro incluso si son privados, ya que esto anulan el propósito de crear un registro y podría romper la inmutabilidad que lo caracteriza.

```java
// Como ejemplo lo siguiente anula 2 metodos de instancia utilizando la anotación opcional @Override

public record Crane(int numbersEggs, String name) {
    private static int type = 10;
    public int size; // No compila, ya que no se pueden agregar campos de instancia fuera de la declaración del registro
    private boolean friendly; // No compila, ya que no se pueden agregar campos de instancia fuera de la declaración del registro
}
```


## Clases anidadas - Nested Classes

Una clase anidada es una clase que se define dentro de otra clase y puede ser de cuatro tipos:

* Clase anidada interna: Un tipo no estático definido en el nivel de miembro de una clase
* Clase anidada estática: Un tipo estático definido en el nivel de miembro de una clase
* Clase local: Una clase definida dentro del cuerpo de un método
* Clase anónima: Un caso especial de una clase local que no tiene nombre.

Entre sus beneficios está la capacidad de definir clases auxiliares y restringir su acceso, mejora la encapsulación, limpieza y legibilidad de código.

### Definiendo una clase anidada interna

Una clase interna "inner class" o clase interna miembro "member inner class" es un tipo no estático definido en el nivel de miembro de una clase (el mismo nivel que los métodos, las variables de instancia y los constructores). Debido a que no son tipos de nivel superior, pueden usar cualquiera de los cuatro niveles de acceso.

* Se pueden declarar públicas, protegidas, de paquete o privadas
* Pueden extender una clase e implementar interfaces
* Pueden marcarse como abstracto o final
* Puede acceder a los miembros de la clase externa, incluyendo los miembros privados

```java
// La linea "greet(greeting);" muestra que la clase interna simplemente se refiere a un "greeting" como si estuviera disponible en la clase Room. 
// Esto funciona porque, de hecho, esta disponible. Aunque la variabl es privada, se accede a ella dentro de esa misma clase.

// Dado que una clase interna no es estática, debe llamarse utilizando una instancia de clase. Esto significa que debe crear dos objetos. 
// La linea 45 crea un objeto externo Home, mientras que la 40 crea una instancia de Room, hay que entender 
// que la linea 40 no requiere una instancia explicita de Home porque es un método de instancia dentro de Home. 
//  Esto funciona porque enterRoom() es un método de instancia dentro de la clase Home. Tanto Room como enterRoom() son miembros de Home.

public class Home {
    private Strring greeting = "Hi"; // variable de clase externa
    
    protected class Room { // declaración de clase interna
        public int repeat = 3;
        public void enter() {
            for(int i = 0; i < repeat; i++) {
                greet(greeting);
            }
        }
        private static void greet(String message) {
            System.out.println(message);
        }
    }
    
    public void enterRoom() {
        var roomm = new Room(); // Instancia de clase interna
        room.enter();
    }
    
    public static void main(String[] args) {
        var home = new Home(); // crea una instancia de la clase externa
        home.enterRoom();
    }
}
```

Hay otra forma de crear una instancia de Room, esta sintaxis no se usa con frecuencia.

```java
public static void main(String[] args) {
    var home = new Home(); // crea una instancia de la clase externa
    var room = home.new Room(); // Instancia de clase interna
    room.enter();
}
```

Desde una instancia de Home podemos directamente crear la instancia de Room, la línea 64 se puede reducir más usando solo la 63 `var home = new Home().new Room().enter();`

**Referencia de miembros en una clase interna**

Las clases internas pueden tener los mismos nombres de variable que las clases externas, lo que hace que el alcance sea un poco confuso. Hay una forma especial de llamar a estos para indicar a qué variable quieres acceder.

```java
// Línea 102: Instancia la clase más externa de forma normal

// Línea 103: Usa la sintaxis "awkward" (incómoda) para instanciar B
// Punto clave: Podrías haber escrito simplemente B como tipo porque está disponible a nivel de miembro de A
// Java sabe dónde buscar la clase B
//
// Línea 104: Aquí SÍ es necesario especificar el tipo completo A.B.C
// C está "demasiado profunda" para que Java sepa dónde buscarla sin la ruta completa
//
// Las líneas del método allTheX() demuestran diferentes formas de acceder a variables

public class A {
    private int x = 10;
    class B {
        private int x = 20;
        class C {
            private int x = 30;
            public void allTheX() {
                System.out.println(x); // 30
                System.out.println(this.x); // 30
                System.out.println(B.this.x); // 20
                System.out.println(A.this.x); // 10
            }
        }
    }
    public static void main(String[] args) {
        A a = new A();
        A.B b = a.new B();
        A.B.C c = b.new C();
        c.allTheX();
    } 
}

// Primera llamada al constructor - SÍ compila:
// ¿Por qué compila?
// goHome() es un método de instancia (no static)
// Cuando se ejecuta, existe un objeto Fox (referenciado por this)
// La clase interna Den puede asociarse con esa instancia de Fox

// Segunda llamada al constructor - NO compila:
// ¿Por qué NO compila?
// visitFriend() es un método static
// Los métodos static no tienen acceso a this (no hay instancia)
// Den es una clase interna que necesita una instancia de Fox para existir
// Solución: Tendrías que crear explícitamente una instancia: new Fox().new Den()

// Tercera llamada al constructor - NO compila (doble problema):
// ¿Por qué NO compila?
// Problema de instancia: Aunque visitFox() es un método de instancia, es de la clase Squirrel, no de Fox
// Problema de visibilidad: Den es private en la clase Fox, por lo que no es accesible desde Squirrel

public class Fox {
    private class Den {}

    public void goHome() {
        new Den();
    }

    public static void visitFriend() {
        new Den(); // DOES NOT COMPILE
    }
}

public class Squirrel {
    public void visitFox() {
        new Den(); // DOES NOT COMPILE
    }
}
```

### Definiendo una clase anidada estática

Una clase anidada estática es un tipo estático definido a nivel de miembro. A diferencia de una clase interna, una clase anidada estática se puede instanciar sin una instancia de la clase envolvente. Sin embargo, la desventaja es que no puede acceder a las variables de instancia o método declarados en la clase externa.

O ea es una clase de nivel superior excepto por:

* La anidación crea un espacio de nombres porque el nombre de la clase envolvente debe usarse para referirse a él.
* Se puede marcar como privado o protegido.
* La clase envolvente puede hacer referencia a los campos y métodos de la clase anidada estática.


```java
// La línea 165 instancia la clase anidada. Dado que la clase es estática, no necesita una instancia de Park para usarla. 
// Puede acceder a variables de instancia privadas, como se ve en la linea 166.

public class Park {
    static class Ride {
        private int price = 6;
    }
    public static void main(String[] args) {
        var ride = new Ride();
        System.out.println(ride.price); // 6
    }
}
```

### Definiendo una clase local

Una clase local es una clase anidada definida dentro de un método. Al igual que las variables locales, una declaración de clase local no existe hasta que se invoca el método y queda fuera del ámbito cuando el método regresa. Esto significa que puede crear instancias solo desde dentro del método. Esas instancias aún pueden devolverse desde el método.

* No tienen un modificador de acceso
* Pueden declararse finales o abstractas
* Tienen acceso a todos los campos y métodos de la clase envolvente (cuando se definen en un método de instancia)
* Pueden acceder a las variables locales finales y efectivamente finales

```java
// La clase local tiene un ámbito que termina en la línea 198. La línea 192 se refiere a una variable de instancia 
// y una variable local final, por lo que se permiten ambas referencias a variables desde dentro de la clase local.

public class PrintNumbers {
    private int length = 5;
    
    public void calculate() {
        final int width = 20;
        
        class Calculator {
            public void multiply() {
                System.out.println(length * width);
            }
        }

        var calculator = new Calculator();
        calculator.multiply();
    }
    
    public static void main(String[] args) {
        var printer = new PrintNumbers();
        printer.calculate(); // 100
    }
}

// Las variables length y height son finales y efectivamente finales (porque nunca se modifican en toda la ejecución), 
// respectivamente, por lo que ninguna causa un problema de compilación. Por otro lado, la variable width se reasigna 
// durante el método, por lo que no puede ser efectivamente final. Por esto la declaración de la clase no compila.

public void processData() {
    final int length = 5;
    int width = 10;
    int height = 2;
    
    class VolumeCalculator {
        public int multiply() {
            return length * width * height; // No compila
        }
    }
    width = 2;
}
```

### Definiendo una clase anónima

Una clase anónima es una forma especializada de una clase local que no tiene nombre. Se declara e instancia en una sola sentencia utilizando la palabra clave `new`, un nombre de tipo entre paréntesis y un conjunto de llaves.

Las clases anónimas deben extender una clase existente o implementar una interfaz existente. Son útiles cuando se tiene una implementación corta que no se utilizará en ningún otro lugar.

```java
// Las líneas 238 a 240 definen una clase abstracta. Las líneas 243 a 247 definen la clase anónima. Observe como esta clase anónima no tiene nombre. 
// El código indica que se debe instanciar un nuevo objeto SaleTodayOnly. Pero SaleTodayOnly es abstracto, esto está bien porque proporcionamos 
// el cuerpo de la clase ahi mismo, de forma anónima. 
// Aqui escribir una clase anónima es equivalente a escribir una clase local con un nombre no especificado que extiende SaleTodayOnly y la usa inmediatamente.
// En la línea 247 usamos punto y coma porque estamos declarando una variable local en estas líneas por lo cual se debe cumplir esa regla.

public class ZooGiftShop {
    abstract class SaleToday {
        abstract int dollarsOff();
    }
    
    public int admission(int basePrice) {
        SaleTodayOnly sale = new SaleTodayOnly() {
            int dollarsOff() {
                return 3;
            }
        }; // debe acabar en punto y coma
        return basePrice - sale.dollarsOff();
    }
}

// Ahora convertimos este ejemplo para implementar una interfaz en lugar de extender una clase abstracta
// Lo más interesante aquí es lo poco que ha cambiado. La línea 258 a 260 declaran la interfaz. La línea 264 es pública en 
// lugar de usar el acceso predeterminado, ya que las interfaces requieren métodos públicos.
//  ¡La clase anónima es la misma tanto si implementas una interfaz como si extiendes una clase! En la línea 263 se crea una instancia de una clase no de una interfaz. 

public class ZooGiftShop {
    interface SaleTodayOnly {
        int dollarsOff();
    }
    
    public int admission(int basePrice) {
        SaleTodayOnly sale = new SaleTodayOnly() {
            public int dollarsOff() {
                return 3;
            }
        }; // debe acabar en punto y coma
        return basePrice - sale.dollarsOff();
    }
}


```

¿Qué pasa si queremos implementar una interfaz y extender una clase? No se puede hacer con una clase anónima a menos que la clase a extender sea `java.lang.Object`

Recuerda que una clase anónima es simplemente una clase local sin nombre.

Puedes escribir una clase local y darle un nombre si tienes este problema. Luego, puedes implementar tantas interfaces como quieras y extender esta clase.

```java
// aunque parece que estamos "instanciando una interfaz", en realidad estamos creando una clase anónima que implementa esa interfaz (indicado por las llaves {} después del nombre de la interfaz)

public class Gorilla {
    interface Climb {}
    Climb climbing = new Climb() {};
}
```


## Entendiendo el polimorfismo

El polimorfismo es la propiedad de un objeto para adoptar muchas formas diferentes. Precisamente se puede acceder a un objeto Java mediante:

* Una referencia con el mismo tipo que el objeto.
* Una referencia que es una superclase del objeto.
* Una referencia que define una interfaz que el objeto implementa o hereda.

Además, no se requiere una conversión si el objeto se reasigna a un supertipo o interfaz del objeto.

```java
// Lo más importante a tener en cuenta en este ejemplo es que solo se crea un objeto Lemur. 
// El polimorfismo permite reasignar una instancia de Lemur o pasarla a un método utilizando uno de sus supertipos, como Primate o HasTail. 
// Una vez que el objeto ha sido asignado a un nuevo tipo de referencia, solo se pueden llamar en el objeto a los métodos 
// y variables disponibles para ese tipo de referencia sin una conversión explícita.

public class Primate {
    public boolean hasHair() {
        return true;
    }
}

public interface HasTail {
    public abstract boolean isTailStriped();
}

public class Lemur extends Primate implements HasTail {
    public boolean isTailStriped() {
        return false;
    }
    
    public int age = 10;
    
    public static void main(String[] args) {
        Lemur lemur = new Lemur();
        System.out.println(lemur.age); // 10

        HasTail hasTail = lemur; // Reasignación a interfaz
        System.out.println(hasTail.isTailStriped()); // false
        
        Primate primate = lemur; // Reasignación a superclase
        System.out.println(primate.hasHair()); // true
    }
}
```

```java
// En este ejemplo, la referencia hasTail solo tiene acceso directo a los métodos definidos con la interfaz HasTail; por lo tanto, 
// no sabe que la variable age es parte del objeto. Del mismo modo, la referencia primate solo tiene acceso a los 
// métodos definidos en la clase Primate y no tiene acceso directo al método isTailStriped()

HasTail hasTail = new Lemur();
System.out.println(hasTail.age); // Error de compilación

Primate primate = new Lemur();
System.out.println(primate.isTailStriped()); // Error de compilación
```

### Objeto vs Referencia

En Java, todos los objetos se acceden por referencia, por lo que como desarrollador nunca tienes acceso directo al objeto mismo. Conceptualmente, sin embargo, deberías considerar el objeto como la entidad que existe en memoria, asignada por el Java Runtime Environment. Independientemente del tipo de referencia que tengas para el objeto en memoria, el objeto en sí mismo no cambia. Por ejemplo, dado que todos los objetos heredan de `java.lang.Object`, todos pueden ser referenciados a `java.lang.Object`, como se muestra en el siguiente ejemplo:

```java
// Aunque el objeto Lemur se haya asignado a una referencia con un tipo diferente, el objeto en sí no ha cambiado 
// y sigue existiendo como un objeto Lemur en memoria. Lo que ha cambiado, entonces, es nuestra capacidad para 
// acceder a los métodos dentro de la clase Lemur con la referencia lemurAsObject. Sin una conversión explicita a Lemur, 
// ya no tenemos acceso a las propiedades del objeto Lemur.

Lemur lemur = new Lemur();
Object lemurAsObject = lemur;
```

Podemos resumir este principio con las siguientes dos reglas:

1. El tipo de objeto determina qué propiedades existen dentro del objeto en memoria.
2. El tipo de referencia al objeto determina qué métodos y variables son accesibles para el programa Java.

Por lo tanto, se deduce que cambiar correctamente una referencia de un objeto a un nuevo tipo de referencia puede dar acceso a nuevas propiedades del objeto; pero recuerde que esas propiedades existían antes de que ocurriera el cambio de referencia.

### Casting (conversión) de objetos

En el ejemplo anterior creamos una única instancia de un objeto Lemur y accedimos a él mediante referencias de superclase e interfaz. Sin embargo, una vez que cambiamos el tipo de referencia, perdimos el acceso a miembros más específicos definidos en la subclase que aún existen dentro del objeto. Podemos recuperar esas referencias volviendo a convertir el objeto a la subclase específica de la que proviene:

```java
Lemur lemur = new Lemur();

Primate primate = lemur; // es implicito la conversión a la superclase

Lemur lemur2 = (Lemur) primate; // explícito la conversión al subtipo

Lemur lemur3 = primate; // No compila (falta el casteo)
```

Al convertir objetos, no se necesita un operador de conversión si se convierte a un supertipo heredado (primer ejemplo). Esto se conoce como conversión implícita y se aplica a las clases o interfaces que hereda el objeto. Alternativamente, si se desea acceder a un subtipo de la referencia actual (segundo ejemplo), se debe realizar una conversión explícita con un tipo compatible. Si el objeto subyacente no es compatible con el tipo (tercer ejemplo), se lanzará una `ClassCastException` en tiempo de ejecución.

Resumiendo las reglas para el examen:

* Convertir una referencia de un subtipo a un supertipo no requiere una conversión explícita.
* Convertir una referencia de un supertipo a un subtipo requiere una conversión explícita.
* En tiempo de ejecución, una conversión no válida de una referencia a un tipo incompatible da como resultado que se lance una `ClassCastExcpetion`.
* El compilador no permite conversiones a tipos no relacionados

### Casting (conversión) no permitida

Esta última regla es un poco más complicada. En el ejemplo anterior, pudimos convertir una referencia `Primate` a una referencia `Lemur` porque `Lemur` es una subclase de `Primate` y, por lo tanto, está relacionada.

```java
// En este ejemplo, las clases Fish y Bird no están relacionadas a través de ninguna 
// jerarquía de clases que el compilador conozca; por lo tanto, el código no se compilará. 

public class Bird {}

public class Fish {
    public static void main(String[] args) {
        Fish fish = new Fish();
        Bird bird = (Bird) fish; // No compila: tipos no relacionados
    }
}
```

### Casting (conversión) de interfaces

Si bien el compilador puede aplicar reglas sobre la conversión a tipos no relacionados para las clases, no siempre puede hacer lo mismo para las interfaces. Recuerde que las instancias admiten herencia múltiple, lo que limita lo que el compilador puede razonar sobre ellas. Si bien una clase determinada puede no implementar una interfaz, es posible que alguna subclase si la implemente. Cuando se mantiene una referencia a una clase en particular, el compilador no sabe qué subtipo específico está manteniendo.

```java
// En este programa, se creó un objeto Wolf y luego se asigna a un tipo de referencia Wolf en la línea 140. 
// Con las interfaces, el compilador tiene una capacidad limitada para aplicar muchas reglas porque, aunque un tipo 
// de referencia no implemente una interfaz, una de sus subclases si podría. Por lo tanto, permite la conversión no 
// válida al tipo de referencia Dog en la línea 141, aunque Dog y Wolf no estén relacionados. Aunque el código compile, 
// todavía lanza una ClassCastException en tiempo de ejecución. 

interface Canine {}
interface Dogo {}
class Wolf implements Canine {}

public class BadCats{
    public static void main(String[] args) {
        Wolf wolfy = new Wolf();
        Dogo badWolf = (Dogo) wolfy;
    }
}
```

Dejando de lado esta limitación, el compilador puede aplicar una regla sobre la conversión de interfaces. El compilador no permite una conversión de una referencia de interfaz a una referencia de objeto si el tipo de objeto no puede implementar la interfaz (por ejemplo la clase está marcada como final).

Por ejemplo, si la clase `Wolf` está marcada como `final` en la línea 136, la línea 141 ya no se compila. El compilador reconoce que no hay subclases posibles de Wolf capaces de implementar la interfaz Dog.

### El operador `intanceof`

El operador `instanceof` se puede usar para verificar si un objeto pertenece a una clase o interfaz en particular y para evitar una `ClassCastException` en tiempo de ejecución.

```java
class Rodent {}

public class Capybara extends  Rodent {
    public static void main(String[] args) {
        Rodent rodent = new Rodent();
        var capybara = (Capybara) rodent; // Lanzará ClassCastException en tiempo de ejecución
    }
}

// podemos cambiar la línea 160 por:
// Ahora el fragmento de código no lanza una excepción en tiempo de ejecución y realiza la conversión solo si el operador instanceof tiene éxito. 

if (rodent instanceof Capybara) {
    // Do stuff
}
```

Asi como el compilador no permite convertir un objeto a tipos no relacionados, tampoco permite que instanceof se use con tipos no relacionados.

Podemos demostrar esto con nuestras clases `Bird` y `Fish` no relacionadas:

```java
public class Bird {}

public class Fish {
    public static void main(String[] args) {
        Fish fish = new Fish();
        if (fish instanceof Bird) { // No compila
            // Do stuff
        }
    }
}
```

### Polimorfismo y sobrecarga de métodos

En Java, el polimorfismo establece que cuando se anula un método, se reemplazan todas las llamadas a él, incluso las definidas en la clase padre.

```java
class Penguin {
    public int getHeight() {
        return 3;
    }
    
    public void printInfo() {
        System.out.print(this.getHeight());
    }
}

public class EmperorPenguin extends Penguin {
    public int getHeight() {
        return 8;
    }
    
    public static void main(String[] args) {
        new EmperorPenguin().printInfo(); // 8
    }
}
```

Si dijiste 8, entonces estás en camino a entender el polimorfismo. Este ejemplo, el objeto sobre el que opera en memoria es un `EmperorPenguin`. El método `getHeight()` se anula en la subclase, lo que significa que todas ls llamadas a este se reemplazan en tiempo de ejecución.

A pesar de que `printInfo()` está definido en la clase `Penguin`, llamar a `getHeight()` en el objeto, llama al método asociado con el objeto preciso en memoria, no al tipo de referencia actual donde se llama. Incluso usando la referencia `this`, que es opcional en este ejemplo, no se llama a la versión padre porque el método ha sido reemplazado.

La capacidad del polimorfismo para reemplazar métodos en tiempo de ejecución mediante la anulación es una de las propiedades más importantes de Java. Permite crear modelos de herencia complejos con subclases que tienen su propia implementación personalizada de métodos anulados.

También significa que la clase padre no necesita actualizarse para usar el método personalizado o anulado. Si el método se anula correctamente, entonces la versión anulada se usara en todos los lugares donde se llame. Recuerda que puedes optar por limitar el comportamiento polimórfico marcando los métodos como finales, lo que evita que una subclase lo sobrescriba.

### Sobrecarga vs. ocultación de miembros

Mientras que la anulación de métodos reemplaza el método en todos los lugares donde se llama, la ocultación de métodos estáticos y variables no lo hace.

Estrictamente hablando, ocultar miembros no es una forma de polimorfismo, ya que los métodos y las variables mantienen sus propiedades individuales.

A diferencia de la anulación de métodos, ocultar miembros es muy sensible al tipo de referencia y la ubicación donde se utiliza el miembro.

```java
class Penguin {
    public static int getHeight() {
        return 3;
    }
    
    public void printInfo() {
        System.out.print(this.getHeight());
    }
}

public class CrestedPenguin extends Penguin {
    public static int getHeight() {
        return 8;
    }
    
    public static void main(String... fish) {
        new CrestedPenguin().printInfo(); // 3
    }
}
```

El ejemplo de `CrestedPenguin` es casi idéntico a nuestro ejemplo anterior de `EmperorPenguin`, aunque como probablemente ya habrás adivinado, imprime 3 en lugar de 8.

El método `getHeight()` es estático y, por lo tanto, está oculto, no sobrescrito. El resultado es que llamar a `getHeight()` en `CrestedPenguin` devuelve un valor diferente que llamarlo en `Penguin`, incluso si el objeto subyacente es el mismo.

Contrasta esto con la sobrescritura de un método, donde devuelve el mismo valor para un objeto independientemente de en qué clase se llame.

¿Qué pasa con el hecho de que usamos `this` para acceder a un método estático en `this.getHeight()`?

Aunque se te permite usar una referencia de instancia para acceder a una variable o método estático, hacerlo a menudo se desaconseja.

El compilador te advertirá cuando accedas a miembros estáticos de una manera no estática. En este caso, la referencia this no tuvo impacto en la salida del programa.

```java
class Marsupial {
    protected int age = 2;
    public static boolean isBiped() {
        return false;
    }
}

public class Kangaroo extends Marsupial {
    protected int age = 6;
    public static boolean isBiped() {
        return true;
    }
    
    public static void main(String[] args) {
        Kangaroo joey = new Kangaroo();
        Marsupial moey = joey;
        System.out.println(joey.isBiped());
        System.out.println(moey.isBiped());
        System.out.println(joey.age);
        System.out.println(moey.age);
    }
}

// true
// false
// 6
// 2
```

En este ejemplo, solo un objeto (de tipo Kangaroo) es creado y almacenado en memoria.

Dado que los métodos estáticos solo pueden ser ocultos, no sobrescritos, Java usa el tipo de referencia para determinar qué versión de `isBiped()` debe ser llamada, resultando en que `joey.isBiped()` imprima true y `moey.isBiped()` imprima false.

Del mismo modo, la variable age está oculta, no sobrescrita, por lo que el tipo de referencia se usa para determinar qué valor mostrar. Esto resulta en que `joey.age` devuelva 6 y `moey.age` devuelva 2.
