# Cap. 09 - Collections and Generics

## Usando la colección de API comunes

Una colección es un grupo de objetos contenidos en un único objeto. 

El Java Collections Framework es un conjunto de clases en `java.util` para almacenar colecciones.

El framework tiene cuatro interfaces principales:

* List (Lista):
  * Colección ordenada de elementos
  * Permite duplicados
  * Los elementos se acceden mediante un índice int
  * Ejemplo típico: mantener una lista de tareas donde el orden importa

* Set (Conjunto):
  * Colección que NO permite duplicados
  * No garantiza orden específico (depende de la implementación)
  * Útil cuando necesitas unicidad de elementos

* Queue (Cola):
  * Colección que ordena elementos en un orden específico para procesamiento
  * Normalmente FIFO (First-In-First-Out), pero puede variar
  * **Deque** es una sub interfaz que permite acceso en ambos extremos (como una cola de doble extremo)

* Map (Mapa):
  * NO implementa la interfaz Collection (detalle importante para el examen)
  * Mapea claves a valores
  * No permite claves duplicadas
  * Los elementos son par clave/valor
  * Es considerado parte del Collections Framework aunque técnicamente no sea una Collection 
    * Se tratan diferente porque necesitan métodos distintos debido a que trabajan con pares clave/valor, no con elementos individuales.

![ch09_01_01.png](images/ch09_01_01.png)

### Usando el operador diamante 

Inicialmente, al construir colecciones con generics, debes especificar el tipo:

```java
List<Integer> list = new ArrayList<Integer>();

// con anidamiento
Map<Long,List<Integer>> mapLists = new HashMap<Long,List<Integer>>();
```

El operador diamante (<>) es una notación abreviada que permite omitir el tipo genérico del lado derecho cuando puede inferirse:

* Se llama diamond operator porque <> parece un diamante
* Ambas declaraciones son equivalentes para el compilador
* La versión con `<>` es más corta y fácil de leer

```java
List<Integer> list = new ArrayList<>();
Map<Long,List<Integer>> mapOfLists = new HashMap<>();
```

El operador diamante NO puede usarse en el lado izquierdo de una declaración de variable. Solo en el lado derecho de una asignación.

```java
List<> list = new ArrayList<Integer>();  // DOES NOT COMPILE

class InvalidUse {
    void use(List<> data) {}  // DOES NOT COMPILE
}
```

### Agregando información

Método `add()`:
* Inserta un nuevo elemento en la Collection
* Retorna un boolean indicando si fue exitoso
* La firma del método es `public boolean add(E element)`
  * `E` representa el tipo genérico usado al crear la colección
* Para algunos tipos de Collection, `add()` siempre retorna `true`
* Para otros tipos, hay lógica sobre si la llamada fue exitosa

Ejemplo con List:

```java
3: Collection<String> list = new ArrayList<>();
4: System.out.println(list.add("Sparrow")); // true
5: System.out.println(list.add("Sparrow")); // true
```

Ejemplo con Set:

```java
7: Collection<String> set = new HashSet<>();
8: System.out.println(set.add("Sparrow")); // true
9: System.out.println(set.add("Sparrow")); // false
```

Diferencia:
* List permite duplicados → retorna `true` ambas veces
* Set NO permite duplicados → línea 9 retorna `false` al intentar agregar un duplicado

### Removiendo información

Método `remove()`:
* Remueve un único valor que coincida en la Collection
* Retorna un boolean indicando si fue exitoso
* La firma del método es `public boolean remove(Object object)`

Explicación: El valor boolean de retorno indica si se encontró y removió una coincidencia (solo remueve 1 por intento).

```java
3: Collection<String> birds = new ArrayList<>();
4: birds.add("hawk");  // [hawk]
5: birds.add("hawk");  // [hawk, hawk]
6: System.out.println(birds.remove("cardinal")); // false
7: System.out.println(birds.remove("hawk"));     // true
8: System.out.println(birds);                    // [hawk]
```

### Contando elementos

Métodos: `size()` y `isEmpty()`
* `size()` retorna el número de elementos en la Collection
* `isEmpty()` retorna `true` si la Collection no tiene elementos
* Su firma es:
  * `public int size()`
  * `public boolean isEmpty()`

```java
Collection<String> birds = new ArrayList<>();
System.out.println(birds.isEmpty()); // true
System.out.println(birds.size());    // 0
birds.add("hawk");                   // [hawk]
birds.add("hawk");                   // [hawk, hawk]

System.out.println(birds.isEmpty()); // false
System.out.println(birds.size());    // 2
```

### Limpiando colecciones

Método `clear()`:
* Proporciona una manera fácil de descartar todos los elementos de la Collection.
* Su firma es `public void clear()`

```java
Collection<String> birds = new ArrayList<>();
birds.add("hawk");                   // [hawk]
birds.add("hawk");                   // [hawk, hawk]
System.out.println(birds.isEmpty()); // false
System.out.println(birds.size());    // 2
birds.clear();                       // []
System.out.println(birds.isEmpty()); // true
System.out.println(birds.size());    // 0
```

Después de llamar `clear()`, birds vuelve a ser un ArrayList vacío de tamaño 0.

### Validando contenido

Método `contains()`:
* Verifica si un elemento específico está en la Collection
* Su firma es `public boolean contains(Object object)`
* Funcionamiento; el método `contains()` llama a `equals()` en los elementos del ArrayList para ver si hay coincidencias.

```java
Collection<String> birds = new ArrayList<>();
birds.add("hawk"); // [hawk]
System.out.println(birds.contains("hawk"));  // true
System.out.println(birds.contains("robin")); // false
```

### Removiendo con condiciones

Método `removeIf()`:
* Remueve todos los elementos que coinciden con una condición. 
* Podemos especificar qué debe eliminarse usando un bloque de código o incluso una referencia a método.
* Firma: `public boolean removeIf(Predicate<? super E> filter)`
  * Nota: La signatura parece así. (Se explica qué significa él `? super` más adelante en este capítulo)
* Funcionamiento; Usa un, `Predicate`, que toma un parámetro y retorna un boolean.

```java
4: Collection<String> list = new ArrayList<>();
5: list.add("Magician");
6: list.add("Assistant");
7: System.out.println(list);  // [Magician, Assistant]
8: list.removeIf(s -> s.startsWith("A"));
9: System.out.println(list);  // [Magician]
```

Explicación línea 8; Remueve todos los valores String que comienzan con la letra A.

```java
11: Collection<String> set = new HashSet<>();
12: set.add("Wand");
13: set.add("");
14: set.removeIf(String::isEmpty); // s -> s.isEmpty()
15: System.out.println(set);  // [Wand]
```

Explicación línea 14; Remueve todos los valores String que están vacíos usando una referencia a método.

### Iterando sobre colecciones

Método `forEach()`:
* Puedes llamarlo en una Collection en lugar de escribir un loop. Usa un Consumer que toma un único parámetro y no retorna nada.
* Firma: `public void forEach(Consumer<? super T> action)`

Ejemplo: A los gatos les gusta explorar, así que imprimamos dos de ellos usando tanto referencias a método como lambdas:

```java
Collection<String> cats = List.of("Annie", "Ripley");
cats.forEach(System.out::println);
cats.forEach(c -> System.out.println(c));
```

**Otras formas de iteración**

Hay otras maneras de iterar a través de una Collection cómo hacer loop a través de una lista usando un enhanced for loop.

```java
for (String element: coll)
    System.out.println(element);

// un enfoque más antiguo es: 
Iterator<String> iter = coll.iterator();
while(iter.hasNext()) {
String string = iter.next();
    System.out.println(string);
}
```

El método `hasNext()` verifica si hay un siguiente valor. En otras palabras, te dice si `next()` se ejecutará sin lanzar una excepción. 

El método `next()` realmente mueve el Iterator al siguiente elemento.

### Determinando igualdad

Implementación personalizada de equals():
* Permite comparar dos Collections y comparar el tipo y contenidos. La implementación varía. 
  * Por ejemplo, ArrayList verifica el orden, mientras que HashSet no.
* La firma es `public boolean equals(Object object)`

```java
23: var list1 = List.of(1, 2);
24: var list2 = List.of(2, 1);
25: var set1 = Set.of(1, 2);
26: var set2 = Set.of(2, 1);
27:
28: System.out.println(list1.equals(list2)); // false
29: System.out.println(set1.equals(set2));   // true
30: System.out.println(list1.equals(set1));  // false
```

* Línea 28 imprime false porque los elementos están en diferente orden, y una List se preocupa por el orden
* En contraste, línea 29 imprime true porque un Set no es sensible al orden
* Finalmente, línea 30 imprime false porque los tipos son diferentes

**Desempaquetando nulos**

Java nos protege de muchos problemas con Collections. Sin embargo, aún es posible escribir un NullPointerException:

```java
3: var heights = new ArrayList<Integer>();
4: heights.add(null);
5: int h = heights.get(0); // NullPointerException
```

* Línea 4: agregamos un null a la lista. Esto es legal porque una referencia null puede ser asignada a cualquier variable de referencia
* Línea 5: intentamos hacer unbox de ese `null` a un primitivo `int`. Esto es un problema
* Java intenta obtener el valor int de `null`. Como llamar cualquier método en null genera un `NullPointerException`, eso es justo lo que obtenemos

