public class MissingArgumentException extends DukeException {
    MissingArgumentException(String correctUsage) {
        super("Missing Argument >:( !!!! Please try:\n" + correctUsage);
    }
}