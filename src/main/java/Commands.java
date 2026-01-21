import java.util.Locale;

public enum Commands {
    BYE,
    LIST,
    MARK,
    UNMARK,
    TODO,
    DEADLINE,
    EVENT,
    DELETE;

    public  static Commands getCommand(String userCommandInput) throws InvalidCommandException {
        try {
            return Commands.valueOf(userCommandInput.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidCommandException();
        }
    }

}