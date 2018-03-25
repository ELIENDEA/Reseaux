TP3: Interrogation Serveur DNS
=====================

#### AUTEURS: DEROISSART Maxime @deroissart | SASU Daniel @sasu

> **Note:** Toute manipulation(commande) décrite dans ce fichier est effectuée depuis le dossier
> racine du projet.

Contenu
=======

* [Présentation du TP et de ses objectifs](#présentation-du-tp-et-de-ses-objectifs)
* [Arborescence Du Projet](#arborescence-du-projet)
* [Lancer le programme](#lancer-le-programme)



Présentation du TP et de ses objectifs
======================================

   - Créer une requête DNS
   - L'envoyer à un serveur DNS
   - Récupérer la trame
   - Afficher les alias ainsi que les adresses IP de type v4 et v6


Arborescence Du Projet
----------------------
```
$ .
+-- pom.xml
+-- README.md
+-- src
    +-- main
    ¦   +-- java
    ¦       +-- fil
    ¦           +-- rsx
    ¦               +-- DNSsimple.java
    ¦               +-- ReceiveUDP.java
    ¦               +-- ReponsesTP3.java
    ¦               +-- SendUDP.java
    +-- test
        +-- java
            +-- fil
                +-- rsx


```


Lancer le programme
------------------------------

Il y a deux façon possibles de lancer le programme: 

1- Lancer Eclipse
```
   Aller dans File/Import/Maven/Existing Maven Projects
   Charger le projet
   Aller dans Run/Run configurations
   Effectuer un clic droit sur Java Applications et en créer un nouveau
   Dans projet devrait être écrit tp3-interrogation-serveur-DNS_sasu_deroissart
   Dans main class être écrit fil.rsx.ReponsesTP3
   Aller dans Arguments
   Entrer le nom de domaine dans le cadre Program arguments:
   Cliquer sur run
```

2- Lancer le terminal
   Entrer les commandes suivantes:

```
$ mvn package
$ java -classpath target/classes fil.rsx.ReponsesTP3 (Nom de domaine du site)
$ mvn clean

```
