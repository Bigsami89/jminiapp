package com.jminiapp.examples.todo;

import java.io.Serializable;

/**
 * Represents a single task in the Todo list.
 */
public class TodoItem implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String description;
    private boolean completed;

    public TodoItem() {
    }

    public TodoItem(int id, String description) {
        this.id = id;
        this.description = description;
        this.completed = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return String.format("[%s] %d. %s", (completed ? "X" : " "), id, description);
    }
}
