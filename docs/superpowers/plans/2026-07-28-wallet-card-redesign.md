# Wallet Card Redesign & Bug Fix Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Redesign `WalletConnectionCard` in `DonateScreen.kt` into a modern Web3 digital card with app theme colors (`MaterialTheme.colorScheme`), truncated Solana public key display, full-address copy-to-clipboard, and prominent balance display.

**Architecture:** Refactor `WalletConnectionCard` composable in `DonateScreen.kt`. Use `LocalClipboardManager` to copy `state.fullAddress` (preventing truncated address copy bug), `MaterialTheme.colorScheme` tokens for dynamic light/dark mode styling, and standard Compose layout primitives (`Column`, `Row`, `Surface`, `Card`, `IconButton`).

**Tech Stack:** Kotlin, Jetpack Compose, Material3 (`androidx.compose.material3`), Solana Mobile Wallet Adapter.

## Global Constraints
- Theme: Strictly use `MaterialTheme.colorScheme` tokens (no hardcoded custom colors).
- Language: English default UI text.
- Component Location: [DonateScreen.kt](file:///c:/Users/PopGore/.gemini/antigravity-ide/scratch/Quran/app/src/main/java/com/quran/app/compose/screens/DonateScreen.kt)
- Bug Prevention: Ensure clipboard receives `state.fullAddress` (not truncated `state.address`).

---

### Task 1: Redesign `WalletConnectionCard` Composable & Fix Copy Bug

**Files:**
- Modify: [DonateScreen.kt](file:///c:/Users/PopGore/.gemini/antigravity-ide/scratch/Quran/app/src/main/java/com/quran/app/compose/screens/DonateScreen.kt):153-258
- Modify: [DonateViewModel.kt](file:///c:/Users/PopGore/.gemini/antigravity-ide/scratch/Quran/app/src/main/java/com/quran/app/viewModels/DonateViewModel.kt):434-437

**Interfaces:**
- Consumes: `WalletState.Connected`, `DonateViewModel`, `ActivityResultSender`
- Produces: Redesigned `WalletConnectionCard` UI & bug-free address copying

- [ ] **Step 1: Inspect `DonateScreen.kt` lines 153-258 and `DonateViewModel.kt` lines 434-437**

- [ ] **Step 2: Update `WalletConnectionCard` implementation in `DonateScreen.kt`**
  - Implement top row with network status badge (chip) + disconnect text button (`MaterialTheme.colorScheme.error`).
  - Implement middle balance section with `Wallet Balance` label and prominent balance text (`headlineMedium`).
  - Implement bottom address pill with formatted truncated address (`state.fullAddress.take(6) + "..." + state.fullAddress.takeLast(6)`).
  - Implement copy-to-clipboard action using `LocalClipboardManager` with `state.fullAddress` + Toast notification `"Address copied to clipboard"`.

- [ ] **Step 3: Fix locale formatting in `DonateViewModel.kt`**
  - Update `lamportsToSol` to use `String.format(java.util.Locale.US, "%.4f", sol)`.

- [ ] **Step 4: Verify build compilation**
  Run: `powershell -Command "cd c:/Users/PopGore/.gemini/antigravity-ide/scratch/Quran; ./gradlew assembleDebug"` or verify syntax cleanly.
