#!/bin/bash
set -ex

export ANDROID_SDK_ROOT="/root/android-sdk"

apt-get update && apt-get install -y expect

mkdir -p "$ANDROID_SDK_ROOT/licenses"
echo "8933bad161af4178b1185d1a37fbf41ea5269c55" > "$ANDROID_SDK_ROOT/licenses/android-sdk-license"

mkdir -p "$ANDROID_SDK_ROOT/cmdline-tools"
cd "$ANDROID_SDK_ROOT/cmdline-tools"
curl -sSL -o tools.zip https://dl.google.com/android/repository/commandlinetools-linux-11076708_latest.zip
unzip -q tools.zip
rm tools.zip

mkdir -p latest
mv cmdline-tools/* latest/

export PATH="$ANDROID_SDK_ROOT/cmdline-tools/latest/bin:$ANDROID_SDK_ROOT/platform-tools:$PATH"

yes | "$ANDROID_SDK_ROOT/cmdline-tools/latest/bin/sdkmanager" --licenses > /dev/null

expect <<EOF
spawn sdkmanager --sdk_root="$ANDROID_SDK_ROOT" --no_https \
  "platform-tools" \
  "platforms;android-35" \
  "build-tools;35.0.0"
expect {
    "Accept? (y/N):" { send "y\r"; exp_continue }
    eof
}
EOF

gradle help --no-daemon
