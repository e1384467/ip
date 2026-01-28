package jerry.exceptions;

public class FileErrorException extends JerryException {
    public FileErrorException(String outputMessage) {
        super("File Error: " + outputMessage);
    }
}
