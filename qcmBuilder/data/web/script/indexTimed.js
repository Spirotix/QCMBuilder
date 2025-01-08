// Charger la question actuelle depuis l'attribut value du body
document.addEventListener("DOMContentLoaded", function () 
{
	// on récupère le chrono
	if (document.querySelector('.chrono-data'))
	{
		const chronoDataElement = document.querySelector(".chrono-data");
		chronoDataElement.textContent = "Oui";
	}

	// on récupère le nombre de questions
	const bodyValue      = document.body.getAttribute('value');
	let pCurrentQuestion = parseInt(bodyValue, 10);
	currentQuestion      = pCurrentQuestion;

	if(document.querySelector('.question')) 
	{
		// On récupère la barre de progression
		const progressBars = document.querySelectorAll(".progress-bar");

		// On fait la barre de progression
		progressBars.forEach(progressBar => {
			const progressFill = progressBar.querySelector(".progress-bar-fill");

			if (totalQuestions && currentQuestion >= 0) {
				
				const widthPercentage = (currentQuestion / totalQuestions) * 100;
				progressFill.style.width = `${widthPercentage}%`;
			}
		});

		const progressBarPercentage = document.querySelector(".progress-percentage");
		progressBarPercentage.textContent = (currentQuestion / totalQuestions * 100).toFixed(0) + '%';

		// On récupère le bouton précédent
		const previousQuestionButton = document.querySelector(".previous-button");
		previousQuestionButton.style.display = 'none';

		// On récupère le bouton suivant
		const nextQuestionButton = document.querySelector(".next-button");
		nextQuestionButton.style.display = 'none';

		// On récupère le bouton feedback
		const feedbackButton = document.querySelector(".feedback-button");
		feedbackButton.style.display = 'none';
	}

	// Récupérer les points depuis l'URL
	const urlParams = new URLSearchParams(window.location.search);

	point      = parseInt(urlParams.get('points'))      || 0;
	totalPoint = parseInt(urlParams.get('totalPoints')) || 0;

	if (document.querySelector('.eliminate-question')) {
		hintValue = 0;
		if (usedHint < correctAnswers[currentQuestion-1][1].length)
		{
			hintValue = correctAnswers[currentQuestion-1][1][usedHint+1][1];
		}
		const hintCost = document.querySelector('.hint-cost');
		hintCost.textContent = hintValue;
	}

	// Afficher les scores sur la page de fin
	if (document.querySelector(".score-data")) 
	{
		// Afficher les points accumulés et la somme des points
		document.querySelector(".score-data").textContent = `${point} / ${totalPoint}`;
	}

	// Commencer le questionnaire quand on clique sur le bouton "Commencer"
	if (document.querySelector(".start-button")) 
	{
		document.querySelector(".start-button").addEventListener('click', function() 
		{
			// Rediriger vers la première question quand on clique sur le bouton "Commencer"
			currentQuestion = 1;
			window.location.href = `./pages/question${currentQuestion}.html?points=${point}&totalPoints=${totalPoint}`;
		});
	}

	// Passer aux questions suivantes quand on clique sur le bouton "Suivant"
	if (document.querySelector(".next-button")) 
	{
		document.querySelector(".next-button").addEventListener('click', function() 
		{
			if(currentQuestion >= totalQuestions) // Si on est à la dernière question
			{
				// Rediriger vers la page de fin
				currentQuestion = 0;
				window.location.href = `../fin.html?points=${point}&totalPoints=${totalPoint}`;
			} 
			else // Sinon, passer à la question suivante
			{
				currentQuestion++;
				window.location.href = `./question${currentQuestion}.html?points=${point}&totalPoints=${totalPoint}`;
			}
		});
	}

	// Revenir à la question précédente quand on clique sur le bouton "Précédent"
	if (document.querySelector(".previous-button")) 
	{
		document.querySelector(".previous-button").addEventListener('click', function() 
		{
			if (currentQuestion > 1) // Si on n'est pas à la première question
			{
				// Revenir à la question précédente
				currentQuestion--;
				window.location.href = `./question${currentQuestion}.html?points=${point}&totalPoints=${totalPoint}`;
			}
			else // Sinon, retourner à l'index
			{
				window.location.href = '../index.html';
			}
		});
	}

	// Valider la question actuelle quand on clique sur le bouton "Valider"
	if (document.querySelector(".validate-button")) 
	{
		document.querySelector(".validate-button").addEventListener('click', function() 
		{
			validateQuestion();
		});
	}

	// Recommencer le questionnaire quand on clique sur le bouton "Recommencer"
	if (document.querySelector(".restart-button")) 
	{
		document.querySelector(".restart-button").addEventListener('click', function() 
		{
			currentQuestion      = 0;            // Réinitialiser la question actuelle
			point                = 0;            // Réinitialiser les points accumulés
			totalPoint           = 0;            // Réinitialiser la somme des points
			window.location.href = 'index.html'; // Rediriger vers l'index
		});
	}

	// Supprimer toutes les lignes quand on clique sur le bouton "Supprimer les lignes"
	if(document.querySelector('.delete-line-button')) 
	{
		document.querySelector('.delete-line-button').addEventListener('click', function() 
		{
			deleteAllLines();
		});
	}

	// Afficher le temps restant
	const countdownElement = document.querySelector(".countdown-data");
	if (countdownElement) 
	{
		const duration = parseInt(countdownElement.textContent, 10); // Récupérer la durée en secondes
		displayTime(duration, countdownElement); 
	}

	// Afficher les indices quand on clique sur le bouton "Indice"
	if(document.querySelector('.hint-button')) 
	{
		document.querySelector('.hint-button').addEventListener('click', function() { showHint();});
		const hintPoint = document.querySelector('.hint-point');
		hintPoint.textContent = '-' + correctAnswers[currentQuestion-1][1][usedHint][1];
	}

	// Gérer les éléments d'association
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
				item.classList .remove('selected'             );
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
				const parent2 = item              .closest('.association-column');
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
				const endId   = tempSelectItems[1].getAttribute('data-id');
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
			item.removeEventListener('click', handleAssociationItemClick); // Supprimer l'eventListener existant
			item.addEventListener   ('click', handleAssociationItemClick); // Ajouter un nouvel eventListener
		});

		// Redessiner les lignes lors du redimensionnement de la fenêtre
		window.addEventListener('resize', function() 
		{
			document.querySelectorAll('.line').forEach(line => line.remove());
			associateItems.forEach(pair => 
			{
				const startElement = document.querySelector(`.association-item[data-id="${pair[0]}"]`);
				const endElement   = document.querySelector(`.association-item[data-id="${pair[1]}"]`);
				drawLine(startElement, endElement);
			});
		});
	}

	// Supprimer toutes les lignes
	function deleteAllLines() 
	{
		// Supprimer toutes les lignes affichées
		document.querySelectorAll('.line').forEach(line => line.remove());

		// Réinitialiser le tableau des éléments associés
		associateItems = [];
		// Réinitialiser les classes 'selected' des éléments d'association
		document.querySelectorAll('.association-item').forEach(item => item.classList.remove('selected'));
	}
});

