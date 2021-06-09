# Sommaire
1. [Tablut](#Tablut)
2. [Récupérer le dépôt](#Récupérer-le-dépôt)
3. [Version testées de Java](#Version-testées-de-Java)
4. [Compilation](#Compilation)
5. [Bibliothèques externes utilisées](#Bibliothèques-externes-utilisées)
6. [Membres du groupe](#Membres-du-groupe)

# Tablut
## Informations générales
Ce projet a été réalisé dans le cadre des études en L3 Informatique. Il a pour but de permettre de jouer au jeu du Tablut depuis un ordinateur. 
## Règles
Voici les règles du Tablut : https://www.regledujeu.fr/tablut-gwezboell/
## Information sur l'IHM
L'IHM a beaucoup été travaillé pour permettre une compréhension rapide du jeu et une expérience utilisateur très intuitive que ce soit pour des personnes qui ont l'habitude de jouer à des jeux sur ordinateur ou pour des personnes novices dans le domaine. Notre jeu est donc adapté à tous les types de personnes quelque soit leur âge.
## Information sur l'IA
Nous avons implémenté la possibilité de jouer contre une IA avec plusieurs niveaux de difficulté définis selon nos propres tests.
## Avancée du projet
Ce projet a été réalisé en 1 mois par un groupe de 6 étudiants. Le projet est dans un état final mais il se peut qu'il y est encore quelques bugs. Tout ce qui avait été prévu a été réalisé.

# Récupérer le dépôt
Ouvrez un invité de commande là où vous voulez télécharger le dépôt et exécutez la commande suivante (il faut au préalable avoir installé git) :
```
git clone https://github.com/EloGamerr/projet-prog-l3.git
cd projet-prog-l3
```

# Version testées de Java
Les versions testées de Java sont les versions 8 et 11 de Java mais les autres versions devraient tout aussi bien marcher. Il se peut que les versions inférieures à Java 8 ne puissent pas compiler.

# Compilation
Ouvrez un invité de commandes dans le dossier racine du projet (là où se trouve le README.md) puis exécutez les commandes suivantes :
## Build (obtenir le .jar exécutable)
```
./gradlew build
```

### Windows
Vous pouvez aussi plus simplement cliquer directement sur le fichier gradlew.bat

### Linux/MAC
Vous pouvez aussi plus simplement cliquer directement sur le fichier gradlew

### Où trouver l'exécutable ?
Après avoir effectué la commande build, vous pouvez retrouver l'exécutable dans le chemin build/libs

## Clean (supprime le dossier build généré précédemment)
```
./gradlew clean
```

## Javadoc
```
./gradlew javadoc
```
Après avoir effectué la commande javadoc, vous pouvez retrouver la javadoc dans le chemin build/docs/javadoc/index.html

## Gradle
Pour plus d'informations sur les commandes disponibles (./gradlew \<votre-commande\>), veuillez vous référez à la doc de gradle : https://docs.gradle.org/current/userguide/userguide.html

# Bibliothèques externes utilisées
Chaque bibliothèques utilisées sont écrites dans le fichier build.gradle du projet dans la partie dependencies, chaque dépendance est automatiquement ajoutée dans le .jar après le build. Liste des dépendances :
* json-rpc pour la sauvegarde des parties
* commons-io pour la lecture et l'écriture des fichiers de configuration

Si pour une raison quelconque vous ne pouvez/voulez pas utiliser gradle pour gérer les dépendances, vous pouvez retrouver les 2 bibliothèques utilisées dans les dossiers json et commons-io


# Membres du groupe
* [Dorian Thivolle](https://github.com/NoxFly)
* [Dorian Lorek](https://github.com/EloGamerr)
* [Lilian Russo](https://github.com/Leer0r)
* [Clément Corbillé](https://github.com/corbillc)
* [Arthur Ducros](https://github.com/SpyciBear)

## IHM (partie programmation) :
* Dorian Thivolle

## IA :
* Lilian Russo
* Dorian Lorek

## Fonctionnalités du jeu :
* Clément Corbillé
* Dorian Lorek

## Fonctionnalités de l'application dans son ensemble :
* Lilian Russo
* Clément Corbillé

## Réflexion IHM (partie maquette) :
* Dorian Thivolle
* Arthur Ducros

## Documentation, aide :
* Arthur Ducros