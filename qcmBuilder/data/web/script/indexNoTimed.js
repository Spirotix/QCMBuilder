// Tableau pour stocker si les questions sont répondues
let answeredQuestions = Array(totalQuestions).fill(false);

// Tableau pour stocker les réponses des questions déjà répondues
let answeredQuestionsResponse = Array(totalQuestions).fill([]);

// Tableau pour stocker les points des questions déjà répondues
let pointAnsweredQuestions = Array(totalQuestions).fill(0);

// Charger la question actuelle depuis l'attribut value du body
document.addEventListener("DOMContentLoaded", function ()
{
	if (document.querySelector('.chrono-data'))
	{
		// Récupérer les données du chrono depuis l'URL
		const chronoDataElement = document.querySelector(".chrono-data");
		chronoDataElement.textContent = "Non";
	}

	// Récupérer la question actuelle depuis l'attribut value du body
	const bodyValue = document.body.getAttribute('value');
	let pCurrentQuestion = parseInt(bodyValue, 10);
	currentQuestion = pCurrentQuestion;

	// Récupérer les points depuis l'URL
	const urlParams = new URLSearchParams(window.location.search);

	// Récupérer les données depuis l'URL
	answeredQuestions         = JSON.parse(urlParams.get('answeredQuestions'        )) || answeredQuestions;
	answeredQuestionsResponse = JSON.parse(urlParams.get('answeredQuestionsResponse')) || answeredQuestionsResponse;
	pointAnsweredQuestions    = JSON.parse(urlParams.get('pointAnsweredQuestions'   )) || pointAnsweredQuestions;

	point             = parseFloat(urlParams.get('points'           )) || 0;
	totalPoint        = parseFloat(urlParams.get('totalPoints'      )) || 0;
	nbQuestionRepondu = parseFloat(urlParams.get('nbQuestionRepondu')) || 0;

	if(document.querySelector('.question'))
	{
		// on fait marché la barre de progression
		const progressBars = document.querySelectorAll(".progress-bar");

		progressBars.forEach(progressBar => {
			const progressFill = progressBar.querySelector(".progress-bar-fill");

			if (totalQuestions && currentQuestion >= 0) {
				
				const widthPercentage = (currentQuestion / totalQuestions) * 100;
				progressFill.style.width = `${widthPercentage}%`;
			}
		});


		// si on est sur la première question on cache le bouton précédent
		if(currentQuestion === 1)
		{
			const previousButton = document.querySelector('.previous-button');
			previousButton.style.display = 'none';
		}


		// si la question a déjà été répondu on cache le bouton valider et on affiche le bouton de feedback 
		if(answeredQuestions[currentQuestion-1])
		{
			const nextButton = document.querySelector('.validate-button');
			nextButton.style.display = 'none';
			const feedbackButton = document.querySelector(".feedback-button");
			feedbackButton.style.display = 'block';
		}
		else // si la question n'a pas été répondu on cache le bouton de feedback
		{
			const feedbackButton = document.querySelector(".feedback-button");
			feedbackButton.style.display = 'none';
		}

		// si on est sur la dernière question on change le texte du bouton suivant en terminer
		if (currentQuestion === totalQuestions)
		{
			const nextButton = document.querySelector('.next-button');
			nextButton.textContent = "Terminer";
		}

		// on cache le countdown car on est pas en mode chrono
		const countdown = document.querySelector('.countdown');
		countdown.style.display = 'none';
	}

	// Afficher les scores sur la page de fin
	if (document.querySelector(".score-data"))
	{
		document.querySelector(".score-data").textContent = `${point} / ${totalPoint}`;
	}

	// Commencer le questionnaire
	if (document.querySelector(".start-button"))
	{
		document.querySelector(".start-button").addEventListener('click', function()
		{
			currentQuestion = 1;
			window.location.href = `./pages/question${currentQuestion}.html?points=${point}&totalPoints=${totalPoint}&nbQuestionRepondu=${nbQuestionRepondu}&answeredQuestions=${JSON.stringify(answeredQuestions)}&answeredQuestionsResponse=${JSON.stringify(answeredQuestionsResponse)}&pointAnsweredQuestions=${JSON.stringify(pointAnsweredQuestions)}`;
		});
	}

	// Passer aux questions suivantes
	if (document.querySelector(".next-button"))
	{
		document.querySelector(".next-button").addEventListener('click', function()
		{
			// Si on est à la dernière question et que toutes les questions ont été répondues
			if(currentQuestion >= totalQuestions && nbQuestionRepondu >= totalQuestions)
			{
				// Aller à la page de fin
				currentQuestion = 0;
				window.location.href = `../fin.html?points=${point}&totalPoints=${totalPoint}&nbQuestionRepondu=${nbQuestionRepondu}}&answeredQuestions=${JSON.stringify(answeredQuestions)}&answeredQuestionsResponse=${JSON.stringify(answeredQuestionsResponse)}&pointAnsweredQuestions=${JSON.stringify(pointAnsweredQuestions)}`;
			}
			else if (currentQuestion < totalQuestions ) // Si on est pas à la dernière question
			{
				// Passer à la question suivante
				currentQuestion++;
				window.location.href = `./question${currentQuestion}.html?points=${point}&totalPoints=${totalPoint}&nbQuestionRepondu=${nbQuestionRepondu}}&answeredQuestions=${JSON.stringify(answeredQuestions)}&answeredQuestionsResponse=${JSON.stringify(answeredQuestionsResponse)}&pointAnsweredQuestions=${JSON.stringify(pointAnsweredQuestions)}`;
			}
		});
	}

	// Revenir à la question précédente
	if (document.querySelector(".previous-button"))
		{
		document.querySelector(".previous-button").addEventListener('click', function()
		{
			if (currentQuestion > 1) // si on est pas à la première question
			{
				// Revenir à la question précédente
				currentQuestion--;
				window.location.href = `./question${currentQuestion}.html?points=${point}&totalPoints=${totalPoint}&nbQuestionRepondu=${nbQuestionRepondu}}&answeredQuestions=${JSON.stringify(answeredQuestions)}&answeredQuestionsResponse=${JSON.stringify(answeredQuestionsResponse)}&pointAnsweredQuestions=${JSON.stringify(pointAnsweredQuestions)}`;
			} 
			else // si on est à la première question
			{
				// Revenir à la page d'accueil
				window.location.href = '../index.html';
			}
		});
	}

	// Valider la question actuelle
	if (document.querySelector(".validate-button"))
	{
		document.querySelector(".validate-button").addEventListener('click', function() { validateQuestion(); });
	}

	// Recommencer le questionnaire
	if (document.querySelector(".restart-button"))
	{
		document.querySelector(".restart-button").addEventListener('click', function()
		{
			currentQuestion      = 0;            // Réinitialiser l'index de la question
			point                = 0;            // Réinitialiser les points accumulés
			totalPoint           = 0;            // Réinitialiser la somme des points
			window.location.href = 'index.html'; // Revenir à la page d'accueil
		});
	}

	// On récupère le bouton de supprimer les lignes quand on est sur une question d'association
	if(document.querySelector('.delete-line-button'))
	{
		document.querySelector('.delete-line-button').addEventListener('click', function() { deleteAllLines(); });
	}

	// On récupère le bouton d'affichage de l'indice
	if(document.querySelector('.hint-button'))
	{
		document.querySelector('.hint-button').addEventListener('click', function() { showHint(); });
	}


	// Gestion des questions d'association
	if (document.querySelector('.association-question'))
	{
		const associationItems = document.querySelectorAll('.association-item');
		let tempSelectItems = [];
		
		// Fonction pour gérer les clics sur les éléments d'association
		function handleAssociationItemClick(event)
		{
			const item = event.currentTarget;

			// Vérifier si l'élément est déjà sélectionné
			const alreadySelectedIndex = tempSelectItems.indexOf(item);
			if (alreadySelectedIndex !== -1)
			{
				// Si l'élément est déjà sélectionné, le désélectionner
				item.classList.remove('selected');
				tempSelectItems.splice(alreadySelectedIndex, 1);
				return;
			}

			// Vérifier si le premier élément appartient à la colonne .left-column
			if (tempSelectItems.length === 0)
			{
				const parent = item.closest('.association-column');
				if (!parent.classList.contains('left-column'))
				{
					alert("Vous devez commencer par un élément de la colonne de gauche.");
					return;
				}
				// Ajouter la classe 'selected' pour l'effet "enfoncé"
				item.classList.add('selected');
			}

			// Vérifier si l'élément appartient à la même colonne
			if (tempSelectItems.length === 1)
			{
				const parent1 = tempSelectItems[0].closest('.association-column');
				const parent2 = item.closest('.association-column');
				if (parent1 === parent2)
				{
					alert("Vous ne pouvez pas relier deux éléments de la même colonne.");
					return;
				}
			}

			// Ajouter l'élément cliqué au tableau tempSelectItems
			tempSelectItems.push(item);

			// Si deux éléments sont sélectionnés, les stocker et dessiner un trait
			if (tempSelectItems.length === 2)
			{
				const startId = tempSelectItems[0].getAttribute('data-id');
				const endId = tempSelectItems[1].getAttribute('data-id');
				const alreadyLinked = associateItems.some(pair => pair[0] === startId || pair[1] === endId);

				if (alreadyLinked)
				{
					alert("Chaque élément ne peut être lié qu'une seule fois.");
				}
				else
				{
					// Ajouter un nouveau couple
					associateItems.push([startId, endId]);

					// Dessiner un trait entre les éléments associés
					drawLine(tempSelectItems[0], tempSelectItems[1]);
				}

				// Réinitialiser le tableau temporaire et retirer la classe 'selected'
				tempSelectItems[0].classList.remove('selected');
				tempSelectItems = [];
			}
		}

		// Ajouter un seul eventListener à chaque élément
		associationItems.forEach((item) =>
		{
			item.removeEventListener('click', handleAssociationItemClick);
			item.addEventListener('click', handleAssociationItemClick);
		});

		// Redessiner les lignes lors du redimensionnement de la fenêtre
		window.addEventListener('resize', function()
		{
			document.querySelectorAll('.line').forEach(line => line.remove());
			associateItems.forEach(pair =>
			{
				const startElement = document.querySelector(`.association-item[data-id="${pair[0]}"]`);
				const endElement = document.querySelector(`.association-item[data-id="${pair[1]}"]`);
				drawLine(startElement, endElement);
			});
		});
	}

	// Fonction pour supprimer les lignes et réinitialiser les éléments associés
	function deleteAllLines()
	{
		document.querySelectorAll('.line').forEach(line => line.remove());

		associateItems = [];

		document.querySelectorAll('.association-item').forEach(item => item.classList.remove('selected'));
	}

	// Affichage des réponses si la question a déjà été répondu
	// Gestion des questions à choix multiples
	if(document.querySelector('.question-multiple') && answeredQuestions[currentQuestion-1])
	{
		const selectedAnswers = answeredQuestionsResponse[currentQuestion-1];
		selectedAnswers.forEach((answer) =>
		{
			// Cocher les réponses déjà sélectionnées
			const element = document.querySelector(`#${answer}`);
			element.checked = true;
		});
		const reponses = document.querySelector('.answers');

		// Désactiver les réponses
		reponses.querySelectorAll('input').forEach((input) => { input.disabled = true; });

		// Afficher les points obtenus dans le feedback
		const feedbackPoint = document.querySelector('.feedback-point');
		feedbackPoint.textContent = `${pointAnsweredQuestions[currentQuestion-1]} points`;
	}

	// Gestion des questions à choix unique
	if(document.querySelector('.question-unique') && answeredQuestions[currentQuestion-1])
	{
		if(answeredQuestionsResponse[currentQuestion-1].length > 0)
		{
			// Cocher la réponse déjà sélectionnée
			const selectedAnswer = answeredQuestionsResponse[currentQuestion-1];
			const element        = document.querySelector(`#${selectedAnswer}`);
			element.checked = true;
		}

		// Désactiver les réponses
		const reponses = document.querySelector('.answers');
		reponses.querySelectorAll('input').forEach((input) =>
		{
			input.disabled = true;
		});

		// Afficher les points obtenus dans le feedback
		const feedbackPoint = document.querySelector('.feedback-point');
		feedbackPoint.textContent = `${pointAnsweredQuestions[currentQuestion-1]} points`;
	}

	// Gestions des questions à élimination
	if(document.querySelector('.eliminate-question') && answeredQuestions[currentQuestion-1])
	{
		if(answeredQuestionsResponse[currentQuestion-1].length > 0) {
			// Cocher la réponse déjà sélectionnée
			const selectedAnswer = answeredQuestionsResponse[currentQuestion-1];
			const element        = document.querySelector(`#${selectedAnswer}`);
			element.checked = true;
		}

		// Désactiver les réponses
		const reponses = document.querySelector('.answers');
		reponses.querySelectorAll('input').forEach((input) => { input.disabled = true; });

		// Désactiver le bouton d'indice
		const hint = document.querySelector(".hint");
		hint.style.display = 'none';

		// Afficher les points obtenus dans le feedback
		const feedbackPoint = document.querySelector('.feedback-point');
		feedbackPoint.textContent = `${pointAnsweredQuestions[currentQuestion-1]} points`;
	}

	// Gestion des questions d'association
	if(document.querySelector('.association-question') && answeredQuestions[currentQuestion-1])
	{
		associateItems = answeredQuestionsResponse[currentQuestion-1];
		associateItems.forEach(pair =>
		{
			// Dessiner les lignes entre les éléments associés
			const startElement = document.querySelector(`.association-item[data-id="${pair[0]}"]`);
			const endElement   = document.querySelector(`.association-item[data-id="${pair[1]}"]`);
			drawLine(startElement, endElement);
		});

		// Désactiver le bouton de suppression des lignes
		const deleteLineButton = document.querySelector('.delete-line-button');
		deleteLineButton.style.display = 'none';

		// Désactiver les éléments associés
		const associationItems = document.querySelectorAll('.association-item');
		associationItems.forEach(item =>
		{
			item.disabled = true;
			item.classList.remove('selected');
			item.style.pointerEvents = 'none';
		});

		// Afficher les points obtenus dans le feedback
		const feedbackPoint = document.querySelector('.feedback-point');
		feedbackPoint.textContent = `${pointAnsweredQuestions[currentQuestion-1]} points`;

	}

	// Dessiner les lignes entre les éléments associés
	function drawLine(startElement, endElement)
	{
		const startRect = startElement.getBoundingClientRect();
		const endRect = endElement.getBoundingClientRect();

		// Coordonnées de départ : bord droit de la div de gauche
		const startX = startRect.right + window.scrollX;
		const startY = startRect.top + startRect.height / 2 + window.scrollY;

		// Coordonnées d'arrivée : bord gauche de la div de droite
		const endX = endRect.left + window.scrollX;
		const endY = endRect.top + endRect.height / 2 + window.scrollY;

		const length = Math.sqrt((endX - startX) ** 2 + (endY - startY) ** 2);
		const angle = Math.atan2(endY - startY, endX - startX) * 180 / Math.PI;

		const line = document.createElement('div');
		line.classList.add('line');
		line.style.width = `${length}px`;
		line.style.height = '2px';
		line.style.transform = `rotate(${angle}deg)`;
		line.style.left = `${startX}px`;
		line.style.top = `${startY}px`;

		document.querySelector('.line-container').appendChild(line);
	}
});


