\# AlquilaYA - Sistema de Gestión de Alquiler de Vehículos (API REST)



\## Descripción del Proyecto



AlquilaYA es una plataforma integral construida con Spring Boot diseñada para la administración completa de un negocio de alquiler de vehículos\[cite: 6, 29]. Su arquitectura ofrece una \*\*API RESTful\*\* para gestionar todas las operaciones, complementada con una interfaz web integrada. El sistema se divide en los siguientes módulos principales:



\*   \*\*Módulo de Clientes (`/api/clientes`):\*\* Administra el registro de los conductores autorizados. Permite crear nuevos clientes, actualizar su información de contacto, consultar registros y eliminarlos del sistema\[cite: 11, 23, 29].

\*   \*\*Módulo de Vehículos (`/api/vehiculos`):\*\* Encargado de la gestión de la flota. Permite la adición de nuevas unidades, actualización de datos (como la tarifa diaria), asignación a una oficina específica y el control de su estado actual (Disponible, Alquilado, Mantenimiento)\[cite: 14, 18, 25, 29].

\*   \*\*Módulo de Oficinas (`/api/oficinas`):\*\* Controla las sucursales físicas donde se ubican los vehículos. Permite registrar, editar y consultar oficinas\[cite: 6, 7, 27, 29].

\*   \*\*Módulo de Alquileres (`/api/alquileres`):\*\* El núcleo del negocio. Vincula clientes con vehículos disponibles. Calcula automáticamente el precio total basado en los días de uso, cambia el estado del vehículo a "Alquilado" y gestiona la devolución, liberando el vehículo nuevamente a "Disponible"\[cite: 8, 10, 15, 29].

\*   \*\*Módulo de Mantenimiento (`/api/mantenimientos`):\*\* Registra el historial de reparaciones. Al enviar un vehículo al taller, cambia su estado a "Mantenimiento" (impidiendo su alquiler) y lo libera al finalizar el servicio\[cite: 8, 10, 16, 26, 29].



\## Tecnologías Implementadas



\*   \*\*Lenguaje Base:\*\* Java

\*   \*\*Framework Principal:\*\* Spring Boot\[cite: 15]

\*   \*\*Mapeo Objeto-Relacional (ORM):\*\* Spring Data JPA / Hibernate\[cite: 23, 24, 25, 26, 27]

\*   \*\*Base de Datos:\*\* (Se requiere configuración en `application.properties`, por ejemplo, MySQL)\[cite: 28, 31]

\*   \*\*Frontend (Vistas Integradas):\*\* HTML, Bootstrap 5, Font Awesome\[cite: 1, 2, 3, 4, 5, 16]



\## Guía de Instalación y Ejecución



1\.  \*\*Preparación del Entorno:\*\* Verifica que tu equipo cuente con Java (JDK), Maven y un IDE compatible. Asegúrate de tener tu servidor de base de datos en ejecución.

2\.  \*\*Base de Datos:\*\* Accede a tu cliente de base de datos y genera una base de datos vacía.

3\.  \*\*Obtención del Código:\*\* Descarga o clona el repositorio del proyecto AlquilaYA en tu espacio de trabajo local\[cite: 28, 31].

4\.  \*\*Importación:\*\* Abre el proyecto en tu IDE como una aplicación basada en Maven.

5\.  \*\*Credenciales:\*\* Ubica el archivo `application.properties` (en la ruta `src/main/resources`) e ingresa tus datos de acceso local a la base de datos\[cite: 28].

6\.  \*\*Arranque del Servidor:\*\* Ejecuta el archivo principal `AlquilayaApplication.java`\[cite: 15]. La aplicación iniciará el servidor embebido (generalmente en el puerto 8080) y ejecutará los scripts de creación de tablas.

7\.  \*\*Acceso a la Interfaz Web:\*\* Abre un navegador y dirígete a `http://localhost:8080` para visualizar la interfaz gráfica\[cite: 3, 16].



\## Variables de Entorno (Configuración)



La conexión a la base de datos se gestiona en el archivo `application.properties`\[cite: 28]. Asegúrate de configurar los parámetros según tu entorno local:



```properties

spring.application.name=alquilaya

server.port=8080

spring.datasource.url=jdbc:mysql://localhost:3306/nombre\_tu\_base\_datos

spring.datasource.username=root

spring.datasource.password=TU\_PASSWORD\_AQUI

spring.jpa.hibernate.ddl-auto=update

spring.jpa.show-sql=true

