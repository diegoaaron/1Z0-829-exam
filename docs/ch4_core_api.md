# Core APIs

## Creating and Manipulating Strings

Un string es básicamente una secuencia de caracteres; aquí hay un ejemplo:

```java
String name = "Fluffy";
String name = new String("Fluffy");
```

* Ambos te dan una variable de referencia llamada name apuntando al objeto String "Fluffy". 
* Por ahora, solo recuerda que la clase String es especial y no necesita ser instanciada con new.

Además, los bloques de texto son otra forma de crear un String. Para repasar, este bloque de texto es el mismo que las variables anteriores:

```java
String name = """
    Fluffy""";
```

* Dado que un String es una secuencia de caracteres, probablemente no te sorprenderá escuchar que implementa la interfaz CharSequence. 
* Esta interfaz es una forma general de representar varias clases, incluyendo String y StringBuilder.

### Concatenating

Colocar un String antes del otro String y combinarlos se llama string concatenation. 
No hay muchas reglas que conocer para esto, pero tienes que conocerlas bien:

1. Si ambos operando son numéricos se realiza una adición. 
2. Si uno de los operandos es un String, + significa concatenación.
3. La expresión se evalúa de izquierda a derecha.

```java
System.out.println(1 + 2);        // 3
System.out.println("a" + "b");    // ab
System.out.println("a" + "b" + 3); // ab3
System.out.println(1 + 2 + "c");  // 3c
System.out.println("c" + 1 + 2);  // c12
System.out.println("c" + null);   // cnull
```

* Hay una cosa más que saber sobre concatenación, pero es fácil. 
* En este ejemplo, solo tienes que recordar qué hace +=. Ten en cuenta, s += "2" significa lo mismo que s = s + "2".

```java
4: var s = "1"; // s currently holds "1"
5: s += "2"; // s currently holds "12"
6: s += 3; // s currently holds "123"
7: System.out.println(s);  // 123
```

* En la línea 5, estamos "sumando" dos strings, lo que significa que los concatenamos. 
* La línea 6 intenta engañarte agregando un número, pero es justo como escribimos s = s + 3. 
* Sabemos que un string "más" cualquier otra cosa significa usar concatenación.

### Important String Methods

* Para todos estos métodos, necesitas recordar que un string es una secuencia de caracteres y Java cuenta desde 0 cuando se indexa.
* También necesitas saber que un String es inmutable, o no modificable. 
* Esto significa que llamar a un método en un String devolverá un objeto String diferente en lugar de cambiar el valor de la referencia. 

### Determining the Length

El método `length()` devuelve el número de caracteres en el String

```java
// firma: public int length() 

var name = "animals";
System.out.println(name.length()); // 7
```

### Getting a Single Character

El método `charAt()` te permite consultar el string para averiguar qué carácter está en un índice específico.

```java

```







---------------------------------------------------------------------
**Palabra** cuando es una palabra en inglés importante que tiene sentido traducirla, pero no es una palabra reservada

() version en ingles de la palabra anterior

`   `  solo cúando es una línea de código o una palabra reservada que va a ser explicada

```java

```

Using the StringBuilder Class
Understanding Equality
Understanding Arrays
Calculating with Math APIs
Working with Dates and Times