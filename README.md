# OpenClassrooms - Software Academy AXA - parcours DAJ
# Projet 3 - Guess game


## Introduction

Le projet 'Guess Game' est une application java en mode console permettant à l'utilisateur de jouer au jeu du Plus/Moins et au Mastermind, selon différents modes de jeu contre l'ordinateur.

## Prérequis

Le projet nécessite le jdk 1.8.

## Lancement du programme

Deux modes de lancement sont proposés:
1. lancement en ligne de commande à partir d'un jar fourni avec le projet. C'est la méthode la plus rapide, directement utilisable après avoir cloné le projet GitHub.
2. lancement en ligne de commande à partir de Maven, en effectuant une compilation du projet.

Chacun de ces deux modes est détaillé dans la suite.

### Lancement à partir d'un jar

Prérequis: jdk 1.8.
La procédure est la suivante:
#### Etape 1
Ouvrir un terminal et se placer dans le répertoire _target_.

#### Etape 2
Exécuter la commande 

                java -jar hello-1.0-SNAPSHOT-jar-with-dependencies.jar  

Deux paramètres (optionnels) peuvent être ajoutés à la ligne de commande:
1. `-DdevMode=true` : permet l'affichage de la séquence secrète à chaque tour de jeu
2. `-Dspring.profiles.active=[sam|knuth]` : permet de choisir la stratégie de décodage utilisée dans le jeu du Mastermind. Si `knuth` est indiqué, c'est l'algorithm de Knuth qui est utilisé (méthode la plus efficace basée sur la résolution par force brute). Si `sam` est indiqué, c'est la stratégie de l'auteur qui est utilisée, moins performante que celle de Knuth mais réalisable par un humain, à la différence de l'autre.

### Lancement après compilation avec Maven

Depuis le répertoire racine du projet, tapez la commande:

                mvn clean compile exec:java
 
 Les deux paramètres optionnes décrits plus haut sont également utilisables dans ce mode de lancement.

Par exemple, on activera le mode _développeur_ en utilisant la commande:

                mvn clean compile exec:java -DdevMode=true

### Mode d'emploi du jeu

Au démarrage, l'utilisateur doit choisir le type de jeu. Deux options lui sont proposées: jeu du Plus/Moins et Mastermind. Une fois choisi le jeu, l'utilisateur doit choisir parmi 4 modes de jeu:
1. Vous devez trouver la combinaison secrète de l'ordinateur
2. L'ordinateur doit trouver votre combinaison secrète
3. L'ordinateur joue contre lui-même (pour les contemplatifs... ou pour tester le comportement de l'ordinateur)
4. Deux jeux sont lancés simultanément, l'un dans lequel le joueur tente de trouver la combinaison de l'ordinateur,
 l'autre dans lequel les rôles sont inversés. Ce mode permet de mettre en compétition la stratégie de recherche du joueur et celle de l'ordinateur.

#### Règles du jeu du Plus/Moins
Dans ce jeu, le joueur appelé _codeur_ choisit une combinaison secrète constituée d'une sequence de chiffres.
L'autre joueur, le _décodeur_, doit la deviner en soumettant une combinaison à laquelle le codeur répond 
en indiquant, pour chaque chiffre, si le chiffre à la même position dans la combinaison secrète est _supérieur_, _inférieur_ 
ou _égal_ à la proposition faite.
Le décodeur doit trouver la bonne combinaison avant le nombre limite d'essais.

### Règles du Mastermind
Dans ce jeu, le codeur génère une séquence constituée de lettres (sans doublons) que le décodeur doit trouver. Le résultat d'un essai indique le nombre de symbols bien placés et le nombre de symbols présents dans la solution mais mal placés.

### Paramètres du jeu

Le jeu est paramétrable via deux fichiers:
1. `config.properties` qui permet de modifier les caractéristiques des jeux (nombre d'essai, taille de la séquence, symbols possibles).
2. `log4j.xml` qui permet de modifier le niveau de trace et le collecteur des messages (la console ou un fichier).

Selon le mode de lancement du jeu choisi (lancement du jar ou utilisation de Maven), on choisira 
soit de modifier ces fichiers depuis le répertoire `src/main/ressources`, soit de modifier ceux présents 
à la racine du jar `target/hello-1.0-SNAPSHOT-jar-with-dependencies.jar`.


## Documentation

Le jeu est livré avec :
1. la javadoc dont le point d'entrée est le fichier `target/site/apidocs/index.html`
2. une documentation technique au format PDF située dans le répertoire `doc`
