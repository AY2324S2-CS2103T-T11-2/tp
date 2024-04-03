package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandExecutionException;
import seedu.address.logic.parser.CommandString;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.CommandHistoryModel;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;
    private final CommandHistoryModel commandHistory;

    @FXML
    private TextField commandTextField;

    /**
     * Creates a {@code CommandBox} with the given {@code CommandExecutor}.
     */
    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        this.commandHistory = new CommandHistoryModel();

        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        final String commandText = commandTextField.getText();
        if (commandText.equals("")) {
            return;
        }

        final CommandString commandString = new CommandString(commandText);
        try {
            commandExecutor.execute(commandString);
            commandHistory.addCommand(commandString);
            commandTextField.setText("");
        } catch (CommandExecutionException | ParseException e) {
            setStyleToIndicateCommandFailure();
            e.getErroneousPart().ifPresent(erroneousPart -> {
                commandTextField.selectRange(erroneousPart.getStartIndex(), erroneousPart.getEndIndex());
            });
        }
    }

    /**
     * Handles any key button pressed event. Currently supports only UP and DOWN keys.
     *
     * @param keyEvent The key pressed while command box is in focus.
     */
    @FXML
    private void handleKeyPressed(KeyEvent keyEvent) {
        final KeyCode keyCode = keyEvent.getCode();

        if (keyCode == KeyCode.UP) {
            CommandString currentCommandText = new CommandString(commandTextField.getText());
            String text = commandHistory.getPreviousCommandHistory(currentCommandText).toString();

            commandTextField.setText(text);
            commandTextField.positionCaret(text.length()); // Set caret to be at the right-end.
        } else if (keyCode == KeyCode.DOWN) {
            CommandString currentCommandText = new CommandString(commandTextField.getText());
            String text = commandHistory.getNextCommandHistory(currentCommandText).toString();

            commandTextField.setText(text);
            commandTextField.positionCaret(text.length()); // Set caret to be at the right-end.
        }
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command and returns the result.
         *
         * @see seedu.address.logic.Logic#execute(CommandString)
         */
        CommandResult execute(CommandString commandText) throws CommandExecutionException, ParseException;
    }

}
