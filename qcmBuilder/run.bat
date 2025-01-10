@echo off
REM Vérifie si les fichiers arg.list et compile.list existent
IF EXIST "arg.list" IF EXIST "compile.list" (
	echo Compilation en cours...
	javac @arg.list @compile.list

	REM Vérifie si la compilation a réussi
	IF %ERRORLEVEL% EQU 0 (
		echo Compilation reussie.

		REM Générer la Javadoc
	REM	IF EXIST "javadoc.list" (
	REM		echo Generation de la Javadoc...
	REM		IF NOT EXIST "../doc" mkdir ../doc
	REM		javadoc -d ../doc @javadoc.list

	REM		IF %ERRORLEVEL% EQU 0 (
	REM			echo Javadoc generee avec succes dans le dossier 'doc'.
	REM		) ELSE (
	REM			echo Erreur : La generation de la Javadoc a echoue.
	REM			EXIT /B 1
	REM		)
	REM	) ELSE (
	REM		echo Erreur : Le fichier javadoc.list est manquant.
	REM		EXIT /B 1
	REM	)

		REM Création du dossier temporaire
		cd data/ressources_notions_questions
		mkdir temp
		cd ../..
		REM Execution de la classe principale
		echo Execution de src/Controleur...
		cd class
		java src.Controleur
	) ELSE (
		echo Erreur : La compilation a echoue.
		EXIT /B 1
	)
) ELSE (
	echo Erreur : Les fichiers arg.list ou compile.list sont manquants.
	EXIT /B 1
)
