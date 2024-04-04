package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_FORCE_TAG_MUST_BE_EMPTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FORCE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Course;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_DUPLICATE_INDEX = "There is a duplicate Index listed.";
    public static final String MESSAGE_ROLE_CONSTRAINTS =
            "Roles should be either 'STUDENT', 'TA', or 'PROFESSOR', or an unambiguous prefix of it.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(CommandPart oneBasedIndex) throws ParseException {
        CommandPart trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex.toString())) {
            throw new ParseException(MESSAGE_INVALID_INDEX, trimmedIndex);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex.toString()));
    }

    /**
     * Parses {@code args}, seperated by a common {@code sep}, into a list of
     * distinct {@code Index} and returns it.
     *
     * @throws ParseException If any of the specified index is invalid, or there are duplicate input indices.
     */
    public static List<Index> parseIndices(CommandPart args, String sep) throws ParseException {
        final CommandPart[] splitArgs = args.split(sep);
        final ArrayList<Index> indices = new ArrayList<>();

        for (int i = 0; i < splitArgs.length; i++) {
            Index index = parseIndex(splitArgs[i]);
            if (indices.contains(index)) {
                // Duplicated Index.
                throw new ParseException(MESSAGE_DUPLICATE_INDEX);
            }

            indices.add(index);
        }

        return indices;
    }

    /**
     * Parses a {@code CommandPart name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(CommandPart name) throws ParseException {
        requireNonNull(name);
        CommandPart trimmedName = name.trim();
        if (!Name.isValidName(trimmedName.toString())) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS, trimmedName);
        }
        return new Name(trimmedName.toString());
    }

    /**
     * Parses a {@code CommandPart phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param shouldCheck If true, check if the phone number is valid.
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(CommandPart phone, boolean shouldCheck) throws ParseException {
        requireNonNull(phone);
        CommandPart trimmedPhone = phone.trim();
        try {
            return new Phone(trimmedPhone.toString(), shouldCheck);
        } catch (IllegalArgumentException e) {
            throw new ParseException(e.getMessage(), trimmedPhone);
        }
    }

    public static Phone parsePhone(CommandPart phone) throws ParseException {
        return parsePhone(phone, true);
    }

    /**
     * Parses a {@code CommandPart phone} into a {@code Optional<Phone>}, allowing empty input.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param shouldCheck If true, check if the phone number is valid.
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Optional<Phone> parseOptionalPhone(CommandPart phone, boolean shouldCheck) throws ParseException {
        requireNonNull(phone);
        CommandPart trimmedPhone = phone.trim();
        if (trimmedPhone.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(parsePhone(trimmedPhone, shouldCheck));
    }

    public static Optional<Phone> parseOptionalPhone(CommandPart phone) throws ParseException {
        return parseOptionalPhone(phone, true);
    }

    /**
     * Parses a {@code CommandPart address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param shouldCheck If true, check if the address is valid.
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(CommandPart address, boolean shouldCheck) throws ParseException {
        requireNonNull(address);
        CommandPart trimmedAddress = address.trim();
        try {
            return new Address(trimmedAddress.toString(), shouldCheck);
        } catch (IllegalArgumentException e) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS, trimmedAddress);
        }
    }

    public static Address parseAddress(CommandPart address) throws ParseException {
        return parseAddress(address, true);
    }

    /**
     * Parses a {@code CommandPart address} into a {@code Optional<Address>}, allowing empty input.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param shouldCheck If true, check if the address is valid.
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Optional<Address> parseOptionalAddress(CommandPart address, boolean shouldCheck)
            throws ParseException {
        requireNonNull(address);
        CommandPart trimmedAddress = address.trim();
        if (trimmedAddress.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(parseAddress(address, shouldCheck));
    }

    public static Optional<Address> parseOptionalAddress(CommandPart address) throws ParseException {
        return parseOptionalAddress(address, true);
    }

    /**
     * Parses a {@code CommandPart email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param shouldCheck If true, check if the phone number is valid.
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(CommandPart email, boolean shouldCheck) throws ParseException {
        requireNonNull(email);
        CommandPart trimmedEmail = email.trim();
        try {
            return new Email(trimmedEmail.toString(), shouldCheck);
        } catch (IllegalArgumentException e) {
            throw new ParseException(e.getMessage(), trimmedEmail);
        }
    }

    public static Email parseEmail(CommandPart email) throws ParseException {
        return parseEmail(email, true);
    }

    /**
     * Parses a {@code CommandPart course} into an {@code Course}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param shouldCheck If true, check if the course is valid.
     * @throws ParseException if the given {@code course} is invalid.
     */
    public static Course parseCourse(CommandPart course, boolean shouldCheck) throws ParseException {
        requireNonNull(course);
        CommandPart trimmedCourse = course.trim();
        try {
            return new Course(trimmedCourse.toString(), shouldCheck);
        } catch (IllegalArgumentException e) {
            throw new ParseException(e.getMessage(), trimmedCourse);
        }
    }

    /**
     * Parses a {@code CommandPart role} from user input into a {@code Role}.
     * Leading and trailing whitespaces will be trimmed.
     * Allows specifying a role case-insensitively, and matching by an unambiguous prefix.
     *
     * @throws ParseException if the given {@code role} is invalid.
     */
    public static Role parseRole(CommandPart role) throws ParseException {
        requireNonNull(role);
        CommandPart trimmedRole = role.trim();
        List<String> matchedRoles = ParserUtil.filterByPrefix(trimmedRole.toString().toUpperCase(), Role.getAllRoles());
        if (matchedRoles.size() == 1) {
            return Role.valueOf(matchedRoles.get(0));
        } else {
            throw new ParseException(MESSAGE_ROLE_CONSTRAINTS, trimmedRole);
        }
    }

    /**
     * Parses a {@code CommandPart tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(CommandPart tag) throws ParseException {
        requireNonNull(tag);
        CommandPart trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag.toString())) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS, trimmedTag);
        }
        return new Tag(trimmedTag.toString());
    }

    /**
     * Parses {@code Collection<CommandPart> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<CommandPart> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (CommandPart tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Returns a new list containing strings from an input array that start with the given prefix.
     * The strings are case and space sensitive (i.e. "hello" will not match " hello" or "HELLO").
     * Note that if the given prefix is an empty string, the returned list will contain all the strings
     * in the input array.
     *
     * @param prefix Input prefix string.
     * @param strings An array of strings to match with the given prefix.
     * @return List of strings that start with the given prefix.
     */
    public static List<String> filterByPrefix(String prefix, String[] strings) {
        final List<String> matchedStrings = new ArrayList<>();

        for (String string : strings) {
            if (string.startsWith(prefix)) {
                matchedStrings.add(string);
            }
        }

        return matchedStrings;
    }

    /**
     * Parses the {@code shouldCheck} flag from the argument multimap.
     * @return true if the {@code PREFIX_FORCE} flag is absent, false otherwise.
     */
    public static boolean parseShouldCheckFlag(ArgumentMultimap<CommandPart> argMultimap) throws ParseException {
        boolean isForced = argMultimap.getValue(PREFIX_FORCE).isPresent();
        if (isForced && !argMultimap.getValue(PREFIX_FORCE).get().isEmpty()) {
            throw new ParseException(MESSAGE_FORCE_TAG_MUST_BE_EMPTY, argMultimap.getValue(PREFIX_FORCE).get());
        }
        boolean shouldCheck = !isForced;
        return shouldCheck;
    }
}
