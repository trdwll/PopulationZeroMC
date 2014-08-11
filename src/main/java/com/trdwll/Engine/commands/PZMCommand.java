package com.trdwll.Engine.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class PZMCommand {

    public abstract String getName();

    public abstract String[] getAliases();

    public abstract String[] getHelp();

    public boolean usesPermission() {
        return false;
    }

    public String getPermission() {
        return "";
    }

    public abstract boolean onCommand(CommandSender sender, Command cmd, String label, String[] args);

}
