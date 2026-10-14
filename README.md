# AlquilaYA: Sistema de Gestión de Alquiler de Vehículos (API REST)

## Descripción del Proyecto

AlquilaYA es una plataforma integral construida con Spring Boot diseñada para la administración completa de un negocio de alquiler de vehículos. Su arquitectura ofrece una **API RESTful** para gestionar todas las operaciones, complementada con una interfaz web integrada. El sistema se divide en los siguientes módulos principales:

*   **Módulo de Clientes (`/api/clientes`):** Administra el registro de los conductores autorizados. Permite crear nuevos clientes, actualizar su información de contacto, consultar registros y eliminarlos del sistema.
*   **Módulo de Vehículos (`/api/vehiculos`):** Encargado de la gestión de la flota. Permite la adición de nuevas unidades, actualización de datos (como la tarifa diaria), asignación a una oficina específica y el control de su estado actual (Disponible, Alquilado, Mantenimiento).
*   **Módulo de Oficinas (`/api/oficinas`):** Controla las sucursales físicas donde se ubican los vehículos. Permite registrar, editar y consultar oficinas.
*   **Módulo de Alquileres (`/api/alquileres`):** El núcleo del negocio. Vincula clientes con vehículos disponibles. Calcula automáticamente el precio total basado en los días de uso, cambia el estado del vehículo a "Alquilado" y gestiona la devolución, liberando el vehículo nuevamente a "Disponible".
*   **Módulo de Mantenimiento (`/api/mantenimientos`):** Registra el historial de reparaciones. Al enviar un vehículo al taller, cambia su estado a "Mantenimiento" (impidiendo su alquiler) y lo libera al finalizar el servicio.

## Tecnologías Implementadas

*   **Lenguaje Base:** Java
*   **Framework Principal:** Spring Boot
*   **Mapeo Objeto-Relacional (ORM):** Spring Data JPA / Hibernate
*   **Base de Datos:** (Se requiere configuración en `application.properties`, por ejemplo, MySQL)
*   **Frontend (Vistas Integradas):** HTML, Bootstrap 5, Font Awesome

## Guía de Instalación y Ejecución

1.  **Preparación del Entorno:** Verifica que tu equipo cuente con Java (JDK), Maven y un IDE compatible. Asegúrate de tener tu servidor de base de datos en ejecución.
2.  **Base de Datos:** Accede a tu cliente de base de datos y genera una base de datos vacía.
3.  **Obtención del Código:** Descarga o clona el repositorio del proyecto AlquilaYA en tu espacio de trabajo local.
4.  **Importación:** Abre el proyecto en tu IDE como una aplicación basada en Maven.
5.  **Credenciales:** Ubica el archivo `application.properties` (en la ruta `src/main/resources`) e ingresa tus datos de acceso local a la base de datos.
6.  **Arranque del Servidor:** Ejecuta el archivo principal `AlquilayaApplication.java`. La aplicación iniciará el servidor embebido (generalmente en el puerto 8080) y ejecutará los scripts de creación de tablas.
7.  **Acceso a la Interfaz Web:** Abre un navegador y dirígete a `http://localhost:8080` para visualizar la interfaz gráfica.

## Variables de Entorno (Configuración)

La conexión a la base de datos se gestiona en el archivo `application.properties`. Asegúrate de configurar los parámetros según tu entorno local:

```properties
spring.application.name=alquilaya
server.port=8080
spring.datasource.url=jdbc:mysql://localhost:3306/nombre_tu_base_datos
spring.datasource.username=root
spring.datasource.password=TU_PASSWORD_AQUI
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

```

## Rutas y Endpoints

### API REST

**Gestión de Oficinas (`/api/oficinas`)**

* `GET /api/oficinas`: Consulta todas las oficinas.
* `GET /api/oficinas/{id}`: Búsqueda de oficina por ID.
* `GET /api/oficinas/ciudad/{ciudad}`: Filtra oficinas por ciudad.
* `POST /api/oficinas`: Crea una nueva oficina.
* `PUT /api/oficinas/{id}`: Modifica una oficina existente.
* `DELETE /api/oficinas/{id}`: Elimina una oficina.

**Gestión de Clientes (`/api/clientes`)**

