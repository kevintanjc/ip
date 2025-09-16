# JavaBot 🤖

> “Your mind is for having ideas, not holding them.” – David Allen  
> (from *Getting Things Done*)

JavaBot is a **task manager** written in Java. It helps you manage your tasks efficiently with a mix of command-line and JavaFX UI support. Built with Gradle, tested with JUnit 5 + Mockito, and packaged neatly with the Shadow plugin.

---

## ✨ Features

1. **Task Management**
    - Add different types of tasks:
      - ToDos (`todo <description>`)
      - Deadlines (`deadline <description> /by <date>`)
      - Events (`event <description> /from <start> /to <end>`)
    - Mark/unmark tasks as done
    - Delete tasks by index
    - List all tasks in your checklist

2. **Persistence**
    - Tasks are automatically saved to disk via `SavingService`
    - Tasks are reloaded at startup with `LoadingService`

3. **Robust Error Handling**
    - Friendly messages for invalid input, missing indices, or bad date formats
    - Custom parsing with `DateTimeUtil` to handle date/time input

4. **Dark Mode UI**
    - Sleek JavaFX interface with customizable CSS styles
    - Bubble-style dialogs for user and bot
    - Scrollable conversation history with styled scrollbar

5. **Testing**
    - Unit tests with JUnit 5
    - Mockito for mocking dependencies and verifying behavior
    - Coverage for services, storage, and core task logic

---

## 🛠 Prerequisites

- **JDK 17**
- **Gradle 7+** (wrapper included)
- Recommended IDE: *IntelliJ IDEA (latest)*

---

## 🚀 Getting Started

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

Want to know where I learnt to style? Look into [JavaFX CSS Reference](https://github.com/se-edu/javafx-tutorial)

### Use of AI-assisted work
This project has been developed with the assistance of GitHub Copilot to enhance productivity and code quality.
All code generated or suggested by these AI tools has been reviewed and modified as necessary to ensure it meets 
the project's standards and requirements.
 
Usage of Copilot for areas in: 
- javadocs
- resolving unfamiliar syntax issues
