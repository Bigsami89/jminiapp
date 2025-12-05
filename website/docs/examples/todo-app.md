# Build a Todo App

This tutorial guides you through building a simple Todo application using the JMiniApp framework.

## Prerequisites

- Java 17+
- Maven 3.6+
- Basic knowledge of Java

## Step 1: Create Project Structure

Create a new Maven module or project with the following structure:

```
todo-app/
├── pom.xml
└── src/main/java/com/example/todo/
    ├── TodoApp.java
    └── TodoItem.java
```

## Step 2: Configure Dependencies

Add the `jminiapp-core` dependency to your `pom.xml`:

```xml
<dependencies>
    <dependency>
        <groupId>com.jminiapp</groupId>
        <artifactId>jminiapp-core</artifactId>
        <version>1.0.0-SNAPSHOT</version>
    </dependency>
</dependencies>
```

## Step 3: Create the Data Model

Create `TodoItem.java` to represent a task. It must be `Serializable` or compatible with your chosen adapter.

```java
public class TodoItem {
    private int id;
    private String description;
    private boolean completed;

    // Constructors, Getters, Setters...
}
```

## Step 4: Implement the Application

Create `TodoApp.java` extending `JMiniApp`.

### 1. Initialize
Load existing data when the app starts.

```java
@Override
protected void initialize() {
    try {
        context.importData("json");
        List<TodoItem> data = context.getData();
        // Initialize your list with data
    } catch (Exception e) {
        // Handle first run (no file)
    }
}
```

### 2. Run Loop
Implement the main menu loop.

```java
@Override
protected void run() {
    while (running) {
        // Show menu
        // Handle input (Add, List, Complete, Remove)
    }
}
```

### 3. Shutdown
Save data before exiting.

```java
@Override
protected void shutdown() {
    context.setData(todoList);
    context.exportData("json");
}
```

## Step 5: Bootstrap the App

In your `main` method, use `JMiniAppRunner` to configure and launch the app. **Don't forget to register the JSONAdapter!**

```java
public static void main(String[] args) {
    JMiniAppRunner.forApp(TodoApp.class)
            .withState(TodoItem.class)
            .withAdapters(new JSONAdapter<TodoItem>() {
                @Override
                public Class<TodoItem> getstateClass() {
                    return TodoItem.class;
                }
            })
            .run(args);
}
```

## Running the App

Build and run your application:

```bash
mvn clean install
mvn exec:java
```

You now have a fully functional Todo App with automatic data persistence!
