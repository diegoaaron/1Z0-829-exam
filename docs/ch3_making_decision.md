# Making Decision

## Creating Decision-Making Statements

### The if Statement

la sentencia `if` statement permite que nuestra aplicación ejecute un bloque particular de código si y solo si una expresión booleana se evalúa a true en tiempo de ejecución.

![ch03_01.png](images/ch03/ch03_01.png)

```java
if(hourOfDay < 11)
    System.out.println("Good Morning");

if(hourOfDay < 11) {
    System.out.println("Good Morning");
    morningGreetingCount++;
}
```

Ambas opciones son válidas. La segunda opción usa llaves `{}` para agrupar múltiples declaraciones en un solo bloque.

### The else Statement

![ch03_02.png](images/ch03/ch03_02.png)

```java
if(hourOfDay < 11) {
    System.out.println("Good Morning");
} else System.out.println("Good Afternoon");

if(hourOfDay < 11) {
        System.out.println("Good Morning");
} else if(hourOfDay < 15) {
        System.out.println("Good Afternoon");
} else {
        System.out.println("Good Evening");
}
```

* El operador else toma una sentencia o bloque de sentencias, de la misma manera que la sentencia if.
* Podemos agregar sentencias if adicionales a un bloque else para llegar a un ejemplo más refinado

### Shortening Code with Pattern Matching

* Java 16 oficialmente introdujo pattern matching con sentencias if y el operador instanceof. 
* Pattern matching es una técnica de controlar el flujo del programa que solo ejecuta una sección de código que cumple ciertos criterios. 
* Es usado en conjunto con sentencias if para mayor control del programa.

* Pattern matching es una nueva herramienta a tu disposición para reducir boilerplate en tu código. 
* Boilerplate code es código que tiende a ser duplicado a través de una sección de código una y otra vez de manera similar. 

```java
void compareIntegers(Number number) {
    if(number instanceof Integer) {
        Integer data = (Integer)number;
        System.out.print(data.compareTo(5));
    }
}
```

* El cast es necesario, ya que el método compareTo() está definido en Integer, pero no en Number.
* Código que primero verifica si una variable es de un tipo particular y luego inmediatamente la castea a Integer. 
* Es tan común que los autores de Java decidieron implementar una sintaxis más corta para ello:

```java
void compareIntegers(Number number) {
    if(number instanceof Integer data) {
        System.out.print(data.compareTo(5));
    }
}
```

* La variable **data** en este ejemplo es referida como la **pattern variable**. 
* Nota que este código también evita cualquier ClassCastException potencial porque la operación de cast es ejecutada solo si el operador instanceof implícito retorna true.

### Pattern Variables and Expressions

Pattern matching incluye expresiones que pueden ser usadas para filtrar datos, como en el siguiente ejemplo:

```java
void printIntegersGreaterThan5(Number number) {
    if(number instanceof Integer data && data.compareTo(5)>0)
        System.out.print(data);
}
```

* Podemos aplicar un número de filtros, o patrones, para que la sentencia if sea ejecutada solo en circunstancias específicas. 
* Nota que estamos usando la variable de patrón en una expresión en la misma línea en la cual es declarada.

### Subtypes

El tipo de la variable de patrón debe ser un subtipo de la variable en el lado izquierdo de la expresión. Tampoco puede ser el mismo tipo.

```java
Integer value = 123;
if(value instanceof Integer) {}
if(value instanceof Integer data) {} // DOES NOT COMPILE
```

Mientras que la segunda línea compila, la última línea no compila porque pattern matching requiere que el tipo de dato de la variable sea un subtipo estricto de Integer.

### Flow Scoping

* El compilador aplica flow scoping cuando trabaja con pattern matching. 
* Flow scoping significa que la variable solo está en alcance cuando el compilador puede determinar definitivamente su tipo. 

```java
void printIntegersOrNumbersGreaterThan5(Number number) {
    if(number instanceof Integer data || data.compareTo(5)>0)
        System.out.print(data);
}
```

* Si la entrada no hereda Integer, la variable data es indefinida. 
* Ya que el compilador no puede garantizar que data es una instancia de Integer, data no está en alcance, y el código no compila.

```java
void printIntegerTwice(Number number) {
    if (number instanceof Integer data)
        System.out.print(data.intValue());
    System.out.print(data.intValue()); // DOES NOT COMPILE
}
```

Ya que la entrada podría no haber heredado Integer, data ya no está en alcance después de la sentencia if. 
Oh, entonces podrías estar pensando que la variable de patrón está entonces solo en alcance dentro de la sentencia if, 

## Applying switch Statements

### The switch Statement

* Una sentencia switch, es una estructura compleja de toma de decisiones en la cual un valor único es evaluado y el flujo es redirigido a la primera rama coincidente, conocida como una sentencia case. 
* Si no se encuentra tal sentencia case que coincida con el valor, una sentencia default opcional será llamada.
* Si no está disponible tal opción default, la sentencia switch entera será omitida. 

![ch03_03.png](images/ch03/ch03_03.png)

