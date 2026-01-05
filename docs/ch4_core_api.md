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

```java
// firma: public String replace(char oldChar, char newChar)
// firma: public String replace(CharSequence target, CharSequence replacement)

System.out.println("abcabc".replace('a', 'A')); // AbcAbc
System.out.println("abcabc".replace("a", "A")); // AbcAbc
```

* El primer ejemplo usa la primera firma del método, pasando parámetros char. 
* El segundo ejemplo usa la segunda firma del método, pasando parámetros String.

### Removing Whitespace

* Estos métodos remueven espacio en blanco del principio y/o fin de un String. 
* Los métodos `strip()` y `trim()` remueven espacios en blanco del principio y fin de un String. 
* En términos del examen, espacios en blanco consiste de espacios junto con los caracteres \t (tab) y \n (newline). 
* Otros caracteres, como \r (carriage return), también se incluyen en lo que se recorta. 
* El método strip() hace todo lo que trim() hace, pero soporta Unicode.
* Adicionalmente, el método `stripLeading()` remueve whitespace del principio del String, pero deja los del final. 
* El método `stripTrailing()` hace lo opuesto. Remueve whitespace del final del String, pero deja los del principio.

```java
// firma: public String strip()
// firma: public String stripLeading()
// firma: public String stripTrailing()
// firma: public String trim()

System.out.println("abc".strip());           // abc
System.out.println("\t a b c\n".strip());    // a b c

String text = " abc\t ";
System.out.println(text.trim().length());         // 3
System.out.println(text.strip().length());        // 3
System.out.println(text.stripLeading().length()); // 5
System.out.println(text.stripTrailing().length()); // 4
```

### Working with Indentation

* El método `indent()` agrega el mismo número de espacios en blanco al principio de cada línea si pasas un número positivo. 
* Si pasas un número negativo, intenta remover ese número de caracteres en espacios en blanco del principio de la línea. 
* Si pasas cero, la indentación no cambiará.

* Sin embargo, indent() también normaliza caracteres de espacios en blanco. ¿Qué significa normalizar whitespace, preguntas? 
* Primero, se agrega un salto de línea al final del string si no está ya ahí.
* Segundo, cualquier salto de línea se convierte al formato \n. Sin importar si tu sistema operativo usa \r\n (Windows) o\n (Mac/Unix), Java estandarizará en \n para ti.

* El método stripIndent() es útil cuando un String fue construido con concatenación en lugar de usar un bloque de texto. 
* Se deshace de todo los espacios en blanco incidentales. 
* Esto significa que todas las líneas no en blanco se desplazan a la izquierda para que el mismo número de caracteres de espacios en blanco se remueva de cada línea y el primer carácter que permanece no sea en blanco. 
* Como indent(), \r\n se convierte en \n. Sin embargo, el método stripIndent() no agrega un salto de línea final si falta.

![ch04_01.png](images/ch04/ch04_01.png)

```java
// firma: public String indent(int numberSpaces)
// firma: public String stripIndent()

10: var block = """
11:      a
12:       b
13:      c""";
14: var concat = " a\n"
15:      + " b\n"
16:      + " c";
17: System.out.println(block.length());      // 6
18: System.out.println(concat.length());     // 9
19: System.out.println(block.indent(1).length());    // 10
20: System.out.println(concat.indent(-1).length());  // 7
21: System.out.println(concat.indent(-4).length());  // 6
22: System.out.println(concat.stripIndent().length()); // 6
```

### Translating Escapes

Cuando escapamos caracteres, usamos un solo backslash. Por ejemplo, \t es un tab. 
Si no queremos este comportamiento, agregamos otro backslash para escapar el backslash, así que \\t es el string literal \t. 
El método `translateEscapes()` toma estos literales y los convierte en el carácter escapado equivalente. 

```java
// firma: public String translateEscapes()

var str = "1\\t2";
System.out.println(str);                      // 1\t2
System.out.println(str.translateEscapes()); // 1   2
```

