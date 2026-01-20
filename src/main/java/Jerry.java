import java.util.Scanner;
import java.util.ArrayList;

public class Jerry {

    public static final String CHATBOT_NAME = "Jerry";
    public static final String EXIT = "bye";
    public static final String SHOW_LIST = "list";
    public static final String MARK_TASK = "mark";
    public static final String UNMARK_TASK = "unmark";

    private final ArrayList<String> taskList;

    public Jerry() {
        this.taskList = new ArrayList<>();
    }

    public void printList() {
        System.out.println("Your list:");
        for (int index = 0; index < this.taskList.size(); index += 1) {
            System.out.println(index + 1 + ". " + this.taskList.get(index));
        }
        System.out.println();
    }
    

    public void readUserInput() {
        Scanner scan = new Scanner(System.in);
        String userInput;
        while (true) {
            System.out.print("User input: ");
            userInput = scan.nextLine();
            String[] userInputByWord = userInput.split("\\s+");
            String userCommand = userInputByWord[0].toLowerCase();

            switch (userCommand) {
                case EXIT:
                    return;
                case SHOW_LIST:
                    if (this.taskList.isEmpty()) {
                        System.out.println(CHATBOT_NAME +
                                ": your list is currently empty. Type to add more!\n");
                    } else {
                        printList();
                    }
                    break;
                case MARK_TASK:

                    break;
                case UNMARK_TASK:

                    break;

                default:


            }
        }
    }

    public static void main(String[] args) {
        Jerry jerry = new Jerry();
        System.out.println("Hello! I'm " + CHATBOT_NAME);
        System.out.println("What can I do for you?\n");
        jerry.readUserInput();
        System.out.println("Bye. Hope to see you again soon!");
    }
}
