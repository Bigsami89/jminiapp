# Todo App Example

A simple task management application demonstrating the JMiniApp framework.

## Overview

This example shows how to create a functional Todo application using JMiniApp core. It demonstrates list management, object serialization, and automatic data persistence.

## Features

- **List Tasks**: View all current tasks
- **Add Task**: Create new tasks with descriptions
- **Complete Task**: Mark tasks as done
- **Remove Task**: Delete tasks from the list
- **Auto-Save**: Automatically saves data to JSON on exit
- **Auto-Load**: Automatically loads data from JSON on startup

## Project Structure

```
todo/
├── pom.xml
├── README.md
└── src/main/java/com/jminiapp/examples/todo/
    ├── TodoApp.java     # Main application class
    └── TodoItem.java    # Data model
```

## Key Components

### TodoItem
A serializable model class representing a task:
- `id`: Unique identifier
- `description`: Task details
- `completed`: Status flag

### TodoApp
The main application class extending `JMiniApp`:
- **Initialization**: Loads existing tasks from `TodoApp.json` using `context.importData("json")`.
- **Runtime**: Manages the interactive menu loop.
- **Shutdown**: Saves current state to `TodoApp.json` using `context.exportData("json")`.
- **Configuration**: Registers the `JSONAdapter` for `TodoItem` class.

## Building and Running

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Build the project
From the project root:
```bash
mvn clean install
```

### Run the application
From the `examples/todo` directory:
```bash
mvn exec:java
```

## Usage Example

```
=== Todo App ===
Welcome to your Task Manager!
Loaded 2 tasks.

--- Menu ---
1. List Tasks
2. Add Task
3. Complete Task
4. Remove Task
5. Exit

Choose an option: 1

--- Task List ---
[ ] 1. Buy milk
[X] 2. Walk the dog
```

## Author
Implemented by Antigravity (AI Assistant)