## Usando la interfaz List

Pasemos a interfaces específicas. Se usa una `list` cuando quieres una colección ordenada

En una lista de nombres puede contener duplicados, ya que dos animales pueden tener el mismo nombre. 
* Los elementos pueden ser recuperados e insertados en posiciones específicas en la lista basándose en un índice `int`, muy parecido a un `array`. 
* A diferencia de un array, sin embargo, muchas implementaciones de List pueden cambiar de tamaño después de ser declaradas.
* Uso común; Las listas se usan donde necesitas mantener un registro de una lista de objetos. 
  * Por ejemplo, podrías hacer una lista de lo que quieres ver en el zoológico: primero, ver los leones; segundo, ver los pandas y así sucesivamente.

* A veces no te importa el orden de elementos en una lista. List es como el tipo de datos "go to". 
* Cuando hacemos una lista de compras antes de ir a la tienda, el orden de la lista resulta ser el orden en el cual pensamos en los artículos. 
  * Probablemente, no estamos apegados a ese orden particular, pero no está haciendo daño.

### Comparando implementación de listas

ArrayList: Un ArrayList es como un array redimensionable. 
* Cuando se agregan elementos, el ArrayList crece automáticamente. Cuando no estás seguro qué colección usar, usa un ArrayList.
* El principal beneficio de un ArrayList es que puedes buscar cualquier elemento en tiempo constante. 
* Agregar o remover un elemento es más lento que acceder a un elemento. 
* Esto hace a un ArrayList una buena elección cuando estás leyendo más a menudo que (o la misma cantidad que) escribiendo al ArrayList.

LinkedList: Un LinkedList es especial porque implementa tanto List como Deque. 
* Tiene todos los métodos de una List. También tiene métodos adicionales para facilitar agregar o remover desde el principio y/o fin de la lista.
* Los principales beneficios de una LinkedList son que puedes acceder, agregar y remover desde el principio y fin de la lista en tiempo constante. 
* El trade-off es que tratar con un índice arbitrario toma tiempo lineal. 
* Esto hace a una LinkedList una buena elección cuando la usarás como Deque. 

### Creating una lista con Factory

Cuando creas una `List` de tipo ArrayList o LinkedList, conoces el tipo. 

Hay unos pocos métodos especiales donde obtienes una List de vuelta, pero no conoces el tipo. 

Estos métodos te permiten crear una List incluyendo datos en una línea usando un factory method. 

Esto es conveniente, especialmente cuando haces testing. 

Algunos de estos métodos retornan un immutable object. 

Como vimos en Chapter 6, "Class Design," un immutable object no puede ser cambiado o modificado. 

![ch09_01_02.png](images/ch09_01_02.png)

```java
16: String[] array = new String[] {"a", "b", "c"};
17: List<String> asList = Arrays.asList(array); // [a, b, c]
18: List<String> of = List.of(array);           // [a, b, c]
19: List<String> copy = List.copyOf(asList);    // [a, b, c]
20:
21: array[0] = "z";
22:
23: System.out.println(asList);  // [z, b, c]
24: System.out.println(of);      // [a, b, c]
25: System.out.println(copy);    // [a, b, c]
26:
27: asList.set(0, "x");
28: System.out.println(Arrays.toString(array)); // [x, b, c]
29:
30: copy.add("y");  // UnsupportedOperationException
```

* Línea 17 crea una List que está respaldada por un array
* Línea 21 cambia el array, y línea 23 refleja ese cambio
* Líneas 27 y 28 muestran la otra dirección donde cambiar la List actualiza el array subyacente
* Líneas 18 y 19 crean una immutable List
* Línea 30 muestra que es immutable al lanzar una excepción cuando se intenta agregar un valor
* Las tres listas lanzarían una excepción al agregar o remover un valor. Las listas of y copy también lanzarían una al intentar actualizar un elemento

### Creando una lista con un constructor

La mayoría de `Collections` tienen dos constructores. Lo siguiente los muestra para `LinkedList`:

```java
var linked1 = new LinkedList<String>();
var linked2 = new LinkedList<String>(linked1);
```

* El primero dice crear una LinkedList vacía conteniendo todos los defaults
* El segundo le dice a Java que queremos hacer una copia de otra LinkedList
* Dado que linked1 está vacío en este ejemplo, no es particularmente interesante

ArrayList tiene un constructor extra que necesitas conocer. Ahora mostramos los tres constructores:

```java
var list1 = new ArrayList<String>();
var list2 = new ArrayList<String>(list1);
var list3 = new ArrayList<String>(10);}
```

* Los primeros dos son los constructores comunes que necesitas conocer para todas las Collections. 
* El ejemplo final dice crear un ArrayList conteniendo un número específico de slots, pero nuevamente no asignar ninguno. 
* Puedes pensar en esto como el tamaño del array subyacente.

**Usando var con ArrayList**

Primer ejemplo - var con generics:
Considera este código, que mezcla var y generics:

```java
var strings = new ArrayList<String>();
strings.add("a");
for (String s: strings) { }
```

El tipo de var es `ArrayList<String>`. Esto significa que puedes agregar un String o hacer loop a través de los objetos String. 

¿Qué pasa si usamos el diamond operator con var?

```java
var list = new ArrayList<>();
```

Esto compila. El tipo del var es `ArrayList<Object>`. 

Como no hay un tipo especificado para él `generic`, Java tiene que asumir la última superclase. 

Esto es un poco tonto e inesperado, así que por favor no lo escribas. Pero si lo ves en el examen, sabrás qué esperar. 

```java
var list = new ArrayList<>();
list.add("a");
for (String s: list) { } // DOES NOT COMPILE
```

El tipo de var es `ArrayList<Object>`. Como no hay un tipo en el diamond operator, Java tiene que asumir la opción genérica más amplia que puede. 

Por lo tanto, elige `Object`, la última superclase. Agregar un String a la lista está bien. 

Puedes agregar cualquier subclase de Object. Sin embargo, en el loop, necesitamos usar el tipo Object en lugar de String.

### Trabajando con métodos de List

Los métodos en la interfaz List son para trabajar con índices. Además de los métodos heredados de Collection

![ch09_01_03.png](images/ch09_01_03.png)

Los siguientes statements demuestran la mayoría de estos métodos para trabajar con una List:

```java
3: List<String> list = new ArrayList<>();
4: list.add("SD");          // [SD]
5: list.add(0, "NY");       // [NY,SD]
6: list.set(1, "FL");       // [NY,FL]
7: System.out.println(list.get(0)); // NY
8: list.remove("NY");       // [FL]
9: list.remove(0);          // []
10: list.set(0, "?");       // IndexOutOfBoundsException
```

* Línea 3, list comienza vacía. 
* Línea 4 agrega un elemento al final de la lista. 
* Línea 5 agrega un elemento en index 0 que empuja el original index 0 a index 1. Nota cómo el ArrayList es ahora automáticamente uno más grande. 
* Línea 6 reemplaza el elemento en index 1 con un nuevo valor.
* Línea 7 usa el método `get()` para imprimir el elemento en un índice específico. 
* Línea 8 remueve el elemento que coincide con NY. 
* Finalmente, línea 9 remueve el elemento en index 0, y list está vacía de nuevo.
* Línea 10 lanza un `IndexOutOfBoundsException` porque no hay elementos en la List. Como no hay elementos para reemplazar, incluso el index 0 no está permitido. 
* Si línea 10 se moviera entre líneas 4 y 5, la llamada tendría éxito.

**Nota sobre LinkedList:** El output sería el mismo si intentaras estos ejemplos con LinkedList. 
Aunque el código sería menos eficiente, no sería notorio hasta que tuvieras listas muy grandes.

Método `replaceAll()`:
Ahora veamos el método `replaceAll()`. Usa un **UnaryOperator** que toma un parámetro y retorna un valor del mismo tipo:

```java
var numbers = Arrays.asList(1, 2, 3);
numbers.replaceAll(x -> x*2);
System.out.println(numbers); // [2, 4, 6]
```

Esta lambda duplica el valor de cada elemento en la lista. El método `replaceAll()` llama a la lambda en cada elemento de la lista y reemplaza el valor en ese índice.

**Método `remove()` sobrecargado**

* Hemos visto ahora dos métodos `remove()` sobrecargados. El de Collection remueve un objeto que coincide con el parámetro. 
* En contraste, el de List remueve un elemento en un índice especificado.

Problema con tipo Integer:
Esto se vuelve complicado cuando tienes un tipo Integer. ¿Qué crees que imprime lo siguiente?

```java
31: var list = new LinkedList<Integer>();
32: list.add(3);
33: list.add(2);
34: list.add(1);
35: list.remove(2);
36: list.remove(Integer.valueOf(2));
37: System.out.println(list);
```

* La respuesta correcta es [3]. Veamos cómo llegamos ahí. 
* Al final de línea 34, tenemos [3, 2, 1]. Línea 35 pasa un primitivo, lo que significa que estamos solicitando eliminación del elemento en index 2. 
* Esto nos deja con [3, 2]. Luego línea 36 pasa un objeto Integer, lo que significa que estamos eliminando el valor 2. Eso nos lleva a que quede [3].

