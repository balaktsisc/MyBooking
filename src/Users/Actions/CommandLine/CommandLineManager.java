package Users.Actions.CommandLine;

import Users.User;

import java.io.Serializable;
import java.util.*;

/**
 *  Command Line manager class, takes the available commands for the corresponding user and runs them with the correct
 *  parameters, while also doing general checks on the input
 *  @author Neron Panagiotopoulos
 */
public abstract class CommandLineManager implements Serializable {

    protected Command[] commandsList;
    protected final User parentUser;

    public CommandLineManager(User parentUser){
        this.parentUser = parentUser;
    }

    /**
     * Begin a command-line user interface session and run the appropriate method for each command input by the user.
     */
    public void showInterface() {
        //Initialize a Session for the user interface.
        System.out.println(welcomeString());
        System.out.println(commandsString());
        Scanner inputStream = new Scanner(System.in);
        String input = inputStream.nextLine().toLowerCase(Locale.ROOT);

        String result = parse(input);

        //While logout hasn't been called by the user, we're in a session, so we're exchanging output and user input.
        while (!result.equals("logout")) {
            String output = result;
            System.out.println(output);
            System.out.println(commandsString());

            input = inputStream.nextLine().toLowerCase(Locale.ROOT);
            result = parse(input);
        }

    }

    private String parse(String input){
        //Split input into a command keyword (Leading word) and it's parameters (as a String).
        String commandName = input.split(" ")[0];
        List<String> arguments = new ArrayList<>(List.of(input.substring(commandName.length()).trim().split(" +")));

        //If the list just contains an empty string, remove it
        if (arguments.get(0).equals("")){
            arguments.remove(0);
        }

        //If the command is logout, default to logging out, instead of passing through users
        if (commandName.equals("logout")) {
            return "logout";
        }

        //Get command object from input string
        Command command = null;
        for (Command commandCandidate : commandsList){
            if (commandCandidate.getCommandName().equals(commandName)){
                command = commandCandidate;
                break;
            }
        }

        if (command == null){
            return "Unknown command! Please try again!";
        }

        //Basic syntax check before invoking
        if (arguments.size() > command.getMaxParams()){
            return "Too many arguments!\nExpected: " + command.getUsage();
        }

        if (arguments.size() < command.getMinParams()){
            return "Too few arguments!\nExpected: " + command.getUsage();
        }

        // Invoke the command
        return command.run(parentUser, arguments);

    }

    protected String welcomeString() {
        return String.format("Welcome %s!", parentUser.getFullName());
    }

    protected String commandsString() {
        List<String> commands = new ArrayList<>();
        for (Command command : this.commandsList){
            commands.add(command.getCommandName());
        }
        Collections.sort(commands);
        return "Commands: " + String.join(" ", commands) + " logout";
    }

}
