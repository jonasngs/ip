package duke.task;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {

    protected LocalDate eventDate;
    protected LocalTime eventTime;

    public Event(String description, LocalDate eventDate, LocalTime eventTime) {
        super(description);
        this.eventDate = eventDate;
        this.eventTime = eventTime;
    }

    @Override
    public LocalDate getDate() {
        return this.eventDate;
    }

    @Override
    public String taskFileString() {
        return "E | " + (super.isDone ? "1 |" : "0 |") + super.description + " | " +
                this.eventDate.toString() + " " + this.eventTime.toString();
    }

    @Override
    public String toString() {
        String formattedEventTime = this.eventDate.format(DateTimeFormatter.ofPattern("EE, MMM dd yyyy")) + ", " +
                this.eventTime.format(DateTimeFormatter.ofPattern("hh:mm a"));
        return "[E]" + super.toString() + " (at: " + formattedEventTime + ")";
    }
}