// Dessiner une ligne entre deux éléments
function drawLine(startElement, endElement, correct = 'F') 
{
	const startRect = startElement.getBoundingClientRect();
	const endRect   = endElement.getBoundingClientRect();

	// Coordonnées de départ : bord droit de la div de gauche
	const startX = startRect.right + window.scrollX;
	const startY = startRect.top   + startRect.height / 2 + window.scrollY;

	// Coordonnées d'arrivée : bord gauche de la div de droite
	const endX = endRect.left + window.scrollX;
	const endY = endRect.top  + endRect.height / 2 + window.scrollY;

	const length = Math.sqrt((endX - startX) ** 2 + (endY - startY) ** 2);
	const angle  = Math.atan2(endY - startY, endX - startX) * 180 / Math.PI;

	// Créer une div pour la ligne
	const line = document.createElement('div');
	line.classList.add('line');
	line.style.width     =         `${length}px`;
	line.style.height    =                 '2px'; // Assurez-vous que la hauteur est définie pour voir la ligne
	line.style.transform = `rotate(${angle}deg)`;
	line.style.left      =         `${startX}px`;
	line.style.top       =         `${startY}px`;

	line.classList.add(startElement.getAttribute('data-id'));
	line.classList.add(endElement.getAttribute('data-id'));

	if (correct === 'G')
	{
		line.style.backgroundColor = '#00561b';
		line.style.height = '4px';
	}
	else if (correct === 'R')
	{
		line.style.backgroundColor = '#a80000';
		line.style.height = '1px';
	}

	document.querySelector('.line-container').appendChild(line);
}

