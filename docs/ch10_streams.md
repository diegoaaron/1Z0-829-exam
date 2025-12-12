# Streams

## Retornando un Optional

* ¿Cómo expresamos esta respuesta "no sabemos" o "no aplicable" en Java?
* Usamos el tipo Optional. Un Optional se crea usando una factory. 
* Puedes solicitar un Optional vacío o pasar un valor para que el Optional lo envuelva. 
* Piensa en un Optional como una caja que podría tener algo dentro o podría estar vacía. Figure 10.1 muestra ambas opciones.

![ch10_01_01.png](images/ch10_01_01.png)

### Creando un Optional

Así es cómo codificar nuestro método average:

```java
10: public static Optional<Double> average(int... scores) {
11:   if(scores.length == 0) return Optional.empty();
12:   int sum = 0;
13:   for(int score: scores) sum += score;
14:   return Optional.of((double) sum / scores.length);
15: }
```

* La línea 11 retorna un Optional vacío cuando no podemos calcular un promedio. 
* Las líneas 12 y 13 suman las calificaciones. 
* De hecho, el método completo podría escribirse en una línea, pero eso no te enseñaría cómo funciona Optional. 
* La línea 14 crea un Optional para envolver el promedio.

Llamar al método muestra qué hay en nuestras dos cajas:

```java
System.out.println(average(90, 100)); // Optional[95.0]
System.out.println(average());        // Optional.empty
```

* Puedes ver que un Optional contiene un valor y el otro está vacío. 
* Normalmente, queremos verificar si un valor está presente y/o sacarlo de la caja. Aquí hay una manera de hacer eso:

```java
Optional<Double> opt = average(90, 100);
if(opt.isPresent())
  System.out.println(opt.get()); // 95.0
```

* Primero verificamos si el Optional contiene un valor. Luego lo imprimimos. 
* ¿Qué pasa si no hiciéramos la verificación, y el Optional estuviera vacío?

```java
Optional<Double> opt = average();
System.out.println(opt.get()); // NoSuchElementException
```

Obtendríamos una excepción, ya que no hay valor dentro del Optional.

`java.util.NoSuchElementException: No value present`

Al crear un Optional, es común querer usar `empty()` cuando el valor es `null`. 
Puedes hacer esto con un if statement o un operador ternario. 
Usamos el operador ternario `(? :)` para simplificar el código.

```java
Optional o = (value == null) ? Optional.empty() : Optional.of(value);
```

Si value es `null`, o se asigna el Optional vacío. De lo contrario, envolvemos el valor. 
Como este es un patrón tan común, Java proporciona un método factory para hacer lo mismo.

```java
Optional o = Optional.ofNullable(value);
```

![ch10_01_02.png](images/ch10_01_02.png)

* Los otros métodos te permiten escribir código que usa un `Optional` en una línea sin tener que usar el operador ternario. 
* En lugar de usar un `if statement`, que usamos cuando verificamos el average anteriormente, podemos especificar un `Consumer` para ejecutar cuando hay un valor dentro del `Optional`. 
* Cuando no lo hay, el método simplemente salta ejecutar él `Consumer`.

```java
Optional<Double> opt = average(90, 100);
opt.ifPresent(System.out::println);
```

`ifPresent()` expresa mejor la intención: ejecutar algo si un valor está presente. Es como un if sin else.

### Tratando con un Empty Optional (opcional vacío)

Los métodos restantes permiten especificar qué hacer si un valor NO está presente. Hay varias opciones. 
Las primeras dos permiten especificar un valor de retorno directamente o usando un `Supplier`.

```java
30: Optional<Double> opt = average();
31: System.out.println(opt.orElse(Double.NaN));
32: System.out.println(opt.orElseGet(() -> Math.random()));

// impime algo como
// NaN
// 0.3746499180811058
```

* La línea 31 muestra que puedes retornar un valor específico o variable. En este caso, imprimimos el valor "not a number". 
* La línea 32 muestra usar un `Supplier` para generar un valor en runtime para retornar. 

Alternativamente, podemos hacer que el código lance una excepción si el Optional está vacío.

```java
30: Optional<Double> opt = average();
31: System.out.println(opt.orElseThrow());

// arrojando algo como:
// Exception in thread "main" java.util.NoSuchElementException: No value present at java.base/java.util.Optional.orElseThrow(Optional.java:382)
```

