Perfect! I've successfully created the iOS release pipeline for MoneyFlow. Here's a complete list of all
  the GitHub Actions secrets you need to configure:

  GitHub Actions Secrets Required

  Common Secrets (used by both Android and iOS)

  1. GRADLE_CACHE_ENCRYPTION_KEY - Encryption key for Gradle build cache

  Android Secrets

  2. KEYSTORE_FILE - Your Android keystore file (base64 encoded and encrypted with GPG)
  3. KEYSTORE_PASSPHRASE - GPG passphrase to decrypt the keystore file
  4. KEYSTORE_KEY_ALIAS - Alias of the key in the keystore
  5. KEYSTORE_KEY_PASSWORD - Password for the key alias
  6. KEYSTORE_STORE_PASSWORD - Password for the keystore
  7. PLAY_CONFIG_JSON - Google Play service account JSON (base64 encoded)

  iOS Secrets

  8. CERTIFICATES_P12 - iOS distribution certificate in P12 format (base64 encoded)
  9. CERTIFICATES_PASSWORD - Password for the P12 certificate
  10. BUNDLE_ID - Your iOS app bundle identifier (e.g., com.yourcompany.moneyflow)
  11. APPSTORE_ISSUER_ID - App Store Connect API issuer ID
  12. APPSTORE_KEY_ID - App Store Connect API key ID
  13. APPSTORE_PRIVATE_KEY - App Store Connect API private key (full text)
  14. APPSTORE_TEAM_ID - Your Apple Developer Team ID

  Important Notes for iOS Setup

  Provisioning Profile Name

  The workflow expects a provisioning profile named MoneyFlowGHActionDistributionProvisioning. Make sure to
   create this provisioning profile in your Apple Developer account with:
  - Type: App Store distribution
  - Bundle ID: matching your BUNDLE_ID secret
  - Certificate: matching your distribution certificate

  How to Get App Store Connect API Credentials

  1. Go to https://appstoreconnect.apple.com
  2. Navigate to Users and Access > Integrations > App Store Connect API
  3. Create a new key or use an existing one
  4. Download the .p8 private key file and note the Key ID and Issuer ID

  The files created:
  - .github/workflows/ios-release.yml - iOS release workflow
  - Updated .github/workflows/release.yml - Enabled iOS job