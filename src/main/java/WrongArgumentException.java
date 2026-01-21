public class WrongArgumentException extends DukeException {
    WrongArgumentException(String outputMessage) {
        super("Wrong Argument >:( !!!!\n" + outputMessage);
    }
}