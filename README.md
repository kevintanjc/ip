# JavaBot

JavaBot is a simple command-line task manager written in Java. It supports creating, managing, and persisting different types of tasks such as **To-Dos**, **Deadlines**, and **Events**. The project is built with Gradle, tested with JUnit 5 + Mockito, and packaged with the Shadow plugin.

---

## Features

- **Task Management**
    - Add different types of tasks:
        - ToDos (`todo <description>`)
        - Deadlines (`deadline <description> /by <date>`)
        - Events (`event <description> /from <start> /to <end>`)
    - Mark/unmark tasks as done
    - Delete tasks by index
    - List all tasks in your checklist

- **Persistence**
    - Tasks are automatically saved to disk via `SavingService`
    - Tasks are reloaded at startup with `LoadingService`

- **Robust Error Handling**
    - Friendly messages for invalid input, missing indices, or bad date formats
    - Custom parsing with `DateTimeUtil` to handle date/time input

- **Testing**
    - Unit tests with JUnit 5
    - Mockito for mocking dependencies and verifying behavior
    - Coverage for services, storage, and core task logic

---

## Prerequisites

- **JDK 17**
- **Gradle 7+** (the wrapper is included)
- Recommended IDE: IntelliJ IDEA (latest version)

---

## Getting Started

### Run with Gradle
```bash
./gradlew run
```

### Build a fat JAR (Shadow plugin)
```bash
./gradlew shadowJar
```

### The runnable JAR will be in build/libs/javabot.jar:
```bash
java -jar build/libs/javabot.jar
```
