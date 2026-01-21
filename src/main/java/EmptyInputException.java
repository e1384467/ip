public class EmptyInputException extends InvalidCommandException{
    EmptyInputException() {
        super("Please enter something :b\n");
    }
}