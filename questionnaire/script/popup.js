// Ouvrir et fermer la popup
document.addEventListener("DOMContentLoaded", function () {
	const feedbackButton = document.querySelector(".feedback-button");
	const popup = document.querySelector(".popup");
	const closeButton = document.querySelector(".close-button");


	feedbackButton.addEventListener("click", function () {
		popup.style.display = 'flex';
	});

	closeButton.addEventListener("click", function () {
		popup.style.display = 'none';
	});

	// Fermer la popup en cliquant en dehors de la popup
	window.addEventListener("click", function (event) {
		if (event.target === popup) {
			popup.style.display = 'none';
		}
	});
});