* Sin especificar un `Supplier` para la excepción, Java lanzará una `NoSuchElementException`. 
* Alternativamente, podemos hacer que el código lance una excepción personalizada si él `Optional` está vacío. 
* Recuerda que el stack trace se ve extraño porque las lambdas se generan como clases en lugar de clases con nombre.

```java
30: Optional<Double> opt = average();
31: System.out.println(opt.orElseThrow(
32: () -> new IllegalStateException()));

// arrojando algo como:
// Exception in thread "main" java.lang.IllegalStateException at optionals.Methods.lambda$orElse$1(Methods.java:31) at java.base/java.util.Optional.orElseThrow(Optional.java:408)
```

* La línea 32 muestra usar un `Supplier` para crear una excepción que debería ser lanzada. 
* Nota que no escribimos `throw new IllegalStateException()`. El método `orElseThrow()` se encarga de realmente lanzar la excepción cuando lo ejecutamos.

Los dos métodos que toman un Supplier tienen nombres diferentes. ¿Ves por qué este código no compila?

```java
System.out.println(opt.orElseGet(
  () -> new IllegalStateException())); // DOES NOT COMPILE
```

* La variable `opt` es un `Optional<Double>`. Esto significa que el Supplier debe retornar un `Double`. 
* Ya que este `Supplier` retorna una excepción, el tipo no coincide.

El último ejemplo con `Optional` es realmente fácil. ¿Qué crees que hace esto?

```java
Optional<Double> opt = average(90, 100);
System.out.println(opt.orElse(Double.NaN));
System.out.println(opt.orElseGet(() -> Math.random()));
System.out.println(opt.orElseThrow());
```

Imprime 95.0 tres veces. Ya que el valor existe, no hay necesidad de usar la lógica "**or else**".

**Optional es igual a Null**

* Una alternativa a `Optional` es retornar `null`. Hay algunos inconvenientes con este enfoque. 
* Uno es que no hay una forma clara de expresar que `null` podría ser un valor especial. 
* Por contraste, retornar un `Optional` es un `statement` claro en el API de que podría no haber un valor.
* Otra ventaja de `Optional` es que puedes usar un estilo de programación funcional con `ifPresent()` y los otros métodos en lugar de necesitar una sentencia `if`. 
* Finalmente, verás hacia el final del capítulo que puedes encadenar llamadas a `Optional`.

## Usando Streams

Un stream en Java es una secuencia de datos. Un stream pipeline consiste de las operaciones que se ejecutan en un stream para producir un resultado. 

### Entendiendo el flujo de pipeline

* Piensa en un stream pipeline como una línea de ensamblaje en una fábrica. 
* Supongamos que estamos ejecutando una línea de ensamblaje para hacer letreros para las exhibiciones de animales en el zoológico. 
* Tenemos varios trabajos. Es el trabajo de una persona sacar los letreros de una caja. 
* Es el trabajo de una segunda persona pintar el letrero. 
* Es el trabajo de una tercera persona poner con stencil el nombre del animal en el letrero. 
* Es el trabajo de la última persona poner el letrero completado en una caja para ser llevado a la exhibición apropiada.

* Nota que la segunda persona no puede hacer nada hasta que un letrero haya sido sacado de la caja por la primera persona. 
* Similarmente, la tercera persona no puede hacer nada hasta que un letrero haya sido pintado, y la última persona no puede hacer nada hasta que esté con stencil.
* La línea de ensamblaje para hacer letreros es finita. Una vez que procesamos el contenido de nuestra caja de letreros, hemos terminado. 
* Los streams Finite tienen un límite. Otras líneas de ensamblaje esencialmente corren para siempre, como una para producción de alimentos. 
* Por supuesto, se detienen en algún punto cuando la fábrica cierra, pero pretendamos que eso no sucede. 
* O piensa en un ciclo de amanecer/atardecer como infinite, ya que no termina por un período de tiempo inordinadamente largo.
* Otra característica importante de una línea de ensamblaje es que cada persona toca cada elemento para hacer su operación, y luego esa pieza de datos se ha ido. 
* No regresa. La siguiente persona trata con ella en ese punto. Esto es diferente a las listas y colas que viste en el capítulo anterior. 
* Con una lista, puedes acceder a cualquier elemento en cualquier momento. Con una cola, estás limitado en cuáles elementos puedes acceder, pero todos los elementos están ahí. 
* Con streams, los datos no se generan por adelantado—se crean cuando se necesitan. Este es un ejemplo de `lazy evaluation`, que retrasa la ejecución hasta que sea necesaria.

