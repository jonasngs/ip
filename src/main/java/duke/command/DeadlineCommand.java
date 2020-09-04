package duke.command;

import duke.Storage;
import duke.Ui;

import duke.exception.DukeException;
import duke.exception.InvalidFunctionException;
import duke.exception.InvalidTaskException;

import duke.task.Deadline;
import duke.task.Task;
import duke.task.TaskList;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a command to add a new deadline task to the user's list of tasks.
 */
public class DeadlineCommand extends Command {

    /** Parsed commands containing details of the deadline task. */
    private final String[] parsedCommand;

    /**
     * Creates and initialises a new DeadlineCommand object.
     *
     * @param parsedCommand String array that contains the deadline task information.
     */
    public DeadlineCommand(String[] parsedCommand) {
        this.parsedCommand = parsedCommand;
    }

    /**
     * Creates a new deadline task, adds it to the user's list of tasks
     * and saves it into the designated file that stores the user's tasks.
     *
     * @param tasks List of tasks which the new deadline task will be added into.
     * @param ui Ui object created for the Duke object.
     * @param storage Storage object used by the Duke object for file operations.
     * @return String containing the reply for successful execution of command.
     * @throws DukeException If the deadline task cannot be created due to invalid inputs.
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");
        try {
            String[] deadlineInfo = retrieveDeadlineInfo();
            String[] timeStamp = deadlineInfo[1].split(" ");

            LocalDate deadlineDate = LocalDate.parse(timeStamp[0], dateFormatter);
            LocalTime deadlineTime = LocalTime.parse(timeStamp[1], timeFormatter);

            Task toAdd = new Deadline(deadlineInfo[0], deadlineDate, deadlineTime);
            tasks.addTask(toAdd);
            storage.saveToFile(tasks);
            return ui.printNewTask(toAdd, tasks.getListSize());
        } catch (DateTimeParseException | ArrayIndexOutOfBoundsException ex) {
            String err = "The task date format is incorrect. \n"
                    + "Please input a valid date using the format: 'dd/mm/yyyy hh:mm'. For eg, 10/8/2020 18:00";
            throw new InvalidFunctionException(err);
        }
    }

    /**
     * Retrieves the details of the deadline task and stores it in an array.
     *
     * @return String array containing the deadline description and deadline time stamp.
     * @throws InvalidTaskException If the deadline information is invalid or is missing arguments.
     */
    public String[] retrieveDeadlineInfo() throws InvalidTaskException {
        String[] deadlineInfo = new String[2];
        String description;
        String time;
        try {
            String[] taskInputArray = this.parsedCommand[1].split(" /by ");
            if (!this.parsedCommand[1].contains(" /by ") && !this.parsedCommand[1].endsWith("/by")) {
                String err = "Your deadline task has an incorrect format. The task cannot be created.";
                throw new InvalidTaskException(err);
            } else if (this.parsedCommand[1].trim().equals("/by")) {
                String err = "Your deadline task is missing a description and time stamp. "
                        + "The task cannot be created.";
                throw new InvalidTaskException(err);
            } else if (this.parsedCommand[1].trim().endsWith("/by")) {
                String err = "Your deadline task is missing a time stamp. The task cannot be created.";
                throw new InvalidTaskException(err);
            } else if (taskInputArray[0].isBlank()) {
                String err = "Your deadline task is missing a description. The task cannot be created.";
                throw new InvalidTaskException(err);
            } else {
                description = taskInputArray[0].trim();
                time = taskInputArray[1].trim();
            }
            deadlineInfo[0] = description;
            deadlineInfo[1] = time;
            return deadlineInfo;
        } catch (ArrayIndexOutOfBoundsException ex) {
            String err = "Your deadline task has missing arguments. The task cannot be created.";
            throw new InvalidTaskException(err);
        }
    }

    /**
     * Indicates if the DukeBot session has ended.
     *
     * @return False since the DukeBot session has not been terminated.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
