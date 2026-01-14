#  Cajero Automático de Consola

Este proyecto es un simulador de cajero automático (ATM) de línea de comandos escrito en Java. Permite a los usuarios interactuar con una base de datos de un banco para realizar operaciones bancarias básicas. La aplicación utiliza Maven para la gestión de dependencias y se conecta a una base de datos MySQL para almacenar y recuperar datos.

## Características

- **Creación de Bancos**: Crea dinámicamente nuevos esquemas de bases de datos para diferentes bancos.
- **Gestión de Monedas**: Inicializa el cajero con un número predefinido de monedas y billetes.
- **Listado de Bancos**: Muestra una lista de los bancos disponibles y permite al usuario seleccionar con cuál desea interactuar.
- **Consulta de Saldo**: Muestra la cantidad actual de cada tipo de moneda/billete en el cajero del banco seleccionado.

## Prerrequisitos

Para ejecutar este proyecto, necesitarás tener instalado lo siguiente:

- **Java Development Kit (JDK)**: Versión 8 o superior.
- **Apache Maven**: Para compilar el proyecto y gestionar las dependencias.
- **MySQL**: Como sistema de gestión de bases de datos.

## Instalación y Configuración

Sigue estos pasos para poner en marcha el proyecto:

1. **Clona el repositorio**:
   ```bash
   git clone <URL-del-repositorio>
   cd <nombre-del-repositorio>
   ```

2. **Configura la conexión a la base de datos**:
   - Abre el archivo `src/main/java/Monedas/banco.java`.
   - Modifica las siguientes líneas con tus credenciales de MySQL:
     ```java
     public static String url = "jdbc:mysql://localhost:3306/";
     public static String usuario = "tu_usuario";
     public static String contraseña = "tu_contraseña";
     ```

3. **Compila el proyecto**:
   - Desde la raíz del directorio del proyecto, ejecuta el siguiente comando para compilar el código y descargar las dependencias:
     ```bash
     mvn compile
     ```

## Uso

Una vez que hayas compilado el proyecto, puedes ejecutar la aplicación con el siguiente comando:

```bash
mvn exec:java -Dexec.mainClass="Monedas.banco"
```

Al iniciar, la aplicación creará un banco llamado "BBVA" por defecto y mostrará un menú con las siguientes opciones:

- **Listar**: Muestra la cantidad de cada moneda/billete en el cajero.
- **Realizar compra**: *Funcionalidad no implementada.*
- **Introducir dinero**: *Funcionalidad no implementada.*
- **Transferir fondos**: *Funcionalidad no implementada.*
- **Cambiar BBDD**: Permite cambiar a otro banco existente.
- **Salir**: Termina la aplicación.

## Estructura del Proyecto

- `pom.xml`: Define las dependencias del proyecto, como el conector de MySQL.
- `src/main/java/`: Contiene el código fuente de la aplicación.
  - `Monedas/banco.java`: Es la clase principal que contiene la lógica de la aplicación, incluyendo la conexión a la base de datos y las operaciones del cajero.
  - `Monedas/Moneda.java`: Una clase de datos para representar las monedas.
  - `menu.java`: Una clase de utilidad para mostrar el menú de la aplicación.
- `target/`: Contiene los archivos compilados del proyecto.

## Futuras Mejoras

- **Implementar las funcionalidades pendientes**: Completar las opciones de "Realizar compra", "Introducir dinero" y "Transferir fondos".
- **Refactorizar el código**:
  - **POO**: Crear una clase `Banco` para encapsular la lógica de negocio y una clase `Cajero` para gestionar las monedas.
  - **Evitar código duplicado**: Mover las clases `menu.java` a una única ubicación.
  - **Paquetes**: Organizar las clases en paquetes más coherentes (por ejemplo, `com.banco.main`, `com.banco.db`, `com.banco.ui`).
- **Seguridad**: Eliminar las credenciales de la base de datos del código fuente y utilizar un archivo de configuración o variables de entorno.
- **Gestión de errores**: Mejorar el manejo de excepciones para proporcionar mensajes más claros al usuario.
- **Pruebas unitarias**: Añadir pruebas para asegurar que la lógica de negocio funcione como se espera.
- **Interfaz de usuario**: Mejorar la interfaz de línea de comandos para que sea más intuitiva.