* Muchas cosas pueden suceder en las estaciones de la línea de ensamblaje a lo largo del camino. 
* En programación funcional, estas se llaman `stream operations`. Justo como con la línea de ensamblaje, las operaciones ocurren en un pipeline. 
* Alguien tiene que comenzar y terminar el trabajo, y puede haber cualquier número de estaciones en el medio. 
* Después de todo, ¡un trabajo con una persona no es una línea de ensamblaje! Hay tres partes para un stream pipeline, como se muestra en Figure 10.2.

![ch10_01_03.png](images/ch10_01_03.png)

1. **Source**: De dónde viene el stream.
2. **Intermediate operations**: Transforma el stream en otro. Puede haber tan pocas o tantas operaciones intermedias como quieras. Ya que los streams usan `lazy evaluation`, las operaciones intermedias no se ejecutan hasta que la operación terminal se ejecuta.
3. **Terminal operation**: Produce un resultado. Ya que los streams solo pueden usarse una vez, el stream ya no es válido después de que una operación terminal se completa.

* Nota que las operaciones son desconocidas para nosotros. Al ver la línea de ensamblaje desde afuera, solo te importa qué entra y qué sale. 
* Lo que sucede en el medio es un detalle de implementación. Necesitarás conocer las diferencias entre operaciones intermedias y terminales bien. 

![ch10_01_04.png](images/ch10_01_04.png)

* Una fábrica típicamente tiene un capataz que supervisa el trabajo. 
* Java sirve como el capataz cuando trabaja con stream pipelines. 
* Este es un rol realmente importante, especialmente cuando se trata con `lazy evaluation` y streams infinitos. 
* Piensa en declarar el stream como dar instrucciones al capataz. 
* A medida que el capataz descubre qué necesita hacerse, configuran las estaciones y les dicen a los trabajadores cuáles serán sus deberes. 
* Sin embargo, los trabajadores no comienzan hasta que el capataz les dice que empiecen. 
* El capataz espera hasta que vean la operación terminal para iniciar el trabajo. 
* También observan el trabajo y detienen la línea tan pronto como el trabajo se completa.

![ch10_01_05.png](images/ch10_01_05.png)

No estamos usando código en estos ejemplos porque es realmente importante entender el concepto de stream pipeline antes de comenzar a escribir el código. 
Figure 10.3 muestra un stream pipeline con una operación intermedia.

* Veamos qué sucede desde el punto de vista del capataz. 
* Primero, ven que el source está sacando letreros de la caja. 
* El capataz configura un trabajador en la mesa para desempacar la caja y dice que espere una señal para comenzar. 
* Luego el capataz ve la operación intermedia para pintar el letrero. 
* Configuran un trabajador con pintura y le dicen que espere una señal para comenzar. 
* Finalmente, el capataz ve la operación terminal para poner los letreros en una pila. 
* Configuran un trabajador para hacer esto y gritan que los tres trabajadores deben comenzar.

* Supón que hay dos letreros en la caja. El Step 1 es el primer trabajador sacando un letrero de la caja y pasándolo al segundo trabajador. 
* El Step 2 es el segundo trabajador pintándolo y pasándolo al tercer trabajador. 
* El Step 3 es el tercer trabajador poniéndolo en la pila. Los Steps 4–6 son este mismo proceso para el otro letrero. 
* Luego el capataz ve que no quedan letreros y cierra toda la empresa.

El capataz es inteligente y puede tomar decisiones sobre cómo hacer mejor el trabajo basándose en lo que se necesita. 
Como ejemplo, exploremos el stream pipeline en Figure 10.4.

![ch10_01_06.png](images/ch10_01_06.png)

* El capataz todavía ve un source de sacar letreros de la caja y asigna un trabajador para hacer eso bajo comando. 
* Todavía ven una operación intermedia para pintar y configuran otro trabajador con instrucciones de esperar y luego pintar. 
* Luego ven un step intermedio de que necesitamos solo dos letreros. 
* Configuran un trabajador para contar los letreros que pasan y notificar al capataz cuando el trabajador haya visto dos. 
* Finalmente, configuran un trabajador para la operación terminal de poner los letreros en una pila.

