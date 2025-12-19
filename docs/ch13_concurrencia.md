# Concurrencia

* En este capítulo, se introduce el concepto de threads y se proporcionan numerosas formas de gestionar threads usando la Concurrency API. 
* Los threads y la concurrencia son temas desafiantes para muchos programadores, ya que los problemas con threads pueden ser frustrantes incluso para desarrolladores veteranos. 
* En la práctica, los problemas de concurrencia están entre los más difíciles de diagnosticar y resolver.

## Introducing Threads

* Se revisa la terminología común asociada con threads. Un thread es la unidad más pequeña de ejecución que puede ser programada por el sistema operativo. 
* Un process es un grupo de threads asociados que se ejecutan en el mismo entorno compartido. 
* Se deduce entonces que un single-threaded es uno que contiene exactamente un thread, mientras que un multithreaded process soporta más de un thread.

* Por shared environment (entorno compartido), se refiere a que los threads en el mismo proceso comparten el mismo espacio de memoria y pueden comunicarse directamente unos con otros. 
* Se hace referencia a la Figure 13.1 para una visión general de los threads y su entorno compartido dentro de un proceso.

* Por shared environment (entorno compartido), se refiere a que los threads en el mismo proceso comparten el mismo espacio de memoria y pueden comunicarse directamente unos con otros. 
* Se hace referencia a la Figure 13.1 para una visión general de los threads y su entorno compartido dentro de un proceso.
* Esta figura muestra un único proceso con tres threads. También muestra cómo están mapeados a un número arbitrario de `n` CPU disponibles dentro del sistema. 
* Se debe mantener este diagrama en mente cuando se discutan los task schedulers más adelante en esta sección.

* En este capítulo, se habla mucho sobre tasks y sus relaciones con los threads. Un task es una única unidad de trabajo realizada por un thread. 
* A lo largo de este capítulo, un task comúnmente será implementado como una lambda expression. 
* Un thread puede completar múltiples tasks independientes pero solo un task a la vez.

![ch13_01_01.png](images/ch13/ch13_01_01.png)

* Por shared memory en la Figure 13.1, generalmente se está refiriendo a variables estáticas así como a variables de instancia y locales pasadas a un thread. 
* Sí, finalmente se ve cómo las variables estáticas pueden ser útiles para realizar tasks complejas y multithreaded. 
* Se recuerda del Chapter 5, "Methods," que los métodos estáticos y las variables están definidas en un único objeto de clase que todas las instancias comparten. 
* Por ejemplo, si un thread actualiza el valor de un objeto estático, esta información está inmediatamente disponible para que otros threads dentro del proceso la lean.

### Understanding Thread Concurrency

* La propiedad de ejecutar múltiples threads y procesos al mismo tiempo se refiere como concurrency. 
* ¿Cómo decide el sistema qué ejecutar cuando hay más threads disponibles que CPU? 
* Los sistemas operativos usan un thread scheduler para determinar qué threads deberían estar ejecutándose actualmente, como se muestra en Figure 13.1. 
* Por ejemplo, un thread scheduler puede emplear un round-robin schedule en el cual cada thread disponible recibe un número igual de ciclos de CPU con los cuales ejecutar, con los threads visitados en un orden circular.

* Cuando el tiempo asignado de un thread está completo, pero el thread no ha terminado de procesar, ocurre un context switch. 
* Un context switch es el proceso de almacenar el estado actual de un thread y posteriormente restaurar el estado del thread para continuar la ejecución. 
* Se debe tener en cuenta que a menudo hay un costo asociado con un context switch debido al tiempo perdido y a tener que recargar el estado de un thread. 
* Los thread schedulers inteligentes hacen su mejor esfuerzo para minimizar el número de context switches mientras mantienen una aplicación ejecutándose suavemente.

* Finalmente, un thread puede interrumpir o reemplazar a otro thread si tiene una prioridad de thread más alta que el otro thread. 
* Un thread priority es un valor numérico asociado con un thread que es tomado en consideración por el thread scheduler al determinar qué threads deberían estar ejecutándose actualmente. 
* En Java, las prioridades de thread se especifican como valores enteros.

### Creating a Thread

* Una de las formas más comunes de definir un task para un thread es usando la instancia Runnable. 
* Runnable es una interfaz funcional que no toma argumentos y no retorna datos.

```java
@FunctionalInterface public interface Runnable {
    void run();
}
```

Con esto, es fácil crear e iniciar un thread. De hecho, puedes hacerlo en una línea de código usando la clase Thread:

```java
new Thread(() -> System.out.print("Hello")).start();
System.out.print("World");
```

* La primera línea crea un nuevo objeto Thread y luego lo inicia con el método start(). ¿Este código imprime HelloWorld o WorldHello? 
* La respuesta es que no lo sabemos. Dependiendo de la prioridad del thread/scheduler, cualquiera es posible. 
* Se debe recordar que el orden de ejecución de threads no está garantizado a menudo. 
* El examen comúnmente presenta preguntas en las cuales múltiples tasks se inician al mismo tiempo, y debes determinar el resultado.

Echemos un vistazo a un ejemplo más complejo:

```java
Runnable printInventory = () -> System.out.println("Printing zoo inventory");
Runnable printRecords = () -> {
  for (int i = 0; i < 3; i++)
    System.out.println("Printing record: " + i);
};
```

Dadas estas instancias, ¿cuál es la salida de lo siguiente?

