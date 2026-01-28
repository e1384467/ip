import java.util.Scanner;
import java.util.ArrayList;

public class Jerry {

    private final ArrayList<Task> taskList;
    private final Ui ui;

    public Jerry() throws JerryException {
        this.taskList = Storage.initialise();
        this.ui = new Ui(new Scanner(System.in));
    }

    public void deleteTask(int targetIndex) throws JerryException {
        Task targetTask = taskList.get(targetIndex);
        taskList.remove(targetTask);
        ui.showDelete(targetTask, taskList.size());
    }

    public void markTask(int targetIndex) throws JerryException  {
        Task targetTask = taskList.get(targetIndex);
        if (targetTask.isDone) {
            throw new RepeatedActionsException( "You've made a mistake, "
                    + targetTask
                    + " is already marked as done\n");
        }
        targetTask.toggleIsDone();
        ui.showMark(targetTask);

    }

    public void unmarkTask(int targetIndex) throws JerryException {
        Task targetTask = taskList.get(targetIndex);
        if (!targetTask.isDone) {
            throw new RepeatedActionsException("You've made a mistake, "
                    + targetTask
                    + " is already unmarked as not done yet\n");
        }
        targetTask.toggleIsDone();
        ui.showMark(targetTask);

    }

    public void addTodoTask(String taskDescription) throws JerryException {
        Task task = Parser.parseTodo(taskDescription);
        taskList.add(task);
        ui.showAdd(task, taskList.size());
    }

    public void addDeadlineTask(String userInput) throws JerryException {
        Task task = Parser.parseDeadline(userInput);
        taskList.add(task);
        ui.showAdd(task, taskList.size());
    }

    public void addEventTask(String userInput) throws JerryException {
        Task task = Parser.parseEvent(userInput);
        taskList.add(task);
        ui.showAdd(task, taskList.size());
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
                    markTask(Parser.getArrayIndex(userInputArray, this.taskList.size()));
                    Storage.save(taskList);
                    break;

                case UNMARK:
                    unmarkTask(Parser.getArrayIndex(userInputArray, this.taskList.size()));
                    Storage.save(taskList);
                    break;

                case TODO:
                    addTodoTask(userInput.substring(Commands.TODO.toString().length()).trim());
                    Storage.save(taskList);
                    break;

                case DEADLINE:
                    addDeadlineTask(userInput.substring(Commands.DEADLINE.toString().length()).trim());
                    Storage.save(taskList);
                    break;

                case EVENT:
                    addEventTask(userInput.substring(Commands.EVENT.toString().length()).trim());
                    Storage.save(taskList);
                    break;

                case DELETE:
                    deleteTask(Parser.getArrayIndex(userInputArray, this.taskList.size()));
                    Storage.save(taskList);
                    break;
                }
            } catch (JerryException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    public static void main(String[] args)  {
        try {
            Jerry jerry = new Jerry();
            jerry.run();
        } catch (JerryException e) {
            System.out.println(e.getMessage());
        }

    }
}