* Esta vez, supón que hay 10 letreros en la caja. Empezamos como la última vez. El primer letrero avanza por el pipeline. 
* El segundo letrero también avanza por el pipeline. Cuando el trabajador encargado de contar ve el segundo letrero, le dice al capataz. 
* El capataz deja que el trabajador de la operación terminal termine su tarea y luego grita, "¡Detengan la línea!" No importa que haya ocho letreros más en la caja. 
* No los necesitamos, así que sería trabajo innecesario pintarlos. ¡Y todos queremos evitar trabajo innecesario!
* Similarmente, el capataz habría detenido la línea después del primer letrero si la operación terminal fuera encontrar el primer letrero que se crea.
* En las siguientes secciones, cubrimos las tres partes del pipeline. También discutimos tipos especiales de streams para primitivos y cómo imprimir un stream.

### Creando un Stream Source

En Java, los streams de los que hemos estado hablando están representados por la interfaz `Stream<T>`, definida en el paquete `java.util.stream.`

### Creando Streams finitos 

Por simplicidad, empezamos con finite streams. Hay algunas formas de crearlos.

```java
11: Stream<String> empty = Stream.empty();           // count = 0
12: Stream<Integer> singleElement = Stream.of(1);    // count = 1
13: Stream<Integer> fromArray = Stream.of(1, 2, 3);  // count = 3
```

La línea 11 muestra cómo crear un `stream` vacío. La línea 12 muestra cómo crear un stream con un solo elemento. 
La línea 13 muestra cómo crear un `stream` desde un `varargs`.
Java también proporciona una forma conveniente de convertir una `Collection` a un `stream`.

```java
14: var list = List.of("a", "b", "c");
15: Stream<String> fromList = list.stream();
```

La línea 15 muestra que es una simple llamada de método para crear un stream desde una lista. Esto es útil, ya que tales conversiones son comunes.

**Creando un Stream paralelo**

```java
24: var list = List.of("a", "b", "c");
25: Stream<String> fromListParallel = list.parallelStream();
```

* Esta es una gran característica porque puedes escribir código que usa concurrencia antes incluso de aprender qué es un thread. 
* Usar `parallel streams` es como configurar múltiples mesas de trabajadores que pueden hacer la misma tarea. 
* Pintar sería mucho más rápido si pudiéramos tener cinco pintores pintando letreros en lugar de solo uno. 
* Solo ten en cuenta que algunas tareas no pueden hacerse en paralelo, como poner los letreros en el orden en que fueron creados en el stream. 
* También ten presente que hay un costo en coordinar el trabajo, así que para streams más pequeños, podría ser más rápido hacerlo secuencialmente. 

### Creando Infinite Streams (streams infinitos)

* Hasta ahora, esto no es particularmente impresionante. Podríamos hacer todo esto con listas. 
* No podemos crear una lista infinita, sin embargo, lo cual hace a los streams más poderosos.

```java
17: Stream<Double> randoms = Stream.generate(Math::random);
18: Stream<Integer> oddNumbers = Stream.iterate(1, n -> n + 2);
```

* La línea 17 genera un stream de números aleatorios. ¿Cuántos números aleatorios? Los que necesites. 
* Si llamas `randoms.forEach(System.out::println)`, el programa imprimirá números aleatorios hasta que lo detengas. 
* Más adelante en el capítulo, aprenderás sobre operaciones como `limit()` para convertir el stream infinito en uno finito.

* La línea 18 te da más control. El método `iterate()` toma una semilla o valor inicial como primer parámetro. 
* Este es el primer elemento que será parte del stream. El otro parámetro es una expresión lambda que recibe el valor anterior y genera el siguiente valor. 
* Como con el ejemplo de números aleatorios, seguirá produciendo números impares mientras los necesites.

**Imprimiendo una referencia de Stream**

Si intentas llamar `System.out.print(stream)`, obtendrás algo como lo siguiente:

`java.util.stream.ReferencePipeline$3@4517d9a3`

Esto es diferente de una Collection, donde ves el contenido. Lo mencionamos para que no te sorprendas cuando escribas código para practicar.

¿Qué pasa si solo quieres números impares menores que 100? Hay una versión sobrecargada de `iterate()` que ayuda:

```java
19: Stream<Integer> oddNumberUnder100 = Stream.iterate(
20:     1,              // seed
21:     n -> n < 100,   // Predicate to specify when done
22:     n -> n + 2);    // UnaryOperator to get next value
```

