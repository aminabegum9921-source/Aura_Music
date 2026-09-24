# Aura Music — Android starter

A polished, ad-free music-player UI built with Kotlin + Jetpack Compose.

## Included
- Modern dark UI
- Home / Search / Downloads / Liked tabs
- Player controls
- Offline-downloads section
- Media3-ready dependency for real playback
- No advertising SDKs

## Important download rule
The app should download only music you are authorized to download (for example, your own files or tracks from a service/API that grants downloads). Do not add a feature that rips copyrighted music from YouTube or other services without permission.

## Build
Open this folder in Android Studio, let Gradle sync, then run on an Android 8+ device.

Next production steps:
1. Add MediaStore scanning for local audio.
2. Add Media3 ExoPlayer service + notification controls.
3. Add a DownloadManager-based downloader for authorized direct audio URLs.
4. Persist playlists/likes with Room.
5. Add album art and metadata parsing.
