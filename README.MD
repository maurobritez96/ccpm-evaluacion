# API RESTful de registro de usuarios

Evaluación técnica CCPM — Java. API que crea y registra usuarios. Todos los endpoints aceptan y devuelven únicamente JSON, incluidos los errores (`{"mensaje": "..."}`).

**Stack:** Java 25 · Spring Boot 3.5.11 · Spring Data JPA (Hibernate) · H2 en memoria · Maven · JWT (jjwt) · Swagger/OpenAPI (springdoc).

## Requisitos

- JDK 25
- Maven 3.9 o superior

```bash
java -version   # debe indicar 25
mvn -version
```

## Compilar, probar y ejecutar

```bash
# compilar y correr los tests
mvn clean verify

# ejecutar la aplicación (puerto 8080)
mvn spring-boot:run
```

- Swagger UI: http://localhost:8080/swagger-ui.html


## Endpoint

`POST /api/users` — `Content-Type: application/json`

```bash
curl -i -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Juan Rodriguez",
    "email": "juan@rodriguez.org",
    "password": "hunter2",
    "phones": [ { "number": "1234567", "citycode": "1", "contrycode": "57" } ]
  }'
```

Se conserva el nombre de campo `contrycode` tal como figura en el enunciado.

Respuesta `201 Created`:

```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "created": "2026-10-01T10:30:00",
  "modified": "2026-10-01T10:30:00",
  "last_login": "2026-10-01T10:30:00",
  "token": "<jwt>",
  "isactive": true,
  "name": "Juan Rodriguez",
  "email": "juan@rodriguez.org",
  "phones": [ { "number": "1234567", "citycode": "1", "contrycode": "57" } ]
}
```

### Códigos de estado

| Código | Escenario | Cuerpo |
|---|---|---|
| 201 | Usuario creado | Datos del usuario + `id`, `created`, `modified`, `last_login`, `token`, `isactive` |
| 400 | Correo inválido, contraseña inválida, campos obligatorios faltantes, JSON malformado | `{"mensaje": "..."}` |
| 409 | Correo ya registrado | `{"mensaje": "El correo ya registrado"}` |
| 404 / 405 / 415 | Ruta inexistente / método no permitido / `Content-Type` distinto de JSON | `{"mensaje": "..."}` |
| 500 | Error inesperado | `{"mensaje": "Error interno del servidor"}` |

## Reglas de validación

- **Correo:** expresión regular (`UserRequest.EMAIL_REGEX`), formato `aaaaaaa@dominio.cl`. Se normaliza a minúsculas, por lo que `Juan@X.org` y `juan@x.org` se consideran el mismo correo.
- **Contraseña:** expresión regular **configurable** en `app.password.regex`.
  Por defecto: entre 6 y 72 caracteres ASCII imprimibles (sin espacios), con al menos una letra y un número (acepta la contraseña `hunter2` del ejemplo).
  Se puede cambiar sin recompilar:

  ```bash
  APP_PASSWORD_REGEX='^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).{8,}$' mvn spring-boot:run
  ```

- **Campos obligatorios:** `name`, `email`, `password`. `phones` es opcional; si viene, cada teléfono requiere `number`, `citycode` y `contrycode`.
- La contraseña se guarda con hash BCrypt, nunca en texto plano, y no se devuelve en la respuesta.

## Token

JWT firmado con HS256, que se persiste junto con el usuario. Claims: `sub` (id del usuario), `email`, `jti`, `iat`, `exp`.

| Propiedad | Variable de entorno | Descripción |
|---|---|---|
| `app.jwt.secret` | `JWT_SECRET` | Secreto de firma, mínimo 32 bytes. **Cambiarlo en cualquier entorno real.** |
| `app.jwt.expiration-minutes` | — | Vigencia del token (60 por defecto). |

## Base de datos

H2 en memoria. El script de creación está en [`src/main/resources/db/schema.sql`](src/main/resources/db/schema.sql) y se ejecuta al iniciar (`spring.sql.init.mode=always`); Hibernate no modifica el esquema (`ddl-auto=none`). Tablas: `users` (con restricción `UNIQUE` sobre `email`) y `phones`.

Los datos se pierden al detener la aplicación.


## Estructura

```
src/main/java/com/ccpm/users
├── config/        SwaggerConfig
├── controller/    UserController
├── dto/           UserRequestDto, UserResponseDto, PhoneDto
├── entity/        User, Phone
├── exception/     excepciones de negocio y GlobalExceptionHandler
├── repository/    UserRepository
└── service/       UserService,
    └── impl/       UserServiceImpl, 
└── security/      JwtService, SecurityConfig 
src/main/resources
├── application.yaml
└── data.sql
```
