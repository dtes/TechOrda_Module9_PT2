#!/bin/bash

# Быстрый запуск Chrome для режима переиспользования
# Quick start Chrome for reuse mode

echo "Starting Chrome with remote debugging..."

# Определяем ОС
if [[ "$OSTYPE" == "darwin"* ]]; then
    # macOS
    /Applications/Google\ Chrome.app/Contents/MacOS/Google\ Chrome \
        --remote-debugging-port=9222 \
        --user-data-dir="$HOME/chrome-dev-profile" &

    echo ""
    echo "Chrome started on macOS"
    echo "Remote debugging: http://localhost:9222"
    echo ""
    echo "Теперь:"
    echo "1. Выполните аутентификацию в открывшемся Chrome"
    echo "2. Запустите тесты: ./gradlew test -Dchrome.reuse=true"
    echo ""

elif [[ "$OSTYPE" == "linux-gnu"* ]]; then
    # Linux
    google-chrome \
        --remote-debugging-port=9222 \
        --user-data-dir="$HOME/chrome-dev-profile" &

    echo ""
    echo "Chrome started on Linux"
    echo "Remote debugging: http://localhost:9222"
    echo ""
    echo "Теперь:"
    echo "1. Выполните аутентификацию в открывшемся Chrome"
    echo "2. Запустите тесты: ./gradlew test -Dchrome.reuse=true"
    echo ""

else
    echo "Unsupported OS: $OSTYPE"
    echo ""
    echo "Для Windows используйте:"
    echo '"C:\Program Files\Google\Chrome\Application\chrome.exe" --remote-debugging-port=9222 --user-data-dir="C:\chrome-dev-profile"'
    exit 1
fi
