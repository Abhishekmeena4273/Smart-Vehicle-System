@echo off
cd /d "%~dp0"
echo ==========================================
echo   Compiling Smart Vehicle Management System
echo ==========================================
echo.

echo Cleaning old class files...
del /q /s src\com\vehicle\system\*.class 2>nul

echo Compiling Java files with UTF-8 encoding...
javac -encoding UTF-8 -d src src\com\vehicle\system\*.java

if errorlevel 1 (
    echo.
    echo ERROR: Compilation failed!
    echo Check for syntax errors in your Java files.
    pause
    exit /b 1
)

echo.
echo ✅ Compilation successful!
echo .class files created in src\com\vehicle\system\
pause