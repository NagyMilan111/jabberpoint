package jabberpoint;

import java.util.HashMap;

import static jabberpoint.Constants.GOTO;

public class CommandInvoker
{
    private HashMap<String, Command> commands;

    public CommandInvoker()
    {
        this.commands = new HashMap<>();
    }

    public void executeCommand(String action)
    {
        if (this.commands.containsKey(action))
        {
            this.commands.get(action).execute();
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
