DRC Sim Client
---

Release: [![Build Status](https://travis-ci.org/rolandoislas/drc-sim-client.svg?branch=master)](https://travis-ci.org/rolandoislas/drc-sim-client)
Dev: [![Build Status](https://travis-ci.org/rolandoislas/drc-sim-client.svg?branch=develop)](https://travis-ci.org/rolandoislas/drc-sim-client)

A desktop and mobile DRC Sim \(gamepad\) client.

[Video]

# Requirements

The server backend, [DRC Sim Server], is required.It handles the pairing 
and communication with the Wii U then creates a server for the client.

# Releases

- [Desktop] \(Windows/OS X/Linux/Java enabled Toaster\)
- [Android]
- iOS - It will need to be built from source.

# Building

DRC Sim Server uses a Gradle build system.

## Desktop

`./gradlew desktop:dist`

Output: `./desktop/build/libs/desktop-<version>.jar`

## Android

`./gradlew android:assembleRelease`

Output: `./android/build/outputs/apk/android-release-unsigned.apk`

## iOS

`./gradlew ios:createIPA`

Output: `./ios/build/robovm/IOSLauncher.ipa`

# Credits

[drc-sim] \(original\) by [memahaxx]
- The original Python codebase

[drc-sim-keyboard] by justjake
- The readme that got me set up initially

# Additional Software

[libGDX] Java game development framework

[RoboVM] Java bytecode to machine code compiler

[Guava] Google Core Libraries for Java



[drc-sim]: https://bitbucket.org/memahaxx/drc-sim
[drc-sim-keyboard]: https://github.com/justjake/drc-sim-keyboard
[libgdx]: https://libgdx.badlogicgames.com
[Desktop]: https://github.com/rolandoislas/drc-sim-client/releases
[Android]: https://play.google.com/store/apps/details?id=com.rolandoislas.drcsimclient
[DRC Sim Server]: https://github.com/rolandoislas/drc-sim
[Video]: https://www.youtube.com/watch?v=659kirZkmxg
[libGDX]: http://libgdx.badlogicgames.com/
[RoboVM]: https://robovm.com/
[Guava]: https://github.com/google/guava
