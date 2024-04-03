package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.parser.CommandString;

/**
 * A model to store the past valid CommandStrings entered by the user. Allows for users to revisit past commands.
 * Note that this model saves the commands edited in the command box while the user is scrolling through them.
 */
public class CommandHistoryModel {

    private final List<CommandString> commandHistoryList;
    private List<CommandString> tempHistoryList;
    private int commandHistoryIndex;

    /**
     * Constructs a {@code CommandHistoryModel} object with an optional input string of {@code commands}.
     */
    public CommandHistoryModel(CommandString... commands) {
        commandHistoryList = new ArrayList<>(Arrays.asList(commands));
        resetTempList();
    }

    /**
     * Add a new command string to the command history.
     * @param command A string representing the new command to be added.
     */
    public void addCommand(CommandString command) {
        requireNonNull(command);

        commandHistoryList.add(command);
        resetTempList();
    }

    /**
     * Reset the state of {@code tempHistoryList} to be equal to {@code commandHistoryList}.
     */
    private void resetTempList() {
        tempHistoryList = new ArrayList<>(commandHistoryList);
        tempHistoryList.add(new CommandString("")); // Extra index to store current command.
        commandHistoryIndex = commandHistoryList.size(); // Reset index to point at current command.
    }

    /**
     * Replaces the current command with {@code currrentCommand} and returns the next newest command in
     * the command history. If the model is already at the newest command, then that command will be returned instead.
     */
    public CommandString getNextCommandHistory(CommandString currrentCommand) {
        // Updates command at current index with currentText.
        tempHistoryList.set(commandHistoryIndex, currrentCommand);

        commandHistoryIndex = Math.min(commandHistoryIndex + 1, commandHistoryList.size());
        return tempHistoryList.get(commandHistoryIndex);
    }

    /**
     * Replaces the current command with {@code currrentCommand} and returns the next oldest command in
     * the command history. If the model is already at the oldest command, then that command will be returned instead.
     */
    public CommandString getPreviousCommandHistory(CommandString currrentCommand) {
        // Updates command at current index with currrentCommandText.
        tempHistoryList.set(commandHistoryIndex, currrentCommand);

        commandHistoryIndex = Math.max(commandHistoryIndex - 1, 0);
        return tempHistoryList.get(commandHistoryIndex);
    }
}