// Validation d'une question
function validateQuestion()
{
	const body = document.body;
	const questionPoints = parseInt(body.getAttribute('point'), 10);

	// Ajouter les points de la question à la somme totale
	totalPoint += questionPoints;
	nbQuestionRepondu++;

	// On la valide en fonction du type de question
	if(document.querySelector('.question-multiple'   )) { validateMultipleQuestion   (questionPoints) ; }
	if(document.querySelector('.eliminate-question'  )) { validateEliminateQuestion  (questionPoints) ; }
	if(document.querySelector('.question-unique'     )) { validateUniqueQuestion     (questionPoints) ; }
	if(document.querySelector('.association-question')) { validateAssociationQuestion(questionPoints) ; }

	// Mettre à jour pour dire que la question a été répondu
	answeredQuestions[currentQuestion-1] = true;

	// Reset le timer
	clearInterval(intervalId);

	// définir le feedback
	const feedbackPoint = document.querySelector('.feedback-point');
	feedbackPoint.textContent = `${point} points`;

	// Afficher la popup
	const popup = document.querySelector(".popup");
	popup.style.display = 'flex';

	// Cacher le bouton de validation
	const validateButton = document.querySelector(".validate-button");
	validateButton.style.display = 'none';

	// Afficher le bouton suivant
	const nextQuestionButton = document.querySelector(".next-button");
	nextQuestionButton.style.display = 'block';

	// Afficher le bouton de feedback
	const feedbackButton = document.querySelector(".feedback-button");
	feedbackButton.style.display = 'block';
}


