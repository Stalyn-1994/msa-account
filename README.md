# Microservicio de Accounts

Este microservicio gestiona la información de las cuentas y movimientos asociados a los clientes. Incluye la funcionalidad de desactivar cuentas de clientes en respuesta a mensajes Kafka provenientes del microservicio de Customer.

## Funcionalidades Principales

1. **CRUD de Cuentas**: Crear, leer, actualizar y eliminar registros de cuentas.
2. **Registro de Movimientos**: Registrar y actualizar movimientos financieros de las cuentas.
3. **Desactivación de Cuentas**: Desactivar cuentas de clientes cuando se recibe un mensaje Kafka indicando que el cliente ha sido desactivado.
4. **Generación de Reportes**: Genera un reporte de todos los movimientos de las cuentas de un cliente dado un rango de fechas.

## Endpoints

- **/cuentas**:
    - **GET**: Obtiene la lista de todas las cuentas segun codigo de cliente.
    - **POST**: Crea una nueva cuenta.
    - **PUT**: Actualiza la información de una cuenta existente.
    - **PATCH**: Edita la información de una cuenta existente.
    - **DELETE**: Elimina una cuenta.

- **/movimientos**:
    - **GET**: Obtiene la lista de todos los movimientos.
    - **POST**: Registra un nuevo movimiento.

- **/reportes**:
    - **GET**: Genera un reporte de movimientos para un cliente específico en un rango de fechas. Este reporte incluye detalles de las cuentas y los movimientos realizados.

## Comunicación Asincrónica

El microservicio de Accounts escucha mensajes de Kafka para recibir notificaciones del microservicio de Customer sobre la desactivación de clientes. Al recibir estos mensajes, el servicio desactiva todas las cuentas asociadas al cliente desactivado.

## Despliegue

El microservicio cuenta con un archivo `Dockerfile` que permite crear una imagen de Docker para desplegar el microservicio. Para ello, se debe ejecutar los siguientes comandos en la raíz del proyecto:

docker build -t mi-aplicacion:latest .

docker run -d -p 8080:8080 mi-aplicacion:latest

## Requisitos

- Java 17 o superior
- Spring Boot 3.2.x o superior
- Apache Kafka
- Docker
