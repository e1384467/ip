public class WrongArgumentException extends JerryException {
    WrongArgumentException(String outputMessage) {
        super("Wrong Argument >:( !!!!\n" + outputMessage);
    }
}