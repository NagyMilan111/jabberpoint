package jabberpoint;

import java.util.HashMap;

public class CommandInvoker
{
    private HashMap<String, Command> commands;

    public CommandInvoker()
    {
        commands = new HashMap<>();
    }

    public void executeCommand(String action, Integer slideNumber) {
        if (commands.containsKey(action)) {
            commands.get(action).execute(null);
        }
        else if (action.equals("GOTO") && commands.containsKey(action))
        {
            commands.get(action).execute(slideNumber);
        }
    }

    public HashMap<String, Command> getCommands()
    {
        return this.commands;
    }

    public void setCommands(HashMap<String, Command> commands)
    {
        this.commands = commands;
    }

    public void addCommand(String action, Command command)
    {
        this.commands.put(action, command);
    }
}
