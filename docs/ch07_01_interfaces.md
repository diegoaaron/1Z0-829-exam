# Cap. 07 - Beyond Classes

## Interfaces

Tipo de dato abstracto que define una lista de métodos abstractos en la que cualquier clase que implemente la interfaz debe aprovisionarlos.

* una interfaz solo puede contener métodos abstractos, estáticos, por defecto y constantes.
* una interfaz puede estar totalmente vacía.

```java
public abstract interface CanBurrow {
    public abstract Float getSpeed(int age);

    public static final int MINIUM_DEPTH = 1;
}
```
* una interfaz siempre es considerada abstracta
* una interfaz tiene modificadores implícitos a diferencia de las clases abstractas
* una interfaz no requiere tener definido ningún método para existir

Un modificador implícito es uno que el compilador inserta automáticamente en el código

```java
// compila
public abstract interface WalksOnTwoLegs {}

public class Biped {
    public static void main(String[] args) {
        // no compila porque no una interfaz no puede ser instanciada 
        var e = new WalksOnTwoLegs();

    }
}

// no compila porque una interfaz no puede ser marcada como final (marcarla asi indicaria que no se puede utilizar por otra clase)
public final interface WalksOnEightLegs {}
```

Haciendo uso de las interfaces a través de la palabra reservada `implements`:

* El modificador de acceso del método de la interfaz es implícitamente público en `Climb`, pero la clase `FieldMouse` debe declararlo explícitamente.
* Una clase puede implementar múltiples interfaces, separándolas con una `,` coma.

```java
public interface Climb {
    Number getSpeed(int age);
}

public class FieldMouse implements Climb, CanBurrow {

    // implementación del método de la interfaz Climb
    public Float getSpeed(int age) {
        return 11f; // retorna un tipo covariante
    }
}

// La covarianza en tipos de retorno significa que cuando una subclase o implementación sobrescribe un método, 
// puede devolver un tipo que es más específico (una subclase) del tipo declarado originalmente.
// en este caso, FieldMouse implementa el método getSpeed de la interfaz Climb, devolviendo un Float, 
// cuando el método original devuelve un Number.
```
* Una interfaz puede ser extendida por otra utilizando la palabra reservada `extends`
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

