# Usar una imagen base de OpenJDK 17
FROM openjdk:17-jdk-slim

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el archivo JAR de la aplicación al contenedor
COPY build/libs/users-0.0.1-SNAPSHOT.jar /app/mi-aplicacion.jar

# Exponer el puerto en el que la aplicación escucha (por defecto 8080 en Spring Boot)
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app/mi-aplicacion.jar"]
