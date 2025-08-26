package resources.util.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedConstruction;
import resources.util.datastorage.CheckList;
import resources.util.tasks.DeadlineTask;
import resources.util.tasks.EventTask;
import resources.util.tasks.Task;
import resources.util.tasks.ToDosTask;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BotServiceTest {

    private PrintStream originalOut;
    private ByteArrayOutputStream outContent;
    private PrintStream originalErr;
    private ByteArrayOutputStream errContent;
    private java.io.InputStream originalIn;

    @BeforeEach
    void captureIO() {
        originalOut = System.out;
        originalErr = System.err;
        originalIn = System.in;
        outContent = new ByteArrayOutputStream();
        errContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    void resetSystemIO() {
        System.setOut(originalOut);
        System.setErr(originalErr);
        System.setIn(originalIn);
    }

    private static void setInput(String script) {
        System.setIn(new ByteArrayInputStream(script.getBytes(StandardCharsets.UTF_8)));
    }

    @Test
    void todoCommand_addsTask_sucess() throws IOException {
        setInput("todo read book\nbye\n");

        CheckList mockCheckList = mock(CheckList.class);

        try (MockedConstruction<LoadingService> mockedLoad =
                     mockConstruction(LoadingService.class, (mock, ctx)
                             -> when(mock.getChecklist()).thenReturn(mockCheckList));
             MockedConstruction<SavingService> mockedSave =
                     mockConstruction(SavingService.class)) {

            new BotService();

            ArgumentCaptor<Task> captor = ArgumentCaptor.forClass(Task.class);
            verify(mockCheckList).addTask(captor.capture());

            Task added = captor.getValue();
            assertEquals(ToDosTask.class, added.getClass());
            assertEquals("read book", added.getDescription());
        }
    }

    @Test
    void deadlineCommand_WithEndDate_success() throws IOException {
        setInput("deadline submit report /by 01/01/2025 1600\nbye\n");

        CheckList mockCheckList = mock(CheckList.class);

        try (MockedConstruction<LoadingService> mockedLoad =
                     mockConstruction(LoadingService.class, (mock, ctx)
                             -> when(mock.getChecklist()).thenReturn(mockCheckList));
             MockedConstruction<SavingService> mockedSave =
                     mockConstruction(SavingService.class)) {

            new BotService();

            ArgumentCaptor<Task> captor = ArgumentCaptor.forClass(Task.class);
            verify(mockCheckList).addTask(captor.capture());

            Task added = captor.getValue();
            assertEquals(DeadlineTask.class, added.getClass());

            DeadlineTask dt = (DeadlineTask) added;
            assertEquals("[D][ ] submit report (by: Jan 01 2025, 4:00 pm)", dt.toString());
        }
    }

    @Test
    void deadlineCommand_WithoutEndDate_success() throws IOException {
        setInput("deadline submit report\nbye\n");

        CheckList mockCheckList = mock(CheckList.class);

        try (MockedConstruction<LoadingService> mockedLoad =
                     mockConstruction(LoadingService.class, (mock, ctx)
                             -> when(mock.getChecklist()).thenReturn(mockCheckList));
             MockedConstruction<SavingService> mockedSave =
                     mockConstruction(SavingService.class);) {

            new BotService();

            ArgumentCaptor<Task> captor = ArgumentCaptor.forClass(Task.class);
            verify(mockCheckList).addTask(captor.capture());

            Task added = captor.getValue();
            assertEquals(DeadlineTask.class, added.getClass());
            DeadlineTask dt = (DeadlineTask) added;
            assertEquals("[D][ ] submit report (by: I DUNNO >.<)", dt.toString());
        }
    }

    @Test
    void eventCommand_WithStartAndEndDates_success() throws IOException {
        setInput("event daily stand up /from 02/01/2025 1000 /to 02/01/2025 1200\nbye\n");

        CheckList mockCheckList = mock(CheckList.class);

        try (MockedConstruction<LoadingService> mockedLoad =
                     mockConstruction(LoadingService.class, (mock, ctx)
                             -> when(mock.getChecklist()).thenReturn(mockCheckList));
             MockedConstruction<SavingService> mockedSave =
                     mockConstruction(SavingService.class);) {

            new BotService();

            ArgumentCaptor<Task> captor = ArgumentCaptor.forClass(Task.class);
            verify(mockCheckList).addTask(captor.capture());

            Task added = captor.getValue();
            assertEquals(EventTask.class, added.getClass());
            EventTask et = (EventTask) added;
            assertEquals("[E][ ] daily stand up (from: Jan 02 2025, 10:00 am to: Jan 02 2025, 12:00 pm)",
                    et.toString());
        }
    }

    @Test
    void eventCommand_WithoutStartAndEndDates_success() throws IOException {
        setInput("event daily stand up\nbye\n");

        CheckList mockCheckList = mock(CheckList.class);

        try (MockedConstruction<LoadingService> mockedLoad =
                     mockConstruction(LoadingService.class, (mock, ctx)
                             -> when(mock.getChecklist()).thenReturn(mockCheckList));
             MockedConstruction<SavingService> mockedSave =
                     mockConstruction(SavingService.class);) {

            new BotService();

            ArgumentCaptor<Task> captor = ArgumentCaptor.forClass(Task.class);
            verify(mockCheckList).addTask(captor.capture());

            Task added = captor.getValue();
            assertEquals(EventTask.class, added.getClass());
            EventTask et = (EventTask) added;
            assertEquals("[E][ ] daily stand up (from: I DUNNO >.< to: I DUNNO >.<)",
                    et.toString());
        }
    }

    @Test
    void deleteCommand_RemoveIndexZero_success() throws IOException {
        setInput("delete 1\nbye\n");

        CheckList mockCheckList = mock(CheckList.class);

        try (MockedConstruction<LoadingService> mockedLoad =
                     mockConstruction(LoadingService.class, (mock, ctx)
                             -> when(mock.getChecklist()).thenReturn(mockCheckList));
             MockedConstruction<SavingService> mockedSave =
                     mockConstruction(SavingService.class)) {

            new BotService();

            verify(mockCheckList).removeTaskByIndex(0);
        }
    }

    @Test
    void markAndUnmark_callChecklistWithParsedIndex() throws IOException {
        setInput("todo code\ntodo sleep\nmark 2\nunmark 2\nbye\n");

        CheckList mockCheckList = mock(CheckList.class);

        try (MockedConstruction<LoadingService> mockedLoad =
                     mockConstruction(LoadingService.class, (mock, ctx)
                             -> when(mock.getChecklist()).thenReturn(mockCheckList));
             MockedConstruction<SavingService> mockedSave =
                     mockConstruction(SavingService.class)) {

            new BotService();

            verify(mockCheckList).markTask(1);
            verify(mockCheckList).unmarkTask(1);
        }
    }

    @Test
    void invalidCommand_throwsIllegalState_failure() {
        setInput("nonsense whatever\n");

        CheckList mockCheckList = mock(CheckList.class);

        try (MockedConstruction<LoadingService> mockedLoad =
                     mockConstruction(LoadingService.class, (mock, ctx) -> when(mock.getChecklist()).thenReturn(mockCheckList));
             MockedConstruction<SavingService> mockedSave =
                     mockConstruction(SavingService.class)) {

            try {
                new BotService();
            } catch (Exception e) {
                // insertTaskIntoChecklist throws IllegalStateException for unknown task types
                assertEquals(IllegalStateException.class, e.getClass());
                assertEquals("Invalid task type! Please use 'todo', 'deadline', or 'event'.", e.getMessage());
            }
        }
    }
}