# Wallet Card Redesign Specification

## Overview
Redesign the `WalletConnectionCard` component in `DonateScreen.kt` for the Solana wallet connection UI. The current design suffers from layout overflow when displaying raw 44-character Base58 Solana public key strings and lacks visual structure, balance hierarchy, and copy-to-clipboard functionality.

## Goals
- Provide a modern Web3 digital wallet card experience.
- Strictly adhere to `MaterialTheme.colorScheme` tokens without hardcoded custom colors.
- Use default English UI labels consistent with the rest of the application.
- Format long wallet public keys securely into compact pills (`4zMMCr...f123`) with a one-click copy to clipboard action.
- Highlight the user's SOL balance prominently with clear typography hierarchy.

## Design Details

### 1. Color Palette & Theme Tokens (`MaterialTheme.colorScheme`)
- **Card Container**: `MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)` with `shape = RoundedCornerShape(20.dp)` and `BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f))`.
- **Connected Status Badge**:
  - Container: `MaterialTheme.colorScheme.primaryContainer`
  - Text Color: `MaterialTheme.colorScheme.onPrimaryContainer`
  - Indicator Dot: `MaterialTheme.colorScheme.primary`
- **Balance Display**: `MaterialTheme.colorScheme.onSurface` (`headlineMedium`, `FontWeight.Bold`).
- **Address Pill Box**:
  - Container: `MaterialTheme.colorScheme.surface`
  - Border: `BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))`
  - Text Color: `MaterialTheme.colorScheme.onSurfaceVariant`
- **Disconnect Action**: `MaterialTheme.colorScheme.error`

### 2. Layout Hierarchy (Connected State)

```
+-------------------------------------------------------------+
|  [🟢 Connected (Devnet)]                     [Disconnect]   |  <- Top Row
|                                                             |
|  Wallet Balance                                             |  <- Subcaption
|  1.4500 SOL                                                 |  <- Prominent Balance
|                                                             |
|  +-------------------------------------------------------+  |
|  |  4zMMCr...f123                                 [📋]   |  |  <- Address Chip + Copy
|  +-------------------------------------------------------+  |
+-------------------------------------------------------------+
```

### 3. Copy Address Interaction
- Uses Jetpack Compose `LocalClipboardManager.current`.
- Displays a `Toast` notification: `"Address copied to clipboard"`.

### 4. Code Location
- Modified Component: `WalletConnectionCard` in [DonateScreen.kt](file:///c:/Users/PopGore/.gemini/antigravity-ide/scratch/Quran/app/src/main/java/com/quran/app/compose/screens/DonateScreen.kt).
