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
// firma: public char charAt(int index)

var name = "animals";
System.out.println(name.charAt(0)); // a
System.out.println(name.charAt(6)); // s
System.out.println(name.charAt(7)); // exception
```

### Finding an Index

* El método `indexOf()` mira los caracteres en el string y encuentra el primer índice que coincide con el valor deseado. 
* El método indexOf puede trabajar con un carácter individual o un String completo como entrada. 
* También puede comenzar desde una posición solicitada. 
* Recuerda que un char puede ser pasado a un parámetro de tipo int. En el examen, solo verás un char pasado a los parámetros llamados ch.

```java
// firma: public int indexOf(int ch)
// firma: public int indexOf(int ch, int fromIndex)
// firma: public int indexOf(String str)
// firma: public int indexOf(String str, int fromIndex)

var name = "animals";
System.out.println(name.indexOf('a'));     // 0
System.out.println(name.indexOf("al"));    // 4
System.out.println(name.indexOf('a', 4));  // 4
System.out.println(name.indexOf("al", 5)); // -1
```

A diferencia de charAt(), el método indexOf() no lanza una excepción si no puede encontrar una coincidencia, en su lugar devuelve -1

### Getting a Substring

* El método substring() devuelve partes del string. 
* El primer parámetro es el índice con el que comenzar para el string devuelto. 
* Hay un segundo parámetro opcional, que es el índice final en el que quieres detenerte.

```java
// firma: public String substring(int beginIndex)
// firma: public String substring(int beginIndex, int endIndex)

var name = "animals";
System.out.println(name.substring(3));                    // mals
System.out.println(name.substring(name.indexOf('m'))); // mals
System.out.println(name.substring(3, 4));                 // m
System.out.println(name.substring(3, 7));                 // mals
System.out.println(name.substring(3, 3)); // empty string
System.out.println(name.substring(3, 2)); // exception
System.out.println(name.substring(3, 8)); // exception
```

Estos métodos hacen fácil convertir tus datos. Las firmas de los métodos son las siguientes:

```java
//firma: public String toUpperCase()
//firma: public String toLowerCase()

var name = "animals";
System.out.println(name.toUpperCase()); // ANIMALS
System.out.println("Abc123".toLowerCase()); // abc123
```

El método `equals()` verifica si dos objetos String contienen exactamente los mismos caracteres en el mismo orden. 
El método `equalsIgnoreCase()` verifica si dos objetos String contienen los mismos caracteres, ignorando si están en mayúsculas o minúsculas.

```java
// firma: public boolean equals(Object obj)
// firma: public boolean equalsIgnoreCase(String str)

System.out.println("abc".equals("ABC"));               // false
System.out.println("ABC".equals("ABC"));               // true
System.out.println("abc".equalsIgnoreCase("ABC")); // true
```

---------------------------------------------------------------------
* El método `toString()` es llamado cuando intentas imprimir un objeto o concatenar el objeto con un String. 
* Es comúnmente sobrescrito con una versión que imprime una descripción única de la instancia usando sus campos de instancia.

* El método `equals(Object)` es usado para comparar objetos, con la implementación predeterminada simplemente usando el operador ==. 
* Deberías sobrescribir el método equals(Object) cada vez que quieras comparar elementos convenientemente por igualdad, especialmente si esto requiere verificar numerosos campos.

* Cada vez que sobrescribas `equals(Object)`, debes sobrescribir `hashCode()` para ser consistente. 
* Esto significa que para cualesquiera dos objetos, si a.equals(b) es true, entonces a.hashCode()==b.hashCode() también debe ser true. 
* Si no son consistentes, esto podría llevar a datos inválidos y efectos secundarios en colecciones basadas en hash como HashMap y HashSet.
---------------------------------------------------------------------

### Searching for Substrings

* Los métodos startsWith() y endsWith() miran si el valor proporcionado coincide con parte del String. 
* El método contains() no es tan particular; busca coincidencias en cualquier lugar del String.

```java
//firma: public boolean startsWith(String prefix)
//firma: public boolean endsWith(String suffix)
//firma: public boolean contains(CharSequence charSeq)

System.out.println("abc".startsWith("a")); // true
System.out.println("abc".startsWith("A")); // false

System.out.println("abc".endsWith("c")); // true
System.out.println("abc".endsWith("a")); // false

System.out.println("abc".contains("b")); // true
System.out.println("abc".contains("B")); // false
```

### Replacing Values

El método `replace()` hace una búsqueda y reemplazo simple en el string.
















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