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
   git clone https://github.com/ThaoDujardin/Equipe2_QCMBuilder.git
   ```

2. Assurez-vous que vous avez Java installé pour exécuter l'application de création.

3. Ouvrez le dossier `evaluation_output` pour accéder aux fichiers générés.

4. Utilisez un navigateur compatible pour exécuter les évaluations (HTML/CSS/JS).

---

## Utilisation

### Pour les enseignants

1. Lancez l'application Java en utilisant le run.sh ou run.bat
2. utiliser les boutons de l'ihm pour sois créer/modifier des questions, créer des notions/ressource ou générer le questionnaire
   
   ![menu](https://github.com/user-attachments/assets/f160d5f0-5f4c-4739-b31e-8a0718523323)
4. Choisissez les options souhaitez pour créer votre question
   ![Crée question](https://github.com/user-attachments/assets/b300f040-b55e-475c-86dc-196e333cfe7a)
5. selon le type de question la fenêtre sera différente :

![QCU](https://github.com/user-attachments/assets/42241c81-5eb1-422f-a997-2cd080a50113)![QCM](https://github.com/user-attachments/assets/cea77947-7e82-4917-bd5e-8fba2784db34)![Asso](https://github.com/user-attachments/assets/e298fc80-abbb-4da3-ad10-22f60e009b45)![Elim](https://github.com/user-attachments/assets/18f1b304-b592-45a9-b847-e1b43aea8e8c)

6. Générez une évaluation en sélectionnant les notions et niveaux souhaités.
![Généré questionnaire](https://github.com/user-attachments/assets/a18cb860-076c-4b2b-98e0-1a87bb80148d)
7. Partagez les fichiers HTML générés avec les étudiants.

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
