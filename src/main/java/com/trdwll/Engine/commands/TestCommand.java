package com.trdwll.Engine.commands;

import com.trdwll.Engine.Messages;
import com.trdwll.Utilities.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class TestCommand extends PZMCommand {

    @Override
    public String getName() {
        return "Test";
    }

    @Override
    public String[] getAliases() {
        return new String[] { "test" };
    }

    @Override
    public String[] getHelp() {
        return new String[] { Utils.translate(Messages.addPrefix("%prefix% &3" + getName() + " Help&8:")), Messages.getHelpFormat("Test", "To test stuffs use /pzm test") };
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        sender.sendMessage("TEST");

        return false;
    }

}
