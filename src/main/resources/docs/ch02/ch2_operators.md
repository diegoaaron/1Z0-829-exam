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


continuar en la 5


---------------------------------------------------------------------
**Palabra** cuando es una palabra en inglés importante que tiene sentido traducirla, pero no es una palabra reservada

() version en ingles de la palabra anterior

`   `  solo cúando es una línea de código o una palabra reservada que va a ser explicada

```java

```


Working with Binary Arithmetic Operators
Applying Binary Arithmetic Operators
Assigning Values
Comparing Values
Making Decisions with the Ternary Operator
Making Decisions with the Ternary Operator