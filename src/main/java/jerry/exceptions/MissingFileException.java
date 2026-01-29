package jerry.exceptions;

/**
 * Represents an exception thrown when a required file is missing or cannot be accessed.
 */
public class MissingFileException extends JerryException {

    /**
     * Constructs a {@code MissingFileException} with the specified error message describing the missing file.
     *
     * @param outputMessage The message detailing the cause of the missing file error.
     */
    public MissingFileException(String outputMessage) {
        super("Missing File: " + outputMessage);
    }
}
