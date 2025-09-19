package resource.util.datastorage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import resources.util.datastorage.CheckList;
import resources.util.tasks.DeadlineTask;
import resources.util.tasks.EventTask;
import resources.util.tasks.ToDosTask;

class CheckListTest {

    private DeadlineTask deadlineTask;
    private ToDosTask toDosTask;
    private EventTask eventTask;
    private CheckList checkListTest;

    @BeforeEach
    public void setUp() {
        checkListTest = new CheckList();
        deadlineTask = new DeadlineTask("Submit assignment", LocalDateTime.now());
        toDosTask = new ToDosTask("Buy groceries");
        eventTask = new EventTask("Team meeting", LocalDateTime.now(), LocalDateTime.now().plusHours(1));
    }

    @Test
    void addTaskTest_addTasks_success() {
        checkListTest.addTask(deadlineTask);
        checkListTest.addTask(toDosTask);
        checkListTest.addTask(eventTask);

        assertEquals(3, checkListTest.getSize());
    }

    @Test
    void removeTaskByIndexTest_removeTwoTasks_success() {

        checkListTest.addTask(deadlineTask);
        checkListTest.addTask(toDosTask);
        checkListTest.addTask(eventTask);

        checkListTest.removeTaskByIndex(0);
        assertEquals(2, checkListTest.getSize());

        checkListTest.removeTaskByIndex(1);
        assertEquals(1, checkListTest.getSize());
    }

    @Test
    void getTaskByIndexTest_getTwoTasks_success() {
        checkListTest.addTask(deadlineTask);
        checkListTest.addTask(toDosTask);
        checkListTest.addTask(eventTask);


        assertEquals(deadlineTask, checkListTest.getTaskByIndex(1));
        assertEquals(toDosTask, checkListTest.getTaskByIndex(0));
        assertEquals("Submit assignment", checkListTest.getTaskByIndex(1).getDescription());
        assertEquals("Buy groceries", checkListTest.getTaskByIndex(0).getDescription());
    }

    @Test
    void markTaskTest_markTwoTasks_success() {
        checkListTest.addTask(deadlineTask);
        checkListTest.addTask(toDosTask);
        checkListTest.addTask(eventTask);

        checkListTest.markTask(0);
        assertTrue(checkListTest.getTaskByIndex(0).isCompleted());
        checkListTest.markTask(1);
        assertTrue(checkListTest.getTaskByIndex(1).isCompleted());
        checkListTest.markTask(2);
        assertTrue(checkListTest.getTaskByIndex(2).isCompleted());
    }

    @Test
    void unmarkTaskTest_unmarkTwoTasks_success() {
        checkListTest.addTask(deadlineTask);
        checkListTest.addTask(toDosTask);
        checkListTest.addTask(eventTask);

        checkListTest.unmarkTask(0);
        assertFalse(checkListTest.getTaskByIndex(0).isCompleted());
        checkListTest.unmarkTask(1);
        assertFalse(checkListTest.getTaskByIndex(1).isCompleted());
        checkListTest.markTask(2);
        assertTrue(checkListTest.getTaskByIndex(2).isCompleted());
    }
}
