# KMM Realm Employee Management

A cross-platform Employee Management app built with Kotlin Multiplatform Mobile, Realm Database, and Jetpack Compose.

## ✨ Features

- **Cross-Platform**: Shared business logic between Android and iOS
- **Employee CRUD**: Add, edit, delete, and search employees
- **Real-time Search**: Instant filtering and department-based sorting
- **Offline-First**: Local Realm database with reactive queries

## 🛠️ Tech Stack

- **Kotlin Multiplatform Mobile** - Shared business logic
- **Realm Kotlin** - Local database with reactive queries
- **Jetpack Compose** - Modern UI framework (shared on both platforms)
- **Kotlin Coroutines & Flow** - Asynchronous programming

## 🚀 Quick Start

### Android
```bash
./gradlew :androidApp:installDebug
```

### iOS
1. Open `iosApp/iosApp.xcodeproj` in Xcode
2. Build and run

## 📁 Project Structure

```
shared/
├── commonMain/          # Shared business logic
│   ├── data/model/      # Employee data model
│   ├── data/repository/ # Repository pattern
│   └── presentation/    # ViewModels & Compose UI
├── androidMain/         # Android-specific code
└── iosMain/            # iOS-specific code
```

## 💾 Data Model

```kotlin
class Employee : RealmObject {
    @PrimaryKey var _id: ObjectId = ObjectId()
    var firstName: String = ""
    var lastName: String = ""
    var email: String = ""
    var department: String = ""
    var position: String = ""
    var salary: Double = 0.0
    // ... more fields
}
```

## 🔧 Requirements

- **Android Studio** Arctic Fox+
- **Xcode** 14.0+ (for iOS)
- **JDK** 11+

---

Built with ❤️ using Kotlin Multiplatform Mobile
