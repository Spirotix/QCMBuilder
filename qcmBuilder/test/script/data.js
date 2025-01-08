// ...existing code...
// Nombre total de questions dans le questionnaire
const totalQuestions = 8;

// Index de la question actuelle
let currentQuestion = 0;

// Identifiant de l'intervalle de temps
let intervalId = 0;

// Nombre d'indices utilisés
let usedHint  = 0;

// Nombre de points perdus en utilisant des indices
let hintPoint = 0;

// Tableau des éléments associés
let associateItems = [];

// Points accumulés par l'élève
let point = 0;

// Somme des points de chaque question
let totalPoint = 0;

// Tableau pour stocker les réponses de chaques questions
const correctAnswers = [

// Exemple question à choix multiple
//["Bonne réponse 1", "Bonne réponse 2", "Bonne réponse 3"],

// Exemple question à élimination
//[["Bonne réponse"],[
//	[["indice1"], point-perdu],
//	[["indice2"], point-perdu]]
//],

// Exemple question à association
//[
//	["Gauche1","Sa Réponse"],
//	["Gauche2","Sa Réponse"],
//	["Gauche3","Sa Réponse"],
//	["Gauche4","Sa Réponse"]
//],

// Exemple question à choix unique
//["Bonne réponse 1"],


// Question 1 de type Association
	[
		["3","5"], 
		["4","1"], 
		["6","0"], 
		["7","2"], 
	],

	// Question 2 de type Association
	[
		["0","2"], 
		["3","1"], 
	],

	// Question 3 de type Elimination
	[["answer1"],[
	]
	],

	// Question 4 de type QCM
	["answer1"],

	// Question 5 de type QCM
	["answer2"],

	// Question 6 de type QCM
	["answer1"],

	// Question 7 de type Elimination
	[["answer2"],[
		["answer1",2.0]]
	],

	// Question 8 de type QCM
	["answer1"]
];