* La primera línea imprime el string literal \t porque el backslash está escapado. 
* La segunda línea imprime un tab real, ya que tradujimos el escape. 
* Este método puede ser usado para secuencias de escape como \t (tab), \n (new line), \s (space), `"` (double quote) y `'` (single quote.)

### Checking for Empty or Blank Strings

Java proporciona métodos de conveniencia para ver si un String tiene una longitud de cero o contiene solo caracteres whitespace.

```java
// firma: public boolean isEmpty()
// firma: public boolean isBlank()

System.out.println(" ".isEmpty()); // false
System.out.println("".isEmpty()); // true
System.out.println(" ".isBlank()); // true
System.out.println("".isBlank()); // true
```

### Formatting Values

* Hay métodos para formatear valores String usando flags de formateo. 
* Dos de los métodos toman el string de formato como parámetro, y el otro usa una instancia para ese valor. 
* Los parámetros del método se usan para construir un String formateado en una sola llamada al método.

```java
//firma: public static String format(String format, Object args...)
//firma: public static String format(Locale loc, String format, Object args...)
//firma: public String formatted(Object args...)

var name = "Kate";
var orderId = 5;
// All print: Hello Kate, order 5 is ready
System.out.println("Hello "+name+", order "+orderId+" is ready");
System.out.println(String.format("Hello %s, order %d is ready", name, orderId));
System.out.println("Hello %s, order %d is ready".formatted(name, orderId));
```

En las operaciones format() y formatted(), los parámetros se insertan y formatean vía símbolos en el orden en que se proporcionan en el vararg. 

![ch04_02.png](images/ch04/ch04_02.png)

```java
var name = "James";
var score = 90.25;
var total = 100;
System.out.println("%s:%n  Score: %f out of %d".formatted(name, score, total));
```

Esto imprime lo siguiente:

```java
James:
  Score: 90.250000 out of 100
```

* Mezclar tipos de datos puede causar excepciones en tiempo de ejecución. 
* Por ejemplo, lo siguiente lanza una excepción porque un número de punto flotante se usa cuando se espera un valor entero:

`var str = "Food: %d tons".formatted(2.0); //IllegalFormatConversionException`

### Method Chaining

* En el examen, hay una tendencia a meter tanto código como sea posible en un espacio pequeño. 
* Verás código usando una técnica llamada method chaining. Aquí hay un ejemplo:

```java
String result = "AniMaL ".trim().toLowerCase().replace('a', 'A');
System.out.println(result);
```

* Para leer código que usa method chaining, se inicia en la izquierda y evalúa el primer método. 
* Luego llama el siguiente método sobre el valor devuelto del primer método. Continúa hasta que llegues al punto y coma.

## Using the StringBuilder Class

* Un programa pequeño puede crear muchos objetos String muy rápidamente. 
* Por ejemplo, ¿cuántos objetos piensas que esta pieza de código crea?

```java
10: String alpha = "";
11: for(char current = 'a'; current <= 'z'; current++)
12:   alpha += current;
13: System.out.println(alpha);
```

* El String vacío en la línea 10 se instancia, y luego la línea 12 agrega una "a". Sin embargo, como el objeto String es inmutable, un nuevo objeto String se asigna a alpha, y el objeto `""` se vuelve elegible para garbage collection. 
* La próxima vez a través del loop, alpha se asigna un nuevo objeto String, "ab", y el objeto "a" se vuelve elegible para garbage collection. 
* La siguiente iteración asigna alpha a "abc", y el objeto "ab" se vuelve elegible para garbage collection, y así sucesivamente.

* Esta secuencia de eventos continúa, y después de 26 iteraciones a través del loop, a total of 27 objetos son instanciados, la mayoría de los cuales son inmediatamente elegibles para garbage collection.
* Esto es muy ineficiente. Afortunadamente, Java tiene una solución. 
* La clase StringBuilder crea un String sin almacenar todos esos valores String intermedios. 
* A diferencia de la clase String, StringBuilder no es inmutable.

