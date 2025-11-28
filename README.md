
# Internal Tools API

## Technologies
- Langage: Java 21
- Framework: Spring boot 4.x (Spring MVC, Spring data JPA)
- Base de données: PostgreSQL
- Port API: 8080

## Quick Start

1. lancer la bdd via docker: `docker-compose --profile postgres up -d`
2. installer les dépendance: `mvn clean install`
3. demarer le projet
4. API disponible sur http://localhost:8080
5. Documentation: http://localhost:8080/swagger-ui/index.html (probleme de compatibilité)

## Configuration
- Variables d'environnement: voir application.properties pour celles du projet, et env.example pour celles de docker


## Architecture
- feature-based: chaque entité a son propre dossier regroupant :
    - Entity: la classe JPA représentant la table
    - EntityController: les endpoints REST pour cette entité
    - EntityRepository: l’accès aux données
    - DTOs: objets pour transfert de données
    - Filter: critères de recherche ou filtrage

- config: configurations globales (ex. Swagger)
- utils: classes utilitaires réutilisables (UtilDate, utilMapper, UtilMetrics,...)
- resource/db/migration: scripts SQL versionnés par Flyway pour gérer les migrations de manière claire et maintenable
- exceptions: gestion centralisée des erreurs personnalisées
- constantes: enums et constantes partagées dans tout le projet
- .env / application.properties :
    - .env: variables Docker
    - application.properties: configuration de l’application 

