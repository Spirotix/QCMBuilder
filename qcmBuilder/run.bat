@echo off

REM Vérifie si les fichiers arg.list et compile.list existent
if exist arg.list (
	if exist compile.list (
		echo Compilation en cours...
		javac @arg.list @compile.list

		REM Vérifie si la compilation a réussi
		if %errorlevel%==0 (
			echo Compilation réussie. Exécution de src/Controleur...
			java class/Controleur
		) else (
			echo Erreur : La compilation a échoué.
			exit /b 1
		)
	) else (
		echo Erreur : Le fichier compile.list est manquant.
		exit /b 1
	)
) else (
	echo Erreur : Le fichier arg.list est manquant.
	exit /b 1
)
