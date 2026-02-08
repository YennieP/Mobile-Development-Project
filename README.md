# NUMAD26SP

<div align="center">

![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-0095D5?style=for-the-badge&logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)

**Northeastern University Mobile Application Development Course**  
**Spring 2026**

[English](#english) | [中文](#中文)

</div>

---

<a name="english"></a>
## 🇺🇸 English Version

### About This Project

This is the course project for **NUMAD (Mobile Application Development)** at the University, Spring 2026. The application will be continuously developed throughout the semester, with new features added for each assignment.

### Current Features

- **Hello World Display**: Main screen with "Hello World!" text
- **About Me Button**: Displays developer name and email in a Toast message
- **Custom App Icon**: Launcher icon with initials "YP"
- **Modern UI**: Built with Jetpack Compose

### Technical Stack

| Category | Technology |
|----------|-----------|
| **Language** | Kotlin |
| **UI Framework** | Jetpack Compose |
| **IDE** | Android Studio |
| **Min SDK** | API 27 (Android 8.1 Oreo) |
| **Target SDK** | API 34 |
| **Build System** | Gradle (Groovy DSL) |

### Project Structure

```
NUMAD26SP_YanxiPan/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/edu/northeastern/numad26sp_yanxipan/
│   │   │   │   ├── MainActivity.kt
│   │   │   │   └── ui/theme/
│   │   │   ├── res/
│   │   │   │   ├── mipmap/         # App icons
│   │   │   │   ├── values/         # String resources
│   │   │   │   └── drawable/       # Image assets
│   │   │   └── AndroidManifest.xml
│   └── build.gradle
├── gradle/
└── build.gradle
```

### Prerequisites

- Android Studio Hedgehog (2023.1.1) or newer
- JDK 8 or higher
- Android SDK with API 27+

### Development Notes

This project uses **Jetpack Compose** for UI development, which is the modern declarative UI toolkit for Android. Key concepts include:

- **Composable Functions**: Building UI with composable functions
- **State Management**: Using `remember` and `mutableStateOf`
- **Material Design 3**: Following the latest Material Design guidelines
---

<a name="中文"></a>
## 🇨🇳 中文版本

### 项目简介

这是大学 2026 年春季学期**移动应用开发（NUMAD）**课程的项目。该应用将在整个学期中持续开发，每次作业都会添加新功能。

### 当前功能

- **Hello World 显示**: 主屏幕显示 "Hello World!" 文本
- **关于我按钮**: 点击按钮显示包含开发者姓名和邮箱的 Toast 消息
- **自定义应用图标**: 启动器图标包含首字母 "YP"
- **现代化界面**: 使用 Jetpack Compose 构建

### 技术栈

| 类别 | 技术 |
|------|------|
| **编程语言** | Kotlin |
| **UI框架** | Jetpack Compose |
| **开发工具** | Android Studio |
| **最低SDK** | API 27 (Android 8.1 Oreo) |
| **目标SDK** | API 34 |
| **构建系统** | Gradle (Groovy DSL) |

### 项目结构

```
NUMAD26SP_YanxiPan/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/edu/northeastern/numad26sp_yanxipan/
│   │   │   │   ├── MainActivity.kt        # 主活动
│   │   │   │   └── ui/theme/              # 主题配置
│   │   │   ├── res/
│   │   │   │   ├── mipmap/                # 应用图标
│   │   │   │   ├── values/                # 字符串资源
│   │   │   │   └── drawable/              # 图片资源
│   │   │   └── AndroidManifest.xml        # 应用清单
│   └── build.gradle                        # 应用级构建配置
├── gradle/                                 # Gradle wrapper
└── build.gradle                            # 项目级构建配置
```

### 环境要求

- Android Studio Hedgehog (2023.1.1) 或更新版本
- JDK 8 或更高版本
- Android SDK API 27+

### 开发笔记

本项目使用 **Jetpack Compose** 进行 UI 开发，这是 Android 的现代声明式 UI 工具包。主要概念包括：

- **Composable 函数**: 使用可组合函数构建 UI
- **状态管理**: 使用 `remember` 和 `mutableStateOf`
- **Material Design 3**: 遵循最新的 Material Design 设计指南

---

<div align="center">

**Made by Yanxi Pan**

</div>
