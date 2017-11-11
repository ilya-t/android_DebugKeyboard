set -e
./gradlew assembleDebug
adb install -r app/build/outputs/apk/debug/app-debug.apk
#adb shell am start -a android.settings.INPUT_METHOD_SETTINGS
