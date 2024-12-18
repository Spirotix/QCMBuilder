// ...existing code...
// Nombre total de questions dans le questionnaire
const totalQuestions = 4;

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

	// Question 1 à choix multiple
	//["Bonne réponse 1", "Bonne réponse 2", "Bonne réponse 3"],

	["answer1", "answer3", "answer5"],


	// Question 2 à élimination
	//[["Bonne réponse"],[
	//	[["indice1"], point-perdu],
	//	[["indice2"], point-perdu]]
	//],

	[["answer1"],[ 
		[["answer3"],1.5],
		[["answer4"],4] ] 
	],


	// Question 3 avec association
	//[
	//	["Gauche1","DroiteB"],
	//	["Gauche2","DroiteC"],
	//	["Gauche3","DroiteA"],
	//	["Gauche4","DroiteD"]
	//],

	[
		["1","B"],
		["2","C"],
		["3","A"],
		["4","D"]
	],

	
	// Question 4 à choix unique
	//["Bonne réponse"],

	["answer1"]

];