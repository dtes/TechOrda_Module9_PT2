@echo off
REM Быстрый запуск Chrome для режима переиспользования (Windows)
REM Quick start Chrome for reuse mode (Windows)

echo Starting Chrome with remote debugging...
echo.

start "" "C:\Program Files\Google\Chrome\Application\chrome.exe" --remote-debugging-port=9222 --user-data-dir="%USERPROFILE%\chrome-dev-profile"

echo Chrome started on Windows
echo Remote debugging: http://localhost:9222
echo.
echo Теперь:
echo 1. Выполните аутентификацию в открывшемся Chrome
echo 2. Запустите тесты: gradlew.bat test -Dchrome.reuse=true
echo.
echo For English:
echo 1. Complete authentication in the opened Chrome
echo 2. Run tests: gradlew.bat test -Dchrome.reuse=true
echo.

pause
