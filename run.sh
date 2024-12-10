#!/bin/bash

# Vérifie si les fichiers arg.list et compile.list existent
if [[ -f "arg.list" && -f "compile.list" ]]; then
	echo "Compilation en cours..."
	javac @arg.list @compile.list

	# Vérifie si la compilation a réussi
	if [[ $? -eq 0 ]]; then
		echo "Compilation réussie. Exécution de src/Controleur..."
		java src/Controleur
	else
		echo "Erreur : La compilation a échoué."
		exit 1
	fi
else
	echo "Erreur : Les fichiers arg.list ou compile.list sont manquants."
	exit 1
fi
