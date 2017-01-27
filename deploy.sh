set -e
./gradlew assembleDebug
adb install -r app/build/outputs/apk/app-debug.apk
#adb shell am start -a android.settings.INPUT_METHOD_SETTINGS