// Validation d'une question
function validateQuestion() 
{
	const body = document.body;
	const questionPoints = parseInt(body.getAttribute('point'), 10);
	totalPoint += questionPoints; // Ajouter les points de la question à la somme totale

	// Vérifier le type de question
	if(document.querySelector('.question-multiple')) 
	{
		validateMultipleQuestion(questionPoints);
	}
	if(document.querySelector('.eliminate-question')) 
	{
		validateEliminateQuestion(questionPoints);
	}
	if(document.querySelector('.question-unique')) 
	{
		validateUniqueQuestion(questionPoints);
	}
	if(document.querySelector('.association-question')) 
	{
		validateAssociationQuestion(questionPoints);
	}

	// Arrêter le chrono
	clearInterval(intervalId);

	// Afficher les points dans le feedback
	const feedbackPoint = document.querySelector('.feedback-point');
	feedbackPoint.textContent = `${point} points`;

	// Afficher le popup
	const popup = document.querySelector(".popup");
	popup.style.display = 'flex';

	// Cacher le bouton "Valider"
	const validateButton = document.querySelector(".validate-button");
	validateButton.style.display = 'none';

	// Afficher le bouton "Suivant"
	const nextQuestionButton = document.querySelector(".next-button");
	nextQuestionButton.style.display = 'block';

	// Afficher le bouton "Feedback"
	const feedbackButton = document.querySelector(".feedback-button");
	feedbackButton.style.display = 'block';
}

// Afficher le temps restant
function displayTime(duration, display) 
{
	let timer = duration, minutes, seconds;
	// Définir un intervalle pour mettre à jour le temps toutes les secondes
	intervalId = setInterval(function () 
	{
		// Calculer les minutes et les secondes restantes
		minutes = parseInt(timer / 60, 10);
		seconds = parseInt(timer % 60, 10);

		// Ajouter un zéro devant les chiffres inférieurs à 10 pour un affichage correct
		minutes = minutes < 10 ? "0" + minutes : minutes;
		seconds = seconds < 10 ? "0" + seconds : seconds;

		// Mettre à jour l'affichage du temps restant
		display.textContent = minutes + ":" + seconds;

		// Décrémenter le timer
		if (--timer < 0) 
		{
			// Si le temps est écoulé, arrêter l'intervalle et valider la question
			clearInterval(intervalId);
			validateQuestion();
			return;
		}
	}, 1000);
}

// Validation d'une question à choix multiple
function validateMultipleQuestion(questionPoints) 
{
	// Récupérer les réponses sélectionnées
	const selectedAnswers = [];
	const titre = document.querySelector('.feedback-title');

	// Parcourir toutes les cases cochées et ajouter leurs IDs au tableau selectedAnswers
	document.querySelectorAll('input[type="checkbox"]:checked').forEach((checkbox) => 
	{
		selectedAnswers.push(checkbox.id);
	});

	// Vérifier si le nombre de réponses sélectionnées est correct et si elles correspondent aux réponses correctes
	if(selectedAnswers.length === correctAnswers[currentQuestion-1].length && 
	   selectedAnswers.every((value, index) => value === correctAnswers[currentQuestion-1][index])) 
	{
		// Si toutes les réponses sont correctes, afficher un message de succès
		titre.classList.add('correct');
		titre.textContent = "Bravo, c'est la bonne réponse !";
		point += questionPoints; // Ajouter les points de la question aux points accumulés
	}
	else 
	{
		// Sinon, afficher un message d'erreur
		titre.classList.add('incorrect');
		titre.textContent = "Dommage, ce n'est pas la bonne réponse.";
	}

	// Désactiver toutes les cases à cocher pour empêcher de changer les réponses après validation
	const reponses = document.querySelectorAll('input');
	reponses.forEach((input) => 
	{
		input.disabled = true;
		if(correctAnswers[currentQuestion-1].includes(input.id)) 
		{
			input.parentElement.style.color = '#00561b';
			input.parentElement.style.fontWeight = 'bold';
			const label = document.querySelector(`label[for="${input.id}"]`);
			label.textContent = label.textContent + ' (correct)'; 
		}
		else if (selectedAnswers.includes(input.id))
		{
			input.parentElement.style.color = '#a80000';
			input.parentElement.style.fontWeight = 'bold';
			const label = document.querySelector(`label[for="${input.id}"]`);
			label.textContent = label.textContent + ' (incorrect)'; 
		}
	});
}

