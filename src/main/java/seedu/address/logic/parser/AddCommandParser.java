package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_FIX_OR_ADD_FORCE_FLAG;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FORCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Course;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(CommandPart args) throws ParseException {
        ArgumentMultimap<CommandPart> argMultimap =
                ArgumentTokenizer.tokenize(
                    args,
                    PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ROLE,
                    PREFIX_ADDRESS, PREFIX_COURSE, PREFIX_TAG, PREFIX_FORCE);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_EMAIL, PREFIX_ROLE, PREFIX_COURSE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_FORCE);

        boolean shouldCheck = ParserUtil.parseShouldCheckFlag(argMultimap);

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Optional<CommandPart> phoneString = argMultimap.getValue(PREFIX_PHONE);
        Optional<Phone> phone;
        Email email;
        Course course;
        try {
            if (phoneString.isPresent()) {
                phone = Optional.of(ParserUtil.parsePhone(phoneString.get(), shouldCheck));
            } else {
                phone = Optional.empty();
            }

            email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get(), shouldCheck);
            course = ParserUtil.parseCourse(argMultimap.getValue(PREFIX_COURSE).get(), shouldCheck);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_FIX_OR_ADD_FORCE_FLAG, pe.getMessage()),
                    pe.getErroneousPart());
        }
        Role role = ParserUtil.parseRole(argMultimap.getValue(PREFIX_ROLE).get());

        Optional<CommandPart> addressString = argMultimap.getValue(PREFIX_ADDRESS);
        Optional<Address> address;
        if (role.equals(Role.PROFESSOR)) {
            if (addressString.isPresent()) {
                address = Optional.of(ParserUtil.parseAddress(addressString.get(), shouldCheck));
            } else {
                throw new ParseException(Address.MESSAGE_CONSTRAINTS_PROFESSOR);
            }
        } else {
            // address is optional for other RoleTypes
            if (addressString.isPresent()) {
                address = Optional.of(ParserUtil.parseAddress(addressString.get(), shouldCheck));
            } else {
                address = Optional.empty();
            }
        }

        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Person person = new Person(name, phone, email, role, address, course, tagList);

        return new AddCommand(person);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
