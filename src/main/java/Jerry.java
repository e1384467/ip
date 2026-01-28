import java.util.Scanner;

public class Jerry {
    private Ui ui;
    private TaskList taskList;

    public Jerry() {
        this.ui = new Ui(new Scanner(System.in));
        try {
            this.taskList = new TaskList(Storage.initialise());
        } catch (JerryException e) {
            this.ui.showError(e.getMessage());
            this.taskList = new TaskList();
        }
    }

    public void  run() {
        ui.showWelcome();
        while (true) {
            try {
                String userInput = this.ui.readUserInput();
                String[] userInputArray = userInput.split("\\s+");
                Commands userCommand = Commands.getCommand(userInputArray[0]);

                switch (userCommand) {
                case BYE:
                    Storage.save(taskList);
                    ui.showBye();
                    return;

                case LIST:
                    ui.displayList(taskList);
                    break;

                case MARK:
                    Task markTask = this.taskList.markTask(Parser.getArrayIndex(userInputArray));
                    ui.showMark(markTask);
                    Storage.save(taskList);
                    break;

                case UNMARK:
                    Task unmarkTask = this.taskList.unmarkTask(Parser.getArrayIndex(userInputArray));
                    ui.showUnmark(unmarkTask);
                    Storage.save(taskList);
                    break;

                case TODO:
                    Task todoTask = Parser.parseTodo(userInput.substring(Commands.TODO.toString().length()).trim());
                    this.taskList.add(todoTask);
                    ui.showAdd(todoTask, taskList.size());
                    Storage.save(taskList);
                    break;

                case DEADLINE:
                    Task deadlineTask = Parser.parseDeadline(userInput.substring(Commands.DEADLINE.toString().length()).trim());
                    this.taskList.add(deadlineTask);
                    ui.showAdd(deadlineTask, taskList.size());
                    Storage.save(taskList);
                    break;

                case EVENT:
                    Task eventTask = Parser.parseEvent(userInput.substring(Commands.EVENT.toString().length()).trim());
                    this.taskList.add(eventTask);
                    ui.showAdd(eventTask, taskList.size());
                    Storage.save(taskList);
                    break;

                case DELETE:
                    Task deletedTask = taskList.deleteTask(Parser.getArrayIndex(userInputArray));
                    ui.showDelete(deletedTask, taskList.size());
                    Storage.save(taskList);
                    break;
                }
            } catch (JerryException e) {
                this.ui.showError(e.getMessage());
            }
        }
    }

    public static void main(String[] args)  {
            Jerry jerry = new Jerry();
            jerry.run();

    }
}
