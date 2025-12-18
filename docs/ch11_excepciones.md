# Exceptions & Localization

## Understanding Exceptions

Un programa puede fallar por casi cualquier razón. Algunas posibilidades:

* El código intenta conectarse a un sitio web, pero la conexión a Internet está caída
* Se cometió un error de programación e intentaste acceder a un índice inválido en un array
* Un método llama a otro con un valor que el método no soporta

* Algunos de estos son errores de programación. Otros están completamente fuera de tu control. 
* Tu programa no puede evitar que la conexión a Internet se caiga. Lo que puede hacer es lidiar con la situación.

### El rol de las excepciones (Exceptions)

Una `exception` es la forma en que Java dice "Me rindo. No sé qué hacer ahora. 
Tú lídialo." Cuando escribes un método, puedes lidiar con la excepción o hacer que sea problema del código que llama.

---------------------------------------------------------------------
**Return Codes vs. Exceptions**

* Las excepciones se usan cuando "algo sale mal". Sin embargo, la palabra **wrong** es subjetiva. 
* El siguiente código retorna `-1` en lugar de lanzar una excepción si no se encuentra coincidencia:

```java
public int indexOf(String[] names, String name) {
    for (int i = 0; i < names.length; i++) {
        if (names[i].equals(name)) { return i; }
    }
    return -1;
}
```

* Aunque es común para ciertas tareas como búsqueda, los códigos de retorno generalmente deberían evitarse. 
* Después de todo, Java proporcionó un framework de excepciones, ¡así que deberías usarlo!
---------------------------------------------------------------------

### Understanding Exception Types

Una excepción es un evento que altera el flujo del programa. Java tiene una clase `Throwable` para todos los objetos que representan estos eventos. 
No todos ellos tienen la palabra exception en el nombre de su clase, lo cual puede ser confuso. La Figura 11.1 muestra las subclases clave de Throwable.

![ch11_01_01.png](images/ch11/ch11_01_01.png)

### Checked Exceptions

* Una checked exception es una excepción que debe ser declarada o manejada por el código de aplicación donde es lanzada. 
* En Java, las checked exceptions heredan de Exception pero no de RuntimeException. 
* Las checked exceptions tienden a ser más anticipadas—por ejemplo, intentar leer un archivo que no existe.

---------------------------------------------------------------------
Nota sobre `Checked Exceptions`
Las `checked exceptions` también incluyen cualquier clase que herede de `Throwable` pero no de `Error` o `RuntimeException`, como una clase que directamente extiende `Throwable`. 
Para el examen, solo necesitas saber sobre `checked exceptions` que extienden `Exception`.
---------------------------------------------------------------------

* ¿Checked exceptions? ¿Qué estamos chequeando? Java tiene una regla llamada handle or declare rule. 
* La handle or declare rule significa que todas las `checked exceptions` que podrían ser lanzadas dentro de un método están envueltas en bloques `try` y `catch` compatibles o declaradas en la firma del método.
* Como las `checked exceptions` tienden a ser anticipadas, Java fuerza la regla de que el programador debe hacer algo para mostrar que la excepción fue considerada.
* Tal vez fue manejada en el método. O tal vez el método declara que no puede manejar la excepción y alguien más debería.

Veamos un ejemplo. El siguiente método `fall()` declara que podría lanzar una `IOException`, que es una `checked exception`:

```java
void fall(int distance) throws IOException {
    if(distance> 10) {
        throw new IOException();
    }
}
```

Nota que estás usando dos palabras clave diferentes aquí. La palabra clave `throw` le dice a Java que quieres lanzar una `Exception`, mientras que la palabra clave `throws` simplemente declara que el método podría lanzar una `Exception`. También podría no hacerlo.

Ahora que sabes cómo declarar una excepción, ¿cómo la manejas? La siguiente versión alternativa del método `fall()` maneja la excepción:

```java
void fall(int distance) {
    try {
        if(distance> 10) {
            throw new IOException();
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
```

* Nota que la declaración `catch` usa `Exception`, no `IOException`. Como `IOException` es una subclase de `Exception`, el bloque catch está permitido para capturarla. 
* Cubriremos los bloques `try` y `catch` con más detalle más adelante en este capítulo.

### Unchecked Exceptions

Una `unchecked exception` es cualquier excepción que no necesita ser declarada o manejada por el código de aplicación donde es lanzada. 
Las `unchecked exceptions` a menudo son referidas como runtime exceptions, aunque en Java, las `unchecked exceptions` incluyen cualquier clase que hereda `RuntimeException` o `Error`.

---------------------------------------------------------------------
**Tip sobre Unchecked Exceptions**
* Es permisible manejar o declarar una unchecked exception. 
* Dicho esto, es mejor documentar las unchecked exceptions que los llamadores deberían conocer en un comentario Javadoc en lugar de declarar una unchecked exception.
---------------------------------------------------------------------

* Una runtime exception se define como la clase RuntimeException y sus subclases. 
* Las runtime exceptions tienden a ser inesperadas pero no necesariamente fatales. 
* Por ejemplo, acceder a un índice de array inválido es inesperado. Aunque heredan de la clase Exception, no son checked exceptions.

