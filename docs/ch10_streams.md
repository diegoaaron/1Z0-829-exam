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

Piensa en un stream pipeline como una línea de ensamblaje en una fábrica. Supongamos que




working with primitive streams
working with advanced stream pipeline concepts
summary

