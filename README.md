# Contact Portfolio Microservice

Un microservicio robusto y escalable para el manejo de formularios de contacto, diseñado específicamente para portafolios web. Proporciona una API REST para el envío de correos electrónicos con soporte para múltiples proveedores de email y configuración CORS optimizada.

## 🚀 Características

- **API REST** simple y eficiente para formularios de contacto
- **Múltiples proveedores de email** (Resend para producción, SMTP para desarrollo)
- **Configuración CORS** optimizada para frontends modernos
- **Validación robusta** de datos de entrada
- **Perfiles de configuración** para desarrollo y producción
- **Logging estructurado** con SLF4J
- **Despliegue containerizado** con Docker

## 🏗️ Arquitectura

### Estructura del Proyecto

```
src/main/java/dev/portfolio/contact_api/
├── config/
│   └── WebClientConfig.java          # Configuración CORS y WebClient
├── controller/
│   └── ContactController.java        # Controlador REST principal
├── dto/
│   └── ContactRequest.java           # DTO para validación de datos
├── service/
│   ├── MailService.java              # Interfaz del servicio de email
│   └── impl/
│       ├── MailServiceResend.java    # Implementación con Resend API
│       └── MailServiceSmtp.java      # Implementación con SMTP
└── ContactApiApplication.java        # Punto de entrada de la aplicación
```

### Patrones de Diseño

- **Strategy Pattern**: Implementaciones intercambiables de `MailService`
- **Builder Pattern**: DTOs con Lombok para construcción fluida
- **Dependency Injection**: Gestión de dependencias con Spring
- **Profile-based Configuration**: Configuraciones específicas por entorno

## 🛠️ Tecnologías

### Backend
- **Java 21** - Lenguaje de programación
- **Spring Boot 3.x** - Framework principal
- **Spring Web** - API REST
- **Spring Mail** - Envío de correos SMTP
- **WebFlux** - Cliente HTTP reactivo para Resend API
- **Validation API** - Validación de datos de entrada

### Herramientas de Desarrollo
- **Lombok** - Reducción de código boilerplate
- **Maven** - Gestión de dependencias y build
- **Docker** - Containerización
- **JUnit 5** - Testing framework

### Servicios Externos
- **Resend API** - Servicio de email para producción
- **SMTP** - Servidor de correo para desarrollo

## 📋 API Endpoints

### POST `/api/contact`

Envía un formulario de contacto por correo electrónico.

**Request Body:**
```json
{
  "name": "Juan Pérez",
  "email": "juan@ejemplo.com",
  "subject": "Consulta sobre servicios",
  "message": "Hola, me interesa conocer más sobre..."
}
```

**Validaciones:**
- `name`: Requerido, máximo 80 caracteres
- `email`: Requerido, formato válido, máximo 120 caracteres
- `subject`: Requerido, máximo 120 caracteres
- `message`: Requerido, máximo 2000 caracteres

**Response:**
- `204 No Content` - Email enviado exitosamente
- `400 Bad Request` - Datos de entrada inválidos
- `500 Internal Server Error` - Error en el envío del email

## ⚙️ Configuración

### Variables de Entorno

#### Producción (Resend)
```bash
RESEND_API_KEY=tu_api_key_de_resend
APP_CORS_ORIGIN=https://tu-dominio.netlify.app
APP_MAIL_TO=tu-email@ejemplo.com
APP_MAIL_FROM=noreply@tu-dominio.com
```

#### Desarrollo (SMTP)
```bash
SPRING_MAIL_HOST=smtp.gmail.com
SPRING_MAIL_PORT=587
SPRING_MAIL_USERNAME=tu-email@gmail.com
SPRING_MAIL_PASSWORD=tu_app_password
APP_MAIL_TO=tu-email@ejemplo.com
APP_MAIL_FROM=tu-email@gmail.com
```

### Configuración CORS

El microservicio está configurado para permitir peticiones desde:
- `https://lucamonteleone-portfolio.netlify.app` (producción)
- `http://localhost:4200` (desarrollo local)

La configuración incluye:
- Headers CORS apropiados
- Métodos HTTP permitidos (GET, POST, PUT, DELETE, OPTIONS)
- Credenciales habilitadas
- Cache de preflight de 1 hora

## 🚀 Despliegue

### Docker

```bash
# Construir imagen
docker build -t contact-portfolio-microservice .

# Ejecutar contenedor
docker run -p 8080:8080 \
  -e RESEND_API_KEY=tu_api_key \
  -e APP_MAIL_TO=tu-email@ejemplo.com \
  -e APP_MAIL_FROM=noreply@tu-dominio.com \
  contact-portfolio-microservice
```

### Railway

1. Conecta tu repositorio a Railway
2. Configura las variables de entorno
3. Railway detectará automáticamente el `Dockerfile`
4. El despliegue se realizará automáticamente

### Variables de Entorno en Railway

```bash
RESEND_API_KEY=re_xxxxxxxxx
APP_MAIL_TO=tu-email@ejemplo.com
APP_MAIL_FROM=noreply@tu-dominio.com
SPRING_PROFILES_ACTIVE=prod
```

## 🧪 Testing

```bash
# Ejecutar tests
mvn test

# Ejecutar con perfil específico
mvn test -Dspring.profiles.active=dev
```

## 📊 Monitoreo y Logs

El microservicio incluye logging estructurado que registra:
- Peticiones entrantes
- Errores de validación
- Errores de envío de email
- Configuración de CORS

## 🔧 Desarrollo Local

```bash
# Clonar repositorio
git clone <repository-url>
cd ContactPortfolioMicroservice

# Ejecutar en modo desarrollo
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# O con variables de entorno
export SPRING_MAIL_HOST=smtp.gmail.com
export SPRING_MAIL_PORT=587
export SPRING_MAIL_USERNAME=tu-email@gmail.com
export SPRING_MAIL_PASSWORD=tu_app_password
export APP_MAIL_TO=tu-email@ejemplo.com
export APP_MAIL_FROM=tu-email@gmail.com
mvn spring-boot:run
```

## 🐛 Solución de Problemas

### Error de CORS
Si experimentas errores de CORS, verifica que:
1. El dominio del frontend esté en la configuración CORS
2. Las peticiones incluyan el header `Content-Type: application/json`
3. El microservicio esté ejecutándose con el perfil correcto

### Error de Email
Para problemas de envío de email:
1. Verifica las variables de entorno
2. Confirma que la API key de Resend sea válida
3. Revisa los logs para errores específicos

## 📝 Changelog

### v1.0.0
- Implementación inicial del microservicio
- Soporte para Resend API y SMTP
- Configuración CORS optimizada
- Validación robusta de datos
- Containerización con Docker

## 🤝 Contribución

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.

## 👨‍💻 Autor

**Luca Monteleone**
- Portfolio: [lucamonteleone-portfolio.netlify.app](https://lucamonteleone-portfolio.netlify.app)
- LinkedIn: [linkedin.com/in/luca-monteleone](https://linkedin.com/in/luca-monteleone)

---

*Desarrollado con ❤️ para portafolios modernos*