* Una unchecked exception puede ocurrir en casi cualquier línea de código, ya que no se requiere que sea manejada o declarada. 
* Por ejemplo, una `NullPointerException` puede ser lanzada en el cuerpo del siguiente método si la referencia de entrada es `null`:

```java
void fall(String input) {
    System.out.println(input.toLowerCase());
}
```

* Trabajamos con objetos en Java tan frecuentemente que una `NullPointerException` puede ocurrir casi en cualquier lugar. 
* Si tuvieras que declarar `unchecked exceptions` en todas partes, ¡cada método individual tendría ese desorden! 
* El código compilará si declaras una unchecked exception. Sin embargo, es redundante.

### Error and Throwable

* Error significa que algo salió tan terriblemente mal que tu programa no debería intentar recuperarse de ello. 
* Por ejemplo, el disco duro "desapareció" o el programa se quedó sin memoria. 
* Estas son condiciones anormales que no es probable que encuentres y de las cuales no puedes recuperarte.

* Para el examen, lo único que necesitas saber sobre `Throwable` es que es la clase padre de todas las excepciones, incluyendo la clase `Error`. 
* Mientras puedes manejar excepciones `Throwable` y `Error`, no se recomienda que lo hagas en tu código de aplicación. 
* Cuando nos referimos a excepciones en este capítulo, generalmente queremos decir cualquier clase que hereda Throwable, aunque casi siempre estamos trabajando con la clase `Exception` o subclases de ella.

### Reviewing Exception Types

* Asegúrate de estudiar cuidadosamente todo en la Tabla 11.1. Para el examen, recuerda que un `Throwable` es ya sea una `Exception` o un `Error`. 
* No deberías capturar Throwable directamente en tu código.

![ch11_01_02.png](images/ch11/ch11_01_02.png)

### Throwing an Exception

* Cualquier código Java puede lanzar una excepción; esto incluye código que escribes. Algunas excepciones son proporcionadas con Java. 
* Podrías encontrar una excepción que fue inventada para el examen. Esto está bien. 
* La pregunta lo hará obvio que es una excepción al hacer que el nombre de la clase termine con Exception. 
* Por ejemplo, `MyMadeUpException` es claramente una excepción.

En el examen, verás dos tipos de código que resultan en una excepción. El primero es código que está mal. Aquí hay un ejemplo:

```java
String[] animals = new String[0];
System.out.println(animals[0]); // ArrayIndexOutOfBoundsException
```

* Este código lanza una `ArrayIndexOutOfBoundsException` ya que el array no tiene elementos. 
* Eso significa que las preguntas sobre excepciones pueden estar ocultas en preguntas que parecen ser sobre algo más.

---------------------------------------------------------------------
**Nota sobre preguntas del examen**
* En el examen, algunas preguntas tienen una opción sobre no compilar y sobre lanzar una excepción. 
* Presta atención especial al código que llama un método en una referencia null o que referencia un índice inválido de array o List. 
* Si detectas esto, sabes que la respuesta correcta es que el código lanza una excepción en tiempo de ejecución.
---------------------------------------------------------------------

* La segunda forma en que el código resulta en una excepción es solicitar explícitamente a Java que lance una. 
* Java te permite escribir declaraciones como estas:

```java
throw new Exception();
throw new Exception("Ow! I fell.");
throw new RuntimeException();
throw new RuntimeException("Ow! I fell.");
```

* La palabra clave throw le dice a Java que quieres que alguna otra parte del código maneje la excepción. 
* Esto es lo mismo que la niña pequeña llorando por su papá. Alguien más necesita averiguar qué hacer sobre la excepción.

---------------------------------------------------------------------
**throw vs. throws**
Cada vez que veas throw o throws en el examen, asegúrate de que se esté usando el correcto. 
La palabra clave throw se usa como una declaración dentro de un bloque de código para lanzar una nueva excepción o relanzar una excepción existente, mientras que la palabra clave throws se usa solo al final de una declaración de método para indicar qué excepciones soporta.
---------------------------------------------------------------------

* Cuando creas una excepción, usualmente puedes pasar un parámetro String con un mensaje, o puedes no pasar parámetros y usar los valores por defecto. 
* Decimos usually porque esto es una convención. Alguien ha declarado un constructor que toma un String. 
* Alguien también podría crear una clase de excepción que no tenga un constructor que tome un mensaje.

Adicionalmente, deberías saber que una Exception es un Object. Esto significa que puedes almacenarla en una referencia de objeto, y esto es legal:

```java
var e = new RuntimeException();
throw e;
```

* El código instancia una excepción en una línea y luego la lanza en la siguiente. 
* La excepción puede venir de cualquier lugar, incluso pasada a un método. Mientras sea una excepción válida, puede ser lanzada.

El examen también podría intentar engañarte. ¿Ves por qué este código no compila?

```java
throw RuntimeException(); // DOES NOT COMPILE
```

Si tu respuesta es que falta una palabra clave, tienes absolutamente razón. La excepción nunca es instanciada con la palabra clave `new`.

