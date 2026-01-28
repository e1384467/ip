package jerry.exceptions;

public class MissingArgumentException extends JerryException {
    public MissingArgumentException(String correctUsage) {
        super("Missing Argument >:( !!!! Please try:\n" + correctUsage);
    }
}