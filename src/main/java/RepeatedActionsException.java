public class RepeatedActionsException extends DukeException {
    RepeatedActionsException(String outputMessage) {
        super("Repeated Action >:( !!!!\n" + outputMessage);
    }
}