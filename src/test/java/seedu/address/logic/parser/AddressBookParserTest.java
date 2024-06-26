package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNCLEAR_COMMAND;
import static seedu.address.logic.Messages.MESSAGE_UNEXPECTED_ARGUMENT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.CourseContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.RoleContainsKeywordsPredicate;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(new CommandString(PersonUtil.getAddCommand(person)));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(new CommandString(ClearCommand.COMMAND_WORD)) instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(new CommandString(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()));
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(new CommandString(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor)));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(new CommandString(ExitCommand.COMMAND_WORD)) instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(new CommandString(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" "))));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords),
                                     new CourseContainsKeywordsPredicate(keywords),
                                     new RoleContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(new CommandString(HelpCommand.COMMAND_WORD)) instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(new CommandString(ListCommand.COMMAND_WORD)) instanceof ListCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(new CommandString("")));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand(
                    new CommandString("unknownCommand")));
    }

    @Test
    public void parseCommand_unclearCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNCLEAR_COMMAND, () -> parser.parseCommand(new CommandString("e")));
    }

    @Test
    public void parseCommand_unexpectedArgument_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_UNEXPECTED_ARGUMENT, ListCommand.COMMAND_WORD), ()
                -> parser.parseCommand(new CommandString("list x")));
        assertThrows(ParseException.class, String.format(MESSAGE_UNEXPECTED_ARGUMENT, ExitCommand.COMMAND_WORD), ()
                -> parser.parseCommand(new CommandString("exit y")));
        assertThrows(ParseException.class, String.format(MESSAGE_UNEXPECTED_ARGUMENT, ClearCommand.COMMAND_WORD), ()
                -> parser.parseCommand(new CommandString("clear 1")));
    }
}
