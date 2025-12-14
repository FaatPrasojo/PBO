@echo off
REM BattleSimulator Launcher
REM Double-click this file to run the game
cd /d "%~dp0"
java -jar BattleSimulator.jar
if errorlevel 1 (
    echo.
    echo ERROR: Failed to run application
    echo Please make sure Java is installed and PATH is set correctly
    echo.
    pause
)