* `GET /api/clientes`: Consulta todos los clientes.
* `GET /api/clientes/{id}`: Búsqueda por ID.
* `GET /api/clientes/email/{email}`: Búsqueda por correo electrónico.
* `GET /api/clientes/licencia/{licencia}`: Búsqueda por licencia de conducir.
* `POST /api/clientes`: Registra un nuevo cliente.
* `PUT /api/clientes/{id}`: Actualiza un cliente.
* `DELETE /api/clientes/{id}`: Elimina un cliente.

**Gestión de Vehículos (`/api/vehiculos`)**

* `GET /api/vehiculos`: Consulta toda la flota (permite parámetro `?estado=Disponible`).
* `GET /api/vehiculos/{id}`: Búsqueda por ID.
* `POST /api/vehiculos`: Registra un nuevo vehículo.
* `PUT /api/vehiculos/{id}`: Modifica un vehículo.
* `PATCH /api/vehiculos/{id}/estado`: Actualiza solo el estado del vehículo.
* `DELETE /api/vehiculos/{id}`: Elimina un vehículo.

**Gestión de Alquileres (`/api/alquileres`)**

* `GET /api/alquileres`: Historial de alquileres.
* `GET /api/alquileres/{id}`: Búsqueda por ID.
* `POST /api/alquileres`: Registra un nuevo contrato de alquiler.
* `POST /api/alquileres/{id}/devolver`: Registra la devolución del vehículo y completa el contrato.
* `DELETE /api/alquileres/{id}`: Elimina un contrato (solo si está Completado).

**Gestión de Mantenimiento (`/api/mantenimientos`)**

* `GET /api/mantenimientos`: Consulta el historial de taller.
* `GET /api/mantenimientos/{id}`: Búsqueda por ID.
* `POST /api/mantenimientos`: Envía un vehículo a taller.
* `POST /api/mantenimientos/{id}/finalizar`: Finaliza el mantenimiento y libera el vehículo.
* `DELETE /api/mantenimientos/{id}`: Elimina un registro de mantenimiento.

---

## Muestras de Peticiones y Respuestas (Payloads)

### A. Registro de una Oficina (POST `/api/oficinas`)

**Formato de envío (JSON):**

```json
{
  "nombre": "Sucursal Centro",
  "direccion": "Av. Principal 123",
  "ciudad": "Ciudad de México",
  "region": "CDMX"
}

```

**Estructura de respuesta exitosa (Ejemplo):**

```json
{
  "idOficina": 1,
  "nombre": "Sucursal Centro",
  "direccion": "Av. Principal 123",
  "ciudad": "Ciudad de México",
  "region": "CDMX"
}

```

### B. Registro de un Vehículo (POST `/api/vehiculos`)

**Formato de envío (JSON):**

```json
{
  "marca": "Toyota",
  "modelo": "Corolla",
  "anio": 2023,
  "matricula": "ABC-123",
  "tarifaDiaria": 500.00,
  "estado": "Disponible",
  "oficina": {
    "idOficina": 1
  }
}

```

**Estructura de respuesta exitosa (Ejemplo):**

```json
{
  "idVehiculo": 1,
  "marca": "Toyota",
  "modelo": "Corolla",
  "anio": 2023,
  "matricula": "ABC-123",
  "tarifaDiaria": 500.00,
  "estado": "Disponible",
  "oficina": {
    "idOficina": 1,
    "nombre": "Sucursal Centro",
    "direccion": "Av. Principal 123",
    "ciudad": "Ciudad de México",
    "region": "CDMX"
  }
}

```

### C. Creación de un Alquiler (POST `/api/alquileres`)

**Formato de envío (JSON):**

```json
{
  "cliente": {
    "idCliente": 1
  },
  "vehiculo": {
    "idVehiculo": 1
  },
  "fechaAlquiler": "2023-11-01",
  "fechaDevolucion": "2023-11-05"
}

```

**Estructura de respuesta exitosa (Ejemplo):**

```json
{
  "idAlquiler": 1,
  "vehiculo": {
    "idVehiculo": 1,
    "marca": "Toyota",
    "modelo": "Corolla",
    "anio": 2023,
    "matricula": "ABC-123",
    "tarifaDiaria": 500.00,
    "estado": "Alquilado"
  },
  "cliente": {
    "idCliente": 1,
    "nombre": "Juan",
    "apellido": "Pérez",
    "email": "juan@email.com",
    "licenciaConducir": "LIC123",
    "telefono": "5551234567"
  },
  "fechaAlquiler": "2023-11-01",
  "fechaDevolucion": "2023-11-05",
  "precioTotal": 2000.00,
  "estado": "Activo"
}

```

```

```