// Validation d'une question à choix unique
function validateUniqueQuestion(questionPoints) 
{
	// Récupérer la réponse sélectionnée
	const selectedAnswer = document.querySelector('input[type="radio"]:checked');
	// Récupérer l'élément pour afficher le titre du feedback
	const titre = document.querySelector('.feedback-title');

	// Vérifier si la réponse sélectionnée est correcte
	if(selectedAnswer && selectedAnswer.id === correctAnswers[currentQuestion-1][0]) 
	{
		// Si la réponse est correcte, ajouter la classe 'correct' et afficher un message de succès
		titre.classList.add('correct');
		titre.textContent = "Bravo, c'est la bonne réponse !";
		// Ajouter les points de la question aux points accumulés
		point += questionPoints;
	}
	else 
	{
		// Si la réponse est incorrecte, ajouter la classe 'incorrect' et afficher un message d'erreur
		titre.classList.add('incorrect');
		titre.textContent = "Dommage, ce n'est pas la bonne réponse.";
	}

	// Désactiver toutes les options de réponse pour empêcher de changer les réponses après validation
	const reponses = document.querySelector('.answers');
	reponses.querySelectorAll('input').forEach((input) => 
	{
		input.disabled = true;
		if(input.id === correctAnswers[currentQuestion-1][0]) 
			{
				input.parentElement.style.color = '#00561b';
				input.parentElement.style.fontWeight = 'bold';
				const label = document.querySelector(`label[for="${input.id}"]`);
				label.textContent = label.textContent + ' (correct)';
			}
			else if (selectedAnswer && selectedAnswer.id === input.id)
			{
				input.parentElement.style.color = '#a80000';
				input.parentElement.style.fontWeight = 'bold';
				const label = document.querySelector(`label[for="${input.id}"]`);
				label.textContent = label.textContent + ' (incorrect)'; 
			}
	});
}

// Validation d'une question à élimination
function validateEliminateQuestion(questionPoints) 
{
	// Récupérer la réponse sélectionnée
	const selectedAnswer = document.querySelector('input[type="radio"]:checked');
	// Récupérer l'élément pour afficher le titre du feedback
	const titre = document.querySelector('.feedback-title');

	// Vérifier si la réponse sélectionnée est correcte
	if(selectedAnswer && selectedAnswer.id === correctAnswers[currentQuestion-1][0][0]) 
	{
		// Si la réponse est correcte, ajouter la classe 'correct' et afficher un message de succès
		titre.classList.add('correct');
		titre.textContent = "Bravo, c'est la bonne réponse !";
		// Ajouter les points de la question aux points accumulés
		point += questionPoints;
	}
	else 
	{
		// Si la réponse est incorrecte, ajouter la classe 'incorrect' et afficher un message d'erreur
		titre.classList.add('incorrect');
		titre.textContent = "Dommage, ce n'est pas la bonne réponse.";
	}
	// Ajouter les points des indices utilisés aux points accumulés
	point += hintPoint;

	// Désactiver toutes les options de réponse pour empêcher de changer les réponses après validation
	const reponses = document.querySelectorAll('input');
	reponses.forEach((input) => 
	{
		input.disabled = true;
		if(input.id === correctAnswers[currentQuestion-1][0][0]) 
		{
			input.parentElement.style.color = '#00561b';
			input.parentElement.style.fontWeight = 'bold';
			const label = document.querySelector(`label[for="${input.id}"]`);
			label.textContent = label.textContent + ' (correct)';
		}
		else if (selectedAnswer && selectedAnswer.id === input.id)
		{
			input.parentElement.style.color = '#a80000';
			input.parentElement.style.fontWeight = 'bold';
			const label = document.querySelector(`label[for="${input.id}"]`);
			label.textContent = label.textContent + ' (incorrect)'; 
		}
	});

	// Cacher le bouton d'indice
	const hint = document.querySelector(".hint");
	hint.style.display = 'none';
}

