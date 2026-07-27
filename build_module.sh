#!/bin/bash

set -e

MODULE_DIR="dolby-atmos-marble"
OUTPUT_ZIP="dolby-atmos-marble.zip"

# Создание директории модуля
mkdir -p "$MODULE_DIR"/{system/priv-app/DaxAppService,vendor/lib64/soundfx,system/etc/permissions,service.d,post-fs-data.d}

# Копирование файлов
cp module.prop "$MODULE_DIR"/
cp service.d/dolby.sh "$MODULE_DIR"/service.d/
cp post-fs-data.d/dolby.sh "$MODULE_DIR"/post-fs-data.d/
cp system/etc/permissions/privapp-permissions-com.dolby.daxservice.xml "$MODULE_DIR"/system/etc/permissions/

cp assets/DaxAppService.apk "$MODULE_DIR"/system/priv-app/DaxAppService/
cp assets/libs/*.so "$MODULE_DIR"/vendor/lib64/soundfx/

# Установка прав
chmod 0755 "$MODULE_DIR"/service.d/dolby.sh
chmod 0755 "$MODULE_DIR"/post-fs-data.d/dolby.sh

# Архивирование
cd "$MODULE_DIR"
zip -r "../$OUTPUT_ZIP" .

echo "Module built: $OUTPUT_ZIP"
