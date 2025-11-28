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

