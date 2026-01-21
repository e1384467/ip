public class RepeatedActionsException extends JerryException {
    RepeatedActionsException(String outputMessage) {
        super("Repeated Action >:( !!!!\n" + outputMessage);
    }
}