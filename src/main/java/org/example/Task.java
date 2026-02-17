package org.example;

public class Task {

    private String title;
    private boolean completed;

    public Task(String title) {
        this.title = title;
        this.completed = false;
    }

    public Task(String title, boolean completed) {
        this.title = title;
        this.completed = completed;
    }

    public void toggle() {
        completed = !completed;
    }

    public String getTitle() {
        return title;
    }

    public boolean isCompleted() {
        return completed;
    }

    @Override
    public String toString() {
        return title + " [" + (completed ? "Done" : "Not Done") + "]";
    }
}
