package resource.util.datastorage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import resources.util.datastorage.CheckList;
import resources.util.tasks.DeadlineTask;
import resources.util.tasks.EventTask;
import resources.util.tasks.ToDosTask;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class CheckListTest {

    @Mock
    private DeadlineTask deadlineTask;

    @Mock
    private ToDosTask toDosTask;

    @Mock
    private EventTask eventTask;

    private CheckList checkListTest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        checkListTest = new CheckList();
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

        when(deadlineTask.getDescription()).thenReturn("Submit assignment");
        when(toDosTask.getDescription()).thenReturn("Buy groceries");

        assertEquals(deadlineTask, checkListTest.getTaskByIndex(0));
        assertEquals(toDosTask, checkListTest.getTaskByIndex(1));
        assertEquals("Submit assignment", checkListTest.getTaskByIndex(0).getDescription());
        assertEquals("Buy groceries", checkListTest.getTaskByIndex(1).getDescription());
    }

    @Test
    void markTaskTest_markTwoTasks_success() {
        checkListTest.addTask(deadlineTask);
        checkListTest.addTask(toDosTask);
        checkListTest.addTask(eventTask);

        when(deadlineTask.isCompleted()).thenReturn(false);
        when(toDosTask.isCompleted()).thenReturn(false);
        when(eventTask.isCompleted()).thenReturn(true);

        checkListTest.markTask(0);
        assertFalse(checkListTest.getTaskByIndex(0).isCompleted());
        checkListTest.markTask(1);
        assertFalse(checkListTest.getTaskByIndex(1).isCompleted());
        checkListTest.markTask(2);
        assertTrue(checkListTest.getTaskByIndex(2).isCompleted());
    }

    @Test
    void unmarkTaskTest_unmarkTwoTasks_success() {
        checkListTest.addTask(deadlineTask);
        checkListTest.addTask(toDosTask);
        checkListTest.addTask(eventTask);

        when(deadlineTask.isCompleted()).thenReturn(true);
        when(toDosTask.isCompleted()).thenReturn(true);
        when(eventTask.isCompleted()).thenReturn(false);

        checkListTest.unmarkTask(0);
        assertTrue(checkListTest.getTaskByIndex(0).isCompleted());
        checkListTest.unmarkTask(1);
        assertTrue(checkListTest.getTaskByIndex(1).isCompleted());
        checkListTest.unmarkTask(2);
        assertFalse(checkListTest.getTaskByIndex(2).isCompleted());
    }
}
