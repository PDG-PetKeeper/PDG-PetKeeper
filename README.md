# PetKeeper:  une app pour votre animal de compagnie

---

## Mise en place et informations générales

---

### 1. Mettre en place un environnement adapté pour developper sur android:

    - Installer soit Android Studio, soit IntelliJ IDEA avec le plugin Android SDK ainsi que gradle
    - Télécharger le SDK Android 13 (API 33) ou le SDK Android 14 (API 34) (moins de problèmes en mode debug)
    - Télécharger OpendJDK 19 comme JVM
    - (Optionnel) avoir un telephone android pour tester l'application. 

### 2. Charger le projet avec gradle

### 3. Architecture:
    - Le projet est découpé en 3 modules:
        - petkeeperCloud: contient les modules googles pour le chat
        - src: le code de l'application android
        - landing_page: le code source de la landing page
        
    - L'application android utilise les services:
        - de firebaseAuth pour l'authentification avec un compte google.
        - de firebaseStorage pour stocker les images des animaux
        - de firebaseFireStore pour stocker les annonces, les messages entre utilisateurs et les utilisateurs

    - La navigation est effectuée à l'aide de nested graphes et de navigation controlers et components
    - Les pages sont des éléments composables et utilisent composeMaterial3 pour le design

        
---

### 4. Workflow - gitflow:

    - Pour travailler sur une fonctionnalité, créer une branche à partir de la branche `dev` et la nommer `feature/<nom de la fonctionnalité>`
    - Une fois la fonctionnalité terminée, créer une pull request vers la branche `dev` et assigner un reviewer
    - Une fois la pull request validée, la fonctionnalité est mergée dans la branche `dev`
    - Une fois que la branche `dev` contient toutes les fonctionnalités prévues pour la prochaine version, créer une pull request vers la branche `main` et assigner un reviewer
    - Une fois la pull request validée et le build test passé, la branche `main` est mergée dans la branche `dev` et une nouvelle version est taggée


(Durant la dernière semaine de projet, on a surtout push sur dev et pas vraiment utilisé les feature branches)

## travail en cours:

- [ ] 1. Finaliser le chat -> depuis la search page en cliquant sur l'annonce, on aimerait pouvoir launch un chat avec le poster d'annonce
- [ ] 2. Faire la page pour afficher correctement les annonces en previews