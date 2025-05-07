@echo off
setlocal enabledelayedexpansion

:: 0. Créer un runtime avec jlink
echo [*] Création du runtime personnalisé...
rmdir /s /q runtime
jlink --module-path "C:\Program Files\Java\jdk-23\jmods" ^
      --add-modules java.base,java.desktop,java.sql ^
      --output runtime

:: 1. Définir le dossier de sortie
set OUTPUT_DIR=output

:: 2. Créer le dossier s'il n'existe pas
if not exist "%OUTPUT_DIR%" mkdir "%OUTPUT_DIR%"

:: 3. Supprimer les anciens fichiers d'installation
if exist "%OUTPUT_DIR%\TJM-Facture-setup.exe" del "%OUTPUT_DIR%\TJM-Facture-setup.exe"

:: 4. Exécuter jpackage
echo [*] Génération du fichier .exe avec jpackage...
jpackage ^
  --name TJM-Facture ^
  --input target\ ^
  --main-jar factures-1.0-SNAPSHOT-jar-with-dependencies.jar ^
  --main-class com.medails.AppFacture ^
  --type exe ^
  --icon src\resources\AppFacture.ico ^
  --win-shortcut ^
  --win-menu ^
  --runtime-image runtime ^
  --vendor "Lyandris System" ^
  --app-version "1.0.0" 

:: 5. Déplacer l'exécutable généré dans output
if exist "TJM-Facture-1.0.0.exe" move "TJM-Facture-1.0.0.exe" "%OUTPUT_DIR%\TJM-Facture-setup.exe"

:: 6. Vérifier si la génération a réussi
if not exist "%OUTPUT_DIR%\TJM-Facture-setup.exe" (
    echo [!] Erreur : L'exécutable n'a pas été généré !
    pause
    exit /b 1
)

:: 7. Créer un fichier ZIP pour la distribution
echo [*] Création de l'archive ZIP...
powershell Compress-Archive -Path "%OUTPUT_DIR%\*" -DestinationPath "%OUTPUT_DIR%\TJM-Facture.zip" -Force

echo [✓] Build terminé avec succès !
pause
exit
