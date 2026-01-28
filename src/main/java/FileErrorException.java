public class FileErrorException extends JerryException{
    FileErrorException(String outputMessage) {
        super("File Error: " + outputMessage);
    }
}
