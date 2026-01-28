package jerry.exceptions;

public class RepeatedActionsException extends JerryException {
    public RepeatedActionsException(String outputMessage) {
        super("Repeated Action >:( !!!!\n" + outputMessage);
    }
}