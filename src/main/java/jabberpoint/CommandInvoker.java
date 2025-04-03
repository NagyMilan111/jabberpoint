package jabberpoint;

import java.util.HashMap;

public class CommandInvoker
{
    private HashMap<String, Command> commands;

    public CommandInvoker()
    {
        this.commands = new HashMap<>();
    }

    public void executeCommand(String action, Integer slideNumber)
    {
        if ("Goto".equals(action) && this.commands.containsKey(action))
        {
            this.commands.get(action).execute(slideNumber);
        }
        else if (this.commands.containsKey(action))
        {
            this.commands.get(action).execute(null);
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
