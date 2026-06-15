@echo off
setlocal EnableDelayedExpansion
chcp 65001 >nul
cd /d "%~dp0"

echo Compiling project in:
echo %CD%
echo.

echo [1/5] Removing UTF-8 BOM from source files...
powershell -NoProfile -ExecutionPolicy Bypass -File "%~dp0strip-bom.ps1"
echo.

echo [2/5] Cleaning old out folder...
if exist out rmdir /s /q out
mkdir out

echo [3/5] Collecting source files...
set COUNT=0
set "SRCFILES="
for /r src %%F in (*.java) do (
    set /a COUNT+=1
    set "SRCFILES=!SRCFILES! "%%F""
)
echo Found !COUNT! Java files.

echo [4/5] Compiling...
javac -encoding UTF-8 -d out !SRCFILES!
set ERR=!ERRORLEVEL!

echo.
if !ERR! NEQ 0 (
    echo RESULT: COMPILE FAILED
    pause
    exit /b 1
)

echo RESULT: COMPILE OK
if exist out\Main.class (echo Main.class: YES) else (echo Main.class: NO)
if exist out\util\AdminInitializer.class (echo AdminInitializer.class: YES) else (echo AdminInitializer.class: NO)
echo.
echo [5/5] Run with: java -Dfile.encoding=UTF-8 -cp out Main
pause
