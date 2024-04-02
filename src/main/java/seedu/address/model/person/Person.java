package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandExecutionException;
import seedu.address.model.person.exceptions.InvalidAddressException;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Optional<Phone> phone;
    private final Email email;
    private final Course course;
    private final Role role;

    // Data fields
    private final Optional<Address> address;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Optional<Phone> phone, Email email, Role role,
            Optional<Address> address, Course course, Set<Tag> tags) {

        requireAllNonNull(name, phone, email, role, address, course, tags);

        // Check for valid address based on the person's role
        validateAddress(role, address);

        this.name = name;
        this.phone = phone;
        this.email = email;
        this.role = role;
        this.address = address;
        this.course = course;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Optional<Phone> getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public Optional<Address> getAddress() {
        return address;
    }

    public Course getCourse() {
        return course;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Creates a new Person object with the given parameters.
     * @param name The name of the person.
     * @param phone The phone number of the person.
     * @param email The email of the person.
     * @param role The role of the person.
     * @param address The address of the person.
     * @param course The course of the person.
     * @param tags The tags of the person.
     * @return A new Person object.
     * @throws CommandExecutionException If the address is invalid.
     */
    public static Person createPerson(Name name, Optional<Phone> phone, Email email, Role role,
                                      Optional<Address> address, Course course, Set<Tag> tags)
            throws CommandExecutionException {
        try {
            return new Person(name, phone, email, role, address, course, tags);
        } catch (InvalidAddressException e) {
            throw new CommandExecutionException(Address.MESSAGE_CONSTRAINTS_INVALID_PROFESSOR);
        }
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns false only if Professors do not have an address.
     */
    public boolean hasValidAddress() {
        // checks if a Professor has an address
        if (this.role == Role.PROFESSOR) {
            return this.address.isPresent();
        } else {
            return true;
        }
    }

    /**
     * Validates the address based on the person's role.
     * @param role
     * @param address
     * @throws IllegalArgumentException
     */
    public static void validateAddress(Role role, Optional<Address> address) {
        if (role == Role.PROFESSOR && address.isEmpty()) {
            throw new InvalidAddressException(Address.MESSAGE_CONSTRAINTS_INVALID_PROFESSOR);
        }
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && role.equals(otherPerson.role)
                && address.equals(otherPerson.address)
                && course.equals(otherPerson.course)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, role, address, course, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("role", role)
                .add("address", address)
                .add("course", course)
                .add("tags", tags)
                .toString();
    }

}