Veamos otro lugar donde el examen podría intentar engañarte. ¿Puedes ver por qué lo siguiente no compila?

```java
3: try {
4:     throw new RuntimeException();
5:     throw new ArrayIndexOutOfBoundsException(); // DOES NOT COMPILE
6: } catch (Exception e) {}
```

* Como la línea 4 se lanza una excepción, la línea 5 nunca puede ser alcanzada durante runtime. 
* El compilador reconoce esto y reporta un error de código inalcanzable.

### Calling Methods That Throw Exceptions

Cuando estás llamando un método que lanza una excepción, las reglas son las mismas que dentro de un método. ¿Ves por qué lo siguiente no compila?

```java
class NoMoreCarrotsException extends Exception {}

public class Bunny {
    public static void main(String[] args) {
        eatCarrot(); // DOES NOT COMPILE
    }
    private static void eatCarrot() throws NoMoreCarrotsException {}
}
```

* El problema es que `NoMoreCarrotsException` es una checked exception. Las checked exceptions deben ser manejadas o declaradas. 
* El código compilaría si cambiaras el método `main()` a cualquiera de estos:

```java
public static void main(String[] args) throws NoMoreCarrotsException {
    eatCarrot();
}
```

```java
public static void main(String[] args) {
    try {
        eatCarrot();
    } catch (NoMoreCarrotsException e) {
        System.out.print("sad rabbit");
    }
}
```

* Podrías haber notado que `eatCarrot()` no lanzó una excepción; solo declaró que podría hacerlo. 
* Esto es suficiente para que el compilador requiera que el llamador maneje o declare la excepción.
* El compilador todavía está buscando código inalcanzable. Declarar una excepción no utilizada no se considera código inalcanzable. 
* Le da al método la opción de cambiar la implementación para lanzar esa excepción en el futuro. ¿Ves el problema aquí?

```java
public void bad() {
    try {
        eatCarrot();
    } catch (NoMoreCarrotsException e) { // DOES NOT COMPILE
        System.out.print("sad rabbit");
    }
}

private void eatCarrot() {}
```

Java sabe que `eatCarrot()` no puede lanzar una checked exception—lo que significa que no hay forma de que el bloque catch en `bad()` sea alcanzado.

---------------------------------------------------------------------
**Nota sobre checked exceptions en bloques catch**
* Cuando veas una checked exception declarada dentro de un bloque catch en el examen, asegúrate de que el código en el bloque try asociado sea capaz de lanzar la excepción o una subclase de la excepción. 
* Si no, el código es inalcanzable y no compila. Recuerda que esta regla no se extiende a unchecked exceptions o excepciones declaradas en una firma de método.
---------------------------------------------------------------------

### Overriding Methods with Exceptions

* Cuando introdujimos la sobrescritura de métodos en el Capítulo 6, "Class Design", incluimos una regla relacionada con excepciones. 
* Un método sobrescrito no puede declarar ninguna excepción checked nueva o más amplia que el método que hereda. Por ejemplo, este código no está permitido:

```java
class CanNotHopException extends Exception {}

class Hopper {
    public void hop() {}
}

class Bunny extends Hopper {
    public void hop() throws CanNotHopException {} // DOES NOT COMPILE
}
```

* Java sabe que `hop()` no está permitido lanzar ninguna checked exception porque el método `hop()` en la superclase Hopper no declara ninguna. 
* Imagina qué pasaría si las versiones de los métodos de las subclases pudieran agregar checked exceptions—podrías escribir código que llame al método `hop()` de Hopper y no manejar ninguna excepción. 
* Entonces, si Bunny fuera usado en su lugar, el código no sabría manejar o declarar `CanNotHopException`.
* Un método sobrescrito en una subclase está permitido declarar menos excepciones que la superclase o interfaz. 
* Esto es legal porque los llamadores ya las están manejando.

```java
class Hopper {
    public void hop() throws CanNotHopException {}
}

class Bunny extends Hopper {
    public void hop() {} // This is fine
}
```

* Un método sobrescrito que no declara una de las excepciones lanzadas por el método padre es similar al método que declara que lanza una excepción que nunca lanza realmente. 
* Esto es perfectamente legal. Similarmente, una clase está permitida declarar una subclase de un tipo de excepción. 
* La idea es la misma. La superclase o interfaz ya ha cuidado de un tipo más amplio.

### Printing an Exception

* Hay tres formas de imprimir una excepción. Puedes dejar que Java la imprima, imprimir solo el mensaje, o imprimir de dónde viene el stack trace. 
* Este ejemplo muestra los tres enfoques:

```java
5: public static void main(String[] args) {
6:     try {
7:         hop();
8:     } catch (Exception e) {
9:         System.out.println(e + "\n");
10:        System.out.println(e.getMessage()+ "\n");
11:        e.printStackTrace();
12:    }
13: }
14: private static void hop() {
15:    throw new RuntimeException("cannot hop");
16: }

// Este código imprime lo siguiente:
// java.lang.RuntimeException: cannot hop
// cannot hop
// java.lang.RuntimeException: cannot hop
//     at Handling.hop(Handling.java:15)
//     at Handling.main(Handling.java:7)
```

