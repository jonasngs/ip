package duke.exception;

public class DukeException extends Exception {

    public DukeException(String message) {
        super("Oh no! " + message);
    }
}