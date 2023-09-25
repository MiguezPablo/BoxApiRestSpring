# API Rest para Cargar Archivos

## Descripción

Este proyecto consiste en una API Rest diseñada para cargar archivos y almacenar un registro del mismo en una base de datos. Los archivos no se guardan, sólo se guarda el registro de la subida del mismo con la utilización de una función hash (SHA-256 y SHA-512 del conjunto SHA-2), guardando el resultado de aplicar la función hash en la base de datos.

Una característica interesante de este proyecto es que si un archivo ya se ha subido previamente (es decir, si el hash del archivo ya está presente en la base de datos), el sistema guarda la fecha de la última vez que se cargó el archivo en una variable llamada "lastUpload". Esto permite llevar un seguimiento de cuándo se subió por última vez cada archivo.


## Tecnologías Utilizadas

- Java 17
- PostgreSQL
- Springboot 

## Instalación

```bash
git clone https://github.com/MiguezPablo/BoxApiRestSpring
```

## Abrir el Proyecto con IntelliJ IDEA

1. Inicia IntelliJ IDEA.
2. Haz clic en `File` > `Open`.
3. Navega al directorio donde clonaste el proyecto.
4. Selecciona la carpeta del proyecto y haz clic en `OK`.
5. IntelliJ IDEA abrirá y cargará el proyecto.

Nota: Asegúrate de tener instalado y configurado correctamente el JDK correspondiente en IntelliJ IDEA.

## Configuración de PostgreSQL

Este proyecto utiliza PostgreSQL como base de datos. Sigue estos pasos para configurar PostgreSQL:

1. Asegúrate de tener PostgreSQL instalado en tu sistema. Si no lo tienes, puedes descargarlo desde su pagina oficial.
2. Inicia el servidor de PostgreSQL.
3. Crea una nueva base de datos para el proyecto.
4. Actualiza el archivo `application.properties` en el directorio `src/main/resources` con la URL de la base de datos, el nombre de usuario y la contraseña.

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/nombre_de_tu_base_de_datos
spring.datasource.username=nombre_de_usuario
spring.datasource.password=contraseña
```

## Uso
Para usar esta API, debes enviar una solicitud POST a nuestro endpoint con el archivo que deseas cargar. Debes especificar el algoritmo de hash que deseas usar como parámetro en la URL.

## Pruebas con Postman

Este proyecto incluye una colección de Postman que puedes utilizar para probar la API. Para utilizarla, sigue estos pasos:

1. Abre Postman.
2. Haz clic en el botón "Import" en la parte superior izquierda.
3. Selecciona la opción "File" y luego busca el archivo de la colección de Postman en la carpeta del proyecto en doc/postman.
4. Una vez importada, puedes utilizar la colección para enviar solicitudes a la API.