* La primera línea muestra lo que Java imprime por defecto: el tipo de excepción y el mensaje. 
* La segunda línea muestra solo el mensaje. El resto muestra un stack trace. 
* El stack trace usualmente es el más útil porque muestra la jerarquía de llamadas a métodos que fueron hechas para alcanzar la línea que lanzó la excepción.

## Recognizing Exception Classes

* Necesitas reconocer tres grupos de clases de excepción para el examen: RuntimeException, checked Exception, y Error. 
* Miramos ejemplos comunes de cada tipo. Para el examen, necesitarás reconocer qué tipo de excepción es y si es lanzada por la Java Virtual Machine (JVM) o por un programador. 
* Para algunas excepciones, también necesitas saber cuáles son heredadas de una a otra.

### RuntimeException Classes

* RuntimeException y sus subclases son unchecked exceptions que no tienen que ser manejadas o declaradas. 
* Pueden ser lanzadas por el programador o la JVM. Las clases de unchecked exception comunes están listadas en la Tabla 11.2.

![ch11_01_03.png](images/ch11/ch11_01_03.png)

### ArithmeticException

Intentar dividir un int por cero da un resultado indefinido. Cuando esto ocurre, la JVM lanzará una `ArithmeticException`:

```java
int answer = 11 / 0;

// Ejecutar este código resulta en la siguiente salida:
// Exception in thread "main" java.lang.ArithmeticException: / by zero
```

* Java no deletrea la palabra divide. Eso está bien, sin embargo, porque sabemos que / es el operador de división y que Java está intentando decirte que ocurrió una división por cero.
* El thread "main" te está diciendo que el código fue llamado directa o indirectamente desde un programa con un método main. 
* En el examen, esto es toda la salida que verás. Luego viene el nombre de la excepción, seguido por información extra (si hay alguna) que va con la excepción.

### ArrayIndexOutOfBoundsException

Ya sabes ahora que los índices de array empiezan con 0 y van hasta 1 menos que la longitud del array—lo que significa que este código lanzará una `ArrayIndexOutOfBoundsException`:

```java
int[] countsOfMoose = new int[3];
System.out.println(countsOfMoose[-1]);
```

Este es un problema porque no existe tal cosa como un índice de array negativo. Ejecutar este código produce la siguiente salida:

`Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: Index -1 out of bounds for length 3`

### ClassCastException

Java intenta protegerte de casts imposibles. Este código no compila porque Integer no es una subclase de String:

```java
String type = "moose";
Integer number = (Integer) type; // DOES NOT COMPILE
```

Código más complicado frustra los intentos de Java de protegerte. Cuando el cast falla en runtime, Java lanzará una `ClassCastException`:

```java
String type = "moose";
Object obj = type;
Integer number = (Integer) obj; // ClassCastException
```

El compilador ve un cast de Object a Integer. Esto podría estar bien. El compilador no se da cuenta de que hay un String en ese Object. Cuando el código se ejecuta, produce la siguiente salida:

```java
Exception in thread "main" java.lang.ClassCastException: java.base/
java.lang.String
cannot be cast to java.lang.base/java.lang.Integer
```

Java te dice ambos tipos que estuvieron involucrados en el problema, haciendo evidente qué está mal.

### NullPointerException

Las variables de instancia y los métodos deben ser llamados en una referencia no-null. Si la referencia es null, la JVM lanzará una NullPointerException.

```java
1: public class Frog {
2:     public void hop(String name, Integer jump) {
3:         System.out.print(name.toLowerCase() + " " + jump.intValue());
4:     }
5:
6:     public static void main(String[] args) {
7:         new Frog().hop(null, 1);
8:     }
}
```

Ejecutar este código resulta en la siguiente salida:

```java
Exception in thread "main" java.lang.NullPointerException: Cannot invoke
"String.toLowerCase()" because "<parameter1>" is null
```

* Si eres nuevo en Java 17, deberías haber notado algo especial sobre la salida. 
* La JVM ahora te dice la referencia de objeto que desencadenó la `NullPointerException`. 
* Esta nueva característica se llama Helpful `NullPointerExceptions`.

Como otro ejemplo, supón que cambiamos la línea 7:

`7: new Frog().hop("Kermit", null);`

Entonces la salida en runtime cambia como sigue:

`Exception in thread "main" java.lang.NullPointerException: Cannot invoke "java.lang.Integer.intValue()" because "<parameter2>" is null`

---------------------------------------------------------------------
**Tip sobre NullPointerException**
* Por defecto, una `NullPointerException` en una variable local o parámetro de método se imprime con un número indicando el orden en el que aparece en el método, como `<local2>` o `<parameter4>`. 
* Si eres como nosotros y quieres que el nombre de variable real sea mostrado, compila el código con el flag `-g:vars`, que agrega información de debug. 
* En los ejemplos previos, `<parameter1>` y `<parameter2>` son entonces reemplazados con name y jump, respectivamente.
---------------------------------------------------------------------

