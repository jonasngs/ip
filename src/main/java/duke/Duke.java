package duke;

import duke.task.TaskList;
import duke.command.Command;
import duke.exception.DukeException;

public class Duke {

    private final Ui ui;
    private final Storage storage;
    private final TaskList taskList;

    public Duke() {
        this.ui = new Ui();
        this.storage = new Storage();
        this.taskList = this.storage.readFile();
    }

    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                Command command = Parser.parse(fullCommand);
                command.execute(taskList, ui, storage);
                isExit = command.isExit();
            } catch (DukeException ex) {
                ui.showError(ex.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }

    public static void main(String[] args) {
        Duke session = new Duke(); // start a new session with JonasBot
        session.run(); // execute the bot to perform intended functions
    }

}
