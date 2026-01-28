public class MissingFileException extends JerryException {
    MissingFileException(String outputMessage) {
        super("Missing File: " + outputMessage);
    }
}
