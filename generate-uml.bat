@echo off
chcp 65001 >nul
cd /d "%~dp0"

set DOCS=%~dp0docs
set TOOLS=%~dp0tools
set JAR=%TOOLS%\plantuml.jar
set PUML=%DOCS%\uml.puml
set OUT=%DOCS%\uml.png

if not exist "%TOOLS%" mkdir "%TOOLS%"

if not exist "%JAR%" (
    echo Downloading PlantUML...
    powershell -NoProfile -Command "Invoke-WebRequest -Uri 'https://github.com/plantuml/plantuml/releases/download/v1.2024.8/plantuml-1.2024.8.jar' -OutFile '%JAR%' -UseBasicParsing"
)

if not exist "%PUML%" (
    echo ERROR: Missing %PUML%
    pause
    exit /b 1
)

echo Generating UML PNG...
java -jar "%JAR%" -charset UTF-8 -tpng -o "%DOCS%" "%PUML%"

if exist "%DOCS%\HonorOfKingsIMS.png" (
    move /y "%DOCS%\HonorOfKingsIMS.png" "%OUT%" >nul
)

if exist "%OUT%" (
    echo SUCCESS: %OUT%
) else (
    echo Checking for other PNG files in docs...
    dir /b "%DOCS%\*.png"
    echo ERROR: uml.png was not created.
    pause
    exit /b 1
)

pause
