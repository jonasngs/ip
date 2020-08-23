import java.time.LocalDate;

public abstract class Task {

    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "✓" : "✗");
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public abstract String taskFileFormat();

    public abstract LocalDate getDate();

    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "]" + this.description;
    }
}