Como esta es una nueva característica en Java, es posible que la veas en una pregunta del examen.

---------------------------------------------------------------------
**Enabling/Disabling Helpful NullPointerExceptions**

Cuando las helpful `NullPointerExceptions` fueron agregadas en Java 14, la característica estaba deshabilitada por defecto y tenía que ser habilitada vía un argumento de línea de comando `ShowCodeDetailsInExceptionMessages` a la JVM: `java -XX:+ShowCodeDetailsInExceptionMessages Frog`

En Java 15 y posteriores, el comportamiento por defecto fue cambiado para que esté habilitado por defecto, aunque todavía puede ser deshabilitado vía el argumento de línea de comando. `java -XX:-ShowCodeDetailsInExceptionMessages Frog`
---------------------------------------------------------------------

### IllegalArgumentException

IllegalArgumentException es una forma en que tu programa se protege a sí mismo. 
Quieres decirle al llamador que algo está mal—preferiblemente de una manera obvia que el llamador no pueda ignorar para que el programador arregle el problema. 
Ver que el código termina con una excepción es un gran recordatorio de que algo está mal. 
Considera este ejemplo cuando es llamado como `setNumberEggs(-2)`:

```java
public void setNumberEggs(int numberEggs) {
    if (numberEggs < 0)
        throw new IllegalArgumentException("# eggs must not be negative");
    this.numberEggs = numberEggs;
}
```

El programa lanza una excepción cuando no está contento con los valores de parámetro. La salida se ve así:

```java
Exception in thread "main"
java.lang.IllegalArgumentException: // eggs must not be negative
```

Claramente, este es un problema que debe ser arreglado si el programador quiere que el programa haga algo útil.

### NumberFormatException

* Java proporciona métodos para convertir strings a números. Cuando estos reciben un valor inválido, lanzan una NumberFormatException. 
* La idea es similar a IllegalArgumentException. Como este es un problema común, Java le da una clase separada. 
* De hecho, NumberFormatException es una subclase de IllegalArgumentException. Aquí hay un ejemplo de intentar convertir algo no-numérico en un int:

`Integer.parseInt("abc");`

La salida se ve así:

```java
Exception in thread "main"
java.lang.NumberFormatException: For input string: "abc"
```

* Para el examen, necesitas saber que NumberFormatException es una subclase de IllegalArgumentException. 
* Cubriremos más sobre por qué eso es importante más adelante en el capítulo.

### Checked Exception Classes

* Las checked exceptions tienen Exception en su jerarquía pero no RuntimeException. Deben ser manejadas o declaradas. 
* Las checked exceptions comunes están listadas en la Tabla 11.3.

![ch11_01_04.png](images/ch11/ch11_01_04.png)

* Para el examen, necesitas saber que todas estas son checked exceptions que deben ser manejadas o declaradas. 
* También necesitas saber que FileNotFoundException y NotSerializableException son subclases de IOException.
* Ves estas tres clases en el Capítulo 14, "I/O," y SQLException en el Capítulo 15, "JDBC."

### Error Classes

* Errors son unchecked exceptions que extienden la clase Error. Son lanzados por la JVM y no deberían ser manejados o declarados. 
* Errors son raros, pero podrías ver los listados en la Tabla 11.4.

![ch11_01_05.png](images/ch11/ch11_01_05.png)

Para el examen, solo necesitas saber que estos errors son unchecked y el código a menudo es incapaz de recuperarse de ellos.

## Handling Exceptions

### Using try and catch Statements

* Se explica que Java usa un try statement para separar la lógica que podría lanzar una excepción de la lógica que maneja esa excepción. 
* Se hace referencia a la Figura 11.2 que muestra la sintaxis de un try statement.

![ch11_01_06.png](images/ch11/ch11_01_06.png)

* El código en el bloque try se ejecuta normalmente. 
* Si alguno de los statements lanza una excepción que puede ser capturada por el tipo de excepción listado en el bloque catch, el bloque try deja de ejecutarse y la ejecución va al catch statement. 
* Si ninguno de los statements en el bloque try lanza una excepción que pueda ser capturada, la cláusula catch no se ejecuta.

* Se aclara que las palabras "block" y "clause" se usan intercambiablemente en el examen. Block es correcto porque hay llaves presentes. 
* Clause es correcto porque es parte de un try statement.

```java
3: void explore() {
4:     try {
5:         fall();
6:         System.out.println("never get here");
7:     } catch (RuntimeException e) {
8:         getUp();
9:     }
10:     seeAnimals();
11: }
12: void fall() { throw new RuntimeException(); }
```

* La línea 5 llama al método `fall()`. La línea 12 lanza una excepción. Esto significa que Java salta directamente al bloque catch, omitiendo la línea 6. 
* La niña se levanta en la línea 8. Ahora el try statement ha terminado, y la ejecución continúa normalmente con la línea 10.

Ahora veamos el siguiente ejemplo e identifiquemos el error:

```java
try // DOES NOT COMPILE
    fall();
catch (Exception e)
    System.out.println("get up");
```