// Validation des questions
// Pour les questions à choix multiples
function validateMultipleQuestion(questionPoints)
{
	// Initialiser un tableau pour les réponses sélectionnées
	const selectedAnswers = [];
	const titre = document.querySelector('.feedback-title');

	// Ajouter les réponses sélectionnées au tableau
	document.querySelectorAll('input[type="checkbox"]:checked').forEach((checkbox) => { selectedAnswers.push(checkbox.id); });

	answeredQuestionsResponse[currentQuestion-1] = selectedAnswers || null;

	// Vérifier si les réponses sélectionnées sont correctes
	if(selectedAnswers.length === correctAnswers[currentQuestion-1].length && selectedAnswers.every((value, index) => value === correctAnswers[currentQuestion-1][index]))
	{
		titre.classList.add('correct');
		titre.textContent = "Bravo, c'est la bonne réponse !";
		
		// Ajouter les points de la question aux points accumulés
		point += questionPoints;
		pointAnsweredQuestions[currentQuestion-1] = questionPoints;
	}
	else // Si les réponses sélectionnées ne sont pas correctes
	{
		titre.classList.add('incorrect');
		titre.textContent = "Dommage, ce n'est pas la bonne réponse.";
	}

	// Désactiver les réponses
	const reponses = document.querySelectorAll('input');
	reponses.forEach((input) => { input.disabled = true; });
}

