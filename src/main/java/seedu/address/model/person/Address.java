package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;


/**
 * Represents a Person's address in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAddress(String)}
 */
public class Address {

    public static final String MESSAGE_CONSTRAINTS = "Addresses can take any values, and it should not be blank";
    public static final String MESSAGE_CONSTRAINTS_PROFESSOR = String.format(
        "Please input the professor's office address. Use: %sADDRESS", PREFIX_ADDRESS);
    public static final String MESSAGE_CONSTRAINTS_INVALID_PROFESSOR = "Professors cannot have empty addresses!";
    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;
    protected final boolean shouldCheck;

    /**
     * Constructs an {@code Address}.
     *
     * @param address A valid address.
     * @param shouldCheck If true, check if the address is valid.
     */
    public Address(String address, boolean shouldCheck) {
        requireNonNull(address);
        if (shouldCheck) {
            checkArgument(isValidAddress(address), MESSAGE_CONSTRAINTS);
        }
        this.shouldCheck = shouldCheck;
        value = address;
    }

    /**
     * Constructs a valid {@code Address}.
     *
     * @param address A valid address.
     */
    public Address(String address) {
        this(address, true);
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidAddress(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Address)) {
            return false;
        }

        Address otherAddress = (Address) other;
        return value.equals(otherAddress.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
