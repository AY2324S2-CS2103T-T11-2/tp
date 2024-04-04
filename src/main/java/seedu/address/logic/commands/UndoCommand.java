package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandExecutionException;
import seedu.address.model.Model;

/**
 * Undo the state of the address book if there was any new changes to it.
 */
public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";

    public static final String COMMAND_DESCRIPTION = COMMAND_WORD
            + ": Undoes the changes (if any) made by the previous command execution.\n";

    public static final String MESSAGE_SUCCESS = "Successfully revert the changes.";

    public static final String MESSAGE_NO_PREVIOUS_STATE = "No new changes has been made to the data.";

    @Override
    public CommandResult execute(Model model) throws CommandExecutionException {
        requireNonNull(model);

        try {
            model.undoAddressBook();
        } catch (NullPointerException e) {
            throw new CommandExecutionException(MESSAGE_NO_PREVIOUS_STATE);
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
