#!/bin/bash

set -e

MODULE_DIR="misound-module"
OUTPUT_ZIP="misound-module.zip"

mkdir -p "$MODULE_DIR"/{system/priv-app/com.miui.misound,vendor/lib64/soundfx,system/etc/permissions,service.d,post-fs-data.d}

cp module.prop "$MODULE_DIR"/
cp service.d/dolby.sh "$MODULE_DIR"/service.d/
cp post-fs-data.d/dolby.sh "$MODULE_DIR"/post-fs-data.d/
cp LunarisDolby/privapp-permissions-dolby.xml "$MODULE_DIR"/system/etc/permissions/privapp-permissions-com.miui.misound.xml

cp assets/MiSound.apk "$MODULE_DIR"/system/priv-app/com.miui.misound/
cp assets/libs/*.so "$MODULE_DIR"/vendor/lib64/soundfx/

chmod 0755 "$MODULE_DIR"/service.d/dolby.sh
chmod 0755 "$MODULE_DIR"/post-fs-data.d/dolby.sh

cd "$MODULE_DIR"
zip -r "../$OUTPUT_ZIP" .

echo "Module built: $OUTPUT_ZIP"