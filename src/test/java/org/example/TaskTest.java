package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    @Test
    void newTaskStartsNotCompleted() {
        Task t = new Task("Study");
        assertFalse(t.isCompleted());
    }

    @Test
    void toggleChangesCompletionState() {
        Task t = new Task("Study");
        t.toggle();
        assertTrue(t.isCompleted());
    }

    @Test
    void toggleTwiceReturnsToOriginalState() {
        Task t = new Task("Study");
        t.toggle();
        t.toggle();
        assertFalse(t.isCompleted());
    }



}
