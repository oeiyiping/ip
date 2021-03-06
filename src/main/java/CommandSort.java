/**
 * Represents the Command for "sort" for the Henchman object. Sorts the tasks in TaskList according to the sort key.
 */
public class CommandSort extends Command {
    private final String sortBy;

    public CommandSort(String sortBy) {
        this.sortBy = sortBy;
    }

    /**
     * Sorts the tasks in TaskList according to the sort key and returns the newly sorted tasks.
     *
     * @param tasks TaskList of Henchman object.
     * @param storage Storage of Henchman object.
     * @return Return message containing all tasks in the newly-sorted tasks.
     */
    @Override
    public String execute(TaskList tasks, Storage storage) {
        boolean isSorted = tasks.sortTasks(sortBy);
        if (isSorted) {
            storage.save(tasks);
            StringBuilder sortedTasks = new StringBuilder(toHenchmanOutput()).append(sortBy).append("!\n");
            sortedTasks.append(tasks.printTasks());

            return sortedTasks.toString();
        } else {
            return "Invalid sort key, task remains unsorted.";
        }
    }

    /**
     * Returns the string representation of the message to be printed.
     *
     * @return String representation of the message to be printed.
     */
    @Override
    public String toHenchmanOutput() {
        return "Task sorted by: ";
    }
}
