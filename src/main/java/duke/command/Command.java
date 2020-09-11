package duke.command;

import duke.Storage;
import duke.Ui;
import duke.exception.DukeException;
import duke.task.TaskList;

/**
 * Represents a command to be executed by the DukeBot.
 */
public abstract class Command {

    /**
     * Executes the relevant command.
     *
     * @param tasks List of tasks belonging to the user.
     * @param ui Ui object created for the Duke object.
     * @param storage Storage object used by the Duke object for file operations.
     * @throws DukeException If the command could not be executed successfully.
     */
    public abstract String execute(TaskList tasks, Ui ui, Storage storage) throws DukeException;

    /**
     * Indicates if the DukeBot session has ended.
     *
     * @return True if the DukeBot session is going to be terminated, false otherwise.
     */
    public abstract boolean isExit();

}
