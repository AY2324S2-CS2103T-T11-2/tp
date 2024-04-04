package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_FIX_OR_ADD_FORCE_FLAG;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX_WITH_USAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FORCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(CommandPart args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap<CommandPart> argMultimap =
                ArgumentTokenizer.tokenize(args,
                    PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                    PREFIX_ROLE, PREFIX_ADDRESS, PREFIX_COURSE, PREFIX_TAG, PREFIX_FORCE);

        Index index;

        CommandPart preamble = argMultimap.getPreamble();
        try {
            index = ParserUtil.parseIndex(preamble);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX_WITH_USAGE,
                        EditCommand.MESSAGE_USAGE), pe, preamble);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(
            PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
            PREFIX_ROLE, PREFIX_ADDRESS, PREFIX_COURSE, PREFIX_FORCE);

        boolean shouldCheck = ParserUtil.parseShouldCheckFlag(argMultimap);

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editPersonDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        try {
            if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
                editPersonDescriptor.setPhone(
                        ParserUtil.parseOptionalPhone(argMultimap.getValue(PREFIX_PHONE).get(), shouldCheck));
            }
            if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
                editPersonDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get(),
                            shouldCheck));
            }
            if (argMultimap.getValue(PREFIX_COURSE).isPresent()) {
                editPersonDescriptor.setCourse(ParserUtil.parseCourse(argMultimap.getValue(PREFIX_COURSE).get(),
                            shouldCheck));
            }
            if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
                editPersonDescriptor.setAddress(ParserUtil.parseOptionalAddress(
                        argMultimap.getValue(PREFIX_ADDRESS).get(), shouldCheck));
            }
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_FIX_OR_ADD_FORCE_FLAG, pe.getMessage()),
                    pe.getErroneousPart());
        }
        if (argMultimap.getValue(PREFIX_ROLE).isPresent()) {
            editPersonDescriptor.setRole(ParserUtil.parseRole(argMultimap.getValue(PREFIX_ROLE).get()));
        }
        if (argMultimap.getValue(PREFIX_FORCE).isPresent()) {
            editPersonDescriptor.setShouldCheck(shouldCheck);
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editPersonDescriptor::setTags);

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editPersonDescriptor);
    }

    /**
     * Parses {@code Collection<CommandPart> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<CommandPart> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<CommandPart> tagSet = tags.size() == 1 && tags.toArray(new CommandPart[0])[0].isEmpty()
            ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