* Cuando se llama a `remove()` con un `int` usa el índice, un índice que no existe lanzará una excepción. 
* Por ejemplo, list.remove(100) lanza un `IndexOutOfBoundsException`.

### Convirtiendo de una lista a un array

Como un array puede ser pasado como `vararg`, la tabla 9.1 cubrió cómo convertir un array a una List. 
Veamos como convertir una List en un array:

```java
13: List<String> list = new ArrayList<>();
14: list.add("hawk");
15: list.add("robin");
16: Object[] objectArray = list.toArray();
17: String[] stringArray = list.toArray(new String[0]);
18: list.clear();
19: System.out.println(objectArray.length);  // 2
20: System.out.println(stringArray.length);  // 2
```

* Línea 16 muestra que una List sabe cómo convertirse a sí misma en un array. 
* El único problema es que por defecto resulta en un array de clase Object. Esto usualmente no es lo que quieres. 
* Línea 17 especifica el tipo del array y hace lo que queremos. 
* La ventaja de especificar un tamaño de 0 para el parámetro es que Java creará un nuevo array del tamaño apropiado para el valor de retorno. 
* Si gustas, puedes sugerir un array más grande para ser usado en su lugar. 
* Si la List cabe en ese array, será retornado. De lo contrario, se creará un nuevo array.
* También, nota que línea 18 limpia la List original. Esto no afecta a ninguno de los arrays. 
* El array es un objeto recién creado sin relación con la List original. Es simplemente una copia.

## Usando la interfaz Set

* Usas un `Set` cuando no quieres permitir entradas duplicadas. 
* Tampoco es importante el orden de los elementos en un Set.

![ch09_01_04.png](images/ch09_01_04.png)

### Comparando implementaciones de Set

`HashSet`:
* Un HashSet almacena sus elementos en una hash table, lo que significa que las claves son un hash y los valores son un Object. 
* Esto significa que el HashSet usa el método `hashCode()` de los objetos para recuperarlos más eficientemente. 
* Recuerda que un `hashCode()` válido no significa que cada objeto obtendrá un valor único, pero el método a menudo está escrito de manera que los valores hash se distribuyan sobre un rango amplio para reducir colisiones.
* El principal beneficio es que agregar elementos y verificar si un elemento está en el set ambos tienen tiempo constante. 
* El trade-off es que pierdes el orden en el cual insertaste los elementos. La mayoría del tiempo, no te preocupa esto en un Set de todas formas, haciendo al HashSet el set más común.

`TreeSet`:
* Un TreeSet almacena sus elementos en una estructura de árbol ordenado. 
* El principal beneficio es que el set está siempre en orden ordenado. 
* El trade-off es que agregar y verificar si un elemento existe toma más tiempo que con un HashSet, especialmente a medida que el árbol crece.

![ch09_01_05.png](images/ch09_01_05.png)

### Trabajando con métodos Set

Como una `List`, puedes crear un `Set` inmutable en una línea o hacer una copia de uno existente.

```java
Set<Character> letters = Set.of('z', 'o', 'o');
Set<Character> copy = Set.copyOf(letters);
```

Tienes que saber cómo los sets se comportan con respecto a los métodos tradicionales de Collection. 

Comencemos con HashSet:

```java
3: Set<Integer> set = new HashSet<>();
4: boolean b1 = set.add(66);  // true
5: boolean b2 = set.add(10);  // true
6: boolean b3 = set.add(66);  // false
7: boolean b4 = set.add(8);   // true
8: set.forEach(System.out::println);
```

**Output:**
Este código imprime tres líneas:
```
66
8
10
```

* Los métodos `add()` son directos. Retornan `true` a menos que el Integer ya esté en él `set`. 
* Línea 6 retorna `false`, porque ya tenemos 66 en él `set`, y un `set` debe preservar unicidad. 
* Línea 8 imprime los elementos del `set` en un orden arbitrario. 
* En este caso, resulta no ser ni orden ordenado ni el orden en el cual agregamos los elementos.

* Recuerda que el método `equals()` se usa para determinar igualdad. 
* El método `hashCode()` se usa para saber en qué bucket buscar de manera que Java no tenga que buscar a través de todo el set para averiguar si un objeto está ahí. 
* El mejor caso es que los hashcode sean únicos y Java tenga que llamar a `equals()` solo en un objeto. 
* El peor caso es que todas las implementaciones retornen el mismo `hashCode()` y Java tenga que llamar a `equals()` en cada elemento del set de todas formas.

Comencemos con TreeSet:

```java
3: Set<Integer> set = new TreeSet<>();
4: boolean b1 = set.add(66);  // true
5: boolean b2 = set.add(10);  // true
6: boolean b3 = set.add(66);  // false
7: boolean b4 = set.add(8);   // true
8: set.forEach(System.out::println);
```

**Output:**

Esta vez, el código imprime lo siguiente:
```
8
10
66
```

* El texto menciona que los elementos se imprimen en su orden natural ordenado. 
* Los números implementan la interfaz `Comparable` en Java, usada para ordenamiento. 

## Usando las interfaces Queue y Deque

* Se usa una Queue cuando los elementos se agregan y remueven en un orden específico. 
* Se puede pensar en una cola como una línea. Por ejemplo, al entrar a un estadio y alguien está esperando en la fila, te pones detrás de esa persona. 
* Esta es una cola FIFO (first-in, first-out).
* Una Deque (cola de doble extremo), pronunciada "deck", es diferente de una cola regular en la que puedes insertar y remover elementos tanto del frente (head) como de la parte trasera (tail). 

![ch09_01_06.png](images/ch09_01_06.png)

### Comparando implementaciones de Deque

* Se vio `LinkedList` anteriormente en la sección de List. Además de ser una lista, es una Deque. 
* El beneficio principal de una `LinkedList` es que implementa tanto las interfaces List como Deque. 
* El trade-off es que no es tan eficiente como una cola "pura". 
* Puedes usar la clase `ArrayDeque` si no necesitas los métodos de List.

### Trabajando con métodos Deque y Queue 

* La interfaz Queue contiene seis métodos, mostrados en la Tabla 9.3. 
* Hay tres piezas de funcionalidad y versiones de los métodos que lanzan una excepción o usan el tipo de retorno, como `null`, para toda la información. 

![ch09_01_07.png](images/ch09_01_07.png)

```java
4: Queue<Integer> queue = new LinkedList<>();
5: queue.add(10);
6: queue.add(4);
7: System.out.println(queue.remove()); // 10
8: System.out.println(queue.peek());   // 4
```

* Las líneas 5 y 6 agregan elementos a la cola. 
* La línea 7 solicita que el primer elemento esperando más tiempo salga de la cola. 
* La línea 8 verifica la siguiente entrada en la cola mientras la deja en su lugar.

* A continuación, pasamos a la interfaz Deque. 
* Dado que la interfaz Deque soporta colas de doble extremo, hereda todos los métodos de Queue y agrega más para que esté claro si estamos trabajando con el frente o la parte trasera de la cola. 
* La Tabla 9.4 muestra los métodos cuando se usa como una cola de doble extremo.

![ch09_01_08.png](images/ch09_01_08.png)

Intentemos un ejemplo que trabaje con ambos extremos de la cola:

```java
Deque<Integer> deque = new LinkedList<>();
```

![ch09_01_09.png](images/ch09_01_09.png)

* Las líneas 13 y 14 agregan exitosamente un elemento al frente y atrás de la cola, respectivamente. 
* Algunas colas están limitadas en tamaño, lo que causaría que ofrecer un elemento a la cola falle. 
* La línea 15 mira el primer elemento en la cola, pero no lo remueve. 
* Las líneas 16 y 17 remueven los elementos de la cola, uno de cada extremo. Esto resulta en una cola vacía. 
* Las líneas 18 y 19 intentan mirar el primer elemento de la cola, lo que resulta en `null`.

* Además de las colas FIFO, existen colas LIFO (last-in, first-out), que comúnmente se denominan **stacks**. 
* Imagina una pila de platos. Siempre agregas o remueves desde la parte superior de la pila para evitar un desastre. 
* Afortunadamente, podemos usar las mismas implementaciones de cola de doble extremo. 
* Se usan diferentes métodos para mayor claridad, como se muestra en la Tabla 9.5.

![ch09_01_10.png](images/ch09_01_10.png)

Intentemos otro usando la Deque como stack:

```java
Deque<Integer> stack = new ArrayDeque<>();
```

![ch09_01_11.png](images/ch09_01_11.png)

* Esta vez, la Figura 9.7 muestra cómo se ve el stack en cada paso del código. 
* Las líneas 13 y 14 colocan exitosamente un elemento en el frente/top del stack. 
* El código restante mira el frente también.

* Al usar una Deque, es realmente importante determinar si se está usando como una cola FIFO, un stack LIFO, o una cola de doble extremo. 
* Para revisar, una cola FIFO es como una línea de personas. Entras por atrás y sales por el frente. 
* Un stack LIFO es como una pila de platos. Pones el plato en la parte superior y lo sacas de la parte superior. 
* Una cola de doble extremo usa ambos extremos.

## Usando la interfaz Map

