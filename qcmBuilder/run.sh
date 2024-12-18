#!/bin/bash

# Vérifie si les fichiers arg.list et compile.list existent
if [[ -f "arg.list" && -f "compile.list" ]]; then
	echo "Compilation en cours..."
	javac @arg.list @compile.list

	# Vérifie si la compilation a réussi
	if [[ $? -eq 0 ]]; then
		echo "Compilation réussie."

	#	# Générer la Javadoc
	#	echo "Génération de la Javadoc..."
	#	mkdir -p ../doc
	#	javadoc -d ../doc @compile.list

	#	if [[ $? -eq 0 ]]; then
	#		echo "Javadoc générée avec succès dans le dossier 'doc'."
	#	else
	#		echo "Erreur : La génération de la Javadoc a échoué."
	#		exit 1
	#	fi

		# Exécution de la classe principale
		echo "Exécution de src/Controleur..."
		cd class
		java src/Controleur
	else
		echo "Erreur : La compilation a échoué."
		exit 1
	fi
else
	echo "Erreur : Les fichiers arg.list ou compile.list sont manquants."
	exit 1
fi
