package seedu.address.logic;

import static seedu.address.logic.parser.CliSyntax.PREFIX_FORCE;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNCLEAR_COMMAND = "Unclear command.";
    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command.";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_FORCE_TAG_MUST_BE_EMPTY = "There must be no value following " + PREFIX_FORCE;
    public static final String MESSAGE_UNEXPECTED_ARGUMENT = "Invalid command format!\n"
        + "Command '%1$s' should not take any arguments.";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX_WITH_USAGE =
        "The contact index provided is invalid.\n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX =
        String.format(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX_WITH_USAGE, "").trim();
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d contact(s) listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_FIX_OR_ADD_FORCE_FLAG =
        "The following is one of the invalid fields(s). "
        + "Fix the issue, or append " + PREFIX_FORCE + " to ignore the warning.\n%1$s";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName());
        person.getPhone().ifPresent(phone -> builder.append("; Phone: ").append(phone));
        builder.append("; Email: ").append(person.getEmail());
        builder.append("; Role: ").append(person.getRole());
        person.getAddress().ifPresent(address -> builder.append("; Address: ").append(address));
        builder.append("; Course: ").append(person.getCourse());
        Set<Tag> tags = person.getTags();
        if (!tags.isEmpty()) {
            if (tags.size() == 1) {
                builder.append("; Tag: ");
            } else {
                builder.append("; Tags: ");
            }
            tags.forEach(builder::append);
        }
        return builder.toString();
    }

}
