# Alten-Ecommerce (backend - Spring Boot)

## Prérequis

Le projet est développé en Java 17 avec le framework Spring Boot 2.6.2, 
en suivant une architecture hexagonale (Clean Architecture).

Il requiert une base de données **Postgresql 14** pour stocker les données.

Pour le développement, l'IDE IntelliJ est préconisé.

## Installation

Dans IntelliJ, importez le projet en tant que **projet Maven**.

Vérifiez que le projet est bien défini en Java 17 :

* Ouvrez les détails du projet : dans le menu ``File > Project Structure``
* Dans ``Project settings`` :
    * Onglet ``Project`` :
        * SDK : ``corretto-17`` (ou autre JDK 17)
        * Language level : ``17 - Local variable syntax for lambda parameters``
    * Onglet ``Modules``
        * Language level : ``17 - Local variable syntax for lambda parameters``

## Exécution

### Exécuter via maven

Le projet peut être exécuté via deux profils maven différents :

* ``mvn clean spring-boot:run`` : utilisation du profil global
* ``mvn clean spring-boot:run -Pazure`` : ajout des spécificités de l'infrastructure Azure (Keyvault par exemple)

### Exécuter via Docker

Le projet peut-être exécuté via Docker en suivant les étapes:

* ``mvn clean package -Dmaven.test.skip=true``
* ``docker-compose build``
* ``docker-compose up -d``
* ``docker-compose down``

## Swagger (documentation API)

La documentation de l'API est générée via Swagger et est accessible ici :

=> http://localhost:8080/swagger-ui/index.html