El problema es que faltan las llaves `{}`. Los try statements son como métodos en que las llaves rizadas son requeridas incluso si hay solo un statement dentro de los bloques de código, mientras que los if statements y loops son especiales y te permiten omitir las llaves rizadas.

Ahora veamos este otro ejemplo: 

```java
try { // DOES NOT COMPILE
    fall();
}
```

* Este código no compila porque el bloque try no tiene nada después de él. El punto de un try statement es que algo suceda si se lanza una excepción. 
* Sin otra cláusula, el try statement está solo. Como verás en breve, hay un tipo especial de try statement que incluye un implicit finally block, aunque la sintaxis es bastante diferente de este ejemplo.

### Chaining catch Blocks

* Para el examen, te pueden dar clases de excepción y necesitas entender cómo funcionan. Aquí está cómo abordarlas. 
* Primero, debes ser capaz de reconocer si la excepción es checked o unchecked. 
* Segundo, necesitas determinar si alguna de las excepciones son subclases de las otras.

```java
class AnimalsOutForAWalk extends RuntimeException {}

class ExhibitClosed extends RuntimeException {}

class ExhibitClosedForLunch extends ExhibitClosed {}
```

* En este ejemplo, hay tres excepciones personalizadas. Todas son excepciones unchecked porque directa o indirectamente extienden RuntimeException. 
* Ahora encadenamos ambos tipos de excepciones con dos bloques catch y las manejamos imprimiendo el mensaje apropiado:

```java
public void visitPorcupine() {
    try {
        seeAnimal();
    } catch (AnimalsOutForAWalk e) { // first catch block
        System.out.print("try back later");
    } catch (ExhibitClosed e) { // second catch block
        System.out.print("not today");
    }
}
```

* Hay tres posibilidades cuando se ejecuta este código. Si seeAnimal() no lanza una excepción, no se imprime nada. 
* Si el animal está fuera para un paseo, solo se ejecuta el primer bloque catch. Si la exhibición está cerrada, solo se ejecuta el segundo bloque catch. 
* No es posible que ambos bloques catch se ejecuten cuando están encadenados juntos de esta manera.

* Existe una regla para el orden de los bloques catch. Java los mira en el orden en que aparecen. 
* Si es imposible que uno de los bloques catch se ejecute, ocurre un error de compilador sobre código unreachable. 
* Por ejemplo, esto sucede cuando un bloque catch de superclase aparece antes que un bloque catch de subclase. 
* Recuerda, te advertimos que prestes atención a cualquier excepción de subclase.

* En el ejemplo del puercoespín, el orden de los bloques catch podría revertirse porque las excepciones no heredan una de la otra. 
* Y sí, hemos visto un puercoespín ser sacado a pasear con una correa.
* El siguiente ejemplo muestra tipos de excepción que sí heredan uno del otro:

```java
public void visitMonkeys() {
    try {
        seeAnimal();
    } catch (ExhibitClosedForLunch e) { // Subclass exception
        System.out.print("try back later");
    } catch (ExhibitClosed e) { // Superclass exception
        System.out.print("not today");
    }
}
```

* Si se lanza la excepción más específica ExhibitClosedForLunch, se ejecuta el primer bloque catch. 
* Si no, Java verifica si se lanza la superclase ExhibitClosed exception y la captura. 
* Esta vez, el orden de los bloques catch sí importa. El reverso no funciona.

```java
public void visitMonkeys() {
    try {
        seeAnimal();
    } catch (ExhibitClosed e) {
        System.out.print("not today");
    } catch (ExhibitClosedForLunch e) { // DOES NOT COMPILE
        System.out.print("try back later");
    }
}
```

* Si se lanza la excepción más específica ExhibitClosedForLunch, el bloque catch para ExhibitClosed se ejecuta—lo que significa que no hay manera de que el segundo bloque catch se ejecute jamás. 
* Java correctamente te dice que hay un bloque catch unreachable. Intentemos este una vez más. ¿Ves por qué este código no compila?

```java
public void visitSnakes() {
    try {
    } catch (IllegalArgumentException e) {
    } catch (NumberFormatException e) { // DOES NOT COMPILE
    }
}
```

* Recuerda que dijimos anteriormente que necesitabas saber que NumberFormatException es una subclase de IllegalArgumentException
* Este ejemplo es la razón por qué. Dado que NumberFormatException es una subclase, siempre será capturada por el primer bloque catch, haciendo el segundo bloque catch unreachable code que no compila. 
* Del mismo modo, para el examen, necesitas saber que FileNotFoundException es una subclase de IOException y no puede ser usada de manera similar.

* Para revisar múltiples bloques catch, recuerda que como máximo un bloque catch se ejecutará, y será el primer bloque catch que pueda manejar la excepción. 
* También, recuerda que una excepción definida por el catch statement está solo in scope para ese bloque catch. 
* Por ejemplo, lo siguiente causa un error de compilador, ya que intenta usar el objeto de excepción fuera del bloque para el cual fue definido:

```java
public void visitManatees() {
    try {
    } catch (NumberFormatException e1) {
        System.out.println(e1);
    } catch (IllegalArgumentException e2) {
        System.out.println(e1); // DOES NOT COMPILE
    }
}
```