```java
15: StringBuilder alpha = new StringBuilder();
16: for(char current = 'a'; current <= 'z'; current++)
17:   alpha.append(current);
18: System.out.println(alpha);
```

* En la línea 15, se instancia un nuevo objeto StringBuilder. 
* La llamada a append() en la línea 17 agrega un carácter al objeto StringBuilder cada vez a través del loop for, agregando el valor de current al final de alpha. 
* Este código reúsa el mismo StringBuilder sin crear un String intermedio cada vez.

### Mutability and Chaining

* Cuando encadenamos llamadas a métodos String, el resultado era un nuevo String con la respuesta. 
* Encadenar métodos StringBuilder no funciona de esta manera. 
* En su lugar, el StringBuilder cambia su propio estado y devuelve una referencia a sí mismo. 

```java
4: StringBuilder sb = new StringBuilder("start");
5: sb.append("+middle");  // sb = "start+middle"
6: StringBuilder same = sb.append("+end"); // "start+middle+end"
```

* La línea 5 agrega texto al final de sb. 
* También devuelve una referencia a sb, que se ignora. 
* La línea 6 también agrega texto al final de sb y devuelve una referencia a sb. 
* Esta vez la referencia se almacena en same. 
* Esto significa sb y same apuntan al mismo objeto e imprimirían el mismo valor.

### Creating a StringBuilder

Hay tres formas de construir un StringBuilder:

```java
StringBuilder sb1 = new StringBuilder();
StringBuilder sb2 = new StringBuilder("animal");
StringBuilder sb3 = new StringBuilder(10);
```

Estos cuatro métodos funcionan exactamente igual que en la clase String:

```java
var sb = new StringBuilder("animals");
String sub = sb.substring(sb.indexOf("a"), sb.indexOf("al"));
int len = sb.length();
char ch = sb.charAt(6);
System.out.println(sub + " " + len + " " + ch);
```

* La respuesta correcta es anim 7 s. 
* El método indexOf() devuelve 0 y 4, respectivamente. 
* El método substring() devuelve el String comenzando con el índice 0 y terminando justo antes del índice 4.
* El método length() devuelve 7 porque es el número de caracteres en el StringBuilder en lugar de un índice. 
* Finalmente, charAt() devuelve el carácter en el índice 6. 

* Nota que substring() devuelve un String en lugar de un StringBuilder. Es por eso que sb no se cambia. 
* El método substring() es realmente solo un método que consulta sobre el estado del StringBuilder.

### Appending Values

el método `append()` agrega el valor proporcionado al final del StringBuilder.

```java
// firma: public StringBuilder append(String str) 
// este método tiene  unas 10 firmas diferentes para varios tipos de datos primitivos

var sb = new StringBuilder().append(1).append('c');
sb.append("-").append(true);
System.out.println(sb);  // 1c-true
```

### Inserting Data

El método `insert()` agrega caracteres al StringBuilder en el índice solicitado y devuelve una referencia al StringBuilder actual.

```java
// firma: public StringBuilder insert(int offset, String str)
// este método tiene unas 10 firmas diferentes para varios tipos de datos primitivos

3: var sb = new StringBuilder("animals");
4: sb.insert(7, "-");      // sb = animals-
5: sb.insert(0, "-");      // sb = -animals-
6: sb.insert(4, "-");      // sb = -ani-mals-
7: System.out.println(sb);
```







---------------------------------------------------------------------
**Palabra** cuando es una palabra en inglés importante que tiene sentido traducirla, pero no es una palabra reservada

() version en ingles de la palabra anterior

`   `  solo cúando es una línea de código o una palabra reservada que va a ser explicada

```java

```

Understanding Equality
Understanding Arrays
Calculating with Math APIs
Working with Dates and Times