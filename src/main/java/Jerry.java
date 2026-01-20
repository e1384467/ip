import java.util.Scanner;
import java.util.ArrayList;

public class Jerry {

    public static final String CHATBOT_NAME = "Jerry";
    public static final String EXIT = "bye";
    public static final String SHOW_LIST = "list";
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

    public void addTask() {
        Scanner scan = new Scanner(System.in);
        String userInput;
        while (true) {
            System.out.print("User input: ");
            userInput = scan.nextLine();
            if (userInput.equalsIgnoreCase(EXIT)) {
                break;
            }
            if (userInput.equalsIgnoreCase(SHOW_LIST)) {
                if (this.taskList.isEmpty())
                {
                    System.out.println(CHATBOT_NAME +
                            ": your list is currently empty. Type to add more!\n");
                } else {
                    printList();
                }
                continue;
            }
            this.taskList.add(userInput);
            System.out.println(CHATBOT_NAME +
                    ": I have added " +
                    userInput +
                    " to your list!\n");
        }
    }

    public static void main(String[] args) {
        Jerry jerry = new Jerry();
        System.out.println("Hello! I'm " + CHATBOT_NAME);
        System.out.println("What can I do for you?\n");
        jerry.addTask();
        System.out.println("Bye. Hope to see you again soon!");
    }
}
