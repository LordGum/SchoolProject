# 📚 School Project

Android-приложение для работы со списком задач (Todo), разработанное в рамках учебного проекта. Проект демонстрирует современный подход к разработке Android-приложений с использованием **Jetpack Compose**, **MVVM**, **Room**, **Retrofit**, **Dagger 2** и **Coroutines**.

---

## ✨ Основные возможности

* 📋 Просмотр списка задач
* ➕ Создание новых задач
* ✏️ Редактирование существующих задач
* 🗑️ Удаление задач
* 💾 Локальное хранение данных с использованием Room
* 🌐 Работа с удалённым API через Retrofit
* 🔐 Подготовленная инфраструктура для авторизации
* 🎨 Интерфейс, реализованный на Jetpack Compose

---

## 🛠️ Стек технологий

### UI

* Jetpack Compose
* Material 3
* Navigation Compose

### Архитектура

* MVVM
* Repository Pattern
* ViewModel
* Dependency Injection (Dagger 2)

### Работа с данными

* Room Database
* Retrofit
* Gson
* Kotlin Coroutines

### Дополнительно

* WorkManager
* Android Security Crypto
* Yandex Auth SDK

---

## 📂 Структура проекта

```
app/
├── data/               # Работа с API, БД и репозиториями
│   ├── database/
│   ├── network/
│   └── repository/
│
├── domain/             # Бизнес-логика
│
├── presentation/       # Экранные модели и Compose UI
│
├── di/                 # Dependency Injection
│
└── ui/                 # Тема и компоненты интерфейса
```

---

## 🚀 Запуск проекта

### Требования

* Android Studio Hedgehog или новее
* Android SDK 34
* JDK 17

### Сборка

```bash
git clone https://github.com/<username>/SchoolProject.git
cd SchoolProject
```

Откройте проект в Android Studio и запустите приложение на эмуляторе или физическом устройстве.

---

## 📖 Чему посвящён проект

Проект был создан в учебных целях для практики современных инструментов Android-разработки:

* построение многослойной архитектуры;
* работа с локальной базой данных;
* взаимодействие с REST API;
* использование Dependency Injection;
* разработка интерфейсов на Jetpack Compose.

---

## 📌 Используемые архитектурные принципы

* Clean Architecture (частично)
* MVVM
* Repository Pattern
* Single Responsibility Principle
* Dependency Injection

