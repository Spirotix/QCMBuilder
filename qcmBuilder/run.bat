@echo off
REM Vérifie si les fichiers arg.list et compile.list existent
IF EXIST "arg.list" IF EXIST "compile.list" (
	echo Compilation en cours...
	javac @arg.list @compile.list

	REM Vérifie si la compilation a réussi
	IF %ERRORLEVEL% EQU 0 (
		echo Compilation reussie.

		REM Générer la Javadoc
		IF EXIST "javadoc.list" (
			echo Generation de la Javadoc...
			IF NOT EXIST "doc" mkdir doc
			javadoc -d doc @javadoc.list

			IF %ERRORLEVEL% EQU 0 (
				echo Javadoc generee avec succes dans le dossier 'doc'.
			) ELSE (
				echo Erreur : La generation de la Javadoc a echoue.
				EXIT /B 1
			)
		) ELSE (
			echo Erreur : Le fichier javadoc.list est manquant.
			EXIT /B 1
		)

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
