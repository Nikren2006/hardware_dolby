#!/system/bin/sh

DOLBY_SERVICE=com.dolby.daxservice

# Убедиться, что сервис Dolby запущен
sleep 5
pm enable $DOLBY_SERVICE || true
am startservice --user 0 -n $DOLBY_SERVICE/.DaxService || true