### Applying a Multi-catch Block

* A menudo, queremos que el resultado de una excepción que se lanza sea el mismo, sin importar qué excepción particular se lanza. 
* Por ejemplo, echa un vistazo a este método:

```java
public static void main(String args[]) {
    try {
        System.out.println(Integer.parseInt(args[1]));
    } catch (ArrayIndexOutOfBoundsException e) {
        System.out.println("Missing or invalid input");
    } catch (NumberFormatException e) {
        System.out.println("Missing or invalid input");
    }
}
```

* Observa que tenemos el mismo statement println() para dos bloques catch diferentes. 
* Podemos manejar esto más elegantemente usando un bloque multi-catch. 
* Un bloque multi-catch permite que múltiples tipos de excepción sean capturados por el mismo bloque catch. 
* Reescribamos el ejemplo anterior usando un bloque multi-catch:

```java
public static void main(String[] args) {
    try {
        System.out.println(Integer.parseInt(args[1]));
    } catch (ArrayIndexOutOfBoundsException | NumberFormatException e)
    {
        System.out.println("Missing or invalid input");
    }
}
```

* Esto es mucho mejor. No hay código duplicado, la lógica común está toda en un lugar, y la lógica está exactamente donde esperarías encontrarla. 
* Si quisieras, aún podrías tener un segundo bloque catch para Exception en caso de que quieras manejar otros tipos de excepciones de manera diferente.
* Figure 11.3 muestra la sintaxis de multi-catch. Es como una cláusula catch regular, excepto que dos o más tipos de excepción están especificados, separados por un pipe. 
* El pipe (|) también se usa como el operador "or", haciendo fácil recordar que puedes usar either/or de los tipos de excepción. 
* Observa cómo hay solo un nombre de variable en la cláusula catch. Java está diciendo que la variable llamada `e` puede ser de tipo Exception1 o Exception2.

![ch11_01_07.png](images/ch11/ch11_01_07.png)

* El examen podría intentar engañarte con sintaxis inválida. Recuerda que las excepciones pueden ser listadas en cualquier orden dentro de la cláusula catch. 
* Sin embargo, el nombre de variable debe aparecer solo una vez y al final. ¿Ves por qué estos son válidos o inválidos?

```java
catch(Exception1 e | Exception2 e | Exception3 e) // DOES NOT COMPILE

catch(Exception1 e1 | Exception2 e2 | Exception3 e3) // DOES NOT COMPILE

catch(Exception1 | Exception2 | Exception3 e)
```

* La primera línea es incorrecta porque el nombre de variable aparece tres veces. Solo porque resulta ser el mismo nombre de variable no hace que esté bien. 
* La segunda línea es incorrecta porque el nombre de variable de nuevo aparece tres veces. Usar diferentes nombres de variable no lo hace mejor. 
* La tercera línea sí compila. Muestra la sintaxis correcta para especificar tres excepciones.

* Java pretende que multi-catch sea usado para excepciones que no están relacionadas, y te previene de especificar tipos redundantes en un multi-catch. 
* ¿Ves qué está mal aquí?

```java
try {
    throw new IOException();
} catch (FileNotFoundException | IOException p) {} // DOES NOT COMPILE
```

* Especificar excepciones relacionadas en el multi-catch es redundante, y el compilador da un mensaje como este:
* The exception FileNotFoundException is already caught by the alternative IOException
* Dado que FileNotFoundException es una subclase de IOException, este código no compilará. 
* Un bloque multi-catch sigue reglas similares a encadenar bloques catch juntos, lo cual viste en la sección anterior. 
* Por ejemplo, ambos desencadenan errores de compilador cuando encuentran código unreachable o duplicate
* La única diferencia entre los bloques multicatch y el encadenamiento de bloques catch es que el orden no importa para un bloque multicatch dentro de una sola expresión catch.

Regresando al ejemplo, el código correcto es simplemente eliminar la referencia a la subclase extraña, como se muestra aquí:

```java
try {
    throw new IOException();
} catch (IOException e) {}
```

* El try statement también te permite ejecutar código al final con una cláusula finally, sin importar si se lanza una excepción. 
* Figure 11.4 muestra la sintaxis de un try statement con esta funcionalidad extra.

* Hay dos caminos a través del código con tanto un catch como un finally. 
* Si se lanza una excepción, el bloque finally se ejecuta después del bloque catch. 
* Si no se lanza ninguna excepción, el bloque finally se ejecuta después de que el bloque try se completa.
* Regresemos a nuestro ejemplo de la niña pequeña, esta vez con finally:

```java
12: void explore() {
13:     try {
14:         seeAnimals();
15:     fall();
16: } catch (Exception e) {
17:     getHugFromDaddy();
18: } finally {
19:     seeMoreAnimals();
20: }
21: goHome();
22: }
```

![ch11_01_08.png](images/ch11/ch11_01_08.png)

