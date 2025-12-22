# I/O

Este capítulo se enfoca en usar APIs de I/O (entrada/salida) y NIO.2 (I/O no bloqueante) para interactuar con archivos y streams de I/O. 
El enfoque preferido para trabajar con archivos y directorios en aplicaciones de software más nuevas es usar NIO.2 en lugar de I/O donde sea posible.

---------------------------------------------------------------------
El texto dice: NIO significa non-blocking input/output API y a veces se refiere como new I/O. El examen cubre NIO versión 2. 
Hubo una versión 1 que cubría canales, pero no está en el examen.
---------------------------------------------------------------------

## Referencing Files and Directories

* Termina con el inicio de un párrafo que dice: Comenzamos este capítulo revisando qué son los archivos y directorios dentro de un sistema de archivos sistema. 
* También presentamos la clase File y la interfaz Path junto con cómo crearlas.

### Conceptualizando el Sistema de Archivos

* Comenzamos con lo básico. Los datos se almacenan en dispositivos de almacenamiento persistente, como discos duros y tarjetas de memoria. 
* Un file dentro del dispositivo de almacenamiento contiene datos. Los archivos se organizan en jerarquías usando directorios. 
* Un directory es una ubicación que puede contener archivos así como otros directorios. 
* Cuando trabajamos con directorios en Java, frecuentemente los tratamos como archivos. 
* De hecho, usamos muchas de las mismas clases e interfaces para operar sobre archivos y directorios. 
* Por ejemplo, un archivo y un directorio pueden ser renombrados con el mismo método Java. 
* Nota que a menudo decimos file para significar file or directory en este capítulo.

* Para interactuar con archivos, necesitamos conectarnos al sistema de archivos. 
* El file system está a cargo de leer y escribir datos dentro de una computadora. 
* Diferentes sistemas operativos usan diferentes sistemas de archivos para gestionar sus datos. 
* Por ejemplo, los sistemas basados en Windows usan un sistema de archivos diferente que los basados en Unix. 
* Para el examen, solo necesitas saber cómo emitir comandos usando las APIs de Java. 
* La JVM se conectará automáticamente al sistema de archivos local, permitiéndote realizar las mismas operaciones a través de múltiples plataformas.

* A continuación, el root directory es el directorio superior en el sistema de archivos, del cual todos los archivos y directorios heredan. 
* En Windows, se denota con una letra de unidad como C:, mientras que en Linux, se denota con una sola barra diagonal, /.

* Un path es una representación de un archivo o directorio dentro de un sistema de archivos. 
* Cada sistema de archivos define su propio carácter separador de ruta que se usa entre entradas de directorio. 
* El valor a la izquierda de un separador es el padre del valor a la derecha del separador. 
* Por ejemplo, el valor de ruta /user/home/zoo.txt significa que el archivo zoo.txt está dentro del directorio home, con el directorio home dentro del directorio user.

---------------------------------------------------------------------
**Separadores de Archivo del Sistema Operativo**
* Diferentes sistemas operativos varían en su formato de nombres de ruta. 
* Por ejemplo, los sistemas basados en Unix usan la barra diagonal, /, para rutas, mientras que los sistemas basados en Windows usan la barra invertida, /, carácter. 
* Dicho esto, muchos lenguajes de programación y sistemas de archivos soportan ambos tipos de barras cuando se escriben declaraciones de ruta. 
* Java ofrece una propiedad del sistema para recuperar el carácter separador local para el entorno actual:
`System.out.print(System.getProperty("file.separator"));`
---------------------------------------------------------------------

Mostramos cómo un directorio y sistema de archivos está organizado de manera jerárquica en Figure 14.1.

![ch14_01_01.png](images/ch14/ch14_01_01.png)

* Este diagrama muestra el directorio raíz, c:, como conteniendo dos directorios, app y zoo, junto con el archivo info.txt. 
* Dentro del directorio app, hay dos carpetas más, animals y employees, junto con el archivo `java.exe`. Finalmente, el directorio animals contiene dos archivos, Bear.java y Bear.class.

* Usamos tanto rutas absolutas como relativas al archivo o directorio dentro del sistema de archivos. 
* La absolute path de un archivo o directorio es la ruta completa desde el directorio raíz al archivo o directorio, incluyendo todos los subdirectorios que contienen el archivo o directorio. 
* Alternativamente, la relative path de un archivo o directorio es la ruta desde el directorio de trabajo actual al archivo o directorio. 
* Por ejemplo, lo siguiente es una ruta absoluta al archivo Bear.java:

`C:\app\animals\Bear.java`

Lo siguiente es una ruta relativa al mismo archivo, asumiendo que el directorio actual del usuario está establecido en `C:\app`:

`animals\Bear.java`

* Determinar si una ruta es relativa o absoluta es dependiente del sistema de archivos. 
* Para coincidir con el examen, adoptamos las siguientes convenciones:












```java

```

---------------------------------------------------------------------
Referencing Files and Directories
Operating on File and Path
Introducing I/O Streams
Reading and Writing Files
Serializing Data
Interacting with Users
Working with Advanced APIs
Review of Key APIs
Summary