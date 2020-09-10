package duke.command;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import duke.Storage;
import duke.Ui;
import duke.exception.DukeException;
import duke.exception.InvalidFunctionException;
import duke.task.Task;
import duke.task.TaskList;

/**
 * Represents a command to search for tasks using a keyword.
 */
public class FindByKeywordCommand extends Command {

    /** Parsed commands containing the search keyword. */
    private final String[] parsedCommand;

    /**
     * Creates and initialises a new FindByKeywordCommand.
     *
     * @param parsedCommand String array that contains the search keyword input.
     */
    public FindByKeywordCommand(String[] parsedCommand) {
        this.parsedCommand = parsedCommand;
    }

    /**
     * Performs the operation of searching for all the tasks in the user's list
     * of tasks that contains the keyword provided for the search.
     *
     * @param tasks List of tasks belonging to the user.
     * @param ui Ui object created for the Duke object.
     * @param storage Storage object used by the Duke object for file operations.
     * @return String containing a list of tasks that matches the search keyword.
     * @throws DukeException If no tasks could be found due to invalid keyword provided.
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        try {
            String keyword = this.parsedCommand[1].trim().toLowerCase();
            Predicate<Task> searchKeywordFilter = task -> {
                return task.getDescription().toLowerCase().contains(keyword);
            };
            List<Task> searchResults = new ArrayList<>(tasks.getTaskList())
                    .stream()
                    .filter(searchKeywordFilter)
                    .collect(Collectors.toList());
            assert !keyword.isBlank();
            return ui.showTaskList(searchResults);
        } catch (ArrayIndexOutOfBoundsException ex) {
            String error = "No keyword for the search was entered. Please enter a keyword!";
            throw new InvalidFunctionException(error);
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
