public class MissingArgumentException extends JerryException {
    MissingArgumentException(String correctUsage) {
        super("Missing Argument >:( !!!! Please try:\n" + correctUsage);
    }
}