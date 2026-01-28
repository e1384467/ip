package jerry.exceptions;

public class MissingFileException extends JerryException {
    public MissingFileException(String outputMessage) {
        super("Missing File: " + outputMessage);
    }
}