* Usas un Map cuando quieres identificar valores por una clave. 
* Por ejemplo, cuando usas la lista de contactos en tu teléfono, buscas "George" en lugar de revisar cada número telefónico por turno.
* No necesitas conocer los nombres de las interfaces específicas que los diferentes maps implementan, pero sí necesitas saber que `TreeMap` es ordenado.
* La cosa principal que todas las clases `Map` tienen en común es que tienen claves y valores. 

![ch09_01_12.png](images/ch09_01_12.png)

**`Map.of()` y `Map.copyOf()`**

Así como `List` y `Set`, existe un método factory para crear un `Map`. Pasas cualquier número de pares de claves y valores.

```java
Map.of("key1", "value1", "key2", "value2");
```

A diferencia de List y Set, esto es menos que ideal.
Pasar claves y valores es más difícil de leer porque tienes que mantener registro de qué parámetro es cuál. 
Afortunadamente, existe una mejor manera. Map también proporciona un método que te permite suministrar pares clave/valor.

```java
Map.ofEntries(
   Map.entry("key1", "value1"),
   Map.entry("key2", "value2"));
```

* Ahora no podemos olvidar pasar un valor. 
* Si omitimos un parámetro, el método `entry()` no compilará. 
* Convenientemente, `Map.copyOf(map)` funciona igual que los métodos `copyOf()` de las interfaces `List` y `Set`.

### Comparando implementaciones de Map

* Un `HashMap` almacena las claves en una tabla hash. 
* Esto significa que usa el método `hashCode()` de las claves para recuperar sus valores de manera más eficiente.
* El beneficio principal es que agregar elementos y recuperar el elemento por clave ambos tienen tiempo constante. 
* El trade-off es que pierdes el orden en el cual insertaste los elementos. 
* La mayoría del tiempo, no te preocupa esto en un map de todas formas. 
* Si lo estuvieras, podrías usar `LinkedHashMap`.
* Un `TreeMap` almacena las claves en una estructura de árbol ordenado. 
* El beneficio principal es que las claves están siempre en orden ordenado. 
* Al igual que un `TreeSet`, el trade-off es que agregar y verificar si una clave está presente toma más tiempo a medida que el árbol crece más grande.

### Trabajando con métodos Map

* Dado que Map no extiende Collection, más métodos están especificados en la interfaz Map. 
* Debido a que hay tanto claves como valores, necesitamos parámetros de tipo genérico para ambos. 
* La clase usa K para clave y V para valor. 

![ch09_01_13.png](images/ch09_01_13.png)

### Llamando a métodos básicos

Comencemos comparando el mismo código con dos tipos de Map. Primero está `HashMap`:

```java
Map<String, String> map = new HashMap<>();
map.put("koala", "bamboo");
map.put("lion", "meat");
map.put("giraffe", "leaf");
String food = map.get("koala"); // bamboo
for (String key: map.keySet())
  System.out.print(key + ","); // koala,giraffe,lion,
```

* Aquí usamos el método `put()` para agregar pares clave/valor al map y `get()` para obtener un valor dada una clave. 
* También usamos el método `keySet()` para obtener todas las claves.
* Java usa el `hashCode()` de la clave para determinar el orden.

Ahora veamos `TreeMap`:

```java
Map<String, String> map = new TreeMap<>();
map.put("koala", "bamboo");
map.put("lion", "meat");
map.put("giraffe", "leaf");
String food = map.get("koala"); // bamboo
for (String key: map.keySet())
  System.out.print(key + ","); // giraffe,koala,lion,
```

* En `TreeMap` se ordena las claves como esperaríamos. 
* Si llamáramos `values()` en lugar de `keySet()`, el orden de los valores correspondería al orden de las claves.

Con nuestro mismo map, podemos intentar algunas verificaciones booleanas:

```java
System.out.println(map.contains("lion")); // DOES NOT COMPILE
System.out.println(map.containsKey("lion")); // true
System.out.println(map.containsValue("lion")); // false
System.out.println(map.size()); // 3
map.clear();
System.out.println(map.size()); // 0
System.out.println(map.isEmpty()); // true
```

* La primera línea es un poco complicada. El método `contains()` está en la interfaz Collection pero no en la interfaz Map. 
* Las siguientes dos líneas muestran que las claves y valores se verifican por separado. 
* Podemos ver que hay tres pares clave/valor en nuestro map. Luego limpiamos el contenido del map y vemos que hay cero elementos y está vacío.

### Iterando a través de Map

* Viste el método `forEach()` anteriormente en el capítulo. 
* Esta vez, la lambda usada por el método `forEach()` tiene dos parámetros: la clave y el valor. 

```java
Map<Integer, Character> map = new HashMap<>();
map.put(1, 'a');
map.put(2, 'b');
map.put(3, 'c');
map.forEach((k, v) -> System.out.println(v));
```

* La lambda tiene tanto la clave como el valor como parámetros. 
* Resulta que imprime el valor, pero podría hacer cualquier cosa con la clave y/o valor. 

Dado que no nos importa la clave, este código en particular podría haber sido escrito con el método `values()` y una referencia a método en su lugar.

```java
map.values().forEach(System.out::println);
```

Otra manera de recorrer todos los datos en un `map` es obtener los pares clave/valor en un `Set`. 

Java tiene una interfaz estática dentro de `Map` llamada `Entry`. Proporciona métodos para obtener la clave y el valor de cada par.

```java
map.entrySet().forEach(e ->
  System.out.println(e.getKey() + " " + e.getValue()));
```

### Obteniendo valores de forma segura

* El método `get()` retorna `null` si la clave solicitada no está en él `map`. 
* Afortunadamente, el método `getOrDefault()` hace esto fácil. Comparemos los dos métodos:

```java
3: Map<Character, String> map = new HashMap<>();
4: map.put('x', "spot");
5: System.out.println("X marks the " + map.get('x'));
6: System.out.println("X marks the " + map.getOrDefault('x', ""));
7: System.out.println("Y marks the " + map.get('y'));
8: System.out.println("Y marks the " + map.getOrDefault('y', ""));

// X marks the spot
// X marks the spot
// Y marks the null
// Y marks the
```

* Como puedes ver, las líneas 5 y 6 tienen la misma salida porque `get()` y `getOrDefault()` se comportan de la misma manera cuando la clave está presente.
* Retornan el valor mapeado por esa clave. Las líneas 7 y 8 dan salida diferente, mostrando que `get()` retorna `null` cuando la clave no está presente.
* Por el contrario, `getOrDefault()` retorna el string vacío que pasamos como parámetro.

### Reemplazando valores

Estos métodos son similares a la versión de `List`, excepto que una clave está involucrada:

```java
21: Map<Integer, Integer> map = new HashMap<>();
22: map.put(1, 2);
23: map.put(2, 4);
24: Integer original = map.replace(2, 10); // 4
25: System.out.println(map); // {1=2, 2=10}
26: map.replaceAll((k, v) -> k + v);
27: System.out.println(map); // {1=3, 2=12}
```

* La línea 24 reemplaza el valor para la clave 2 y retorna el valor original. 
* La línea 26 llama una función y establece el valor de cada elemento del `map` al resultado de esa función. En nuestro caso, sumamos la clave y el valor juntos.

### Poniendo lo ausente

El método `putIfAbsent()` establece un valor en él `map`, pero lo omite si el valor ya está establecido a un valor `no-null`.

```java
Map<String, String> favorites = new HashMap<>();
favorites.put("Jenny", "Bus Tour");
favorites.put("Tom", null);
favorites.putIfAbsent("Jenny", "Tram");
favorites.putIfAbsent("Sam", "Tram");
favorites.putIfAbsent("Tom", "Tram");
System.out.println(favorites); // {Tom=Tram, Jenny=Bus Tour, Sam=Tram}
```

* Como puedes ver, el valor de Jenny no se actualiza porque uno ya estaba presente. 
* Sam no estaba allí, así que fue agregado. 
* Tom estaba presente como clave, pero tenía un valor `null`. Por lo tanto, fue agregado también.

### Uniendo data

* El método `merge()` agrega lógica de qué elegir. Supongamos que queremos elegir el viaje con el nombre más largo. 
* Podemos escribir código para expresar esto pasando una función de mapeo al método `merge()`:

```java
11: BiFunction<String, String, String> mapper = (v1, v2)
12:   -> v1.length() > v2.length() ? v1 : v2;
13:
14: Map<String, String> favorites = new HashMap<>();
15: favorites.put("Jenny", "Bus Tour");
16: favorites.put("Tom", "Tram");
17:
18: String jenny = favorites.merge("Jenny", "Skyride", mapper);
19: String tom = favorites.merge("Tom", "Skyride", mapper);
20:
21: System.out.println(favorites); // {Tom=Skyride, Jenny=Bus Tour}
22: System.out.println(jenny);  // Bus Tour
23: System.out.println(tom);    // Skyride
```

* El código en las líneas 11 y 12 toma dos parámetros y retorna un valor. Nuestra implementación retorna el que tiene el nombre más largo. 
* La línea 18 llama esta función de mapeo, y ve que Bus Tour es más largo que Skyride, así que deja el valor como Bus Tour. 
* La línea 19 llama esta función de mapeo nuevamente. Esta vez, Tram es más corto que Skyride, así que el map se actualiza. 
* La línea 21 imprime el contenido del nuevo mapa. Las líneas 22 y 23 muestran que el resultado se devuelve desde `merge()`.

