public class ExitCommand extends Command {

    public ExitCommand(String[] parsedCommand) {
        super(parsedCommand);
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.end();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
