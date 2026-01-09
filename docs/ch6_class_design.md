# Class Design

## Understanding Inheritance

* Cuando creas una nueva clase en Java, puedes definir la clase como heredando de una clase existente. 
* Herencia (Inheritance) es el proceso por el cual una subclase automáticamente incluye ciertos miembros de la clase, incluyendo primitivos, objetos, o métodos, definidos en la clase padre.

Para propósitos ilustrativos, nos referimos a cualquier clase que hereda de otra clase como una subclass o **child class**, ya que es considerada un descendiente de esa clase. 
Alternativamente, nos referimos a la clase de la que la clase hija hereda como la superclass o **parent class**, ya que es considerada un ancestro de la clase.

### Declaring a Subclass

Comencemos con la declaración de una clase y su subclase. La Figura 6.1 muestra un ejemplo de una superclase, Mammal, y subclase Rhinoceros.

![ch06_01.png](images/ch06/ch06_01.png)

Indicamos que una clase es una subclase al declararla con la palabra clave `extends`. 
No necesitamos declarar nada en la superclase más que asegurarnos de que no esté marcada como `final`. 

* Un aspecto clave de la herencia es que es transitiva. 
* Dadas tres clases [X, Y, Z], si `X` extends `Y`, y `Y` extends `Z`, entonces `X` es considerada una subclase o descendiente de `Z`. 
* De la misma manera, `Z` es una superclase o ancestro de `X`. 
* A veces usamos el término direct subclass o descendant para indicar que la clase directamente extends la clase padre. 
* Por ejemplo, `X` es un descendiente directo solo de la clase `Y`, no de `Z`.

* Cuando una clase hereda de una clase padre, todos los miembros `public` y `protected` están automáticamente disponibles como parte de la clase hija. 
* Si las dos clases están en el mismo package, entonces los miembros package están disponibles para la clase hija. 
* Por último, pero no menos importante, los miembros `private` están restringidos a la clase en la que están definidos y nunca están disponibles vía herencia. 
* Esto no significa que la clase padre no pueda tener miembros `private` que puedan contener datos o modificar un objeto; solo significa que la subclase no tiene acceso directo a ellos.

Echemos un vistazo a un ejemplo simple:

```java
public class BigCat {
    protected double size;
}

public class Jaguar extends BigCat {
```

continuar en la página 2






---------------------------------------------------------------------
**Palabra** cuando es una palabra en inglés importante que tiene sentido traducirla, pero no es una palabra reservada

() version en ingles de la palabra anterior

`   `  solo cúando es una línea de código o una palabra reservada que va a ser explicada

```java

```


Understanding Inheritance
Creating Classes
Declaring Constructors
Initializing Objects
Inheriting Members
Creating Abstract Classes
Creating Immutable Objects