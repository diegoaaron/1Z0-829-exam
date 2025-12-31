# Operators

## Understanding Java Operators

Un operator de Java es un símbolo especial que puede ser aplicado a un conjunto de variables, valores, o literales y que retorna un resultado

### Types of Operators

* Java soporta tres tipos de operadores: unary, binary, y ternary. 
* Estos tipos de operadores pueden ser aplicados a uno, dos, o tres operands, respectivamente. 

* Los operadores Java no son necesariamente evaluados de izquierda a derecha. 
* En este siguiente ejemplo, la segunda expresión es en realidad evaluada de derecha a izquierda, dados los operadores específicos involucrados:

```java
int cookies = 4;
double reward = 3 + 2 * --cookies;
System.out.print("Zoo animal receives: "+reward+" reward points");
```

En este ejemplo, primero decrementas cookies a 3, luego multiplicas el valor resultante por 2, y finalmente sumas 3. 
El valor entonces es automáticamente promovido de 9 a 9.0 y asignado a reward. 
Los valores finales de reward y cookies son 9.0 y 3, respectivamente.

### Operator Precedence

En matemáticas, ciertos operadores pueden sobreescribir otros operadores y ser evaluados primero. 
Determinar qué operadores son evaluados en qué orden es referido como operator precedence. 
De esta manera, Java sigue más de cerca las reglas para matemáticas. Considera la siguiente expresión:

`var perimeter = 2 * height + 2 * length;`

* El operador de multiplicación (*) tiene una mayor precedencia que el operador de adición (+), así que height y length son ambos multiplicados por 2 antes de ser sumados juntos. 
* El operador de asignación (=) tiene el menor orden de precedencia, así que la asignación a la variable perimeter es realizada al último.

A menos que sea sobreescrito con paréntesis, los operadores Java siguen el orden de operación, listados en Table 2.1, por orden decreciente de precedencia de operador. 
Si dos operadores tienen el mismo nivel de precedencia, entonces Java garantiza evaluación de izquierda a derecha para la mayoría de operadores distintos a los marcados en la tabla.

![ch02_01_01.png](images/ch02/ch02_01_01.png)


## Applying Unary Operators

* Por definición, un operador unary es uno que requiere exactamente una variable, para funcionar. 
* Como se muestra en Table 2.2, a menudo realizan tareas simples, tales como incrementar una variable numérica por uno o negar un valor boolean.

![ch02_01_02.png](images/ch02/ch02_01_02.png)

### Complement and Negation Operators

* El operador complemento (!) voltea el valor de una expresión boolean. 
* Por ejemplo, si el valor es true, será convertido a false, y viceversa. 

```java
boolean isAnimalAsleep = false;
System.out.print(isAnimalAsleep); // false
isAnimalAsleep = !isAnimalAsleep;
System.out.print(isAnimalAsleep); // true
```

Para el examen, también necesitas conocer sobre el operador bitwise (~), que voltea todos los 0 y 1 en un número. 
Solo puede ser aplicado a tipos numéricos enteros tales como byte, short, char, int, y long. Intentemos un ejemplo. 
Por simplicidad, solo mostramos los últimos cuatro bits (en lugar de todos los 32 bits).

```java
int value = 3;        // Stored as 0011
int complement = ~value;    // Stored as 1100
System.out.println(value);  // 3
System.out.println(complement); // -4
```

Para saber el valor de complemento de un numero al cual se le aplico bitwise solo debes; multiplícalo por uno negativo y luego resta uno.

```java
System.out.println(-1*value - 1);  // -4
System.out.println(-1*complement - 1); // 3
```

Pasando a operadores más comunes, el negation operator (-) revierte el signo de una expresión numérica, como se muestra en estos statements:

```java
double zooTemperature = 1.21;
System.out.println(zooTemperature); // 1.21
zooTemperature = -zooTemperature;
System.out.println(zooTemperature); // -1.21
```

Por ejemplo, ninguna de las siguientes líneas de código compilará:

