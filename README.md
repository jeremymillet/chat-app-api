# chat-app-api

> API backend de messagerie instantanée en Java Spring Boot (WebSocket, JWT, Spring Data JPA)

## 🧩 Présentation

- **Nom du projet** : `chat-app-api`
- **Package principal** : `com.jeremy.chatapp.chat_app_api`
- **Technologies** : Spring Boot, Spring Security, Spring Data JPA, WebSockets STOMP, JWT
- **Build** : Maven (`mvn`, `./mvnw`, `mvnw.cmd`)
- **BDD** : configurable (H2/MySQL/PostgreSQL)

## 📁 Organisation du code

### `src/main/java/com/jeremy/chatapp/chat_app_api`

- `controller/` : REST API (auth, conversations, amis, messages, utilisateurs, images)
- `service/` : logique métier
- `repository/` : interfaces Spring Data JPA
- `entity/` : entités JPA (`User`, `Conversation`, `Message`, `Friend`)
- `dto/` : objets de transfert
- `security/` : JWT, filtre d’authentification, configuration Spring Security
- `config/` : configuration WebSocket et événements

### `src/main/resources`

- `application.properties` (configuration globale)
- `application-local.properties` (variables locales)
- `static/` et `templates/` (si frontend statique intégré)


## ⚙️ Prérequis

- Java 17+
- Maven 3.6+
- Base de données compatible JDBC (H2, MySQL, PostgreSQL)
- Git (optionnel)

---

## 🚀 Installation et démarrage

1. Cloner le dépôt

```bash
git clone <https://github.com/jeremymillet/chat-app-api>
cd chat-app-api/chat-app-api
```

2. Compiler le projet

- Windows
  ```bash
  mvnw.cmd clean package
  ```
- Mac/Linux
  ```bash
  ./mvnw clean package
  ```

3. Démarrer l’application

- En mode développement
  ```bash
  mvnw.cmd spring-boot:run
  # ou
  ./mvnw spring-boot:run
  ```
- En production
  ```bash
  java -jar target/chat-app-api-<version>.jar
  ```

4. Accéder à l’application

- URL : `http://localhost:8080`

---

## 🔧 Configuration

Configurer les propriétés dans `src/main/resources/application.properties` ou `application-local.properties` :

- `spring.datasource.url`
- `spring.datasource.username`
- `spring.datasource.password`
- `spring.jpa.hibernate.ddl-auto` (ex. `update`)
- `jwt.secret-key-access-token`, `jwt.secret-key-refresh-token`, `jwt.expiration-time`
-`cloudinary.cloud-name`,`cloudinary.api-key`,`cloudinary.api-secret`
- `spring.websocket.*`

> Pour la production, utilisez des variables d’environnement au lieu de garder les secrets en clair.

---

## 🔐 Authentification JWT

- `POST /api/auth/register`
- `POST /api/auth/login`
- Token dans en-tête : `Authorization: Bearer <token>`
- Classes clés : `SecureConfig`, `JwtAuthenticationFilter`, `JwtUtil`

---

## 📡 WebSocket

- Configuration dans : `WebSocketConfig`
- Connexion STOMP généralement sur : `/ws` ou chemin configuré
- Topics : `/topic/messages` (ou équivalent selon implémentation)
- Événements écoute : `WebSocketEventListener`

---

## 🛠️ Endpoints principaux

Voici un aperçu (à adapter selon projet) :

- `GET /api/users`
- `GET /api/users/{id}`
- `PUT /api/users/{id}`
- `POST /api/conversations`
- `GET /api/conversations/{id}/messages`
- `POST /api/messages`
- `GET /api/friends`
- `POST /api/friends/request`
- `GET /api/images/{id}`


## 🧹 Bonnes pratiques

- Séparer config dev (`application-local.properties`) / prod (`application.properties`)
- Utiliser `SPRING_DATASOURCE_*`, `JWT_SECRET` en variables d’environnement
- En dev : `spring.jpa.show-sql=true`; en prod : `false`
- Ajouter gestion globale des exceptions (`@ControllerAdvice`)
- Documenter l’API via Swagger/OpenAPI (`springdoc-openapi-ui`)

## 📦 Versioning

- Utiliser versioning sémantique dans `pom.xml`
- Tags recommandés : `v1.0.0`, `v1.1.0` …

---
