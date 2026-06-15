@echo off
setlocal EnableDelayedExpansion
chcp 65001 >nul
cd /d "%~dp0"

echo ========================================
echo  Honor of Kings IMS - Compile and Run
echo  Project: %CD%
echo ========================================
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

if !ERR! NEQ 0 (
    echo.
    echo COMPILE FAILED. Fix errors above, then run this script again.
    pause
    exit /b 1
)

if not exist out\Main.class (
    echo ERROR: out\Main.class was not created.
    pause
    exit /b 1
)

echo Compile OK.
echo Main.class exists: YES

echo.
echo [5/5] Starting program...
echo Login: 饭团linj44 / 070530
echo        红糖guoy10 / 123456
echo.
java -Dfile.encoding=UTF-8 -cp out Main

pause