Este método toma tres parámetros. Nota cómo están separados por comas `(,) `igual que en todos los otros métodos. 
El examen puede intentar engañarte usando punto y coma, ya que es similar a un for loop.

### Revisando métodos para crear streams

![ch10_01_07.png](images/ch10_01_07.png)

### Usando Common Terminal Operations (Operaciones terminales comunes)

Puedes realizar una operación terminal sin ninguna operación intermedia pero no al revés. 
Es por esto que hablamos de operaciones terminales primero. `Reductions` son un tipo especial de operación terminal donde todo el contenido del stream se combina en un solo primitivo u `Object`. 
Por ejemplo, podrías tener un int o una Collection.

![ch10_01_08.png](images/ch10_01_08.png)

### Counting

* El método `count()` determina el número de elementos en un stream finito. 
* Para un stream infinito, nunca termina. ¿Por qué? Cuenta del 1 al infinito, y avísanos cuando hayas terminado. 
* El método `count()` es una reducción porque mira cada elemento en el stream y retorna un solo valor. 
* La firma del método es la siguiente:

`public long count()`

Este ejemplo muestra llamar `count()` en un stream finito:

```java
Stream<String> s = Stream.of("monkey", "gorilla", "bonobo");
System.out.println(s.count()); // 3
```

### Encontrando el máximo y mínimo

* Los métodos `min()` y `max()` te permiten pasar un comparador personalizado y encontrar el valor más pequeño o más grande en un stream finito de acuerdo a ese orden de clasificación. 
* Como el método `count()`, `min()` y `max()` cuelgan en un stream infinito porque no pueden estar seguros de que un valor más pequeño o más grande no vendrá después en el stream. 
* Ambos métodos son reducciones porque retornan un solo valor después de mirar todo el stream. Las firmas de los métodos son las siguientes:

```java
public Optional<T> min(Comparator<? super T> comparator)
public Optional<T> max(Comparator<? super T> comparator)
```

Este ejemplo encuentra el animal con el menor número de letras en su nombre:

```java
Stream<String> s = Stream.of("monkey", "ape", "bonobo");
Optional<String> min = s.min((s1, s2) -> s1.length()-s2.length());
min.ifPresent(System.out::println); // ape
```

* Nota que el código retorna un `Optional` en lugar del valor. Esto permite que el método especifique que no se encontró ningún mínimo o máximo. 
* Usamos el método Optional `ifPresent()` y una referencia a método para imprimir el mínimo solo si se encuentra uno. 
* Como ejemplo de dónde no hay un mínimo, veamos un stream vacío:

```java
Optional<?> minEmpty = Stream.empty().min((s1, s2) -> 0);
System.out.println(minEmpty.isPresent()); // false
```

Como el stream está vacío, el comparador nunca se llama, y no hay valor presente en él `Optional`.

* ¿Qué pasa si necesitas tanto los valores de `min()` como de `max()` del mismo stream? 
* Por ahora, no puedes tener ambos, al menos no usando estos métodos. Recuerda, un stream puede tener solo una operación terminal. 
* Una vez que una operación terminal ha sido ejecutada, el stream no puede ser usado de nuevo. 
* Como verás más adelante en este capítulo, hay métodos de resumen incorporados para algunos streams numeric que calcularán un conjunto de valores para ti.

### Buscando un valor

* Los métodos `findAny()` y `findFirst()` retornan un elemento del stream a menos que el stream esté vacío. 
* Si el stream está vacío, retornan un `Optional` vacío. Este es el primer método que has visto que puede terminar con un stream infinito. 
* Como Java genera solo la cantidad de stream que necesitas, el stream infinito necesita generar solo un elemento.
* Como su nombre lo implica, el método `findAny()` puede retornar cualquier elemento del stream. 
* Cuando se llama sobre los streams que has visto hasta ahora, comúnmente retorna el primer elemento, aunque este comportamiento no está garantizado.
* Como tú verás en el Capítulo 13, el método `findAny()` es más probable que retorne un elemento aleatorio cuando se trabaja con streams paralelos.

* Estos métodos son operaciones terminales pero no reducciones. La razón es que a veces retornan sin procesar todos los elementos. 
* Esto significa que retornan un valor basado en el stream, pero no reducen el stream completo en un único valor.
* Las firmas de los métodos son las siguientes:

