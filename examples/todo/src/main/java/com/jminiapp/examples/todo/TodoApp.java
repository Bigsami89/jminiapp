package com.jminiapp.examples.todo;

import com.jminiapp.core.api.JMiniApp;
import com.jminiapp.core.api.JMiniAppConfig;
import com.jminiapp.core.engine.JMiniAppRunner;
import com.jminiapp.core.adapters.JSONAdapter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * A simple Todo application demonstrating the JMiniApp framework.
 */
public class TodoApp extends JMiniApp {
    private Scanner scanner;
    private List<TodoItem> todoList;
    private boolean running;

    public TodoApp(JMiniAppConfig config) {
        super(config);
    }

    @Override
    protected void initialize() {
        System.out.println("\n=== Todo App ===");
        System.out.println("Welcome to your Task Manager!");

        scanner = new Scanner(System.in);
        running = true;

        // Load existing data
        try {
            context.importData("json");
            List<TodoItem> data = context.getData();
            if (data != null) {
                todoList = new ArrayList<>(data);
                System.out.println("Loaded " + todoList.size() + " tasks.");
            } else {
                todoList = new ArrayList<>();
                System.out.println("Starting with an empty list.");
            }
        } catch (Exception e) {
            System.out.println("Starting with new list (No previous data found: " + e.getMessage() + ")");
            todoList = new ArrayList<>();
        }
    }

    @Override
    protected void run() {
        while (running) {
            displayMenu();
            handleUserInput();
        }
    }

    @Override
    protected void shutdown() {
        // Save data
        context.setData(todoList);

        // Auto-export to JSON on exit for convenience
        try {
            context.exportData("json");
            System.out.println("Data saved to TodoApp.json");
        } catch (Exception e) {
            System.out.println("Failed to auto-save data: " + e.getMessage());
        }

        scanner.close();
        System.out.println("Goodbye!");
    }

    private void displayMenu() {
        System.out.println("\n--- Menu ---");
        System.out.println("1. List Tasks");
        System.out.println("2. Add Task");
        System.out.println("3. Complete Task");
        System.out.println("4. Remove Task");
        System.out.println("5. Exit");
        System.out.print("\nChoose an option: ");
    }

    private void handleUserInput() {
        try {
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    listTasks();
                    break;
                case "2":
                    addTask();
                    break;
                case "3":
                    completeTask();
                    break;
                case "4":
                    removeTask();
                    break;
                case "5":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void listTasks() {
        if (todoList.isEmpty()) {
            System.out.println("\nNo tasks found.");
        } else {
            System.out.println("\n--- Task List ---");
            for (TodoItem item : todoList) {
                System.out.println(item);
            }
        }
    }

    private void addTask() {
        System.out.print("Enter task description: ");
        String description = scanner.nextLine().trim();
        if (!description.isEmpty()) {
            int newId = todoList.stream()
                    .mapToInt(TodoItem::getId)
                    .max()
                    .orElse(0) + 1;

            TodoItem newItem = new TodoItem(newId, description);
            todoList.add(newItem);
            System.out.println("Task added: " + newItem);
        } else {
            System.out.println("Description cannot be empty.");
        }
    }

    private void completeTask() {
        listTasks();
        if (todoList.isEmpty())
            return;

        System.out.print("Enter task ID to complete: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            boolean found = false;
            for (TodoItem item : todoList) {
                if (item.getId() == id) {
                    item.setCompleted(true);
                    System.out.println("Task marked as completed: " + item);
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Task not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID.");
        }
    }

    private void removeTask() {
        listTasks();
        if (todoList.isEmpty())
            return;

        System.out.print("Enter task ID to remove: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            boolean removed = todoList.removeIf(item -> item.getId() == id);
            if (removed) {
                System.out.println("Task removed.");
            } else {
                System.out.println("Task not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID.");
        }
    }

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
}
