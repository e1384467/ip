package jerry.commands;

import jerry.exceptions.InvalidCommandException;

/**
 * Represents the set of valid command keywords supported by Jerry.
 * Each enum constant corresponds to a command a user can enter (e.g., {@code todo}, {@code list}).
 */
public enum Commands {
    BYE,
    LIST,
    MARK,
    UNMARK,
    TODO,
    DEADLINE,
    EVENT,
    DELETE;

    /**
     * Returns the {@code Commands} enum that matches the given user command input.
     * The matching is case-insensitive (e.g., {@code "todo"} and {@code "TODO"} both match {@link #TODO}).
     *
     * @param userCommandInput The raw command keyword entered by the user.
     * @return The corresponding {@code Commands} enum constant.
     * @throws InvalidCommandException If {@code userCommandInput} does not match any supported command keyword.
     */
    public static Commands getCommand(String userCommandInput) throws InvalidCommandException {
        try {
            return Commands.valueOf(userCommandInput.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidCommandException();
        }
    }
}
