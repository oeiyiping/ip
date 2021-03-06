import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class Parser {
    /**
     * Parses a valid input string into the corresponding valid Commmand.
     *
     * @param input Input string to be parsed.
     * @return The corresponding Command of the input string.
     * @throws HenchmanException Thrown exception caused by the reason specified in its error body.
     */
    public static Command parse(String input) throws HenchmanException {
        String[] commandAndParams = input.split(" ", 2);
        String command = commandAndParams[0].toUpperCase();
        Command parsed = null;
        boolean hasParams = commandAndParams.length > 1 && !commandAndParams[1].isBlank();

        switch (command) {
        case "BYE":
            parsed = new CommandBye();
            break;

        case "CLEAR":
            parsed = new CommandClear();
            break;

        case "DEADLINE":
            if (!hasParams) {
                throw new HenchmanException("DEADLINE description and date cannot be empty!");
            }

            parsed = parseTimedTaskInput(commandAndParams[1], command);
            break;

        case "DELETE":
            if (!hasParams) {
                throw new HenchmanException("Task index cannot be empty!");
            }

            parsed = new CommandDelete(Integer.parseInt(commandAndParams[1]));
            break;

        case "DONE":
            if (!hasParams) {
                throw new HenchmanException("Task index cannot be empty!");
            }

            parsed = new CommandDone(Integer.parseInt(commandAndParams[1]));
            break;

        case "EVENT":
            if (!hasParams) {
                throw new HenchmanException("EVENT description and date cannot be empty!");
            }

            parsed = parseTimedTaskInput(commandAndParams[1], command);
            break;

        case "FIND":
            if (!hasParams) {
                throw new HenchmanException("Search term cannot be empty!");
            }

            parsed = new CommandFind(commandAndParams[1]);
            break;

        case "LIST":
            parsed = new CommandList();
            break;

        case "SORT":
            if (hasParams) {
                parsed = new CommandSort(commandAndParams[1]);
            } else {
                parsed = new CommandSort("CREATED");
            }
            break;

        case "TODO":
            if (!hasParams) {
                throw new HenchmanException("TODO description cannot be empty!");
            }

            parsed = new CommandToDo(commandAndParams[1]);
            break;

        default:
            throw new HenchmanException("Invalid command, please provide a supported command.");
        }

        assert parsed != null : "Error in parsed command: parsed command is null";

        return parsed;
    }

    private static Command parseTimedTaskInput(String input, String taskType) throws HenchmanException,
            DateTimeParseException {

        char taskIcon = taskType.charAt(0);
        String delimiter = null;
        String defaultTime = null;

        switch (taskIcon) {
        case 'D':
            delimiter = " /by ";
            defaultTime = "23:59:59";
            break;
        case 'E':
            delimiter = " /at ";
            defaultTime = "00:00:00";
            break;
        default:
            // do nothing on default
            break;
        }

        assert delimiter != null : "Split key is invalid.";
        String[] arguments = input.split(delimiter, 2);

        if (arguments.length != 2) {
            String errorMsg = taskType + " command must follow the format: description" + delimiter + "date";
            throw new HenchmanException(errorMsg);
        }

        String[] dateAndTime = arguments[1].split(" ");
        String date = dateAndTime[0];
        String time = dateAndTime.length > 1 // if time is not provided, use default time
                ? dateAndTime[1].substring(0, 2) + ":" + dateAndTime[1].substring(2, 4)
                : defaultTime;

        try {
            LocalDateTime parsedDateAndTime = LocalDateTime.parse(date + "T" + time);
            Command timedTaskCommand = null;
            switch (taskIcon) {
            case 'D':
                timedTaskCommand = new CommandDeadline(arguments[0], parsedDateAndTime);
                break;

            case 'E':
                timedTaskCommand = new CommandEvent(arguments[0], parsedDateAndTime);
                break;

            default:
                break; // do nothing on default
            }

            assert timedTaskCommand != null : "Error in creating timedTaskCommand.";
            return timedTaskCommand;

        } catch (DateTimeParseException e) {
            throw new HenchmanException("Please follow the Date-Time format: YYYY-MM-DD TIME");
        }
    }
}