// Pour les questions à choix unique
function validateUniqueQuestion(questionPoints)
{
	const selectedAnswer = document.querySelector('input[type="radio"]:checked');
	const titre          = document.querySelector('.feedback-title');
	
	// Ajouter la réponse sélectionnée au tableau
	if (selectedAnswer !== null) { answeredQuestionsResponse[currentQuestion-1] = selectedAnswer.id || null; }

	// Vérifier si la réponse sélectionnée est correcte
	if(selectedAnswer && selectedAnswer.id === correctAnswers[currentQuestion-1][0])
	{
		titre.classList.add('correct');
		titre.textContent = "Bravo, c'est la bonne réponse !";
		
		// Ajouter les points de la question aux points accumulés
		point += questionPoints;
		pointAnsweredQuestions[currentQuestion-1] = questionPoints;
	}
	else // Si la réponse sélectionnée n'est pas correcte
	{
		titre.classList.add('incorrect');
		titre.textContent = "Dommage, ce n'est pas la bonne réponse.";
	}

	// Désactiver les réponses
	const reponses = document.querySelector('.answers');
	reponses.querySelectorAll('input').forEach((input) => { input.disabled = true; });
}

// Pour les questions à élimination
function validateEliminateQuestion(questionPoints)
{
	const selectedAnswer = document.querySelector('input[type="radio"]:checked');
	const titre = document.querySelector('.feedback-title');

	// Ajouter la réponse sélectionnée au tableau
	if (selectedAnswer !== null) { answeredQuestionsResponse[currentQuestion-1] = selectedAnswer.id || null; }

	// Vérifier si la réponse sélectionnée est correcte
	if(selectedAnswer && selectedAnswer.id === correctAnswers[currentQuestion-1][0][0])
	{
		titre.classList.add('correct');
		titre.textContent = "Bravo, c'est la bonne réponse !";

		// Ajouter les points de la question aux points accumulés
		point += questionPoints;
		pointAnsweredQuestions[currentQuestion-1] = questionPoints
	}
	else // Si la réponse sélectionnée n'est pas correcte
	{
		titre.classList.add('incorrect');
		titre.textContent = "Dommage, ce n'est pas la bonne réponse.";
	}
	// Prendre en compte les indices utilisés
	point += hintPoint;
	pointAnsweredQuestions[currentQuestion-1] += hintPoint;

	// Désactiver les réponses
	const reponses = document.querySelectorAll('input');
	reponses.forEach((input) => { input.disabled = true; });

	// Désactiver le bouton d'indice
	const hint = document.querySelector(".hint");
	hint.style.display = 'none';
}

