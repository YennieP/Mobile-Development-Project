# NUMAD26SP

![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-0095D5?style=for-the-badge&logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)

**Northeastern University Mobile Application Development Course | Spring 2026**

[English](#english) | [中文](#中文)

---

<a name="english"></a>
## English Version

### About This Project

This is the course project for **NUMAD (Mobile Application Development)** at Northeastern University, Spring 2026. The application is continuously developed throughout the semester, with new features added for each assignment.

### Current Features

#### Assignment 1 — Hello World
- **Hello World Display**: Main screen with "Hello World!" text
- **Custom App Icon**: Launcher icon with initials "YP"
- **Modern UI**: Built with Jetpack Compose

#### Assignment 3 — Quic Calc
- **About Me Button**: Launches a dedicated screen showing developer information
- **Quic Calc Button**: Launches a calculator activity
- **Calculator**: 14-button layout (0–9, +, −, =, x) using a single ConstraintLayout
- **Expression Evaluation**: Supports addition and subtraction with left-to-right evaluation
- **Delete**: The x button removes the last character
- **Orientation Support**: Layout adapts to both portrait and landscape

#### Assignment 5 — Prime Directive
- **Prime Directive Button**: Launches a prime number search activity
- **Find Primes**: Starts a worker thread searching for prime numbers beginning at 3, incrementing by 2
- **Terminate Search**: Stops the search, last displayed values remain on screen
- **Pacifier Switch**: Checkbox to verify the main thread is not blocked during search
- **Rotation Support**: Search continues running across screen rotation using `onSaveInstanceState()`
- **Back Button Confirmation**: If search is running, pressing back shows a confirmation dialog before exiting
- Written in **Java** using raw `Thread` and `Handler`

#### Assignment 4 — Contacts Collector
- **Contacts Collector Button**: Launches a contacts management activity
- **RecyclerView List**: Displays all contacts with name and phone number
- **FAB Add Contact**: Floating action button opens a dialog to add a new contact
- **Tap to Call**: Tapping a contact opens the system dialer with their number
- **Edit Contact**: Modify existing contact name and phone number
- **Delete Contact**: Remove contacts with a confirmation dialog
- **Snackbar Feedback**: Confirms successful or unsuccessful contact creation with an Undo action

### Technical Stack

| Category | Technology |
|----------|-----------|
| **Language** | Kotlin |
| **UI Framework** | Jetpack Compose (Main) + XML Views (Activities) |
| **IDE** | Android Studio |
| **Min SDK** | API 27 (Android 8.1 Oreo) |
| **Target SDK** | API 36 |
| **Build System** | Gradle (Groovy DSL) |

### Project Structure

```
app/src/main/
├── java/edu/northeastern/numad26sp_yanxipan/
│   ├── ui/theme/
│   ├── MainActivity.kt          # Main screen with all navigation buttons
│   ├── AboutMeActivity.kt       # Developer information screen
│   ├── QuicCalcActivity.kt      # Calculator
│   └── ContactsActivity.kt      # Contacts list and adapter
├── res/layout/
│   ├── activity_main.xml
│   ├── activity_quic_calc.xml
│   ├── activity_about_me.xml
│   ├── activity_contacts.xml
│   ├── item_contact.xml
│   └── dialog_contact.xml
└── AndroidManifest.xml
```

### Prerequisites

- Android Studio Hedgehog (2023.1.1) or newer
- JDK 8 or higher
- Android SDK with API 27+

### Assignment Progress

- [x] Assignment 1: Hello World App with custom icon
- [x] Assignment 3: Quic Calc + About Me Activity
- [x] Assignment 4: Contacts Collector with RecyclerView

### Development Notes

This project uses **Jetpack Compose** for the main screen and traditional **XML Views** for feature activities. Key concepts include:

- **Composable Functions**: Building UI with composable functions
- **State Management**: Using `remember` and `mutableStateOf`
- **Material Design 3**: Following the latest Material Design guidelines
- **ConstraintLayout**: Single layout supporting both orientations
- **RecyclerView**: Efficient list rendering with a custom adapter

---

<a name="中文"></a>
## 中文版本

### 项目简介

这是东北大学 2026 年春季学期**移动应用开发（NUMAD）**课程的项目。该应用在整个学期中持续开发，每次作业都会添加新功能。

### 当前功能

#### 作业1 — Hello World
- **Hello World 显示**: 主屏幕显示 "Hello World!" 文本
- **自定义应用图标**: 启动器图标包含首字母 "YP"
- **现代化界面**: 使用 Jetpack Compose 构建

#### 作业3 — Quic Calc
- **About Me 按钮**: 跳转到专属页面显示开发者信息
- **Quic Calc 按钮**: 跳转到计算器页面
- **计算器**: 使用单一 ConstraintLayout 排布 14 个按钮（0–9、+、−、=、x）
- **表达式求值**: 支持加减运算，从左到右计算
- **删除功能**: x 按钮删除最后一个字符
- **竖横屏适配**: 布局在两个方向均正常显示

#### 作业5 — Prime Directive
- **Prime Directive 按钮**: 跳转到质数搜索页面
- **Find Primes**: 启动工作线程从 3 开始每次 +2 搜索质数
- **Terminate Search**: 停止搜索，最后显示的数值保留在屏幕上
- **Pacifier Switch**: 复选框，用于验证搜索运行时主线程未被阻塞
- **旋转适配**: 使用 `onSaveInstanceState()` 保证旋转屏幕后搜索继续运行
- **返回键确认**: 搜索运行时按返回键弹出确认框
- 使用 **Java** 编写，使用原生 `Thread` 和 `Handler`

#### 作业4 — Contacts Collector
- **Contacts Collector 按钮**: 跳转联系人管理页面
- **RecyclerView 列表**: 显示所有联系人的姓名和电话
- **FAB 添加联系人**: 浮动按钮打开对话框输入姓名和电话
- **点击拨号**: 点击联系人打开系统拨号盘
- **编辑联系人**: 修改已有联系人的姓名和电话
- **删除联系人**: 带确认弹窗的删除功能
- **Snackbar 反馈**: 添加成功或失败的提示，含撤销操作

### 技术栈

| 类别 | 技术 |
|------|------|
| **编程语言** | Kotlin |
| **UI框架** | Jetpack Compose（主界面）+ XML Views（各 Activity）|
| **开发工具** | Android Studio |
| **最低SDK** | API 27 (Android 8.1 Oreo) |
| **目标SDK** | API 36 |
| **构建系统** | Gradle (Groovy DSL) |

### 作业进度

- [x] 作业1: Hello World 应用和自定义图标
- [x] 作业3: Quic Calc + About Me Activity
- [x] 作业4: Contacts Collector + RecyclerView

### 开发笔记

本项目主界面使用 **Jetpack Compose**，功能页面使用传统 **XML Views**。主要概念包括：

- **Composable 函数**: 使用可组合函数构建 UI
- **状态管理**: 使用 `remember` 和 `mutableStateOf`
- **Material Design 3**: 遵循最新的 Material Design 设计指南
- **ConstraintLayout**: 单一布局支持竖横屏两种方向
- **RecyclerView**: 使用自定义 Adapter 实现高效列表渲染

---

**Made by Yanxi Pan**