
TP1: UDP et Multicast
======================


Realisation d'un  client  de  discussion  qui  utilise  le  protocole  UDP  et  le MultiCasting.

#### AUTEURS: DEROISSART Maxime @deroissart | SASU Daniel @sasu

> **Note:** Toute manipulation(commande) décrite dans ce fichier est effectuée depuis le dossier
> racine du projet.

Arborescence Du Projet
----------------------
```
.
├── pom.xml
├── README.md
└── src
    └── main
        └── java
            └── fil
                └── rsx
                    ├── ex1
                    │   ├── ReceiveUDP.java
                    │   └── SendUDP.java
                    ├── ex2
                    │   ├── MultiReceiveUDP.java
                    │   └── MultiSendUDP.java
                    └── ex3
                        ├── MulticastReceiver.java
                        ├── MulticastSender.java
                        ├── NameGenerator.java
                        └── Testing.java


```

Compilation et exécution du code (exemple):
--------------------------------------------

```
$ mvn compile
$ cd target/classes
$ java fil.rsx.ex3.Testing
hello
cgdchead says : hello
```

Comment "nettoyer" le projet
------------------------------

```
$ mvn clean
```
