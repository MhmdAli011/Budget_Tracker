# Budget Tracker

A simple Android app to track your daily income and expenses and get a clear view of your monthly budget.

> *Status:* Work in progress – basic budget tracking features are being built and improved over time.

---

## Features

- Add *income* and *expense* transactions
- Assign *categories* (e.g., Food, Travel, Bills, Shopping, Others)
- View *total balance, **total income, and **total expenses*
- See *daily / monthly* summaries (planned)
- Edit or delete existing transactions (planned)
- Fully *offline* – data stored locally on the device

You can update this list as you add more screens/features.

---

## Tech Stack

- *Platform:* Android (Native)
- *Language:* Kotlin
- *Build system:* Gradle (Kotlin DSL)
- *Minimum Android version:* (set your actual minSdk here)
- *Target Android version:* (set your actual targetSdk here)

Edit the min/target SDK in this section to match your build.gradle.kts.

---

## Project Structure

High-level structure of the project:

```text
Budget_Tracker/
├─ app/
│  ├─ src/
│  │  ├─ main/
│  │  │  ├─ java/
│  │  │  │  └─ ...           # Kotlin source files (activities/fragments/viewmodels)
│  │  │  ├─ res/             # drawables, colors, strings, etc.
│  │  │  └─ AndroidManifest.xml
│  ├─ build.gradle.kts
├─ build.gradle.kts
├─ settings.gradle.kts
└─ gradle/                    # Gradle wrapper files