* El método `merge()` también tiene lógica para qué sucede si valores nulos o claves faltantes están involucradas. 
* En este caso, no llama a la `BiFunction` en absoluto, y simplemente usa el nuevo valor.

```java
BiFunction<String, String, String> mapper =
    (v1, v2) -> v1.length() > v2.length() ? v1 : v2;
Map<String, String> favorites = new HashMap<>();
favorites.put("Sam", null);
favorites.merge("Tom", "Skyride", mapper);
favorites.merge("Sam", "Skyride", mapper);
System.out.println(favorites); // {Tom=Skyride, Sam=Skyride}
```

* Observa que la función de mapeo no se llama. Si lo fuera, tendríamos una `NullPointerException`. 
* La función de mapeo se usa solo cuando hay dos valores reales para decidir entre ellos.
* Lo último a saber sobre `merge()` es qué sucede cuando la función de mapeo se llama y devuelve `null`. La clave se elimina del mapa cuando esto sucede:

```java
BiFunction<String, String, String> mapper = (v1, v2) -> null;
Map<String, String> favorites = new HashMap<>();
favorites.put("Jenny", "Bus Tour");
favorites.put("Tom", "Bus Tour");

favorites.merge("Jenny", "Skyride", mapper);
favorites.merge("Sam", "Skyride", mapper);
System.out.println(favorites); // {Tom=Bus Tour, Sam=Skyride}
```

* Tom se dejó solo, ya que no hubo llamada a `merge()` para esa clave. 
* Sam se agregó, ya que esa clave no estaba en la lista original. 
* Jenny fue eliminada porque la función de mapeo devolvió `null`.

![ch09_01_14.png](images/ch09_01_14.png)

## Comparando tipos de Collection

Concluimos esta sección con una revisión de todas las clases de colección.

![ch09_01_15.png](images/ch09_01_15.png)

![ch09_01_16.png](images/ch09_01_16.png)

Las estructuras de datos que involucran ordenamiento no permiten valores nulos.

**Colecciones viejas**

* Hay algunas colecciones que ya no están en el examen, pero que podrías encontrar en código más antiguo. 
* Las tres fueron estructuras de datos tempranas de Java que podrías usar con hilos:

* Vector: Implements List.
* Hashtable: Implements Map.
* Stack: Implements Queue.

Estas clases se usan raramente ahora, ya que hay alternativas concurrentes mucho mejores que cubrimos en Chapter 13.

## Ordenando Data

Discutimos "orden" para las clases `TreeSet` y `TreeMap`. Para números, el orden es obvio orden numérico. 
Para objetos String, el orden se define según el mapeo de caracteres Unicode.

* Cuando trabajas con un String, recuerda que los números se ordenan antes que las letras, y las letras en mayúsculas se ordenan antes que las letras en minúsculas.
* Usamos `Collections.sort()` en muchos de estos ejemplos. Devuelve void porque el parámetro del método es lo que se ordena.
* También puedes ordenar objetos que creas tú mismo. Java proporciona una interfaz llamada `Comparable`. 
* Si tu clase implementa `Comparable`, puede usarse en estructuras de datos que requieren comparación. 
* También hay una clase llamada `Comparator`, que se usa para especificar que quieres usar un orden diferente al que el objeto mismo proporciona.
* El elemento `Comparable` y `Comparator` son lo suficientemente similares como para ser complicados. 

### Creando una clase Comparable 

La interfaz Comparable tiene solo un método. De hecho, esta es la interfaz completa:

```java
public interface Comparable<T> {
    int compareTo(T o);
}
```

* El genérico **T** te permite implementar este método y especificar el tipo de tu objeto. 
* Esto te permite evitar un cast al implementar `compareTo()`. Cualquier objeto puede ser Comparable. 
* Por ejemplo, tenemos un grupo de patos y queremos ordenarlos por nombre. 
* Primero, actualizamos la declaración de la clase para heredar `Comparable<Duck>`, y luego implementamos el método `compareTo()`:

```java
import java.util.*;
public class Duck implements Comparable<Duck> {
    private String name;
    public Duck(String name) {
        this.name = name;
    }
    public String toString() {  // use readable output
        return name;
    }
    public int compareTo(Duck d) {
        return name.compareTo(d.name); // sorts ascendingly by name
    }
    public static void main(String[] args) {
        var ducks = new ArrayList<Duck>();
        ducks.add(new Duck("Quack"));
        ducks.add(new Duck("Puddles"));
        Collections.sort(ducks);  // sort by name
        System.out.println(ducks);  // [Puddles, Quack]
    }
}
```

* Sin implementar esa interfaz, todo lo que tendríamos es un método llamado `compareTo()`, pero no sería un objeto Comparable. 
* También podríamos implementar `Comparable<Object> `o alguna otra clase para **T**, pero esto no sería tan útil para ordenar un grupo de objetos Duck.

La clase Duck sobrescribe el método toString() de Object. Este override proporciona una salida útil al imprimir patos. Sin este override, 
la salida sería algo como [Duck@70dea4e, Duck@5c647e05] apenas útil para ver qué nombre de pato viene primero.

La clase Duck implementa `compareTo()`. Como Duck está comparando objetos de tipo String y la clase String ya tiene un método `compareTo()`, puede simplemente delegar.
Todavía necesitamos saber qué devuelve el método `compareTo()` para que podamos escribir el nuestro. Hay tres reglas que conocer:

* El número 0 se devuelve cuando el objeto actual es equivalente al argumento de `compareTo()`.
* Un número negativo (menor que 0) se devuelve cuando el objeto actual es más pequeño que el argumento de `compareTo()`.
* Un número positivo (mayor que 0) se devuelve cuando el objeto actual es más grande que el argumento de `compareTo()`.

Veamos una implementación de `compareTo()` que compara números en lugar de objetos String:

```java
1: public class Animal implements Comparable<Animal> {
2:     private int id;
3:     public int compareTo(Animal a) {
4:         return id - a.id;        // sorts ascending by id
5:     }
6:     public static void main(String[] args) {
7:         var a1 = new Animal();
8:         var a2 = new Animal();
9:         a1.id = 5;
10:        a2.id = 7;
11:        System.out.println(a1.compareTo(a2)); // -2
12:        System.out.println(a1.compareTo(a1)); // 0
13:        System.out.println(a2.compareTo(a1)); // 2
14:    }
}
```

* Las líneas 7 y 8 crean dos objetos Animal. Las líneas 9 y 10 establecen sus valores de id. 
* Esta no es una buena forma de establecer variables de instancia. Sería mejor usar un constructor o método setter. 
* Las líneas 3–5 muestran una forma de comparar dos valores int. 
* Podríamos haber usado `Integer.compare(id, a.id)` en su lugar. 

* Las líneas 11–13 confirman que hemos implementado `compareTo()` correctamente. 
* La línea 11 compara un, id más pequeño con uno más grande, y, por lo tanto, imprime un número negativo. 
* La línea 12 compara animales con el mismo id, y, por lo tanto, imprime 0. 
* La línea 13 compara un, id más grande con uno más pequeño, y, por lo tanto, devuelve un número positivo.

### Casteando argumentos de compareTo()

Cuando se trata con código legacy o código que no usa genéricos, el método `compareTo()` requiere un cast, ya que se le pasa un Object.

```java
public class LegacyDuck implements Comparable {
    private String name;
    public int compareTo(Object obj) {
        LegacyDuck d = (LegacyDuck) obj; // cast because no generics
        return name.compareTo(d.name);
    }
}
```

Como no especificamos un tipo genérico para `Comparable`, Java asume que queremos un `Object`, lo que significa que tenemos que hacer cast a `LegacyDuck` antes de acceder a las variables de instancia en él.

### Revisando Null

* Cuando trabajas con `Comparable` y `Comparator` en este capítulo, tendemos a asumir que los datos tienen valores, pero esto no es siempre el caso. 
* Al escribir tus propios métodos `compare`, deberías verificar los datos antes de compararlos si no está validado antes de tiempo.

```java
public class MissingDuck implements Comparable<MissingDuck> {
    private String name;
    public int compareTo(MissingDuck quack) {
        if (quack == null)
            throw new IllegalArgumentException("Poorly formed duck!");
        if (this.name == null && quack.name == null)
            return 0;
        else if (this.name == null) return -1;
        else if (quack.name == null) return 1;
        else return name.compareTo(quack.name);
    }
}
```

Este método lanza una excepción si se le pasa un objeto `MissingDuck` nulo. 

¿Qué pasa con el ordenamiento? Si el nombre de un pato es `null`, se ordena primero.

### Manteniendo consistencia entre compareTo() and equals()

* Si escribes una clase que implementa `Comparable`, introduces nueva lógica de negocio para determinar igualdad. 
* El método `compareTo()` devuelve 0 si dos objetos son iguales, mientras que el método `equals()` devuelve true si dos objetos son iguales. 
* Un natural ordering que usa `compareTo()` se dice que es consistente con `equals` si, y solo si, `x.equals(y)` es **true** cuando `x.compareTo(y)` es igual a **0**.
* De manera similar, `x.equals(y)` debe ser **false** cuando `x.compareTo(y)` no es **0**. 
* Se debe asegurar que las clases `Comparable` creadas, sean consistentes con `equals` porque no todas las clases de `Collection` se comportan predeciblemente si los métodos `compareTo()` y `equals()` no son consistentes.
* Por ejemplo, la siguiente clase `Product` define un método `compareTo()` que no es consistente con `equals`:

