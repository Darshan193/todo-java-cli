package org.example;

import java.util.Scanner;

public class Main {

    // Safe integer input: never crashes on bad input and enforces a range.
    public static int readInt(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);

            if (!scanner.hasNextInt()) {
                System.out.println("Please enter a valid whole number.");
                scanner.nextLine(); // discard bad input
                continue;
            }

            int value = scanner.nextInt();
            scanner.nextLine(); // clear newline

            if (value < min || value > max) {
                System.out.println("Number must be between " + min + " and " + max + ".");
                continue;
            }

            return value;
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        TaskManager manager = new TaskManager();

        String filename = "tasks.txt";

        // Auto-load on startup
        try {
            manager.loadFromFile(filename);
            if (!manager.isEmpty()) {
                System.out.println("Loaded tasks from " + filename);
            }
        } catch (Exception e) {
            System.out.println("Could not load tasks: " + e.getMessage());
        }

        while (true) {

            System.out.println("\n1. Add Task");
            System.out.println("2. View Tasks");
            System.out.println("3. Toggle Task Done/Not Done");
            System.out.println("4. Delete Task");
            System.out.println("5. Save");
            System.out.println("6. Load");
            System.out.println("7. Exit");

            int choice = readInt(scanner, "Choose: ", 1, 7);

            if (choice == 1) {
                System.out.print("Enter new task: ");
                String newTask = scanner.nextLine().trim();

                if (newTask.isEmpty()) {
                    System.out.println("Task title cannot be empty.");
                    continue;
                }

                manager.addTask(newTask);
                System.out.println("New task added.");
            }
            else if (choice == 2) {
                if (manager.isEmpty()) {
                    System.out.println("No tasks yet.");
                } else {
                    manager.printTasks();
                }
            }
            else if (choice == 3) {
                if (manager.isEmpty()) {
                    System.out.println("No tasks yet.");
                    continue;
                }

                System.out.println("Which task number to toggle?");
                manager.printTasks();

                int num = readInt(scanner, "Choose Task Number: ", 1, manager.size());

                boolean ok = manager.toggleTask(num);
                if (!ok) {
                    System.out.println("Invalid input.");
                } else {
                    System.out.println("Task updated.");
                }
            }
            else if (choice == 4) {
                if (manager.isEmpty()) {
                    System.out.println("No tasks yet.");
                    continue;
                }

                System.out.println("Which task number to delete?");
                manager.printTasks();

                int num = readInt(scanner, "Choose Task Number: ", 1, manager.size());

                Task removed = manager.deleteTask(num);
                if (removed == null) {
                    System.out.println("Invalid input.");
                } else {
                    System.out.println("Deleted: " + removed);
                }
            }
            else if (choice == 5) {
                try {
                    manager.saveToFile(filename);
                    System.out.println("Saved to " + filename);
                } catch (Exception e) {
                    System.out.println("Save failed: " + e.getMessage());
                }
            }
            else if (choice == 6) {
                try {
                    manager.loadFromFile(filename);
                    System.out.println("Loaded from " + filename);
                } catch (Exception e) {
                    System.out.println("Load failed: " + e.getMessage());
                }
            }
            else if (choice == 7) {
                System.out.println("Goodbye!");
                break;
            }
        }

        scanner.close();
    }
}
