package jerry.exceptions;

public class CorruptedSavedFileException extends JerryException {
    public CorruptedSavedFileException(String outputMessage) {
        super("Corrupted Saved File: " + outputMessage);
    }
}