* La niña cae en la línea 15. Si se levanta por sí misma, el código continúa al bloque finally y ejecuta la línea 19. 
* Luego el try statement ha terminado, y el código procede en la línea 21. Si la niña no se levanta por sí misma, lanza una excepción. 
* El bloque catch se ejecuta, y ella recibe un abrazo en la línea 17. Con ese abrazo, está lista para ver más animales en la línea 19. 
* Luego el try statement ha terminado, y el código procede en la línea 21. 
* De cualquier manera, el final es el mismo. El bloque finally se ejecuta, y la ejecución continúa después del try statement.

El examen intentará engañarte con cláusulas faltantes o cláusulas en el orden incorrecto. ¿Ves por qué los siguientes sí o no compilan?

```java
25: try { // DOES NOT COMPILE
26:     fall();
27: } finally {
28:     System.out.println("all better");
29: } catch (Exception e) {
30:     System.out.println("get up");
31: }
32:
33: try { // DOES NOT COMPILE
34:     fall();
35: }
36:
37: try {
38:     fall();
39: } finally {
40:     System.out.println("all better");
41: }
```

* El primer ejemplo (líneas 25-31) no compila porque los bloques catch y finally están en el orden incorrecto. 
* El segundo ejemplo (líneas 33-35) no compila porque debe haber un bloque catch o finally. 
* El tercer ejemplo (líneas 37-41) está bien. El bloque catch no es requerido si finally está presente.

La mayoría de los ejemplos que encuentres en el examen con finally van a verse forzados. Por ejemplo, te harán preguntas como qué imprime este código:

```java
public static void main(String[] unused) {
    StringBuilder sb = new StringBuilder();
    try {
        sb.append("t");
    } catch (Exception e) {
        sb.append("c");
    } finally {
        sb.append("f");
    }
    sb.append("a");
    System.out.print(sb.toString());
}
```

* La respuesta es tfa. El bloque try se ejecuta. Dado que no se lanza ninguna excepción, Java va directo al bloque finally. 
* Luego el código después del try statement se ejecuta. Sabemos que este es un ejemplo tonto, pero puedes esperar ver ejemplos como este en el examen.
* Hay una regla adicional que deberías saber para los bloques finally. 
* Si se entra a un try statement con un bloque finally, entonces el bloque finally siempre será ejecutado, sin importar si el código se completa exitosamente. 
* Echa un vistazo al siguiente método goHome(). Asumiendo que una excepción puede o no puede ser lanzada en la línea 14, 
* ¿cuáles son los posibles valores que este método podría imprimir? También, ¿cuál sería el valor de retorno en cada caso?

```java
12: int goHome() {
13:     try {
14:         // Optionally throw an exception here
15:         System.out.print("1");
16:         return -1;
17:     } catch (Exception e) {
18:     System.out.print("2");
19:     return -2;
20:     } finally {
21:     System.out.print("3");
22:     return -3;
23:     }
24: }
```

* Si no se lanza una excepción en la línea 14, entonces la línea 15 será ejecutada, imprimiendo 1. 
* Antes de que el método retorne, sin embargo, el bloque finally se ejecuta, imprimiendo 3. 
* Si se lanza una excepción, entonces las líneas 15 y 16 serán omitidas y las líneas 17-19 serán ejecutadas, imprimiendo 2, seguido por 3 del bloque finally. 
* Mientras que el primer valor impreso puede diferir, el método siempre imprime 3 al final, ya que está en el bloque finally.
* ¿Cuál es el valor de retorno del método goHome()? En este caso, siempre es -3. Porque el bloque finally se ejecuta poco antes de que el método se complete, interrumpe el return statement desde dentro tanto del bloque try como del catch.
* Para el examen, necesitas recordar que un bloque finally siempre será ejecutado. Dicho esto, puede no completarse exitosamente. 
* Echa un vistazo al siguiente código snippet. ¿Qué pasaría si info fuera null en la línea 32?

```java
31: } finally {
32:     info.printDetails();
33:     System.out.print("Exiting");
34:     return "zoo";
35: }
```

* Si info fuera null, entonces el bloque finally sería ejecutado, pero se detendría en la línea 32 y lanzaría una NullPointerException. 
* Las líneas 33 y 34 no serían ejecutadas. En este ejemplo, ves que mientras un bloque finally siempre será ejecutado, puede que no termine.

---------------------------------------------------------------------
**System.exit():**
* Hay una excepción a la regla "the finally block will always be executed": Java define un método que llamas como System.exit(). 
* Toma un parámetro entero que representa el código de estado que se retorna.

```java
try {
    System.exit(0);
} finally {
    System.out.print("Never going to get here"); // Not printed
}
```

System.exit() le dice a Java, "Stop. End the program right now. Do not pass Go. Do not collect $200." 
Cuando `System.exit()` es llamado en el bloque try o catch, el bloque finally no se ejecuta.
---------------------------------------------------------------------

## Automating Resource Management

A menudo, tu aplicación trabaja con archivos, bases de datos, y varios objetos de conexión. 
Comúnmente, estas fuentes de datos externas son referidas como resources.

continuar en la 25

```java

```


Formatting Values
Supporting Internationalization and Localization
Loading Properties with Resource Bundles
Summary