// Validation d'une question avec association
function validateAssociationQuestion(questionPoints) 
{
	// Récupérer l'élément pour afficher le titre du feedback
	const titre = document.querySelector('.feedback-title');
	// Récupérer les éléments associés sélectionnés par l'utilisateur
	const selectedItems = associateItems;
	// Récupérer les éléments corrects pour la question actuelle
	const correctItems = correctAnswers[currentQuestion - 1];

	// Cacher le bouton "Supprimer les lignes"
	const deleteLineButton = document.querySelector('.delete-line-button');
	deleteLineButton.style.display = 'none';
	
	// Vérifier si le nombre de paires sélectionnées est correct
	let suite = true;
	if (selectedItems.length !== correctItems.length) 
	{
		// Si le nombre de paires est incorrect, afficher un message d'erreur
		titre.classList.add('incorrect');
		titre.textContent = "Dommage, ce n'est pas la bonne réponse.";
		suite = false;
	}
	
	// Vérifier si toutes les paires sélectionnées sont correctes
	if(suite)
	{
		const correct = selectedItems.every(pair => 
		{
			const correctPair = correctItems.find(correctPair => correctPair[0] === pair[0] && correctPair[1] === pair[1]);
			return !!correctPair;
		});
		
		if (correct) 
		{
			// Si toutes les paires sont correctes, afficher un message de succès
			titre.classList.add('correct');
			titre.textContent = "Bravo, c'est la bonne réponse !";

			// Ajouter les points de la question aux points accumulés
			point += questionPoints;
		} 
		else 
		{
			// Si une ou plusieurs paires sont incorrectes, afficher un message d'erreur
			titre.classList.add('incorrect');
			titre.textContent = "Dommage, ce n'est pas la bonne réponse.";
		}
	}

	// Parcours les pairs sélectionnées pour les mettre en rouge
	selectedItems.forEach(pair => 
		{
			const startElement = document.querySelector(`.left-column .association-item[data-id="${pair[0]}"]`);
			const endElement   = document.querySelector(`.right-column .association-item[data-id="${pair[1]}"]`);
			if (!correctItems.find(correctPair => correctPair[0] === pair[0] && correctPair[1] === pair[1]))
			{
				drawLine(startElement, endElement,'R');
			}
		});

	// Parcours les bonne pairs pour les mettre en vert
	correctItems.forEach(pair => 
	{

		const startElement = document.querySelector(`.left-column .association-item[data-id="${pair[0]}"]`);
		const endElement   = document.querySelector(`.right-column .association-item[data-id="${pair[1]}"]`);
		drawLine(startElement, endElement,'G');
	});

	// Désactiver tous les éléments d'association pour empêcher de changer les réponses après validation
	const associatedItems = document.querySelectorAll('.association-item');
	associatedItems.forEach(item => 
	{
		item.disabled = true;
		// Enlever la classe 'selected'
		item.classList.remove('selected');
		// Désactiver les clics sur les éléments
		item.style.pointerEvents = 'none';
	});

	// Cacher le bouton "Supprimer les lignes"
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

		const hintCost = document.querySelector('.hint-cost');
		hintCost.textContent = hintValue;
		

		// Affiche le nombre de point perdus par indices
		if(usedHint < correctAnswers[currentQuestion-1][1].length)
		{
			const hintPointElement = document.querySelector('.lost-points-data');
			hintPointElement.textContent = '-' + correctAnswers[currentQuestion-1][1][usedHint][1];
		}
		else
		{
			const hintButton = document.querySelector('.lost-points-data');
			hintButton.textContent = hintPoint;
			const hintPointElement = document.querySelector('.cout');
			hintPointElement.style.display = 'none';
		}
	}
}