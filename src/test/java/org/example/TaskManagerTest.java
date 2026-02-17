package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class TaskManagerTest {

    @Test
    void toggleValidTaskReturnsTrue() {
        TaskManager manager = new TaskManager();
        manager.addTask("Homework");

        boolean result = manager.toggleTask(1);

        assertTrue(result);
    }

    @Test
    void toggleInvalidTaskReturnsFalseAndDoesNothing() {
        TaskManager manager = new TaskManager();
        manager.addTask("Homework");

        boolean result = manager.toggleTask(99);

        assertFalse(result);
        assertEquals(1, manager.size()); // still 1 task
    }

    @Test
    void togglingChangesSavedCompletionValue(@TempDir Path tempDir) throws Exception {
        TaskManager manager = new TaskManager();
        manager.addTask("Homework");

        manager.toggleTask(1);

        Path file = tempDir.resolve("tasks.txt");
        manager.saveToFile(file.toString());

        String saved = Files.readString(file);
        // Expected format: true\tHomework\n
        assertTrue(saved.startsWith("true\tHomework"));
    }

    @Test
    void savingAndLoadingRestoresTasks(@TempDir Path tempDir) throws Exception {
        // Arrange
        TaskManager original = new TaskManager();
        original.addTask("Homework");
        original.addTask("Gym");

        Path file = tempDir.resolve("tasks.txt");

        // Act
        original.saveToFile(file.toString());

        TaskManager loaded = new TaskManager();
        loaded.loadFromFile(file.toString());

        // Assert
        assertEquals(2, loaded.size());
    }

    @Test
    void saveLoadPreservesCompletionStatus(@TempDir Path tempDir) throws Exception {

        TaskManager original = new TaskManager();
        original.addTask("Homework");
        original.addTask("Gym");

        // mark Gym done
        original.toggleTask(2);

        Path file1 = tempDir.resolve("file1.txt");
        original.saveToFile(file1.toString());

        // load into a new manager
        TaskManager loaded = new TaskManager();
        loaded.loadFromFile(file1.toString());

        // save again
        Path file2 = tempDir.resolve("file2.txt");
        loaded.saveToFile(file2.toString());

        // files should match exactly
        String before = java.nio.file.Files.readString(file1);
        String after  = java.nio.file.Files.readString(file2);

        assertEquals(before, after);
    }


}
