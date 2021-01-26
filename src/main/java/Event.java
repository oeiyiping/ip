public class Event extends Task {
    protected String at;

    public Event(String name, String at) {
        super(name, "E");
        this.at = at;
    }

    public Event(String input) throws DukeException {
        super(input.split(" /at ", 2)[0], "E");

        String[] split = input.split(" /at ", 2);
        if (split.length != 2) {
            throw new DukeException("Event command must follow the format: description /at time");
        }

        this.at = split[1];
    }

    public String toString() {
        return super.toString() + " (at: " + at + ")";
    }

    public String toLog() {
        return super.toLog() + " | " + at;
    }
}
