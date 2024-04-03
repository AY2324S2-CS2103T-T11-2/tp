package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.CommandString;

public class CommandHistoryModelTest {
    private static final CommandString EMPTY_STRING = new CommandString("");

    private final CommandHistoryModel commandHistoryModel = new CommandHistoryModel();

    @Test
    public void emptyCommandHistory_returnTestString() {
        CommandString testString1 = new CommandString("Test1");
        CommandString testString2 = new CommandString("Test2");
        CommandString testString3 = new CommandString("Test3");

        assertEquals(testString1, commandHistoryModel.getNextCommandHistory(testString1));
        assertEquals(testString2, commandHistoryModel.getNextCommandHistory(testString2));
        assertEquals(testString3, commandHistoryModel.getPreviousCommandHistory(testString3));
        assertEquals(testString3, commandHistoryModel.getPreviousCommandHistory(testString3));
        assertEquals(testString1, commandHistoryModel.getPreviousCommandHistory(testString1));
    }

    @Test
    public void consecutiveGetNext_returnByNewest() {
        CommandString newestCommand = new CommandString("newest");
        CommandString newerCommand = new CommandString("newer");
        CommandString newCommand = new CommandString("new");

        commandHistoryModel.addCommand(newCommand);
        commandHistoryModel.addCommand(newerCommand);
        commandHistoryModel.addCommand(newestCommand);

        assertEquals(newestCommand, commandHistoryModel.getPreviousCommandHistory(EMPTY_STRING));
        assertEquals(newerCommand, commandHistoryModel.getPreviousCommandHistory(EMPTY_STRING));
        assertEquals(newCommand, commandHistoryModel.getPreviousCommandHistory(EMPTY_STRING));
    }

    @Test
    public void consecutiveGetPrevious_returnByOldest() {
        CommandString oldestCommand = new CommandString("Oldest");
        CommandString olderCommand = new CommandString("Older");
        CommandString oldCommand = new CommandString("Old");

        commandHistoryModel.addCommand(EMPTY_STRING); // Start point of the assert below.
        commandHistoryModel.addCommand(oldestCommand);
        commandHistoryModel.addCommand(olderCommand);
        commandHistoryModel.addCommand(oldCommand);

        // Go to start point.
        commandHistoryModel.getPreviousCommandHistory(EMPTY_STRING);
        commandHistoryModel.getPreviousCommandHistory(oldCommand);
        commandHistoryModel.getPreviousCommandHistory(olderCommand);
        commandHistoryModel.getPreviousCommandHistory(oldestCommand);

        assertEquals(oldestCommand, commandHistoryModel.getNextCommandHistory(EMPTY_STRING));
        assertEquals(olderCommand, commandHistoryModel.getNextCommandHistory(EMPTY_STRING));
        assertEquals(oldCommand, commandHistoryModel.getNextCommandHistory(EMPTY_STRING));
    }

    @Test
    public void currentCommandSaved() {
        CommandString currentString = new CommandString("current");
        CommandString savedString = new CommandString("saved");
        CommandString unsavedString = new CommandString("saved");

        commandHistoryModel.addCommand(unsavedString);
        commandHistoryModel.getPreviousCommandHistory(currentString);

        assertEquals(currentString, commandHistoryModel.getNextCommandHistory(savedString));
        assertEquals(savedString, commandHistoryModel.getPreviousCommandHistory(savedString));
    }

    @Test
    public void resetTempOnAddCommand() {
        CommandString helloString = new CommandString("hello");
        CommandString worldString = new CommandString("world");
        CommandString resetString = new CommandString("RESET");

        commandHistoryModel.addCommand(helloString);
        commandHistoryModel.addCommand(worldString);
        commandHistoryModel.getPreviousCommandHistory(EMPTY_STRING); // Change current command to "bye".
        commandHistoryModel.getPreviousCommandHistory(EMPTY_STRING); // Change "world" to "earth".

        commandHistoryModel.addCommand(resetString);
        assertEquals(resetString, commandHistoryModel.getPreviousCommandHistory(EMPTY_STRING));
        assertEquals(worldString, commandHistoryModel.getPreviousCommandHistory(EMPTY_STRING));
        assertEquals(helloString, commandHistoryModel.getPreviousCommandHistory(EMPTY_STRING));
    }
}
