package jerry.exceptions;

public class WrongArgumentException extends JerryException {
    public WrongArgumentException(String outputMessage) {
        super("Wrong Argument >:( !!!!\n" + outputMessage);
    }
}
