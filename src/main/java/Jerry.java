import java.util.Scanner;

public class Jerry {

    public static final String CHATBOT_NAME = "Jerry";
    public static final String EXIT = "bye";

    public static void echo() {
        Scanner scan = new Scanner(System.in);
        String userInput;
        while (true) {
            System.out.print("User input: ");
            userInput = scan.nextLine();
            if (userInput.equalsIgnoreCase(EXIT)) {
                break;
            }
            System.out.println(CHATBOT_NAME + ": " + userInput + "\n");
        }
    }

    public static void main(String[] args) {
        System.out.println("Hello! I'm " + CHATBOT_NAME);
        System.out.println("What can I do for you?\n");
        echo();
        System.out.println("Bye. Hope to see you again soon!");
    }
}
