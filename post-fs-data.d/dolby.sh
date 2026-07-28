#!/system/bin/sh

# Проверка, есть ли поддержка в системе
if [ -f /vendor/lib64/soundfx/libvolumelistener.so ]; then
    # Подмена, если нужно
    mount -o bind /data/adb/modules/dolby-atmos-marble/vendor/lib64/soundfx/libdlbalm.so /vendor/lib64/soundfx/libdlbalm.so
fi
