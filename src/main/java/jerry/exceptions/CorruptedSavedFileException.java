package jerry.exceptions;

/**
 * Represents an exception thrown when the saved data file contains invalid or corrupted entries that prevent it from being parsed correctly.
 */
public class CorruptedSavedFileException extends JerryException {

    /**
     * Constructs a {@code CorruptedSavedFileException} with the specified error message describing the corruption in the saved file.
     *
     * @param outputMessage The message detailing the cause of the file corruption.
     */
    public CorruptedSavedFileException(String outputMessage) {
        super("Corrupted Saved File: " + outputMessage);
    }
}
