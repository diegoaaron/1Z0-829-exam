# Módulos

## Introducing Modules

* Al escribir código para el examen, generalmente ves clases pequeñas. Después de todo, las preguntas del examen tienen que caber en una sola pantalla.
* Cuando trabajas en programas reales, son mucho más grandes. Un proyecto real consistirá de cientos o miles de clases agrupadas en paquetes. 
* Estos paquetes se agrupan en archivos Java (JAR). Un JAR es un archivo ZIP con algo de información extra, y la extensión es .jar.
* Además del código escrito por tu equipo, la mayoría de las aplicaciones también usan código escrito por otros. 
* Open source es software con el código suministrado y a menudo es gratuito de usar. 
* Java tiene una vibrante comunidad de software de código abierto (OSS), y esas bibliotecas también se suministran como archivos JAR. 
* Por ejemplo, hay bibliotecas para leer archivos, conectarse a una base de datos, y mucho más.
* Algunos proyectos de código abierto incluso dependen de funcionalidad en otros proyectos de código abierto. 
* Por ejemplo, Spring es un framework comúnmente usado, y JUnit es una biblioteca de pruebas comúnmente usada. 
* Para usar cualquiera de ellas, necesitas asegurarte de tener versiones compatibles de todos los JARs relevantes disponibles en tiempo de ejecución. 
* Esta compleja cadena de dependencias y versiones mínimas es a menudo referida por la comunidad como JAR hell. 
* Hell es una excelente manera de describir la versión incorrecta de una clase siendo cargada o incluso una `ClassNotFoundException` en tiempo de ejecución.

* El Java Platform Module System (JPMS) agrupa código a un nivel más alto. 
* El propósito principal de un módulo es proporcionar grupos de paquetes relacionados que ofrecen a los desarrolladores un conjunto particular de funcionalidad. 
* Es como un archivo JAR, excepto que un desarrollador elige qué paquetes son accesibles fuera del módulo. 
* Veamos qué son los módulos y qué problemas están diseñados para resolver.

El Sistema de Módulos de la Plataforma Java incluye lo siguiente:
* Un formato para archivos JAR de módulos
* Particionamiento del JDK en módulos
* Opciones adicionales de línea de comandos para herramientas Java

### Explorando un Módulo

En Capítulo 1, "Building Blocks," tuvimos una pequeña aplicación Zoo. Tenía solo una clase y simplemente imprimía una cosa. 
Ahora imagina que teníamos todo un equipo de programadores y estábamos automatizando las operaciones del zoológico. 
Muchas cosas necesitan ser codificadas, incluyendo las interacciones con los animales, visitantes, el sitio web público, y alcance comunitario.
Un module es un grupo de uno o más paquetes más un archivo especial llamado `module-info.java`. 
Los contenidos de este archivo son la `module declaration`.
Decidimos enfocarnos en las interacciones con los animales en nuestro ejemplo. 
El zoológico completo podría fácilmente tener una docena de módulos. 
En Figure 12.1, nota que hay flechas entre muchos de los módulos. 
Estas representan dependencies, donde un módulo depende del código en otro. 
El personal necesita alimentar a los animales para mantener sus trabajos. La línea desde `zoo.staff` a `zoo.animal.feeding` muestra que el primero depende del último.

![ch12_01_01.png](images/ch12/ch12_01_01.png)

* Ahora profundicemos en uno de estos módulos. Figure 12.2 muestra qué hay dentro del módulo `zoo.animal.talks`. 
* Hay tres paquetes con dos clases cada uno. (Es un zoológico pequeño.) 
* También hay un archivo extraño llamado `module-info.java`. Este archivo es requerido para estar dentro de todos los módulos. 
* Explicamos esto con más detalle más adelante en el capítulo.

![ch12_01_02.png](images/ch12/ch12_01_02.png)

### Benefits of Modules

Los módulos parecen otra capa de cosas que necesitas conocer para programar. 
Aunque usar módulos es opcional, es importante entender los problemas que están diseñados para resolver:

* Better access control: Además de los niveles de control de acceso cubiertos en Chapter 5, "Methods," puedes tener paquetes que solo son accesibles a otros paquetes en el módulo.
* Clearer dependency management: Dado que los módulos especifican en qué dependen, Java puede quejarse sobre un JAR faltante al iniciar el programa en lugar de cuando es accedido por primera vez en tiempo de ejecución.
* Custom Java builds: Puedes crear un Java runtime que tiene solo las partes del JDK que tu programa necesita en lugar del completo de más de 150 MB.
* Improved security: Dado que puedes omitir partes del JDK de tu construcción personalizada, no tienes que preocuparte sobre vulnerabilidades descubiertas en una parte que no usas.
* Improved performance: Otro beneficio de un paquete Java más pequeño es un tiempo de inicio mejorado y un requerimiento de memoria menor.
* Unique package enforcement: Dado que los módulos especifican paquetes expuestos, Java puede asegurar que cada paquete proviene de solo un módulo y evitar confusión sobre qué está siendo ejecutado.

## Creating and Running a Modular Program

* En esta sección, creamos, construimos, y ejecutamos el módulo zoo.animal.feeding. 
* Elegimos este para comenzar porque todos los otros módulos dependen de él. Figure 12.3 muestra el diseño de este módulo. 
* Además del archivo `module-info.java`, tiene un paquete con una clase dentro.

![ch12_01_03.png](images/ch12/ch12_01_03.png)






















```java

```

---------------------------------------------------------------------

Updating Our Example for Multiple Modules
Diving into the Module Declaration
Creating a Service
Discovering Modules
Comparing Types of Modules
Migrating an Application
Summary