```java
public class Product implements Comparable<Product> {
    private int id;
    private String name;
    
    public int hashCode() { return id; }
    public boolean equals(Object obj) {
        if(!(obj instanceof Product)) return false;
        var other = (Product) obj;
        return this.id == other.id;
    }
    public int compareTo(Product obj) {
        return this.name.compareTo(obj.name);
    }
}
```

* Podrías estar ordenando objetos `Product` por nombre, pero los nombres no son únicos. 
* El método `compareTo()` no tiene que ser consistente con `equals`. 
* Una forma de arreglar eso es usar un `Comparator` para definir el ordenamiento en otro lugar.
* Ahora que sabes cómo implementar objetos `Comparable`, puedes mirar un `Comparator` y enfocarte en las diferencias.

### Comparando data con Comparator

* A veces quieres ordenar un objeto que no implementó `Comparable`, o quieres ordenar objetos de diferentes maneras en diferentes momentos. 
* Supongamos que agregamos peso a nuestra clase Duck. Ahora tenemos lo siguiente:

```java
1: import java.util.ArrayList;
2: import java.util.Collections;
3: import java.util.Comparator;
4:
5: public class Duck implements Comparable<Duck> {
6:     private String name;
7:     private int weight;
8:
9:     // Assume getters/setters/constructors provided
10:
11:    public String toString() { return name; }
12:
13:    public int compareTo(Duck d) {
14:        return name.compareTo(d.name);
15:    }
16:
17:    public static void main(String[] args) {
18:      Comparator<Duck> byWeight = new Comparator<Duck>() {
19:        public int compare(Duck d1, Duck d2) {
20:                return d1.getWeight()-d2.getWeight();
21:        }
22:      };
23:      var ducks = new ArrayList<Duck>();
24:      ducks.add(new Duck("Quack", 7));
25:      ducks.add(new Duck("Puddles", 10));
26:      Collections.sort(ducks);
27:      System.out.println(ducks); // [Puddles, Quack]
28:      Collections.sort(ducks, byWeight);
29:      System.out.println(ducks); // [Quack, Puddles]
30:    }
31: }
```
* Primero, observa que este programa importa `java.util.Comparator` en la línea 3. 
* Aquí, mostramos él `import` para llamar la atención al hecho de que `Comparable` y `Comparator` están en diferentes paquetes: `java.lang` y `java.util`, respectivamente. 
* Eso significa que `Comparable` puede usarse sin un `import statement`, mientras que `Comparator` no puede.
* La clase `Duck` en sí misma solo puede definir un método `compareTo()`. En este caso, se eligió `name`. 
* Si queremos ordenar por algo más, tenemos que definir ese orden de ordenamiento fuera del método `compareTo()` usando una clase separada o expresión lambda.
* Las líneas 18–22 del método `main()` muestran cómo definir un `Comparator` usando una clase interna. 
* En las líneas 26–29, ordenamos sin él `Comparator` y luego con él `Comparator` para ver la diferencia en la salida.
* Él `Comparator` es una interfaz funcional, ya que solo hay un método abstracto para implementar. 
* Esto significa que podemos reescribir él `Comparator` en las líneas 18–22 usando una expresión lambda, como se muestra aquí:

```java
Comparator<Duck> byWeight = (d1, d2) -> d1.getWeight()-d2.getWeight();
```

En el siguiente ejemplo, `Comparator.comparing()` es un método de interfaz estático que crea un `Comparator` dada una expresión lambda o referencia de método. 

```java
Comparator<Duck> byWeight = Comparator.comparing(Duck::getWeight);
```

**¿Es `Comparable` una interfaz funcional?**

* Dijimos que `Comparator` es una interfaz funcional porque tiene un solo método abstracto. 
* `Comparable` también es una interfaz funcional, ya que también tiene un solo método abstracto. 
* Sin embargo, usar una lambda para `Comparable` sería tonto. El punto de `Comparable` es implementarlo dentro del objeto que se está comparando.

### Comparando Comparable y Comparator

![ch09_01_17.png](images/ch09_01_17.png)

El examen intentará engañarte mezclando los dos y viendo si puedes detectarlo. ¿Ves por qué esto no compila?

```java
var byWeight = new Comparator<Duck>() { // DOES NOT COMPILE
    public int compareTo(Duck d1, Duck d2) {
        return d1.getWeight()-d2.getWeight();
    }
};
```

* El nombre del método está mal. Un Comparator debe implementar un método llamado `compare()`. 
* Presta atención especial a los nombres de los métodos y al número de parámetros cuando veas `Comparator` y `Comparable` en preguntas.

### Comparando multiples campos 

* Al escribir un `Comparator` que compara múltiples variables de instancia, el código se vuelve un poco desordenado. 
* Supongamos que tenemos una clase `Squirrel`, como se muestra aquí:

```java
public class Squirrel {
    private int weight;
    private String species;
    // Assume getters/setters/constructors provided
}
```

Queremos escribir un `Comparator` para ordenar por nombre de especie. 

Si dos ardillas son de la misma especie, queremos ordenar la que pesa menos primero. Podríamos hacer esto con código que se ve así:

```java
public class MultiFieldComparator implements Comparator<Squirrel> {
    public int compare(Squirrel s1, Squirrel s2) {
        int result = s1.getSpecies().compareTo(s2.getSpecies());
        if (result != 0) return result;
        return s1.getWeight()-s2.getWeight();
    }
}
```

* Esto funciona asumiendo que ningún nombre de especie es `null`. 
* Verifica un campo. Si no coinciden, hemos terminado ordenando. Si coinciden, mira el siguiente campo. 
* Esto no es fácil de leer, sin embargo. También es fácil equivocarse. Cambiar `!=` a `==` rompe el ordenamiento completamente.

Alternativamente, podemos usar referencias de método y construir él `Comparator`. Este código representa lógica para la misma comparación:

```java
Comparator<Squirrel> c = Comparator.comparing(Squirrel::getSpecies)
        .thenComparingInt(Squirrel::getWeight);
```

* Esta vez, encadenamos los métodos. Primero, creamos un `Comparator` en especies ascendente. 
* Luego, si hay un empate, ordenamos por peso. También podemos ordenar en orden descendente. 
* Algunos métodos en `Comparator`, como `thenComparingInt()`, son métodos por defecto.
* Supongamos que queremos ordenar en orden descendente por especies.

```java
var c = Comparator.comparing(Squirrel::getSpecies).reversed();
```
![ch09_01_18.png](images/ch09_01_18.png)

![ch09_01_19.png](images/ch09_01_19.png)

### Ordenamiento y busqueda

Ahora que has aprendido todo sobre `Comparable` y `Comparator`, finalmente podemos hacer algo útil con ellos, como ordenar. 
El método `Collections.sort()` usa el método `compareTo()` para ordenar. Espera que los objetos a ordenar sean `Comparable`.

```java
2: public class SortRabbits {
3:     static record Rabbit(int id) {}
4:     public static void main(String[] args) {
5:     List<Rabbit> rabbits = new ArrayList<>();
6:     rabbits.add(new Rabbit(3));
7:     rabbits.add(new Rabbit(1));
8:     Collections.sort(rabbits); // DOES NOT COMPILE
9: } }
```
* Java sabe que el `record` Rabbit no es `Comparable`. Sabe que el ordenamiento fallará, por lo que ni siquiera deja que el código compile. 
* Puedes arreglar esto pasando un `Comparator` a `sort()`. 
* Recuerda que un `Comparator` es útil cuando quieres especificar el orden de ordenamiento sin usar un método `compareTo()`.

```java
8:     Comparator<Rabbit> c = (r1, r2) -> r1.id - r2.id;
9:     Collections.sort(rabbits, c);
10:    System.out.println(rabbits); // [Rabbit[id=1], Rabbit[id=3]]
```

Supongamos que quieres ordenar los conejos en orden descendente. 
Podrías cambiar él `Comparator` a `r2.id - r1.id`. Alternativamente, podrías invertir el contenido de la lista después:

```java
8:     Comparator<Rabbit> c = (r1, r2) -> r1.id - r2.id;
9:     Collections.sort(rabbits, c);
10:    Collections.reverse(rabbits);
11:    System.out.println(rabbits); // [Rabbit[id=3], Rabbit[id=1]]
```

Los métodos `sort()` y `binarySearch()` te permiten pasar un objeto `Comparator` cuando no quieres usar el orden natural.

**Revisando binarySearch()**

El método `binarySearch()` requiere una List ordenada.

```java
11: List<Integer> list = Arrays.asList(6,9,1,8);
12: Collections.sort(list); // [1, 6, 8, 9]
13: System.out.println(Collections.binarySearch(list, 6)); // 1
14: System.out.println(Collections.binarySearch(list, 3)); // -2
```

