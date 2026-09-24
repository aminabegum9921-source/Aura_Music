# Build Aura Music APK using only your phone

1. Create/sign in to a GitHub account.
2. Create a new repository named `AuraMusic`.
3. Upload the **contents** of this project (not the ZIP itself).
4. Make sure `.github/workflows/build-apk.yml` is present.
5. Open the repository's **Actions** tab.
6. Select **Build Aura Music APK**.
7. Tap **Run workflow**.
8. Wait for the green checkmark.
9. Open that workflow run and scroll to **Artifacts**.
10. Download `Aura-Music-debug-apk`.
11. Extract the downloaded ZIP and install `app-debug.apk`.

The workflow builds the APK on GitHub's hosted Linux runner and uploads the APK as a workflow artifact.