```java
int pelican = !5;    // DOES NOT COMPILE
boolean penguin = -true; // DOES NOT COMPILE
boolean peacock = !0;  // DOES NOT COMPILE
```

* La primera sentencia no compilará porque en Java no puedes negar un valor numérico y asignarlo a un entero. 
* La segunda sentencia no compila porque no puedes negar numéricamente un valor boolean; necesitas usar el operador logical inverse. 
* Finalmente, la última sentencia no compila porque no puedes tomar el complemento lógico de un valor numérico, ni puedes asignar un entero a una variable boolean.

### Increment and Decrement Operators

* Los operadores incremento y decremento, **++** y **--**, respectivamente, pueden ser aplicados a variables numéricas y tienen un alto orden de precedencia comparados con operadores binarios.
* Se debe tener un cuidado especial porque el orden en el cual están adjuntos a su variable asociada puede hacer una diferencia en cómo una expresión es procesada.

![ch02_01_03.png](images/ch02/ch02_01_03.png)

El siguiente fragmento de código ilustra esta distinción:

```java
int parkAttendance = 0;
System.out.println(parkAttendance);  // 0
System.out.println(++parkAttendance); // 1
System.out.println(parkAttendance);  // 1
System.out.println(parkAttendance--); // 1
System.out.println(parkAttendance);  // 0
```

* El primer operador pre-increment actualiza el valor para parkAttendance y muestra el nuevo valor de 1. 
* El siguiente operador post-decrement también actualiza el valor de parkAttendance, pero muestra el valor antes de que ocurra el decremento.

## Working with Binary Arithmetic Operators

### Arithmetic Operators

Los Arithmetic operators son aquellos que operan en valores numéricos. Son mostrados en Table 2.4.

![ch02_01_04.png](images/ch02/ch02_01_04.png)

`int price = 2 * 5 + 3 * 4 - 8;`

Primero, evalúas el 2 * 5 y 3 * 4, lo cual reduce la expresión a esto:

`int price = 10 + 12 - 8;`

Luego, evalúas los términos restantes en orden de izquierda a derecha, resultando en un valor de price de 14. 

El siguiente fragmento de código contiene los mismos valores y operadores, en el mismo orden, pero con dos sets de paréntesis agregados:

`int price = 2 * ((5 + 3) * 4 - 8);`

Esta vez evaluarías el operador addition 5 + 3, lo cual reduce la expresión a lo siguiente:

`int price = 2 * (8 * 4 - 8);`

`short robin = 3 + [(4 * 2) + 4];   // DOES NOT COMPILE`

Este ejemplo no compila porque Java, a diferencia de algunos otros lenguajes de programación, no permite corchetes, [], para ser usados en lugar de paréntesis.

### Division and Modulus Operators

* El operador modulus, algunas veces llamado el remainder operator, es simplemente el residuo cuando dos números son divididos. 
* Por ejemplo, 9 dividido por 3 divide equitativamente y no tiene residuo; por lo tanto, el resultado de 9 % 3 es 0. 
* Por otro lado, 11 dividido por 3 no divide equitativamente; por lo tanto, el resultado de 11 % 3 es 2.

```java
System.out.println(10 / 3); // 3
System.out.println(10 % 3); // 1
```

* Para valores enteros, la división resulta en el floor value del entero más cercano que cumple la operación, mientras que modulus es el valor residuo. 
* Si escuchas la frase **floor value**, solo significa el valor sin nada después del punto decimal. 
* Por ejemplo, el floor value es 4 para cada uno de los valores 4.0, 4.5, y 4.9999999.

### Numeric Promotion Rules
















---------------------------------------------------------------------
**Palabra** cuando es una palabra en inglés importante que tiene sentido traducirla, pero no es una palabra reservada

() version en ingles de la palabra anterior

`   `  solo cúando es una línea de código o una palabra reservada que va a ser explicada

```java

```


Applying Binary Arithmetic Operators
Assigning Values
Comparing Values
Making Decisions with the Ternary Operator
Making Decisions with the Ternary Operator