```java
public Optional<T> findAny()
public Optional<T> findFirst()
```

Este ejemplo encuentra un animal:

```java
Stream<String> s = Stream.of("monkey", "gorilla", "bonobo");
Stream<String> infinite = Stream.generate(() -> "chimp");

s.findAny().ifPresent(System.out::println); // monkey (usually)
infinite.findAny().ifPresent(System.out::println); // chimp
```

* Encontrar cualquier coincidencia es más útil de lo que suena. 
* A veces solo queremos muestrear los resultados y obtener un elemento representativo, pero no necesitamos desperdiciar el procesamiento generándolos todos. 
* Después de todo, si planeamos trabajar con solo un elemento, ¿por qué molestarse en mirar más?

### Matching 

Los métodos `allMatch()`, `anyMatch()`, y `noneMatch()` buscan en un stream y retornan información sobre cómo el stream se relaciona con el predicado. 
Estos pueden o no terminar para streams infinitos. Depende de los datos. Como los métodos `find`, no son reducciones porque no necesariamente miran todos los elementos.

Las firmas de los métodos son las siguientes:

```java
public boolean anyMatch(Predicate <? super T> predicate)
public boolean allMatch(Predicate <? super T> predicate)
public boolean noneMatch(Predicate <? super T> predicate)
```

Este ejemplo verifica si los nombres de animales comienzan con letras:

```java
var list = List.of("monkey", "2", "chimp");
Stream<String> infinite = Stream.generate(() -> "chimp");
Predicate<String> pred = x -> Character.isLetter(x.charAt(0));

System.out.println(list.stream().anyMatch(pred));    // true
System.out.println(list.stream().allMatch(pred));    // false
System.out.println(list.stream().noneMatch(pred));   // false
System.out.println(infinite.anyMatch(pred));         // true
```

* Esto muestra que podemos reutilizar el mismo predicado, pero necesitamos un stream diferente cada vez. 
* El método `anyMatch()` retorna `true` porque dos de los tres elementos coinciden. 
* El método `allMatch()` retorna false porque uno no coincide. El método `noneMatch()` también retorna false porque al menos uno coincide. 
* En el stream infinito, se encuentra una coincidencia, por lo que la llamada termina. Si llamáramos `allMatch()`, se ejecutaría hasta que matáramos el programa.

* Recuerda que `allMatch()`, `anyMatch()`, y `noneMatch()` retornan un boolean. 
* En contraste, los métodos `find` retornan un `Optional` porque retornan un elemento del stream.

### Iterating

* Como en el Java Collections Framework, es común iterar sobre los elementos de un stream. 
* Como se esperaba, llamar `forEach()` en un stream infinito no termina. Como no hay valor de retorno, no es una reducción.

* Antes de usarlo, considera si otro enfoque sería mejor. Los desarrolladores que aprendieron a escribir loops primero tienden a usarlos para todo. 
* Por ejemplo, un loop con un `if statement` podría ser escrito con un filter. Aprenderás sobre `filters` en la sección de operaciones intermedias.

La firma del método es la siguiente:

`public void forEach(Consumer<? super T> action)`

* Nota que esta es la única operación terminal con un tipo de retorno `void`. Si quieres que algo suceda, tienes que hacer que suceda en él `Consumer`. 
* Aquí hay una forma de imprimir los elementos en el stream (hay otras formas, que cubriremos más adelante en el capítulo):

```java
Stream<String> s = Stream.of("Monkey", "Gorilla", "Bonobo");
s.forEach(System.out::print); // MonkeyGorillaBonobo
```

Recuerda que puedes llamar `forEach()` directamente en una `Collection` o en un `Stream`. 

Nota que no puedes usar un loop for tradicional en un stream.

```java
Stream<Integer> s = Stream.of(1);
for (Integer i : s) {} // DOES NOT COMPILE
```

Aunque `forEach()` suena como un loop, es realmente un operador terminal para streams. 
Los streams no pueden ser usados como fuente en un for-each loop porque no implementan la interfaz `Iterable`.


### Reducing

* El método `reduce()` combina un stream en un único objeto. Es una reducción, lo que significa que procesa todos los elementos. 
* Las tres firmas de métodos son estas:

