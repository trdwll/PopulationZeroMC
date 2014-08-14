package com.trdwll.Engine.commands;

import com.trdwll.Engine.Messages;
import com.trdwll.Utilities.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandRegistry {

    private static final int COMMANDS_PER_PAGE = 5;

    private List<PZMCommand> commandList;
    private Map<String, PZMCommand> commands;

    public CommandRegistry() {
        this.commandList = new ArrayList<PZMCommand>();
        this.commands = new HashMap<String, PZMCommand>();

        init();
    }

    public void init() {
        registerWithoutHelp(new ReloadComand());

        registerCommand(new JoinCommand());
        registerCommand(new LeaveCommand());
        registerCommand(new ListCommand());
    }

    public void registerCommand(PZMCommand command) {
        if (!commandList.contains(command))
            commandList.add(command);

        registerWithoutHelp(command);
    }

    public void registerWithoutHelp(PZMCommand command) {
        for (String alias : command.getAliases())
            if (!commands.containsKey(alias))
                commands.put(alias.toLowerCase(), command);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 1 && (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("h") || args[0].equalsIgnoreCase("?"))) {
            sender.sendMessage(getHelp(1));

            return true;
        } else if (args.length == 2 && (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("h") || args[0].equalsIgnoreCase("?"))) {
            int page = 1;

            try {
                page = Integer.valueOf(args[1]);
            } catch (NumberFormatException e) { }

            sender.sendMessage(getHelp(page));

            return true;
        } else if (args.length == 2 && (args[1].equalsIgnoreCase("help") || args[1].equalsIgnoreCase("h") || args[1].equalsIgnoreCase("?"))) {
            if (commands.containsKey(args[0].toLowerCase())) {
                sender.sendMessage(commands.get(args[0].toLowerCase()).getHelp());

                return true;
            } else {
                sender.sendMessage(Messages.getInvalidCommand());

                return true;
            }
        } else if (args.length >= 1 && !args[0].equalsIgnoreCase("about")) {
            if (commands.containsKey(args[0].toLowerCase()))
                if (!commands.get(args[0].toLowerCase()).usesPermission() || sender.hasPermission(commands.get(args[0].toLowerCase()).getPermission()))
                    return commands.get(args[0].toLowerCase()).onCommand(sender, cmd, label, args);
                else {
                    sender.sendMessage(Messages.getNoPermission());

                    return true;
                }
            else {
                sender.sendMessage(Messages.getInvalidCommand());

                return true;
            }
        }

        sender.sendMessage(new String[] { Utils.translate(Messages.addPrefix("%prefix% &3About&8:")), Messages.getHelpFormat("Lead Programmer", "@trdwll | Twitter"), Messages.getHelpFormat("Lead Programmer", "@OhYea777 | BukkitDev"), Messages.getHelpFormat("Programmer", "@parkbully3 | Twitter"), Messages.getHelpFormat("QA Tester", "@dalton_test | Twitter") });

        return true;
    }

    public String[] getHelp(int page) {
        List<String> help = new ArrayList<String>();
        int pages = commandList.size() / COMMANDS_PER_PAGE + 1;

        page = Math.min(page, commandList.size() / COMMANDS_PER_PAGE + 1) > 0 ? Math.min(page, commandList.size() / COMMANDS_PER_PAGE + 1) : 1;

        help.add(Messages.getHelp("Command Help", page, pages));

        int helpLength = 1;

        for (int i = helpLength; i < helpLength + COMMANDS_PER_PAGE; i ++)
            if (commandList.size() > i - helpLength + page * COMMANDS_PER_PAGE - COMMANDS_PER_PAGE && i - helpLength + page * COMMANDS_PER_PAGE - COMMANDS_PER_PAGE > -1) {
                PZMCommand command = commandList.get(i - helpLength + page * COMMANDS_PER_PAGE - COMMANDS_PER_PAGE);

                if (command == null)
                    continue;

                help.add(i, Messages.getHelpFormat(command.getName(), "/pzm " + command.getAliases()[0] + " help"));
            }

        return help.toArray(new String[help.size()]);
    }

}
