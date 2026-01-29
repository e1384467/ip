package jerry.exceptions;

/**
 * Represents an exception thrown when a file-related error occurs during reading from or writing to a file.
 */
public class FileErrorException extends JerryException {

    /**
     * Constructs a {@code FileErrorException} with the specified error message describing the file-related error.
     *
     * @param outputMessage The message detailing the cause of the file error.
     */
    public FileErrorException(String outputMessage) {
        super("File Error: " + outputMessage);
    }
}