```java
3: System.out.println("begin");
4: new Thread(printInventory).start();
5: new Thread(printRecords).start();
6: new Thread(printInventory).start();
7: System.out.println("end");
```

La respuesta es que es desconocido hasta el tiempo de ejecución. Lo siguiente es solo una posible salida:

begin
Printing record: 0
Printing zoo inventory
end
Printing record: 1
Printing zoo inventory
Printing record: 2

* Este ejemplo usa un total de cuatro threads: el thread usuario main() y tres threads adicionales creados en las líneas 4–6. 
* Cada thread creado en estas líneas es ejecutado como un asynchronous task. 
* Por asynchronous, se refiere a que el thread que ejecuta el método main() no espera los resultados de cada thread recién creado antes de continuar. 
* Por ejemplo, las líneas 5 y 6 pueden ser ejecutadas antes de que el thread creado en la línea 4 termine. 
* Lo opuesto a este comportamiento es un synchronous task en el cual el programa espera (o blocks) en la línea 4 para que el thread termine de ejecutarse antes de moverse a la siguiente línea. 
* La gran mayoría de las llamadas a métodos usadas en este libro han sido sincrónicas hasta este capítulo.

* Mientras que el orden de ejecución de threads es indeterminado una vez que los threads han sido iniciados, el orden dentro de un único thread es aún lineal. 
* En particular, el loop for() sigue estando ordenado. También, begin siempre aparece antes de end.

---------------------------------------------------------------------
**Calling run() Instead of start()**
* En el examen, se debe tener cuidado con código que intenta iniciar un thread llamando a run() en lugar de start(). 
* Llamar a run() en un Thread o un Runnable does not start a new thread. 
* Mientras que los siguientes fragmentos de código compilarán, ninguno ejecutará un task en un thread separado:

```java
System.out.println("begin");
new Thread(printInventory).run();
new Thread(printRecords).run();
new Thread(printInventory).run();
System.out.println("end");
```

* A diferencia del ejemplo anterior, cada línea de este código esperará hasta que el método run() esté completo antes de moverse a la siguiente línea. 
* También a diferencia del programa anterior, la salida para este ejemplo de código será la misma cada vez que se ejecute.
---------------------------------------------------------------------

Más generalmente, podemos crear un Thread y su task asociado de una de dos maneras en Java:

Lado derecho:
* Provide a Runnable object or lambda expression to the Thread constructor.
* Create a class that extends Thread and overrides the run() method.

* A lo largo de este libro, se prefiere crear tasks con lambda expressions. 
* Después de todo, es mucho más fácil, especialmente cuando llegamos a la Concurrency API. 
* Crear una clase que extiende Thread es relativamente poco común y solo debería hacerse bajo ciertas circunstancias, como si necesitas sobrescribir otros métodos de thread.

### Distinguishing Thread Types

* Puede sorprenderte que todas las aplicaciones Java, incluyendo todas las que hemos presentado en este libro, son multithreaded porque incluyen system threads. 
* Un system thread es creado por la Java Virtual Machine (JVM) y se ejecuta en el fondo de la aplicación. 
* Por ejemplo, garbage collection es gestionado por un system thread creado por la JVM.

* Alternativamente, un user-defined thread es uno creado por el desarrollador de aplicación para llevar a cabo un task específico. 
* La mayoría de los programas que hemos presentado hasta ahora han contenido solo un user-defined thread, el cual llama al método main(). 
* Por simplicidad, comúnmente nos referimos a programas que contienen solo un único user-defined thread como single-threaded applications.

* Tanto los system threads como los user-defined threads pueden ser creados como daemon threads. 
* Un daemon thread es uno que no impedirá que la JVM salga cuando el programa termine. 
* Una aplicación Java termina cuando los únicos threads que están ejecutándose son daemon threads. 
* Por ejemplo, si garbage collection es el único thread que queda ejecutándose, la JVM se apagará automáticamente.

Echemos un vistazo a un ejemplo. ¿Qué crees que este código produce como salida?

```java
1: public class Zoo {
2:   public static void pause() {        // Defines the thread task
3:     try {
4:       Thread.sleep(10_000);            // Wait for 10 seconds
5:     } catch (InterruptedException e) {}
6:     System.out.println("Thread finished!");
7:   }
8:
9:   public static void main(String[] unused) {
10:    var job = new Thread(() -> pause());  // Create thread
11:
12:    job.start();                        // Start thread
13:    System.out.println("Main method finished!");
14:  } }
```

El programa producirá dos declaraciones aproximadamente con 10 segundos de diferencia:

Main method finished!
--- 10 second wait ---
Thread finished!

* Eso es correcto. Aunque el método main() está terminado, la JVM esperará a que el user thread esté hecho antes de finalizar el programa. 
* ¿Qué pasa si cambiamos job para que sea un daemon thread agregando esto a la línea 11?

`11:    job.setDaemon(true);`

El programa imprimirá la primera declaración y terminará sin nunca imprimir la segunda línea.

`Main method finished!`

Para el examen, solo hay que recordar que por defecto, los user-defined threads no son daemons, y el programa esperará a que terminen.

### Managing a Thread's Life Cycle

Después de que un thread ha sido creado, está en uno de seis estados, mostrado en Figure 13.2. 
Puedes consultar el estado de un thread llamando a getState() en el objeto thread.

continuar en la 6






```java

```

---------------------------------------------------------------------

Creating Threads with the Concurrency API
Writing Thread-Safe Code
Using Concurrent Collections
Identifying Threading Problems
Working with Parallel Streams