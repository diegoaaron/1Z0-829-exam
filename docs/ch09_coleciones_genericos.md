# Cap. 09 - Collections and Generics

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

![ch09_01_01.png](images/ch09_01_01.png)

