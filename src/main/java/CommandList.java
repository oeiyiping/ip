public class CommandList extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.printCommand(this);
        tasks.printTasks();
    }

    @Override
    public String toDukeOutput() {
        return "Aye boss, here to see your \"collection\" eh?";
    }
}