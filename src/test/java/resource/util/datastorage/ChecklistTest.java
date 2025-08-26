package resource.util.datastorage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import resources.util.datastorage.Checklist;
import resources.util.tasks.DeadlineTask;
import resources.util.tasks.EventTask;
import resources.util.tasks.ToDosTask;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class ChecklistTest {

    @Mock
    private DeadlineTask deadlineTask;

    @Mock
    private ToDosTask toDosTask;

    @Mock
    private EventTask eventTask;

    private Checklist checklistTest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        checklistTest = new Checklist();
    }

    @Test
    void addTaskTest_addTasks_success() {
        checklistTest.addTask(deadlineTask);
        checklistTest.addTask(toDosTask);
        checklistTest.addTask(eventTask);

        assertEquals(3, checklistTest.getSize());
    }

    @Test
    void removeTaskByIndexTest_removeTwoTasks_success() {

        checklistTest.addTask(deadlineTask);
        checklistTest.addTask(toDosTask);
        checklistTest.addTask(eventTask);

        checklistTest.removeTaskByIndex(0);
        assertEquals(2, checklistTest.getSize());

        checklistTest.removeTaskByIndex(1);
        assertEquals(1, checklistTest.getSize());
    }

    @Test
    void getTaskByIndexTest_getTwoTasks_success() {
        checklistTest.addTask(deadlineTask);
        checklistTest.addTask(toDosTask);
        checklistTest.addTask(eventTask);

        when(deadlineTask.getDescription()).thenReturn("Submit assignment");
        when(toDosTask.getDescription()).thenReturn("Buy groceries");

        assertEquals(deadlineTask, checklistTest.getTaskByIndex(0));
        assertEquals(toDosTask, checklistTest.getTaskByIndex(1));
        assertEquals("Submit assignment", checklistTest.getTaskByIndex(0).getDescription());
        assertEquals("Buy groceries", checklistTest.getTaskByIndex(1).getDescription());
    }

    @Test
    void markTaskTest_markTwoTasks_success() {
        checklistTest.addTask(deadlineTask);
        checklistTest.addTask(toDosTask);
        checklistTest.addTask(eventTask);

        when(deadlineTask.isCompleted()).thenReturn(false);
        when(toDosTask.isCompleted()).thenReturn(false);
        when(eventTask.isCompleted()).thenReturn(true);

        checklistTest.markTask(0);
        assertFalse(checklistTest.getTaskByIndex(0).isCompleted());
        checklistTest.markTask(1);
        assertFalse(checklistTest.getTaskByIndex(1).isCompleted());
        checklistTest.markTask(2);
        assertTrue(checklistTest.getTaskByIndex(2).isCompleted());
    }

    @Test
    void unmarkTaskTest_unmarkTwoTasks_success() {
        checklistTest.addTask(deadlineTask);
        checklistTest.addTask(toDosTask);
        checklistTest.addTask(eventTask);

        when(deadlineTask.isCompleted()).thenReturn(true);
        when(toDosTask.isCompleted()).thenReturn(true);
        when(eventTask.isCompleted()).thenReturn(false);

        checklistTest.unmarkTask(0);
        assertTrue(checklistTest.getTaskByIndex(0).isCompleted());
        checklistTest.unmarkTask(1);
        assertTrue(checklistTest.getTaskByIndex(1).isCompleted());
        checklistTest.unmarkTask(2);
        assertFalse(checklistTest.getTaskByIndex(2).isCompleted());
    }
}
