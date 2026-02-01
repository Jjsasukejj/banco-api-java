🏦 Banco API – Spring Boot

API REST desarrollada en Java 21 + Spring Boot que implementa un sistema bancario básico, cumpliendo reglas de negocio reales (clientes, cuentas, movimientos y reportes), con enfoque en buen diseño, separación de responsabilidades y preparación para entornos productivos.

🧱 Arquitectura y enfoque

El proyecto está construido siguiendo una arquitectura por capas, evitando exponer entidades directamente y manteniendo el dominio limpio:
	•	Controller → Exposición de endpoints REST (solo DTOs)
	•	Service → Lógica de negocio y reglas bancarias
	•	Repository → Acceso a datos con Spring Data JPA
	•	Domain → Entidades y enums del modelo
	•	DTOs + Mappers → Separación entre API y dominio
	•	Tests → Pruebas unitarias con JUnit y Mockito

Se prioriza:
	•	Borrado lógico donde aplica
	•	Inmutabilidad de movimientos (no se editan ni eliminan)
	•	Uso correcto de enums
	•	Queries claras y explícitas
	•	Código orientado a negocio, no solo CRUD

🛠️ Tecnologías utilizadas
	•	Java 21
	•	Spring Boot 3
	•	Spring Data JPA
	•	Hibernate
	•	SQL Server
	•	Maven
	•	JUnit 5 + Mockito
	•	OpenPDF (exportación de reportes)
	•	Docker (base de datos)

🗄️ Base de datos
	•	Motor: SQL Server
	•	El esquema está versionado en un script SQL: /database/BancoDb.sql
  Este script:
	•	Elimina la base si existe
	•	La crea nuevamente
	•	Crea tablas, índices y relaciones
	•	Está pensado para ejecutarse manual y explícitamente (no en runtime)

🐳 Ejecución con Docker (Base de Datos)

Requisitos
	•	Docker
	•	Docker Compose
	•	Java 21
	•	Maven

1️⃣ Levantar SQL Server

Desde la raíz del proyecto (o donde tengas tu docker-compose.yml): docker-compose up -d
Esto levantará SQL Server en: localhost:1433

2️⃣ Crear la base de datos

Ejecutar manualmente el script: database/BancoDb.sql
Compatible con (SSMS / Azure Data Studio / sqlcmd).

▶️ Ejecutar la aplicación
Una vez creada la base de datos, ejecutar el siguiente comando dentro de nuestro proyecto: ./mvnw spring-boot:run
La API quedará disponible en: http://localhost:8080

🧪 Pruebas unitarias

El proyecto incluye pruebas unitarias para las capas de servicio, enfocadas en:
	•	Reglas de negocio
	•	Casos válidos y de error
	•	Aislamiento mediante mocks

Para ejecutarlas: ./mvnw test

📄 Reportes

La API permite generar reportes de movimientos por cliente y rango de fechas:
	•	Consulta estructurada (JSON)
	•	Exportación a PDF en Base64, lista para ser consumida desde frontend

Esto facilita:
	•	Descarga directa
	•	Visualización web
	•	Integración con apps móviles o web

📬 Colección Postman

Se adjunta una colección Postman (.json) con todos los endpoints disponibles y ejemplos de uso, para facilitar la evaluación y pruebas del proyecto.

👨‍💻 Autor

Proyecto desarrollado como ejercicio técnico, enfocado en demostrar:
	•	Dominio de backend con Java
	•	Buenas prácticas
	•	Pensamiento orientado a negocio
	•	Código mantenible y extensible