// Pour les questions d'association
function validateAssociationQuestion(questionPoints)
{
	const titre         = document.querySelector('.feedback-title');
	const selectedItems = associateItems;
	const correctItems  = correctAnswers[currentQuestion - 1];

	// Désactiver le bouton de suppression des lignes
	const deleteLineButton = document.querySelector('.delete-line-button');
	deleteLineButton.style.display = 'none';

	// Ajouter les réponses sélectionnées au tableau
	answeredQuestionsResponse[currentQuestion-1] = selectedItems || null;
	
	// Vérifier si le nombre de paires sélectionnées est correct
	if (selectedItems.length !== correctItems.length)
	{
		titre.classList.add('incorrect');
		titre.textContent = "Dommage, ce n'est pas la bonne réponse.";
		return;
	}

	// Vérifier si les paires sélectionnées sont correctes
	const correct = selectedItems.every(pair =>
	{
		const correctPair = correctItems.find(correctPair => correctPair[0] === pair[0] && correctPair[1] === pair[1]);
		return !!correctPair;
	});

	// Dans le cas où toutes les paires sont correctes
	if (correct)
	{
		titre.classList.add('correct');
		titre.textContent = "Bravo, c'est la bonne réponse !";

		// Ajouter les points de la question aux points accumulés
		point += questionPoints;
		pointAnsweredQuestions[currentQuestion-1] = questionPoints;
	}
	else // Dans tout n'est pas correct
	{
		titre.classList.add('incorrect');
		titre.textContent = "Dommage, ce n'est pas la bonne réponse.";
	}

	// Désactiver les éléments associés
	const associatedItems = document.querySelectorAll('.association-item');
	associatedItems.forEach(item =>
	{
		item.disabled = true;
		item.classList.remove('selected');
		item.style.pointerEvents = 'none';
	});

	const deletLineButton = document.querySelector('.delete-line-button');
	deletLineButton.style.display = 'none';
}

// Afficher un indice
function showHint()
{
	// Vérifier si il reste des indices à utiliser
	if(usedHint < correctAnswers[currentQuestion-1][1].length)
	{
		const hint           = correctAnswers[currentQuestion-1][1][usedHint][0];
		const hintValue      = correctAnswers[currentQuestion-1][1][usedHint][1];
		const hintElement    = document.querySelector(`#${hint}`);

		// Désactiver l'indice mis en place par l'enseignant
		hintElement.disabled = true;
		hintElement.nextElementSibling.style.textDecoration = 'line-through';
		usedHint++;

		// Prendre en compte les indices utilisés
		hintPoint -= hintValue;

		const hintCurrentData = document.querySelector('.hint-current-data');
		const hintTotalData   = document.querySelector('.hint-total-data'  );

		// Mettre à jour les indices utilisés
		hintCurrentData.textContent = usedHint;
		hintTotalData.textContent = correctAnswers[currentQuestion-1][1].length;
	}
}