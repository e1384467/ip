package jerry.exceptions;

public class EmptyInputException extends InvalidCommandException {
    public EmptyInputException() {
        super("Please enter something :b\n");
    }
}