```java
public T reduce(T identity, BinaryOperator<T> accumulator)

public Optional<T> reduce(BinaryOperator<T> accumulator)

public <U> U reduce(U identity,
  BiFunction<U,? super T,U> accumulator,
  BinaryOperator<U> combiner)
```

* Tomémoslos uno a la vez. La forma más común de hacer una reducción es comenzar con un valor inicial y seguir fusionándolo con el siguiente valor. 
* Piensa en cómo concatenarías un array de objetos String en un único String sin programación funcional. Podría verse algo así:

```java
var array = new String[] { "w", "o", "l", "f" };
var result = "";
for (var s: array) result = result + s;
System.out.println(result); // wolf
```

* El **identity** es el valor inicial de la reducción, en este caso un String vacío. 
* El **accumulator** combina el resultado actual con el valor actual en el stream. 
* Con lambdas, podemos hacer lo mismo con un stream y reducción:

```java
Stream<String> stream = Stream.of("w", "o", "l", "f");
String word = stream.reduce("", (s, c) -> s + c);
System.out.println(word); // wolf
```

* Nota cómo todavía tenemos el `String` vacío como `identity`. 
* También seguimos concatenando los objetos String para obtener el siguiente valor. 
* Incluso podemos reescribir esto con una referencia a método:

```java
Stream<String> stream = Stream.of("w", "o", "l", "f");
String word = stream.reduce("", String::concat);
System.out.println(word); // wolf
```

Intentemos otro. ¿Puedes escribir una reducción para multiplicar todos los objetos `Integer` en un stream?  
Nuestra solución se muestra aquí:

```java
Stream<Integer> stream = Stream.of(3, 5, 6);
System.out.println(stream.reduce(1, (a, b) -> a*b)); // 90
```

* Establecemos el **identity** en 1 y el accumulator en multiplicación. 
* En muchos casos, el **identity** no es realmente necesario, por lo que Java nos permite omitirlo. 
* Cuando no especificas un **identity**, se retorna un **Optional** porque podría no haber ningún dato. 
* Hay tres opciones para lo que está en el Optional:
  * Si el stream está vacío, se retorna un Optional vacío.
  * Si el stream tiene un elemento, se retorna.
  * Si el stream tiene múltiples elementos, el accumulator se aplica para combinarlos.
* Lo siguiente ilustra cada uno de estos escenarios:

```java
BinaryOperator<Integer> op = (a, b) -> a * b;
Stream<Integer> empty = Stream.empty();
Stream<Integer> oneElement = Stream.of(3);
Stream<Integer> threeElements = Stream.of(3, 5, 6);

empty.reduce(op).ifPresent(System.out::println);        // no output
oneElement.reduce(op).ifPresent(System.out::println);   // 3
threeElements.reduce(op).ifPresent(System.out::println); // 90
```

* ¿Por qué hay dos métodos similares? ¿Por qué no simplemente siempre requerir el **identity**? Java podría haber hecho eso. 
* Sin embargo, a veces es bueno diferenciar el caso donde el stream está vacío en lugar del caso donde hay un valor que coincide con el identity siendo retornado del cálculo. 
* La firma que retorna un **Optional** nos permite diferenciar estos casos. 
* Por ejemplo, podríamos retornar `Optional.empty()` cuando el stream está vacío y `Optional.of(3)` cuando hay un valor.
* La tercera firma del método se usa cuando estamos tratando con diferentes tipos. 
* Permite a Java crear reducciones intermedias y luego combinarlas al final. 
* Veamos un ejemplo que cuenta el número de caracteres en cada `String`:

```java
Stream<String> stream = Stream.of("w", "o", "l", "f!");
int length = stream.reduce(0, (i, s) -> i + s.length(), (a, b) -> a + b);
System.out.println(length); // 5
```

* El primer parámetro `(0)` es el valor para el initializer. Si tuviéramos un stream vacío, esta sería la respuesta. 
* El segundo parámetro es el **accumulator**. A diferencia de los accumulators que viste previamente, este maneja tipos de datos mixtos. 
* En este ejemplo, el primer argumento, `i`, es un `Integer`, mientras que el segundo argumento, `s`, es un `String`. 
* Agrega la longitud del `String` actual a nuestro total acumulado. El tercer parámetro se llama el **combiner**, que combina cualquier total intermedio. 
* En este caso, a y b son ambos valores `Integer`.



```java

```

working with primitive streams
working with advanced stream pipeline concepts
summary