* La línea 12 ordena la `List` para que podamos llamar a `binarySearch()` apropiadamente. 
* La línea 13 imprime el índice en el cual se encuentra una coincidencia. 
* La línea 14 imprime uno menos que el índice negado de donde el valor solicitado necesitaría ser insertado. 
* El número 3 necesitaría ser insertado en el índice 1 (después del número 1 pero antes del número 6). Negar eso nos da -1, y restar 1 nos da -2.

Hay un truco al trabajar con `binarySearch()`. ¿Qué piensas que producen las siguientes salidas?

```java
3: var names = Arrays.asList("Fluffy", "Hoppy");
4: Comparator<String> c = Comparator.reverseOrder();
5: var index = Collections.binarySearch(names, "Hoppy", c);
6: System.out.println(index);
```

* La respuesta resulta ser -1. Antes de que entres en pánico, no necesitas saber que la respuesta es -1. 
* Sí necesitas saber que la respuesta no está definida. La línea 3 crea una lista, [Fluffy, Hoppy]. 
* Esta lista resulta estar ordenada en orden ascendente. 
* La línea 4 crea un Comparator que invierte el orden natural. 
* La línea 5 solicita una búsqueda binaria en orden descendente. 
* Como la lista no está en ese orden, no cumplimos la precondición para hacer una búsqueda.
* Aunque el resultado de llamar a `binarySearch()` en una lista ordenada inapropiadamente es indefinido, a veces puedes tener suerte. 
* Por ejemplo, la búsqueda comienza en el medio de una lista numerada impar. Si pides el elemento del medio, el índice devuelto será el que esperas.
* Anteriormente en el capítulo, hablamos sobre colecciones que requieren que las clases implementen Comparable. A diferencia del ordenamiento, no verifican que hayas implementado Comparable en tiempo de compilación.
* Volviendo a nuestro Rabbit que no implementa Comparable, intentamos agregarlo a un TreeSet:

```java
2: public class UseTreeSet {
3:     static class Rabbit{ int id; }
4:     public static void main(String[] args) {
5:         Set<Duck> ducks = new TreeSet<>();
6:         ducks.add(new Duck("Puddles"));
7:
8:         Set<Rabbit> rabbits = new TreeSet<>();
9:         rabbits.add(new Rabbit()); // ClassCastException
10: } }
```

La línea 6 está bien. `Duck` implementa `Comparable`. `TreeSet` puede ordenarlo en la posición apropiada en el set. 
La línea 9 es un problema. Cuando `TreeSet` intenta ordenarlo, Java descubre el hecho de que `Rabbit` no implementa `Comparable`. 
Java lanza una excepción que se ve así:

```java
Exception in thread "main" java.lang.ClassCastException:
    class Rabbit cannot be cast to class java.lang.Comparable
```

* Puede parecer extraño que esta excepción se lance cuando el primer objeto se agrega al set. 
* Después de todo, no hay nada con que comparar todavía. Java funciona de esta manera por consistencia.
* Tal como buscar y ordenar, puedes decirle a las colecciones que requieren ordenamiento que quieres usar un `Comparator` específico. Por ejemplo:

```java
8: Set<Rabbit> rabbits = new TreeSet<>((r1, r2) -> r1.id - r2.id);
9: rabbits.add(new Rabbit());
```

* Ahora Java sabe que quieres ordenar por ID, y todo está bien. 
* Un `Comparator` es un objeto útil. Te permite separar el orden de ordenamiento del objeto a ordenar. 
* Observa que la línea 9 en ambos ejemplos anteriores es la misma. Es la declaración del `TreeSet` la que ha cambiado.

### Ordenando una Lista

Aunque puedes llamar a `Collections.sort(list)`, también puedes ordenar directamente en el objeto list.

```java
3: List<String> bunnies = new ArrayList<>();
4: bunnies.add("long ear");
5: bunnies.add("floppy");
6: bunnies.add("hoppy");
7: System.out.println(bunnies);  // [long ear, floppy, hoppy]
8: bunnies.sort((b1, b2) -> b1.compareTo(b2));
9: System.out.println(bunnies);  // [floppy, hoppy, long ear]
```

* En la línea 8, ordenamos la lista alfabéticamente. 
* El método `sort()` toma un `Comparator` que proporciona el orden de ordenamiento. 
* Recuerda que `Comparator` toma dos parámetros y devuelve un `int`. 
* No hay un método sort en Set o Map. Ambos tipos son desordenados, por lo que no tendría sentido ordenarlos.

## Trabajando con Generics

Los generics son una de las características más útiles y a veces más confusas de Java. 
Han sido usados extensivamente en los dos capítulos anteriores (el tipo entre `<>`).

```java
14: static void printNames(List list) {
15:   for (int i = 0; i < list.size(); i++) {
16:     String name = (String) list.get(i); // ClassCastException
17:     System.out.println(name);
18:   }
19: }
20: public static void main(String[] args) {
21:   List names = new ArrayList();
22:   names.add(new StringBuilder("Webby"));
23:   printNames(names);
24: }
```

* El código arroja un `ClassCastException`. La línea 22 es legal porque una lista no genérica puede contener cualquier cosa. 
* Sin embargo, la línea 16 espera que haya un tipo específico (`String`). El cast refleja esa suposición. 
* Como la suposición es incorrecta, se arroja una excepción indicando que `StringBuilder` no puede ser casteado a `String`.

Solución con Generics:
* Los generics permiten escribir y usar tipos parametrizados. 
* Al especificar `ArrayList<String>`, el compilador tiene suficiente información para prevenir este problema desde el principio.

```java
List<String> names = new ArrayList<String>();
names.add(new StringBuilder("Webby")); // DOES NOT COMPILE
```

Obtener un error del compilador es bueno, porque sabes inmediatamente que algo está mal en lugar de descubrirlo después.

### Creando clases genéricas

Puedes introducir generics en tus propias clases, comenzando a explicar la sintaxis.

Un `generic` se declara dentro de `<>`. 
Por ejemplo, la siguiente clase `Crate` tiene una variable de tipo genérico declarada después del nombre de la clase:

```java
public class Crate<T> {
  private T contents;
  public T lookInCrate() {
    return contents;
  }
  public void packCrate(T contents) {
    this.contents = contents;
  }
}
```

* El tipo genérico `T` está disponible en cualquier lugar dentro de la clase `Crate`. 
* Cuando instancias la clase, le indicas al compilador qué debe ser `T` para esa instancia particular.

**Convención de nombres para Genericos**

* Un parámetro de este tipo puede nombrarse como quieras. 
* La convención es usar letras mayúsculas solas para hacer obvio que no son nombres de clases reales. 
* Las siguientes son letras comunes para usar:
  * E for an element
  * K for a map key
  * V for a map value
  * N for a number
  * T for a generic data type
  * S, U, V, and so forth for multiple generic types

Supongamos que existe una clase `Elephant`, y estamos moviendo nuestro elefante a un recinto nuevo y más grande en nuestro zoológico. 

```java
Elephant elephant = new Elephant();
Crate<Elephant> crateForElephant = new Crate<>();
crateForElephant.packCrate(elephant);
Elephant inNewHome = crateForElephant.lookInCrate();
```

* Puedes ver que la clase `Crate` es capaz de trabajar con un Elephant sin saber nada sobre él.
* Esto probablemente no parece particularmente impresionante. Podría haber simplemente escrito `Elephant` en lugar de `T` al codificar `Crate`. 

¿Qué pasa si quisiéramos crear un Crate para otro animal?

```java
Crate<Zebra> crateForZebra = new Crate<>();
```

Ahora no podría haber simplemente hard-coded `Elephant` en la clase `Crate` ya que una Zebra no es un Elephant. 
Sin embargo, podríamos haber creado una superclase o interfaz Animal y usado eso en `Crate`.
Las clases genéricas se vuelven útiles cuando las clases usadas como parámetro de tipo no pueden tener absolutamente nada que ver entre sí. 

```java
Robot joeBot = new Robot();
Crate<Robot> robotCrate = new Crate<>();
robotCrate.packCrate(joeBot);
// ship to Houston
Robot atDestination = robotCrate.lookInCrate();
```

* Ahora se está poniendo interesante. La clase `Crate` funciona con cualquier tipo de clase. 
* Antes de los `generics`, habríamos necesitado que `Crate` usara la clase `Object` para su variable de instancia, lo que habría puesto la carga en el invocador para hacer `cast` del objeto que recibe.
* Además de que `Crate` no necesita saber sobre los objetos que entran en ella, esos objetos no necesitan saber sobre Crate. 
* No estamos requiriendo que los objetos implementen una interfaz llamada `Creatable` o similar. Una clase puede ser puesta en `Crate` sin ningún cambio.

Las clases genéricas no están limitadas a tener un solo parámetro de tipo. Esta clase muestra dos parámetros genéricos:

```java
public class SizeLimitedCrate<T, U> {
  private T contents;
  private U sizeLimit;
  public SizeLimitedCrate(T contents, U sizeLimit) {
    this.contents = contents;
    this.sizeLimit = sizeLimit;
  }
}
```

`T` representa el tipo que estamos poniendo en la jaula. `U` representa la unidad que estamos usando para medir el tamaño máximo de la jaula. 

Para usar esta clase genérica, podemos escribir lo siguiente:

