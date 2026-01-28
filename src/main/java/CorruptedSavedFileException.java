public class CorruptedSavedFileException extends JerryException {
    CorruptedSavedFileException(String outputMessage) {
        super("Corrupted Saved File: " + outputMessage);
    }
}