```java
int month = 5;

switch month { // DOES NOT COMPILE
    case 1: System.out.print("January");
}

switch(month) // DOES NOT COMPILE
    case 1: System.out.print("January");

switch(month) {
    case 1: 2: System.out.print("January"); // DOES NOT COMPILE
}
```

* La primera sentencia switch no compila porque le faltan paréntesis alrededor de la variable switch. 
* La segunda sentencia no compila porque le faltan llaves alrededor del cuerpo del switch. 
* La tercera sentencia no compila porque una coma `(,)` debería ser usada para separar sentencias case combinadas, no dos puntos (:).

* Una última nota de la que deberías estar consciente para el examen: una sentencia switch no está obligada a contener ninguna sentencia case. 
* Por ejemplo, esta sentencia es perfectamente válida:

`switch(month) {}`

### Exiting with break Statements

* Una sentencia break termina la sentencia switch y retorna el control de flujo al proceso que la encierra. 
* Las sentencias break son opcionales, pero sin ellas el código ejecutará cada rama siguiendo una sentencia case coincidente, incluyendo cualquier sentencia default que encuentre. 

* ¿Qué piensas que imprime lo siguiente cuando printSeason(2) es llamado?

```java
public void printSeason(int month) {
    switch(month) {
        case 1, 2, 3:  System.out.print("Winter");
        case 4, 5, 6:  System.out.print("Spring");
        default:    System.out.print("Unknown");
        case 7, 8, 9:  System.out.print("Summer");
        case 10, 11, 12: System.out.print("Fall");
    }
}
```

Imprime todo: WinterSpringUnknownSummerFall

* Coincide con la primera sentencia case y ejecuta todas las ramas en el orden en que se encuentran, incluyendo la sentencia default. 
* Es común, aunque ciertamente no requerido, usar una sentencia break después de cada sentencia case.

### Selecting switch Data Types

Como se muestra en Figure 3.3, una sentencia switch tiene una variable objetiva que no es evaluada hasta tiempo de ejecución. 
La siguiente es una lista de todos los tipos de datos soportados por sentencias switch:

* int and Integer
* byte and Byte
* short and Short
* char and Character
* String
* enum values
* var (si el tipo es resuelto en pasos previos)

Para este capítulo, solo necesitas saber que una enumeración, o enum, representa un conjunto fijo de constantes, como días de la semana, meses del año, y así sucesivamente. 

### Determining Acceptable Case Values

* Primero, los valores en cada sentencia `case` deben ser valores constantes en tiempo de compilación del mismo tipo de datos que el valor switch. 
* Por ejemplo, no puedes tener un valor de sentencia case que requiera ejecutar un método en tiempo de ejecución, incluso si ese método siempre retorna el mismo valor. 

```java
final int getCookies() { return 4; }
void feedAnimals() {
    final int bananas = 1;
    int apples = 2;
    int numberOfAnimals = 3;
    final int cookies = getCookies();
    switch(numberOfAnimals) {
        case bananas:
        case apples:     // DOES NOT COMPILE
        case getCookies(): // DOES NOT COMPILE
        case cookies :  // DOES NOT COMPILE
        case 3 * 5 :
    }
}
```

* La variable bananas está marcada final, y su valor es conocido en tiempo de compilación, así que es válida. 
* La variable apples no está marcada final, incluso aunque su valor es conocido, así que no está permitida. 
* Las siguientes dos sentencias case, con valores getCookies() y cookies, no compilan porque los métodos no son evaluados hasta tiempo de ejecución, así que no pueden ser usados como el valor de una sentencia case, incluso si uno de los valores está almacenado en una variable final. 
* La última sentencia case, con valor 3 * 5, compila, ya que las expresiones están permitidas como valores case, siempre que el valor pueda ser resuelto en tiempo de compilación. 
* También deben ser capaces de caber en el tipo de datos switch sin un cast explícito. 

* Siguiente, el tipo de datos para sentencias case debe coincidir con el tipo de datos de la variable switch. 
* Por ejemplo, no puedes tener una sentencia case de tipo String si la variable de la sentencia switch es de tipo int, ya que los tipos son incomparables.

### The switch Expression

Una expresión switch es una forma mucho más compacta de una sentencia switch, capaz de retornar un valor. 

![ch03_04.png](images/ch03/ch03_04.png)

* Para empezar, ahora podemos asignar el resultado de una expresión switch a una variable result. 
* Para que esto funcione, todas las ramas case y default deben retornar un tipo de datos que sea compatible con la asignación. 
* La expresión switch soporta dos tipos de ramas: una expresión y un bloque. Cada una tiene reglas sintácticas diferentes sobre cómo debe ser creada. 










---------------------------------------------------------------------
**Palabra** cuando es una palabra en inglés importante que tiene sentido traducirla, pero no es una palabra reservada

() version en ingles de la palabra anterior

`   `  solo cúando es una línea de código o una palabra reservada que va a ser explicada

```java

```

Writing while Loops
Writing while Loops
Constructing for Loops
Controlling Flow with Branching