```java
Elephant elephant = new Elephant();
Integer numPounds = 15_000;
SizeLimitedCrate<Elephant, Integer> c1
  = new SizeLimitedCrate<>(elephant, numPounds);
```

Aquí especificamos que el tipo es `Elephant`, y la unidad es `Integer`. También incluimos un recordatorio de que los literales numéricos pueden contener guiones bajos.

### Entendiendo el tipo Erasure

* Especificar un tipo genérico permite al compilador hacer cumplir el uso apropiado del tipo genérico.
* Por ejemplo, especificar el tipo genérico de `Crate` como `Robot` es como reemplazar la `T` en la clase Crate con `Robot`.
* Sin embargo, esto es solo para tiempo de compilación.
* Detrás de escenas, el compilador reemplaza todas las referencias a `T` en Crate con `Object`.
* En otras palabras, después de que el código compila, tus `generics` son simplemente tipos `Object`.
* La clase `Crate` se ve como lo siguiente en tiempo de ejecución:

```java
public class Crate {
  private Object contents;
  public Object lookInCrate() {
    return contents;
  }
  public void packCrate(Object contents) {
    this.contents = contents;
  }
}
```
* Esto significa que hay solo un archivo de clase. No hay diferentes copias para diferentes tipos parametrizados. (Algunos otros lenguajes funcionan de esa manera.) 
* Este proceso de remover la sintaxis de generics de tu código es referido como `type erasure`. 
* Él `Type erasure` permite que tu código sea compatible con versiones antiguas de Java que no contienen generics. 
* El compilador agrega los casts relevantes para que tu código funcione con este tipo de clase con `type erasure`. Por ejemplo, escribes lo siguiente:

```java
Robot r = crate.lookInCrate();
```

El compilador lo convierte en lo siguiente:

```java
Robot r = (Robot) crate.lookInCrate();
```

En las siguientes secciones, veremos las implicaciones de los generics para declaraciones de métodos.

### Sobrecarga con un método generico

Solo uno de estos dos métodos está permitido en una clase porque `type erasure` reducirá ambos conjuntos de argumentos a `(List input)`:

```java
public class LongTailAnimal {
    protected void chew(List<Object> input) {}
    protected void chew(List<Double> input) {} // DOES NOT COMPILE
}
```

Por la misma razón, tampoco puedes sobrecargar un método genérico desde una clase padre.

```java
public class LongTailAnimal {
    protected void chew(List<Object> input) {}
}

public class Anteater extends LongTailAnimal {
    protected void chew(List<Double> input) {} // DOES NOT COMPILE
}
```

* Ambos ejemplos fallan al compilar debido a `type erasure`. 
* En la forma compilada, el tipo genérico es eliminado, y aparece como un método sobrecargado inválido. Ahora, veamos una subclase:

```java
public class Anteater extends LongTailAnimal {
    protected void chew(List<Object> input) {}
    protected void chew(ArrayList<Double> input) {}
}
```

* El primer método `chew()` compila porque usa el mismo tipo genérico en el método sobrescrito que el definido en la clase padre. 
* El segundo método `chew()` compila también. Sin embargo, es un método sobrecargado porque uno de los argumentos del método es un `List` y el otro es un `ArrayList`. 
* Al trabajar con métodos genéricos, es importante considerar el tipo subyacente.

### Retornando tipos genericos

* Cuando trabajas con métodos sobrescritos que retornan generics, los valores de retorno deben ser covariantes. 
* En términos de `generics`, esto significa que el tipo de retorno de la clase o interfaz declarada en el método que sobrescribe debe ser un subtipo de la clase definida en la clase padre. 
* El parámetro de tipo genérico debe coincidir exactamente con el tipo del padre.

Dada la siguiente declaración para la clase `Mammal`, ¿cuál de las dos subclases, Monkey y Goat, compila?

```java
public class Mammal {
  public List<CharSequence> play() { ... }
  public CharSequence sleep() { ... }
}

public class Monkey extends Mammal {
  public ArrayList<CharSequence> play() { ... }
}

public class Goat extends Mammal {
  public List<String> play() { ... } // DOES NOT COMPILE

  public String sleep() { ... }
}
```

* La clase `Monkey` compila porque `ArrayList` es un subtipo de `List`. El método `play()` en la clase `Goat` no compila, sin embargo. 
* Para que los tipos de retorno sean covariantes, el parámetro de tipo genérico debe coincidir. 
* Aunque `String` es un subtipo de `CharSequence`, no coincide exactamente con el tipo genérico definido en la clase `Mammal`. 
* Por lo tanto, esto se considera un `override` inválido.
* Nota que el método `sleep()` en la clase `Goat` sí compila ya que `String` es un subtipo de `CharSequence`. 
* Este ejemplo muestra que la covarianza se aplica al tipo de retorno, no solo al parámetro de tipo genérico.
* Para el examen, puede ser útil aplicar `type erasure` a preguntas que involucren generics para asegurar que compilen apropiadamente. 
* Una vez que hayas determinado qué métodos están sobrescritos y cuáles están siendo sobrecargados, trabaja hacia atrás, asegurándote de que los tipos genéricos coincidan para métodos sobrescritos. 
* Y recuerda, los métodos genéricos no pueden ser sobrecargados cambiando solo el parámetro de tipo genérico.

### Implementando interfaces genéricas

Al igual que una clase, una interfaz puede declarar un parámetro de tipo formal. 
Por ejemplo, la siguiente interfaz `Shippable` usa un tipo genérico como argumento para su método `ship()`:

```java
public interface Shippable<T> {
  void ship(T t);
}
```

Hay tres formas en que una clase puede abordar la implementación de esta interfaz. 
La primera es especificar el tipo genérico en la clase. La siguiente clase concreta dice que solo trabaja con robots. 
Esto le permite declarar el método `ship()` con un parámetro Robot:

```java
class ShippableRobotCrate implements Shippable<Robot> {
  public void ship(Robot t) {}
}
```

La siguiente forma es crear una clase genérica. La siguiente clase concreta permite al invocador especificar el tipo del generic:

```java
class ShippableAbstractCrate<U> implements Shippable<U> {
    public void ship(U t) {}
}
```

En este ejemplo, el parámetro de tipo podría haberse nombrado como cualquier cosa, incluyendo `T`. 
Usamos `U` en el ejemplo para evitar confusión sobre a qué se refiere `T`. 

La forma final es no usar generics en absoluto. Esta es la forma antigua de escribir código. 
Genera un compiler warning sobre que `Shippable` es un `raw type`, pero sí compila. 
Aquí el método `ship()` tiene un parámetro `Object` ya que el tipo genérico no está definido:

```java
class ShippableCrate implements Shippable {
    public void ship(Object t) {}
}
```

**Que se puede hacer con los tipos genéricos**

Hay algunas limitaciones sobre lo que puedes hacer con un tipo genérico. 
La mayoría de las limitaciones se deben a `type erasure`. 
Oracle se refiere a tipos cuya información está completamente disponible en tiempo de ejecución como `reifiable`. 
Los tipos `reificables` pueden hacer cualquier cosa que Java permita. Los tipos no `reificables` tienen algunas limitaciones.

Aquí están las cosas que no puedes hacer con generics (y por "can't", significa sin recurrir a contorsiones como pasar un objeto de clase):

* Call a constructor: Escribir `new T()` no está permitido porque en tiempo de ejecución, sería `new Object()`.
* Create an array of that generic type: Esto es lo más molesto, pero tiene sentido porque estarías creando un array de valores `Object`.
* Call instanceof: Esto no está permitido porque en tiempo de ejecución `List<Integer>` y `List<String>` se ven iguales para Java, gracias a `type erasure`.
* Use a primitive type as a generic type parameter: Esto no es gran problema porque puedes usar la clase wrapper en su lugar. Si quieres un tipo de `int`, simplemente usa `Integer`.
* Create a static variable as a generic type parameter: Esto no está permitido porque el tipo está vinculado a la instancia de la clase.

### Escribiendo métodos genéricos

Hasta este punto, has visto parámetros de tipo formal declarados a nivel de clase o interfaz. 
También es posible declararlos a nivel de método. Esto es a menudo útil para métodos estáticos, ya que no son parte de una instancia que puede declarar el tipo. 
Sin embargo, también está permitido en métodos no estáticos.

En este ejemplo, ambos métodos usan un parámetro genérico:

```java
public class Handler {
    public static <T> void prepare(T t) {
        System.out.println("Preparing " + t);
    }
    public static <T> Crate<T> ship(T t) {
        System.out.println("Shipping " + t);
        return new Crate<T>();
    }
}
```

El parámetro del método es el tipo genérico `T`. Antes del tipo de retorno, declaramos el parámetro de tipo formal de `<T>`. 
En el método `ship()`, mostramos cómo puedes usar el parámetro genérico en el tipo de retorno, `Crate<T>`, para el método.
A menos que un método esté obteniendo el parámetro de tipo formal genérico de la clase/interfaz, se especifica inmediatamente antes del tipo de retorno del método. 
Esto puede llevar a código que se ve interesante:

```java
2: public class More {
3:   public static <T> void sink(T t) {}
4:   public static <T> T identity(T t) { return t; }
```

