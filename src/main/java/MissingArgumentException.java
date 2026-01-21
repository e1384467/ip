public class MissingArgumentException extends DukeException {
    MissingArgumentException(String correctUsage) {
        super(correctUsage);
    }
}