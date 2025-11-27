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

Explicación: El valor boolean de retorno indica si se encontró y removió una coincidencia.

```java
3: Collection<String> birds = new ArrayList<>();
4: birds.add("hawk");  // [hawk]
```

continuar en la hoja 3 
