# QCMBuilder

QCMBuilder est une application permettant aux enseignants de l'IUT du Havre de créer des questionnaires d'auto-évaluation pour les étudiants. L'objectif principal est de favoriser l'apprentissage actif grâce à divers types de questions adaptées aux besoins pédagogiques.

---

## Fonctionnalités principales

### Application Concepteur

- **Gestion des questions :**
  - Création, modification et suppression de questions.
  - Types de questions pris en charge :
    - QCM (réponse unique ou multiple).
    - Association d'éléments.
    - Questions avec élimination de propositions.
  - Gestion des métadonnées des questions : notions, niveaux de difficulté, temps de réponse, points attribués.
  - Ajout de fichiers supplémentaires (PDF, images, vidéos, etc.) liés aux questions.

- **Génération d'évaluations :**
  - Sélection des notions, niveaux de difficulté et nombre de questions souhaitées.
  - Option d'évaluation chronométrée ou non chronométrée.
  - Génération de fichiers HTML/CSS/JS prêts à être partagés avec les étudiants.

### Application Etudiant

- **Prise en main intuitive :**
  - Navigation fluide entre les questions.
  - Feedback immédiat après chaque question (explications incluses).

- **Modes d'évaluation :**
  - Chronométré : progression linéaire, compte à rebours.
  - Non chronométré : navigation libre entre les questions.

- **Page de résultats :**
  - Score global et récapitulatif de l'évaluation.

---

## Technologies Utilisées

- **Back-End :** Java (application de création).
- **Front-End :** HTML, CSS, JavaScript (interface étudiant).
- **Base de données :**
  - Fichiers RTF pour les questions et réponses.
  - Fichiers CSV pour structurer les ressources et notions.
- **Compatibilité :** Edge, Chrome, Firefox, dispositifs desktop et mobiles (responsive design).

---

## Installation

1. Clonez le répertoire :
   ```bash
   git clone https://github.com/<votre-utilisateur>/QCMBuilder.git
   ```

2. Assurez-vous que vous avez Java installé pour exécuter l'application de création.

3. Ouvrez le dossier `evaluation_output` pour accéder aux fichiers générés.

4. Utilisez un navigateur compatible pour exécuter les évaluations (HTML/CSS/JS).

---

## Utilisation

### Pour les enseignants

1. Lancez l'application Java en utilisant le run.sh ou run.bat
2. utiliser les boutons de l'ihm pour sois créer/modifier des questions, créer des notions/ressource ou générer le questionnaire
   ![menu d'accueil](https://github.com/user-attachments/assets/501c2bdb-43dc-4cbc-9193-d46f1ab6b815)
3. Choisissez les options souhaitez pour créer votre question
   ![Création question](https://github.com/user-attachments/assets/947fb6e1-e06b-449a-b986-8cc711f9021b)
4. Générez une évaluation en sélectionnant les notions et niveaux souhaités.
  ![image](https://github.com/user-attachments/assets/43bb378b-0232-4451-92e0-050180a32cab)
5. Partagez les fichiers HTML générés avec les étudiants.

### Pour les étudiants

1. Ouvrez la fiche d'évaluation partagée par votre enseignant.
2. Répondez aux questions en suivant les consignes.
3. Consultez le score final et les explications fournies.

---

## Auteurs

**Groupe 2** :

- Killian Demeillers
- Fanch Even
- Rafael Debein
- Thao Dujardin
- Théo Harel
