@echo off
REM Simple Ant-style build script for Windows
REM Usage: antbuild.bat [target]
REM Targets: clean, compile, build, rebuild, run

setlocal enabledelayedexpansion
cd /d "%~dp0"

set SRC_DIR=src
set BUILD_DIR=build
set DIST_DIR=dist
set LIB_DIR=lib
set MAIN_CLASS=BattleGameGUI
set JAR_NAME=BattleSimulator.jar

REM Default target
if "%1"=="" set TARGET=build
if not "%1"=="" set TARGET=%1

echo.
echo =========================================
echo  BattleSimulator Build System (Ant-style)
echo =========================================
echo.

REM CLEAN TARGET
if "%TARGET%"=="clean" (
    echo [clean] Removing build artifacts...
    if exist %BUILD_DIR% rmdir /s /q %BUILD_DIR% >nul 2>&1
    if exist %DIST_DIR% rmdir /s /q %DIST_DIR% >nul 2>&1
    echo [clean] ^✓ Cleanup complete
    goto end
)

REM INIT
echo [init] Creating directories...
if not exist %BUILD_DIR% mkdir %BUILD_DIR%
if not exist %DIST_DIR% mkdir %DIST_DIR%

REM COMPILE TARGET
if "%TARGET%"=="compile" (
    echo [compile] Compiling Java source files...
    javac -cp "%LIB_DIR%\sqlite-jdbc-3.51.1.0.jar" -d %BUILD_DIR% %SRC_DIR%\*.java
    if errorlevel 1 (
        echo [compile] ^✗ Compilation failed!
        pause
        exit /b 1
    )
    echo [compile] ^✓ Compilation successful
    goto end
)

REM BUILD TARGET (default)
if "%TARGET%"=="build" (
    echo [compile] Compiling Java source files...
    javac -cp "%LIB_DIR%\sqlite-jdbc-3.51.1.0.jar" -d %BUILD_DIR% %SRC_DIR%\*.java
    if errorlevel 1 (
        echo [compile] ^✗ Compilation failed!
        pause
        exit /b 1
    )
    echo [compile] ^✓ Compilation successful
    
    echo [jar] Extracting SQLite JDBC...
    if exist temp_ant rmdir /s /q temp_ant >nul 2>&1
    mkdir temp_ant
    cd temp_ant
    jar xf ..\%LIB_DIR%\sqlite-jdbc-3.51.1.0.jar org META-INF >nul 2>&1
    cd ..
    
    echo [jar] Copying compiled files...
    xcopy /E /I /Y %BUILD_DIR%\*.class temp_ant\ >nul 2>&1
    
    echo [jar] Creating manifest...
    (
        echo Manifest-Version: 1.0
        echo Main-Class: %MAIN_CLASS%
        echo Created-By: Ant Build System
        echo.
    ) > temp_ant\MANIFEST.MF
    
    echo [jar] Building JAR archive...
    cd temp_ant
    jar cvfm ..\%DIST_DIR%\%JAR_NAME% MANIFEST.MF *.class org META-INF >nul 2>&1
    cd ..
    
    echo [jar] ^✓ JAR created: %DIST_DIR%\%JAR_NAME%
    
    echo [clean] Removing temporary files...
    rmdir /s /q temp_ant >nul 2>&1
    
    echo.
    echo =========================================
    echo  ^✅ BUILD SUCCESSFUL
    echo =========================================
    echo.
    goto end
)

REM REBUILD TARGET
if "%TARGET%"=="rebuild" (
    echo [rebuild] Cleaning...
    if exist %BUILD_DIR% rmdir /s /q %BUILD_DIR% >nul 2>&1
    if exist %DIST_DIR% rmdir /s /q %DIST_DIR% >nul 2>&1
    
    echo [rebuild] Rebuilding...
    if not exist %BUILD_DIR% mkdir %BUILD_DIR%
    if not exist %DIST_DIR% mkdir %DIST_DIR%
    
    echo [compile] Compiling Java source files...
    javac -cp "%LIB_DIR%\sqlite-jdbc-3.51.1.0.jar" -d %BUILD_DIR% %SRC_DIR%\*.java
    if errorlevel 1 (
        echo [compile] ^✗ Compilation failed!
        pause
        exit /b 1
    )
    echo [compile] ^✓ Compilation successful
    
    echo [jar] Extracting SQLite JDBC...
    if exist temp_ant rmdir /s /q temp_ant >nul 2>&1
    mkdir temp_ant
    cd temp_ant
    jar xf ..\%LIB_DIR%\sqlite-jdbc-3.51.1.0.jar org META-INF >nul 2>&1
    cd ..
    
    echo [jar] Copying compiled files...
    xcopy /E /I /Y %BUILD_DIR%\*.class temp_ant\ >nul 2>&1
    
    echo [jar] Creating manifest...
    (
        echo Manifest-Version: 1.0
        echo Main-Class: %MAIN_CLASS%
        echo Created-By: Ant Build System
        echo.
    ) > temp_ant\MANIFEST.MF
    
    echo [jar] Building JAR archive...
    cd temp_ant
    jar cvfm ..\%DIST_DIR%\%JAR_NAME% MANIFEST.MF *.class org META-INF >nul 2>&1
    cd ..
    
    echo [jar] ^✓ JAR created: %DIST_DIR%\%JAR_NAME%
    echo [copy] Copying JAR to root...
    copy /Y %DIST_DIR%\%JAR_NAME% . >nul 2>&1
    
    echo [clean] Removing temporary files...
    rmdir /s /q temp_ant >nul 2>&1
    
    echo.
    echo =========================================
    echo  ^✅ REBUILD SUCCESSFUL
    echo =========================================
    echo.
    goto end
)

REM RUN TARGET
if "%TARGET%"=="run" (
    echo [run] Starting application...
    java -jar %DIST_DIR%\%JAR_NAME%
    goto end
)

echo Unknown target: %TARGET%
echo Available targets: clean, compile, build, rebuild, run
pause
exit /b 1

:end
echo.
pause
