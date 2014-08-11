package com.trdwll.Engine.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

public class CommandRegistry {

    private static final int COMMANDS_PER_PAGE = 10;

    private Map<String, PZMCommand> commands;

    public CommandRegistry() {
        this.commands = new HashMap<String, PZMCommand>();

        init();
    }

    public void init() {

    }

    public void registerCommand(PZMCommand command) {
        for (String alias : command.getAliases())
            if (!commands.containsKey(alias))
                commands.put(alias, command);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 1 && (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("h") || args[0].equalsIgnoreCase("?") || args[0].equalsIgnoreCase("i") || args[0].equalsIgnoreCase("info"))) {

        } else if (args.length == 2 && (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("h") || args[0].equalsIgnoreCase("?") || args[0].equalsIgnoreCase("i") || args[0].equalsIgnoreCase("info"))) {

        }

        return false;
    }

    public String[] getHelp(int page) {
        String[] help = new String[] { };



        return help;
    }

}
