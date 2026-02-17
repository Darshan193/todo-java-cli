package org.example;
import java.util.ArrayList;
import java.io.*;

public class TaskManager {

    private final ArrayList<Task> tasks = new ArrayList<>();

    public void addTask(String title) {
        tasks.add(new Task(title));
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    public void printTasks() {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }

    public boolean toggleTask(int taskNumber) {
        if (taskNumber < 1 || taskNumber > tasks.size()) return false;
        tasks.get(taskNumber - 1).toggle();
        return true;
    }

    public Task deleteTask(int taskNumber) {
        if (taskNumber < 1 || taskNumber > tasks.size()) return null;
        return tasks.remove(taskNumber - 1);
    }

    public int size() {
        return tasks.size();
    }

    // ====== NEW: Save / Load ======

    public void saveToFile(String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Task t : tasks) {
                // Format: completed<TAB>title
                writer.write(t.isCompleted() + "\t" + t.getTitle());
                writer.newLine();
            }
        }
    }

    public void loadFromFile(String filename) throws IOException {
        tasks.clear();

        File file = new File(filename);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t", 2);
                if (parts.length != 2) continue;

                boolean completed = Boolean.parseBoolean(parts[0]);
                String title = parts[1];

                tasks.add(new Task(title, completed));
            }
        }
    }
}
